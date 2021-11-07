package com.yavin.mybustickets.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.yavin.mybustickets.TicketSoldRoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

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

        @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
        @Provides
        fun provideYourDatabase(
            @ApplicationContext app: Context
        ) = Room.databaseBuilder(
            app,
            TicketSoldRoomDatabase::class.java,
            "TicketSold_database"
        ).build()

        @Singleton
        @Provides
        fun provideYourDao(db: TicketSoldRoomDatabase) = db.ticketSoldDao()
    }

}
