package gr.android.softposecr.di;

import android.app.Application;

import androidx.room.Room;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import gr.android.softposecr.data.database.ItemDao;
import gr.android.softposecr.data.database.ItemDatabase;
import gr.android.softposecr.data.repo.ItemRepositoryImpl;
import gr.android.softposecr.domain.repoInterfaces.ItemRepository;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {

    @Provides
    public static ItemRepository provideItemRepository(ItemDao movieDao) {
        return new ItemRepositoryImpl(movieDao);
    }

}

