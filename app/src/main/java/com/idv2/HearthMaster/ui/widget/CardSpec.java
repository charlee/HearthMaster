package com.idv2.HearthMaster.ui.widget;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardClass;
import com.idv2.HearthMaster.model.CardType;
import com.idv2.HearthMaster.model.Quality;

/**
 * Created by charlee on 2014-06-25.
 */
public class CardSpec {


    public static final int CARD_WIDTH = 307;
    public static final int CARD_HEIGHT = 465;
    public static final int CARD_QUALITY_WIDTH = 48;
    public static final int CARD_QUALITY_HEIGHT = 48;

    public static final int CARD_BASE_LABEL_MARGIN = 20;

    public int artWidth;
    public int artHeight;
    public Point artPosition;
    public Path namePath;
    public float namePathLength;
    public Rect descRect;
    public Point costPosition;      // bottom-middle point
    public Point attackPosition;      // bottom-middle point
    public Point healthPosition;    // bottom-middle point
    public Point qualityPosition;   // left-top corner
    public Point elitePosition;     // elite dragon position
    public Point racePosition;      // race indicator position
    public Rect raceTextRect;

    private static CardSpec spellCardSpec = null;
    private static CardSpec weaponCardSpec = null;
    private static CardSpec minionCardSpec = null;

    public static CardSpec getCardSpec(Card card) {
        CardSpec spec;
        if (card.isMinion()) spec = getMinionCardSpec();
        else if (card.isSpell()) spec = getSpellCardSpec();
        else spec = getWeaponCardSpec();

        return spec;
    }

    /**
     * Get card position
     * @param card card object
     * @return
     */
    public static Rect getCardBaseRect(Card card) {

        int xPos, yPos;

        if (card.cardClass == null) {           // neutral
            xPos = 0;
        } else {
            switch (card.cardClass.id) {
                case CardClass.WARRIOR: xPos = 1; break;
                case CardClass.PALADIN: xPos = 2; break;
                case CardClass.HUNTER: xPos = 3; break;
                case CardClass.ROGUE: xPos = 4; break;
                case CardClass.PRIEST: xPos = 5; break;
                case CardClass.SHAMAN: xPos = 6; break;
                case CardClass.MAGE: xPos = 7; break;
                case CardClass.WARLOCK: xPos = 8; break;
                case CardClass.DRUID: xPos = 9; break;
                default: xPos = 0;
            }
        }

        if (card.cardType == null) {
            yPos = 0;
        } else {
            switch (card.cardType.id) {
                case CardType.MINION: yPos = 0; break;
                case CardType.SPELL: yPos = 1; break;
                case CardType.WEAPON: yPos = 2; break;
                default: yPos = 0; break;
            }
        }

        int x = xPos * CARD_WIDTH + CARD_BASE_LABEL_MARGIN;
        int y = yPos * CARD_HEIGHT + CARD_BASE_LABEL_MARGIN;

        return new Rect(x, y, x + CARD_WIDTH, y + CARD_HEIGHT);

    }

    /**
     * Get Card quality indicator position
     * @param card
     * @return return null for a free card
     */
    public static Rect getCardQualityRect(Card card) {

        int xPos, yPos;

        if (card.quality == null || card.quality.id == Quality.FREE) {
            return null;
        } else {

            switch (card.quality.id) {
                case Quality.COMMON: xPos = 0; break;
                case Quality.RARE: xPos = 1; break;
                case Quality.EPIC: xPos = 2; break;
                case Quality.LEGENDARY: xPos = 3; break;
                default: xPos = 0; break;
            }
        }

        if (card.cardType == null) {
            return null;
        } else {
            switch (card.cardType.id) {
                case CardType.SPELL: yPos = 0; break;
                case CardType.WEAPON: yPos = 1; break;
                case CardType.MINION: yPos = 2; break;
                default: yPos = 2; break;
            }
        }

        int x = xPos * CARD_QUALITY_WIDTH + CARD_BASE_LABEL_MARGIN;
        int y = yPos * CARD_QUALITY_HEIGHT + CARD_BASE_LABEL_MARGIN;

        return new Rect(x, y, x + CARD_QUALITY_WIDTH, y + CARD_QUALITY_HEIGHT);
    }

    /**
     * Get the rect of the elite indicator
     * @return
     */
    public static Rect getEliteRect() {
        return new Rect(20, 184, 234, 342);
    }

    /**
     * Get the rect of the race indicator
     * @return
     */
    public static Rect getRaceRect() {
        return new Rect(20, 362, 168, 395);
    }

    /**
     * Card spec for spell cards
     */
    private static CardSpec getSpellCardSpec() {

        if (spellCardSpec == null) {
            CardSpec spec = new CardSpec();

            // start point for the art image
            spec.artPosition = new Point(53, 95);

            // width and height for the art image
            spec.artWidth = 196;
            spec.artHeight = 145;


            // path for the name txt
            RectF oval = new RectF(-241.115f, 258f, 541.115f, 1040.23f);
            spec.namePath = new Path();
            spec.namePath.addArc(oval, 255.186f, 29.628f);

            spec.namePathLength = 202.248f;

            spec.descRect = new Rect(64, 315, 236, 392);


            spec.costPosition = new Point(49, 112);
            spec.attackPosition = null;
            spec.healthPosition = null;

            spec.qualityPosition = new Point(128, 269);

            spellCardSpec = spec;
        }

        return spellCardSpec;
    }

    /**
     * Card spec for minion cards
     *
     * @return
     */
    private static CardSpec getMinionCardSpec() {

        if (minionCardSpec == null) {
            CardSpec spec = new CardSpec();

            // start point for the art image
            spec.artPosition = new Point(76, 64);

            // width and height for the art image
            spec.artWidth = 154;
            spec.artHeight = 186;

            // path for the name txt
            spec.namePath = new Path();
            spec.namePath.moveTo(52.5f, 276.5f);
            spec.namePath.cubicTo(59.5f, 281.75f, 85.5f, 278.25f, 94.25f, 275.75f);
            spec.namePath.cubicTo(105.75f, 272.75f, 132f, 267.75f, 154.25f, 264.75f);
            spec.namePath.cubicTo(190.5f, 259f, 236.75f, 257f, 252.75f, 272f);

            spec.namePathLength = 200f;

            spec.descRect = new Rect(64, 315, 236, 392);

            spec.costPosition = new Point(49, 112);
            spec.attackPosition = new Point(50, 427);
            spec.healthPosition = new Point(261, 427);

            spec.qualityPosition = new Point(128, 265);

            spec.elitePosition = new Point(79, 36);
            spec.racePosition = new Point(85, 390);
            spec.raceTextRect = new Rect(98, 402, 209, 414);


            minionCardSpec = spec;
        }

        return minionCardSpec;
    }

    /**
     * Card spec for weapon cards
     *
     * @return
     */
    private static CardSpec getWeaponCardSpec() {

        if (weaponCardSpec == null) {

            CardSpec spec = new CardSpec();

            // start point for the art image
            spec.artPosition = new Point(71, 82);

            // width and height for the art image
            spec.artWidth = 175;
            spec.artHeight = 153;

            // path for the name txt
            spec.namePath = new Path();
            spec.namePath.moveTo(59, 262);
            spec.namePath.lineTo(255, 262);

            spec.namePathLength = 196f;

            spec.descRect = new Rect(64, 315, 236, 392);

            spec.costPosition = new Point(49, 112);
            spec.attackPosition = new Point(50, 427);
            spec.healthPosition = new Point(261, 427);

            spec.qualityPosition = new Point(128, 269);

            weaponCardSpec = spec;

        }
        return weaponCardSpec;
    }

}
