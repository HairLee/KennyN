package com.haipham.kennyn.adater;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haipham.kennyn.R;
import com.haipham.kennyn.model.Caterogy;

import java.util.List;


public class CaterogyAdapter extends RecyclerView.Adapter<CaterogyAdapter.MyViewHolder> {

    private Context mContext;
    private List<Caterogy> caterogyList;
    private OnRemoveCallBack mOnRemoveCallBack;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, imvRemove;
        public LinearLayout lnItemLike;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            count = (TextView) view.findViewById(R.id.count);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnRemoveCallBack.onItemClick(getLayoutPosition());
                }
            });
        }

    }


    public CaterogyAdapter(Context mContext, List<Caterogy> albumList, OnRemoveCallBack pOnRemoveCallBack) {
        this.mContext = mContext;
        this.caterogyList = albumList;
        mOnRemoveCallBack = pOnRemoveCallBack;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_like_content, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Caterogy caterogy = caterogyList.get(position);
        holder.title.setText(caterogy.getItemCount() + " Videos");

        if (position == 0) {
            Glide.with(mContext)
                    .load(Uri.parse("file:///android_asset/flag.png"))
                    .into(holder.thumbnail);
            holder.count.setText("ALL");
        } else if (position == 1) {
            Glide.with(mContext)
                    .load(Uri.parse("file:///android_asset/flag.png"))
                    .into(holder.thumbnail);
            holder.count.setText("Từ Vựng Và Ngữ Pháp");
        } else if (position == 2) {
            Glide.with(mContext)
                    .load(Uri.parse("file:///android_asset/flag.png"))
                    .into(holder.thumbnail);
            holder.count.setText("Luyện Nghe/ Listening Skill");
        } else if (position == 3) {
            Glide.with(mContext)
                    .load(Uri.parse("file:///android_asset/flag.png"))
                    .into(holder.thumbnail);
            holder.count.setText("Phrasal Verbs/ Cụm Từ");
        } else {
            Glide.with(mContext)
                    .load(Uri.parse("file:///android_asset/flag.png"))
                    .into(holder.thumbnail);
            holder.count.setText("Others");
        }
    }

    @Override
    public int getItemCount() {
        return caterogyList.size();
    }

    public interface OnRemoveCallBack {
        public void remove(int pos);

        public void onItemClick(int pos);
    }
}
