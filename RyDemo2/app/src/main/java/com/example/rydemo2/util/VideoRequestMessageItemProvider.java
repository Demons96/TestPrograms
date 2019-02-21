package com.example.rydemo2.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rydemo2.R;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

/**
 * 消息展示
 *
 * @author yunPeng.Gong
 * @date 19.1.31
 */
@ProviderTag(messageContent = VideoRequestMessage.class)
public class VideoRequestMessageItemProvider extends IContainerItemProvider.MessageProvider<VideoRequestMessage> {

    class ViewHolder {
        TextView message;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rong_yun_video_message, null);
        ViewHolder holder = new ViewHolder();
        holder.message = view.findViewById(R.id.tv_text);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int i, VideoRequestMessage content, UIMessage message) {
        ViewHolder holder = (ViewHolder) v.getTag();

        holder.message.setText(content.getContent());
    }

    @Override
    public Spannable getContentSummary(VideoRequestMessage data) {
        return new SpannableString("您有一条视频消息");
    }

    @Override
    public void onItemClick(View view, int i, VideoRequestMessage customizeMessage, UIMessage uiMessage) {
    }

}