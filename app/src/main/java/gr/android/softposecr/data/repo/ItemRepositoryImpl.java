package gr.android.softposecr.data.repo;

import androidx.lifecycle.LiveData;

import java.util.List;

import gr.android.softposecr.data.model.home.ItemRemote;
import gr.android.softposecr.data.model.home.ItemApiResponse;
import gr.android.softposecr.data.database.ItemDao;
import gr.android.softposecr.data.database.ItemEntity;
import gr.android.softposecr.domain.repoInterfaces.ItemRepository;
import io.reactivex.rxjava3.core.Observable;

public class ItemRepositoryImpl implements ItemRepository {
    private ItemDao movieDao;


    public ItemRepositoryImpl(ItemDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public LiveData<List<ItemEntity>> getAllItems() {
        return movieDao.getAll();
    }
}
