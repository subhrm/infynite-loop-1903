package com.stg.vms;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stg.vms.model.InsideVisitor;
import com.stg.vms.util.VMSUtil;

import java.util.List;

public class VisitorInsideAdaptor extends RecyclerView.Adapter<VisitorInsideAdaptor.ViewHolder> {
    private List<InsideVisitor> allVisitors;
    private VisitorClickListener listener;

    public VisitorInsideAdaptor(List<InsideVisitor> allVisitors, VisitorClickListener listener) {
        this.allVisitors = allVisitors;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visitor_inside, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setupView(allVisitors.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return allVisitors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView visitorImage;
        final TextView visitorName, visitorType, exitTime;
        final Button callVisitorBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            visitorImage = mView.findViewById(R.id.vi_visitorImg);
            visitorName = mView.findViewById(R.id.vi_visitorName);
            visitorType = mView.findViewById(R.id.vi_visitorType);
            exitTime = mView.findViewById(R.id.vi_exitTime);
            callVisitorBtn = mView.findViewById(R.id.vi_btn_callVisitor);
        }
        public void setupView(final InsideVisitor visitor, final VisitorClickListener listener) {
            Picasso.get().load(visitor.getPhotoUrl()).placeholder(R.drawable.ic_person).into(visitorImage);
            visitorName.setText(visitor.getName());
            visitorType.setText(visitor.getVisitorType());
            exitTime.setText(VMSUtil.formatStringDate(visitor.getExpectedOutTime(), "yyyy-MM-dd HH:mm:ss", "hh:mm a"));
            callVisitorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCall(visitor.getMobile());
                }
            });
        }
    }
    public interface VisitorClickListener {
        void onCall(String mobileNumber);
    }
}
