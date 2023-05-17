package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    @Override
    public void render (float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        splash.draw(batch);
        batch.end();

        world.step(TIMESTEP,VELOCITYITERATIONS,POSITIONITERATIONS);
        Cm.applyForceToCenter(movement,true);

        camera.position.set(Cm.getPosition().x,Cm.getPosition().y,0);
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

        Gdx.input.setInputProcessor(new InputController(){
            @Override
            public boolean keyDown(int keycode){
                switch (keycode){
                    case Input.Keys.W:
                        movement.y = speed;
                        break;
                    case Input.Keys.A:
                        movement.x = -speed;
                        break;
                    case Input.Keys.S:
                        movement.y = -speed;
                        break;
                    case Input.Keys.D:
                        movement.x = speed;

                }
                return true;

            }
            @Override
            public boolean keyUp(int keycode){
                switch (keycode) {
                    case Input.Keys.W:
                        movement.x = 0;
                    case Input.Keys.A:
                        movement.y = 0;
                    case Input.Keys.S:
                        movement.y = 0;
                        break;
                    case Input.Keys.D:
                        movement.x = 0;

                }
                return true;

            }

        });
        // Ball
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(0,4);

        CircleShape shape = new CircleShape();
        shape.setRadius(.25f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 2.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = .8f;
        fixtureDef.shape = shape;

        Cm = world.createBody(bodyDef);
        Cm.createFixture(fixtureDef);
        shape.dispose();

        //Ground
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(0,0);

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(-50,0), new Vector2(50,0)});

        // Fixture
        fixtureDef.shape = groundShape;
        fixtureDef.friction =.5f;
        fixtureDef.restitution = 0;

        world.createBody(bodyDef).createFixture(fixtureDef);

        groundShape.dispose();

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
        camera.viewportWidth = width /25;
        camera.viewportHeight = height/25;
        camera.update();
    }

    @Override
    public void dispose () {
        batch.dispose();
        splash.getTexture().dispose();
    }
}


