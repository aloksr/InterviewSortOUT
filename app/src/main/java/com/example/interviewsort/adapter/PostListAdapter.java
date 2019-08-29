package com.example.interviewsort.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.interviewsort.OnItemCLickListener;
import com.example.interviewsort.R;
import com.example.interviewsort.model.PostListModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alok on 17/6/19.
 */

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {
    private OnItemCLickListener mListener;
    private Context context;
    private List<PostListModel> courseListModels;


    public PostListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(courseListModels.get(position));
    }

    public void setOnItemClickListener(OnItemCLickListener listener) {
        mListener = listener;
    }

    public PostListModel getItemAt(int pos) {
        if (courseListModels != null && courseListModels.size() > pos) {
            return courseListModels.get(pos);
        }
        return null;
    }


    public void setData(List<PostListModel> courseListModels) {
        this.courseListModels = courseListModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return courseListModels == null ? 0 : courseListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivPostImage;
        private TextView txvTitle;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            txvTitle = itemView.findViewById(R.id.tvPostName);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getAdapterPosition());
            }
        }


        public void bindData(PostListModel courseListModel) {
            txvTitle.setText(courseListModel.getTitle());
            if (!TextUtils.isEmpty(courseListModel.getImage())) {
                Picasso.with(itemView.getContext()).load(courseListModel.getImage()).placeholder(R.drawable.placeholder_others).into(ivPostImage);
            } else {
                ivPostImage.setImageResource(R.drawable.placeholder_others);
            }

        }

    }
}
