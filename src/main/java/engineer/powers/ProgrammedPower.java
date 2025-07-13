/*package engineer.powers;

import static engineer.BasicMod.makeID;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import engineer.Program;
import engineer.Program.Command;

public class ProgrammedPower extends BasePower {
    public static final String ID = makeID("programmed");
    protected Program program;
    
    public ProgrammedPower(AbstractCreature owner, Program program) {
        super(ID, AbstractPower.PowerType.BUFF, false, owner, 1);
        this.program = program;
    }

    public void updateDescription() {
        if (program == null) {
            this.description = "N/A";
            return;
        }

        String desc = "";
        for (Command cmd : program.commands) {
            desc += " NL " + cmd.repr();
        }
        this.description = desc;
    }
}
*/