package com.idv2.HearthMaster.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardManager;
import com.idv2.HearthMaster.ui.widget.CardListView;

import java.util.List;

/**
 * Created by charlee on 2014-09-14.
 */
public class DeckBuilderFragment extends Fragment {

    public final static String CLASS_ID = "class_id";

    private final static String TAB_CLASS_CARDS = "tab_class_cards";
    private final static String TAB_NEUTRAL_CARDS = "tab_neutral_cards";

    private CardListView classCardListView;
    private CardListView neutralCardListView;
    private ListView deckCardListView;
    private CardListView.CardAdapter classCardsAdapter;
    private CardListView.CardAdapter neutralCardsAdapter;
    private CardManager cm;
    private List<Card> classCards;
    private List<Card> neutralCards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int cardClass = getArguments().getInt(CLASS_ID);

        cm = CardManager.getInstance();

        classCards = cm.getCardsByClass(cardClass);
        neutralCards = cm.getCardsByClass(Card.NEUTRAL);

        View view = inflater.inflate(R.layout.fragment_deck_builder, container, false);

        // setup tabs
        final TabHost tabHost = (TabHost) view.findViewById(R.id.card_class_tabhost);
        tabHost.setup();

        TabHost.TabSpec classTab = tabHost.newTabSpec(TAB_CLASS_CARDS);
        classTab.setContent(R.id.class_card_list);
        classTab.setIndicator(getString(Card.className.get(cardClass)));
        tabHost.addTab(classTab);

        TabHost.TabSpec neutralTab = tabHost.newTabSpec(TAB_NEUTRAL_CARDS);
        neutralTab.setContent(R.id.neutral_card_list);
        neutralTab.setIndicator(getString(Card.className.get(Card.NEUTRAL)));
        tabHost.addTab(neutralTab);

        classCardListView = (CardListView) view.findViewById(R.id.class_card_list);
        neutralCardListView = (CardListView) view.findViewById(R.id.neutral_card_list);
        deckCardListView = (ListView) view.findViewById(R.id.deck_card_list);
        classCardsAdapter = (CardListView.CardAdapter) classCardListView.getAdapter();
        neutralCardsAdapter = (CardListView.CardAdapter) neutralCardListView.getAdapter();

        classCardsAdapter.addAll(classCards);
        classCardsAdapter.notifyDataSetChanged();

        neutralCardsAdapter.addAll(neutralCards);
        neutralCardsAdapter.notifyDataSetChanged();

        return view;
    }
}
