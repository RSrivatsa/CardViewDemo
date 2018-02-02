package info.androidhive.cardview;

/**
 * Created by Lincoln on 18/05/16.
 */
public class Album {
    private String name;
    private int price;
    private int thumbnail;

    public Album() {
    }

    public Album(String name, int numOfSongs, int thumbnail) {
        this.name = name;
        this.price = numOfSongs;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return price;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.price = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
