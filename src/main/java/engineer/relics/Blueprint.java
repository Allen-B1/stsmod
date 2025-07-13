package engineer.relics;

import static engineer.BasicMod.imagePath;
import static engineer.BasicMod.relicPath;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomRelic;
import engineer.BasicMod;
import engineer.EngineerCharacter;
import engineer.Program;
import engineer.util.TextureLoader;

public class Blueprint extends CustomRelic {
    public final static String ID = BasicMod.makeID("blueprint");

    public Blueprint() {
        super(ID, TextureLoader.getTexture(relicPath("blueprint.png")), TextureLoader.getTexture(relicPath("blueprint.png")), RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.player instanceof EngineerCharacter) {
            ((EngineerCharacter)AbstractDungeon.player).program.add(new Program.AttackCommand(6));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
