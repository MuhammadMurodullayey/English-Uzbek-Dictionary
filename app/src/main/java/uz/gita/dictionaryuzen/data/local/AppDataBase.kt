package uz.gita.dictionaryuzen.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.dictionaryuzen.data.local.dao.DatabaseDao
import uz.gita.dictionaryuzen.data.local.entity.DictionaryEntity

@Database(entities = [DictionaryEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dao(): DatabaseDao
}