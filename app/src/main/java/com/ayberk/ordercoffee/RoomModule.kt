package com.ayberk.ordercoffee

import android.app.Application
import androidx.room.Room
import com.ayberk.ordercoffee.data.local.dao.BasketDao
import com.ayberk.ordercoffee.room.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "order_coffee_database"
        )
            // Veritabanı şeması değişirse, eski veritabanını siler ve yeni veritabanını oluşturur
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBasketDao(database: AppDatabase): BasketDao {
        return database.basketDao()
    }

    @Provides
    @Singleton
    fun provideBasketRepository(basketDao: BasketDao): BasketRepository {
        return BasketRepository(basketDao)
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()
    }
}
