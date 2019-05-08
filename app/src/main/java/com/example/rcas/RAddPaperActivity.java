package com.example.rcas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.FirebaseDatabase;
import com.example.rcas.User;


import java.io.IOException;

public class RAddPaperActivity extends AppCompatActivity {

        public ImageView select_file;
        public TextView tv_notif;
        public TextView et_r_new_paper_title;
        public TextView et_r_new_paper_abstract;
        public Button btn_upload;
        public Button btn_r_fragment_paper_upload_next;
        public Uri docUri;
        public ProgressBar pb_upload;
        public FirebaseStorage storage; //to uploafd files
        public FirebaseDatabase database; //to store urls of uploaded files
    private DatabaseReference databaseUsers;
    //databaseUsers = firebaseDatabase.getInstance().getReference("Users");

    private DatabaseReference firebaseDatabase= FirebaseDatabase.getInstance().getReference("Users");
    private FirebaseAuth firebaseAuth_signup;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_radd_paper);
            Init();
            select_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if(ContextCompat.checkSelfPermission(r_fragment_paper_upload.this, (Manifest.permission.READ_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED)))
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                           == PackageManager.PERMISSION_GRANTED)
                    {
                        selectDOC();
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(RAddPaperActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 9 );
                    }

                }
            });

            btn_upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendPaperInfo();
                    if(docUri!=null) {
                        uploadFile(docUri);
                    }
                    else
                    {
                        Toast.makeText(RAddPaperActivity.this, "Select a file.", Toast.LENGTH_SHORT).show();
                    }
                }

                private void uploadFile(Uri docUri) {
                    pb_upload.setVisibility(View.VISIBLE);

                    StorageReference storageReference= storage.getReference();
                    //gets root path
                    final String fileName= System.currentTimeMillis()+"";
                    //TODO: dynamically get file name.
                    //not sotring file in root.
                    //make subfolder
                    storageReference.child("Papers").child(fileName).putFile(docUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //String url= taskSnapshot.getDownloadURL().toString();
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();

                            Uri downloadUrl = urlTask.getResult();
Log.d("94",urlTask.getException().getMessage());
                            final String url = String.valueOf(downloadUrl);
                            DatabaseReference reference= database.getReference();
                            reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {                pb_upload.setVisibility(View.GONE);

                                        Toast.makeText(RAddPaperActivity.this, "File Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                    else

                                    { Log.d("dberror", task.getException().getMessage());
                                        pb_upload.setVisibility(View.GONE);
                                        Toast.makeText(RAddPaperActivity.this, "File Not Uploaded", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(RAddPaperActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                                    }
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pb_upload.setVisibility(View.GONE);
                            Toast.makeText(RAddPaperActivity.this, "File Not Uploaded", Toast.LENGTH_SHORT).show();


                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            pb_upload.setVisibility(View.VISIBLE);

                        }
                    });

                }
            });
        }

    private void sendPaperInfo() {
        //to put the information of the paper along with the user basic info in firebase database.
            String PaperTitle= et_r_new_paper_title.getText().toString();
            String PaperAbstract = et_r_new_paper_abstract.getText().toString();
            String PaperRef = storage.getReference().toString();
            FirebaseUser currentUser= firebaseAuth_signup.getCurrentUser();
            User user = new User();
            user.setPaperTitle(PaperTitle);
            user.setPaperAbstract(PaperAbstract);
            user.setPaperRef(PaperRef);
        databaseUsers= FirebaseDatabase.getInstance().getReference("Users");
        String id = firebaseAuth_signup.getCurrentUser().getUid();
        User userfb = new User(
                id, user.getEmail(), user.getFirstName(),user.getLastName(), user.getUserType(), user.getPaperTitle(), user.getPaperAbstract(), user.getPaperRef()
        );
        databaseUsers.child(id).setValue(userfb);

    }

    @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                selectDOC();
            }
            else
            {
                Toast.makeText(RAddPaperActivity.this, "Please provide permission.", Toast.LENGTH_SHORT).show();
            }
        }

        private void selectDOC() {
            Intent intent= new Intent();
            intent.setType("application/doc");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 89);



        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            //check if file selected
           //if(requestCode==86 && resultCode==RESULT_OK && data!=null)
            if(requestCode==89 && resultCode==RESULT_OK && data!=null)
            {
                docUri= data.getData();//return uri of selcted file
                //String msg= "File"+data.getData().getLastPathSegment()+ "Selected";
                String msg= "File is selected, press Upload.";
                tv_notif.setText(msg);
            }
            else
            {
                Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show();
            }
        }

        private void Init() {
            storage = FirebaseStorage.getInstance();
            database = FirebaseDatabase.getInstance();
            select_file= (ImageView)findViewById(R.id.iv_r_add_new_paper_select_file);
            tv_notif= (TextView)findViewById(R.id.tv_r_add_new_paper_notif);
            btn_upload= (Button)findViewById(R.id.btn_r_add_new_paper_upload_file);
            //btn_r_fragment_paper_upload_next= (Button)findViewById(R.id.btn_r_fragment_paper_upload_next);
            pb_upload= (ProgressBar) findViewById(R.id.pb_upload);
            et_r_new_paper_title = (TextView)findViewById(R.id.et_r_new_paper_title);
                    et_r_new_paper_abstract = (TextView)findViewById(R.id.et_r_new_paper_abstract);
            firebaseAuth_signup= FirebaseAuth.getInstance();

            pb_upload.setVisibility(View.GONE);
        }
    }



