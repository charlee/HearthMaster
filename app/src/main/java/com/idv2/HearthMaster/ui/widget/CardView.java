package com.idv2.HearthMaster.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.display.DisplayManager;
import android.os.AsyncTask;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
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
    private static Bitmap cardQualityBitmap = null;
    private static Typeface cardNameFont = null;
    private static Typeface cardDescFont = null;


    private Bitmap cardArtBitmap = null;

    private float screenDensity;

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
//            cardDescFont = Typeface.createFromAsset(context.getAssets(), "fonts/FRA-Gothic-Demi.ttf");

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

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenDensity = metrics.density;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int)(CardSpec.CARD_WIDTH * screenDensity), (int)(CardSpec.CARD_HEIGHT * screenDensity));
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

        canvas.save();
        canvas.scale(screenDensity, screenDensity);

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

        // draw name text
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawTextOnPath(card.name, spec.namePath, hOffset, 0, paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath(card.name, spec.namePath, hOffset, 0, paint);

        // draw description text
        if (card.description != null) {
            TextPaint descPaint = new TextPaint();
            descPaint.setColor(Color.BLACK);
            descPaint.setAntiAlias(true);
//            descPaint.setTypeface(cardDescFont);
            descPaint.setTextSize(15);
            //        descPaint.setStrokeWidth(1f);
            //        descPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            StaticLayout layout = new StaticLayout(card.description, descPaint, spec.descRect.width(), StaticLayout.Alignment.ALIGN_CENTER, 1.0f, 1.0f, false);
            canvas.save();
            canvas.translate(spec.descRect.left, spec.descRect.top + (spec.descRect.height() - layout.getHeight()) / 2);        // vertical align middle
            layout.draw(canvas);
            canvas.restore();
        }


        // draw cost, attack and health/durability numbers
        drawCardNumber(canvas, spec.costPosition, card.cost);
        drawCardNumber(canvas, spec.attackPosition, card.attack);
        drawCardNumber(canvas, spec.healthPosition, card.health);

        // draw quality
        canvas.drawBitmap(cardQualityBitmap,
                CardSpec.getCardQualityRect(card),
                new Rect(spec.qualityPosition.x, spec.qualityPosition.y, spec.qualityPosition.x + CardSpec.CARD_QUALITY_WIDTH, spec.qualityPosition.y + CardSpec.CARD_QUALITY_HEIGHT),
                null);

        // draw elite dragon
        if (card.isMinion() && card.elite) {
            Rect eliteRect = CardSpec.getEliteRect();
            Rect elitePos = new Rect(spec.elitePosition.x, spec.elitePosition.y, spec.elitePosition.x + eliteRect.width(), spec.elitePosition.y + eliteRect.height());
            canvas.drawBitmap(cardQualityBitmap, eliteRect, elitePos, null);
        }

        // draw race indicator
        if (card.isMinion() && card.race != null) {
            Rect raceRect = CardSpec.getRaceRect();
            Rect racePos = new Rect(spec.racePosition.x, spec.racePosition.y, spec.racePosition.x + raceRect.width(), spec.racePosition.y + raceRect.height());
            canvas.drawBitmap(cardQualityBitmap, raceRect, racePos, null);

            String raceText = CardManager.getInstance().getRace(card.race.id);

            // decide font size
            int raceTextSize = 16;
            paint.setTextSize(raceTextSize);
            float raceTextWidth = paint.measureText(raceText);
            while (raceTextWidth > spec.raceTextRect.width()) {
                raceTextSize--;
                raceTextWidth = paint.measureText(raceText);
            }

            int raceTextX = (int) (spec.raceTextRect.left + (spec.raceTextRect.width() - raceTextWidth) / 2);
            int raceTextY = (int) (spec.raceTextRect.bottom);

            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            canvas.drawText(raceText, raceTextX, raceTextY, paint);

            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawText(raceText, raceTextX, raceTextY, paint);
        }

        canvas.restore();
    }


    /**
     * Draw cost, attack and health numbers
     * @param canvas canvas object
     * @param pos position to draw at; set to null will skip the drawing
     * @param number number to draw
     */
    private void drawCardNumber(Canvas canvas, Point pos, int number) {

        if (pos == null) return;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(cardNameFont);
        paint.setTextSize(64);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);

        String text = String.format("%d", number);
        float textWidth = paint.measureText(text);
        canvas.drawText(text, pos.x - textWidth / 2, pos.y, paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, pos.x - textWidth / 2, pos.y, paint);
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

                // load card indicator
                if (cardQualityBitmap == null) {
                    is = getContext().getAssets().open("cards/quality-indicator.png");
                    cardQualityBitmap = BitmapFactory.decodeStream(is);
                    is.close();
                }

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


