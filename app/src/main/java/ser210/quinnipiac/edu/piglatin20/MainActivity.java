package ser210.quinnipiac.edu.piglatin20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    final static String url1 = "https://piglatin.p.mashape.com/piglatin.json?text=";
    final static String url2 = "&mashape-key=Ua35jWwKEMmsh16tGNbyeJ9zdat3p1mbW60jsn8Q7tn8rJw8po";
    final LatinHandler latinhandler = new LatinHandler();
    public String english;
    ShareActionProvider provider;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.viewFav:
                // sends user to listview of all favorites saved in database
                Intent favIntent = new Intent(MainActivity.this, FavDisplayActivity.class);
                startActivity(favIntent);
                break;
            case R.id.action_settings:
                // sends user to where they set set preferred font and bg color
                Intent prefIntent = new Intent(MainActivity.this, PreferenceActivity.class);
                startActivity(prefIntent);
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
        getMenuInflater().inflate(R.menu.menu_main,menu);

        // set up share providers for share option
        MenuItem shareItem =  menu.findItem(R.id.share);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set background color from preferences
        RelativeLayout r = (RelativeLayout) findViewById(R.id.relativeLayout);
        setBG(r);

        // set font from preferences
        TextView dir = (TextView) findViewById(R.id.directions);
        EditText eng = (EditText) findViewById(R.id.englishText);
        Button butt = (Button) findViewById(R.id.translateButton);
        setFont(dir);
        setFont(eng);
        setFont(butt);

        // if user was typing and rotates the phone, put back what they were typing
        if (savedInstanceState != null) {
            english = savedInstanceState.getString("english");
        }

        // sets up toolbar for this activity
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    public void onClick (View view) {
        // gets String from the EditText
        EditText text = (EditText) findViewById(R.id.englishText);
        english = text.getText().toString();

        // reformats String so it can be put in HTTP form
        // replace all spaces with plus signs
        // start AsyncTask
        String englishPlus = english.replaceAll("\\s+","+");
        new FetchPigLatin().execute(englishPlus);
    }

    private class FetchPigLatin extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            // gets translation from the Pig Latin REST API
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String latin = null;
            try {
                URL url = new URL( url1 + strings[0] + url2);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                if (in == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(in));
                String latinJsonString = getJsonStringFromBuffer(reader);

                latin = latinhandler.getTranslation(latinJsonString);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            } finally {

                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch(IOException e) {
                        return null;
                    }
                }
            }
            return latin;
        }

        @Override
        protected void onPostExecute(String result) {
            // pushes result (pig latin translate) to the next activity after doInBackground is done
            super.onPostExecute(result);
            if (result != null) {
                Log.d(LOG_TAG, result);
            }
            Intent intent = new Intent(MainActivity.this, PigLatinActivity.class);
            intent.putExtra("pigLatin", result);
            startActivity(intent);
        }
    }

    private String getJsonStringFromBuffer(BufferedReader br) throws Exception {
        // gets json string from buffer reader
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            buffer.append(line + '\n');
        }
        if (buffer.length() == 0) {
            return null;
        } else {
            return buffer.toString();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // save what the user is typing even if they rotate the phone
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("english", english);
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
        // red if I assign View istead of TextView, but works for Buttons and EditTexts as well
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
