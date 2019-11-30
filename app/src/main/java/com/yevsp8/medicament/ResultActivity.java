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

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView_searchResult;
    private boolean switch_SearchInSubstances = false;
    private TextView searchText;
    private TextView resultTitle;
    private Repository repo;
    private String intentSearchText="";

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

        searchText = findViewById(R.id.textView_search_text);
        searchText.setText(String.format(getResources().getString(R.string.search_text), intentSearchText));
        resultTitle = findViewById(R.id.textView_result_title);

        setMedicamentResults();

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

    void setMedicamentResults()
    {
        List<MedicamentEntity> med=repo.getMedicamentListBySimilarName(intentSearchText);
        List<String> nameList=new ArrayList<>();
        for (MedicamentEntity m:med) {
            nameList.add(m.getName());
        }
        adapter=new ResultAdapter(nameList,true,Constants.AdapterType_SearchResult,this);
        recyclerView_searchResult.setAdapter(adapter);
        resultTitle.setText(getResources().getText(R.string.text_titleForMedicamentList));
    }

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
        resultTitle.setText(getResources().getText(R.string.text_titleForSubstanceList));
    }
}
