package engineer.cards.common.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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

public class CollectCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("collect");
    public final static int cost = 1;
    public final static CardType type = CardType.SKILL;
    public final static CardRarity rarity = CardRarity.COMMON;
    public final static CardTarget target = CardTarget.NONE;

    public CollectCard() {
        super(ID, cost, type, rarity, target);
        baseBlock = 7;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeBlock(3);
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster enemy) {
        addToBot(new GainBlockAction(player, block));
        
        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;
            addToBot(new DrawCardAction(engineer.program.commands.size()));
        }
    }
}
