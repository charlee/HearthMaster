package com.idv2.HearthMaster.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;

/**
 * Created by charlee on 2014-09-14.
 */
public class ClassSelectFragment extends Fragment implements View.OnClickListener {

    private Button chooseWarrior;
    private Button chooseShaman;
    private Button chooseRogue;
    private Button choosePaladin;
    private Button chooseHunter;
    private Button chooseDruid;
    private Button chooseWarlock;
    private Button chooseMage;
    private Button choosePriest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_class_select, container, false);

        chooseWarrior = (Button) view.findViewById(R.id.choose_warrior);
        chooseShaman = (Button) view.findViewById(R.id.choose_shaman);
        chooseRogue = (Button) view.findViewById(R.id.choose_rogue);
        choosePaladin = (Button) view.findViewById(R.id.choose_paladin);
        chooseHunter = (Button) view.findViewById(R.id.choose_hunter);
        chooseDruid = (Button) view.findViewById(R.id.choose_druid);
        chooseWarlock = (Button) view.findViewById(R.id.choose_warlock);
        chooseMage = (Button) view.findViewById(R.id.choose_mage);
        choosePriest = (Button) view.findViewById(R.id.choose_priest);


        chooseWarrior.setOnClickListener(this);
        chooseShaman.setOnClickListener(this);
        chooseRogue.setOnClickListener(this);
        choosePaladin.setOnClickListener(this);
        chooseHunter.setOnClickListener(this);
        chooseDruid.setOnClickListener(this);
        chooseWarlock.setOnClickListener(this);
        chooseMage.setOnClickListener(this);
        choosePriest.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (v == chooseWarrior) startDeckBuilder(Card.WARRIOR);
        else if (v == chooseShaman) startDeckBuilder(Card.SHAMAN);
        else if (v == chooseRogue) startDeckBuilder(Card.ROGUE);
        else if (v == choosePaladin) startDeckBuilder(Card.PALADIN);
        else if (v == chooseHunter) startDeckBuilder(Card.HUNTER);
        else if (v == chooseDruid) startDeckBuilder(Card.DRUID);
        else if (v == chooseWarlock) startDeckBuilder(Card.WARLOCK);
        else if (v == chooseMage) startDeckBuilder(Card.MAGE);
        else if (v == choosePriest) startDeckBuilder(Card.PRIEST);
    }

    private void startDeckBuilder(int cardClass) {

        Bundle bundle = new Bundle();
        bundle.putInt(DeckBuilderFragment.CLASS_ID, cardClass);
        Fragment fragment = new DeckBuilderFragment();
        fragment.setArguments(bundle);
        getFragmentManager().popBackStack();            // restore the backstack so that back button will skip class select screen
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
