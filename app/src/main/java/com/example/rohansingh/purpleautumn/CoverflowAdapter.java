package com.example.rohansingh.purpleautumn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.zip.Inflater;

public class CoverflowAdapter extends BaseAdapter{



    Context context;
    LayoutInflater layoutInflater;
    int[] images={
            R.drawable.oone,
            R.drawable.otwo,
            R.drawable.aone,
            R.drawable.atwo,
            R.drawable.btwo,
            R.drawable.bone,
            R.drawable.abone,
            R.drawable.abtwo

    };

    public CoverflowAdapter(Context context){

        this.context=context;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        layoutInflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.coverflow_item, null);
        ImageView cover_image = (ImageView)view.findViewById(R.id.coverflowImageView);
        cover_image.setImageResource(images[i]);
        return view;
    }
}
