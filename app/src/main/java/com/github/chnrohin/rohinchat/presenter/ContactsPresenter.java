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

package com.github.chnrohin.rohinchat.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import com.github.chnrohin.rohinchat.base.BasePresenter;
import com.github.chnrohin.rohinchat.data.entity.Contacts;
import com.github.chnrohin.rohinchat.model.ContactsModel;
import com.github.chnrohin.rohinchat.model.ContactsModelImpl;
import com.github.chnrohin.rohinchat.contract.ContactsContract;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public final class ContactsPresenter extends BasePresenter<ContactsContract.View>
        implements ContactsContract.Presenter {

    @NonNull
    private ContactsModel mModel = new ContactsModelImpl();

    private boolean mFirstLoad = true;

    public ContactsPresenter() {

    }

    @Override
    public void subscribe() {
        loadingContactsList(false);
    }

    @Override
    public void unsubscribe() {
        mModel.clear();
    }

    @Override
    public void loadingContactsList(boolean forceUpdate) {
        loadingContactsList(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadingContactsList(final boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mView.showLoadingIndicator(true);
        }
        if (forceUpdate) {
            mView.refresh();
        }
        List<Contacts> contactsList = mModel.fetchContactsList();
        if (contactsList != null) {
            mView.showContactsList(contactsList);
        } else {
            mView.showNoData();
        }
    }
}
