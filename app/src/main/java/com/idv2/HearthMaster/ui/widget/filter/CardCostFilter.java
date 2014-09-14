package com.idv2.HearthMaster.ui.widget.filter;

import android.content.Context;
import android.util.AttributeSet;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;

/**
 * Created by charlee on 2014-09-13.
 */
public class CardCostFilter extends BaseCardFilter {
    public CardCostFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();

        Context context = getContext();
        mAdapter.add(context.getString(R.string.all));
        for (int cost = 0; cost < 7; cost++) {
            mAdapter.add(String.valueOf(cost));
        }
        mAdapter.add("7+");

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isValid(Card card) {
        int pos = getSelectedItemPosition();
        return (pos == 0) || (pos >= 7 && card.cost >= 7) || (pos - 1 == card.cost);
    }
}
