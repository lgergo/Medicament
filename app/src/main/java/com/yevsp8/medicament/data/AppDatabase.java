package com.yevsp8.medicament.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.yevsp8.medicament.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Database(entities = {MedicamentEntity.class, SubstanceEntity.class}, version = 1, exportSchema = false)
@TypeConverters(StringTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MedicamentDao medicamentDao();
    public abstract SubstanceDao substanceDao();
    private static AppDatabase dbInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (dbInstance == null) {
            context.deleteDatabase(Constants.DatabaseName);
            dbInstance = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.DatabaseName)
                    .allowMainThreadQueries()
                    .build();
            dbInstance.populateDatabaseFromSubstituteFile(context, Constants.Asset_SubstitutesFileName, Constants.RootObject_Substances);
            dbInstance.populateDatabaseFromMedicamentsFile(context, Constants.Asset_MedicamentsFileName, Constants.RootObject_Medicaments);

        }
        return dbInstance;
    }

    private void populateDatabaseFromSubstituteFile(Context context, String fileName, String jsonRootObject)
    {
        if(substanceDao().substancesCount()>0)
            return;
        String json=readStreamToString(context, fileName);

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray(jsonRootObject);

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String id = jo_inside.getString(Constants.SubstancesAttribute_Id);
                String name = jo_inside.getString(Constants.SubstancesAttribute_Name);

                JSONArray substituteArray = jo_inside.getJSONArray(Constants.SubstancesAttribute_Replaceable);
                String[] nameArray=new String[substituteArray.length()];
                for (int j=0;j<substituteArray.length();j++ )
                {
                    JSONObject medicament=substituteArray.getJSONObject(j);
                    String name2=medicament.getString(Constants.Substances_InnerElementAttribute_Name);
                    nameArray[j]=name2;
                }
                if(tryParseInt(id)) {
                    substanceDao().insertSubstance(new SubstanceEntity(Integer.parseInt(id), name, Arrays.asList(nameArray)));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateDatabaseFromMedicamentsFile(Context context, String fileName, String jsonRootObject)
    {
        if(medicamentDao().medicamentsCount()>0)
            return;
        String json=readStreamToString(context, fileName);

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray(jsonRootObject);

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String name = jo_inside.getString(Constants.MedicamentsAttribute_Name);
                String key = jo_inside.getString(Constants.MedicamentsAttribute_OgyiKey);

                medicamentDao().insertMedicament(new MedicamentEntity(name,key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readStreamToString(Context context, String fileName)
    {
        String json="";
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
