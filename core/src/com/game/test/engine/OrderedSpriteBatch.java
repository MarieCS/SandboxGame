package com.game.test.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderedSpriteBatch {
    private SpriteBatch spriteBatch;
    private List<DrawableComponent> components;
    private List<Rectangle> hitboxs;

    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    public OrderedSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        this.components = new ArrayList<>();
        this.hitboxs = new ArrayList<>();
        this.shapeRenderer = new ShapeRenderer();
    }

    public void draw(Texture texture, float x, float y, float width, float height) {
        this.components.add(new DrawableComponent(texture, y, new Vector2(x, y), new Vector2(width, height)));
    }

    public void draw(Texture texture, float x, float y, float width, float height, Color color) {
        this.components.add(new DrawableComponent(texture, y, new Vector2(x, y), new Vector2(width, height), color));
    }

    public void draw(Texture texture, float drawOrder, float x, float y, float width, float height) {
        this.components.add(new DrawableComponent(texture, drawOrder, new Vector2(x, y), new Vector2(width, height)));
    }

    public void draw(TextureRegion region, float x, float y, float width, float height, Color color) {
        this.components.add(new DrawableComponent(region, y, new Vector2(x, y), new Vector2(width, height), color));
    }

    public void directDraw(Texture texture, float x, float y, float width, float height) {
       this.spriteBatch.draw(texture, x, y, width, height);
    }

    public void draw (TextureRegion region, float x, float y, float width, float height) {
        this.components.add(new DrawableComponent(region, y, new Vector2(x, y), new Vector2(width, height)));
    }

    public void directDraw(TextureRegion region, float x, float y, float width, float height) {
        this.spriteBatch.draw(region, x, y, width, height);
    }

    public void addHitbox(Rectangle r) {
        this.hitboxs.add(r);
    }

    public void render() {

        //shapeRenderer.setProjectionMatrix(this.spriteBatch.getProjectionMatrix());
        List<DrawableComponent> toDraw = new ArrayList<>();

        for (DrawableComponent c : this.components) {
            if (CameraUtils.isInCameraSpeed(this.camera, c.getPosition().x, c.getPosition().y, c.getSize().x, c.getSize().y)) {
                toDraw.add(c);
            }
        }

        for (DrawableComponent c :
                toDraw.stream().sorted(Comparator.comparing(DrawableComponent::getDrawOrder).reversed()).collect(Collectors.toList())) {
            c.draw(spriteBatch);
        }

        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Rectangle r : this.hitboxs) {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(r.x, r.y, r.width, r.height);
        }
        shapeRenderer.end();*/

        this.hitboxs.clear();
        this.components.clear();
    }

    public void setCamera(OrthographicCamera cam) {
        this.camera = cam;
    }
}
