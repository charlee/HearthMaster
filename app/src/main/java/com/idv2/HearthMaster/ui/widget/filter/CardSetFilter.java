package com.idv2.HearthMaster.ui.widget.filter;

import android.content.Context;
import android.util.AttributeSet;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;

/**
 * Created by charlee on 2014-09-13.
 */
public class CardSetFilter extends BaseCardFilter {

    public CardSetFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void init() {
        super.init();

        Context context = getContext();

        mAdapter.add(context.getString(R.string.all));
        for (int setId: Card.allSets) {
            int resId = Card.setName.get(setId);
            mAdapter.add(context.getString(resId));
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isValid(Card card) {
        int pos = getSelectedItemPosition();
        return (pos == 0) || (Card.allSets.get(pos - 1) == card.cardSet);
    }
}
