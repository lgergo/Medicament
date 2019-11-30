package com.yevsp8.medicament;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yevsp8.medicament.data.Repository;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private List<String> dataset;
    private boolean isClickEnabled;
    private Context context;
    private Repository repo;
    private String type;
    private IOManager ioManager;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView listItemView;

        ViewHolder(TextView v) {
            super(v);
            listItemView = v;
        }
    }

    ResultAdapter(List<String> myDataset, boolean isClickEnabled, String adapterType, Context context) {
        dataset = myDataset;
        this.isClickEnabled = isClickEnabled;
        this.context = context;
        repo=Repository.getInstance(context);
        this.type = adapterType;

        ioManager=new IOManager(context);
    }

    @NonNull
    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(context)
                .inflate(R.layout.listitem, parent, false);

        final ViewHolder vh = new ViewHolder(v);
        if (isClickEnabled) {
            vh.listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (type) {
                        case Constants.AdapterType_ImageResult:
                            String clickedName = dataset.get(vh.getAdapterPosition()).toLowerCase();
                            Intent intent = new Intent(context, ResultActivity.class);
                            intent.putExtra(Constants.Intent_SearchedText, clickedName);
                            context.startActivity(intent);
                            break;
                        case Constants.AdapterType_SearchResult:
                            String clickedN = dataset.get(vh.getAdapterPosition()).toLowerCase();
                            String value = repo.getMedicamentOgyiKeyByName(clickedN);
                            ioManager.openWebPage(value);
                            break;
                    }
                }
            });
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = dataset.get(position);
        holder.listItemView.setText(text);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
