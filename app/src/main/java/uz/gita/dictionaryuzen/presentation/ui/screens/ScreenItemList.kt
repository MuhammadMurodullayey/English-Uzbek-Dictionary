package uz.gita.dictionaryuzen.presentation.ui.screens

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dictionaryuzen.R
import uz.gita.dictionaryuzen.data.model.ItemData
import uz.gita.dictionaryuzen.databinding.ScreenDictionaryListBinding
import uz.gita.dictionaryuzen.presentation.adapter.DictionaryAdapter
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.ScreenItemListViewModel
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.iml.ScreenItemListViewModelImpl
import uz.gita.dictionaryuzen.utils.Position


@AndroidEntryPoint
class ScreenItemList: Fragment(R.layout.screen_dictionary_list) {
    private val viewModel: ScreenItemListViewModel by viewModels<ScreenItemListViewModelImpl>()
    private val binding by viewBinding(ScreenDictionaryListBinding::bind)
    private val adapter = DictionaryAdapter()
    private var page = 0

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.changePage(Position.POS)
        binding.recycler.adapter = adapter

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getWordsLiveData.observe(viewLifecycleOwner, getWordsObserver)
        viewModel.changePageLiveData.observe(viewLifecycleOwner, changePageObserver)
        viewModel.goToNextScreenLiveData.observe(this@ScreenItemList, goToNextScreenObserver)
        viewModel.goToFaforiteScreen.observe(this@ScreenItemList, goToFavoriteScreenObserver)

        adapter.setonItemClickListener {
            viewModel.goToNextScreen(it)
        }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (page == 0) {
                    viewModel.getAllEnglishDataByQuery("$p0%")
                    adapter.query = p0
                } else {
                    viewModel.getAllUzbekDataByQuery("$p0%")
                    adapter.query = p0
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (page == 0) {
                    Log.d("SSS", "$p0")
                    viewModel.getAllEnglishDataByQuery("$p0%")
                    adapter.query = p0
                } else {
                    Log.d("SSS", "$p0")
                    viewModel.getAllUzbekDataByQuery("$p0%")
                    adapter.query = p0
                }
                return true
            }

        })

        binding.btnExchange.setOnClickListener {
            binding.search.setQuery(null, false)
            adapter.query = ""
            if (page == 0) {
                viewModel.changePage(1)
                Position.POS = 1
            } else {
                viewModel.changePage(0)
                Position.POS = 0
            }
        }
        binding.btnStar.setOnClickListener {
            viewModel.goToFavoriteScreen()
        }

    }

    private val getWordsObserver = Observer<Cursor> {
        Log.d("TTT", "${it.count}")
        if (it.count == 0) {
            binding.emptyPleysholder.visibility = View.VISIBLE
        } else {
            binding.emptyPleysholder.visibility = View.GONE
        }
        adapter.submitItem(it)
    }
    private val changePageObserver = Observer<Int> {
        if (it == 0) {
            adapter.speakerVisibility = true
            viewModel.getAllEnglishData()
            page = 0
            binding.search.queryHint = "Search-Qidiruv"
        } else {
            adapter.speakerVisibility = false
            viewModel.getAllUzbekData()
            page = 1
            binding.search.queryHint = "Qidiruv-Search"
        }
    }
    private val goToNextScreenObserver = Observer<ItemData> {
        val bundle = Bundle()
        bundle.putSerializable("DATA", it)
        binding.search.setQuery(null, false)
        adapter.query = ""
        findNavController().navigate(R.id.action_screenItemList_to_infoScreen, bundle)
    }
    private val goToFavoriteScreenObserver = Observer<Unit> {
        binding.search.setQuery(null, false)
        adapter.query = ""
        findNavController().navigate(R.id.action_screenItemList_to_favoriteScreen)
    }

}