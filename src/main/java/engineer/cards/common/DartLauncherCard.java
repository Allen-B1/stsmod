package engineer.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import engineer.BasicMod;
import engineer.EngineerCharacter;
import engineer.Program;
import engineer.cards.EngineerCard;

public class DartLauncherCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("dartlauncher");
    public final static int cost = 1;
    public final static CardType type = CardType.SKILL;
    public final static CardRarity rarity = CardRarity.COMMON;
    public final static CardTarget target = CardTarget.NONE;

    public DartLauncherCard() {
        super(ID, cost, type, rarity, target);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeBaseCost(0);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster enemy) {
        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;
            engineer.program.add(new Program.SprayCommand(4));
            engineer.program.add(new Program.WeakenCommand());
        }
    }
}
