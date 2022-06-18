package uz.gita.dictionaryuzen.domain

import android.database.Cursor
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getAllEnglishWord() : Flow<Cursor>
    fun getAllUzbekWord() : Flow<Cursor>
    fun getEnglishWordByQuery(query : String) : Flow<Cursor>
    fun getUzbekWordByQuery(query : String) : Flow<Cursor>
    fun addToFavourite(id: Int) : Flow<Unit>
    fun deleteFromFavourite(id: Int) : Flow<Unit>
    fun getAllEnglishFavoriteWords(): Flow<Cursor>
    fun getAllUzbekFavoriteWords(): Flow<Cursor>
    fun getNearEngWords(word : String) : Flow<Cursor>
    fun getNearUzWords(word : String) : Flow<Cursor>


}