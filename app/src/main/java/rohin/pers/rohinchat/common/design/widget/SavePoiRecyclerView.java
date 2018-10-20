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

package rohin.pers.rohinchat.common.design.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class SavePoiRecyclerView extends RecyclerView {

    private int lastPosition = 0;

    private int lastOffset = 0;

    public SavePoiRecyclerView(Context context) {
        this(context, null);
    }

    public SavePoiRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SavePoiRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        new SavePoi().scrollToPosition();
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public int getLastOffset() {
        return lastOffset;
    }

    public void setLastOffset(int lastOffset) {
        this.lastOffset = lastOffset;
    }

    class SavePoi extends OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            savePosition();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            savePosition();
        }

        private void savePosition() {
            LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
            View istViewOnScreen = layoutManager.getChildAt(0);
            if (istViewOnScreen != null) {
                lastPosition = layoutManager.getPosition(istViewOnScreen);
            }
        }

        private void scrollToPosition() {
            if (getLayoutManager() != null && lastPosition >= 0) {
                getLayoutManager().scrollToPosition(lastPosition);
            }
        }
    }

}
