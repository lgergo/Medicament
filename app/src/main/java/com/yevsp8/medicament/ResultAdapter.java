package com.yevsp8.medicament;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private List<String> dataset;
    private boolean isClickEnabled;
    private Context context;
    private IOManager ioManager;
    private String type;

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
        ioManager = new IOManager(context);
        this.type = adapterType;
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
                            HashMap<String, String> idResults = ioManager.searchMedicamentIdByName(clickedName);
                            Intent i = new Intent(context, ResultActivity.class);
                            i.putExtra(Constants.Intent_ResultList, idResults);
                            i.putExtra(Constants.Intent_SearchedText, clickedName);
                            context.startActivity(i);
                            break;
                        case Constants.AdapterType_MedicamentSearchResult:
                            String clickedKey = dataset.get(vh.getAdapterPosition()).toLowerCase();
                            String value = ioManager.getValueByKey(clickedKey);
                            openWebPage(value);
                            break;
                        case Constants.AdapterType_SubstanceSearchResult:
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

    private void openWebPage(String extraUrl) {
        String url = "https://ogyei.gov.hu/gyogyszeradatbazis&action=show_details&item=";
        url += extraUrl;
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
