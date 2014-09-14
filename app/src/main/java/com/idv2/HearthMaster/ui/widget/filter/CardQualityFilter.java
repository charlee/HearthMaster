package com.idv2.HearthMaster.ui.widget.filter;

import android.content.Context;
import android.util.AttributeSet;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;

/**
 * Created by charlee on 2014-09-13.
 */
public class CardQualityFilter extends BaseCardFilter {

    public CardQualityFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();

        Context context = getContext();

        mAdapter.add(context.getString(R.string.all));
        for (int qualityId: Card.allQualities) {
            int resId = Card.qualityName.get(qualityId);
            mAdapter.add(context.getString(resId));
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isValid(Card card) {
        int pos = getSelectedItemPosition();
        return (pos == 0) || (Card.allQualities.get(pos - 1) == card.quality);
    }
}
