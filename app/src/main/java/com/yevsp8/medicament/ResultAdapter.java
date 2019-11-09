package com.yevsp8.medicament;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private List<String> dataset;
    private boolean isClickEnabled;
    private Context context;
    private Utils utils;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView listItemView;

        public ViewHolder(TextView v) {
            super(v);
            listItemView = v;
        }
    }

    public ResultAdapter(List<String> myDataset, boolean isClickEnabled, Context context) {
        dataset = myDataset;
        this.isClickEnabled = isClickEnabled;
        this.context = context;
        utils = new Utils(context);
    }

    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(context)
                .inflate(R.layout.listitem, parent, false);

        final ViewHolder vh = new ViewHolder(v);
        if (isClickEnabled) {
            vh.listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String clickedName = dataset.get(vh.getAdapterPosition());
                    String idForName = utils.searchMedicamentIdByName(clickedName);
                    if (idForName != null && !idForName.isEmpty()) {
                        openWebPage(idForName);
                    }
                }
            });
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.listItemView.setText(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public void openWebPage(String extraUrl) {
        String url = "https://ogyei.gov.hu/gyogyszeradatbazis&action=show_details&item=";
        url += extraUrl;
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

}
