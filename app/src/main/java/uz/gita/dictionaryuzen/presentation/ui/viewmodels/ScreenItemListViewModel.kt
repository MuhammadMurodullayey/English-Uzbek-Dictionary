package uz.gita.dictionaryuzen.presentation.ui.viewmodels

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.dictionaryuzen.data.model.ItemData

interface ScreenItemListViewModel {
    val getWordsLiveData : LiveData<Cursor>
    val changePageLiveData : LiveData<Int>
    val goToFaforiteScreen : LiveData<Unit>
    val goToNextScreenLiveData : LiveData<ItemData>

    fun goToNextScreen(data : ItemData)
    fun goToFavoriteScreen()
    fun getAllEnglishData()
    fun getAllUzbekData()
    fun getAllEnglishDataByQuery(query : String)
    fun getAllUzbekDataByQuery(query: String)
    fun changePage(page : Int)
}