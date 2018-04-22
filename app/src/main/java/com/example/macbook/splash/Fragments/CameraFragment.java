package com.example.macbook.splash.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IMediaApi;
import com.example.macbook.splash.R;
import com.example.macbook.splash.TeacherSelectingChildrenForPostActivity;
import com.otaliastudios.cameraview.Audio;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.otaliastudios.cameraview.Flash;
import com.otaliastudios.cameraview.Gesture;
import com.otaliastudios.cameraview.GestureAction;
import com.otaliastudios.cameraview.SessionType;
import com.otaliastudios.cameraview.VideoQuality;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CameraFragment extends Fragment {

    //region VARIABLES
    private ImageView teacherCameraTakePhoto,teacherCameraFlash,teacherCameraFlip;
    private int flash;
    private CameraView camera;
    private File video;
    private File photoFile;
    private pl.droidsonroids.gif.GifImageView recordButton;
    private byte[] PhotoArray;
    private boolean cameraIsRecording = false;
    private boolean videoIsRecorded = false;
    private Chronometer teacherCameraChronometer;
    private int recordingSeconds = 0;
    private ProgressBar progressBar;
    private FileOutputStream fileOutputStream = null;
    //endregion

    //region khorm

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private OnFragmentInteractionListener mListener;

    public CameraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CameraFragment.
     */
    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }




    //endregion

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view =  inflater.inflate(R.layout.fragment_camera, container, false);

        //region CAMERA PARAMETERS
        camera = (CameraView)view.findViewById(R.id.camera);

        camera.mapGesture(Gesture.PINCH, GestureAction.ZOOM); // Pinch to zoom!
        camera.mapGesture(Gesture.TAP, GestureAction.FOCUS_WITH_MARKER); // Tap to focus!
        camera.mapGesture(Gesture.SCROLL_VERTICAL,GestureAction.EXPOSURE_CORRECTION);

        camera.setFlash(Flash.AUTO);
        camera.setFacing(Facing.BACK);
        camera.setSessionType(SessionType.PICTURE);
        camera.setVideoQuality(VideoQuality.MAX_QVGA);
        camera.setAudio(Audio.ON);
        camera.setCropOutput(true);
        //endregion

        camera.addCameraListener(new CameraListener() {
            public void onPictureTaken(byte[] picture) {
                // Create a bitmap or a file...
                CameraUtils.BitmapCallback photo = new CameraUtils.BitmapCallback() {
                    @Override
                    public void onBitmapReady(Bitmap bitmap) {
                        Bitmap bmp;
                        Log.e("DEBUG","width = "+bitmap.getWidth());
                        Log.e("DEBUG","height = "+bitmap.getHeight());
                        /////////////////////////////
                        try {
                            photoFile = new File(getContext().getFilesDir()+"/snap.png");
                            Log.e("DEBUG"," "+photoFile.getAbsolutePath());

                            fileOutputStream = new FileOutputStream(photoFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream); // bmp is your Bitmap instance
                            // PNG is a lossless format, the compression factor (100) is ignored
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        /////////////////////////////

                        transition();


                    }
                };
                // CameraUtils will read EXIF orientation for you, in a worker thread.
                CameraUtils.decodeBitmap(picture,photo);
            }
            @Override
            public void onVideoTaken(File video) {
                super.onVideoTaken(video);
                Log.e("DEBUG",""+video.length());
                onVideo(video);
            }
        });

        //region Buttons

        //region FLASH
        flash = 0;
        teacherCameraFlash = (ImageView) view.findViewById(R.id.teacherCameraFlash);
        teacherCameraFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //region CameraFlashOnClickListener
                switch (flash){
                    case 0:
                        camera.setFlash(Flash.ON);
                        teacherCameraFlash.setImageResource(R.drawable.flashison);
                        Toast.makeText(getContext(),"Le flash est activé",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        camera.setFlash(Flash.OFF);
                        teacherCameraFlash.setImageResource(R.drawable.flashisoff);
                        Toast.makeText(getContext(),"Le flash est desactivé",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        camera.setFlash(Flash.AUTO);
                        teacherCameraFlash.setImageResource(R.drawable.flashisautomatic);
                        Toast.makeText(getContext(),"Le flash est automatique",Toast.LENGTH_SHORT).show();
                        break;
                }
                flash=(flash+1)%3;
                //endregion
            }

        });
        //endregion

        //region take photo button
        teacherCameraTakePhoto = (ImageView) view.findViewById(R.id.teacherCameraTakePhoto);

        teacherCameraTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.setSessionType(SessionType.PICTURE);
                camera.capturePicture();
            }
        });
        //endregion

        //region recordButton + chronometer

        progressBar = view.findViewById(R.id.recordProgressBar);

        teacherCameraChronometer = (Chronometer)view.findViewById(R.id.teacherCameraChronometer);
        teacherCameraChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(recordingSeconds > 11){
                    camera.stopCapturingVideo();
                }else{
                    recordingSeconds++;
                    progressBar.setProgress(recordingSeconds);
                }
            }
        });
        ImageView recordButton = (ImageView)view.findViewById(R.id.recordButton);

        //endregion

        //region recordButton On click listener
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if((cameraIsRecording) && (recordingSeconds>4)){
                        Log.e("DEBUG","miboun "+recordingSeconds);
                        camera.stopCapturingVideo();
                        teacherCameraChronometer.stop();
                        recordingSeconds = 0;
                        cameraIsRecording = false;
                    }else if((cameraIsRecording) && (recordingSeconds<4)){
                        Log.e("DEBUG",""+recordingSeconds);
                    }else if (!(videoIsRecorded) && !(cameraIsRecording)) {
                        videoIsRecorded = true;
                        cameraIsRecording = true;
                        teacherCameraChronometer.setBase(SystemClock.elapsedRealtime());
                        camera.setSessionType(SessionType.VIDEO);
                        camera.startCapturingVideo(video);
                        teacherCameraChronometer.start();

                        teacherCameraFlip.setVisibility(View.GONE);
                        teacherCameraFlash.setVisibility(View.GONE);
                        teacherCameraTakePhoto.setEnabled(false);
                    }

                }

        });
        //endregion

        //region FLIP CAMERA
        teacherCameraFlip = (ImageView) view.findViewById(R.id.teacherCameraFlip);
        teacherCameraFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.toggleFacing();
            }
        });
        //endregion

        //endregion
        return view;
    }



    //region Methods

    public void transition(){
        Intent intent = new Intent(getActivity(), TeacherSelectingChildrenForPostActivity.class);
        intent.putExtra("photoUriString",photoFile.getAbsolutePath());
        teacherCameraTakePhoto.setClickable(true);
        startActivity(intent);
    }


    private void onVideo(File video) {
        Intent intent = new Intent(getActivity(), TeacherSelectingChildrenForPostActivity.class);
        intent.putExtra("videoUri", Uri.fromFile(video));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        camera.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        camera.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        camera.destroy();
    }

    //endregion

}


