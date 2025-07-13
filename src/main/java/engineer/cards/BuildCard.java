package engineer.cards;

import static engineer.BasicMod.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import engineer.EngineerCharacter;
import engineer.Program;
import engineer.monsters.Automaton;
import engineer.monsters.WoodenAutomaton;

public class BuildCard extends EngineerCard {
    public final static String ID = makeID("build");
    public final static int cost = 1;
    public final static CardType type = CardType.SKILL;
    public final static CardRarity rarity = CardRarity.BASIC;
    public final static CardTarget target = CardTarget.NONE;

    public BuildCard() {
        super(ID, cost, type, rarity, target);
        upgradedCost = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;

            Automaton automaton = new WoodenAutomaton();
            automaton.setProgram(engineer.consumeProgram(), engineer);
            engineer.addAutomaton(automaton);
        }
    }
}
