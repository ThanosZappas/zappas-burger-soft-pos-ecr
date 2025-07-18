package gr.android.cineflix.data.model.details.credits;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieCreditsResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("cast")
    private List<CastMember> cast;

    public int getId() {
        return id;
    }

    public List<CastMember> getCast() {
        return cast;
    }
}
