package com.android.expandview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.expandview.adapter.SpacesItemDecoration;
import com.android.expandview.app.LoadManager;
import com.gogh.okrxretrofit.util.Logger;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: 自定义的 RecyclerView ，可以实现放大缩小item，实现加载更多，滑动不加载</p>
 * <p> Created by <b>高晓峰</b> on 6/12/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 6/12/2017 do fisrt create. </li>
 */

public class ExRecyclerView extends RecyclerView {

    private static final String TAG = "ExRecyclerView";

    private int mSelectedPosition = 0;

    private LoadManager loadManager;

    private int offsetNum = 3;

    private int mLastVisibleItemPosition; //最后一个角标位置

    public OnScrollListener onScrollChangeListener = new OnScrollListener() {

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mLastVisibleItemPosition = getLastVisibleItemPosition(recyclerView);
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Logger.d(TAG, " onScrollStateChanged");
            switch (newState) {
                case SCROLL_STATE_IDLE:
                    if (mLastVisibleItemPosition + offsetNum >= recyclerView.getAdapter().getItemCount()) {
                        Logger.d(TAG, " onScrollStateChanged::loadmore");
                        if (loadManager != null) {
                            loadManager.loadMore();
                        }
                    }
                    break;
                case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
                case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under
                    break;
            }
        }
    };

    public ExRecyclerView(Context context) {
        this(context, null);
    }

    public ExRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setClipToPadding(false);
        //启用子视图排序功能
        setChildrenDrawingOrderEnabled(true);
    }

    public void init(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 50;
        setLayoutParams(params);
        setPadding(50, 0, 50, 0);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        SpacesItemDecoration decoration = new SpacesItemDecoration(15);
        addItemDecoration(decoration);
        addOnScrollListener(onScrollChangeListener);
    }

    public void init(int topMargin, int leftPadding, int topPadding, int rightPadding, int bottomPadding, int divider) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = topMargin;
        setLayoutParams(params);
        setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        SpacesItemDecoration decoration = new SpacesItemDecoration(divider);
        addItemDecoration(decoration);
        addOnScrollListener(onScrollChangeListener);
    }

    public void setOffsetNum(int offsetNum) {
        this.offsetNum = offsetNum;
    }

    /**
     * 主动请求刷新（目前只使用在横竖图推荐位上）
     *
     * @author 高晓峰
     * @date 8/9/2017
     * @ChangeLog: <li> 高晓峰 on 8/9/2017 </li>
     */
    public void loadMore() {
        Logger.d(TAG, "loadMore called.");
        if (loadManager != null) {
            loadManager.loadMore();
        }
    }

    private int getLastVisibleItemPosition(RecyclerView recyclerView) {
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else {
            //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
            //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
            return findMax(lastPositions);
        }
    }

    /**
     * 找到数组中的最大值，最后一个索引
     *
     * @param lastPositions
     * @author 高晓峰
     * @date 8/9/2017
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void setLoadManager(LoadManager loadManager) {
        this.loadManager = loadManager;
    }

    public int getSelectedPosition() {
        int position = getChildAdapterPosition(getFocusedChild());
        Logger.d(TAG, "getSelectedPosition " + position);
        return position;
    }

    @Override
    public void onDraw(Canvas c) {
        mSelectedPosition = getChildAdapterPosition(getFocusedChild());
        super.onDraw(c);
    }

    /**
     * 重写该方法，适配放大焦点view的被覆盖的情况
     *
     * @param childCount
     * @param i          ChangeLog:
     *                   <li> 高晓峰 on 6/21/2017 </li>
     * @author 高晓峰
     * @date 6/21/2017
     */
    @Override
    public int getChildDrawingOrder(int childCount, int i) {
        int position = mSelectedPosition;
        if (position < 0) {
            return i;
        } else {
            if (i == childCount - 1) {
                if (position > i) {
                    position = i;
                }
                return position;
            }
            if (i == position) {
                return childCount - 1;
            }
        }
        return i;
    }

    /*实现焦点居中的情况（不太理想）*/
    /*@Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            if (DynamicGridView.this.hasFocus()) {
                resetFocusItemToCenter();
            }
        }
        return super.dispatchKeyEvent(event);
    }*/

    /**
     * 计算焦点view的位置，垂直方向滚到到居中的位置
     *
     * @author 高晓峰
     * @date 7/24/2017
     * @ChangeLog: <li> 高晓峰 on 7/24/2017 </li>
     */
    /*public void resetFocusItemToCenter() {
        Logger.d(TAG, "resetFocusItemToCenter");
        View focusView = ((BaseActivity) getContext()).getCurrentFocus();
        if (focusView == null) {
            return;
        }
        int[] position = new int[2];
        focusView.getLocationInWindow(position);
        int tDes = (int) ((this.getX() + this.getWidth() / 2) - focusView.getWidth() * focusView.getScaleX() / 2);
        if (position != null && position[0] != tDes) {
            this.smoothScrollBy(position[0] - tDes, 0);
            postInvalidate();
        }
    }*/

    /**
     * 在刷新横竖图时，如果焦点在开头的上下两个位置，则重新计算移动的位置
     *
     * @author 高晓峰
     * @date 8/24/2017
     * @ChangeLog: <li> 高晓峰 on 8/24/2017 </li>
     */
    /*public void resetFocusPosition() {
        Logger.d(TAG, "resetFocusPosition ");
        if (mSelectedPosition == 1 || mSelectedPosition == 2) {
            Logger.d(TAG, "resetFocusPosition " + mSelectedPosition);
            View focusView = ((BaseActivity) getContext()).getCurrentFocus();
            if (focusView == null) {
                return;
            }

            int[] position = new int[2];
            focusView.getLocationInWindow(position);
            this.smoothScrollBy(position[0] - getContext().getResources()
                    .getDimensionPixelOffset(R.dimen.launcher_recommend_list_down_scroll_to_header_offset_refresh), 0);// 51dp
            postInvalidate();
        }
    }*/

}
