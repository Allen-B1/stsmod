package engineer.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import engineer.BasicMod;
import engineer.EngineerCharacter;
import engineer.cards.EngineerCard;
import engineer.monsters.Automaton;
import engineer.monsters.SteelAutomaton;

public class DownloadCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("download");
    public final static int cost = 1;
    public final static CardType type = CardType.SKILL;
    public final static CardRarity rarity = CardRarity.COMMON;
    public final static CardTarget target = CardTarget.NONE;

    public DownloadCard() {
        super(ID, cost, type, rarity, target);
        upgradedCost = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;

            Automaton automaton = new SteelAutomaton();
            automaton.setProgram(engineer.program.copy(), engineer);
            engineer.addAutomaton(automaton);
        }
    }
}
