package com.idv2.HearthMaster.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.idv2.HearthMaster.R;
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
        deckAdapter = new ArrayAdapter<Deck>(getActivity(), android.R.layout.simple_list_item_1);
        deckList.setAdapter(deckAdapter);

        cm = CardManager.getInstance();
        List<Deck> decks = cm.getAllDecks();
        deckAdapter.addAll(decks);
        deckAdapter.notifyDataSetChanged();

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
}
