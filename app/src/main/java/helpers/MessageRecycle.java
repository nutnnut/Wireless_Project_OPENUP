package helpers;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nut.wireless_project_openup.R;

import java.util.List;

import model.Chatmessage;

public class MessageRecycle extends RecyclerView.Adapter<MessageRecycle.MessageViewHolder> {

    List<Chatmessage> list;

    public MessageRecycle(List<Chatmessage> receivemessage){
        this.list=receivemessage;
    }

    public MessageRecycle.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_receive, parent, false);

        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.textViewName.setText(list.get(position).getUserID().toString());
        holder.textViewTime.setText(list.get(position).getMessageTime());
        holder.textViewMessage.setText(list.get(position).getMessageText());
    }

    @Override
    public int getItemCount() {
        Log.v(MessageRecycle.class.getSimpleName(), "" + list.size());
        return list.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
    public TextView textViewName;
    public TextView textViewMessage;
    public ImageView UserPic;
    public TextView textViewTime;

    public MessageViewHolder(View view) {
        super(view);
        textViewName = (TextView) view.findViewById(R.id.receiveusername);
        textViewMessage = (TextView) view.findViewById(R.id.receivetext);
        UserPic = (ImageView) view.findViewById(R.id.receivepic);
        textViewTime = (AppCompatTextView) view.findViewById(R.id.receivetimesent);
    }
    }

}
