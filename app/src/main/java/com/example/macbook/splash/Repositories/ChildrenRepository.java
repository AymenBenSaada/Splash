package com.example.macbook.splash.Repositories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IChildrenApi;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;

import static org.apache.commons.io.IOUtils.copy;

/**
 * Created by macbook on 12/02/2018.
 */

public class ChildrenRepository {
    private IChildrenApi _apiClient = ApiClient.getClient().create(IChildrenApi.class) ;
    private Child result;
    private List<Child> results;

    public @Nullable
    Bitmap getChildProfilePicture(final Context context , final int id) {
        // logic for getting profile picture by ID remove context cause you dont need it
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.nader);
        return icon ;};



}
