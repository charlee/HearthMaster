package com.idv2.HearthMaster.ui;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardManager;
import com.idv2.HearthMaster.ui.widget.CardListView;

import java.util.List;

/**
 * Created by charlee on 2014-09-12.
 */
public class CardBrowserFragment extends Fragment {

    private CardListView cardList;
    private List<Card> cards;

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

        cm = CardManager.getInstance();

        View view = inflater.inflate(R.layout.activity_main, container, false);
        cardList = (CardListView) view.findViewById(R.id.card_list);

        return view;
    }
}
