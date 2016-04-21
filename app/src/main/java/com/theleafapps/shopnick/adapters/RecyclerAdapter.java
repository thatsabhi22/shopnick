package com.theleafapps.shopnick.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.Collections;
import java.util.List;

/**
 * Created by aviator on 14/04/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    Context mContext;
    private LayoutInflater inflater;
    List<Info> data = Collections.emptyList();
    private ImageLoader mImageLoader;

    public RecyclerAdapter(Context context, List<Info> data) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.single_row,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Info current = data.get(position);

        mImageLoader = MySingleton.getInstance(mContext).getImageLoader();

        holder.titleView.setText(current.getName());
        holder.descView.setText(current.getDesc());
        holder.imageView.setImageUrl(current.getImage(),mImageLoader);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        NetworkImageView imageView;
        TextView titleView,descView;
        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = (NetworkImageView) itemView.findViewById(R.id.thumb);
            titleView = (TextView) itemView.findViewById(R.id.itemTitle);
            descView  = (TextView) itemView.findViewById(R.id.desc);

        }
    }
}
