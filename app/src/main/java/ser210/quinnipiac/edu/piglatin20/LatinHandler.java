package ser210.quinnipiac.edu.piglatin20;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by juliannashevchenko on 2/26/18.
 */

public class LatinHandler {

    public LatinHandler() {
    }

    public String getTranslation(String PLJsonStr) throws JSONException {
        // reads json and returns only the string that we want
        JSONObject PLTransJSONObj = new JSONObject(PLJsonStr);
        return PLTransJSONObj.getJSONObject("contents").getString("translated");
    }

}
