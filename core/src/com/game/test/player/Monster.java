package com.game.test.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.game.test.engine.OrderedSpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Monster {

    private static final Texture spriteSheet = new Texture("monsters.png");
    public static final List<Monster> MONSTER_LIST = new ArrayList<>();
    private static final int FRAME_COLS = 3, FRAME_ROWS = 4;
    private static final int SPRITE_WIDTH = 24, SPRITE_HEIGHT = 32;
    private static final float MONSTER_WIDTH = 1f, MONSTER_HEIGHT = 1.5f;
    private static final float MAX_TIME_ON_DIRECTION = 0.05f;
    private static final List<String> PHRASES = Arrays.asList(
            "Un autographe s.v.p",
            "Savez-vous comment aller à la gare ?",
            "J'peux faire un selfie avec vous ?",
            "Pika pika",
            "zZZ..zz..",
            "Il fait beau non ?",
            "mdr",
            "Vous chaussez du combien ?",
            "Vous connaissez l'UPR ?"
    );
    private static final float AFFICHAGE_PHRASE_DUREE = 1f;

    private List<Animation<TextureRegion>> walkAnimations;
    private Vector2 position;
    private float stateTime;
    private Direction currentDirection;
    private float timeOnCurrentDirection;
    private float velocity;
    private float minDestWithPlayer;
    private int currentPhraseIndex;
    private float phraseTimerAffichage;
    private boolean sayPhrase;


    public static void createMonster(final MonsterType type, float x, float y) {
        Monster monster = new Monster(type);
        monster.position = new Vector2(x, y);

        monster.currentDirection = Direction.UP;
        monster.timeOnCurrentDirection = 0;
        monster.velocity = type.velocity;
        monster.minDestWithPlayer = 1;
        monster.phraseTimerAffichage = 0;
        monster.sayPhrase = false;
        monster.currentPhraseIndex = MathUtils.random(PHRASES.size() - 1);

        MONSTER_LIST.add(monster);
    }

    public void draw(OrderedSpriteBatch batch, float deltaTime, PlayerCharacter playerToFollow) {

        this.stateTime += deltaTime;
        this.timeOnCurrentDirection += deltaTime;

        if (!sayPhrase) {
            sayPhrase = MathUtils.random(50) == 0;
            if (sayPhrase) {
                this.currentPhraseIndex = MathUtils.random(PHRASES.size() - 1);
            }
        } else {
            phraseTimerAffichage += deltaTime;
            if (phraseTimerAffichage >= AFFICHAGE_PHRASE_DUREE) {
                sayPhrase = false;
                phraseTimerAffichage = 0;
            }
        }


        if (timeOnCurrentDirection >= MAX_TIME_ON_DIRECTION) {
            timeOnCurrentDirection = 0;
            currentDirection = calculateDirection(playerToFollow);

            minDestWithPlayer = MathUtils.random(1, 8);
        }

        if (position.dst(playerToFollow.getPlayerPosition()) > minDestWithPlayer) {

            if (Direction.UP.equals(currentDirection)) {
                this.position.y += velocity * deltaTime;
            } else if (Direction.RIGHT.equals(currentDirection)) {
                this.position.x += velocity * deltaTime;
            } else if (Direction.DOWN.equals(currentDirection)) {
                this.position.y -= velocity * deltaTime;
            } else if (Direction.LEFT.equals(currentDirection)) {
                this.position.x -= velocity * deltaTime;
            } else {
                this.stateTime = 0.25f;
            }

            this.position.x = MathUtils.clamp(this.position.x, 0, 60 - MONSTER_WIDTH);
            this.position.y = MathUtils.clamp(this.position.y, 0, 60 - MONSTER_HEIGHT);


        }

        TextureRegion currentFrame = null;
        switch (currentDirection) {
            case UP: {
                currentFrame = walkAnimations.get(0).getKeyFrame(stateTime, true);
                break;
            }
            case RIGHT: {
                currentFrame = walkAnimations.get(1).getKeyFrame(stateTime, true);
                break;
            }
            case DOWN: {
                currentFrame = walkAnimations.get(2).getKeyFrame(stateTime, true);
                break;
            }
            case LEFT: {
                currentFrame = walkAnimations.get(3).getKeyFrame(stateTime, true);
                break;
            }
        }
        batch.draw(currentFrame, this.position.x, this.position.y, MONSTER_WIDTH, MONSTER_HEIGHT);
    }

    private Direction calculateDirection(PlayerCharacter playerToFollow) {
        Vector2 positionToFollow = playerToFollow.getPlayerPosition();

        float xDist = position.x - positionToFollow.x;
        float yDist = position.y - positionToFollow.y;


        if (Math.abs(xDist) > Math.abs(yDist)) {
            if (position.x > positionToFollow.x) {
                return Direction.LEFT;
            } else if (position.x < positionToFollow.x) {
                return Direction.RIGHT;
            }

        } else {
            if (position.y > positionToFollow.y) {
                return Direction.DOWN;
            } else if (position.y < positionToFollow.y) {
                return Direction.UP;
            }
        }

        return currentDirection;
    }


    private Monster(final MonsterType type) {
        this.walkAnimations = new ArrayList<>();

        TextureRegion[][] tmp = TextureRegion.split(this.spriteSheet, SPRITE_WIDTH, SPRITE_HEIGHT);

        TextureRegion[] upWalkFrames = new TextureRegion[3];
        TextureRegion[] rightWalkFrames = new TextureRegion[3];
        TextureRegion[] downWalkFrames = new TextureRegion[3];
        TextureRegion[] leftWalkFrames = new TextureRegion[3];

        int index = 0;
        for (int i = 0; i < FRAME_COLS; i++) {
            upWalkFrames[index] = tmp[type.getFirstRow()][type.getFirstCol() + i];
            rightWalkFrames[index] = tmp[type.getFirstRow() + 1][type.getFirstCol() + i];
            downWalkFrames[index] = tmp[type.getFirstRow() + 2][type.getFirstCol() + i];
            leftWalkFrames[index] = tmp[type.getFirstRow() + 3][type.getFirstCol() + i];
            index++;
        }

        walkAnimations.add(new Animation<TextureRegion>(0.25f, upWalkFrames));
        walkAnimations.add(new Animation<TextureRegion>(0.25f, rightWalkFrames));
        walkAnimations.add(new Animation<TextureRegion>(0.25f, downWalkFrames));
        walkAnimations.add(new Animation<TextureRegion>(0.25f, leftWalkFrames));

        walkAnimations.get(0).setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        walkAnimations.get(1).setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        walkAnimations.get(2).setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        walkAnimations.get(3).setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    public String getCurrentPhrase() {
        return PHRASES.get(currentPhraseIndex);
    }

    public boolean hasPhraseToSay() {
        return sayPhrase;
    }

    public float getXposPhrase() {
        return this.position.x;
    }

    public float getYposPhrase() {
        return this.position.y + MONSTER_HEIGHT;
    }

    public enum MonsterType {
        PIC_BLEU(0, 0, 9f),
        PIC_ROUGE(0, 3, 9f),
        PIC_VERT(0, 6, 9f),
        PIC_JAUNE(0, 9, 9f),
        POULPE_BLEU(4, 0, 8f),
        POULPE_ROUGE(4, 3, 8f),
        SQUELETTE_BLEU(4, 6, 4f),
        SQUELETTE_ROUGE(4, 9, 5f);

        private final int firstRow;
        private final int firstCol;
        private final float velocity;

        MonsterType(int firstRow, int firstCol, float velocity) {
            this.firstRow = firstRow;
            this.firstCol = firstCol;
            this.velocity = velocity;
        }

        public int getFirstRow() {
            return firstRow;
        }

        public int getFirstCol() {
            return firstCol;
        }

    }
}
