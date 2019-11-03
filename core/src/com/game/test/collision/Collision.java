package com.game.test.collision;

import com.badlogic.gdx.math.Rectangle;
import com.game.test.player.GunBullet;
import com.game.test.player.Pnj;

public class Collision {

    private Pnj pnj;

    public void verify() {
        Rectangle r = new Rectangle(pnj.getPosition().x, pnj.getPosition().y, pnj.PNJ_WIDTH, pnj.PNJ_HEIGHT);
        for (GunBullet gunBullet : GunBullet.pool) {
            if (gunBullet.isLaunched()) {
                if (r.contains(gunBullet.getPosition())) {

                }
            }
        }

    }

    public void setPnj(Pnj pnj) {
        this.pnj = pnj;
    }
}
