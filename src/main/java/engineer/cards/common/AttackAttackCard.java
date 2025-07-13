package engineer.cards.common;

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
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster enemy) {
        addToBot(new DamageAction(enemy, new DamageInfo(player, upgraded ? 8 : 6, DamageInfo.DamageType.NORMAL)));
        
        if (player instanceof EngineerCharacter) {
            EngineerCharacter engineer = (EngineerCharacter)player;
            engineer.program.add(new Program.AttackCommand(upgraded ? 6 : 4));
        }
    }
}
