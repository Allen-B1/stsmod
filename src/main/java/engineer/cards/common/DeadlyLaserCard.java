package engineer.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import engineer.BasicMod;
import engineer.EngineerCharacter;
import engineer.Program;
import engineer.cards.EngineerCard;

public class DeadlyLaserCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("deadlylaser");
    public final static int cost = 1;
    public final static CardType type = CardType.SKILL;
    public final static CardRarity rarity = CardRarity.COMMON;
    public final static CardTarget target = CardTarget.NONE;

    public DeadlyLaserCard() {
        super(ID, cost, type, rarity, target);
        baseMagicNumber = magicNumber = 12;
    }

    @Override
    public void upgrade() {
        super.upgrade();

        upgradeMagicNumber(4);
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster enemy) {
        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;
            engineer.program.add(new Program.AttackCommand(magicNumber));
        }
    }
}
