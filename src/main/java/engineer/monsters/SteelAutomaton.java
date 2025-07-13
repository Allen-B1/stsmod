package engineer.monsters;

import static engineer.BasicMod.makeID;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SteelAutomaton extends Automaton {
    public final static String ID = makeID("steel");

    public SteelAutomaton() {
        super(ID, "Steel Automaton", 16); // TODO
        this.currentHealth = 8;
    }

    @Override
    public void applyStartOfTurnPowers() {
        super.applyStartOfTurnPowers();

        AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, 2));
    }
}
