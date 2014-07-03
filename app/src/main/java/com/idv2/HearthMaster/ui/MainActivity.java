package com.idv2.HearthMaster.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardManager;
import com.idv2.HearthMaster.model.DatabaseHelper;
import com.idv2.HearthMaster.ui.widget.CardView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;


public class MainActivity extends Activity {

    private ArrayAdapter<String> cardsAdapter;
    private ListView cardList;
    private List<Card> cards;

    private CardManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cm = CardManager.getInstance();

        cards = cm.getAllCards();
        cardList = (ListView) findViewById(R.id.card_list);
        cardsAdapter = new ArrayAdapter<String>(this, R.layout.card_list_item);
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

        for (Card card: cards) {
            cardsAdapter.add(card.name);
        }
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

}
