package com.grace.flappypikachu;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.Random;

public class FlappyPikachu extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    //  ShapeRenderer shapeRenderer;

    Texture gameover;

    Texture[] pikachu;
    int flapState = 0;
    float pikachuY = 0;
    float velocity = 0;
    Circle pikachuCircle;
    int score = 0;
    int scoringTube = 0;
    BitmapFont font;

    int gameState = 0;
    float gravity = 1.5f;

    Texture topTube;
    Texture bottomTube;
    float gap = 2200;
    float maxTubeOffset;
    Random randomGenerator;
    float tubeVelocity = 4;

    int numberOfTubes = 4;
    float[] tubeX = new float[numberOfTubes];
    float[] tubeOffset = new float[numberOfTubes];
    float distanceBetweenTubes;
    Rectangle[] topTubeRectangles;
    Rectangle[] bottomTubeRectangles;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        gameover = new Texture("Gameover.png");
        // shapeRenderer = new ShapeRenderer();
        pikachuCircle = new Circle();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(5);

        pikachu = new Texture[2];
        pikachu[0] = new Texture("Pika-Fly.png");
        pikachu[1] = new Texture("Pika-Fly.png");

        topTube = new Texture("top tube.png");
        bottomTube = new Texture("bottom tube - Copy.png");
        maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
        randomGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;
        topTubeRectangles = new Rectangle[numberOfTubes];
        bottomTubeRectangles = new Rectangle[numberOfTubes];

        startGame();

    }

    public void startGame() {
        pikachuY = Gdx.graphics.getHeight() / 2 - pikachu[0].getHeight() / 2;

        for (int i = 0; i < numberOfTubes; i++) {

            tubeOffset[i] = (randomGenerator.nextFloat() - 0.001f) * (Gdx.graphics.getHeight() - gap +1200);

            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;

            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();

        }

    }
    @Override
    public void render() {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == 1) {

            if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {

                score++;

                Gdx.app.log("Score", String.valueOf(score));

                if (scoringTube < numberOfTubes - 1) {

                    scoringTube++;

                } else {

                    scoringTube = 0;

                }

            }

            if (Gdx.input.justTouched()) {
                Gdx.app.log("yes", "yes");
                velocity = -20;

            }
            for (int i = 0; i < numberOfTubes; i++) {

                if (tubeX[i] < -topTube.getWidth()) {

                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                    tubeOffset[i] = (randomGenerator.nextFloat() - 0.0001f) * (Gdx.graphics.getHeight() - gap + 1200);

                } else {

                    tubeX[i] = tubeX[i] - tubeVelocity;

                }

               // batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
//                batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()*2);
                batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]-800, Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()*2);

//                batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()*2);
                batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]-1200,Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()*2);
               // batch.draw(bottomTube, tubeX[i], 0 ,Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/3);

              //  topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
                topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]-800, Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()*2);
                bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]-1200, Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()*2);

            }

            if (pikachuY > 0) {

                velocity = velocity + gravity;
                pikachuY -= velocity;
            } else {

                gameState = 2;

            }

        } else if (gameState == 0) {

            if (Gdx.input.justTouched()) {
                Gdx.app.log("yes", "yes");
                if (flapState == 0) {
                    flapState = 1;
                } else {
                    flapState = 0;
                }

                gameState = 1;

            }
        } else if (gameState == 2) {

            batch.draw(gameover, Gdx.graphics.getWidth() / 2 - gameover.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameover.getHeight() / 2);

            if (Gdx.input.justTouched()) {
                Gdx.app.log("yes", "yes");
                gameState = 1;
                startGame();
                score = 0;
                scoringTube = 0;
                velocity = 0;

            }
        }

 //       batch.draw(pikachu[flapState], Gdx.graphics.getWidth() / 2 - pikachu[flapState].getWidth() / 2, pikachuY);
        batch.draw(pikachu[flapState], Gdx.graphics.getWidth() / 2 - pikachu[flapState].getWidth() / 2, pikachuY,Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/6);
        font.draw(batch, String.valueOf(score), 15, 75);

        batch.end();

        pikachuCircle.set(Gdx.graphics.getWidth() / 2, pikachuY + pikachu[flapState].getHeight() / 2, pikachu[flapState].getWidth() / 2);

        // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // shapeRenderer.setColor(Color.RED);
        // shapeRenderer.circle(pikachuCircle.x, pikachuCircle.y, pikachuCircle.radius);

        for (int i = 0; i < numberOfTubes; i++) {

            // shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
            // shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());

            if (Intersector.overlaps(pikachuCircle, topTubeRectangles[i]) || Intersector.overlaps(pikachuCircle, bottomTubeRectangles[i])) {

                gameState = 2;

            }
        }

        // shapeRenderer.end();

    }

}
