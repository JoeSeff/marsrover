package com.joeseff.marsrover;

public class Rover {

    // Circle
    private int centerX = 0;
    private int centerY = 0;
    private int radius = 7;

    // Directions
    // X0, Y+ = N
    // X+, Y+ = NE
    // X-, Y+ = NW
    // X0, Y- = S
    // X-, Y- = SW
    // X-, Y0 = W
    // X+, Y- = SE
    // X+, Y0 = E
    // X0, Y0 = Centre

    // forward - backwards, will be movement on the y axis
    // forwards y is positive, backwards, y is negative

    // left - right, will be movement on the x axis
    // left y is negative, right y is positive


    public Rover() {

    }

    // direction = {left, right, forwards, backwards}
    public Location move(int xSteps, int ySteps, char direction) {

        return null;
    }

    public boolean isWrappingNeeded(int x, int y) {
        int xInterval = Math.abs(x) - centerX;
        int yInterval = Math.abs(y) - centerY;

        int sum = (int)Math.pow(xInterval, 2) + (int)Math.pow(yInterval, 2);
        int radiusSquared = (int) Math.pow(radius, 2);
        if ( sum <= radiusSquared ) {
            return false; // the point is within the sphere
        }

        return true; // the point is outside of the sphere
    }

}


// Represents the current location of the rover on the sphere
class Location {
    private int x;
    private int y;
    private String cardinalPoint;

    public Location() {
        x = 0;
        y = 0;
        cardinalPoint = "N"; // By default the rover will face the North when the program begins
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getCardinalPoint() {
        return cardinalPoint;
    }

    public void setCardinalPoint(String cardinalPoint) {
        this.cardinalPoint = cardinalPoint;
    }
}
