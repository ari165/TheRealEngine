package util;

import org.joml.Vector2f;

public class Matha {

    /**
     * Rotates a point by the angle given to
     * Note: it modifies the vector
     *
     * @param vec the point's position
     * @param angleDeg rotated angle in degrees
     * @param origin the origin that the rotation is based on
     *
     */
    public static void rotate(Vector2f vec, float angleDeg, Vector2f origin){
        // Variables
        // x, y: the starting points
        // x', y': the rotated points
        // T: the rotated angle (theta)

        // Formulas
        // x' = x * cosT - y * sinT
        // y' = x * sinT + y * cosT

        // move the to (0, 0)
        float x = vec.x - origin.x;
        float y = vec.y - origin.y;

        // calculate cos and sin of the angle
        float cos = (float)Math.cos(Math.toRadians(angleDeg));
        float sin = (float)Math.sin(Math.toRadians(angleDeg));

        // use the equations
        float xPrime = (x * cos) - (y * sin);
        float yPrime = (x * sin) + (y * cos);

        // move them back to correct position
        xPrime += origin.x;
        yPrime += origin.y;

        // modify the vector
        vec.x = xPrime;
        vec.y = yPrime;
    }

    /**
     * compares two floats with the given precision
     *
     * @param x float 1
     * @param y float 2
     * @param epsilon the precision
     *
     * @return whether the floats are equal (type: bool)
     * */

    public static boolean compare(float x, float y, float epsilon){
        return Math.abs(x - y) <= epsilon * Math.max(1.0f, Math.max(Math.abs(x), Math.abs(y)));
    }
    /**
     * compares two vectors with the given precision
     *
     * @param vec1 vector 1
     * @param vec2 vector 2
     * @param epsilon the precision
     *
     * @return whether the vectors are equal (type: bool)
     * */
    public static boolean compare(Vector2f vec1, Vector2f vec2,  float epsilon){
        return compare(vec1.x, vec2.x, epsilon) && compare(vec1.y, vec2.y, epsilon);
    }


    /**
     * compares two floats with the precision of Float.MIN_VALUE
     *
     * @param x float 1
     * @param y float 2
     *
     * @return whether the floats are equal (type: bool)
     * */

    public static boolean compare(float x, float y){
        return Math.abs(x - y) <= Float.MIN_VALUE * Math.max(1.0f, Math.max(Math.abs(x), Math.abs(y)));
    }
    /**
     * compares two vectors with the precision of Float.MIN_VALUE
     *
     * @param vec1 vector 1
     * @param vec2 vector 2
     *
     * @return whether the vectors are equal (type: bool)
     * */
    public static boolean compare(Vector2f vec1, Vector2f vec2){
        return compare(vec1.x, vec2.x) && compare(vec1.y, vec2.y);
    }
}
