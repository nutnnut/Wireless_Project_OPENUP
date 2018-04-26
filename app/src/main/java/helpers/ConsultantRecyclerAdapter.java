package helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nut.wireless_project_openup.Chatroom;
import com.example.nut.wireless_project_openup.ConsultantListActivity;
import com.example.nut.wireless_project_openup.R;

import java.util.List;

import model.ConsultantInfo;

/**
 * Created by BAMBOOK on 4/18/2018.
 */

public class ConsultantRecyclerAdapter extends RecyclerView.Adapter<ConsultantRecyclerAdapter.ConsultantViewHolder> {

    private List<ConsultantInfo> list;
    private Context context;
    private String TAG = "ConsultantRecycler";

    public ConsultantRecyclerAdapter(List<ConsultantInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ConsultantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_consultant_recycler, parent, false);

        return new ConsultantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConsultantViewHolder holder, int position) {
        holder.consultantID = list.get(position).getConsultantID();
        holder.textViewName.setText(list.get(position).getName());
        holder.textViewGender.setText(list.get(position).getGender());
        holder.textViewAge.setText(list.get(position).getAge().toString());
        holder.textViewExpertise.setText(list.get(position).getExpertise());
    }

    @Override
    public int getItemCount() {
        Log.v(ConsultantRecyclerAdapter.class.getSimpleName(), "" + list.size());
        return list.size();
    }


    /**
     * ViewHolder class
     */
    public class ConsultantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewGender;
        public AppCompatTextView textViewAge;
        public AppCompatTextView textViewExpertise;
        private Integer consultantID;

        public ConsultantViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewGender = (AppCompatTextView) view.findViewById(R.id.textViewGender);
            textViewAge = (AppCompatTextView) view.findViewById(R.id.textViewAge);
            textViewExpertise = (AppCompatTextView) view.findViewById(R.id.textViewExpertise);
        }

        @Override
        public void onClick(View view) {
            Log.e(TAG, "onClick: DO U KNO DA WAE?");
            final Intent chatIntent = new Intent(context, Chatroom.class);
            final Bundle bundle = new Bundle();
            bundle.putInt("consultantID", consultantID);
            chatIntent.putExtras(bundle);
            context.startActivity(chatIntent);
        }
    }
}


