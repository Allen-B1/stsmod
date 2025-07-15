package engineer.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import engineer.BasicMod;
import engineer.EngineerCharacter;
import engineer.Program;
import engineer.cards.EngineerCard;

public class BombCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("bomb");
    public final static int cost = 2;
    public final static CardType type = CardType.SKILL;
    public final static CardRarity rarity = CardRarity.UNCOMMON;
    public final static CardTarget target = CardTarget.NONE;

    public BombCard() {
        super(ID, cost, type, rarity, target);
        baseBlock = 10;
        upgradedCost = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster enemy) {
        addToBot(new GainBlockAction(player, baseBlock));

        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;
            engineer.program.add(new Program.ExplodeCommand());
        }
    }
}
