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

package rohin.pers.rohinchat.social.presenter;

import java.util.List;

import rohin.pers.rohinchat.data.eneity.Contacts;
import rohin.pers.rohinchat.social.contract.ContactsContract;
import rohin.pers.rohinchat.social.model.ContactsModel;
import rohin.pers.rohinchat.social.model.ContactsModelImpl;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class ContactsPresenter implements ContactsContract.Presenter {

    private ContactsContract.View mView;

    private ContactsModel mModel;

    private boolean mFirstLoad = true;

    public ContactsPresenter(ContactsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mModel = new ContactsModelImpl();
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
