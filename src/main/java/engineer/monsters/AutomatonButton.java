package engineer.monsters;

import static engineer.BasicMod.imagePath;

import java.util.function.Supplier;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;

import basemod.ClickableUIElement;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.RenderCardDescriptors.Text;

public class AutomatonButton extends ClickableUIElement {
    private String text;
    private Supplier<String> description;
    private Runnable onClick;

    public AutomatonButton(String icon, String text, Supplier<String> description, Runnable onclick) {
        super(new Texture(imagePath(icon + ".png")), 0, 0, 96.0f, 96f);
        this.text = text;
        this.onClick = onclick;
        this.description  = description;
    }

    @Override
    protected void onHover() {
        TipHelper.renderGenericTip(this.x, this.y - 32f * Settings.scale, text, description.get());
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