package ser210.quinnipiac.edu.piglatin20;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PigLatinFragment extends Fragment {
    public String pigs;

    public PigLatinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_pig_latin, container, false);

        pigs = (String) (getActivity().getIntent().getExtras().get("pigLatin"));
        TextView view = (TextView) layout.findViewById(R.id.pigText);
        view.setText(pigs);

        return layout;
    }

    public void setFont(TextView view) {
        SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String font = (mSharedPreference.getString("FONT_PREF", "1"));
        Typeface type;

        if ((font).equals("1")) {
            type = Typeface.createFromAsset(getActivity().getAssets(), "Roboto.ttf");
            view.setTypeface(type);
        } else if ((font).equals("2")) {
            type = Typeface.createFromAsset(getActivity().getAssets(), "Pacifico.ttf");
            view.setTypeface(type);
        } else if ((font).equals("3")) {
            type = Typeface.createFromAsset(getActivity().getAssets(), "Amatic.ttf");
            view.setTypeface(type);
        } else if ((font).equals("4")) {
            type = Typeface.createFromAsset(getActivity().getAssets(), "Tusj.ttf");
            view.setTypeface(type);
        } else if ((font).equals("5")) {
            type = Typeface.createFromAsset(getActivity().getAssets(), "Windsong.ttf");
            view.setTypeface(type);
        }
    }

}
