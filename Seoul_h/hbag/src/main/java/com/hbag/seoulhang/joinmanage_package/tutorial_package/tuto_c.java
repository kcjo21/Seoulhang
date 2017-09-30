package com.hbag.seoulhang.joinmanage_package.tutorial_package;

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
import com.hbag.seoulhang.R;

import java.util.Locale;

public class tuto_c extends Fragment
{
    public tuto_c()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_tuto_c, container, false);
        Locale systemLocale = getContext().getResources().getConfiguration().locale;
        String strLanguage = systemLocale.getLanguage(); // ko

        if(strLanguage=="ko") {
            ImageView tuto_c = (ImageView) layout.findViewById(R.id.tuto_c);
            GlideDrawableImageViewTarget c = new GlideDrawableImageViewTarget(tuto_c);
            Glide.with(this).load(R.raw.tuto_3).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(c);
        }
        if(strLanguage=="en") {
            ImageView tuto_c = (ImageView) layout.findViewById(R.id.tuto_c);
            GlideDrawableImageViewTarget c = new GlideDrawableImageViewTarget(tuto_c);
            Glide.with(this).load(R.raw.tuto_3_en).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(c);

        }
        return layout;
    }
}
