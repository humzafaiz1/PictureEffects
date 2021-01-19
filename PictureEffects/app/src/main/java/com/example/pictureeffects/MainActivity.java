package com.example.pictureeffects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    TextView textView;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    int rotateAmt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        //Don't want the user to be able to click the image view before an image is actually there
        imageView.setClickable(false);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
    }
    //Using Math.random I choose a number from 1-5  which will result in a random effect on the picture when tapped
    public void randomEffect(View view) {
        int max = 5;
        int min = 1;
        int rand = (int)(Math.random() * (max - min + 1) + min);
        switch(rand){
            case 1:
                rotate(1);
                break;
            case 2:
                gray();
                break;
            case 3:
                red();
                break;
            case 4:
                green();
                break;
            case 5:
                blue();
                break;
            default:
                break;
        }
    }
    //Picking a picture from the gallery
    public void getImage(View view) {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }
    //Setting the picture from the gallery to the imageview and also resetting the color/rotation
    //Also setting the imageview to clickable so that the user can tap the photo for a random effect
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            reset();
            imageView.setClickable(true);
            imageView.setImageURI(imageUri);
        }
    }
    //Rotating the picture a random amount and using the .animate().rotation to show the rotation
    //Setting the textview to display which effect happened exactly
    public void rotate(int x){
        int max = 360;
        int min = 1;
        int rand = (int)(Math.random() * (max - min + 1) + min);
        rotateAmt += rand;
        imageView.animate().rotation(rotateAmt);
        textView.setText("Rotation!");
    }
    //Colormatrix was used to get the saturation to 0 so the picture will appear gray
    //Colormatrix cannot be applied to the imageview directly so I use a ColorMatrixColorFilter since I can change the colorfilter of the imageview.
    public void gray(){
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        textView.setText("Gray!");
    }

    //Set the scale so that it shows up red, used the same method for the other colors as well
    public void red(){
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        colorMatrix.setScale(1f, 0, 0, 1f);

        imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        textView.setText("Red!");
    }

    public void blue(){
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        colorMatrix.setScale(0, 0, 1f, 1f);

        imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        textView.setText("Blue!");
    }

    public void green(){
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        colorMatrix.setScale(0, 1f, 0, 1f);

        imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        textView.setText("Green!");
    }
    // Set the saturation back so that the picture looks normal and set the rotation back to 0 so it is properly orientated
    // Set the text back to nothing since there is no effect on the picture when picking a new one
    public void reset(){
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(1);
        imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        textView.setText("");
        imageView.setRotation(0);
    }
}