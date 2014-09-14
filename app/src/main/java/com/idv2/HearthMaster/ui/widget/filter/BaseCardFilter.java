package com.idv2.HearthMaster.ui.widget.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.idv2.HearthMaster.model.Card;

/**
 * Created by charlee on 2014-09-13.
 */
public abstract class BaseCardFilter extends Spinner implements CardFilter {

    protected ArrayAdapter<CharSequence> mAdapter;

    public BaseCardFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
        mAdapter = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAdapter(mAdapter);
    }
}
