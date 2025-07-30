package gr.android.softposecr.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item_db")
    LiveData<List<ItemEntity>> getAll();

    @Query("SELECT * FROM item_db WHERE id = :id LIMIT 1")
    ItemEntity getItemById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ItemEntity movie);

    @Delete
    void delete(ItemEntity movie);
}

