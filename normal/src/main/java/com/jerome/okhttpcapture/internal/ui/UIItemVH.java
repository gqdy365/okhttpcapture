package com.jerome.okhttpcapture.internal.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerome.okhttpcapture.InitCapture;
import com.jerome.okhttpcapture.R;
import com.jerome.okhttpcapture.internal.utils.CacheUtils;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractExpandableAdapterItem;

/**
 * author:DingDeGao
 * time:2019-10-31-16:58
 * function: default function
 */
public class UIItemVH extends AbstractExpandableAdapterItem {

    private ImageView mArrow;
    private TextView mName;

    @Override
    public int getLayoutResId() {
        return R.layout.item_capture;
    }

    @Override
    public void onBindViews(View root) {
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doExpandOrUnexpand();
            }
        });
        mName =  root.findViewById(R.id.tv_name);
        mArrow = root.findViewById(R.id.iv_arrow);
    }

    @Override
    public void onSetViews() {

    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        if (expanded) {
            animateExpand();
        } else {
            animateCollapse();
        }
    }

    private void animateExpand() {
        mArrow.animate().rotation(180).setDuration(300);
    }

    private void animateCollapse() {
        mArrow.animate().rotation(360).setDuration(300);
    }

    @Override
    public void onUpdateViews(Object model, int position) {
        super.onUpdateViews(model, position);
        if(model instanceof UIItemEntity){
            String name = ((UIItemEntity) model).name;
            mName.setText(InitCapture.get().getCacheUtils().getUrl(name));
        }
    }
}
