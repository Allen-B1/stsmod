package engineer.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import engineer.BasicMod;
import engineer.cards.EngineerCard;

public class ShieldCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("shield");
    public final static int cost = 1;
    public final static CardType type = CardType.SKILL;
    public final static CardRarity rarity = CardRarity.COMMON;
    public final static CardTarget target = CardTarget.NONE;

    public ShieldCard() {
        super(ID, cost, type, rarity, target);
        baseBlock = 6;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster enemy) {
        addToBot(new GainBlockAction(player, block));
        addToBot(new ApplyPowerAction(player, player, new PlatedArmorPower(player, magicNumber)));
    }
}
