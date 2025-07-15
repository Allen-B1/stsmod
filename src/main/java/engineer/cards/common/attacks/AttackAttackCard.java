package engineer.cards.common.attacks;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import engineer.BasicMod;
import engineer.EngineerCharacter;
import engineer.Program;
import engineer.cards.EngineerCard;

public class AttackAttackCard extends EngineerCard {
    public final static String ID = BasicMod.makeID("attackattack");
    public final static int cost = 1;
    public final static CardType type = CardType.ATTACK;
    public final static CardRarity rarity = CardRarity.COMMON;
    public final static CardTarget target = CardTarget.ENEMY;

    public AttackAttackCard() {
        super(ID, cost, type, rarity, target);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 4;
    }

    @Override
    public void upgrade() {
        super.upgrade();

        upgradeDamage(2);
        upgradeMagicNumber(2);    
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster enemy) {
        addToBot(new DamageAction(enemy, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL)));
        
        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;
            engineer.program.add(new Program.AttackCommand(magicNumber));
        }
    }
}
