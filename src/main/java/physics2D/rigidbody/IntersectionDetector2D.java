package physics2D.rigidbody;

import org.joml.Vector2f;
import physics2D.primitives.AABB;
import physics2D.primitives.Box2D;
import physics2D.primitives.Circle;
import renderer.Line2D;
import util.Matha;

public class IntersectionDetector2D {
    // =========================
    // Point vs. Primitive Tests
    // =========================
    public static boolean pointOnLine(Vector2f point, Line2D line){
        // Variables
        // dy: delta y, dx: delta x
        // E: end, S: start

        // Formulas
        // 1: y = m*x + b
        // 2: dy = Ey - Sy
        // 3: y - m*x = b

        float dy = line.getEnd().y - line.getStart().y;
        float dx = line.getEnd().x - line.getStart().x;

        if (dx == 0f){
            return Matha.compare(point.x, line.getStart().x);
        }
        float m = dy / dx;

        float b = line.getEnd().y - (m * line.getEnd().x);

        // Check the line equation using formula 1
        return point.y == m * point.x + b;
    }

    public static boolean pointInCircle(Vector2f point, Circle circle){
        Vector2f circleCenter = circle.getCenter();
        Vector2f centerToPoint = new Vector2f(point).sub(circleCenter);

        // using the lengthSquared because the normal one uses sqrt (its expensive) so now we have to compare it to the radius * radius
        return centerToPoint.lengthSquared() <= circle.getRadius() * circle.getRadius();
    }

    public static boolean pointInAABB(Vector2f point, AABB box){
        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        return point.x <= max.x && point.x >= min.x &&
                point.y <= max.y && point.y >= min.y;
    }

    public static boolean pointInBox2D(Vector2f point, Box2D box){
        // Translate point to local space
        Vector2f pointLocalBox = new Vector2f(point);
        Matha.rotate(pointLocalBox, box.getRigidbody().getRotation(), box.getRigidbody().getPosition());

        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        return pointLocalBox.x <= max.x && pointLocalBox.x >= min.x &&
                pointLocalBox.y <= max.y && pointLocalBox.y >= min.y;
    }

    // ========================
    // Line vs. Primitive Tests
    // ========================

    public static boolean lineAndCircle(Line2D line, Circle circle){
        // Formulas
        // A.B = |A||B|cos(Theta)
        // (A.B) / (B.B) = |A| / |B|

        // if start or end of the line is in the circle, return true
        if (pointInCircle(line.getStart(), circle) || pointInCircle(line.getEnd(), circle)){
            return true;
        }

        Vector2f ab = new Vector2f(line.getEnd()).sub(line.getStart());

        // project the point (circle position) onto ab (line segment)
        // parameterized position t
        Vector2f circleCenter = circle.getCenter();
        Vector2f centerToLineStart = new Vector2f(circleCenter).sub(line.getStart());

        // dot the vector (t is the percentage)
        float t = centerToLineStart.dot(ab) / ab.dot(ab);

        if (t < 0.0f || t > 1.0f){
            // then it means its not on the line
            return false;
        }

        // Find the closest point to line segment
        Vector2f closestPoint = new Vector2f(line.getStart()).add(ab.mul(t));

        return pointInCircle(closestPoint, circle);
    }

    public static boolean lineAndAABB(Line2D line, AABB box){
        // honestly, I don't fully understand it so I can't explain how it works well.
        // check this video if you are interested: https://youtu.be/eo_hrg6kVA8

        // check if the start or end point is in the box. if they are return true immediately
        if (pointInAABB(line.getStart(), box) || pointInAABB(line.getEnd(), box)){
            return true;
        }

        Vector2f unitVector = new Vector2f(line.getEnd()).sub(line.getStart());
        unitVector.normalize();
        unitVector.x = (unitVector.x != 0) ? 1.0f / unitVector.x : 0f;
        unitVector.y = (unitVector.y != 0) ? 1.0f / unitVector.y : 0f;

        Vector2f min = box.getMin();
        min.sub(line.getStart()).mul(unitVector);
        Vector2f max = box.getMax();
        max.sub(line.getStart()).mul(unitVector);

        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.min(min.y, max.y));
        if (tmax < 0 || tmin > tmax){
            return false;
        }

        float t = (tmin < 0f) ? tmax : tmin;
        return t > 0f && t * t < line.lengthSquared();
    }

    public static boolean lineAndBox2D(Line2D line, Box2D box){
        float theta = -box.getRigidbody().getRotation();
        Vector2f center = box.getRigidbody().getPosition();
        Vector2f localStart = new Vector2f(line.getStart());
        Vector2f localEnd = new Vector2f(line.getEnd());
        Matha.rotate(localStart, theta, center);
        Matha.rotate(localEnd, theta, center);

        Line2D localLine = new Line2D(localStart, localEnd);
        AABB aabb = new AABB(box.getMin(), box.getMax());

        return lineAndAABB(localLine, aabb);
    }

}
