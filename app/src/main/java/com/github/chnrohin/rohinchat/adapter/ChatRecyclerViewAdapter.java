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

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.chnrohin.rohinchat.R;
import com.github.chnrohin.rohinchat.R2;
import com.github.chnrohin.rohinchat.frgms.ChatFragment;
import com.github.chnrohin.rohinchat.data.entity.SimpleChat;
import com.github.chnrohin.rohinchat.common.util.DateUtils;
import com.github.chnrohin.rohinchat.common.util.ImageUtils;
import com.github.chnrohin.rohinchat.common.util.LogUtils;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SimpleChat} and makes a call to the
 * specified {@link ChatFragment.OnSimpleChatItemClickListener}.
 * TODO: Replace the implementation with code for your data TYPE.
 *
 * @author Rohin
 * @date 2018/7/15
 */
public final class ChatRecyclerViewAdapter
        extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {

    /**
     * 聊天列表life cycle需与Application一致
     */
    private List<SimpleChat> mSimpleChats;
    /**
     * Activity回调
     */
    private final ChatFragment.OnSimpleChatItemClickListener mListener;

    public ChatRecyclerViewAdapter(List<SimpleChat> simpleChats,
                                   @NonNull ChatFragment.OnSimpleChatItemClickListener listener) {
        mSimpleChats = simpleChats;
        mListener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(
                inflater.inflate(R.layout.chat_recycle_simplechat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        SimpleChat simpleChat = mSimpleChats.get(position);

        byte[] bytes = simpleChat.getContacts().getProfilePic();
        if (bytes != null && bytes.length > 0) {
            // 弱引用 - 及时释放图像资源
            WeakReference<Bitmap> bitmapWeakReference =
                    new WeakReference<>(ImageUtils.convertBytesToBitmap(bytes, null));
            holder.mContactsProfilePicIv.setImageBitmap(bitmapWeakReference.get());
        }

        holder.mContactsNicknameTv.setText(simpleChat.getContacts().getNickname());

        holder.mTimestampTv.setText(DateUtils.getTimestamp(simpleChat.getMessage().getDate()));

        holder.mMsgDigestTV.setText(simpleChat.getMessage().getContent());

        // ChatWith Object
        holder.itemView.setOnClickListener(v -> mListener.onSimpleChatItemClick(simpleChat));

        holder.itemView.setOnLongClickListener((v) -> {
//            ContextMenu contextMenu;
            PopupMenu popupMenu
                    = new PopupMenu(holder.itemView.getContext(), holder.itemView);
            popupMenu.getMenuInflater()
                    .inflate(R.menu.main_recylerview_simple_chat, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_marked_as_unread:
                        LogUtils.i("标为未读");
                        break;
                    case R.id.action_promote_chat:
                        LogUtils.i("置顶聊天");
                        break;
                    case R.id.action_delete_chat:
                        LogUtils.i("删除聊天");
                        break;
                    default:
                        return false;
                }
                return true;
            });
            popupMenu.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mSimpleChats.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.iv_chat_contactsProfilePic)
        ImageView mContactsProfilePicIv;
        @BindView(R2.id.tv_chat_contactsNickname)
        TextView mContactsNicknameTv;
        @BindView(R2.id.tv_chat_timestamp)
        TextView mTimestampTv;
        @BindView(R2.id.tv_chat_msgDigest)
        TextView mMsgDigestTV;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContactsNicknameTv.getText() + "'";
        }

    }

}
