package com.example.emerus;

import static java.util.Arrays.sort;
import static java.util.Collections.*;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.service.autofill.Transformation;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.github.mikephil.charting.data.BarEntry;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.TensorProcessor;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final float IMAGE_MEAN = 0.0f;
    private static final float IMAGE_STD = 1.0f;
    private static final float PROBABILITY_MEAN = 0.0f;
    private static final float PROBABILITY_STD = 255.0f;
    private static final int PERMISSON_CODE = 1000;
    private static final int IMAGE_CAPTUREW_CDOE = 1001;
    protected Interpreter tflite;
    ImageView imageView;
    Uri imageuri;
    Button buclassify;
    Button mCaptureBtn;
    Button detaljibtn;
    ImageView mIMageView;
    Uri image_uri;
    TextView textView;
    private MappedByteBuffer tfliteModel;
    private TensorImage inputImageBuffer;
    private int imageSizeX;
    private int imageSizeY;
    private TensorBuffer outputProbabilityBuffer;
    private TensorProcessor probabilityProcessor;
    private Bitmap bitmap;
    private List<String> labels;
    private Object Float;




    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        Button pronađi = (Button) findViewById(R.id.capture_image_btn1);
        buclassify = (Button) findViewById(R.id.classify);

        mIMageView = findViewById(R.id.image);
        mCaptureBtn = findViewById(R.id.capture_image_btn);
        View textview = findViewById(R.id.Designed);
        textView = (TextView) findViewById(R.id.result);



        // Open Browser after Clicking on "Designed by Emerus" and open the Emerus Site on the browser.
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("http://www.emerus.eu"));
                startActivity(browserIntent);
            }
        });

        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSON_CODE);
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent();
                                           intent.setType("image/*");
                                           intent.setAction(Intent.ACTION_GET_CONTENT);
                                           startActivityForResult(Intent.createChooser(intent, "Select Picture"), 12);
                                       }
                                   });
        pronađi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 12);
            }
        });

        try {
            tflite = new Interpreter(loadmodelfile(MainActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }

        buclassify.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                try {
                    int imageTensorIndex = 0;
                    int[] imageShape = tflite.getInputTensor(imageTensorIndex).shape(); // {1, height, width, 3}
                    imageSizeY = imageShape[1];
                    imageSizeX = imageShape[2];
                    DataType imageDataType = tflite.getInputTensor(imageTensorIndex).dataType();

                    int probabilityTensorIndex = 0;
                    int[] probabilityShape =
                            tflite.getOutputTensor(probabilityTensorIndex).shape(); // {1, NUM_CLASSES}
                    DataType probabilityDataType = tflite.getOutputTensor(probabilityTensorIndex).dataType();

                    inputImageBuffer = new TensorImage(imageDataType);
                    outputProbabilityBuffer = TensorBuffer.createFixedSize(probabilityShape, probabilityDataType);
                    probabilityProcessor = new TensorProcessor.Builder().add(getPostprocessNormalizeOp()).build();

                    inputImageBuffer = loadImage(bitmap);

                    tflite.run(inputImageBuffer.getBuffer(), outputProbabilityBuffer.getBuffer().rewind());
                    showresult();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,
                            "Molimo prvo odaberite profil",Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.TITLE, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTUREW_CDOE);

    }
    

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSON_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private TensorImage loadImage(final Bitmap bitmap) {
        // Loads bitmap into a TensorImage.
        inputImageBuffer.load(bitmap);

        // Creates processor for the TensorImage.
        int cropSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
         ImageProcessor imageProcessor =
                new ImageProcessor.Builder()
                        .add(new ResizeWithCropOrPadOp(cropSize, cropSize))
                        .add(new ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                        .add(getPreprocessNormalizeOp())
                        .build();
        return imageProcessor.process(inputImageBuffer);
    }

    private MappedByteBuffer loadmodelfile(Activity activity) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startoffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startoffset, declaredLength);
    }

    private TensorOperator getPreprocessNormalizeOp() {
        return new NormalizeOp(IMAGE_MEAN, IMAGE_STD);
    }

    private TensorOperator getPostprocessNormalizeOp() {
        return new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ResourceAsColor")


    //Buttons for details :
    
    private void showButton(){
        Button detaljibtn = findViewById(R.id.detaljibtn);
        detaljibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Details.class)) ;

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showresult() {
        TextView result = (TextView)findViewById(R.id.result);
        TextView postotak = (TextView) findViewById(R.id.postotak);
        TextView result2 = (TextView)findViewById(R.id.result2);
        TextView postotak2 = (TextView) findViewById(R.id.postotak2);
        TextView result3 = (TextView)findViewById(R.id.result3);
        TextView postotak3 = (TextView) findViewById(R.id.postotak3);


        try {
            labels = FileUtil.loadLabels(MainActivity.this, "labels.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Float> labeledProbability =
                new TensorLabel(labels, probabilityProcessor.process(outputProbabilityBuffer))
                        .getMapWithFloatValue();


        // float maxValueInMap = (max(labeledProbability.values()));


       // Collection <String> allNames= labeledProbability.keySet();
        // Collection<Float> allValues =  labeledProbability.values();

       // System.out.println(allNames);
        // System.out.println(allValues);



       // Float max = Collections.max(allValues);

       // int biggestValue =Math.round(max * 100);


        for (Map.Entry<String, Float> entry : labeledProbability.entrySet()) {
            String[] label = labeledProbability.keySet().toArray(new String[0]);
            Float[] label_probability = labeledProbability.values().toArray(new Float[0]);


            // PREPARING THE ARRAY LIST OF BAR ENTRIES

            ArrayList<Float> PercentNum = new ArrayList<>();
            for (int i = 0; i < label_probability.length; i++) {
                if (label_probability[i] >= 0.0) {
                   PercentNum.add(label_probability[i]);

                }}

            Collections.sort(PercentNum);
            Collections.reverse(PercentNum);

            // TO ADD THE VALUES In Percent
            ;
            ArrayList<String> xAxisNames = new ArrayList<>();
            for (int i = 0; i < label.length; i++) {
                if(label_probability[i] == PercentNum.get(0) ){
                    xAxisNames.add(label[i]);;
            }}


            ArrayList<String> xAxisNames2nd = new ArrayList<>();
            for (int i = 0; i < label.length; i++) {
                if(label_probability[i] == PercentNum.get(1) ){
                    xAxisNames2nd.add(label[i]);;
                }}

            ArrayList<String> xAxisNames3nd = new ArrayList<>();
            for (int i = 0; i < label.length; i++) {
                if(label_probability[i] == PercentNum.get(2) ){
                    xAxisNames3nd.add(label[i]);;
                }}

            //Progress Bar for the 3 profiles :

            ProgressBar simpleProgressBar=(ProgressBar) findViewById(R.id.simpleProgressBar); // initiate the progress bar
            simpleProgressBar.setMax(100); // 100 maximum value for the progress bar
            ObjectAnimator progressAnimator = ObjectAnimator.ofInt(simpleProgressBar, "progress", 0,Math.round(PercentNum.get(0) * 100));
            progressAnimator.setDuration(2000);
            progressAnimator.start();
            simpleProgressBar.setProgress((int) (PercentNum.get(0) * 100));


            ProgressBar simpleProgressBar2=(ProgressBar) findViewById(R.id.simpleProgressBar2); // initiate the progress bar
            simpleProgressBar2.setMax(100); // 100 maximum value for the progress bar
            ObjectAnimator progressAnimator1 = ObjectAnimator.ofInt(simpleProgressBar2, "progress", 0,Math.round(PercentNum.get(1) * 100));
            progressAnimator1.setDuration(1500);
            progressAnimator1.start();
            simpleProgressBar2.setProgress((int) (PercentNum.get(1) * 100));

            ProgressBar simpleProgressBar3=(ProgressBar) findViewById(R.id.simpleProgressBar3); // initiate the progress bar
            simpleProgressBar3.setMax(100); // 100 maximum value for the progress bar
            ObjectAnimator progressAnimator2 = ObjectAnimator.ofInt(simpleProgressBar3, "progress", 0,Math.round(PercentNum.get(2) * 100));
            progressAnimator2.setDuration(1000);
            progressAnimator2.start();
            simpleProgressBar3.setProgress((int) (PercentNum.get(2) * 100));

            //Progress Bar for the 3 profiles - height :
            simpleProgressBar.setScaleY(3.8f);
            simpleProgressBar2.setScaleY(3.8f);
            simpleProgressBar3.setScaleY(3.8f);

            result.setText(xAxisNames.get(0));
            postotak.setText(Math.round(PercentNum.get(0) * 100) + " %");
            result2.setText(xAxisNames2nd.get(0));
            postotak2.setText(Math.round(PercentNum.get(1) * 100) + " %");
            result3.setText(xAxisNames3nd.get(0));
            postotak3.setText(Math.round(PercentNum.get(2) * 100) + " %");

            //Progress bar button
            showButton();

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 12 && resultCode == RESULT_OK) {
                imageuri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (resultCode == RESULT_OK) {
                mIMageView.setImageURI(image_uri);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

