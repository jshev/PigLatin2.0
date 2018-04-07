package ser210.quinnipiac.edu.piglatin20;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

public class FavDisplayActivity extends AppCompatActivity {
    private FavoritesDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_display);
        ListView myList = (ListView) findViewById(R.id.my_list);

        // set background color from preferences
        RelativeLayout r = (RelativeLayout) findViewById(R.id.relativeLayout);
        setBG(r);

        // open datasource, get data, and put it in listview
        // unfortunately, cannot set font here without having to make my own adapter...
        datasource = new FavoritesDataSource(this);
        datasource.open();
        List<Favorites> values = datasource.getAllFavorites();
        ArrayAdapter<Favorites> adapter = new ArrayAdapter<Favorites>(this,
                android.R.layout.simple_list_item_1, values);
        myList.setAdapter(adapter);

        // toolbar allows user to move back to main activity
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        // ensure open after closing
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        // always close when not in use
        datasource.close();
        super.onPause();
    }

    public void setBG(View view) {
        // preference parsing put in its own method to declutter onCreate
        SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(this);
        String color = (mSharedPreference.getString("COLOR_PREF", "1"));

        if ((color).equals("1")) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultBreak));
        } else if ((color).equals("2")) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        } else if ((color).equals("3")) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
        } else if ((color).equals("4")) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        } else if ((color).equals("5")) {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
        }

    }
}
