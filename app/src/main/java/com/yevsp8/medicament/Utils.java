package com.yevsp8.medicament;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Utils {

    private static HashMap<String, String> jsonObjects = new HashMap<>();
    private Context context;
    private static final String AssetFileName = "ogyei_gyogyszerek.json";

    public Utils(Context context) {
        this.context = context;
        if (jsonObjects.size() == 0) {
            loadJSONFromAsset();
        }
    }

    public String searchMedicamentIdByName(String name) {
        return jsonObjects.get(name);
    }

    private void loadJSONFromAsset() {

        String json;
        try {
            InputStream is = context.getAssets().open(AssetFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("medicaments");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String name = jo_inside.getString("name");
                String id = jo_inside.getString("id");

                jsonObjects.put(name, id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
