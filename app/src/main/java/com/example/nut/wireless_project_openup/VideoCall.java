package com.example.nut.wireless_project_openup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class VideoCall extends AppCompatActivity {

    private Camera mCamera;
    private CameraPreview mPreview;
    private int cameraID;
    private String TAG = "VideoCall";
    FrameLayout previewlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videocall);

        //Switch Camera Button
        ImageView SwitchCamera = (ImageView) findViewById(R.id.switchCamera);
        // set a onclick listener for when the button gets clicked
        SwitchCamera.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                SwitchCamera();
            }
        });

        //RequestPermission if needed
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 50);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO}, 50);
        }

        // Create an instance of Camera
        cameraID = findFrontFacingCamera();
        if (cameraID < 0) {
            Toast.makeText(this, "No front facing camera found.",
                    Toast.LENGTH_LONG).show();
        } else {
            mCamera = Camera.open(cameraID);
            //Create our Preview view and set it as the content of our activity.
            mPreview = new CameraPreview(this, mCamera);
            previewlayout = (FrameLayout) findViewById(R.id.videofeed);
            previewlayout.addView(mPreview);
        }

        setupCamera(1536,2048);
        setCameraDisplayOrientation(this,cameraID,mCamera);

    }

    private void SwitchCamera(){
        //Find info
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraID, info);

        //Clear current camera
        if(mCamera!=null)mCamera.release();
        previewlayout.removeAllViews();

        //Currently front, switch to back
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

            cameraID = findBackFacingCamera();
            if (cameraID < 0) {
                Toast.makeText(this, "No back facing camera found.",
                        Toast.LENGTH_LONG).show();
            } else {
                mCamera = Camera.open(cameraID);
                //Create our Preview view and set it as the content of our activity.
                mPreview = new CameraPreview(this, mCamera);
                FrameLayout preview = (FrameLayout) findViewById(R.id.videofeed);
                preview.addView(mPreview);
            }
        }
        //Currently back, switch to front
        else{
            cameraID = findFrontFacingCamera();
            if (cameraID < 0) {
                Toast.makeText(this, "No front facing camera found.",
                        Toast.LENGTH_LONG).show();
            } else {
                mCamera = Camera.open(cameraID);
                //Create our Preview view and set it as the content of our activity.
                mPreview = new CameraPreview(this, mCamera);
                FrameLayout preview = (FrameLayout) findViewById(R.id.videofeed);
                preview.addView(mPreview);
            }
        }

        setupCamera(1536,2048);
        setCameraDisplayOrientation(this,cameraID,mCamera);
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    private int findBackFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    private  void setupCamera(int width,int height)
    {
        //What is this even for???
//        CameraManager Manager=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
//        try{
//            for(String c:Manager.getCameraIdList()){
//                CameraCharacteristics characteristics=Manager.getCameraCharacteristics(c);
//                if(characteristics.get(characteristics.LENS_FACING)==characteristics.LENS_FACING_FRONT){
//                    continue;
//                }cameraID=Integer.parseInt(c);
//            }
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    public void EndCall(View view){
        if(mCamera!=null)mCamera.release();
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(mCamera!=null)mCamera.release();
        super.onBackPressed();
    }

    public static void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
}
