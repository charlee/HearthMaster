package com.idv2.HearthMaster.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardManager;
import com.idv2.HearthMaster.ui.widget.CardListView;
import com.idv2.HearthMaster.ui.widget.CardPopupView;
import com.idv2.HearthMaster.ui.widget.CardStatsView;
import com.idv2.HearthMaster.ui.widget.DeckCardListView;
import com.idv2.HearthMaster.ui.widget.ManaCurveView;

import java.util.List;

/**
 * Created by charlee on 2014-09-14.
 */
public class DeckBuilderFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public final static String CLASS_ID = "class_id";

    private final static String TAB_CLASS_CARDS = "tab_class_cards";
    private final static String TAB_NEUTRAL_CARDS = "tab_neutral_cards";

    private CardListView classCardListView;
    private CardListView neutralCardListView;
    private DeckCardListView deckCardListView;
    private CardListView.CardAdapter classCardAdapter;
    private CardListView.CardAdapter neutralCardAdapter;
    private DeckCardListView.DeckCardAdapter deckCardAdapter;
    private CardManager cm;
    private List<Card> classCards;
    private List<Card> neutralCards;
    private CardStatsView cardStatsView;

    private ManaCurveView manaCurveView;

    private TextView deckCardCount;
    private CardPopupView cardPopup;
    private TextView deckNameView;
    private TextView classNameView;

    private String deckName;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int cardClassId = getArguments().getInt(CLASS_ID);

        cm = CardManager.getInstance();

        classCards = cm.getCardsByClass(cardClassId);
        neutralCards = cm.getCardsByClass(Card.NEUTRAL);

        View view = inflater.inflate(R.layout.fragment_deck_builder, container, false);

        // setup tabs
        final TabHost tabHost = (TabHost) view.findViewById(R.id.card_class_tabhost);
        tabHost.setup();

        TabHost.TabSpec classTab = tabHost.newTabSpec(TAB_CLASS_CARDS);
        classTab.setContent(R.id.class_card_list);
        classTab.setIndicator(getString(Card.className.get(cardClassId)));
        tabHost.addTab(classTab);

        TabHost.TabSpec neutralTab = tabHost.newTabSpec(TAB_NEUTRAL_CARDS);
        neutralTab.setContent(R.id.neutral_card_list);
        neutralTab.setIndicator(getString(Card.className.get(Card.NEUTRAL)));
        tabHost.addTab(neutralTab);

        // class specific cards list, neutral cards list, deck cards list
        classCardListView = (CardListView) view.findViewById(R.id.class_card_list);
        neutralCardListView = (CardListView) view.findViewById(R.id.neutral_card_list);
        deckCardListView = (DeckCardListView) view.findViewById(R.id.deck_card_list);
        classCardAdapter = (CardListView.CardAdapter) classCardListView.getAdapter();
        neutralCardAdapter = (CardListView.CardAdapter) neutralCardListView.getAdapter();
        deckCardAdapter = (DeckCardListView.DeckCardAdapter) deckCardListView.getAdapter();

        classCardAdapter.addAll(classCards);
        classCardAdapter.notifyDataSetChanged();
        classCardListView.setOnItemClickListener(this);
        classCardListView.setOnItemLongClickListener(this);

        neutralCardAdapter.addAll(neutralCards);
        neutralCardAdapter.notifyDataSetChanged();
        neutralCardListView.setOnItemClickListener(this);
        neutralCardListView.setOnItemLongClickListener(this);

        deckCardListView.setOnItemClickListener(this);

        // mana curve widget
        manaCurveView = (ManaCurveView) view.findViewById(R.id.mana_curve);

        deckCardCount = (TextView) view.findViewById(R.id.deck_card_count);
        updateCardCount();

        // card preview popup
        cardPopup = (CardPopupView) view.findViewById(R.id.card_popup);

        // card stats widget
        cardStatsView = (CardStatsView) view.findViewById(R.id.card_stats);


        // card name and class display
        deckName = String.format(getString(R.string.deck_name_custom), getString(Card.className.get(cardClassId)));
        deckNameView = (TextView) view.findViewById(R.id.deck_name);
        classNameView = (TextView) view.findViewById(R.id.class_name);

        updateDeckName(deckName);
        classNameView.setText(Card.className.get(cardClassId));

        // click header to change deck name
        View deckHeader = view.findViewById(R.id.deck_header);
        deckHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup dialog view
                final View dialogView = inflater.inflate(R.layout.dialog_deck_name, null);
                final EditText deckNameEdit = (EditText) dialogView.findViewById(R.id.deck_name_edit);
                deckNameEdit.setText(deckName);

                // create dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialogView)
                        .setTitle(R.string.dialog_edit_deck_name)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // save name
                                updateDeckName(deckNameEdit.getEditableText().toString());
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .create()
                        .show();
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent == deckCardListView) {
            // remove card
            deckCardAdapter.remove(position);
        } else {
            // add card
            Card card = (Card) parent.getItemAtPosition(position);
            deckCardAdapter.add(card);
        }

        deckCardAdapter.notifyDataSetChanged();

        // update mana curve
        int[] manaCurve = deckCardAdapter.getManaCurve();
        manaCurveView.setCurve(manaCurve);

        // update card count indicator
        updateCardCount();

        // update stats
        cardStatsView.updateStats(deckCardAdapter.getAllDeckCards());

    }

    /**
     * Uupdate card count indicator
     */
    private void updateCardCount() {
        deckCardCount.setText(String.format("%d/%d", deckCardAdapter.getCardCount(), deckCardAdapter.getMaxCardCount()));
    }

    /**
     * update deck name displayed in the view
     * @param deckName
     */
    private void updateDeckName(String deckName) {
        this.deckName = deckName;
        deckNameView.setText(deckName);
    }

    /**
     * show card detail on long click
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Card card = (Card) parent.getAdapter().getItem(position);
        cardPopup.show(card.id);

        return true;            // prevent click event occuring
    }
}
