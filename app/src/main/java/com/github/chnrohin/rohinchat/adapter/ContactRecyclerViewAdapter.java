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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.chnrohin.rohinchat.R;
import com.github.chnrohin.rohinchat.R2;
import com.github.chnrohin.rohinchat.data.entity.Contacts;
import com.github.chnrohin.rohinchat.common.design.widget.DrawableTextView;
import com.github.chnrohin.rohinchat.common.util.ImageUtils;
import com.github.chnrohin.rohinchat.frgms.ContactsFragment;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public final class ContactRecyclerViewAdapter
        extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {

    private List<Contacts> mContactsList;

    private ContactsFragment.OnContactItemClickListener mListener;

    public ContactRecyclerViewAdapter(List<Contacts> contactsList,
                                      @NonNull ContactsFragment.OnContactItemClickListener listener) {
        mContactsList = contactsList;
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
                inflater.inflate(R.layout.social_recycler_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacts contacts =  mContactsList.get(position);
        if (null != contacts) {
            byte[] bytes = mContactsList.get(position).getProfilePic();
            if (bytes != null && bytes.length > 0) {
                Bitmap userProfilePic = ImageUtils.convertBytesToBitmap(bytes, null);
                holder.mContactsView.setBitmap(userProfilePic);
                userProfilePic.recycle();
            }
            holder.mContactsView.setText(mContactsList.get(position).getNickname());

            holder.itemView.setOnClickListener(v -> mListener.onContactItemClick(contacts));
        }
    }

    @Override
    public int getItemCount() {
        return mContactsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.dtv_main_contacts)
        DrawableTextView mContactsView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContactsView.getText() + "'";
        }

    }

}
