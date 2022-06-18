package uz.gita.dictionaryuzen.presentation.ui.screens

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dictionaryuzen.R
import uz.gita.dictionaryuzen.data.model.ItemData
import uz.gita.dictionaryuzen.databinding.ScreenFavoriteBinding
import uz.gita.dictionaryuzen.databinding.ScreenInfoBinding
import uz.gita.dictionaryuzen.presentation.adapter.DictionaryAdapter
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.FavoriteViewModel
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.InfoViewModel
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.iml.FavoriteViewModelImpl
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.iml.InfoViewModelImpl

@AndroidEntryPoint
class FavoriteScreen : Fragment(R.layout.screen_favorite) {
    private val viewModel: FavoriteViewModel by viewModels<FavoriteViewModelImpl>()
    private val binding by viewBinding(ScreenFavoriteBinding::bind)
    private val adapter = DictionaryAdapter()
    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.back.setOnClickListener {
            viewModel.goToBackScreen()
        }
        adapter.setonItemClickListener {
            viewModel.goToNextScreen(it)
        }

        viewModel.getAllData()

        viewModel.favoriteWordLiveData.observe(viewLifecycleOwner,favoriteWordObserver)
        viewModel.goToBackStackLiveData.observe(viewLifecycleOwner,goToBackScreenObserver)
        viewModel.goToNextScreenLiveData.observe(this@FavoriteScreen,goToNextScreenObserver)
    }
    private val favoriteWordObserver = Observer<Cursor>{
        Log.d("FFF","${it.count}")
        if (it.count == 0){
            binding.emptyPleysholder.visibility = View.VISIBLE
        }else{
            binding.emptyPleysholder.visibility = View.GONE
        }
        adapter.submitItem(it)
        adapter.notifyDataSetChanged()
    }
    private val goToBackScreenObserver = Observer<Unit>{
        findNavController().popBackStack()
    }
    private val goToNextScreenObserver = Observer<ItemData>{
        val bundle  = Bundle()
        bundle.putSerializable("DATA",it)
        findNavController().navigate(R.id.action_favoriteScreen_to_infoScreen,bundle)
    }
}