package me.alexirving.core.actions;

import me.alexirving.core.animation.objects.Offset;

public class Circle3D {
    //initialize variables
    private final double currentX;
    private final double currentY;
    private final double currentZ;

    public Circle3D(int X, int Y, int Z) {
        this.currentX = X;
        this.currentY = Y;
        this.currentZ = Z;
    }

    public void drawCircle(double originX, double originY, double originZ, double angleOfPlane, double degreesOfRotation, int numberOfFrames) {
        //draw a vector v[x, y, z] to the center of the circle and use that point as the origin for the following calculations
        Offset originVector = new Offset(originX, originY, originZ);
        //change angleOfPlane from degrees to radians and add 90 (perpendicular to the normal)
        angleOfPlane = Math.toRadians(angleOfPlane + 90);

        //draw a vector from the origin to the current point to use as our radius vector
        Offset radiusVector = new Offset(currentX - originX, currentY - originY, currentZ - originZ);
        //project that vector onto the xz plane and take their cross product to get the vector perpendicular to the radius on the xz plane (N0)
        //use N0 and N90 as unit vectors to define the span of all possible vectors perpendicular to the radius
        Offset radiusOnXZPlane = new Offset(radiusVector.getX(), 0.0, radiusVector.getZ());
        Offset normal0 = Offset.Companion.cross(radiusOnXZPlane, radiusVector);
        Offset normal90 = Offset.Companion.cross(radiusVector, normal0);
        normal0.normalize();
        normal90.normalize();

        //create the unit vectors of the plane of the circle, where the first axis of the plane is the radius vector normalized,
        // and the second axis is a perpendicular normal vector such that the plane defined by these two vectors is at some angle theta+90 to the xz plane
        // (where theta=0 would be a circle that is perpendicular to the xz plane)
        Offset unitVectorR = Offset.Companion.normalize(radiusVector);
        Offset unitVectorA = Offset.Companion.add(Offset.Companion.scale(normal0, Math.cos(angleOfPlane)), Offset.Companion.scale(normal90, Math.sin(angleOfPlane)));

        //create variables for calculating the circle
        final double incrementThetaBy = Math.toRadians(degreesOfRotation / numberOfFrames);
        double currentTheta = incrementThetaBy;
        double radiusLength = radiusVector.length();
        Offset currentPoint;

        //scale the unit vectors, multiply by the length and respective trig function, add them, and add the origin vector to the result for each frame
        for (int i = 0; i < numberOfFrames; i++) {
            currentPoint = Offset.Companion.add(Offset.Companion.scale(unitVectorR, radiusLength * Math.cos(currentTheta)), Offset.Companion.scale(unitVectorA, radiusLength * Math.sin(currentTheta)));
            currentPoint.add(originVector);
            System.out.println(currentPoint);
            //increment theta
            currentTheta += incrementThetaBy;
        }
    }

    public static void main(String[] args) {
        Circle3D circle = new Circle3D(-12, 6, 19);
        circle.drawCircle(10, 15, -5, 77, 360, 10);
    }
}