package engineer.cards.common;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import engineer.BasicMod;
import engineer.EngineerCharacter;
import engineer.cards.EngineerCard;
import engineer.monsters.Automaton;
import engineer.monsters.PlasticAutomaton;
import engineer.monsters.SteelAutomaton;

public class SwarfCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("swarf");
    public final static int cost = 1;
    public final static CardType type = CardType.ATTACK;
    public final static CardRarity rarity = CardRarity.COMMON;
    public final static CardTarget target = CardTarget.ENEMY;

    public SwarfCard() {
        super(ID, cost, type, rarity, target);
        baseDamage = 6;
        upgradedDamage = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeDamage(3);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster enemy) {
        addToBot(new DamageAction(enemy, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL)));

        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;

            Automaton automaton = new SteelAutomaton();
            automaton.setProgram(engineer.consumeProgram(), engineer);
            engineer.addAutomaton(automaton);
        }
    }
}
