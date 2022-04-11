package com.gtappdevelopers.dynamicpdfviewer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

import iamutkarshtiwari.github.io.ananas.editimage.EditImageActivity;
import iamutkarshtiwari.github.io.ananas.editimage.ImageEditorIntentBuilder;

public class PhotoEditor extends AppCompatActivity {

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_editor);
        btn = findViewById(R.id.idBtnPick);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });

    }

    private void checkPermission(){
        int permission = ActivityCompat.checkSelfPermission(PhotoEditor.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            pickImage();
        }else{
            if(permission!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(PhotoEditor.this,new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },100);

            }else{
                pickImage();
            }
        }
    }

    private void pickImage(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            pickImage();
        }else{
            Toast.makeText(this, "Permission denined..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri uri = data.getData();
            switch (requestCode){
                case 100:
                    Intent i = new Intent(PhotoEditor.this, DsPhotoEditorActivity.class);
                    i.setData(uri);
                    i.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY,"Images");
                    i.putExtra(DsPhotoEditorConstants.DS_TOOL_BAR_BACKGROUND_COLOR,getResources().getColor(R.color.dark_blue));
                    i.putExtra(DsPhotoEditorConstants.DS_MAIN_BACKGROUND_COLOR,getResources().getColor(R.color.dark_blue));
                    i.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE,new int[]{DsPhotoEditorActivity.TOOL_WARMTH,
                    DsPhotoEditorActivity.TOOL_PIXELATE});
                    startActivityForResult(i,101);
                    break;
                case 101:
                    Toast.makeText(this, "Image saved..", Toast.LENGTH_SHORT).show();
                break;

            }
        }
    }
}