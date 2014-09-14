package com.idv2.HearthMaster.ui.widget.filter;

import android.content.Context;
import android.util.AttributeSet;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;

/**
 * Created by charlee on 2014-09-13.
 */
public class CardClassFilter extends BaseCardFilter {

    public CardClassFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();

        Context context = getContext();

        mAdapter.add(context.getString(R.string.all));
        for (int classId: Card.allClasses) {
            int resId = Card.className.get(classId);
            mAdapter.add(context.getString(resId));
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isValid(Card card) {
        int pos = getSelectedItemPosition();
        return (pos == 0) ? true : (Card.allClasses.get(pos - 1) == card.cardClass);
    }
}
