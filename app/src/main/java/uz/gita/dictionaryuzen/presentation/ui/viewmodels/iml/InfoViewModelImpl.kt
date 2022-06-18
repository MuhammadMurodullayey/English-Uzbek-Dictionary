package uz.gita.dictionaryuzen.presentation.ui.viewmodels.iml

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dictionaryuzen.data.model.ItemData
import uz.gita.dictionaryuzen.domain.AppRepository
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.InfoViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModelImpl @Inject constructor(
    private val repository: AppRepository
): ViewModel(),InfoViewModel {
    override val goToBackScreenLiveData =  MutableLiveData<Unit>()
    override val getNearestData = MutableLiveData<Cursor>()
    override val goToNextScreenLiveData =  MutableLiveData<ItemData>()

    override fun goToNextScreen(data: ItemData) {
        goToNextScreenLiveData.value = data
    }

    override fun getEngNearestData(word: String) {
        repository.getNearEngWords(word).onEach {
            getNearestData.value = it
        }.launchIn(viewModelScope)
    }

    override fun getUzNearestData(word: String) {
        repository.getNearUzWords(word).onEach {
            getNearestData.value = it
        }.launchIn(viewModelScope)
    }

    override fun goToBackScreen() {
        goToBackScreenLiveData.value = Unit
    }

    override fun addToFavorite(data: ItemData) {
        repository.addToFavourite(data.id).onEach {

        }.launchIn(viewModelScope)
    }

    override fun deleteFromFavorite(data: ItemData) {
        repository.deleteFromFavourite(data.id).onEach {

        }.launchIn(viewModelScope)
    }
}