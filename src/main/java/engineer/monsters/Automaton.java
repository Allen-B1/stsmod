package engineer.monsters;

import static engineer.BasicMod.imagePath;
import static engineer.BasicMod.makeID;
import static engineer.util.GeneralUtils.makeGreen;
import static engineer.util.GeneralUtils.removePrefix;

import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAndEnableControlsAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.BaseMod;
import engineer.EngineerCharacter;
import engineer.Program;
import engineer.Program.Command;

public abstract class Automaton extends AbstractMonster {
    protected Program program;
    protected Consumer<Automaton> onDeath;

    protected AutomatonButton[] buttons = new AutomatonButton[0];

    public Automaton(String id, String name, int health) {
        super(name, id, health, 0f, 0f, 128f, 128f, imagePath("monsters/" + removePrefix(id) + ".png"));
    }

    public void setOnDeath(Consumer<Automaton> onDeath) {
        this.onDeath = onDeath;
    }

    EngineerCharacter owner; // unavoidable
    public void setProgram(Program program, EngineerCharacter owner) {
        this.owner = owner;
        
        if (program == null) {
            BaseMod.logger.debug("program = null?");
            return;
        }

        this.program = program;
        this.buttons = new AutomatonButton[] {
            new AutomatonButton("activate", "Run | 1 [E]", () -> (activatedThisTurn ? "Already activated." : "Activate " + makeGreen(this.name) + " NL NL " + program.repr()), () -> {
                if (EnergyPanel.totalCount > 0 && this.activate(false)) {
                    AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(1));
                }
            }),
            new AutomatonButton("remove", "Remove | 0 [E]", () -> ("Kill " + makeGreen(this.name)), () -> {
                this.die();
            })
        };
    }
    
    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        for (int i = 0; i < this.buttons.length; i++) {
            AutomatonButton button = buttons[i];

            float x = this.drawX + 64 * Settings.scale, 
                y = this.drawY + 64 * Settings.scale * i;
            button.setX(x);
            button.setY(y);
            button.getHitbox().x = x;
            button.getHitbox().y = y;
//            BaseMod.logger.info(button.getHitbox().width);
//            BaseMod.logger.info(button.getHitbox().height);

            sb.setColor(Color.RED);
            button.getHitbox().render(sb);
            sb.setColor(Color.WHITE); 
            sb.draw(button.getImage(), button.getHitbox().x, button.getHitbox().y, 0f, 0f, button.getHitbox().width, button.getHitbox().height, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0f, 0, 0, 
                button.getImage().getWidth(), button.getImage().getHeight(), false, false);
        }
    }

    @Override
    public void update() {
        super.update();

        for (AutomatonButton button : buttons) {
            button.update();
        }
    }

    @Override
    public void die() {
        super.die(false);
        this.onDeath.accept(this);

        for (Command cmd : program.commands) {
            cmd.onDeath(this, owner);
        }
    }

    @Override
    protected void getMove(int arg0) {}

    @Override
    public void takeTurn() {}

    protected boolean activatedThisTurn = false;

    public boolean activate(boolean force) {
        if (!activatedThisTurn || force) {
            for (Program.Command cmd : program.commands) {
                cmd.execute(this, owner);  
            }

            activatedThisTurn = true;
            return true;
        }

        return false;
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        activatedThisTurn = false;
    }
}
