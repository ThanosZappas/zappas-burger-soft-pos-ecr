package gr.android.softposecr.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(
        entities = {ItemEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class ItemDatabase extends RoomDatabase {
    public abstract ItemDao movieDao();
}


