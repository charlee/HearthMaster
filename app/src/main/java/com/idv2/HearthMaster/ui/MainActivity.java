package com.idv2.HearthMaster.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardManager;

import java.util.List;


public class MainActivity extends Activity {

    private CardAdapter cardsAdapter;
    private ListView cardList;
    private List<Card> cards;

    private CardManager cm;

    private Typeface listFont;

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
        cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = cards.get(position);

                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                intent.putExtra(CardActivity.CARD_ID, card.id);
                startActivity(intent);
            }
        });

        cardsAdapter.addAll(cards);
        cardsAdapter.notifyDataSetChanged();

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
                TextView cardText = (TextView) v.findViewById(R.id.card_text);
                TextView cardHealth = (TextView) v.findViewById(R.id.card_health);
                TextView cardCost = (TextView) v.findViewById(R.id.card_cost);
                TextView cardAttack = (TextView) v.findViewById(R.id.card_attack);
                ImageView iconAttack = (ImageView) v.findViewById(R.id.icon_attack);
                ImageView iconHealth = (ImageView) v.findViewById(R.id.icon_health);

                cardName.setTypeface(listFont);
                cardName.setTextColor(cm.getQualityColor(card.quality.id));

                cardName.setText(card.name);
                cardText.setText(card.description);
                cardCost.setText(String.format("%d", card.cost));
                cardHealth.setText(String.format("%d", card.health));
                cardAttack.setText(String.format("%d", card.attack));

                if (card.isSpell()) {
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

                if (card.isWeapon()) {
                    iconHealth.setImageDrawable(getResources().getDrawable(R.drawable.durability));
                    iconAttack.setImageDrawable(getResources().getDrawable(R.drawable.weapon));
                } else {
                    iconHealth.setImageDrawable(getResources().getDrawable(R.drawable.health));
                    iconAttack.setImageDrawable(getResources().getDrawable(R.drawable.attack));
                }
            }

            return v;
        }
    }

}
