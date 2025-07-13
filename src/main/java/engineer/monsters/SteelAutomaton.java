package engineer.monsters;

import static engineer.BasicMod.makeID;

public class SteelAutomaton extends Automaton {
    public final static String ID = makeID("steel");

    public SteelAutomaton() {
        super(ID, "Steel Automaton", 16); // TODO
        this.currentHealth = 8;
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();

        this.currentHealth += 2;
        if (this.currentHealth >= this.maxHealth) {
            this.currentHealth = this.maxHealth;
        }
    }
}
