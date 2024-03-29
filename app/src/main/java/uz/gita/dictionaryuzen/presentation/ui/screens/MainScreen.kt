package uz.gita.dictionaryuzen.presentation.ui.screens

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.startUpdateFlowForResult
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dictionaryuzen.R
import uz.gita.dictionaryuzen.databinding.ScreenMainBinding
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.MainViewModel
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.iml.MainViewModelImpl
import uz.gita.dictionaryuzen.utils.Position


@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private val binding by viewBinding(ScreenMainBinding::bind)


    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        updateVersion()
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
        binding.share.setOnClickListener {
            val intentInvite = Intent(Intent.ACTION_SEND)
            intentInvite.type = "text/plain"
            val body = "https://play.google.com/store/apps/details?id=uz.gita.dictionaryuzen"
            val subject = "Your Subject"
            intentInvite.putExtra(Intent.EXTRA_SUBJECT, subject)
            intentInvite.putExtra(Intent.EXTRA_TEXT, body)
            startActivity(Intent.createChooser(intentInvite, "Share using"))
        }

        binding.englishUzBtn.setOnClickListener {
            viewModel.goToNextScreen(0)
        }
        binding.uzEnglishBtn.setOnClickListener {
            viewModel.goToNextScreen(1)
        }
        viewModel.goToNextScreenLiveData.observe(this@MainScreen,goToNextScreenObserver)

    }

     private fun updateVersion(){
         val appUpdateManager = AppUpdateManagerFactory.create(requireContext())
         val appUpdateInfoTask = appUpdateManager.appUpdateInfo

         appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
             if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                 && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
                 appUpdateManager.startUpdateFlowForResult(
                     appUpdateInfo,
                     AppUpdateType.FLEXIBLE,
                     this,
                     5
                 )
             }else{
                 Log.d("MY_APP", "this is last version")
             }
         }
     }
    private val goToNextScreenObserver = Observer<Int>{
        Position.POS = it
        findNavController().navigate(R.id.action_mainScreen_to_screenItemList)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 5) {
            if (resultCode != RESULT_OK) {
                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
                updateVersion()
            }else{
                Log.d("MY_APP","APP SUCCESSFULLY UPDATED")
            }
        }
    }
}


