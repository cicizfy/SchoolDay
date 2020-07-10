package com.example.fangyuanzheng.schoolday;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewNoteFargment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewNoteFargment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static int postion;
    static HashMap note =new HashMap();
    static NoteData noteData =new NoteData();
    static EditText NoteTiltle_TextView;
    static TextView NoteDate_TextView;
    static EditText NoteContent_TextView;
    static final int REQUEST_TAKE_PHOTO=1;
    static String mCurrentPhotoPath;
    static ImageView NoteImage_ImageView;
    public NewNoteFargment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewNoteFargment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewNoteFargment newInstance(String param1, String param2) {
        NewNoteFargment fragment = new NewNoteFargment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        setRetainInstance(true);
        View currentView= inflater.inflate(R.layout.fragment_note_detail, container, false);
        NoteContent_TextView=(EditText)currentView.findViewById(R.id.Note_SharedIntroduction);
        NoteDate_TextView=(TextView)currentView.findViewById(R.id.Note_SharedDate);
        NoteTiltle_TextView=(EditText) currentView.findViewById(R.id.Note_SharedTitle);
        NoteImage_ImageView=(ImageView)currentView.findViewById(R.id.Note_ContentImage);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM/dd/yyyy");
        String currentDate=simpleDateFormat.format(new Date());
        NoteDate_TextView.setText(currentDate);
       // NoteTiltle_TextView.setText(note.get("name").toString());
      //  NoteDate_TextView.setText(note.get("date").toString());
        //NoteContent_TextView.setText(note.get("content").toString());
        if (note.get("image")!=null){
            setPic();
        }
        return currentView;

    }
    public void onPause() {
        super.onPause();
        String newID=NoteTiltle_TextView.getText().toString();
        String content=NoteContent_TextView.getText().toString();

        note.put("name",newID);
        note.put("content",content);
        // String currentDate= DateFormat.getDateInstance().format(new Date());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM/dd/yyyy");
        String currentDate=simpleDateFormat.format(new Date());
        note.put("date",currentDate);

        if(newID.length()==0){
            simpleDateFormat=new SimpleDateFormat("yyMMddHHmmss");
             currentDate=simpleDateFormat.format(new Date());
            newID=currentDate;
        }
        note.put("id",newID);
        simpleDateFormat=new SimpleDateFormat("yyyy");
        note.put("year",simpleDateFormat.format(new Date()));
        noteData.addItemToServer(note);
        Snackbar snackbar = Snackbar.make(NoteTiltle_TextView, note.get("id").toString()+" is added", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu,menuInflater);
        //menu.add("test").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        // if(menu.findItem(R.id.action_search)==null)
        //getActivity().getActionBar().setTitle("Class Schedule");
        menuInflater.inflate(R.menu.camera_video_menu,menu);
        MenuItem pic =menu.findItem(R.id.action_pic);
       // MenuItem vid =menu.findItem(R.id.action_video);
        // Toast.makeText(getActivity(), "Add " + Pageposition, Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_pic:
                takePhoto();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void takePhoto() {
        Context context = getActivity();
        PackageManager packageManager = context.getPackageManager();
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false){
            Toast.makeText(getActivity(), "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        Intent takePictureIntent  = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //  takePictureIntent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("TAKE PHOTO",ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(), "com.example.fangyuanzheng.schoolday.fileprovider", photoFile);

                takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
        /* File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
         imageUri = Uri.fromFile(photo);*/
        // startActivityForResult(intent, 1888);


    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK&&requestCode==REQUEST_TAKE_PHOTO) {
            // Bitmap photo=(Bitmap)data.getExtras().get("data");
            // NoteImage_ImageView.setImageBitmap(photo);
            note.put("image",mCurrentPhotoPath);
            setPic();
            galleryAddPic();
        }
    }

    private void setPic() {
        // Get the dimensions of the View

        int targetW = NoteImage_ImageView.getWidth();
        int targetH = NoteImage_ImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(note.get("image").toString(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        //int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        //bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(note.get("image").toString(), bmOptions);
        // getActivity().grantUriPermission();

        //  MediaStore.Images.Media.insertImage(getContext().getContentResolver(),bitmap, note.get("name").toString(),"");
        NoteImage_ImageView.setImageBitmap(bitmap);
    }
    private void galleryAddPic() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "IMG_");
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, note.get("image").toString());

        getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
}
