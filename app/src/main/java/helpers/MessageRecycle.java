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
    boolean isUser;

    public MessageRecycle(List<Chatmessage> receivemessage, boolean isUser){
        this.list=receivemessage;
        this.isUser = isUser;
    }

    public int getItemViewType(int position){
        Chatmessage chatmessage = list.get(position);
        int isSender;
        if(isUser){
            if(chatmessage.getSender()){
                isSender = 1;
            }
            else{
                isSender = 0;
            }
        }
        else{
            if(chatmessage.getSender()){
                isSender = 0;
            }
            else{
                isSender = 1;
            }
        }
        return isSender;
    }

    public MessageRecycle.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == 1){

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_receive, parent, false);

        }
        else{
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_send, parent, false);
        }

        return new MessageViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.textViewTime.setText(list.get(position).getMessageTime());
        holder.textViewMessage.setText(list.get(position).getMessageText());
    }

    @Override
    public int getItemCount() {
        Log.v(MessageRecycle.class.getSimpleName(), "" + list.size());
        return list.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
    //public TextView textViewName;
    public TextView textViewMessage;
    //public ImageView UserPic;
    public TextView textViewTime;

    public MessageViewHolder(View view) {
        super(view);
        //textViewName = (TextView) view.findViewById(R.id.receiveusername);
        textViewMessage = (TextView) view.findViewById(R.id.receivetext);
        //UserPic = (ImageView) view.findViewById(R.id.receivepic);
        textViewTime = (AppCompatTextView) view.findViewById(R.id.timesent);
    }
    }

}
