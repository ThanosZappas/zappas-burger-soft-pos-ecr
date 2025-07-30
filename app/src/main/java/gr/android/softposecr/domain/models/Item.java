package gr.android.softposecr.domain.models;

public class Item {
    private String title;
    private float price;
    private String overview;
    private int posterPath;

    public Item(String title, float price, String overview, int posterPath) {
        this.title = title;
        this.price = price;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
}
