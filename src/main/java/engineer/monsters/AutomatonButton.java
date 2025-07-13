package engineer.monsters;

import static engineer.BasicMod.imagePath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;

import basemod.ClickableUIElement;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.RenderCardDescriptors.Text;

public class AutomatonButton extends ClickableUIElement {
    private String name;
    private String program;
    private Runnable onClick;

    public AutomatonButton(String name, String program, Runnable onclick) {
        super(new Texture(imagePath("activate.png")), 0, 0, 96.0f, 96f);
        this.program = program;
        this.onClick = onclick;
        this.name = name;
    }

    @Override
    protected void onHover() {
        TipHelper.renderGenericTip(this.x, this.y - 32f * Settings.scale, name, program);
    }

    @Override
    protected void onUnhover() {}

    @Override
    protected void onClick() {
        this.onClick.run();
    }

    public Hitbox getHitbox() {
        return this.hitbox;
    }

    public Texture getImage() {
        return image;
    }
}