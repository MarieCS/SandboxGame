package com.game.test.collision;

import com.badlogic.gdx.math.Rectangle;
import com.game.test.player.GunBullet;
import com.game.test.player.Pnj;

import java.util.List;

public class Collision {

    private List<Pnj> pnjList;

    public void verify() {
        for (Pnj pnj : pnjList) {
            Rectangle r = new Rectangle(pnj.getPosition().x, pnj.getPosition().y, Pnj.PNJ_WIDTH, Pnj.PNJ_HEIGHT);
            for (GunBullet gunBullet : GunBullet.pool) {
                if (gunBullet.isLaunched()) {
                    if (r.contains(gunBullet.getPosition())) {
                        pnj.setEtat(Pnj.Etat.VNR);
                    }
                }
            }
        }


    }

    public void setPnj(List<Pnj> pnjList) {
        this.pnjList = pnjList;
    }
}
