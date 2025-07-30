package gr.android.softposecr.data.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import gr.android.softposecr.common.Helper;

@Entity(tableName = "item_db")
public class ItemEntity {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "poster_path")
    private int posterPath;


    @ColumnInfo(name = "vote_average")
    private double voteAverage;

    @ColumnInfo(name = "backdropPath")
    private String backdropPath;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "price")
    private float price;

    // Constructor
    public ItemEntity(int id, String title, String overview, int posterPath, double voteAverage, String backdropPath, String releaseDate, float price) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.backdropPath = backdropPath;
        this.releaseDate = Helper.formatDate(releaseDate);
        this.price = price;
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
        return Helper.formatDate(releaseDate);
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
}
