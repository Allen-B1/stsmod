package engineer.cards.uncommon;

import static engineer.BasicMod.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import engineer.EngineerCharacter;
import engineer.Program;
import engineer.cards.EngineerCard;
import engineer.monsters.Automaton;
import engineer.monsters.PlasticAutomaton;
import engineer.monsters.SteelAutomaton;
import engineer.monsters.WoodenAutomaton;

public class CraftsCard extends EngineerCard {
    public final static String ID = makeID("crafts");
    public final static int cost = 2;
    public final static CardType type = CardType.SKILL;
    public final static CardRarity rarity = CardRarity.UNCOMMON;
    public final static CardTarget target = CardTarget.NONE;

    public CraftsCard() {
        super(ID, cost, type, rarity, target);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeMagicNumber(1);   
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;
            Program program = engineer.consumeProgram();

            Automaton[] autos;
            if (upgraded) {
                autos = new Automaton[] {
                    new PlasticAutomaton(),
                    new WoodenAutomaton(),
                    new SteelAutomaton()
                };
            } else {
                autos = new Automaton[] {
                    new PlasticAutomaton(), new PlasticAutomaton(),
                    new WoodenAutomaton(), new WoodenAutomaton(),
                    new SteelAutomaton()
                };
            }

            for (Automaton auto : autos) {
                auto.setProgram(program, engineer);
                engineer.addAutomaton(auto);    
            }
        }
    }
}
