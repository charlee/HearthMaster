package com.idv2.HearthMaster.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.DatabaseHelper;
import com.idv2.HearthMaster.ui.widget.CardView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;


public class MainActivity extends Activity {

    private ArrayAdapter<String> cardsAdapter;
    private ListView cardsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

        RuntimeExceptionDao<Card, Integer> cardDao = dbHelper.getCardDao();


        LinearLayout cardContainer = (LinearLayout) findViewById(R.id.card_container);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardContainer.addView(new CardView(this, 306, "english"), lp);
        cardContainer.addView(new CardView(this, 749, "english"), lp);
        cardContainer.addView(new CardView(this, 841, "english"), lp);
        cardContainer.addView(new CardView(this, 810, "english"), lp);

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
