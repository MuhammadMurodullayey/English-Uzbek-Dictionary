package uz.gita.dictionaryuzen.presentation.ui.viewmodels

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.dictionaryuzen.data.model.ItemData

interface InfoViewModel {
    val goToBackScreenLiveData : LiveData<Unit>
    val getNearestData  : LiveData<Cursor>
    val goToNextScreenLiveData : LiveData<ItemData>

    fun goToBackScreen()
    fun addToFavorite(data : ItemData)
    fun deleteFromFavorite(data : ItemData)
    fun getEngNearestData(word : String)
    fun getUzNearestData(word : String)

    fun goToNextScreen(data : ItemData)

}