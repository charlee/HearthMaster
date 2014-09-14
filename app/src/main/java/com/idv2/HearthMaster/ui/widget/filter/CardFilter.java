package com.idv2.HearthMaster.ui.widget.filter;

import com.idv2.HearthMaster.model.Card;

/**
 * Created by charlee on 2014-09-13.
 */
public interface CardFilter {
    public boolean isValid(Card card);
}
