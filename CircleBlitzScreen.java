package com.simhasmithhartley.circleblitz;

import sofia.graphics.Color;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.os.CountDownTimer;
import java.util.ArrayList;
import java.util.List;
import sofia.util.Random;
import android.widget.TextView;
import sofia.app.ShapeScreen;
import sofia.app.OptionsMenu;

// -------------------------------------------------------------------------
/**
 * Manages all the user interaction with the screen and allows the user to
 * change the difficulty of the game, reset the game, read instructions, or exit
 * the game. Also manages the points for the game.
 *
 * @author Ryan Smith (ryans6)
 * @author Anirudha Simha (anirud3)
 * @author Doug Hartley (dough7)
 * @version Apr 10, 2014
 */

@OptionsMenu
public class CircleBlitzScreen
    extends ShapeScreen
{
    private List<CircleShape>      circles;
    private List<BackgroundCircle> backgroundCircles;

    private CountDownTimer         countdown;

    private TextView               score;
    private TextView               highScore;
    private TextView               timer;

    private int                    numPoints;
    private int                    highS;

    private int                    numTimesTapped;
    private int                    numShapesHit;
    private double                 accuracy;

    private int                    difficulty;
    private int                    numCreateCircle;


    /**
     * Initializes the screen to start with three CircleShape object and sets up
     * the timer, score, and high score TextViews
     */
    public void initialize()
    {
        circles = new ArrayList<CircleShape>();
        backgroundCircles = new ArrayList<BackgroundCircle>();

        startTimer(60000, 100);

        numPoints = 0;
        highS = 0;

        score.setText("Score: " + numPoints);
        highScore.setText("HighScore: " + highS);

        numTimesTapped = 0;
        numShapesHit = 0;
        accuracy = 0.0;

        makeShapes(3);
        addBackgroundStuff(4);

        difficulty = 0;
        numCreateCircle = 0;

    }


    /**
     * When a CircleShape object is tapped it will disappear and then update the
     * score with the correct amount of points. The width of the CircleShape
     * when tapped corresponds to the score that the user receives.
     *
     * @param x
     *            The x coordinate of the touch
     * @param y
     *            The y coordinate of the touch
     */
    public void onTouchDown(float x, float y)
    {
        numTimesTapped++;

        for (int i = 0; i < circles.size(); i++)
        {
            CircleShape c = circles.get(i);

            if (x < (c.getX() + (c.getWidth() / 2) + 10)
                && x > (c.getX() - (c.getWidth() / 2) - 10)
                && y < (c.getY() + (c.getHeight() / 2) + 10)
                && y > (c.getY() - (c.getHeight() / 2) - 10))
            {
                if (difficulty == 0)
                {
                    numCreateCircle = 2;
                }
                else if (difficulty == 1)
                {
                    numCreateCircle = 3;
                }
                else
                {
                    numCreateCircle = 4;
                }

                c.stopAnimation();
                c.remove();

                numPoints += calcPoints(c.getWidth());
                circles.remove(c);

                numShapesHit++;

                score.setText("Score: " + numPoints);
                makeShapes(numCreateCircle);
                break;
            }
        }
    }


    /**
     * Makes the desired number of CircleShapes with random sizes and positions
     * then adds them to the screen and starts to shrink them
     *
     * @param numCircles
     *            The number of CircleShpaes that should be created
     */
    public void makeShapes(int numCircles)
    {
        for (int i = 0; i < numCircles; i++)
        {
            int size = Random.generator().nextInt(50, 150);

            int x =
                Random.generator().nextInt(size, (int)this.getWidth() - size);
            int y =
                Random.generator().nextInt(size, (int)this.getHeight() - size);

            CircleShape s = new CircleShape(size, size, size, size);

            circles.add(s);
            s.setPosition(x, y);

            this.add(s);

            s.shrinkShape();

        }
    }


    /**
     * Starts the timer for the game and displays dialog boxes when time is
     * finished
     *
     * @param time
     *            The time that the timer runs for
     * @param interval
     *            The time interval that the timer decreases by
     */
    public void startTimer(int time, int interval)
    {
        countdown = new CountDownTimer(time, interval) {

            public void onTick(long millisUntilFinished)
            {
                timer.setText("Time: " + millisUntilFinished / 1000);
                manageHighScore();
            }


            public void onFinish()
            {
                timer.setText("TIME UP!");

                accuracy = ((1.0 * numShapesHit) / numTimesTapped) * 100;

                AlertDialog.Builder builder =
                    new AlertDialog.Builder(getScreen());
                builder.setMessage("Replay?")
                    .setPositiveButton("Yes", replayClickListener)
                    .setNegativeButton("No", replayClickListener).show();

                dialogOk("Game Over", "Thanks for playing!\nScore: "
                    + numPoints + "\nHighScore: " + highS + "\nAccuracy: "
                    + Math.floor(accuracy * 100) / 100 + "%");
            }

        }.start();
    }


    /**
     * Calculates the correct number of points earned by tapping the CircleShape
     * based off of the width of the circle when tapped
     *
     * @return The calculated score for that CircleShape
     * @param width
     *            The width of that particular CircleShape
     */
    public int calcPoints(float width)
    {
        return (int)width / 5;
    }


    /**
     * Display a message in a dialog. Note: dialogs in Android are asynchronous.
     * Execution in the app will continue while dialog is displayed.
     *
     * @param title
     *            the title for the dialog window
     * @param message
     *            the message for the dialog window
     */
    public void dialogOk(String title, String message)
    {
        ContextThemeWrapper ctw =
            new ContextThemeWrapper(this, R.style.AppBaseTheme);
        AlertDialog.Builder alert = new AlertDialog.Builder(ctw);

        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK", null);
        alert.setCancelable(true);
        alert.create().show();
    }


    /**
     * Updates the high score TextView with the highest score obtained from all
     * the games played
     */
    public void manageHighScore()
    {
        if (numPoints > highS)
        {
            highS = numPoints;
            highScore.setText("HighScore: " + highS);
        }
    }


    /**
     * Clears the screen of all shapes and resets the score and timer
     */
    public void clearScreen()
    {
        for (int i = 0; i < circles.size(); i++)
        {
            CircleShape c = circles.get(i);

            c.remove();
            circles.remove(c);
        }
        for (int i = 0; i < backgroundCircles.size(); i++)
        {
            BackgroundCircle c = backgroundCircles.get(i);
            c.remove();
            backgroundCircles.remove(c);
        }

        numPoints = 0;

        score.setText("Score: " + numPoints);
        timer.setText("Time: ");

        numTimesTapped = 0;
        numShapesHit = 0;
        accuracy = 0.0;
    }


    /**
     * Runs when the reset option is clicked on the menu and resets the game
     */
    public void resetOptionClicked()
    {
        clearScreen();
        makeShapes(3);
        addBackgroundStuff(4);

        countdown.cancel();

        startTimer(60000, 100);

    }


    /**
     * Runs when the instructions option is clicked on the menu and displays the
     * instructions
     */
    public void instructionsOptionClicked()
    {
        dialogOk(
            "Instructions",
            "Tap on the circles before they shrink all the way! The smaller "
                + "the circle is, the more points you get. Try to get the "
                + "highest score in 60 seconds.");
    }


    /**
     * Runs when the difficulty option is clicked on the menu and asks the user
     * which difficulty level they would like. Then resets the game
     */
    public void difficultyOptionClicked()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getScreen());
        builder.setMessage("Choose your difficulty:")
            .setNegativeButton("Easy", difficultyClickListener)
            .setNeutralButton("Medium", difficultyClickListener)
            .setPositiveButton("Hard", difficultyClickListener).show();

        resetOptionClicked();
    }


    /**
     * Runs when the exit option is clicked on the menu
     */
    public void exitOptionClicked()
    {
        finish();
    }


    /**
     * Returns this screen instance
     *
     * @return This screen instance
     */
    public CircleBlitzScreen getScreen()
    {
        return this;
    }


    /**
     * Returns the circles ArrayList's size
     *
     * @return The circles ArrayList's size
     */
    public int getCirclesListSize()
    {
        return circles.size();
    }

    /**
     * Dialog box used to ask the user if they would like to replay or not
     */
    DialogInterface.OnClickListener replayClickListener     =
                                                                new DialogInterface.OnClickListener() {
                                                                    public void onClick(
                                                                        DialogInterface dialog,
                                                                        int which)
                                                                    {
                                                                        switch (which)
                                                                        {
                                                                            case DialogInterface.BUTTON_POSITIVE:
                                                                                resetOptionClicked();
                                                                                break;

                                                                            case DialogInterface.BUTTON_NEGATIVE:
                                                                                finish();
                                                                                break;
                                                                        }
                                                                    }
                                                                };

    /**
     * Dialog box used to ask the user which difficulty level they would like to
     * play on
     */
    DialogInterface.OnClickListener difficultyClickListener =
                                                                new DialogInterface.OnClickListener() {
                                                                    public void onClick(
                                                                        DialogInterface dialog,
                                                                        int which)
                                                                    {
                                                                        switch (which)
                                                                        {
                                                                            case DialogInterface.BUTTON_NEGATIVE:
                                                                                difficulty =
                                                                                    0;
                                                                                break;
                                                                            case DialogInterface.BUTTON_NEUTRAL:
                                                                                difficulty =
                                                                                    1;
                                                                                break;
                                                                            case DialogInterface.BUTTON_POSITIVE:
                                                                                difficulty =
                                                                                    2;
                                                                                break;
                                                                        }
                                                                    }
                                                                };


    /**
     * adds all the objects associated with generating/making the "dynamic"
     * background
     *
     * @param numBC
     *            number of background objects to make
     */
    public void addBackgroundStuff(int numBC)
    {

        float height = this.getHeight();
        float width = this.getWidth();
        // top bumper
        BackgroundBumpers bumper1 = new BackgroundBumpers(0, 0, width, 1);
        this.add(bumper1);
        // left bumper
        BackgroundBumpers bumper2 = new BackgroundBumpers(0, 0, 1, height);
        this.add(bumper2);
        // bottom bumper
        BackgroundBumpers bumper3 =
            new BackgroundBumpers(0, height - 1, width, height);
        this.add(bumper3);
        // right bumper
        BackgroundBumpers bumper4 =
            new BackgroundBumpers(width - 1, 0, width, height);
        this.add(bumper4);
        // background circle
        this.addBackgroundCircles(numBC);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @param numBC
     *            number of background objects to make
     */
    public void addBackgroundCircles(int numBC)
    {
        for (int i = 0; i < numBC; i++)
        {
            int size = Random.generator().nextInt(50, 75);

            int x =
                Random.generator().nextInt(size, (int)this.getWidth() - size);
            int y =
                Random.generator().nextInt(size, (int)this.getHeight() - size);
            // Random Number generator to determine if x and y Velocity will be
// + or -
            int xSign = Random.generator().nextInt(1, 2);
            int ySign = Random.generator().nextInt(1, 2);

            long xVelocity;
            long yVelocity;

            if (xSign == 1) // x is -
            {
                xVelocity =
                    Random.generator().nextLong(-1000000000, -100000000);
            }
            else
            // x is +
            {
                xVelocity = Random.generator().nextLong(100000000, 1000000000);
            }

            if (ySign == 1) // y is -
            {
                yVelocity =
                    Random.generator().nextLong(-1000000000, -100000000);
            }
            else
            // y is +
            {
                yVelocity = Random.generator().nextLong(100000000, 1000000000);
            }

            BackgroundCircle bc = new BackgroundCircle(size, size, size, size);
            bc.setLinearVelocity(xVelocity, yVelocity);
            bc.setPosition(x, y);

            backgroundCircles.add(bc);

            this.add(bc);
            // Manages the color for the background circles
            int color2 = Random.generator().nextInt(1, 10);
            switch (color2)
            {
                case 1:
                    bc.setFillColor(Color.aliceBlue);
                    break;

                case 2:
                    bc.setFillColor(Color.violet);
                    break;
                case 3:
                    bc.setFillColor(Color.chocolate);
                    break;
                case 4:
                    bc.setFillColor(Color.lawnGreen);
                    break;
                case 5:
                    bc.setFillColor(Color.darkOrange);
                    break;
                case 6:
                    bc.setFillColor(Color.red);
                    break;
                case 7:
                    bc.setFillColor(Color.green);
                    break;
                case 8:
                    bc.setFillColor(Color.maroon);
                    break;
                case 9:
                    bc.setFillColor(Color.blue);
                    break;
                case 10:
                    bc.setFillColor(Color.orange);
                    break;
            }

        }
    }


    // ----------------------------------------------------------
    /**
     * adds a cool moving screen
     */
    public void addScreen()
    {
        /*
         * BackgroundScreen screen1 = new BackgroundScreen( 10, 10,
         * this.getWidth() - 10, this.getHeight() - 10); this.add(screen1);
         * screen1.setZIndex(-900); screen1.setFillColor(Color.yellow);
         * screen1.animation1(); BackgroundScreen screen2 = new
         * BackgroundScreen( this.getWidth() / 4, this.getHeight() / 4, 3 *
         * this.getWidth() / 4, 3 * this.getHeight() / 4); this.add(screen2);
         * screen2.setFillColor(Color.silver); screen2.setZIndex(-800);
         * screen2.animation2();
         */

        Background screen3 =
            new Background(0, 0, this.getWidth(), this.getHeight());
        this.add(screen3);
        screen3.setFillColor(Color.purple);
    }
}
