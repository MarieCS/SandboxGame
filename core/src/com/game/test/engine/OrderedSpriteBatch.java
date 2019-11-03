package com.game.test.engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderedSpriteBatch {
    private SpriteBatch spriteBatch;
    private List<DrawableComponent> components;

    public OrderedSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        this.components = new ArrayList<>();
    }

    public void draw(Texture texture, float x, float y, float width, float height) {
        this.components.add(new DrawableComponent(texture, y, new Vector2(x, y), new Vector2(width, height)));
    }

    public void draw(Texture texture, float drawOrder, float x, float y, float width, float height) {
        this.components.add(new DrawableComponent(texture, drawOrder, new Vector2(x, y), new Vector2(width, height)));
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

    public void render() {
        for (DrawableComponent c :
                this.components.stream().sorted(Comparator.comparing(DrawableComponent::getDrawOrder).reversed()).collect(Collectors.toList())) {
            c.draw(spriteBatch);
        }
        this.components.clear();
    }
}
