package com.code.fypurduvoiceassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "Register1212";
    Button register;
    EditText email;
    EditText password;
    EditText firstname;
    EditText lastname;
    FirebaseAuth mAuth;
    de.hdodenhof.circleimageview.CircleImageView profile_image;
    DatabaseReference mDatabaseRef;
    StorageReference mStorageRef;
    Uri selectedImageUri_upload;
    ProgressBar simpleProgressBar;
    StorageTask muploadtask;


    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadfile(Uri uri,String email,String firstname,String lastname){
        if(uri!=null){
            StorageReference fileReference=mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(uri));
            muploadtask=fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final String downloadUrl = uri.toString();
                                    Handler handler=new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            simpleProgressBar.setProgress(0);
                                        }
                                    },5000);

                                    Toast.makeText(RegisterActivity.this,"Upload Successful",Toast.LENGTH_LONG).show();

                                    Upload upload = new Upload(email,downloadUrl,firstname,lastname);
                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadId).setValue(upload);




                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress=(100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            simpleProgressBar.setProgress((int) progress);

                        }
                    });

        }
        else{
            Toast.makeText(RegisterActivity.this,"No File Selected",Toast.LENGTH_LONG).show();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        register=(Button)findViewById(R.id.getregistrationbutton);
        email=(EditText)findViewById(R.id.getemail);
        password=(EditText)findViewById(R.id.getpassword);
        firstname=(EditText) findViewById(R.id.getfirstname);
        lastname=(EditText) findViewById(R.id.getlastname);
        mAuth = FirebaseAuth.getInstance();
        profile_image=findViewById(R.id.profile_image_of_register);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        simpleProgressBar=findViewById(R.id.simpleProgressBar);
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");
        mStorageRef= FirebaseStorage.getInstance().getReference("uploads");

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> user = new HashMap<>();
                user.put("Email", email.getText().toString());
                user.put("Password", password.getText().toString());
                db.collection("UserInformation")
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

                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user=mAuth.getCurrentUser();
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(RegisterActivity.this,"Email Sent To User's Email",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(RegisterActivity.this,LoginPage.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                firstname.setText("");
                                lastname.setText("");
                                email.setText("");
                                lastname.setText("");
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this,"Unable To Send Email",Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this,"System Error",Toast.LENGTH_LONG).show();
                    }
                });

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("name", email.getText().toString());
                myEdit.commit();



                if(muploadtask!=null && muploadtask.isInProgress()){
                    Toast.makeText(RegisterActivity.this,"File Already Uploading Please Wait",Toast.LENGTH_LONG).show();
                }
                else {
                    uploadfile(selectedImageUri_upload,email.getText().toString(),firstname.getText().toString(),lastname.getText().toString());
                }




            }
        });




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {

                    Picasso.with(this).load(selectedImageUri).into(profile_image);
                    selectedImageUri_upload=selectedImageUri;




                }
            }
        }
    }

}