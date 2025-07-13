package engineer;

import static engineer.BasicMod.characterPath;
import static engineer.BasicMod.makeID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.green.Neutralize;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.BurningBlood;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import basemod.BaseMod;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import engineer.cards.BuildCard;
import engineer.cards.DirectCard;
import engineer.cards.common.FrenzyCard;
import engineer.monsters.Automaton;
import engineer.powers.ProgrammerPower;
import engineer.relics.Blueprint;

public class EngineerCharacter extends CustomPlayer {
    public final static String ID = makeID("engineer");

    public final static String[] NAMES = CardCrawlGame.languagePack.getCharacterString(ID).NAMES;
    public final static String[] TEXT = CardCrawlGame.languagePack.getCharacterString(ID).TEXT;

    public static class Meta {
        @SpireEnum
        public static PlayerClass ENGINEER_CHARACTER;

        @SpireEnum(name="ENGINEER_COLOR")
        public static AbstractCard.CardColor CARD_COLOR;

        @SpireEnum(name="ENGINEER_COLOR")
        public static CardLibrary.LibraryType LIBRARY_COLOR;

        public static void registerColor() {
            BaseMod.addColor(CARD_COLOR, 
                new Color(0.5f, 0.5f, 0.5f, 1),
                characterPath("cardback/bg_attack.png"),
                characterPath("cardback/bg_skill.png"),
                characterPath("cardback/bg_power.png"),
                characterPath("cardback/energy_orb.png"),
                characterPath("cardback/bg_attack_p.png"),
                characterPath("cardback/bg_skill_p.png"),
                characterPath("cardback/bg_power_p.png"),
                characterPath("cardback/energy_orb_p.png"),
                characterPath("cardback/small_orb.png")
            );
        }

        public static void registerCharacter() {
            BaseMod.addCharacter(new EngineerCharacter(), 
                characterPath("select/button.png"),
                characterPath("select/portrait.png"), 
                ENGINEER_CHARACTER);
        }
    }

    public final static String[] orbTextures = new String[] {
        characterPath("energyorb/layer1.png"),
        characterPath("energyorb/layer2.png"),
        characterPath("energyorb/layer3.png"),
        characterPath("energyorb/layer4.png"),
        characterPath("energyorb/layer5.png"),
        characterPath("energyorb/cover.png"),
        characterPath("energyorb/layer1d.png"),
        characterPath("energyorb/layer2d.png"),
        characterPath("energyorb/layer3d.png"),
        characterPath("energyorb/layer4d.png"),
        characterPath("energyorb/layer5d.png")
    };
    public final static float[] layerSpeeds = new float[] {-20f, 20f, -40f, 40f, 30f};

