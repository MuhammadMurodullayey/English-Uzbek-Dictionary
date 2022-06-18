package uz.gita.dictionaryuzen.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.dictionaryuzen.data.local.AppDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @[Provides Singleton]
    fun getDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(context, AppDataBase::class.java, "Sample.db")
        .createFromAsset("dictionary.db")
        .build()
}