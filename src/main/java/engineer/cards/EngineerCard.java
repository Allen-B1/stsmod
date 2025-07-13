package engineer.cards;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.abstracts.DynamicVariable;
import engineer.BasicMod;
import engineer.EngineerCharacter;
import engineer.util.TriFunction;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static engineer.BasicMod.imagePath;
import static engineer.util.GeneralUtils.removePrefix;
import static engineer.util.TextureLoader.getCardTextureString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class EngineerCard extends CustomCard {
    private String cardID;
    public EngineerCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, EngineerCharacter.Meta.CARD_COLOR);
    }

    public EngineerCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, 
            CardCrawlGame.languagePack.getCardStrings(cardID).NAME,
            genericImage(type), cost,
            CardCrawlGame.languagePack.getCardStrings(cardID).DESCRIPTION,
            type, color, rarity, target);
        this.cardID = cardID;
    }

    private static String genericImage(final CardType type) {
        String image = null;
        switch (type) {
            case SKILL:
                image = imagePath("cards/skill/default.png");
                break;
            case POWER:
                image = imagePath("cards/power/default.png");
                break;
            case ATTACK:
                image = imagePath("cards/attack/default.png");
                break;
            default:
                image = imagePath("missing.png");
        }
        return image;
    }

    @Override
    public void upgrade() {
        if (upgraded) {
            return;
        }

        upgradeName();
        
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);
        if (cardStrings.UPGRADE_DESCRIPTION != null) {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }

        if (upgradedCost) {
            upgradeBaseCost(cost - 1);
        }
    }
}