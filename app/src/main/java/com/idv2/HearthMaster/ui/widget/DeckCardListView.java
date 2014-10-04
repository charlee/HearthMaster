package com.idv2.HearthMaster.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;

import java.util.Comparator;

/**
 * Created by charlee on 2014-10-01.
 */
public class DeckCardListView extends ListView {

    /**
     * Adapter for the deck card list
     */
    private DeckCardAdapter deckCardAdapter = null;

    public DeckCardListView(Context context) {
        super(context);
        init(null, 0);
    }

    public DeckCardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DeckCardListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        Context context = getContext();
        deckCardAdapter = new DeckCardAdapter(context);
        this.setAdapter(deckCardAdapter);
    }

    public class DeckCardAdapter extends ArrayAdapter<DeckCard> {

        public DeckCardAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                v = inflater.inflate(R.layout.deck_card_list_item, null);
            }

            DeckCard deckCard = getItem(position);
            if (deckCard != null) {
                TextView cardCost = (TextView) v.findViewById(R.id.card_cost);
                TextView cardName = (TextView) v.findViewById(R.id.card_name);
                TextView cardCount = (TextView) v.findViewById(R.id.card_count);

                cardCost.setText(String.format("%d)", deckCard.cost));
                cardName.setText(deckCard.name);
                cardName.setTextColor(Card.qualityColor.get(deckCard.quality));
                cardCount.setText(deckCard.count == 1 ? "" : String.format("x%d", deckCard.count));
            }

            return v;
        }

        /**
         * Add a card to deck
         * @param card
         */
        public void add(Card card) {

            if (getCount() >= 30) return;           // max 30 cards

            DeckCard deckCard = findDeckCard(card);
            if (deckCard != null) {
                if (deckCard.count >= 2) return;    // max 2 per card
                deckCard.count++;
            } else {
                deckCard = new DeckCard(card);
                this.add(deckCard);
            }

            this.sort(new Comparator<DeckCard>() {
                @Override
                public int compare(DeckCard lhs, DeckCard rhs) {
                    return lhs.cost < rhs.cost ? -1 : lhs.cost == rhs.cost ? 0 : 1;
                }
            });
        }

        /**
         * Remove a card from deck
         * @param position
         */
        public void remove(int position) {

            DeckCard deckCard = getItem(position);
            deckCard.count--;

            if (deckCard.count == 0) remove(deckCard);
        }

        private DeckCard findDeckCard(Card card) {
            for (int i = 0; i < getCount(); i++) {
                DeckCard deckCard = getItem(i);
                if (card.id == deckCard.id) return deckCard;
            }
            return null;
        }
    }

    public class DeckCard extends Card {

        /**
         * Card count
         */
        public int count;

        public DeckCard(Card card) {
            super(card);
            this.count = 1;
        }
    }

}

