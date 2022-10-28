package uz.gita.dictionaryuzen.presentation.ui.screens

import android.database.Cursor
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dictionaryuzen.R
import uz.gita.dictionaryuzen.data.model.ItemData
import uz.gita.dictionaryuzen.databinding.ScreenInfoBinding
import uz.gita.dictionaryuzen.presentation.adapter.DictionaryAdapter
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.InfoViewModel
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.iml.InfoViewModelImpl
import uz.gita.dictionaryuzen.utils.Position
import java.util.*

@AndroidEntryPoint
class InfoScreen : Fragment(R.layout.screen_info) {
    private val viewModel: InfoViewModel by viewModels<InfoViewModelImpl>()
    private val binding by viewBinding(ScreenInfoBinding::bind)
    private val adapter = DictionaryAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.getSerializable("DATA") as ItemData
        val adRequest = AdRequest.Builder()
            .build()
        binding.adView.loadAd(adRequest)
        binding.adView.adListener = object  : AdListener(){
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d("TTT","onAdLoaded")
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                // Code to be executed when an ad request fails.
                Log.d("TTT","onAdFailedToLoad $p0")
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d("TTT","onAdOpened")
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d("TTT","onAdClicked")
            }



            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.d("TTT","onAdClosed")
            }
        }
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter.setonItemClickListener {
            viewModel.goToNextScreen(it)
        }
        if (Position.POS == 0){
            viewModel.getEngNearestData(data.english)
        }else{
            viewModel.getUzNearestData(data.uzbek)
        }
        val speech = TextToSpeech(binding.root.context, TextToSpeech.OnInitListener {

        })
        speech.setLanguage(Locale.UK)
        binding.speaker.setOnClickListener {
            speech.speak(data.english,TextToSpeech.QUEUE_FLUSH,null)

        }



        if (Position.POS == 0) {
            binding.word.text = data.english
            binding.word2.text = data.translate
            binding.mean.text = data.uzbek
            binding.type.text = data.type
            binding.speaker.visibility = View.VISIBLE
            binding.word2.visibility = View.VISIBLE
            binding.near.text = "nearest words"

        } else {
            binding.word.text = data.uzbek
            binding.word2.text = data.translate
            binding.mean.text = data.english
            binding.type.text = data.type
            binding.speaker.visibility = View.GONE
            binding.word2.visibility = View.GONE
            binding.near.text = "o'xshash so'zlar"
        }
        if (data.isFavourite == 0) {
            binding.btnStar.setImageResource(R.drawable.starpale)
        } else {
            binding.btnStar.setImageResource(R.drawable.star)
        }
        binding.back.setOnClickListener {
            viewModel.goToBackScreen()
        }
        binding.btnStar.setOnClickListener {
            if (data.isFavourite == 0) {
                Log.d("YYY", "LIKE")
                viewModel.addToFavorite(data)
                binding.btnStar.setImageResource(R.drawable.starpale)
                data.isFavourite = 1
            } else {
                Log.d("YYY", "DISLIKE")
                viewModel.deleteFromFavorite(data)
                binding.btnStar.setImageResource(R.drawable.star)
                data.isFavourite = 0
            }
        }


        viewModel.goToBackScreenLiveData.observe(viewLifecycleOwner, goToBackScreenObserver)
        viewModel.getNearestData.observe(viewLifecycleOwner,getNearestDataObserver)
        viewModel.goToNextScreenLiveData.observe(viewLifecycleOwner,goToNextScreenObserver)
    }
    private val goToNextScreenObserver = Observer<ItemData>{
        val bundle  = Bundle()
        bundle.putSerializable("DATA",it)
        findNavController().navigate(R.id.action_infoScreen_self,bundle)
    }
    private val getNearestDataObserver = Observer<Cursor>{
        adapter.submitItem(it)
    }

    private val goToBackScreenObserver = Observer<Unit> {
        findNavController().popBackStack()
    }
}