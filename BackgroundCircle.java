package com.simhasmithhartley.circleblitz;

import sofia.util.Random;
import sofia.graphics.Shape;
import sofia.graphics.Color;
import sofia.graphics.ShapeMotion;
import sofia.graphics.OvalShape;
import android.graphics.Canvas;
import android.graphics.Paint;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 *
 * @author Ryan Smith (ryans6)
 * @author Anirudha Simha (anirud3)
 * @author Doug Hartley (dough7)
 * @version Apr 10, 2014
 */
public class BackgroundCircle
    extends OvalShape
{
    // private Shape.Animator<?> animation;

    // ----------------------------------------------------------
    /**
     * Create a new Background object.
     *
     * @param l
     *            The left coordinate of the circle
     * @param t
     *            The top coordinate of the circle
     * @param r
     *            The right coordinate of the circle
     * @param b
     *            The bottom coordinate of the circle
     */
    public BackgroundCircle(float l, float t, float r, float b)
    {
        super(l, t, r, b);
        this.setShapeMotion(ShapeMotion.DYNAMIC);
        this.setZIndex(-100);
        this.setRestitution(500);
        //this.setStrokeMiter(100);
        // this.addBackgroundCircle();
        this.setStrokeWidth(50);
        // animation =
// this.animate(100).color(Color.aquamarine).delay(100).color(Color.blueViolet).delay(100).color(Color.coral);
        // animation.play();
    }


    // NOT IN USE!!!!!!!!!!! --------------------------------------------------
    /**
     * this randomly generates almost a size, position, xvelocity, and yvelocity
     * for the background circle
     */
    public void addBackgroundCircle()
    {

        int size = Random.generator().nextInt(50, 100);

        int x = Random.generator().nextInt(size, (int)this.getWidth() - size);
        int y = Random.generator().nextInt(size, (int)this.getHeight() - size);
        // Random Number generator to determine if x and y Velocity will be
// + or -
        int xSign = Random.generator().nextInt(1, 2);
        int ySign = Random.generator().nextInt(1, 2);

        long xVelocity;
        long yVelocity;

        if (xSign == 1) // x is -
        {
            xVelocity = Random.generator().nextInt(-1000000000, -100000000);
        }
        else
        // x is +
        {
            xVelocity = Random.generator().nextInt(100000000, 1000000000);
        }

        if (ySign == 1) // y is -
        {
            yVelocity = Random.generator().nextInt(-1000000000, -100000000);
        }
        else
        // y is +
        {
            yVelocity = Random.generator().nextInt(100000000, 1000000000);
        }

        BackgroundCircle bc = new BackgroundCircle(size, size, size, size);
        bc.setLinearVelocity(xVelocity, yVelocity);
        bc.setPosition(x, y);

        // Manages the color for the background circles
        int color = Random.generator().nextInt(1, 10);
        switch (color)
        {
            case 1:
                bc.setFillColor(Color.aliceBlue);
                break;

            case 2:
                bc.setFillColor(Color.violet);
                break;
            case 3:
                bc.setFillColor(Color.gold);
                break;
            case 4:
                bc.setFillColor(Color.lawnGreen);
                break;
            case 5:
                bc.setFillColor(Color.yellow);
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
