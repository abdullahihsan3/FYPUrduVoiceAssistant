package com.code.fypurduvoiceassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class MainScreenMessage extends AppCompatActivity  {
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    public boolean checkPermissions() {
        // this method is used to check permission
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void startRecording (MediaRecorder mediaRecorder) throws IllegalStateException
    {

        if(checkPermissions()) {

            try {

                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                mediaRecorder.setAudioSamplingRate(41000);
                String mFileName=Environment.getExternalStorageDirectory() + File.separator
                        + Environment.DIRECTORY_DCIM + File.separator + UUID.randomUUID();
                mediaRecorder.setOutputFile(mFileName);
                mediaRecorder.prepare();
                mediaRecorder.start();
                stop_button.setEnabled(true);
                mic_button.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(MainScreenMessage.this, "Recording....", Toast.LENGTH_LONG).show();
        }
        else{
            RequestPermissions();
        }
    }

    public void stopRecording (MediaRecorder mediaRecorder) throws IllegalStateException
    {
        try {
            mediaRecorder.stop();
            mediaRecorder.release();
            mic_button.setEnabled(true);
            stop_button.setEnabled(false);
            Toast.makeText(getApplicationContext(),"Audio Recorded Successfully",Toast.LENGTH_LONG).show();
        } catch (RuntimeException stopException){
            stopException.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error "+stopException,Toast.LENGTH_LONG).show();
        }

    }

    Button button_to_command_list;
    Button logout;
    ImageView mic_button;
    ImageView stop_button;
    MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_message);
        logout=findViewById(R.id.logout);
        button_to_command_list=findViewById(R.id.button_to_command_list);
        mic_button=findViewById(R.id.mic_button);
        stop_button=findViewById(R.id.stop_button);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainScreenMessage.this);
        String username = preferences.getString("Username", "");



        button_to_command_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainScreenMessage.this,RecyclerViewListActivity.class);
                startActivity(intent);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                Map<String, Object> user = new HashMap<>();
                user.put("Email", username);
                user.put("TimeLogout",currentTime);


                db.collection("UserLogout")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("123", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("456", "Error adding document", e);
                            }
                        });

                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(MainScreenMessage.this,LoginPage.class);
                startActivity(intent);

            }
        });


        mic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder=new MediaRecorder();
                startRecording(mediaRecorder);
            }
        });

        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    stopRecording(mediaRecorder);
            }

        });





    }

    private void RequestPermissions() {

        ActivityCompat.requestPermissions(MainScreenMessage.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }
}