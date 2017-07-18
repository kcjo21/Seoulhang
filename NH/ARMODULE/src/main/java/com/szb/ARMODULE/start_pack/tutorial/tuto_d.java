package com.szb.ARMODULE.start_pack.tutorial;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.szb.ARMODULE.R;

import java.util.Locale;

public class tuto_d extends Fragment
{
    public tuto_d()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_tuto_d, container, false);
        Locale systemLocale = getContext().getResources().getConfiguration().locale;
        String strLanguage = systemLocale.getLanguage(); // ko

        if(strLanguage=="ko") {
            ImageView tuto_d = (ImageView) layout.findViewById(R.id.tuto_d);
            GlideDrawableImageViewTarget d = new GlideDrawableImageViewTarget(tuto_d);
            Glide.with(this).load(R.raw.tuto_4).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(d);
        }
        if(strLanguage=="en") {
            ImageView tuto_d = (ImageView) layout.findViewById(R.id.tuto_d);
            GlideDrawableImageViewTarget d = new GlideDrawableImageViewTarget(tuto_d);
            Glide.with(this).load(R.raw.tuto_4_en).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(d);

        }
        return layout;
    }
}