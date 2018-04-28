package com.example.macbook.splash.Adapters.Admin_Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Models.Admin;
import com.example.macbook.splash.Models.Message;
import com.example.macbook.splash.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by macbook on 28/04/2018.
 */

public class Messages_Adapter extends BaseAdapter{

    private List<Message> messageList;
    private Activity activity;

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy' à 'HH:mm", Locale.US);

    public Messages_Adapter(List<Message> messageList, Activity activity) {
        this.messageList = messageList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = activity.getLayoutInflater().inflate(R.layout.customlayout_admin_messages_list,null);
        
        Message message = messageList.get(position);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));


        TextView message_title = convertView.findViewById(R.id.message_title);
        TextView message_text = convertView.findViewById(R.id.message_text);
        TextView message_date = convertView.findViewById(R.id.message_date);
        
        final Typeface Roboto_Light_Font = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Light.ttf");
        final Typeface Roboto_Regular_Font = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Regular.ttf");
        
        message_title.setTypeface(Roboto_Regular_Font);
        message_text.setTypeface(Roboto_Light_Font);
        message_date.setTypeface(Roboto_Light_Font);
        
        message_title.setText(message.getTitle());
        message_text.setText(message.getContent());
        message_date.setText(dateFormat.format(message.getDate()));

        ImageButton messageResendButton = convertView.findViewById(R.id.messageResendButton);
        messageResendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                View mView = activity.getLayoutInflater().inflate(R.layout.customlayout_dialog_validating_action,null);

                TextView etes_vous_sur = (TextView) mView.findViewById(R.id.etes_vous_sur);
                CardView cardView_ok = (CardView) mView.findViewById(R.id.cardview_ok);
                CardView cardView_not_ok = (CardView) mView.findViewById(R.id.cardview_not_ok);

                etes_vous_sur.setText("Voulez-vous réenvoyer le message?");

                etes_vous_sur.setTypeface(Roboto_Light_Font);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                cardView_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                        /**
                         * Request to Resend the Message 
                         * RESPONSE 200 OK :    - dialog.dismiss();
                         *                      - addMessageToInternalStorage(message); //we add the resent message to the list as if he s a new message
                         *                      - Toast.makeText(MainActivity.this, "Message réenvoyé !", Toast.LENGTH_SHORT).show();
                         *
                         *
                         */
                        
                        //dialog.dismiss();
                    }
                });

                cardView_not_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        ImageButton messageDeleteButton = convertView.findViewById(R.id.messageDeleteButton);
        messageDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                View mView = activity.getLayoutInflater().inflate(R.layout.customlayout_dialog_validating_action,null);

                TextView etes_vous_sur = (TextView) mView.findViewById(R.id.etes_vous_sur);
                CardView cardView_ok = (CardView) mView.findViewById(R.id.cardview_ok);
                CardView cardView_not_ok = (CardView) mView.findViewById(R.id.cardview_not_ok);

                etes_vous_sur.setText("Voulez-vous supprimer le message?");

                etes_vous_sur.setTypeface(Roboto_Light_Font);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                cardView_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                        /**
                         * Request to Delete the Message 
                         * RESPONSE 200 OK :    - dialog.dismiss();
                         *                      - removeMessageFromInternalStorage(message);
                         *                      - Toast.makeText(MainActivity.this, "Message supprimé !", Toast.LENGTH_SHORT).show();
                         *
                         *
                         */

                        //dialog.dismiss();
                    }
                });

                cardView_not_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        return convertView;
    }


    
    private void addMessageToInternalStorage(Message message){
        Admin admin = loadAdminFromInternalStorage();
        messageList.add(message);
        notifyDataSetChanged();
        saveKindergartenMessagesInTheInternalStorage(messageList,admin.getKindergartenId());
    }
    
    private void removeMessageFromInternalStorage(Message message){
        Admin admin = loadAdminFromInternalStorage();
        messageList.remove(message);
        notifyDataSetChanged();
        saveKindergartenMessagesInTheInternalStorage(messageList,admin.getKindergartenId());
    }
    
    //region Methods
    private void saveObjectAsFileInTheInternalStorage(Object object,String file_name){
        File file = new File(activity.getFilesDir() + "/"+file_name);
        Log.e("Admin","new File");

        file.delete();
        Log.e("Admin","File Deleted");

        FileOutputStream fos = null;

        try {
            fos = activity.openFileOutput(file_name, Context.MODE_PRIVATE);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                    .create();
            String json = gson.toJson(object);
            fos.write(json.getBytes());
            Log.e("Admin","File Saved");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void saveKindergartenMessagesInTheInternalStorage(List<Message> messages,int kindergarten_id){
        saveObjectAsFileInTheInternalStorage(messages,"kg_messages_"+kindergarten_id+".dat");
    }

    private Admin loadAdminFromInternalStorage(){
        FileInputStream fis = null;
        //CODE

        try {
            fis = activity.openFileInput("admin.dat");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<Admin>(){}.getType();
            Gson gson = new GsonBuilder()
                    .create();
            Admin admin = gson.fromJson(Json,type);
            return admin;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //endregion
        
}
