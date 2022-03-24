package net.kayasoft.gravitysimulator;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class Ball extends Sprite {
    private final Body body;

    public Ball(Body body) {
        this.body = body;
    }

    public void step() {
        // step
    }

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        batch.draw(getTexture(), getX(), getY());
    }
}
