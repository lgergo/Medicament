package com.yevsp8.medicament;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yevsp8.medicament.data.MedicamentEntity;
import com.yevsp8.medicament.data.Repository;
import com.yevsp8.medicament.data.SubstanceEntity;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    protected RecyclerView.Adapter adapter;
    RecyclerView recyclerView_searchResult;
    boolean switch_SearchInSubstances = false;
    TextView searchText;
    Repository repo;
    String intentSearchText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        repo=Repository.getInstance(this);

        intentSearchText = getIntent().getStringExtra(Constants.Intent_SearchedText);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView_searchResult = findViewById(R.id.recycleView_searchResults);
        recyclerView_searchResult.setHasFixedSize(true);
        recyclerView_searchResult.setLayoutManager(mLayoutManager);

        //TODO klikkelt sor alapján megtipplni hogy gyógyszer vagy hatóanag
        setMedicamentResults();

        searchText = findViewById(R.id.textView_search_text);
        searchText.setText(String.format(getResources().getString(R.string.search_text), intentSearchText));

        Switch sw = findViewById(R.id.switch_substance);
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipClicked();
            }
        });
    }

    void chipClicked() {
        switch_SearchInSubstances = !switch_SearchInSubstances;
        if (switch_SearchInSubstances) {
            setSubstanceResults();
        } else {
            setMedicamentResults();
        }
    }

    //TODO menteni hogy ne kelljen minden váltásnál újra lekérdezni
    void setMedicamentResults()
    {
        List<MedicamentEntity> med=repo.getMedicamentListBySimilarName(intentSearchText);
        List<String> nameList=new ArrayList<>();
        for (MedicamentEntity m:med) {
            nameList.add(m.getName());
        }
        adapter=new ResultAdapter(nameList,true,Constants.AdapterType_SearchResult,this);
        recyclerView_searchResult.setAdapter(adapter);
    }

    //TODO adapter létrehozások nélkül
    void setSubstanceResults()
    {
        SubstanceEntity substance=repo.getSubstanceByName(intentSearchText);
        if(substance!=null && substance.getMedicamentList()!=null) {
            adapter = new ResultAdapter(substance.getMedicamentList(), true, Constants.AdapterType_SearchResult, this);
            recyclerView_searchResult.setAdapter(adapter);
        }
        else
        {
            adapter = new ResultAdapter(new ArrayList<String>(), true, Constants.AdapterType_SearchResult, this);
            recyclerView_searchResult.setAdapter(adapter);
        }
    }
}
