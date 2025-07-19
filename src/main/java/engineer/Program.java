package engineer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.WeakPower;

import engineer.monsters.Automaton;

public class Program {
    public static interface Command {
        void execute(Automaton source, EngineerCharacter player);
        default void onDeath(Automaton source, EngineerCharacter player) {}
        String repr();
    }

    public static class AttackCommand implements Command {
        public final int dmg;

        public AttackCommand(int dmg) {
            this.dmg = dmg;
        }

        @Override
        public void execute(Automaton source, EngineerCharacter player) {
            AbstractMonster target = AbstractDungeon.getMonsters().monsters.stream().max((a, b) -> {
                return a.currentHealth - b.currentHealth;
            }).get();

            if (target != null) {
                DamageInfo info = new DamageInfo(source, dmg, DamageInfo.DamageType.NORMAL);
                info.applyPowers(source, target);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info, dmg >= 12 ? AttackEffect.SLASH_HEAVY : AttackEffect.SLASH_HORIZONTAL));
            }
        }

        @Override
        public String repr() {
            return "Attack #b" + Integer.toString(dmg);
        }
    }

    public static class SprayCommand implements Command {
        public final int dmg;

        public SprayCommand(int dmg) {
            this.dmg = dmg;
        }

        @Override
        public void execute(Automaton source, EngineerCharacter player) {
            MonsterGroup monsters = AbstractDungeon.getMonsters();
            for (AbstractMonster target : monsters.monsters) {
                DamageInfo info = new DamageInfo(source, dmg, DamageInfo.DamageType.NORMAL);
                info.applyPowers(source, target);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            }
        }

        @Override
        public String repr() {
            return "Spray #b" + Integer.toString(dmg);
        }
    }

    public static class DefenseCommand implements Command {
        public final int block;

        public DefenseCommand(int block) {
            this.block = block;
        }

        @Override
        public void execute(Automaton source, EngineerCharacter player) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(source, block));
        }

        @Override
        public void onDeath(Automaton source, EngineerCharacter player) {
            for (Automaton auto : player.automatons) {
                if (auto != null && auto != source) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(auto, block));
                }
            }

            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, block));
        }

        @Override
        public String repr() {
            return "Defense #b" + Integer.toString(block);
        }
    }

    public static class WeakenCommand implements Command {
        public WeakenCommand() {}

        @Override
        public void execute(Automaton source, EngineerCharacter player) {
            MonsterGroup monsters = AbstractDungeon.getMonsters();
            for (AbstractMonster target : monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, 
                    new WeakPower(target, 1, false)
                ));
            }
        }

        @Override
        public String repr() {
            return "Weaken";
        }
    }

    public static class ExplodeCommand implements Command {
        public ExplodeCommand() {}

        @Override
        public void execute(Automaton source, EngineerCharacter player) {
            AbstractDungeon.actionManager.addToBottom(new InstantKillAction(source));

            MonsterGroup monsters = AbstractDungeon.getMonsters();
            for (AbstractMonster target : monsters.monsters) {
                DamageInfo info = new DamageInfo(null, 24, DamageInfo.DamageType.NORMAL);
                info.applyPowers(source, target);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info, AttackEffect.FIRE));
            }
        }

        @Override
        public String repr() {
            return "Explode";
        }
    }

    public ArrayList<Command> commands;

    public Program() {
        commands = new ArrayList<Command>();
    }

    public void add(Command command) {
        commands.add(command);
        reorder();
    }

    protected void reorder() {
        Set<Command> explodes = commands.stream().filter(x -> x instanceof ExplodeCommand).collect(Collectors.toSet());
        for (Command cmd : explodes) {
            commands.remove(cmd);
            commands.add(cmd);
        }
    }

    public Program copy() {
        Program program = new Program();
        program.commands.addAll(this.commands);
        return program;
    }

    public String repr() {
        return "- " + String.join(" NL - ", this.commands.stream().map(x -> x.repr()).collect(Collectors.toList()));
    }
}
