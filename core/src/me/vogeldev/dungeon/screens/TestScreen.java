package me.vogeldev.dungeon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import me.vogeldev.dungeon.MainActivity;
import me.vogeldev.dungeon.support.InputController;

/**
 * Created by Vogel on 2/21/2016.
 */
public class TestScreen implements Screen {

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private final float TIMESTEP = 1/60f;
    private final int VELOCITYITERATION = 8;
    private final int POSITIONITERATION = 3;

    private Vector2 movement = new Vector2();
    private int speed = 1000;
    private float angleVel = (float)Math.sqrt(Math.pow(speed, 2) / 2);
    private Body player;
    //up, down, left, right
    private int[] playerMovement = {0,0,0,0};

    public TestScreen(MainActivity game){

    }

    @Override
    public void show() {
        world = new World(new Vector2(), true);
        debugRenderer = new Box2DDebugRenderer();
        movement = new Vector2();

        camera = new OrthographicCamera(Gdx.graphics.getWidth() / 50, Gdx.graphics.getHeight() / 50);

        Gdx.input.setInputProcessor(new InputController(){
            @Override
            public boolean keyDown(int keycode) {

                switch(keycode){
                    case Input.Keys.W :
                        playerMovement[0] = 1;
                        break;
                    case Input.Keys.S :
                        playerMovement[1] = 1;
                        break;
                    case Input.Keys.A :
                        playerMovement[2] = 1;
                        break;
                    case Input.Keys.D :
                        playerMovement[3] = 1;
                        break;
                }

                return true;
            }

            @Override
            public boolean keyUp(int keycode) {


                switch(keycode){
                    case Input.Keys.W :
                        playerMovement[0] = 0;
                        break;
                    case Input.Keys.S :
                        playerMovement[1] = 0;
                        break;
                    case Input.Keys.A :
                        playerMovement[2] = 0;
                        break;
                    case Input.Keys.D :
                        playerMovement[3] = 0;
                        break;
                }

                switch(keycode){
                    case Input.Keys.W :
                    case Input.Keys.S :
                        movement.y = 0;
                        player.setLinearVelocity(Vector2.Zero);
                        player.setAngularVelocity(0f);
                        break;
                    case Input.Keys.A :
                    case Input.Keys.D :
                        movement.x = 0;
                        player.setLinearVelocity(Vector2.Zero);
                        player.setAngularVelocity(0f);
                        break;
                }
                return true;
            }
        });

        //Body Definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,1);

        //ball shape
        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(.25f);

        //Fixture definition
        FixtureDef fixtureDef= new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = 0;

        world.createBody(bodyDef).createFixture(fixtureDef);

        ballShape.dispose();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] {new Vector2(-10, 0), new Vector2(10, 0)});

        fixtureDef.shape = groundShape;
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0;

        world.createBody(bodyDef).createFixture(fixtureDef);

        groundShape.dispose();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(1, 1);
        bodyDef.fixedRotation = true;
        bodyDef.linearDamping = 50;

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(.5f, .5f);

        fixtureDef.shape = boxShape;
        fixtureDef.friction = .75f;
        fixtureDef.restitution = 0;
        fixtureDef.density = 5;


        player = world.createBody(bodyDef);
        player.createFixture(fixtureDef);

        boxShape.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(world, camera.combined);

        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update();

        world.step(TIMESTEP, VELOCITYITERATION, POSITIONITERATION);

        if(playerMovement[0] == 1){
            if(playerMovement[2] == 1){
                movement.x = -angleVel;
                movement.y = angleVel;
            }else if(playerMovement[3] == 1){
                movement.x = angleVel;
                movement.y = angleVel;
            }else{
                movement.y = speed;
            }
        }else if(playerMovement[1] == 1){
            if(playerMovement[2] == 1){
                movement.x = -angleVel;
                movement.y = -angleVel;
            }else if(playerMovement[3] == 1){
                movement.x = angleVel;
                movement.y = -angleVel;
            }else{
                movement.y = -speed;
            }
        }else if(playerMovement[2] == 1){
            movement.x = -speed;
        }else if(playerMovement[3] == 1){
            movement.x = speed;
        }

        player.applyForceToCenter(movement, true);
    }

    @Override
    public void resize(int width, int height) {
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
