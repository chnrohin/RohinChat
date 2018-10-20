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

package rohin.pers.rohinchat.chat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rohin.pers.rohinchat.R;
import rohin.pers.rohinchat.data.eneity.Message;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class MsgExRecyclerViewAdapter extends RecyclerView.Adapter<MsgExRecyclerViewAdapter.ViewHolder> {

    private final List<Message> mMessages;

    public MsgExRecyclerViewAdapter(List<Message> messages) {
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
        return new ViewHolder(
                inflater.inflate(R.layout.chat_recycle_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final View mItemView;
//        final TextView mDate;
        final ImageView mContactsProfilePic;
        final TextView mMsgContent;

        ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
//            mDate = itemView.findViewById(R.id.message_date);
            mContactsProfilePic = itemView.findViewById(R.id.iv_chat_contactsProfilePic);
            mMsgContent = itemView.findViewById(R.id.tv_chat_msgContent);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mDate.getText() + "'";
//        }
    }

}
