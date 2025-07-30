package gr.android.softposecr.data.model.home;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ItemApiResponse {
    @SerializedName("results")
    private List<ItemRemote> results;

    public List<ItemRemote> getResults() {
        return results;
    }

}

