package gr.android.softposecr.domain.repoInterfaces;

import androidx.lifecycle.LiveData;

import java.util.List;

import gr.android.softposecr.data.model.home.ItemRemote;
import gr.android.softposecr.data.database.ItemEntity;
import io.reactivex.rxjava3.core.Observable;

public interface ItemRepository {
    LiveData<List<ItemEntity>> getAllItems();
}

