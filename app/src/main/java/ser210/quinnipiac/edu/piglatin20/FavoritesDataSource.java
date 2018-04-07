package ser210.quinnipiac.edu.piglatin20;

/**
 * Created by juliannashevchenko on 4/6/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoritesDataSource {
    // simplifies methods used in activities
    // so much better than what the book did

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_FAVORITE };

    public FavoritesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        // need WritableDatabase instead of ReadableDatabase bc we want to add favorites occasionally
        database = dbHelper.getWritableDatabase();
    }
    public  SQLiteDatabase getDatabase() {
        return database;
    }

    public void close() {
        // simplifies closing database
        dbHelper.close();
    }

    public Favorites createFavorite(String fav) {
        // later used to add user's favorite pig latin translations to database
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_FAVORITE, fav);

        long insertId = database.insert(MySQLiteHelper.TABLE_FAVORITES, null,
                values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FAVORITES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();

        Favorites newFav = cursorToFavorite(cursor);
        cursor.close();

        return newFav;
    }

    public void deleteFavorite(Favorites fav) {
        // used to delete the oldest favorite
        // but how does one getAdapter after setAdapter?
        long id = fav.getId();
        database.delete(MySQLiteHelper.TABLE_FAVORITES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Favorites> getAllFavorites() {
        // used to display all favorites by going through database with a cursor
        List<Favorites> favorites = new ArrayList<Favorites>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FAVORITES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Favorites fav = cursorToFavorite(cursor);
            favorites.add(fav);
            cursor.moveToNext();
        }

        cursor.close();
        return favorites;
    }

    private Favorites cursorToFavorite(Cursor cursor) {
        // used in the getAllFavorites method
        Favorites fav = new Favorites();
        fav.setId(cursor.getLong(0));
        fav.setFavorite(cursor.getString(1));
        return fav;
    }

}
