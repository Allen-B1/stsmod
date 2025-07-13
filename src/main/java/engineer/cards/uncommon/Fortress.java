package engineer.cards.uncommon;

import static engineer.BasicMod.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import engineer.EngineerCharacter;
import engineer.Program;
import engineer.Program.BlockCommand;
import engineer.cards.EngineerCard;
import engineer.monsters.Automaton;
import engineer.monsters.SteelAutomaton;

public class Fortress extends EngineerCard {
    public final static String ID = makeID("fortress");
    public final static int cost = 1;
    public final static CardType type = CardType.SKILL;
    public final static CardRarity rarity = CardRarity.UNCOMMON;
    public final static CardTarget target = CardTarget.NONE;

    public Fortress() {
        super(ID, cost, type, rarity, target);
        upgradedCost = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;
            engineer.program.add(new Program.BlockCommand(12));

            Automaton auto = new SteelAutomaton();
            auto.setProgram(engineer.consumeProgram(), engineer);
            engineer.addAutomaton(auto);
        }
    }
}
