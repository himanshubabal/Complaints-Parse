package com.parse.starter.Navigation.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.starter.MainActivity;
import com.parse.starter.R;
import com.parse.starter.Utilities.UploadImageToParse;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ChangeDP extends Fragment implements View.OnClickListener{
    Button imgFromCamera, imgFromGallery, uploadImg;
    ImageView imageView;
    Bitmap userDP;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.change_dp,container,false);
        imageView = (ImageView)v.findViewById(R.id.imageView_update_dp);
        imgFromCamera = (Button)v.findViewById(R.id.imgFromCamera_button_updateDP);
        imgFromCamera.setOnClickListener(this);
        imgFromGallery = (Button)v.findViewById(R.id.imgFromGallary_button_updateDP);
        imgFromGallery.setOnClickListener(this);
        uploadImg = (Button)v.findViewById(R.id.updateDP_updateDP);
        uploadImg.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.imgFromCamera_button_updateDP){
            Log.i("parse-test", "1");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);//CAMERA request code is 0.
        }
        else if(v.getId() == R.id.imgFromGallary_button_updateDP){
            Log.i("parse-test", "2");
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(
                    Intent.createChooser(intent, "Select File"), 1);//GALLERY request code is 0.
        }
        else if(v.getId() == R.id.updateDP_updateDP) {//Upload Image Button Clicked
            Log.i("parse-test", "5");
            if (userDP != null) {
                int i = UploadImageToParse.uploadToParse(userDP);
                Log.i("parse-test", i + "value of i");
                if (i != 1) {
                    Log.i("parse-test", "11");
                    Toast.makeText(getActivity(), "Picture Updated Successfully", Toast.LENGTH_SHORT).show();
                    Log.i("parse-test", "12");
                    ((MainActivity) getActivity()).updateNavBarDp(userDP);
                    Fragment userProfile = new UserProfile();
                    FragmentTransaction fragmentT = getFragmentManager().beginTransaction();
                    fragmentT.replace(R.id.frame, userProfile);
                    fragmentT.addToBackStack(null);
                    fragmentT.commit();
                    Log.i("parse-test", "13");
                } else {
                    Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Choose photo to upload", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == MainActivity.RESULT_OK && data != null) {
            if (requestCode == 0) {//CAMERA
                Log.i("parse-test", "3");
                userDP = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(userDP);
            }
            else if(requestCode == 1){//Gallery
                Log.i("parse-test", "4");
                Uri selectedImg = data.getData();
                try {
                    userDP = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImg);
                    int nh = (int) ( userDP.getHeight() * (512.0 / userDP.getWidth()) );
                    Bitmap scaled = Bitmap.createScaledBitmap(userDP, 512, nh, true);
                    userDP = scaled;
                    imageView.setImageBitmap(userDP);

                } catch (FileNotFoundException e) {
                    Log.i("parse-updateDP", e.toString());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.i("parse-updateDP", e.toString());
                    e.printStackTrace();
                }
            }
        }
    }
}
