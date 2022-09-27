package uz.gita.dictionaryuzen.data.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query
import uz.gita.dictionaryuzen.data.local.entity.DictionaryEntity

@Dao
interface DatabaseDao {

    @Query(
        "select id, english , type,transcript, uzbek, is_favourite as isFavourite " +
                "from dictionary"
    )
    fun getAllEnglishWords(): Cursor

    @Query(
        "select id, english , type,transcript, uzbek, is_favourite as isFavourite " +
                "from dictionary where english = :word1 or english = :word2 or english = :word3 or english = :word4"
    )
    fun getNearEnglishWords(word1: String, word2: String, word3: String, word4: String): Cursor

    @Query(
        "select id, english , type,transcript, uzbek, is_favourite as isFavourite " +
                "from dictionary where isFavourite = 1"
    )
    fun getAllEnglishFavoriteWords(): Cursor

    @Query(
        "select id, english , type,transcript, uzbek, is_favourite as isFavourite " +
                "from dictionary  ORDER BY uzbek ASC"
    )
    fun getAllUzbekWords(): Cursor

    @Query(
        "select id, english , type,transcript, uzbek, is_favourite as isFavourite " +
                "from dictionary where uzbek = :word1 or uzbek = :word2 or uzbek = :word3  or uzbek = :word4 ORDER BY uzbek ASC"
    )
    fun getNearUzbekWords(word1: String, word2: String, word3: String, word4: String): Cursor

    @Query(
        "select id, english , type,transcript, uzbek, is_favourite as isFavourite " +
                "from dictionary where isFavourite = 1"
    )
    fun getAllUzbekFavoriteWords(): Cursor

    @Query("select * from dictionary where id = :id")
    fun getWordById(id: Int): DictionaryEntity

    @Query("update dictionary set is_favourite = 1 where id = :id")
    fun addToFavourite(id: Int)

    @Query("update dictionary set is_favourite = 0 where id = :id")
    fun deleteFromFavourite(id: Int)

    @Query(
        "select id, english , type,transcript, uzbek, is_favourite as isFavourite " +
                "from dictionary where dictionary.english like :query"
    )
    fun getAllEnglishWordsByQuery(query: String): Cursor

    @Query(
        "select id, english , type,transcript, uzbek , is_favourite as isFavourite " +
                "from dictionary where dictionary.uzbek like :query"
    )
    fun getAllUzbekWordsByQuery(query: String): Cursor

}