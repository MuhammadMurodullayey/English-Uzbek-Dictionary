package uz.gita.dictionaryuzen.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dictionaryuzen.domain.AppRepository
import uz.gita.dictionaryuzen.domain.impl.AppRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun getAppRepository(impl: AppRepositoryImpl)  : AppRepository
}