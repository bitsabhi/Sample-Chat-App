package com.docsapp.sample.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utils {

    public static String formatDateTime(long createdAt) {
        Timestamp timestamp = new Timestamp(createdAt);

        Date date = new Date(timestamp.getTime());

        // S is the millisecond
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss:S");

        return simpleDateFormat.format(timestamp);
//        System.out.println(simpleDateFormat.format(date));
    }

    public static void displayRoundImageFromUrl(Context mContext, String profileUrl, ImageView profileImage) {
        if (!TextUtils.isEmpty(profileUrl)) {
            Glide.with(mContext).load(profileImage)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profileImage);

        }

    }
}
