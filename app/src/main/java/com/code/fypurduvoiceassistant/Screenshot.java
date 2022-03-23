package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Screenshot extends AppCompatActivity {
    TextView screenshot;
    TextView textView;

    private Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);
        screenshot=findViewById(R.id.screenshot);
        textView=findViewById(R.id.text_to_view);
        textView.setText("Taking Screenshot...");

        screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Screenshot Taken...");
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                Bitmap b=screenShot(rootView);
                String pathofBmp= MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),
                        b,"Title", null);
                Uri uri = Uri.parse(pathofBmp);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Urdu Assistant App");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(shareIntent, "ScreenShot Title"));
                Toast.makeText(Screenshot.this, "ScreenShot Taken", Toast.LENGTH_SHORT).show();
            }
        });


    }
}