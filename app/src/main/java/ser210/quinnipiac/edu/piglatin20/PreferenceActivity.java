package ser210.quinnipiac.edu.piglatin20;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        // set background color from preferences
        // slight delay bc activity is not recreated everytime preferences are changed
        FrameLayout f = (FrameLayout) findViewById(R.id.frameLayout);
        setBG(f);

        // toolbar allows user to move back to main activity
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // put PrefFragment in fragment container
        PrefFragment prefFragment = new PrefFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, prefFragment);
        fragmentTransaction.commit();
    }

    public void setBG(View view) {
        // preference parsing put in its own method to declutter onCreate
        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
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
        // red if I assign View istead of TextView, but works for Buttons and EditTexts as well
        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
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
