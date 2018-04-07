package ser210.quinnipiac.edu.piglatin20;

/**
 * Created by juliannashevchenko on 4/6/18.
 */

public class Favorites {
    // used to simplify the DataCourse
    private long id;
    private String fav;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFavorite() {
        return fav;
    }

    public void setFavorite(String fav) {
        this.fav = fav;
    }

    @Override
    public String toString() {
        return fav;
    }
}
