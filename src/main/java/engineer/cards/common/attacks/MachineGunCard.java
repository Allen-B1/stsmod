package engineer.cards.common.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import engineer.BasicMod;
import engineer.cards.EngineerCard;

public class MachineGunCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("machinegun");
    public final static int cost = 1;
    public final static CardType type = CardType.ATTACK;
    public final static CardRarity rarity = CardRarity.COMMON;
    public final static CardTarget target = CardTarget.NONE;

    public MachineGunCard() {
        super(ID, cost, type, rarity, target);
        baseDamage = 2;
        baseMagicNumber = magicNumber = 4;
        upgradedMagicNumber = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeMagicNumber(2);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster __) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DamageRandomEnemyAction(new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL));
        }
    }
}
