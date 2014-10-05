package com.idv2.HearthMaster.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;

import java.util.List;

/**
 * Created by charlee on 2014-10-05.
 */
public class CardStatsView extends FrameLayout {

    private int spellCount;
    private int minionCount;
    private int weaponCount;

    private TextView spellView;
    private TextView weaponView;
    private TextView minionView;

    public CardStatsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CardStatsView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardStatsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.widget_card_stats, null);
        addView(layout);

        minionCount = 0;
        spellCount = 0;
        weaponCount = 0;

        minionView = (TextView) layout.findViewById(R.id.stats_minion);
        spellView = (TextView) layout.findViewById(R.id.stats_spell);
        weaponView = (TextView) layout.findViewById(R.id.stats_weapon);

        updateStatsView();
    }

    /**
     * Update stats widget
     * @param deckCards
     */
    public void updateStats(List<DeckCardListView.DeckCard> deckCards) {

        spellCount = 0;
        minionCount = 0;
        weaponCount = 0;

        for (DeckCardListView.DeckCard deckCard: deckCards) {

            if (deckCard.cardType == Card.SPELL) {
                spellCount += deckCard.count;
            } else if (deckCard.cardType == Card.MINION) {
                minionCount += deckCard.count;
            } else {
                weaponCount += deckCard.count;
            }

        }

        updateStatsView();
    }

    private void updateStatsView() {
        minionView.setText(String.format("%d", minionCount));
        spellView.setText(String.format("%d", spellCount));
        weaponView.setText(String.format("%d", weaponCount));
    }
}
