package com.simhasmithhartley.circleblitz;

import sofia.graphics.RepeatMode;
import android.view.animation.Interpolator;
import android.graphics.RectF;
import sofia.graphics.Color;
import sofia.graphics.Shape;
import sofia.graphics.ShapeMotion;
import sofia.graphics.RectangleShape;
import sofia.graphics.Timings;

/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 *
 * @author Ryan Smith (ryans6)
 * @author Anirudha Simha (anirud3)
 * @author Doug Hartley (dough7)
 * @version Apr 10, 2014
 */
public class Background
    extends RectangleShape
{
    // ----------------------------------------------------------
    /**
     * Create a new BackgroundScreen object.
     *
     * @param l
     *            left coordinate
     * @param t
     *            top coordinate
     * @param r
     *            right coordinate
     * @param b
     *            bottom coordinate
     */
    public Background(float l, float t, float r, float b)
    {
        super(l, t, r, b);
        this.setShapeMotion(ShapeMotion.STATIC);
        this.setZIndex(-1000);

    }


    // ----------------------------------------------------------
    /**
     * makes background even more dynamic
     */
    public void animation1()
    {
        // Shape.Animator<?> anim =
// animate(500).fillColor(Color.blue);//.delay(1).fillColor(Color.red).repeat();
        RectF bound = new RectF();

        bound.set(this.getX(), this.getY(), this.getX(), this.getY());
        Shape.Animator<?> anim =
            animate(5000).bounds(bound).repeatMode(RepeatMode.OSCILLATE);
        anim.play();
    }


    public void animation2()
    {
        // Shape.Animator<?> anim =
// animate(500).fillColor(Color.blue);//.delay(1).fillColor(Color.red).repeat();
        RectF bound = new RectF();

        bound.set(this.getX(), this.getY(), this.getX(), this.getY());
        Shape.Animator<?> anim =
            animate(5000).bounds(bound).repeatMode(RepeatMode.OSCILLATE);
        anim.play();
    }
}
