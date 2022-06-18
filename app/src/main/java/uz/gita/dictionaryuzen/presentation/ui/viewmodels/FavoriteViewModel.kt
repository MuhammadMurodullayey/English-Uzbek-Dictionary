package uz.gita.dictionaryuzen.presentation.ui.viewmodels

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.dictionaryuzen.data.model.ItemData

interface FavoriteViewModel {
    val goToBackStackLiveData  :LiveData<Unit>
    val goToNextScreenLiveData  :LiveData<ItemData>
    val favoriteWordLiveData : LiveData<Cursor>


    fun goToBackScreen()
    fun goToNextScreen(data: ItemData)
    fun getAllData()
}