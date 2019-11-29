package com.yevsp8.medicament;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

class IOManager {
    private static IOManager instance;
    private Context context;

    static IOManager getInstance(Context context)
    {
        if(instance==null)
        {
            instance=new IOManager();
            instance.context=context;
        }
        return  instance;
    }

    private IOManager(){}

    void openWebPage(String extraUrl) {
        String url = Constants.BaseUrlForOgyei;
        url += extraUrl;
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
