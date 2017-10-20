package com.android.expandview.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.android.expandview.adapter.DynamicAdapter;
import com.android.expandview.uitls.FieldReflector;
import com.gogh.okrxretrofit.util.Logger;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: 自定义布局，根据指定的 坐标值排版</p>
 * <p> Created by <b>高晓峰</b> on 8/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 8/23/2017 do fisrt create. </li>
 */

public class DynamicLayout extends HorizontalScrollView {

    private static final String TAG = "DynamicLayout";

    private int mOffset = 50;
    private RelativeLayout mRootView;
    private DynamicAdapter dynamicAdapter;

    public DynamicLayout(Context context) {
        this(context, null);
    }

    public DynamicLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSmoothScrollingEnabled(true);
        setSoundEffectsEnabled(true);
        setClipToPadding(false);
        init(context);
    }

    private void init(Context context){
        mRootView = new RelativeLayout(context);
        mRootView.setPadding(0, 0, mOffset, 0);
        addView(mRootView);
    }

    /**
     * 设置左右的边距
     * @author 高晓峰
     * @date 10/19/2017
     * @param offset 边距值
     * @ChangeLog:
     * <li> 高晓峰 on 10/19/2017 </li>
     */
    public void setHorizantalOffset(int offset){
        this.mOffset = offset;
    }

    public View getContainerView() {
        return mRootView;
    }

    private void addItem(View view){
        mRootView.addView(view);
    }

    public void setAdapter(DynamicAdapter dynamicAdapter){
        this.dynamicAdapter = dynamicAdapter;
        bindView();
    }

    private void bindView(){
        if(dynamicAdapter != null){
            dynamicAdapter.onContanier(this);
            for(int i = 0; i < dynamicAdapter.getItemCount(); i++){
                View view = LayoutInflater.from(getContext()).inflate(dynamicAdapter.getLayoutId(), null);
                dynamicAdapter.resetItemParams(view, i);
                dynamicAdapter.onBindView(dynamicAdapter.getViewHolder(view), i);
                final int index = i;
                view.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {
                        dynamicAdapter.onViewAttachedToWindow(dynamicAdapter.getViewHolder(v), v, index);
                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {
                        dynamicAdapter.onViewDetachedFromWindow(dynamicAdapter.getViewHolder(v), v, index);
                    }
                });
                addItem(view);
            }
        } else {
            throw new NullPointerException("Adapter is null.");
        }
    }

    @Override
    public boolean arrowScroll(int direction) {
        View currentFocused = findFocus();
        if (currentFocused == this) {
            currentFocused = null;
        }

        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);

        final int maxJump = getMaxScrollAmount();

        Rect mTempRect = (Rect) FieldReflector.getFieldValue(this);

        if (nextFocused != null && isWithinDeltaOfScreen(nextFocused, maxJump)) {
            nextFocused.getDrawingRect(mTempRect);
            offsetDescendantRectToMyCoords(nextFocused, mTempRect);
            int scrollDelta = computeScrollDeltaToGetChildRectOnScreen(mTempRect);
            FieldReflector.setFieldValue(this, mTempRect);

            if(direction == View.FOCUS_RIGHT){
                if(mRootView.getWidth() - mTempRect.right <= 50){
                    int distance = mRootView.getWidth() - mTempRect.right;
                    doScrollX(scrollDelta + getPaddingRight() + mOffset + distance);
                } else {
                    if(scrollDelta > 0) {
                        doScrollX(scrollDelta + getPaddingRight() + mOffset);
                    } else {
                        doScrollX(scrollDelta);
                    }
                }
            } else if(direction == View.FOCUS_LEFT){
                if(mRootView.getWidth() - mTempRect.left <= 50){
                    int distance = mRootView.getWidth() - mTempRect.left;
                    doScrollX(scrollDelta - getPaddingLeft() - mOffset - distance);
                } else {
                    if(scrollDelta < 0){
                        doScrollX(scrollDelta - getPaddingLeft() - mOffset);
                    } else {
                        doScrollX(scrollDelta);
                    }
                }
            }
           /* if(scrollDelta < 0){
                doScrollX(scrollDelta - getPaddingLeft() - mOffset);
            } else if(scrollDelta > 0) {
                doScrollX(scrollDelta + getPaddingRight() + mOffset);
            } else {
                doScrollX(scrollDelta);
            }*/

            nextFocused.requestFocus(direction);
        } else {
            // no new focus
            int scrollDelta = maxJump;

            if (direction == View.FOCUS_LEFT && getScrollX() < scrollDelta) {
                scrollDelta = getScrollX();
            } else if (direction == View.FOCUS_RIGHT && getChildCount() > 0) {

                int daRight = getChildAt(0).getRight();

                int screenRight = getScrollX() + getWidth();

                if (daRight - screenRight < maxJump) {
                    scrollDelta = daRight - screenRight;
                }
            }
            if (scrollDelta == 0) {
                return false;
            }
            doScrollX(direction == View.FOCUS_RIGHT ? scrollDelta : -scrollDelta);
        }

        if (currentFocused != null && currentFocused.isFocused()
                && isOffScreen(currentFocused)) {
            // previously focused item still has focus and is off screen, give
            // it up (take it back to ourselves)
            // (also, need to temporarily force FOCUS_BEFORE_DESCENDANTS so we are
            // sure to
            // get it)
            final int descendantFocusability = getDescendantFocusability();  // save
            setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            requestFocus();
            setDescendantFocusability(descendantFocusability);  // restore
        }
        return true;
    }

    private void doScrollX(int delta) {
        if (delta != 0) {
            if (isSmoothScrollingEnabled()) {
                smoothScrollBy(delta, 0);
            } else {
                scrollBy(delta, 0);
            }
        }
    }

    private boolean isOffScreen(View descendant) {
        return !isWithinDeltaOfScreen(descendant, 0);
    }

    private boolean isWithinDeltaOfScreen(View descendant, int delta) {
        Rect mTempRect = (Rect) FieldReflector.getFieldValue(this);

        descendant.getDrawingRect(mTempRect);
        offsetDescendantRectToMyCoords(descendant, mTempRect);

        FieldReflector.setFieldValue(this, mTempRect);

        return (mTempRect.right + delta) >= getScrollX()
                && (mTempRect.left - delta) <= (getScrollX() + getWidth());
    }

}
