package com.simhasmithhartley.circleblitz;

import sofia.graphics.ShapeMotion;
import sofia.graphics.Shape;
import android.graphics.RectF;
import sofia.util.Random;
import sofia.graphics.OvalShape;

/**
 * Holds all the methods and private fields that allow the CircleShape to shrink
 * on the screen
 *
 * @author Ryan Smith (ryans6)
 * @author Anirudha Simha (anirud3)
 * @author Doug Hartley (dough7)
 * @version Apr 10, 2014
 */
public class CircleShape
    extends OvalShape
{
    private int               morphTime;
    private Shape.Animator<?> anim;


    /**
     * Creates a new CircleShape object and sets the image inside to a random
     * image layout
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
    public CircleShape(float l, float t, float r, float b)
    {
        super(l, t, r, b);

        int numImg = Random.generator().nextInt(1, 8);

        setImage("circleshape" + numImg);

        this.setShapeMotion(ShapeMotion.DYNAMIC);
        this.setZIndex(-50);
        this.setRestitution(500);

        morphTime = Random.generator().nextInt(3000, 6000);
    }


    /**
     * Shrinks the CircleShape object in a random amount of time (but equal to
     * the morphTime) until the shape is gone
     */
    public void shrinkShape()
    {
        RectF bound = new RectF();

        bound.set(this.getX(), this.getY(), this.getX(), this.getY());
        anim =
            this.animate(morphTime).bounds(bound).rotation(720)
                .removeWhenComplete();
        anim.play();
    }


    /**
     * Returns the morphTime
     *
     * @return The morphTime field
     */
    public int getMorphTime()
    {
        return morphTime;
    }
}
