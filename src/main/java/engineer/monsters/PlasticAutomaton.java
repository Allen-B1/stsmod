package engineer.monsters;

import static engineer.BasicMod.makeID;

public class PlasticAutomaton extends Automaton {
    public final static String ID = makeID("plastic");

    public PlasticAutomaton() {
        super(ID, "Plastic Automaton", 4);
    }
}
