package com.idv2.HearthMaster.ui.widget;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardClass;
import com.idv2.HearthMaster.model.CardType;

/**
 * Created by charlee on 2014-06-25.
 */
public class CardSpec {


    public static final int CARD_WIDTH = 307;
    public static final int CARD_HEIGHT = 465;

    public static final int CARD_BASE_LABEL_MARGIN = 20;

    public static final int SPELL_ART_WIDTH = 196;
    public static final int SPELL_ART_HEIGHT = 145;
    public static final int MINION_ART_WIDTH = 154;
    public static final int MINION_ART_HEIGHT = 186;
    public static final int WEAPON_ART_WIDTH = 175;
    public static final int WEAPON_ART_HEIGHT = 153;


    public Point artPosition;
    public Path namePath;
    public float namePathLength;

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
     * Card spec for spell cards
     */
    private static CardSpec getSpellCardSpec() {

        if (spellCardSpec == null) {
            CardSpec spec = new CardSpec();

            spec.artPosition = new Point(53, 95);

            RectF oval = new RectF(-241.115f, 258f, 541.115f, 1040.23f);
            spec.namePath = new Path();
            spec.namePath.addArc(oval, 255.186f, 29.628f);

            spec.namePathLength = 202.248f;

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

            spec.artPosition = new Point(76, 64);

            spec.namePath = new Path();
            spec.namePath.moveTo(52.5f, 276.5f);
            spec.namePath.cubicTo(59.5f, 281.75f, 85.5f, 278.25f, 94.25f, 275.75f);
            spec.namePath.cubicTo(105.75f, 272.75f, 132f, 267.75f, 154.25f, 264.75f);
            spec.namePath.cubicTo(190.5f, 259f, 236.75f, 257f, 252.75f, 272f);

            spec.namePathLength = 200f;

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

            spec.artPosition = new Point(71, 82);

            spec.namePath = new Path();
            spec.namePath.moveTo(59, 262);
            spec.namePath.lineTo(255, 262);

            spec.namePathLength = 196f;

            minionCardSpec = spec;

        }
        return minionCardSpec;
    }

}
