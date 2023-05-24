package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Splash implements Screen {
    private SpriteBatch batch;
    private Sprite splash;
    private World world;
    private Body Cm;
    private OrthographicCamera camera;
    private float speed = 10;
    private Box2DDebugRenderer debugRenderer;
    private final float TIMESTEP = 1/60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
    private Vector2 movement = new Vector2();
    private Sprite ballSprite;
    private Array<Body> tmpBodies = new Array<Body>();
    private Player player;
    @Override
    public void render (float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        world.getBodies(tmpBodies);
        for(Body body : tmpBodies)
            if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x-sprite.getWidth()/2, body.getPosition().y-sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }

        batch.end();

        world.step(TIMESTEP,VELOCITYITERATIONS,POSITIONITERATIONS);
        player.update();
        camera.position.set(player.getBody().getPosition().x,player.getBody().getPosition().y,0);
        camera.update();

        debugRenderer.render(world, camera.combined);

    }

    @Override
    public void pause() {

    }

    public void show(){
        batch = new SpriteBatch();
        world = new World(new Vector2(0,-9.81f),true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();

        player = new Player(world,0,5);

        Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter(){
        },player));
        // Ground
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(0,0);

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(-1,0), new Vector2(10,0)});
        ChainShape groundShape1 = new ChainShape();
        groundShape1.createChain(new Vector2[]{new Vector2(15,0), new Vector2(30,0)});
        ChainShape groundShape2 = new ChainShape();
        groundShape2.createChain(new Vector2[]{new Vector2(40,0), new Vector2(55,0)});
        ChainShape groundShape3 = new ChainShape();
        groundShape3.createChain(new Vector2[]{new Vector2(65,0), new Vector2(80,0)});
        ChainShape groundShape4 = new ChainShape();
        groundShape4.createChain(new Vector2[]{new Vector2(85,3), new Vector2(95,3)});
        ChainShape groundShape5 = new ChainShape();
        groundShape5.createChain(new Vector2[]{new Vector2( 110,0), new Vector2(120,0)});
        ChainShape groundShape6 = new ChainShape();
        groundShape6.createChain(new Vector2[]{new Vector2(130,0), new Vector2(140,5)});
        ChainShape groundShape7 = new ChainShape();
        groundShape7.createChain(new Vector2[]{new Vector2(145,5), new Vector2(155,10)});
        ChainShape groundShape8 = new ChainShape();
        groundShape8.createChain(new Vector2[]{new Vector2(165,5), new Vector2(175,0)});
        ChainShape groundShape9 = new ChainShape();
        groundShape9.createChain(new Vector2[]{new Vector2(190,0), new Vector2(200,0)});


        // Fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction =.5f;
        fixtureDef.restitution = 0;
        world.createBody(bodyDef).createFixture(fixtureDef);
        fixtureDef.shape = groundShape1;
        world.createBody(bodyDef).createFixture(fixtureDef);
        fixtureDef.shape = groundShape2;
        world.createBody(bodyDef).createFixture(fixtureDef);
        fixtureDef.shape = groundShape3;
        world.createBody(bodyDef).createFixture(fixtureDef);
        fixtureDef.shape = groundShape4;
        world.createBody(bodyDef).createFixture(fixtureDef);
        fixtureDef.shape = groundShape5;
        world.createBody(bodyDef).createFixture(fixtureDef);
        fixtureDef.shape = groundShape6;
        world.createBody(bodyDef).createFixture(fixtureDef);
        fixtureDef.shape = groundShape7;
        world.createBody(bodyDef).createFixture(fixtureDef);
        fixtureDef.shape = groundShape8;
        world.createBody(bodyDef).createFixture(fixtureDef);
        fixtureDef.shape = groundShape9;
        world.createBody(bodyDef).createFixture(fixtureDef);
        groundShape.dispose();
        groundShape1.dispose();
        groundShape2.dispose();
        groundShape3.dispose();
        groundShape4.dispose();
        groundShape5.dispose();
        groundShape6.dispose();
        groundShape7.dispose();
        groundShape8.dispose();
        groundShape9.dispose();

        Texture splashTexture = new Texture("background.jpg");
        splash = new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
//        splash.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.setScreen();
//            }
//        });
    }
    public void hide(){
        dispose();
    }
    @Override
    public void resume(){

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width/25;
        camera.viewportHeight = height/25;
        camera.update();
    }

    @Override
    public void dispose () {
        batch.dispose();
        splash.getTexture().dispose();
    }
}


