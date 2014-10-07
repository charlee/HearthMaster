package com.idv2.HearthMaster.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardManager;
import com.idv2.HearthMaster.model.Deck;

import java.util.List;

/**
 * Created by charlee on 2014-09-13.
 */
public class DeckListFragment extends Fragment {

    private Button newDeckButton;
    private ListView deckList;

    private ArrayAdapter<Deck> deckAdapter;
    private CardManager cm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_deck_list, container, false);

        // setup deck list
        deckList = (ListView) view.findViewById(R.id.deck_list);
        deckAdapter = new DeckAdapter(getActivity(), 0);
        deckList.setAdapter(deckAdapter);

        cm = CardManager.getInstance();
        List<Deck> decks = cm.getAllDecks();
        deckAdapter.addAll(decks);
        deckAdapter.notifyDataSetChanged();

        deckList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Deck deck = deckAdapter.getItem(position);
                if (deck != null) {

                    // start deck builder

                    Bundle bundle = new Bundle();
                    bundle.putInt(DeckBuilderFragment.DECK_ID, deck.id);
                    Fragment fragment = new DeckBuilderFragment();
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        newDeckButton = (Button)view.findViewById(R.id.new_deck);
        newDeckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ClassSelectFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    public class DeckAdapter extends ArrayAdapter<Deck> {

        public DeckAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                v = inflater.inflate(android.R.layout.simple_list_item_2, null);
            }

            Deck deck = getItem(position);

            ((TextView) v.findViewById(android.R.id.text1)).setText(deck.name);
            ((TextView) v.findViewById(android.R.id.text2)).setText(Card.className.get(deck.classId));

            return v;
        }
    }
}
