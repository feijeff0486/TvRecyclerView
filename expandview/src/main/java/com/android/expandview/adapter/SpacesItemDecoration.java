package com.android.expandview.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: recyclerview 边距设置</p>
 * <p> Created by <b>高晓峰</b> on 6/12/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 6/12/2017 do fisrt create. </li>
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * item之间的间隙
     */
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
//        outRect.right = space;
        outRect.top = space;

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = 0;
        }
    }
}