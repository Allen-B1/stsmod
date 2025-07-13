package engineer.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import engineer.BasicMod;
import engineer.cards.EngineerCard;

public class TazerCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("tazer");
    public final static int cost = 1;
    public final static CardType type = CardType.ATTACK;
    public final static CardRarity rarity = CardRarity.COMMON;
    public final static CardTarget target = CardTarget.ENEMY;

    public TazerCard() {
        super(ID, cost, type, rarity, target);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster enemy) {
        addToBot(new DamageAction(enemy, new DamageInfo(player, upgraded ? 7 : 5, DamageInfo.DamageType.NORMAL)));
        addToBot(new ApplyPowerAction(enemy, player, new WeakPower(enemy, upgraded ? 2 : 1, false)));
        addToBot(new ApplyPowerAction(enemy, player, new VulnerablePower(enemy, upgraded ? 2 : 1, false)));
    }
}
