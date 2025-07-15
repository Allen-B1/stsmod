package engineer.cards.common.attacks;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
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

public class NeedlesCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("needles");
    public final static int cost = 0;
    public final static CardType type = CardType.ATTACK;
    public final static CardRarity rarity = CardRarity.COMMON;
    public final static CardTarget target = CardTarget.ALL_ENEMY;

    public NeedlesCard() {
        super(ID, cost, type, rarity, target);
        baseDamage = 1;
        baseMagicNumber = magicNumber = 3;
        isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeMagicNumber(2);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster __) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DamageAllEnemiesAction(player, multiDamage, DamageType.NORMAL, AttackEffect.BLUNT_LIGHT));
        }

        addToBot(new AllEnemyApplyPowerAction(player, 1, (m) -> new WeakPower(m, 1, false)));
    }
}
