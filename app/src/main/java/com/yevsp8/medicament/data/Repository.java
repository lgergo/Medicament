package com.yevsp8.medicament.data;

import android.content.Context;

import java.util.List;

public class Repository {

    private static MedicamentDao medicamentDao;
    private static SubstanceDao substanceDao;
    private static Repository repoInstance;

    public static Repository getInstance(Context context)
    {
        if(repoInstance==null)
        {
            medicamentDao=AppDatabase.getInstance(context).medicamentDao();
            substanceDao=AppDatabase.getInstance(context).substanceDao();
            repoInstance=new Repository();
        }
        return  repoInstance;
    }
    private Repository(){}


    public List<MedicamentEntity> getMedicamentListBySimilarName(String name)
    {
        String queryParam="%"+name+"%";
        return medicamentDao.getMedicamentListBySimilarName(queryParam);
    }
    public int getMedicamentsCount()
    {
        return medicamentDao.medicamentsCount();
    }
    public String getMedicamentOgyiKeyByName(String name)
    {
        MedicamentEntity med=medicamentDao.getMedicamentByName(name);
        if(med!=null)
            return med.getOgyi_key();
        else
            return "";
    }

    public int getSubstancesCount()
    {
        return substanceDao.substancesCount();
    }
    public SubstanceEntity getSubstanceByName(String name)
    {
        String queryParam="%"+name+"%";
        return substanceDao.getSubstanceByName(queryParam);
    }
}
