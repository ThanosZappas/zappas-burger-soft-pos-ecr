package gr.android.softposecr.data.model.home;

import com.google.gson.annotations.SerializedName;

import gr.android.softposecr.common.Helper;

public class ItemRemote {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private int posterPath;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("release_date")
    private String releaseDate;


    public ItemRemote(int id, String title, String overview, int posterPath, double voteAverage, String backdropPath,
                       String releaseDate){
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.backdropPath = backdropPath;
        this.releaseDate =  Helper.formatDate(releaseDate);  // Keep formatting here as this is from API
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(int posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;  // Remove formatting here as it might be from DB
    }

}
