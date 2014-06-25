package com.idv2.HearthMaster.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
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
    private static Bitmap cardBaseBitmap = null;
    private static Typeface cardNameFont = null;


    private Bitmap cardArtBitmap = null;

    private boolean cardLoaded;

    static {
        Context context = HearthMasterApp.getInstance().getApplicationContext();
        try {

            // load card back on class loading
            InputStream is = context.getAssets().open("cards/back-default.png");
            cardBackBitmap = BitmapFactory.decodeStream(is);
            is.close();

            // load customize font
            cardNameFont = Typeface.createFromAsset(context.getAssets(), "fonts/BelweBT-Bold.ttf");

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
        setMeasuredDimension(CardSpec.CARD_WIDTH, CardSpec.CARD_HEIGHT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (cardLoaded) {
            drawCard(canvas);
        } else {
            drawCardBack(canvas);
        }
    }

    /**
     * Draw the card
     * @param canvas
     */
    private void drawCard(Canvas canvas) {
        // card loaded, draw card
        CardSpec spec = CardSpec.getCardSpec(card);

        canvas.drawBitmap(cardArtBitmap, spec.artPosition.x, spec.artPosition.y, null);
        canvas.drawBitmap(cardBaseBitmap, CardSpec.getCardBaseRect(card), new Rect(0, 0, CardSpec.CARD_WIDTH, CardSpec.CARD_HEIGHT), null);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(cardNameFont);

        // find proper text size
        int textSize = 24;          // initial text size
        paint.setTextSize(textSize);

        float textWidth = paint.measureText(card.name);
        while (textWidth > spec.namePathLength) {
            paint.setTextSize(--textSize);
            textWidth = paint.measureText(card.name);
        }

        // find text position
        float hOffset = (spec.namePathLength - textWidth) / 2;

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawTextOnPath(card.name, spec.namePath, hOffset, 0, paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath(card.name, spec.namePath, hOffset, 0, paint);

    }

    /**
     * Draw the card back
     * @param canvas
     */
    private void drawCardBack(Canvas canvas) {
        // card not loaded, draw card back
        canvas.drawBitmap(cardBackBitmap, 0, 0, null);
    }


    class CardLoadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            CardManager cm = CardManager.getInstance();
            card = cm.getCard(cardId);


            try {
                // load the card art
                String cardArtFilename = String.format("cards/%s.jpg", card.image);
                InputStream is = getContext().getAssets().open(cardArtFilename);
                cardArtBitmap = BitmapFactory.decodeStream(is);
                is.close();

                // load the card base
                if (cardBaseBitmap == null) {
                    is = getContext().getAssets().open("cards/base-all.png");
                    cardBaseBitmap = BitmapFactory.decodeStream(is);
                    is.close();
                }

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


