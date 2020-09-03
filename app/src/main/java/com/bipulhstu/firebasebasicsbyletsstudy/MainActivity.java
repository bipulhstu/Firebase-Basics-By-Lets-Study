package com.bipulhstu.firebasebasicsbyletsstudy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private StorageReference folder;
    EditText editText;
    Button sendButton;
    String name[] = {"Bipul", "Shumi", "Bipasha", "Haowa", "Bipul", "Shumi", "Bipasha", "Haowa", "Bipul", "Shumi", "Bipasha", "Haowa", "Bipul", "Shumi", "Bipasha", "Haowa", "Bipul", "Shumi", "Bipasha", "Haowa"};
    ArrayList<Datalist> arrayList;
    private int ImageBack = 1;
    ArrayList<Uri> imageList = new ArrayList<Uri>();
    int uploadCount = 0;

    //upload multiple images
    private static final int PICK_IMG = 1;
    private static final int PICK_FILE = 1;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private int uploads = 0;
    private ProgressDialog progressDialog;
    int index = 0;
    TextView textView;
    Button choose, upload;

    //upload multiple files
    private static final int PICK_IMAGE = 1;
    TextView alert;
    ArrayList<Uri> FileList = new ArrayList<Uri>();
    private Uri FileUri;
    private int upload_count = 0;

    TextView readDataTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.button);
        arrayList = new ArrayList<Datalist>();
        folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendDataUsingObject();
                //sendDataUsingHashmap();
                //sendUserDataUsingHashmap();
                //sendUserDataUsingObject();
                //sendListOfData();
                //sendDataUsingPushMethod();
                //uploadImageAndSaveUrlToFirebase();
            }
        });


        //upload multiple images
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading ..........");
        textView = findViewById(R.id.showText);
        choose = findViewById(R.id.chooseImages);
        upload = findViewById(R.id.uploadButton);

        readDataTextView = findViewById(R.id.readDataTextView);


    }

    //1.Set data to firebase using object
    private void sendDataUsingObject() {
        UserData userData = new UserData("Bipul Islam", 25);
        databaseReference.setValue(userData);
    }

    //2.Set data to firebase using hashmap
    private void sendDataUsingHashmap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "Sumya Shumi");
        hashMap.put("hobby", "Gaming");
        databaseReference.setValue(hashMap);
    }

    //3.Set user data to firebase using hashmap
    private void sendUserDataUsingHashmap() {
        String data = editText.getText().toString();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Info", data);
        databaseReference.setValue(hashMap);
    }

    //4.Set user data to firebase using object
    private void sendUserDataUsingObject() {
        String data = editText.getText().toString();
        UserData2 userData2 = new UserData2(data);
        databaseReference.child("object").setValue(userData2);
    }

    //5.Set a list of data to firebase database using object
    private void sendListOfData() {
        int i = 0;
        while (i < name.length) {
            Datalist datalist = new Datalist(name[i]);
            arrayList.add(datalist);
            i++;
        }
        databaseReference.child("nameList").setValue(arrayList);
    }

    //6.Push data to firebase using hashmap
    //Using push() method everytime it will generate a new key
    //under the unique key the value will be stored
    private void sendDataUsingPushMethod() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("institute", "Public University");
        //databaseReference.push().setValue(hashMap);
        //we can also add child before the unique key
        databaseReference.child("user1").push().setValue(hashMap);
    }


    //7.Upload image to firebase storage and save url to firebase database
    private void uploadImageAndSaveUrlToFirebase() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, ImageBack);
    }

    //onActivityForResult for uploadImageAndSaveUrlToFirebase() method
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImageBack) {
            if (resultCode == RESULT_OK) {
                Uri imageData = data.getData();
                //put data to imageFolder

                //give image a name
                final StorageReference imageName = folder.child("image" + imageData.getLastPathSegment());
                //upload image to firebase storage
                imageName.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(MainActivity.this, "Image uloaded successfully", Toast.LENGTH_SHORT).show();


                        //save image to firebase database
                        imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("uploadedImage", String.valueOf(uri));
                                databaseReference.child("ImageUrl").setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "Url stored successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                });
            }
        }
    }*/

    //8.Upload multiple images to firebase storage and save url to firebase database
    public void choose(View view) {
        //we will pick images
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMG);

    }

    //this is for upload multiple images to firebase storage
    /*@SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMG) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();

                    int CurrentImageSelect = 0;

                    while (CurrentImageSelect < count) {
                        Uri imageuri = data.getClipData().getItemAt(CurrentImageSelect).getUri();
                        ImageList.add(imageuri);
                        CurrentImageSelect = CurrentImageSelect + 1;
                    }
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("You Have Selected "+ ImageList.size() +" Pictures" );
                    choose.setVisibility(View.GONE);
                }

            }

        }

    }*/

    @SuppressLint("SetTextI18n")
    public void upload(View view) {

        textView.setText("Please Wait ... If Uploading takes Too much time please the button again ");
        progressDialog.show();
        final StorageReference ImageFolder =  FirebaseStorage.getInstance().getReference().child("ImageFolder");
        for (uploads=0; uploads < ImageList.size(); uploads++) {
            Uri Image  = ImageList.get(uploads);
            final StorageReference imagename = ImageFolder.child("image/"+Image.getLastPathSegment());

            imagename.putFile(ImageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = String.valueOf(uri);
                            SendLink(url);
                        }
                    });

                }
            });


        }


    }

    private void SendLink(String url) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("link", url);
        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                progressDialog.dismiss();
                textView.setText("Image Uploaded Successfully");
                upload.setVisibility(View.GONE);
                ImageList.clear();
            }
        });


    }

    //9.Upload file to Firebase Storage and save url to firebase database
    public void uploadFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE);
    }

    //this is for upload file
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_FILE){
            if(resultCode == RESULT_OK){
                //get file
                Uri fileUri = data.getData();
                StorageReference folder2 = FirebaseStorage.getInstance().getReference().child("FileFolder");
                final StorageReference fileName = folder2.child("file"+fileUri.getLastPathSegment());

                //upload file
                fileName.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(MainActivity.this, "File uploaded successfully", Toast.LENGTH_SHORT).show();

                        //save url to firebase database
                        fileName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("fileLink", String.valueOf(uri));

                                databaseReference.child("File").setValue(hashMap);
                            }
                        });
                    }
                });

            }
        }
    }*/

    //10.Upload multiple files to firebase storage and save url to firebase database
    public void chooseMultipleFiles(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent, PICK_FILE);
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE){
            if(resultCode == RESULT_OK){
                if(data.getClipData() != null){

                    int countClipData = data.getClipData().getItemCount();
                    int currentImageSelect = 0;

                    while (currentImageSelect < countClipData){
                        FileUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                        FileList.add(FileUri);
                        currentImageSelect = currentImageSelect +1;
                    }

                    textView.setVisibility(View.VISIBLE);
                    textView.setText("You Have Selected "+ FileList.size() +" Images");
                    choose.setVisibility(View.GONE);

                }else{
                    Toast.makeText(this, "Please Select Multiple File", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void uploadMultipleFiles(View view) {
        progressDialog.show();
        textView.setText("If Loading Takes too long please Press the button again");

        StorageReference folder2 = FirebaseStorage.getInstance().getReference().child("FilesFolder");

        for(upload_count = 0; upload_count < FileList.size(); upload_count++){

            Uri IndividualFile = FileList.get(upload_count);
            final StorageReference filesName = folder2.child("file"+IndividualFile.getLastPathSegment());

            filesName.putFile(IndividualFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filesName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = String.valueOf(uri);
                            StoreLink(url);
                        }
                    });
                }
            });
        }
    }

    private void StoreLink(String url) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserOne");

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Filelink",url);

        databaseReference.child("filesLink").push().setValue(hashMap);

        progressDialog.dismiss();
        textView.setText("File Uploaded Successfully");
        upload.setVisibility(View.GONE);
        FileList.clear();
    }

    //11.Read all the unique child value from android firebase database
    public void readData(View view) {
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    String value = String.valueOf(snapshot1.getValue());
                    readDataTextView.setText(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}