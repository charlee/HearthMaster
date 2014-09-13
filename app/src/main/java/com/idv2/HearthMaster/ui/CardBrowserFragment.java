package com.idv2.HearthMaster.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardManager;
import com.idv2.HearthMaster.ui.widget.CardListView;
import com.idv2.HearthMaster.ui.widget.CardPopupView;

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
    private Spinner filterClassSpinner;
    private Spinner filterQualitySpinner;
    private Spinner filterCostSpinner;
    private Spinner filterTypeSpinner;
    private Spinner filterSetSpinner;

    // filter spinner adapters
    private ArrayAdapter<CharSequence> filterClassAdapter;
    private ArrayAdapter<CharSequence> filterQualityAdapter;
    private ArrayAdapter<CharSequence> filterCostAdapter;
    private ArrayAdapter<CharSequence> filterTypeAdapter;
    private ArrayAdapter<CharSequence> filterSetAdapter;

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
        filterClassSpinner = (Spinner) view.findViewById(R.id.filter_class);
        filterQualitySpinner = (Spinner) view.findViewById(R.id.filter_quality);
        filterCostSpinner = (Spinner) view.findViewById(R.id.filter_cost);
        filterTypeSpinner = (Spinner) view.findViewById(R.id.filter_type);
        filterSetSpinner = (Spinner) view.findViewById(R.id.filter_set);

        filterClassAdapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item);
        filterClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterClassSpinner.setAdapter(filterClassAdapter);

        filterClassAdapter.add(getString(R.string.all));
        for (int classId: Card.allClasses) {
            int resId = Card.className.get(classId);
            filterClassAdapter.add(getString(resId));
        }

        filterClassAdapter.notifyDataSetChanged();

        filterQualityAdapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item);
        filterQualityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterQualitySpinner.setAdapter(filterQualityAdapter);

        filterQualityAdapter.add(getString(R.string.all));
        for (int qualityId: Card.allQualities) {
            int resId = Card.qualityName.get(qualityId);
            filterQualityAdapter.add(getString(resId));
        }

        filterCostAdapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item);
        filterCostAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterCostSpinner.setAdapter(filterCostAdapter);

        filterCostAdapter.add(getString(R.string.all));
        for (int cost = 0; cost < 7; cost++) {
            filterCostAdapter.add(String.valueOf(cost));
        }
        filterCostAdapter.add("7+");
        filterCostAdapter.notifyDataSetChanged();

        filterTypeAdapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item);
        filterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterTypeSpinner.setAdapter(filterTypeAdapter);

        filterTypeAdapter.add(getString(R.string.all));
        for (int typeId: Card.allTypes) {
            int resId = Card.typeName.get(typeId);
            filterTypeAdapter.add(getString(resId));
        }
        filterTypeAdapter.notifyDataSetChanged();

        filterSetAdapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item);
        filterSetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSetSpinner.setAdapter(filterSetAdapter);

        filterSetAdapter.add(getString(R.string.all));
        for (int setId: Card.allSets) {
            int resId = Card.setName.get(setId);
            filterSetAdapter.add(getString(resId));
        }
        filterSetAdapter.notifyDataSetChanged();

        filterSetSpinner.setOnItemSelectedListener(this);
        filterTypeSpinner.setOnItemSelectedListener(this);
        filterQualitySpinner.setOnItemSelectedListener(this);
        filterCostSpinner.setOnItemSelectedListener(this);
        filterClassSpinner.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int costPos = filterCostSpinner.getSelectedItemPosition();
        int qualityPos = filterQualitySpinner.getSelectedItemPosition();
        int classPos = filterClassSpinner.getSelectedItemPosition();
        int typePos = filterTypeSpinner.getSelectedItemPosition();
        int setPos = filterSetSpinner.getSelectedItemPosition();

        int classCriteria = (classPos > 0) ? Card.allClasses.get(classPos - 1) : -1;
        int qualityCriteria = (qualityPos > 0) ? Card.allQualities.get(qualityPos - 1) : -1;
        int typeCriteria = (typePos > 0) ? Card.allTypes.get(typePos - 1) : -1;
        int costCriteria = costPos - 1;
        int setCriteria = (setPos > 0) ? Card.allSets.get(setPos - 1) : -1;

        // remove all cards
        cardsAdapter.clear();

        // add cards
        for (Card card: cards) {

            if (classPos > 0 && classCriteria != card.cardClass) continue;
            if (qualityPos > 0 && qualityCriteria != card.quality) continue;
            if (typePos > 0 && typeCriteria != card.cardType) continue;
            if (setPos > 0 && setCriteria != card.cardSet) continue;
            if (costPos > 0 && (costCriteria < 7 && card.cost != costCriteria || costCriteria == 7 && card.cost < costCriteria)) continue;

            cardsAdapter.add(card);
        }

        cardsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
