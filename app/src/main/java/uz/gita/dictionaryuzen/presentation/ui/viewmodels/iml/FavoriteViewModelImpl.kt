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
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.FavoriteViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModelImpl@Inject constructor(
    private val repository: AppRepository
)  : ViewModel(), FavoriteViewModel {
    override val goToBackStackLiveData =  MutableLiveData<Unit>()
    override val favoriteWordLiveData =  MutableLiveData<Cursor>()
    override val goToNextScreenLiveData =  MutableLiveData<ItemData>()

    override fun goToNextScreen(data: ItemData) {
        goToNextScreenLiveData.value = data
    }

    override fun goToBackScreen() {
        goToBackStackLiveData.value = Unit
    }

    override fun getAllData() {
        repository.getAllEnglishFavoriteWords().onEach {
            favoriteWordLiveData.value = it
        }.launchIn(viewModelScope)
    }
}