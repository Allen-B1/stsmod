package engineer.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.BaseMod;
import engineer.BasicMod;
import engineer.EngineerCharacter;

public class ProgrammerPower extends BasePower {
    public final static String ID = BasicMod.makeID("programmer");
    public ProgrammerPower(AbstractCreature owner) {
        super(ID, AbstractPower.PowerType.BUFF, false, owner, 1);
    }

    public void updateDescription() {
        this.description = generateDescription();
    }

    protected String generateDescription() {
        if (owner instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)owner;
            if (engineer.program.commands.size() == 0) {
                return "Empty"; // TODO
            } else {
                return engineer.program.repr();
            }
        }
        return "Empty"; //
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        super.onAfterCardPlayed(usedCard);
        this.description = generateDescription();
    }
}
