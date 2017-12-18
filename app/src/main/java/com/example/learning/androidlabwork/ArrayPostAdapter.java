package com.example.learning.androidlabwork;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learning.androidlabwork.Model.Post;

import java.util.List;

/**
 * Created by Rabab Chahboune on 12/18/2017.
 */


public class ArrayPostAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private List<Post> posts;
    public ArrayPostAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Post[] posts) {
        super(context, resource, posts);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.post_item, null);
            holder.title = (TextView)convertView.findViewById(R.id.showTitle);
            holder.description = (TextView)convertView.findViewById(R.id.showDescription);
            holder.image=  (ImageView) convertView.findViewById(R.id.showImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(posts.get(position).getTitle());
        holder.description.setText(posts.get(position).getDescription());
        //holder.image.setImageResource(posts.get(position).ge());
        return convertView;
    }

    static class ViewHolder
    {
        public TextView title ;
        public TextView description;
        public ImageView image;
    }
}
