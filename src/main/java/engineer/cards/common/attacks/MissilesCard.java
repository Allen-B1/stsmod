package engineer.cards.common.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import engineer.BasicMod;
import engineer.cards.EngineerCard;

public class MissilesCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("missiles");
    public final static int cost = 2;
    public final static CardType type = CardType.ATTACK;
    public final static CardRarity rarity = CardRarity.COMMON;
    public final static CardTarget target = CardTarget.ALL_ENEMY;

    public MissilesCard() {
        super(ID, cost, type, rarity, target);
        baseDamage = 7;
        upgradedDamage = true;
        isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeDamage(3);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster __) {
        for (int i = 0; i < 2; i++) {
            addToBot(new DamageAllEnemiesAction(player, multiDamage, DamageType.NORMAL, AttackEffect.BLUNT_LIGHT));
        }
    }
}
