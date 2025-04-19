package com.ayberk.ordercoffee

import android.app.Application
import androidx.room.Room
import com.ayberk.ordercoffee.room.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)  // Uygulama genelinde geçerli olması için SingletonComponent
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "order_coffee_database"
        ).build()
    }

    @Provides
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()  // Veritabanı nesnesi üzerinden DAO'yu sağlıyoruz
    }
}
