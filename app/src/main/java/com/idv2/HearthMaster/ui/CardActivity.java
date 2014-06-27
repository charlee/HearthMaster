package com.idv2.HearthMaster.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.ui.widget.CardView;

/**
 * Created by charlee on 2014-06-27.
 */
public class CardActivity extends Activity {

    public static final String CARD_ID = "CARDID";

    int cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        cardId = intent.getIntExtra(CARD_ID, 0);

        if (cardId == 0) {
            finish();
        }

        setContentView(R.layout.activity_card);

        CardView cardView = new CardView(this, cardId, "english");
        FrameLayout layout = (FrameLayout) findViewById(R.id.card_container);
        FrameLayout.LayoutParams lp =new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        layout.addView(cardView, lp);
    }
}