    public EngineerCharacter() {
        super(NAMES[0], Meta.ENGINEER_CHARACTER, 
            new CustomEnergyOrb(orbTextures, characterPath("energyorb/vfx.png"), layerSpeeds),            
            new SpriterAnimation(characterPath("animation/default.scml")));
        initializeClass(null, characterPath("shoulder.png"), characterPath("shoulder2.png"), 
            characterPath("corpse.png"), getLoadout(), 20f, -20f, 200f, 250f,
            new EnergyManager(3));

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> deck = new ArrayList<String>();
/*        for (int i = 0; i < 4; i++) {
            deck.add(Strike_Blue.ID);
            deck.add(Defend_Blue.ID);
        }*/

        deck.add(BuildCard.ID);
        deck.add(DirectCard.ID);
        deck.add(FrenzyCard.ID);
        return deck;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> relics = new ArrayList<String>();
        relics.add(Blueprint.ID);
        return relics;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Neutralize();
    }


    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        };
    }

    public final Color characterColor = Color.LIGHT_GRAY.cpy();
    @Override
    public Color getCardRenderColor() {
        return characterColor;
    }
    @Override
    public Color getCardTrailColor() {
        return characterColor;
    }
    @Override
    public Color getSlashAttackColor() {
        return characterColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_2", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_2";
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                70, 70, 0, 99, 5, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Meta.CARD_COLOR;
    }

    @Override
    public AbstractPlayer newInstance() {
        //Makes a new instance of your character class.
        return new EngineerCharacter();
    }

    /** Automatons */
    public Program program = new Program();
    protected Automaton[] automatons = new Automaton[8];

    public boolean addAutomaton(Automaton automaton) {
        for (int i = 0; i < automatons.length; i++) {
            if (automatons[i] == null) {
                automaton.init();
                automaton.usePreBattleAction();
                automaton.showHealthBar();

                automatons[i] = automaton;
                automaton.drawX = this.drawX + this.hb_w + 32 * Settings.scale;
                automaton.drawY = this.drawY + Settings.scale*(192*i - 64);

                automaton.setOnDeath(auto -> relinquishAutomaton(auto));

                return true;
            }
        }

        return false;
    }

    // Remove Automaton from list.
    private void relinquishAutomaton(Automaton automaton) {
        int i = Arrays.asList(automatons).indexOf(automaton);
        if (i < 0) {
            return;
        }

        automatons[i] = null;
    }
 
    @Override
    public void updatePowers() {
        super.updatePowers();
        eachAuto(auto -> auto.updatePowers());
    }

    @Override
    public void applyTurnPowers() {
        super.applyTurnPowers();
        eachAuto(auto -> auto.applyTurnPowers());
    }

    @Override
    public void applyStartOfTurnPowers() {
        super.applyStartOfTurnPowers();
        eachAuto(auto -> auto.applyStartOfTurnPowers());
        eachAuto(auto -> auto.loseBlock());

        if (powers.stream().filter(p -> p instanceof ProgrammerPower).count() == 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ProgrammerPower(this)));
        }
    }

    @Override
    public void applyStartOfTurnPostDrawPowers() {
        super.applyStartOfTurnPostDrawPowers();
        eachAuto(auto -> auto.applyStartOfTurnPostDrawPowers());
    }

    @Override
    public void applyEndOfTurnTriggers() {
        BaseMod.logger.info("apply end of turn triggers");
        super.applyEndOfTurnTriggers();
        eachAuto((auto) -> auto.applyEndOfTurnTriggers());
        eachAuto(auto -> auto.powers.forEach(power -> power.atEndOfRound()));
    }
    

    @Override
    public void render(final SpriteBatch sb) {
        BaseMod.logger.info("render");
        super.render(sb);

        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            eachAuto((auto) -> auto.render(sb));
        }
    }

    @Override
    public void update() {
        BaseMod.logger.info("update");
        super.update();

        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            eachAuto((auto) -> auto.update());
        }
    }

    protected void eachAuto(Consumer<Automaton> f) {
        for (Automaton auto : automatons) {
            if (auto != null) {
                f.accept(auto);
            }
        }
    }

    public Program consumeProgram() {
        Program program = this.program;
        this.program = new Program();
        return program;
    }

    public void clearAutomatons() {
        BaseMod.logger.info("clearing automatons");
        automatons = new Automaton[8];
        program = new Program();
    }

    @Override
    public void damage(DamageInfo info) {
        if (!(info.owner instanceof AbstractMonster) || info.type != DamageType.NORMAL) {
            super.damage(info);
            return;
        }

        AbstractMonster source = (AbstractMonster)info.owner;
        List<AbstractCreature> targets = Arrays.asList(automatons).stream().filter(x -> x != null).collect(Collectors.toList());
        targets.add(this);

        int[] scores = new int[targets.size()];
        for (int i = 0; i < targets.size(); i++) {
            int output = info.output - targets.get(i).currentBlock;
            if (output >= targets.get(i).currentHealth) {
                output = targets.get(i).currentHealth;
                output += (targets.get(i).maxHealth <= 12 ? 3 : targets.get(i).maxHealth / 4); // bonus for killing enemy
            }
            if (output < 0) {
                output = 0;
            }

            scores[i] = output;
        }

        int maxScore = -1;
        int maxScoreIdx = -1;
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] > maxScore) {
                maxScore = scores[i];
                maxScoreIdx = i;
            }
        }

        if (targets.get(maxScoreIdx) == this) {
            super.damage(info);
            return;
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAction(targets.get(maxScoreIdx), info, AbstractGameAction.AttackEffect.NONE));
    }
}
