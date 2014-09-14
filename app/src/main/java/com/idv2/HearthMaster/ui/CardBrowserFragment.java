package com.idv2.HearthMaster.ui;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardManager;
import com.idv2.HearthMaster.ui.widget.CardListView;
import com.idv2.HearthMaster.ui.widget.CardPopupView;
import com.idv2.HearthMaster.ui.widget.filter.BaseCardFilter;
import com.idv2.HearthMaster.ui.widget.filter.CardClassFilter;
import com.idv2.HearthMaster.ui.widget.filter.CardCostFilter;
import com.idv2.HearthMaster.ui.widget.filter.CardFilter;
import com.idv2.HearthMaster.ui.widget.filter.CardQualityFilter;
import com.idv2.HearthMaster.ui.widget.filter.CardSetFilter;
import com.idv2.HearthMaster.ui.widget.filter.CardTypeFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charlee on 2014-09-12.
 */
public class CardBrowserFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private CardListView cardList;
    private CardListView.CardAdapter cardsAdapter;
    private List<Card> cards;
    private CardPopupView cardPopup;

    private CardManager cm;

    private Typeface listFont;

    // filter spinners
    private CardClassFilter filterClass;
    private CardQualityFilter filterQuality;
    private CardCostFilter filterCost;
    private CardTypeFilter filterType;
    private CardSetFilter filterSet;

    private List<BaseCardFilter> filters;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);
        Context context = getActivity();

        cm = CardManager.getInstance();

        cards = cm.getAllCards();
        cardList = (CardListView) view.findViewById(R.id.card_list);
        cardsAdapter = (CardListView.CardAdapter) cardList.getAdapter();
        cardPopup = (CardPopupView) view.findViewById(R.id.card_popup);

        cardsAdapter.addAll(cards);
        cardsAdapter.notifyDataSetChanged();

        cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = cardsAdapter.getItem(position);
                cardPopup.show(card.id);
            }
        });

        // init filters
        filterClass = (CardClassFilter) view.findViewById(R.id.filter_class);
        filterQuality = (CardQualityFilter) view.findViewById(R.id.filter_quality);
        filterCost = (CardCostFilter) view.findViewById(R.id.filter_cost);
        filterType = (CardTypeFilter) view.findViewById(R.id.filter_type);
        filterSet = (CardSetFilter) view.findViewById(R.id.filter_set);

        filters = new ArrayList<BaseCardFilter>();
        filters.add(filterClass);
        filters.add(filterQuality);
        filters.add(filterCost);
        filters.add(filterType);
        filters.add(filterSet);

        for (BaseCardFilter filter: filters) {
            filter.setOnItemSelectedListener(this);
        }



        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // remove all cards
        cardsAdapter.clear();

        // add cards
        nextCard:
        for (Card card: cards) {

            for (BaseCardFilter filter: filters) {
                if (!filter.isValid(card)) continue nextCard;
            }

            cardsAdapter.add(card);
        }

        cardsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
