package com.simhasmithhartley.circleblitz;

import android.os.CountDownTimer;
import sofia.util.Random;
import android.widget.*;
import sofia.graphics.ShapeView;

/**
 * Tests all the methods of the CircleBlitzScreen class and the CircleShape
 * class (Except for menu options because they could not be selected and the
 * three dialog boxes because there is no good way to test those)
 *
 * @author Ryan Smith (ryans6)
 * @author Anirudha Simha (anirud3)
 * @author Doug Hartley (dough7)
 * @version Apr 10, 2014
 */
public class CircleBlitzScreenTests
    extends student.AndroidTestCase<CircleBlitzScreen>
{
    private CircleShape circle;
    private ShapeView   shapeView;
    private TextView    score;
    private TextView    highScore;
    private TextView    timer;


    /**
     * Creates a new CircleBlitzScreenTests object
     */
    public CircleBlitzScreenTests()
    {
        super(CircleBlitzScreen.class);
        Random.setNextInts(100, 100, 100);
    }


    /**
     * Sets up a new CircleShape object to use in testing
     */
    public void setUp()
    {
        circle = new CircleShape(2.0f, 2.0f, 2.0f, 2.0f);
        Random.setNextInts(100, 100, 100);
    }


    /**
     * Tests that the constructor of the CircleShape initializes everything
     * correctly
     */
    public void testConstructor()
    {
        assertTrue(circle.getMorphTime() < 6000);
        assertTrue(circle.getMorphTime() >= 3000);
    }


    /**
     * Tests that the CircleShape shrinks all the way to zero
     */
    public void testShrinkShape()
    {
        Random.setNextInts(1, 1);

        circle = new CircleShape(10.0f, 10.0f, 10.0f, 10.0f);
        this.getScreen().add(circle);

        circle.shrinkShape();

        assertEquals(0.0f, circle.getWidth(), 0.1);
    }


    /**
     * Tests that all the initial values are displayed correctly on the screen
     * in the right place
     */
    public void testInitialize()
    {
        assertEquals("Score: 0", score.getText());
        assertEquals("HighScore: 0", highScore.getText());

        // Varies depending on emulator speed
        assertEquals("Time: 59", timer.getText());
    }


    /**
     * Tests that once a CircleShape has been tapped, it is removed and that
     * more CircleShapes appear after the first one has been removed
     */
    public void testTapACircle()
    {
        assertEquals(3, this.getScreen().getCirclesListSize());

        touchDown(shapeView, 100, 100);

        assertEquals(4, this.getScreen().getCirclesListSize());
    }


    /**
     * Tests that tapping 2 CircleShapes removes both and creates more
     * ******************************NOTE*************************************
     * Doesn't pass when run with all the other tests, but passes when run by
     * itself
     * ***********************************************************************
     */
    public void testTap2Circles()
    {
        assertEquals(3, this.getScreen().getCirclesListSize());

        touchDown(shapeView, 100, 100);
        touchUp();
        touchDown(shapeView, 100, 100);

        assertEquals(5, this.getScreen().getCirclesListSize());
    }


    /**
     * Tests that a tap on just the screen will do nothing
     */
    public void testTapNotACircle()
    {
        assertEquals(3, this.getScreen().getCirclesListSize());

        touchDown(shapeView, 1, 1);

        assertEquals(3, this.getScreen().getCirclesListSize());
    }


    /**
     * Tests the makeShapes method with a small number
     */
    public void testMakeShapes()
    {
        assertEquals(3, this.getScreen().getCirclesListSize());

        this.getScreen().makeShapes(2);

        assertEquals(5, this.getScreen().getCirclesListSize());
    }


    /**
     * Tests the makeShapes method with a big number
     */
    public void testMakeShapesBig()
    {
        assertEquals(3, this.getScreen().getCirclesListSize());

        this.getScreen().makeShapes(100);

        assertEquals(103, this.getScreen().getCirclesListSize());
    }


    /**
     * Tests the makeShapes method with zero
     */
    public void testMakeShapesZero()
    {

        assertEquals(3, this.getScreen().getCirclesListSize());

        this.getScreen().makeShapes(0);

        assertEquals(3, this.getScreen().getCirclesListSize());
    }


    /**
     * Tests that the timer has the correct time
     */
    public void testTimer()
    {
        // Varies depending on emulator speed
        assertEquals("Time: 59", timer.getText());
    }


    /**
     * Tests that the calcPoints calculates the correct amount of points given a
     * number
     */
    public void testCalcPoints()
    {
        assertEquals(4, getScreen().calcPoints(20.0f));
    }


    /**
     * Tests that the calcPoints calculates the correct amount of points given a
     * number
     */
    public void testCalcPointsZero()
    {
        assertEquals(0, getScreen().calcPoints(0.0f));
    }


    /**
     * Tests the output after the timer has finished counting down
     */
    public void testTimerEnded()
    {
        new CountDownTimer(60000, 100) {
            public void onFinish()
            {
                assertEquals("TIME UP!", timer.getText());
            }


            @Override
            public void onTick(long millisUntilFinished)
            {
                // Not required for the test
            }
        }.start();
    }

}
