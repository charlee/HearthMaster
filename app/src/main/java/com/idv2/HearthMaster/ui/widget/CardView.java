package com.idv2.HearthMaster.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.view.View;

import com.idv2.HearthMaster.HearthMasterApp;
import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by charlee on 2014-06-22.
 */
public class CardView extends View {

    private int cardId;
    private String language;
    private Card card = null;

    /**
     * shared card back bitmap
     */
    private static Bitmap cardBackBitmap = null;

    private Bitmap cardBase = null;
    private Bitmap cardArt = null;

    private boolean cardLoaded;

    static {
        // load card back on class loading
        Context context = HearthMasterApp.getInstance().getApplicationContext();
        try {
            InputStream is = context.getAssets().open("cards/back-default.png");
            cardBackBitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CardView(Context context, int cardId, String language) {
        super(context);

        this.cardId = cardId;
        this.language = language;

        cardLoaded = false;

        (new CardLoadTask()).execute();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(Card.CARD_WIDTH, Card.CARD_HEIGHT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (cardLoaded) {
            // card loaded, draw card
            canvas.drawBitmap(cardArt, 53, 95, null);
            canvas.drawBitmap(cardBase, 0, 0, null);
        } else {

            // card not loaded, draw card back
            canvas.drawBitmap(cardBackBitmap, 0, 0, null);

        }
    }


    class CardLoadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            CardManager cm = CardManager.getInstance();
            card = cm.getCard(cardId);

            String cardClass = cm.getCardClass(card.cardClass.id).toLowerCase();
            String cardType = cm.getCardType(card.cardType.id).toLowerCase();
            String cardBaseFilename = String.format("cards/base-%s-%s.png", cardType, cardClass);

            String cardArtFilename = String.format("cards/%s.jpg", card.image);

            try {
                InputStream is = getContext().getAssets().open(cardArtFilename);
                cardArt = BitmapFactory.decodeStream(is);
                is.close();

                is = getContext().getAssets().open(cardBaseFilename);
                cardBase = BitmapFactory.decodeStream(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            cardLoaded = true;
            invalidate();
        }
    }
}
