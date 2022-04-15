package com.code.fypurduvoiceassistant;

import androidx.annotation.MainThread;
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
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.code.fypurduvoiceassistant.ml.Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.Utils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.io.*;
import java.net.*;
class MessageSender extends AsyncTask<String,Void,Void>{

    Socket socket;
   // DataOutputStream dataOutputStream;
    PrintWriter printWriter;

    @Override
    protected Void doInBackground(String... strings) {
        String filename=strings[0];
        try {

            InputStream inputStream = new FileInputStream(filename);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[0xFFFF];
            for (int len = inputStream.read(buffer); len != -1; len = inputStream.read(buffer)) {
                os.write(buffer, 0, len);
            }
            byte[] audioBytes =  os.toByteArray();


            System.out.println("Making Socket");
            socket=new Socket("34.131.143.93",3389);
            if(socket.isConnected()){
                System.out.println("Connected To Server");
                printWriter=new PrintWriter(socket.getOutputStream());
                printWriter.write(String.valueOf(audioBytes));
                printWriter.flush();
                printWriter.close();
                socket.close();
                Thread.sleep(5000);
            }
            else{
                System.out.println("Not Connected To Server");
            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    }




public class MainScreenMessage extends AppCompatActivity  {
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    Button button_to_command_list;
    Button logout;
    ImageView mic_button;
    Button stop_button;
    MediaRecorder mediaRecorder;
    String filename;
    MessageSender messageSender;
    Socket socket;

    private void RequestPermissions() {

        ActivityCompat.requestPermissions(MainScreenMessage.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    private void animatelogo(){

        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(10000);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        ImageView image= (ImageView) findViewById(R.id.mainscreenlogo);
        image.startAnimation(rotate);

    }
    public boolean checkPermissions() {
        // this method is used to check permission
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    public String startRecording (MediaRecorder mediaRecorder) throws IllegalStateException
    {
        String mFileName = null;
        if(checkPermissions()) {

            try {

                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.WEBM);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.OPUS);
                mediaRecorder.setAudioSamplingRate(8000);
                mFileName=Environment.getExternalStorageDirectory() + File.separator
                        + Environment.DIRECTORY_DCIM + File.separator + UUID.randomUUID()+".wav";
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
        return mFileName;
    }

    public void stopRecording (MediaRecorder mediaRecorder,String filename) throws IllegalStateException, InterruptedException, IOException {

        mediaRecorder.stop();
        mediaRecorder.release();
        mic_button.setEnabled(true);
        stop_button.setEnabled(false);
       // Toast.makeText(getApplicationContext(), "Audio Recorded Successfully", Toast.LENGTH_LONG).show();

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference ref = storageReference.child("audio/" + UUID.randomUUID()+".wav");
        Uri file = Uri.fromFile(new File(filename));
        ref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final Uri downloadUrl = uri;
                        Toast.makeText(MainScreenMessage.this, "Download URL at " + downloadUrl.toString(), Toast.LENGTH_LONG).show();
                               // messageSender=new MessageSender();
                                //messageSender.doInBackground(filename);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainScreenMessage.this, "Unable to Upload", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });







    }










                    @Override
                    protected void onCreate (Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        setContentView(R.layout.activity_main_screen_message);
                        if (android.os.Build.VERSION.SDK_INT > 9)
                        {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                        }

                        animatelogo();
                        logout = findViewById(R.id.logout);
                        button_to_command_list = findViewById(R.id.button_to_command_list);
                        mic_button = findViewById(R.id.mic_button);
                        stop_button = findViewById(R.id.stop_button);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainScreenMessage.this);
                        String username = preferences.getString("Username", "");


                        button_to_command_list.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainScreenMessage.this, RecyclerViewListActivity.class);
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
                                user.put("TimeLogout", currentTime);


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
                                Intent intent = new Intent(MainScreenMessage.this, LoginPage.class);
                                startActivity(intent);

                            }
                        });


                        mic_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mediaRecorder = new MediaRecorder();
                                filename = startRecording(mediaRecorder);
                            }
                        });

                        stop_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                try {
                                    stopRecording(mediaRecorder, filename);
                                } catch (InterruptedException | IOException e) {
                                    e.printStackTrace();
                                }

                            }

                        });
                    }}


