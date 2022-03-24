package net.kayasoft.gravitysimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class BalonSimulation extends ApplicationAdapter {
	private World world;
	SpriteBatch batch;
	public static Texture backgroundTexture;
	public static Sprite backgroundSprite;
	private int width;
	private int height;
	private static final int numOfBalls = 50;
	private final float sizeOfBall = 50f;
	private Ball[] balls;
	private static final String[] Colors = {"yellow", "blue", "green", "pink", "red", "turquoise"};

	@Override
	public void create () {
		world = new World(new Vector2(0, -10), true);
		balls = new Ball[numOfBalls];
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		backgroundTexture = new Texture("bg.jpg");
		backgroundSprite =new Sprite(backgroundTexture);
		backgroundSprite.setSize(width, height);

		createBox();

		for (int i = 0; i < balls.length; i++) {
			balls[i] = new Ball(createBallBody());
			balls[i].setTexture(getTextureBall(Colors.length > i ? Colors[i] : getRandomColor()));
			balls[i].setPosition((float) randX(), (float) (height - height*Math.random()*0.1));
		}

		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		batch.begin();
		backgroundSprite.draw(batch);
		for (Ball ball : balls) {
			ball.step();
			ball.draw(batch);
		}
		batch.end();
		world.step(1/60f, 20, 3);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for (Ball ball : balls)
			ball.getTexture().dispose();
	}

	public static String getRandomColor() {
		int rnd = new Random().nextInt(Colors.length);
		return Colors[rnd];
	}

	public double randX() {
		double x =  Math.random() * width;
		return x > sizeOfBall ? x - sizeOfBall : x;
	}

	private Texture getTextureBall(String name) {
		Pixmap img = new Pixmap(Gdx.files.internal(name+".png"));
		Pixmap resized = new Pixmap((int)sizeOfBall, (int)sizeOfBall, img.getFormat());
		resized.drawPixmap(img,
				0, 0, img.getWidth(), img.getHeight(),
				0, 0, resized.getWidth(), resized.getHeight()
		);
		Texture texture = new Texture(resized);
		img.dispose();
		resized.dispose();
		return texture;
	}


	private void createBox() {
		//bottom
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2(0, -sizeOfBall/2));
		Body groundBody = world.createBody(groundBodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(width, 0.0f);
		groundBody.createFixture(groundBox, 0.0f);

		//side
		for (byte i = 0; 2>i; i++) {
			BodyDef sideBodyDef = new BodyDef();
			sideBodyDef.position.set(new Vector2((i == 0 ? (-sizeOfBall/2) : (width - sizeOfBall/2)), 0));
			Body sideBody = world.createBody(sideBodyDef);
			PolygonShape sideBox = new PolygonShape();
			sideBox.setAsBox(0, height);
			sideBody.createFixture(sideBox, 0.0f);
			sideBox.dispose();
		}

		groundBox.dispose();
	}

	private Body createBallBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set((float) randX(), (float) (height + height*Math.random()*0.75));
		Body body = world.createBody(bodyDef);

		CircleShape circle = new CircleShape();
		circle.setRadius(sizeOfBall/2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.2f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0.9f;
		Fixture fixture = body.createFixture(fixtureDef);
		circle.dispose();
		return body;
	}
}
