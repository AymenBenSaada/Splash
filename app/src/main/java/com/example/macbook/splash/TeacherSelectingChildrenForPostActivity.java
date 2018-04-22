package com.example.macbook.splash;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.macbook.splash.Adapters.ChildProfilePictureAdapter;
import com.example.macbook.splash.Enum.Gender;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IMediaApi;
import com.example.macbook.splash.Interfaces.IPostsApi;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Post;
import com.example.macbook.splash.Models.Teacher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherSelectingChildrenForPostActivity extends AppCompatActivity {

    byte[] Photo;
    Bitmap bitmap;
    ImageView imageView;
    VideoView videoView;
    String TeacherFile = "teacher.dat";
    private Uri videoUri;
    private List<Child> children;
    private ArrayList<Boolean> IsChildSelected;
    private ChildProfilePictureAdapter childProfilePictureAdapter;
    private ImageView btnAfterSelectingChildren;
    private String postContent = "Ce post ne contient pas de contenu";
    private String postLegend = "Ce post ne contient pas de légende";
    private Post post;
    private ImageView yesrepas,yesjeux,yessport,yesdessin,yesapprentissage,yesmignon;
    private ArrayList<Boolean> IsActivitySelected = new ArrayList<>();
    private String photoURI;
    private String videoURII;
    private File photoFileCompressed;
    private String compressedPhotoURIforSendeing;
    private Boolean isSellectedAtAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_teacher_selecting_children_for_post);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        imageView = (ImageView)findViewById(R.id.teacherSelectingChildrenImageView);
        videoView = (VideoView)findViewById(R.id.teacherSelectingChildrenVideoView);

        for(int k=0;k<6;k++){IsActivitySelected.add(Boolean.FALSE);}

        Intent i =getIntent();
        Photo = i.getByteArrayExtra("photo");



        if(Photo!=null) //if the media is a photo
        {
            photoURI = i.getStringExtra("photoUriString");
            photoFileCompressed = new File(photoURI);
            imageView.setImageURI(Uri.parse(photoURI));
            compressImage();
        }
        else           //if the media is a video
        {
            videoView.setVisibility(View.VISIBLE);
            videoUri = i.getParcelableExtra("videoUri");
            videoView.setVideoURI(videoUri);
            videoView.start();
            //uploadVideo();
        }



        ListView profilePicturesList = (ListView)findViewById(R.id.listViewChildrenPics);

        children = loadChildren();
        IsChildSelected = new ArrayList<>();
        for(int k=0;k<=children.size();k++){
            IsChildSelected.add(Boolean.FALSE);
        }

        childProfilePictureAdapter = new ChildProfilePictureAdapter(children,IsChildSelected,this,getLayoutInflater());
        profilePicturesList.setAdapter(childProfilePictureAdapter);

        profilePicturesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //region traitement
                isSellectedAtAll=true;
                if(position != 0){
                    IsChildSelected.set(position,!(IsChildSelected.get(position)));
                    childProfilePictureAdapter.update(IsChildSelected);
                }else{
                    Boolean bool;
                    if(IsChildSelected.get(0)){
                        bool = Boolean.FALSE;
                    }else{
                        bool = Boolean.TRUE;
                    }
                    for(int k=0;k<IsChildSelected.size();k++){
                        IsChildSelected.set(k,bool);
                        childProfilePictureAdapter.update(IsChildSelected);
                    }
                }
                //endregion
            }
        });
        //TODO: fix the selection process here
        btnAfterSelectingChildren =(ImageView) findViewById(R.id.btnAfterSelectingChildren);
        btnAfterSelectingChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region POST-MEDIA
                // if it s a photo
                //convert the byteArray to a file and send it then delete the file; or save it in a tempFile
                //if its s video
                //File file = new File(uri.getPath());
                //then send it
                //on response mana3mel chay
                //endregion
                //region Traitement
                if (isSellectedAtAll) {

                Date today = Calendar.getInstance().getTime();
                //TODO : fix the teacher ID
                post = new Post(-1, postContent, today, postLegend, 2);
                //TRAITEMENT BACKEND -> ON RESPONSE
                post.setId(1);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(TeacherSelectingChildrenForPostActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.customlayout_selecting_activity_for_post, null);

                // SETTING CONTENT

                //region setting activity view
                yesrepas = (ImageView) mView.findViewById(R.id.yesrepas);
                yesjeux = (ImageView) mView.findViewById(R.id.yesjeux);
                yessport = (ImageView) mView.findViewById(R.id.yessport);
                yesdessin = (ImageView) mView.findViewById(R.id.yesdessin);
                yesapprentissage = (ImageView) mView.findViewById(R.id.yesapprentissage);
                yesmignon = (ImageView) mView.findViewById(R.id.yesmignon);
                //endregion

                TextView tv_decrire_post = (TextView) mView.findViewById(R.id.tv_decrire_post);
                final EditText et_postLegend = (EditText) mView.findViewById(R.id.et_postLegend);
                Button btnPublierPost = (Button) mView.findViewById(R.id.btnPublierPost);

                String text = "<font color=#888888>Veuillez </font> <font color=#3d0d28>décrire</font> <font color=#888888> ce post</font>";
                tv_decrire_post.setText(Html.fromHtml(text));

                btnPublierPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        post.setLegend(et_postLegend.getText().toString());
                        Toast toast = Toast.makeText(getApplicationContext(), "Publication réussie!", Toast.LENGTH_SHORT);
                        toast.show();
                        transition();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
            else{
                    return;
                }
                //endregion
            }
        });
    }

    private void compressImage() {
        int gear;
        gear = Luban.THIRD_GEAR;
        compressSingleListener(gear);
    }

    private void compressSingleListener(int gear)  {

        Luban.compress(getApplicationContext(),photoFileCompressed)
                .putGear(gear)
                .launch(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onSuccess(File file) {
                        Log.i("TAG", file.getAbsolutePath());
                        compressedPhotoURIforSendeing = Uri.fromFile(file).getPath();
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    //region SetContentChosenOnImageClick
    @Override
    public void onBackPressed() {
        videoView.setVisibility(View.INVISIBLE);
        transition();
    }

    private List<Child> loadChildren(){
        List<Child> childrenFromInternalStorage = new ArrayList<>();
        FileInputStream fis = null;
        //CODE

        try {
            fis = openFileInput(TeacherFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<Teacher>(){}.getType();
            Gson gson = new Gson();
            Teacher teacher = gson.fromJson(Json,type);
            for (Group group:teacher.getGroups()
                    ) {
                for (Child child:group.getChildren()
                        ) {
                    childrenFromInternalStorage.add(child);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return childrenFromInternalStorage;
    }

    public void repasMethod(View view){
        post.setLegend("Repas");
        Toast toast = Toast.makeText(getApplicationContext(), "Publication réussie!", Toast.LENGTH_SHORT); toast.show();
        transition();
    }

    public void jeuxMethod(View view){
        post.setLegend("Jeux");
        Toast toast = Toast.makeText(getApplicationContext(), "Publication réussie!", Toast.LENGTH_SHORT); toast.show();
        transition();
    }

    public void sportMethod(View view){
        post.setLegend("Sport");
        Toast toast = Toast.makeText(getApplicationContext(), "Publication réussie!", Toast.LENGTH_SHORT); toast.show();
        transition();
    }

    public void dessinMethod(View view){
        post.setLegend("Dessin");
        Toast toast = Toast.makeText(getApplicationContext(), "Publication réussie!", Toast.LENGTH_SHORT); toast.show();
        transition();
    }

    public void apprentissageMethod(View view){
        post.setLegend("Apprentissage");
        Toast toast = Toast.makeText(getApplicationContext(), "Publication réussie!", Toast.LENGTH_SHORT); toast.show();
        transition();
    }

    public void mignonMethod(View view){
        post.setLegend("Mignon");
        Toast toast = Toast.makeText(getApplicationContext(), "Publication réussie!", Toast.LENGTH_SHORT); toast.show();
        transition();
    }

    private void transition(){
        addPost();
        uploadFile();
        Intent intent = new Intent(this,LoggedTeacherMainActivity.class);
        intent.putExtra("position",1);
        startActivity(intent);
    }

    //endregion

    private void setImageInvisibile(int k){
        switch (k){
            case 0:
                yesrepas.setVisibility(View.INVISIBLE);
                break;
            case 1:
                yesjeux.setVisibility(View.INVISIBLE);
                break;
            case 2:
                yessport.setVisibility(View.INVISIBLE);
                break;
            case 3:
                yesdessin.setVisibility(View.INVISIBLE);
                break;
            case 4:
                yesapprentissage.setVisibility(View.INVISIBLE);
                break;
            case 5:
                yesmignon.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void addPost() {

        IPostsApi service = ApiClient.getClient().create(IPostsApi.class);
        //post.setTeacherId(teacher.Id);
        service.postPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.e("Posting Post", "success");
                post = response.body();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e("Posting Post", "failed");
            }
        });

        for (int i=0;i<IsChildSelected.size();i++) {

            if (!IsChildSelected.get(i)) {
                continue;
            }

            service.linkChildToPost(post.getId(), children.get(i).getId()).
                    enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.e("Linking child", "success");
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("Linking child", "failure");
                        }
                    });
        }
    }
    private void uploadFile(){

        File file = new File (compressedPhotoURIforSendeing);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        IPostsApi service = ApiClient.getClient().create(IPostsApi.class);
        service.AddPostShot(post.getId(),filePart).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.e("Upload", "success");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("Upload", "failure" + t.toString());
            }
        });
    }

    private void uploadVideo(){

        File videoFile = new File (videoUri.getPath());

        RequestBody requestVideoFile = RequestBody.create(MediaType.parse("video/*"),videoFile);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file_",videoFile.getName(), requestVideoFile);

        IPostsApi service = ApiClient.getClient().create(IPostsApi.class);
        service.AddPostVideo(post.getId(),filePart).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.e("Upload", "success");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("Upload", "failure" + t.toString());
            }
        });

    }

    private int getTheIndex(ArrayList<Boolean> booleanArrayList){
        int result=-1;
        int i=0;
        while(i<6 && (result==-1)){
            if (booleanArrayList.get(i)){
                result = i;
            }else{
                i++;
            }
        }
        return result;
    }
}






//region retrofit uplaod
  /* private void uploadFile(Uri fileUri) {
        // create upload service client


        IMediaApi service = ApiClient.getClient().create(IMediaApi.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file1 = new File(fileUri.getPath());
        File file = FileUtils.getFile(this,fileUri);

        // create RequestBody instance from file
        String sth = MediaType.parse(getContentResolver().getType(fileUri)).toString();
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = service.uploadPhoto(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.e("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload", t.getMessage());
            }
        });
    }



    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

*/
//endregion