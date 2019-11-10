package com.yevsp8.medicament;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Utils {

    private static HashMap<String, String> jsonObjects = new HashMap<>();
    private Context context;
    private static final String AssetFileName = "ogyei_gyogyszerek.json";

    Utils(Context context) {
        this.context = context;
        if (jsonObjects.size() == 0) {
            loadJSONFromAsset();
        }
    }

    ArrayList<String> searchMedicamentIdByName(String name) {
        ArrayList<String> results = new ArrayList<>();
        for (Map.Entry<String, String> entries : jsonObjects.entrySet()) {
            if (entries.getKey().contains(name)) {
                results.add(entries.getValue());
            }
        }
        return results;
        //return jsonObjects.get(name.toLowerCase());
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
