package com.yevsp8.medicament;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OcrResultActivity extends AppCompatActivity {

    ImageView image;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_result);

        image = findViewById(R.id.imageView);
        resultText = findViewById(R.id.textView);
    }

//    public void getTextFromImage(View view)
//    {
//        Bitmap b= BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.im);
//        final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
//
//        if (!textRecognizer.isOperational()) {
//            Toast.makeText(this,"Text recognizer is not operating", Toast.LENGTH_LONG);
//        } else {
//            Frame frame=new Frame.Builder().setBitmap(b).build();
//            SparseArray<TextBlock> items=textRecognizer.detect(frame);
//
//            StringBuilder sb=new StringBuilder();
//
//            for (int i=0;i<items.size();i++)
//            {
//
//                TextBlock myItem=items.valueAt(i);
//                sb.append(myItem.getValue());
//                sb.append("\n");
//            }
//
//            resultText.setText(sb.toString());
//        }
//
//    }
}
