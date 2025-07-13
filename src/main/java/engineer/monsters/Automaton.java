package engineer.monsters;

import static engineer.BasicMod.imagePath;
import static engineer.BasicMod.makeID;
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

public abstract class Automaton extends AbstractMonster {
    protected Program program;
    protected Consumer<Automaton> onDeath;
    protected AutomatonButton button;

    public Automaton(String id, String name, int health) {
        super(name, id, health, 0f, 0f, 128f, 128f, imagePath("monsters/" + removePrefix(id) + ".png"));
    }

    public void setOnDeath(Consumer<Automaton> onDeath) {
        this.onDeath = onDeath;
    }

    public void setProgram(Program program, EngineerCharacter player) {
        if (program == null) {
            BaseMod.logger.debug("program = null?");
            return;
        }

        this.program = program;
        this.button = new AutomatonButton(this.name, program.repr(), () -> {
            if (EnergyPanel.totalCount > 0) {
                AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(1));
                this.activate(player);
            }
        });
    }

    public void activate(EngineerCharacter player) {
        for (Program.Command cmd : program.commands) {
            cmd.execute(this, player);  
        }
    }
    
    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        if (this.button != null) {
            this.button.setX(this.drawX + 96 * Settings.scale);
            this.button.setY(this.drawY);
            button.getHitbox().x = this.drawX + 64 * Settings.scale;
            button.getHitbox().y = this.drawY - 64 * Settings.scale;
//            BaseMod.logger.info(button.getHitbox().width);
//            BaseMod.logger.info(button.getHitbox().height);

            sb.setColor(Color.RED);
            this.button.getHitbox().render(sb);
            sb.setColor(Color.WHITE); 
            sb.draw(button.getImage(), button.getHitbox().x, button.getHitbox().y, 48f, 48f, 96f, 96f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0f, 0, 0, 
                button.getImage().getWidth(), button.getImage().getHeight(), false, false);
        }
    }

    @Override
    public void update() {
        super.update();

        if (this.button != null) {
            this.button.update();
        }
    }

    @Override
    public void die() {
        super.die(false);
        this.onDeath.accept(this);
    }

    @Override
    protected void getMove(int arg0) {}

    @Override
    public void takeTurn() {}
}
