package cn.iamding.chatrobot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.iamding.chatrobot.R;
import cn.iamding.chatrobot.model.OneMessage;

/**
 * Created by Xuding on 2015/12/13 20:16
 */
public class ChatListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<OneMessage> messageList;

    public ChatListAdapter(Context context, List<OneMessage> messageList) {
        mInflater = LayoutInflater.from(context);
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 来自网络的消息为1，本地发送的消息为0
     */
    @Override
    public int getItemViewType(int position) {
        OneMessage msg = messageList.get(position);
        return msg.getFrom() == OneMessage.From.NET ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OneMessage msg = messageList.get(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            if (msg.getFrom() == OneMessage.From.NET) {
                convertView = mInflater.inflate(R.layout.robot_chat, parent, false);
                viewHolder.speech = (TextView) convertView.findViewById(R.id.robot_speech);
                convertView.setTag(viewHolder);
            } else {
                convertView = mInflater.inflate(R.layout.my_chat, null);
                viewHolder.speech = (TextView) convertView.findViewById(R.id.my_speech);
                convertView.setTag(viewHolder);
            }

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.speech.setText(msg.getContent());
        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView speech;
    }

}

