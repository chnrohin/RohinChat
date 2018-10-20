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

package rohin.pers.rohinchat.social.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.List;

import rohin.pers.rohinchat.R;
import rohin.pers.rohinchat.data.eneity.Contacts;
import rohin.pers.rohinchat.common.design.widget.DrawableTextView;
import rohin.pers.rohinchat.common.util.ImageUtils;
import rohin.pers.rohinchat.social.view.ContactsFragment;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {

    private List<Contacts> mContactsList;

    private ContactsFragment.OnContactItemClickListener mListener;

    public ContactRecyclerViewAdapter(List<Contacts> contactsList,
                               ContactsFragment.OnContactItemClickListener listener) {
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
                inflater.inflate(R.layout.social_recycle_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mContacts = mContactsList.get(position);
        byte[] bytes = mContactsList.get(position).getProfilePic();
        if (bytes != null && bytes.length > 0) {
            WeakReference<Bitmap> userProfilePic =
                    new WeakReference<>(ImageUtils.convertBytesToBitmap(bytes, null));
            holder.mContactsView.setBitmap(userProfilePic.get());
        }
        holder.mContactsView.setText(mContactsList.get(position).getNickname());

        holder.mItemView.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onContactItemClick(holder.mContacts);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContactsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final View mItemView;
        final DrawableTextView mContactsView;
        Contacts mContacts;

        ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mContactsView = itemView.findViewById(R.id.dtv_main_contacts);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContactsView.getText() + "'";
        }

    }

}
