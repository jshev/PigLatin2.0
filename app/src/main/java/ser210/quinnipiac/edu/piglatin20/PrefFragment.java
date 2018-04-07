package ser210.quinnipiac.edu.piglatin20;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrefFragment extends PreferenceFragment {

    public PrefFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // instead of inflating a fragment layout, it takes the preferences screen created in the
        // preference xml
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
