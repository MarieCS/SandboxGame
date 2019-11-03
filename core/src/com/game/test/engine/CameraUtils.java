package com.game.test.engine;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public class CameraUtils {

    public static boolean isInCamera(OrthographicCamera cam, float x, float y, float width, float height) {
        Rectangle rCam = new Rectangle(cam.position.x - (cam.viewportWidth/2), cam.position.y - (cam.viewportHeight/2), cam.viewportWidth, cam.viewportHeight);
        Rectangle rObject = new Rectangle(x, y, width, height);

        return rCam.overlaps(rObject);
    }

    public static boolean isInCameraSpeed(OrthographicCamera cam, float x, float y, float width, float height) {

        return  ((x > (cam.position.x - (cam.viewportWidth/2) - width) && x <(cam.position.x + (cam.viewportWidth/2)) + width) &&
                (y > (cam.position.y - (cam.viewportHeight/2)) - height && y <(cam.position.y + (cam.viewportHeight/2)) + height));

    }
}
