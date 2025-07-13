package engineer.cards.uncommon;

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
import engineer.monsters.Automaton;
import engineer.monsters.PlasticAutomaton;

public class FrenzyCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("frenzy");
    public final static int cost = 2;
    public final static CardType type = CardType.SKILL;
    public final static CardRarity rarity = CardRarity.UNCOMMON;
    public final static CardTarget target = CardTarget.NONE;

    public FrenzyCard() {
        super(ID, cost, type, rarity, target);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.exhaust = false;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster enemy) {
        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;

            Program program = engineer.consumeProgram();
            for (int i = 0; i < 3; i++) {
                Automaton automaton = new PlasticAutomaton();
                automaton.setProgram(program, engineer);    
                engineer.addAutomaton(automaton);
            }
        }
    }
}
