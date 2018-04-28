package helpers;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nut.wireless_project_openup.Chatroom;
import com.example.nut.wireless_project_openup.R;

import java.util.List;

import model.ConsultantInfo;
import model.Information;

/**
 * This class is a recycler adapter for viewing user list in consultant inbox
 */

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder> {

    private List<Information> list;
    private Context context;

    public UserRecyclerAdapter(List<Information> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * This method sets layout for user recycler holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public UserRecyclerAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler, parent, false);

        return new UserRecyclerAdapter.UserViewHolder(itemView);
    }

    /**
     * This method binds user name and gender to holder content views
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(UserRecyclerAdapter.UserViewHolder holder, int position) {
        holder.userID = list.get(position).getUserID();
        holder.textViewName.setText(list.get(position).getDisplayName());
        holder.textViewGender.setText(list.get(position).getGender());
    }

    @Override
    public int getItemCount() {
        Log.v(ConsultantRecyclerAdapter.class.getSimpleName(), "" + list.size());
        return list.size();
    }


    /**
     * ViewHolder class for user list
     */
    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewGender;
        private Integer userID;

        public UserViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewNameUser);
            textViewGender = (AppCompatTextView) view.findViewById(R.id.textViewGenderUser);
        }

        @Override
        public void onClick(View view) {
            //Create intent and bundle to send user ID to chatroom
            final Intent chatIntent = new Intent(context, Chatroom.class);
            final Bundle bundle = new Bundle();
            bundle.putInt("userID", userID);
            chatIntent.putExtras(bundle);
            context.startActivity(chatIntent);
        }
    }
}