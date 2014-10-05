package com.idv2.HearthMaster.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        deckCardAdapter = new DeckCardAdapter();
        this.setAdapter(deckCardAdapter);
    }

    public class DeckCardAdapter extends BaseAdapter {

        public static final int MAX_DECK_CARD_COUNT = 30;

        private List<DeckCard> deckCards;
        private int cardCount = 0;

        public DeckCardAdapter() {
            super();
            cardCount = 0;
            deckCards = new ArrayList<DeckCard>();
        }

        @Override
        public int getCount() {
            return deckCards.size();
        }

        @Override
        public Object getItem(int position) {
            return deckCards.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Return current count of cards in the deck.
         * @return
         */
        public int getCardCount() {
            return cardCount;
        }

        public int getMaxCardCount() {
            return MAX_DECK_CARD_COUNT;
        }

        public List<DeckCard> getAllDeckCards() {
            return this.deckCards;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                v = inflater.inflate(R.layout.deck_card_list_item, null);
            }

            DeckCard deckCard = deckCards.get(position);
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

            if (cardCount >= MAX_DECK_CARD_COUNT) return;           // max 30 cards

            DeckCard deckCard = findDeckCard(card);
            if (deckCard != null) {
                if (deckCard.quality == Card.LEGENDARY && deckCard.count == 1 || deckCard.count >= 2) return;    // max 2 per card
                deckCard.count++;
            } else {
                deckCard = new DeckCard(card);
                deckCards.add(deckCard);
            }
            cardCount++;

            Collections.sort(deckCards, new Comparator<DeckCard>() {
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

            DeckCard deckCard = deckCards.get(position);
            deckCard.count--;
            cardCount--;

            // clear card if no more exists
            if (deckCard.count == 0) deckCards.remove(position);
        }

        /**
         * Get mana curve
         * @return
         */
        public int[] getManaCurve() {
            int[] manaCurve = new int[8];
            for (DeckCard deckCard: deckCards) {
                if (deckCard.cost >= 7) manaCurve[7] += deckCard.count;
                else manaCurve[deckCard.cost] += deckCard.count;
            }

            return manaCurve;
        }

        private DeckCard findDeckCard(Card card) {
            for (DeckCard deckCard: deckCards) {
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

