package engineer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import engineer.BasicMod;
import engineer.EngineerCharacter;
import engineer.Program;
import engineer.monsters.Automaton;
import engineer.monsters.WoodenAutomaton;

public class DirectCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("direct");
    public final static int cost = 1;
    public final static CardType type = CardType.SKILL;
    public final static CardRarity rarity = CardRarity.BASIC;
    public final static CardTarget target = CardTarget.NONE;

    public DirectCard() {
        super(ID, cost, type, rarity, target);
        upgradedCost = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;
            engineer.program.add(new Program.AttackCommand(6));
        }
    }
}
