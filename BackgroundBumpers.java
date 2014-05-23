package com.simhasmithhartley.circleblitz;

import sofia.graphics.Color;
import sofia.graphics.ShapeMotion;
import sofia.graphics.RectangleShape;

// -------------------------------------------------------------------------
/**
 * Basically these exists so that the background circles can bounce and colide
 * with them They are static objects so they wont move but they can collide with
 * the background circles which are dynamic in motion.
 *
 * @author Ryan Smith (ryans6)
 * @author Anirudha Simha (anirud3)
 * @author Doug Hartley (dough7)
 * @version Apr 10, 2014
 */
public class BackgroundBumpers
    extends RectangleShape
{
    // ----------------------------------------------------------
    /**
     * Create a new BackgroundBumpers object.
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
    public BackgroundBumpers(float l, float t, float r, float b)
    {
        super(l, t, r, b);
        this.setShapeMotion(ShapeMotion.STATIC);
        this.setZIndex(-1);
        // this.setFillColor(Color.antiqueWhite);
        this.setRestitution(500);
    }
}
