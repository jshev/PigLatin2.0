package ser210.quinnipiac.edu.piglatin20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PigLatinActivity extends AppCompatActivity {

    public String pigs;
    ShareActionProvider provider;
    private FavoritesDataSource datasource;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Favorites fav = null;
        switch (id) {
            case R.id.viewFav:
                // sends user to listview of all favorites saved in database
                Intent favIntent = new Intent(PigLatinActivity.this, FavDisplayActivity.class);
                startActivity(favIntent);
                break;
            case R.id.addFav:
                // adds user's translation to favorites database
                fav = datasource.createFavorite(pigs);
                break;
            case R.id.share:
                // gives user option to share via bluetooth for messages
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Want to learn how to speak Pig Latin?");
                provider.setShareIntent(shareIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate options for toolbar
        getMenuInflater().inflate(R.menu.menu_pigs,menu);

        // set up share providers for share option
        MenuItem shareItem =  menu.findItem(R.id.share);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pig_latin);

        // if user rotates the phone, they don't lose their translation
        if (savedInstanceState != null) {
            pigs = savedInstanceState.getString("pigs");
        }

        // set font from preferences
        pigs = (String) getIntent().getExtras().get("pigLatin");
        TextView view = (TextView) findViewById(R.id.pigText);
        view.setText(pigs);
        setFont(view);

        // set background color from preferences
        RelativeLayout r = (RelativeLayout) findViewById(R.id.relativeLayout);
        setBG(r);

        // sets up toolbar for this activity
        // toolbar also allows user to move back to main activity... replaces cute ImageButton
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // open in case user wants to save translation to favorites
        datasource = new FavoritesDataSource(this);
        datasource.open();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // save what the user is translated even if they rotate the phone
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("pigs", pigs);
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

    public void setFont(TextView view) {
        // preference parsing put in its own method to declutter onCreate
        SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(this);
        String font = (mSharedPreference.getString("FONT_PREF", "1"));
        Typeface type;

        if ((font).equals("1")) {
            type = Typeface.createFromAsset(getAssets(), "Roboto.ttf");
            view.setTypeface(type);
        } else if ((font).equals("2")) {
            type = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");
            view.setTypeface(type);
        } else if ((font).equals("3")) {
            type = Typeface.createFromAsset(getAssets(), "Amatic.ttf");
            view.setTypeface(type);
        } else if ((font).equals("4")) {
            type = Typeface.createFromAsset(getAssets(), "Tusj.ttf");
            view.setTypeface(type);
        } else if ((font).equals("5")) {
            type = Typeface.createFromAsset(getAssets(), "Windsong.ttf");
            view.setTypeface(type);
        }
    }
}
