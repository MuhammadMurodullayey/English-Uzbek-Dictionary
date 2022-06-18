package uz.gita.dictionaryuzen.presentation.ui.viewmodels.iml

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.dictionaryuzen.domain.AppRepository
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.MainViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val repository: AppRepository
): ViewModel(), MainViewModel {

    override val goToNextScreenLiveData =  MutableLiveData<Int>()

    override fun goToNextScreen(id: Int) {
        goToNextScreenLiveData.value = id
    }


}