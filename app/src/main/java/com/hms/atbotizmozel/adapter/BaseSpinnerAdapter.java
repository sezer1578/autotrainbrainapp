package com.hms.atbotizmozel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hms.atbotizmozel.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Batuhan on 18.01.2018.
 */

public class BaseSpinnerAdapter extends ArrayAdapter {

    private Context context;
    private List<String> objects;
    private List<String> imgs;

    public BaseSpinnerAdapter(Context context, int textViewResourceId,
                              List<String> objects, List<String> imgs) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
        this.imgs = imgs;
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.item_base_spinner, parent, false);

        TextView itemTxt = (TextView) layout.findViewById(R.id.itemTxt);
        ImageView itemImg = (ImageView) layout.findViewById(R.id.itemImg);

        if (imgs == null) {
            itemImg.setVisibility(View.GONE);
        } else {
            itemImg.setVisibility(View.VISIBLE);
            String img = imgs.get(position);
            Picasso.with(context).load(img).into(itemImg);
        }
        itemTxt.setText(objects.get(position));

        return layout;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return objects.size();
    }
}
