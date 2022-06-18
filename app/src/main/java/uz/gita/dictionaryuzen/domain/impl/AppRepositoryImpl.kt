package uz.gita.dictionaryuzen.domain.impl

import android.database.Cursor
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.dictionaryuzen.data.local.AppDataBase
import uz.gita.dictionaryuzen.domain.AppRepository
import uz.gita.dictionaryuzen.domain.Near
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    dataBase: AppDataBase
) : AppRepository {
    private val table = dataBase.dao()
    override fun getAllEnglishWord() = flow<Cursor> {
        emit(table.getAllEnglishWords())
    }.flowOn(Dispatchers.IO)

    override fun getAllUzbekWord() = flow<Cursor> {
        emit(table.getAllUzbekWords())
    }.flowOn(Dispatchers.IO)

    override fun getEnglishWordByQuery(query: String) = flow<Cursor> {
        Log.d("TTT","$query")
        emit(table.getAllEnglishWordsByQuery(query))
    }.flowOn(Dispatchers.IO)

    override fun getUzbekWordByQuery(query: String) = flow<Cursor> {
        Log.d("TTT","$query")
        emit(table.getAllUzbekWordsByQuery(query))
    }.flowOn(Dispatchers.IO)

    override fun addToFavourite(id: Int) = flow<Unit> {
        Log.d("YYY", "$id ")
        table.addToFavourite(id)
        emit(Unit)
    }.flowOn(Dispatchers.IO)

    override fun deleteFromFavourite(id: Int) = flow<Unit> {
        table.deleteFromFavourite(id)
        emit(Unit)
    }.flowOn(Dispatchers.IO)

    override fun getAllEnglishFavoriteWords() = flow<Cursor> {
        emit(table.getAllEnglishFavoriteWords())
    }.flowOn(Dispatchers.IO)

    override fun getAllUzbekFavoriteWords() = flow<Cursor> {
        emit(table.getAllUzbekFavoriteWords())
    }.flowOn(Dispatchers.IO)

    override fun getNearEngWords(word: String) =flow<Cursor> {
        val dataes = table.getAllEnglishWords()
        var words = ArrayList<String>()
            dataes.moveToPosition(0)
        while (!dataes.isLast){
            words.add(dataes.getString(1))
            Log.d("AAA","${dataes.getString(1)}")
            dataes.moveToNext()
        }
        Log.d("AAA","${words.size}")
        words = Near.getMostNearest(word,words)
        emit(table.getNearEnglishWords(words[1],words[2],words[3],words[4]))

    }.flowOn(Dispatchers.IO)

    override fun getNearUzWords(word: String) =flow<Cursor> {
        val dataes = table.getAllUzbekWords()
        var words = ArrayList<String>()
        dataes.moveToPosition(0)
        while (!dataes.isLast){
            words.add(dataes.getString(4))
            dataes.moveToNext()
        }
        words = Near.getMostNearest(word,words)
        emit(table.getNearUzbekWords(words[1],words[2],words[3],words[4]))
    }.flowOn(Dispatchers.IO)

}

/*
TableInfo{name='dictionary', columns={transcript=Column{name='transcript', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, english=Column{name='english', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, uzbek=Column{name='uzbek', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, id=Column{name='id', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=1, defaultValue='null'}, is_favourite=Column{name='is_favourite', type='INTEGER', affinity='3', notNull=false, primaryKeyPosition=0, defaultValue='null'}, type=Column{name='type', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, countable=Column{name='countable', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}}, foreignKeys=[], indices=[]}
     Found:
    TableInfo{name='dictionary', columns={transcript=Column{name='transcript', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, english=Column{name='english', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, uzbek=Column{name='uzbek', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, id=Column{name='id', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=1, defaultValue='null'}, is_favourite=Column{name='is_favourite', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, type=Column{name='type', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, countable=Column{name='countable', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}}, foreignKeys=[], indices=[]}
* */