//region ButtonHandler
       /* teacherCameraTakePhoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(!longClickActive)
                        {
                            isTakePicture=true;
                            longClickActive=true;
                            startClickTime = Calendar.getInstance().getTimeInMillis();
                            isVideoBroke = false;
                        }
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        elapsed = Calendar.getInstance().getTimeInMillis()-startClickTime;
                        if(elapsed>=MIN_CLICK_DURATION&&!(recording)&&(isTakePicture))
                        {
                            recording = true;
                            startRecordTime = Calendar.getInstance().getTimeInMillis();

                            //StartRecording video
                            recordButton.setVisibility(View.VISIBLE);
                            Log.e("Debug","entered Recording");
                            camera.setSessionType(SessionType.VIDEO);
                            camera.startCapturingVideo(video);
                        }
                        if(elapsed>=MAX_CLICK_DURATION&&(recording)&&(isTakePicture))
                        {

                            //StartRecording video
                            Log.e("Debug","stop hhhhh stop camera Recording");
                            isTakePicture = false;
                            recording = false;
                            camera.stopCapturingVideo();
                            camera.setSessionType(SessionType.PICTURE);
                            recordButton.setVisibility(View.INVISIBLE);
                        }
                        //update PB if(elapsed>=MIN_CLICK_DURATION&&!(recording)&&(isTakePicture)
                        return true;
                    case MotionEvent.ACTION_UP:

                        elapsedVideoInitTime = Calendar.getInstance().getTimeInMillis()-startRecordTime;
                        if(recording&&(elapsedVideoInitTime>MIN_CLICK_DURATION)&&(elapsedVideoInitTime>MIN_VIDEO_DURATION))
                        {
                            //stopRecording();
                            Log.e("Debug","stop Recording");

                            recording = false;
                            camera.stopCapturingVideo();
                            camera.setSessionType(SessionType.PICTURE);
                            recordButton.setVisibility(View.INVISIBLE);
                        }
                        else if(recording&&!(elapsedVideoInitTime>MIN_VIDEO_DURATION))
                        {
                            //delay
                            Log.e("Debug","Start Delay Recording");
                            isVideoBroke = true;
                            recordButton.setVisibility(View.INVISIBLE);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something after 100ms
                                    Log.e("Debug","Start Delay Recording");

                                    recording = false;
                                    camera.stopCapturingVideo();
                                    camera.setSessionType(SessionType.PICTURE);


                                }
                            }, 1000);


                        }
                        else if(isTakePicture)
                        {
                            Log.e("Debug","take picture");

                            //take picture
                            //camera.setSessionType(SessionType.PICTURE);
                            camera.capturePicture();
                            teacherCameraTakePhoto.setClickable(false);


                        }
                        longClickActive=false;

                        return true;
                }
                return true;
            }
        });
        //endregion
*/

//endregion