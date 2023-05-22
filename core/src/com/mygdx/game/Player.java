package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends InputAdapter {
    private Body Cm;
    private Vector2 movement = new Vector2();
    private Sprite ballSprite;
    private float speed = 10;

    public Player(World world, float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);
        bodyDef.fixedRotation = true;
        ballSprite = new Sprite(new Texture("Sball.png"));
        ballSprite.setSize(1f,1f);
        ballSprite.setOrigin(ballSprite.getWidth()/2,ballSprite.getHeight()/2);
        CircleShape shape = new CircleShape();
        shape.setRadius(.25f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 2.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = 1;
        fixtureDef.shape = shape;

        Cm = world.createBody(bodyDef);
        Cm.createFixture(fixtureDef);
        Cm.setUserData(ballSprite);
        shape.dispose();
    }

    public void update(){
        Cm.applyForceToCenter(movement, true);
    }

    @Override
    public boolean keyDown(int keycode){
        switch (keycode){
            case Input.Keys.A:
                movement.x = -speed;
                break;
            case Input.Keys.D:
                movement.x = speed;
                break;
            default:
                return false;
        }
        return true;
    }
    @Override
    public boolean keyUp(int keycode){
        if(keycode == Input.Keys.A || keycode == Input.Keys.D)
            movement.x = 0;
        else
            return false;
        return true;

    }
    public Body getBody(){
        return Cm;
    }
}
