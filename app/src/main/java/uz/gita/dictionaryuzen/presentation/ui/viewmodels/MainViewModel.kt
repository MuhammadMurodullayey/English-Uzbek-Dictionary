package uz.gita.dictionaryuzen.presentation.ui.viewmodels

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

interface MainViewModel {
    val goToNextScreenLiveData : LiveData<Int>

    fun goToNextScreen(id : Int)

}