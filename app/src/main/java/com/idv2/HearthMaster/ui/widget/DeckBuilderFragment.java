package com.idv2.HearthMaster.ui.widget;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.idv2.HearthMaster.R;

/**
 * Created by charlee on 2014-09-14.
 */
public class DeckBuilderFragment extends Fragment {

    public final static String CLASS_ID = "class_id";

    private CardListView cardListView;
    private ListView deckCardListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int cardClass = getArguments().getInt(CLASS_ID);

        View view = inflater.inflate(R.layout.fragment_deck_builder, container, false);

        cardListView = (CardListView) view.findViewById(R.id.card_list);
        deckCardListView = (ListView) view.findViewById(R.id.deck_card_list);

        return view;
    }
}
