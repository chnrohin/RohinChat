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

package com.github.chnrohin.rohinchat.common.design.recyclerview

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.FrameLayout
import com.github.chnrohin.rohinchat.R

/**
 * A RecyclerView that can display a header fill at the start of the list. The header can be set by
 * {@code app:suwHeader} in XML. Note that the header will not be inflated until a layout manager
 * is set.
 *
 * @author Rohin
 * @date 2018/11/14
 */
class HeaderRecyclerViewKt : RecyclerView {

    private val TAG = "HeaderRecyclerViewKt"

    private var mHeader : View? = null

    private var mHeaderRes = 0

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    fun init(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.HeaderRecyclerViewKt, defStyle, 0)
        mHeaderRes = typedArray.getResourceId(R.styleable.HeaderRecyclerViewKt_suwHeaderKt, 0)
        typedArray.recycle()
    }

    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent) {
        super.onInitializeAccessibilityEvent(event)

        val numberOfHeaders = if (mHeader != null) 1 else 0
        event.itemCount = event.itemCount - numberOfHeaders
        event.fromIndex = Math.max(event.fromIndex - numberOfHeaders, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            event.toIndex = Math.max(event.toIndex - numberOfHeaders, 0)
        }
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        if (layout != null && mHeader == null && mHeaderRes != 0) {
            val inflater = LayoutInflater.from(context)
            mHeader = inflater.inflate(mHeaderRes, this, false)
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        var localAdapter = adapter
        if (mHeader != null && localAdapter != null) {
            val headerAdapter = HeaderAdapter(localAdapter)
            headerAdapter.mHeader = mHeader
            localAdapter = headerAdapter
        }
        super.setAdapter(localAdapter)
    }

    private class HeaderViewHolder(itemView: View) : ViewHolder(itemView), DividerItemDecoration.DividedViewHolder {

        override fun isDividerAllowedAbove(): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun isDividerAllowedBelow(): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    interface DividerItemDecoration {

        interface DividedViewHolder {

            fun isDividerAllowedAbove(): Boolean

            fun isDividerAllowedBelow(): Boolean
        }
    }

    class HeaderAdapter<CVH : ViewHolder>(adapter : RecyclerView.Adapter<CVH>) : RecyclerView.Adapter<ViewHolder>() {

        companion object {
            private const val HEADER_VIEW_TYPE = Int.MAX_VALUE
        }

        private var mAdapter: RecyclerView.Adapter<CVH> = adapter

        var mHeader : View? = null

        private val mObserver = object : AdapterDataObserver() {

            override fun onChanged() {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                var localPositionStart = positionStart
                if (mHeader != null) {
                    localPositionStart++
                }
                notifyItemChanged(localPositionStart, itemCount)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                var localPositionStart = positionStart
                if (mHeader != null) {
                    localPositionStart++
                }
                notifyItemRangeInserted(localPositionStart, itemCount)
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                var localFromPosition = fromPosition
                var localToPosition = toPosition

                if (mHeader != null) {
                    localFromPosition++
                    localToPosition++
                }

                for (i in 0..itemCount) {
                    notifyItemMoved(localFromPosition + i, localToPosition + i)
                }
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                var localPositionStart = positionStart
                if (mHeader != null) {
                    localPositionStart++
                }
                notifyItemRangeRemoved(localPositionStart, itemCount)
            }
        }

        init {
            mAdapter.registerAdapterDataObserver(mObserver)
            setHasStableIds(mAdapter.hasStableIds())
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            if (viewType == HEADER_VIEW_TYPE) {
                val frameLayout = FrameLayout(parent.context)
                val layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT)
                frameLayout.layoutParams = layoutParams
                return HeaderViewHolder(frameLayout)
            } else {
                return mAdapter.onCreateViewHolder(parent, viewType)
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var localPosition = position
            if (mHeader != null) {
                localPosition--
            }

            if (holder is HeaderViewHolder) {
                if (mHeader == null) {
                    throw IllegalArgumentException("HeaderViewHolder cannot find mHeader")
                }
                if (mHeader?.parent != null) {
                    ((mHeader?.parent) as ViewGroup).removeView(mHeader)
                }
                val mHeaderParent = holder.itemView as FrameLayout
                mHeaderParent.addView(mHeader)
            } else {
                @Suppress("UNCHECKED_CAST")
                mAdapter.onBindViewHolder(holder as CVH, localPosition)
            }
        }

        override fun getItemViewType(position: Int): Int {
            var localPosition = position
            if (mHeader != null) {
                localPosition--
            }
            if (localPosition < 0) {
                return HEADER_VIEW_TYPE
            }
            return mAdapter.getItemViewType(localPosition)
        }

        override fun getItemCount(): Int {
            var count = mAdapter.itemCount
            if (mHeader != null) {
                count++
            }
            return count
        }

        override fun getItemId(position: Int): Long {
            var localPosition = position
            if (mHeader != null) {
                localPosition--
            }
            if (localPosition < 0) {
                return Long.MAX_VALUE
            }
            return mAdapter.getItemId(localPosition)
        }

        fun getWrappedAdapter() : RecyclerView.Adapter<CVH> = mAdapter
    }

}
