package gr.android.softposecr.domain.models;

public class ItemUi {
    private int id;

    private String title;

    private String overview;

    private int posterPath;

    private String backdropPath;

    private double voteAverage;

    private String releaseDate;
    
    private float price;

    public ItemUi(int id, String title, String overview, int posterPath, double voteAverage, String backdropPath, String releaseDate, float price) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
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
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;  // Remove formatting since it's already formatted
    }

    public float getPrice() {
        return this.price;
    }
    
    public void setPrice(float price) {
        this.price = price;
    }
}
