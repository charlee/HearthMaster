package com.idv2.HearthMaster.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardManager;
import com.idv2.HearthMaster.ui.widget.CardView;

import java.util.List;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private CardAdapter cardsAdapter;
    private ListView cardList;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cm = CardManager.getInstance();

        // load typeface
        listFont = Typeface.createFromAsset(getAssets(), "fonts/BelweBT-Bold.ttf");

        cards = cm.getAllCards();
        cardList = (ListView) findViewById(R.id.card_list);
        cardsAdapter = new CardAdapter(this);
        cardList.setAdapter(cardsAdapter);
        final View cardPopup = findViewById(R.id.card_popup);
        final CardView cardView = new CardView(this, 0, "english");

        FrameLayout layout = (FrameLayout) findViewById(R.id.card_container);
        FrameLayout.LayoutParams lp =new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        layout.addView(cardView, lp);

        cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = (Card) parent.getItemAtPosition(position);
                cardView.setCardId(card.id);
                Animation cardInAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.card_in);
                cardPopup.setVisibility(View.VISIBLE);
                cardView.startAnimation(cardInAnimation);
            }
        });

        cardPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation cardOutAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.card_out);
                cardOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        cardPopup.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                cardView.startAnimation(cardOutAnimation);
            }
        });

        cardsAdapter.addAll(cards);
        cardsAdapter.notifyDataSetChanged();

        // init filters
        filterClassSpinner = (Spinner) findViewById(R.id.filter_class);
        filterQualitySpinner = (Spinner) findViewById(R.id.filter_quality);
        filterCostSpinner = (Spinner) findViewById(R.id.filter_cost);
        filterTypeSpinner = (Spinner) findViewById(R.id.filter_type);
        filterSetSpinner = (Spinner) findViewById(R.id.filter_set);

        filterClassAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        filterClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterClassSpinner.setAdapter(filterClassAdapter);

        filterClassAdapter.add(getString(R.string.all));
        for (int classId: Card.allClasses) {
            int resId = Card.className.get(classId);
            filterClassAdapter.add(getString(resId));
        }

        filterClassAdapter.notifyDataSetChanged();

        filterQualityAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        filterQualityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterQualitySpinner.setAdapter(filterQualityAdapter);

        filterQualityAdapter.add(getString(R.string.all));
        for (int qualityId: Card.allQualities) {
            int resId = Card.qualityName.get(qualityId);
            filterQualityAdapter.add(getString(resId));
        }

        filterCostAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        filterCostAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterCostSpinner.setAdapter(filterCostAdapter);

        filterCostAdapter.add(getString(R.string.all));
        for (int cost = 0; cost < 7; cost++) {
            filterCostAdapter.add(String.valueOf(cost));
        }
        filterCostAdapter.add("7+");
        filterCostAdapter.notifyDataSetChanged();

        filterTypeAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        filterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterTypeSpinner.setAdapter(filterTypeAdapter);

        filterTypeAdapter.add(getString(R.string.all));
        for (int typeId: Card.allTypes) {
            int resId = Card.typeName.get(typeId);
            filterTypeAdapter.add(getString(resId));
        }
        filterTypeAdapter.notifyDataSetChanged();

        filterSetAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    class CardAdapter extends ArrayAdapter<Card> {

        public CardAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                v = inflater.inflate(R.layout.card_list_item, null);
            }

            Card card = getItem(position);

            if (card != null) {
                TextView cardName = (TextView) v.findViewById(R.id.card_name);
                TextView cardHealth = (TextView) v.findViewById(R.id.card_health);
                TextView cardCost = (TextView) v.findViewById(R.id.card_cost);
                TextView cardAttack = (TextView) v.findViewById(R.id.card_attack);
                ImageView iconAttack = (ImageView) v.findViewById(R.id.icon_attack);
                ImageView iconHealth = (ImageView) v.findViewById(R.id.icon_health);
                ImageView classIcon = (ImageView) v.findViewById(R.id.class_icon);

                cardName.setTypeface(listFont);
                cardName.setTextColor(Card.qualityColor.get(card.quality));

                cardName.setText(card.name);
                cardCost.setText(String.format("%d", card.cost));
                cardHealth.setText(String.format("%d", card.health));
                cardAttack.setText(String.format("%d", card.attack));

                if (card.cardType == Card.SPELL) {
                    cardHealth.setVisibility(View.GONE);
                    cardAttack.setVisibility(View.GONE);
                    iconHealth.setVisibility(View.GONE);
                    iconAttack.setVisibility(View.GONE);
                } else {
                    cardHealth.setVisibility(View.VISIBLE);
                    cardAttack.setVisibility(View.VISIBLE);
                    iconHealth.setVisibility(View.VISIBLE);
                    iconAttack.setVisibility(View.VISIBLE);
                }

                if (card.cardType == Card.WEAPON) {
                    iconHealth.setImageDrawable(getResources().getDrawable(R.drawable.durability));
                    iconAttack.setImageDrawable(getResources().getDrawable(R.drawable.weapon));
                } else {
                    iconHealth.setImageDrawable(getResources().getDrawable(R.drawable.health));
                    iconAttack.setImageDrawable(getResources().getDrawable(R.drawable.attack));
                }

                switch (card.cardClass) {
                    case Card.WARRIOR: classIcon.setImageResource(R.drawable.class_warrior); break;
                    case Card.MAGE: classIcon.setImageResource(R.drawable.class_mage); break;
                    case Card.DRUID: classIcon.setImageResource(R.drawable.class_druid); break;
                    case Card.WARLOCK: classIcon.setImageResource(R.drawable.class_warlock); break;
                    case Card.ROGUE: classIcon.setImageResource(R.drawable.class_rogue); break;
                    case Card.PRIEST: classIcon.setImageResource(R.drawable.class_priest); break;
                    case Card.PALADIN: classIcon.setImageResource(R.drawable.class_paladin); break;
                    case Card.HUNTER: classIcon.setImageResource(R.drawable.class_hunter); break;
                    case Card.SHAMAN: classIcon.setImageResource(R.drawable.class_shaman); break;
                    default: classIcon.setImageResource(0);break;
                }
            }

            return v;
        }
    }


    class FilterItem {
        int value;
        CharSequence label;

        public FilterItem(int value, CharSequence label) {
            this.value = value;
            this.label = label;
        }
    }

}
