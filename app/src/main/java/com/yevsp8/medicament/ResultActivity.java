package com.yevsp8.medicament;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    protected RecyclerView.Adapter adapter;
    RecyclerView recyclerView_searchResult;
    HashMap<String, String> ids;
    List<String> names;
    boolean switch_SearchInSubstances = false;
    TextView searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent i = getIntent();
        ids = (HashMap<String, String>) i.getSerializableExtra(Constants.Intent_ResultList);
        String searchedText = i.getStringExtra(Constants.Intent_SearchedText);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        recyclerView_searchResult = findViewById(R.id.recycleView_searchResults);
        recyclerView_searchResult.setHasFixedSize(true);
        recyclerView_searchResult.setLayoutManager(mLayoutManager);
        names = new ArrayList<>(ids.keySet());
        adapter = new ResultAdapter(names, true, Constants.AdapterType_MedicamentSearchResult, this);
        recyclerView_searchResult.setAdapter(adapter);

        searchText = findViewById(R.id.textView_search_text);
        searchText.setText(String.format(getResources().getString(R.string.search_text), searchedText));

        Switch sw = findViewById(R.id.switch_substance);
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipClicked();
            }
        });
    }

    void chipClicked() {
        //search in different file
        switch_SearchInSubstances = !switch_SearchInSubstances;
        if (switch_SearchInSubstances) {
            adapter = new ResultAdapter(names, true, Constants.AdapterType_SubstanceSearchResult, this);
            recyclerView_searchResult.setAdapter(adapter);
            //recyclerView_searchResult.notify();
        } else {
            adapter = new ResultAdapter(names, true, Constants.AdapterType_MedicamentSearchResult, this);
            recyclerView_searchResult.setAdapter(adapter);
            //recyclerView_searchResult.notify();
        }
    }
}
