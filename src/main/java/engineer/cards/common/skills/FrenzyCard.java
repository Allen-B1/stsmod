package engineer.cards.common.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.BaseMod;
import engineer.BasicMod;
import engineer.EngineerCharacter;
import engineer.Program;
import engineer.cards.EngineerCard;
import engineer.monsters.Automaton;
import engineer.monsters.PlasticAutomaton;
import engineer.monsters.SteelAutomaton;

public class FrenzyCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("frenzy");
    public final static int cost = 2;
    public final static CardType type = CardType.SKILL;
    public final static CardRarity rarity = CardRarity.COMMON;
    public final static CardTarget target = CardTarget.NONE;

    public FrenzyCard() {
        super(ID, cost, type, rarity, target);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster enemy) {
        BaseMod.logger.info("magicNumber : " + magicNumber + " / " + baseMagicNumber);

        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;

            Program program = engineer.consumeProgram();
            for (int i = 0; i < magicNumber; i++) {
                Automaton automaton = new PlasticAutomaton();
                automaton.setProgram(program, engineer);    
                engineer.addAutomaton(automaton);
            }
        }
    }
}
