package com.yavin.mybustickets.di

import android.app.Application
import android.content.Context
import com.yavin.mybustickets.TicketSoldRoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindAppContext(app: Application): Context

    companion object {
        @Provides
        fun providesDataBase(application: Application): TicketSoldRoomDatabase {
            return TicketSoldRoomDatabase.getDatabase(application)
        }

        @Provides
        fun provideDispatcher(): CoroutineDispatcher {
            return Dispatchers.IO
        }
    }
}
