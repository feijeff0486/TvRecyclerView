package com.android.expandview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.expandview.uitls.FieldReflector;
import com.android.expandview.uitls.Screen;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 10/12/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 10/12/2017 do fisrt create. </li>
 */

public class ExNestedScrollView extends NestedScrollView {

    private static final String TAG = "ExNestedScrollView";
    private static int OFFSETX = 36;
    private int mMinDistance = 50;
    private int mContainerHeight = 0;
    private LinearLayout mRootView;

    public ExNestedScrollView(Context context) {
        super(context);
        init(context);
    }

    public ExNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOFFSETX(int offsetx) {
        OFFSETX = offsetx;
    }

    public void setMinDistance(int mMinDistance) {
        this.mMinDistance = mMinDistance;
    }

    private void init(Context context) {
        setFocusable(false);
        setSmoothScrollingEnabled(true);
        setSoundEffectsEnabled(true);
        setClipToPadding(false);
        initChild(context);
    }

    private void initChild(Context context) {
        mRootView = new LinearLayout(context);
        mRootView.setOrientation(LinearLayout.VERTICAL);
        mRootView.setClipToPadding(false);
        mRootView.setFocusable(false);
        mRootView.removeAllViewsInLayout();
        addView(mRootView);
    }

    // override for auto display padding

    public LinearLayout getContainer() {
        return mRootView;
    }

    /**
     * Handle scrolling in response to an up or down arrow click.
     *
     * @param direction The direction corresponding to the arrow key that was
     *                  pressed
     * @return True if we consumed the event, false otherwise
     */
    @Override
    public boolean arrowScroll(int direction) {
        if (mContainerHeight == 0) {
            mContainerHeight = mRootView.getHeight();
        }
        View currentFocused = findFocus();
        if (currentFocused == this) {
            currentFocused = null;
        }

        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);

        final int maxJump = getMaxScrollAmount();

        // read value by reflection
        Rect mTempRect = (Rect) FieldReflector.getFieldValue(this);

        if (nextFocused != null && isWithinDeltaOfScreen(nextFocused, maxJump, getHeight())) {
            nextFocused.getDrawingRect(mTempRect);
            offsetDescendantRectToMyCoords(nextFocused, mTempRect);
            int scrollDelta = computeScrollDeltaToGetChildRectOnScreen(mTempRect);
            FieldReflector.setFieldValue(this, mTempRect);
            //
            int[] location = new int[2];
            if (nextFocused.getTag() != null) {
                if (nextFocused.getTag() instanceof View) {
                    View container = (View) nextFocused.getTag();
                    container.getLocationOnScreen(location);
                    scrollDelta = location[1] - Screen.getHeight() / 2 + container.getHeight() / 2;

                }
                doScrollY(scrollDelta);
            } else {
                // override
                if (scrollDelta < 0) {
                    doScrollY(scrollDelta - getPaddingTop() - OFFSETX);
                } else if (scrollDelta > 0) {
                    if (mTempRect.top + mTempRect.height() >= mContainerHeight
                            || (mContainerHeight - mTempRect.top - mTempRect.height()) <= mMinDistance) {
                        int distance = mContainerHeight - mTempRect.top;
                        doScrollY(scrollDelta + getPaddingBottom() + OFFSETX * 2 + distance);
                    } else {
                        doScrollY(scrollDelta + getPaddingBottom() + OFFSETX);
                    }
                } else {
                    doScrollY(scrollDelta);
                }
            }

            nextFocused.requestFocus(direction);
        } else {
            // no new focus
            int scrollDelta = maxJump;

            if (direction == View.FOCUS_UP && getScrollY() < scrollDelta) {
                scrollDelta = getScrollY();
            } else if (direction == View.FOCUS_DOWN) {
                if (getChildCount() > 0) {
                    int daBottom = getChildAt(0).getBottom();
                    int screenBottom = getScrollY() + getHeight() - getPaddingBottom();
                    if (daBottom - screenBottom < maxJump) {
                        scrollDelta = daBottom - screenBottom;
                    }
                }
            }
            if (scrollDelta == 0) {
                return false;
            }
            doScrollY(direction == View.FOCUS_DOWN ? scrollDelta : -scrollDelta);
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

    /**
     * Smooth scroll by a Y delta
     *
     * @param delta the number of pixels to scroll by on the Y axis
     */
    private void doScrollY(int delta) {
        if (delta != 0) {
            if (isSmoothScrollingEnabled()) {
                smoothScrollBy(0, delta);
            } else {
                scrollBy(0, delta);
            }
        }
    }

    /**
     * @return whether the descendant of this scroll view is scrolled off
     * screen.
     */
    private boolean isOffScreen(View descendant) {
        return !isWithinDeltaOfScreen(descendant, 0, getHeight());
    }

    /**
     * @return whether the descendant of this scroll view is within delta
     * pixels of being on the screen.
     */
    private boolean isWithinDeltaOfScreen(View descendant, int delta, int height) {
        // read value by reflection
        Rect mTempRect = (Rect) FieldReflector.getFieldValue(this);

        descendant.getDrawingRect(mTempRect);
        offsetDescendantRectToMyCoords(descendant, mTempRect);

        FieldReflector.setFieldValue(this, mTempRect);

        return (mTempRect.bottom + delta) >= getScrollY()
                && (mTempRect.top - delta) <= (getScrollY() + height);
    }

}
