/*
 * Copyright © Rohin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.chnrohin.rohinchat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.chnrohin.rohinchat.R;
import com.github.chnrohin.rohinchat.R2;
import com.github.chnrohin.rohinchat.common.util.DateUtils;
import com.github.chnrohin.rohinchat.data.entity.Message;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public final class ChatWithRecyclerViewAdapter
        extends RecyclerView.Adapter<ChatWithRecyclerViewAdapter.ViewHolder> {

    private final List<Message> mMessages;

    /**
     * 数据加载计数器
     */
    private int i = -1;

    private long msgPreviousDate;

    public ChatWithRecyclerViewAdapter(List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        // 反转
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        i++;
        if (mMessages.get(i).getFrom() == Message.RECEIVE) {
            return new ViewHolder(inflater.inflate(R.layout.chat_recycle_message_receive, parent
                    , false));
        } else {
            return new ViewHolder(inflater.inflate(R.layout.chat_recycle_message_send, parent
                    , false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message msg = mMessages.get(i);

        long msgDate = msg.getDate();
        String timestamp;
        timestamp = msgPreviousDate < msgDate
                ? DateUtils.msgMoreThanInterval(msgPreviousDate, msgDate)
                : DateUtils.msgMoreThanInterval(msgDate, msgPreviousDate);
        if (timestamp.length() > 0) {
            holder.mMsgDateTv.setVisibility(View.VISIBLE);
            holder.mMsgDateTv.setText(timestamp);
        }

        String msgContent = msg.getContent();
        holder.mMsgContentTv.setText(msgContent);

        msgPreviousDate = msgDate;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.tv_chat_msgDate)
        TextView mMsgDateTv;
        @BindView(R2.id.iv_chat_contactsProfilePic)
        ImageView mContactsProfilePicIv;
        @BindView(R2.id.tv_chat_msgContent)
        TextView mMsgContentTv;
//        TextView mDate;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            mDate = itemView.findViewById(R.id.message_date);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mDate.getText() + "'";
//        }
    }

}
