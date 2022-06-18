package uz.gita.dictionaryuzen.presentation.ui.viewmodels.iml

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.dictionaryuzen.data.model.ItemData
import uz.gita.dictionaryuzen.domain.AppRepository
import uz.gita.dictionaryuzen.presentation.ui.viewmodels.ScreenItemListViewModel
import javax.inject.Inject

@HiltViewModel
class ScreenItemListViewModelImpl @Inject constructor(
    private val repository: AppRepository
): ViewModel(),ScreenItemListViewModel {
    private var canI = true

    override val getWordsLiveData =  MutableLiveData<Cursor>()
    override val changePageLiveData = MutableLiveData<Int>()
    override val goToNextScreenLiveData =  MutableLiveData<ItemData>()
    override val goToFaforiteScreen =  MutableLiveData<Unit>()
    override fun getAllEnglishDataByQuery(query: String) {
        repository.getEnglishWordByQuery(query).onEach {
            getWordsLiveData.value = it
        }.launchIn(viewModelScope)
    }

    override fun getAllUzbekDataByQuery(query: String) {
       repository.getUzbekWordByQuery(query).onEach {
           getWordsLiveData.value = it
       }.launchIn(viewModelScope)
    }

    override fun goToFavoriteScreen() {
        goToFaforiteScreen.value =Unit
    }

    override fun goToNextScreen(data: ItemData) {
        if (canI){
            canI = false
            goToNextScreenLiveData.value = data
            viewModelScope.launch(Dispatchers.IO) {
                delay(100)
                canI = true
            }
        }
    }

    override fun changePage(page: Int) {
        changePageLiveData.value = page
    }

    override fun getAllEnglishData() {
        repository.getAllEnglishWord().onEach {
            getWordsLiveData.value = it
        }.launchIn(viewModelScope)
    }

    override fun getAllUzbekData() {
        repository.getAllUzbekWord().onEach {
            getWordsLiveData.value = it
        }.launchIn(viewModelScope)
    }
}