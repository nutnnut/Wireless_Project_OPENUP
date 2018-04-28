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

/**
 * This class is the recycler adapter for viewing chat messages in chatroom
 */
public class MessageRecycle extends RecyclerView.Adapter<MessageRecycle.MessageViewHolder> {

    List<Chatmessage> list;
    boolean isUser; //variable that identifies whether the logged in person is a user or consultant

    public MessageRecycle(List<Chatmessage> receivemessage, boolean isUser){
        this.list=receivemessage;
        this.isUser = isUser;
    }

    /**
     * This method identifies whether the message is sent or received
     * @param position
     * @return
     */
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

    /**
     * This method set layout according to the type of message
     * @param parent
     * @param viewType
     * @return
     */
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

    /**
     * This method bind holder content to message content
     * @param holder
     * @param position
     */
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

    /**
     * ViewHolder class for chat messages
     */
    public class MessageViewHolder extends RecyclerView.ViewHolder{
    public TextView textViewMessage;
    public TextView textViewTime;

    public MessageViewHolder(View view) {
        super(view);
        textViewMessage = (TextView) view.findViewById(R.id.receivetext);
        textViewTime = (AppCompatTextView) view.findViewById(R.id.timesent);
    }
    }

}
