package gr.android.cineflix.domain.models;

public class MovieUi {
    private int id;

    private String title;

    private String overview;

    private String posterPath;

    private String backdropPath;

    private double voteAverage;

    // Additional fields for favorite handling
    private boolean favorite;

    private String releaseDate;

    public MovieUi(int id, String title, String overview, String posterPath, double voteAverage, boolean isFavorite, String backdropPath, String releaseDate) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.favorite = isFavorite;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;  // Remove formatting since it's already formatted
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;  // Remove formatting since it's already formatted
    }
}
