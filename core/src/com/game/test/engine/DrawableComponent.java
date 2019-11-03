package com.game.test.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class DrawableComponent {
    private Color color;
    private Texture texture;
    private TextureRegion textureRegion;
    private float drawOrder;
    private Vector2 position;
    private Vector2 size;

    public DrawableComponent(Texture t, float drawOrder, Vector2 p, Vector2 s) {
        this.texture = t;
        this.drawOrder = drawOrder;
        this.position = p;
        this.size = s;
    }

    public DrawableComponent(TextureRegion tr, float drawOrder, Vector2 p, Vector2 s) {
        this.textureRegion = tr;
        this.drawOrder = drawOrder;
        this.position = p;
        this.size = s;
    }

    public DrawableComponent(TextureRegion tr, float drawOrder, Vector2 p, Vector2 s, Color color) {
        this.textureRegion = tr;
        this.drawOrder = drawOrder;
        this.position = p;
        this.size = s;
        this.color = color;
    }

    public DrawableComponent(Texture t, float drawOrder, Vector2 p, Vector2 s, Color color) {
        this.texture = t;
        this.drawOrder = drawOrder;
        this.position = p;
        this.size = s;
        this.color = color;
    }

    public float getDrawOrder() {
        return this.drawOrder;
    }

    public void draw(SpriteBatch spriteBatch) {
        if (color != null) {
            spriteBatch.setColor(color);
        }
        if (textureRegion != null) {
           spriteBatch.draw(textureRegion, position.x, position.y, size.x, size.y);
        } else {
            spriteBatch.draw(texture, position.x, position.y, size.x, size.y);
        }
        if (color != null) {
            spriteBatch.setColor(1, 1, 1, 1);
        }
    }
}
