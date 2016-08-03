package com.example.user.youtubevideolist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by user on 2016-07-20.
 */
public class Youtube_Adapter extends ArrayAdapter<Video_array> {
    private static Activity mcontext;
    ArrayList<Video_array> Order;
    int Resource;
    Context context;
    LayoutInflater inflater;

    public Youtube_Adapter(Context context, int resource, ArrayList<Video_array> Order) {
        super(context, resource, Order);
        this.Order = Order;
        Resource = resource;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(Resource, null);
            holder = new ViewHolder();
            holder.Title = (TextView) convertView.findViewById(R.id.title);
            holder.Descripation = (TextView) convertView.findViewById(R.id.description);
            holder.image = (ImageView) convertView.findViewById(R.id.video_thumb);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.Title.setText(Order.get(position).getVIdeotitle());

        holder.Descripation.setText(Order.get(position).getVIdeotitle());
        ImageLoader.getInstance().displayImage(Order.get(position).getVideothumb(), holder.image);

        return convertView;

    }

    static class ViewHolder {

        public TextView Title;
        public TextView Descripation;
        public ImageView image;

    }
}
