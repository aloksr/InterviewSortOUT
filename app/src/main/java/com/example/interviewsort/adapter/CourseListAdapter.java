package com.example.interviewsort.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.interviewsort.OnItemCLickListener;
import com.example.interviewsort.R;
import com.example.interviewsort.model.CourseListModel;

import java.util.List;

/**
 * Created by alok on 17/6/19.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {
    private OnItemCLickListener mListener;
    private Context context;
    private List<CourseListModel> courseListModels;


    public CourseListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(courseListModels.get(position));

    }

    public void setOnItemClickListener(OnItemCLickListener listener) {

        mListener = listener;

    }

    public void setData(List<CourseListModel> courseListModels) {
        this.courseListModels = courseListModels;
        notifyDataSetChanged();

    }

    public CourseListModel getItemAt(int pos) {
        if (courseListModels != null && courseListModels.size() > pos) {
            return courseListModels.get(pos);
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return courseListModels == null ? 0 : courseListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private TextView txvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            txvTitle = itemView.findViewById(R.id.txvTitle);
            cardView.setOnClickListener(this);
            cardView.setOnClickListener(this);

        }

        public void bindData(CourseListModel courseListModel) {
            txvTitle.setText(courseListModel.getName());
            cardView.setCardBackgroundColor(Color.parseColor(TextUtils.isEmpty(courseListModel.getBg_color()) ? "#007be0" : courseListModel.getBg_color()));
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}
