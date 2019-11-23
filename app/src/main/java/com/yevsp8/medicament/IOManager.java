package com.yevsp8.medicament;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

class IOManager {

    private static HashMap<String, String> medicaments = new HashMap<>();
    //private static HashMap<String,String> substances=new HashMap<>();
    private Context context;

    IOManager(Context context) {
        this.context = context;
        if (medicaments.size() == 0) {
            loadJsonToMap(Constants.Asset_MedicamentsFileName, "medicaments", medicaments);
        }
//        if(substances.size()==0) {
//            loadJsonToMap(Constants.Asset_SubstancesFileName,"substances",substances);
//        }
    }

    String getValueByKey(String key) {
        return medicaments.get(key);
    }

    HashMap<String, String> searchMedicamentIdByName(String name) {
        HashMap<String, String> results = new HashMap<>();
        for (Map.Entry<String, String> entries : medicaments.entrySet()) {
            if (entries.getKey().contains(name)) {
                results.put(entries.getKey(), entries.getValue());
            }
        }
        return results;
    }

    private void loadJsonToMap(String fileName, String jsonRootObject, HashMap<String, String> map) {

        String json;
        try {
            InputStream is = context.getAssets().open(fileName);
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
            JSONArray m_jArry = obj.getJSONArray(jsonRootObject);

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String name = jo_inside.getString("name");
                String id = jo_inside.getString("id");

                map.put(name, id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
