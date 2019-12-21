package com.joeseff.marsrover;

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

import java.util.ArrayList;
import java.util.List;

public class Rover {

    // Circle
    private int centerX = 0;
    private int centerY = 0;
    private int radius = 7;
    private Location currentLocation;
    private List<Point> obstacles;

    public Rover() {
        currentLocation = new Location();
        obstacles = new ArrayList<>();
    }

    public List<Point> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<Point> obstacles) {
        this.obstacles = obstacles;
    }

    public Location forwards(int steps) {
        return determineLocation('F', steps);
    }

    public Location backwards(int steps) {
        return determineLocation('B', steps);
    }

    public Location left(int steps) {
        return determineLocation('L', steps);
    }

    public Location right(int steps) {
        return determineLocation('R', steps);
    }

    private Location determineLocation(char direction, int steps) {
        String cardinalPoint = "N";

        if ( steps == 0 ) {
            return currentLocation;
        }

        if ( steps < 0 ) {
            steps = Math.abs(steps);
        }

        if( steps > 22 ) {
            throw new IllegalArgumentException("Number of steps exceeds circumference of sphere!");
        }

        if ( direction == 'F' ) {
            direction = 'Y';
        } else if ( direction == 'B' ) {
            direction = 'Y';
            steps = steps * -1;
        } else if ( direction == 'L' ) {
            direction = 'X';
            steps = steps * -1;
        } else if ( direction == 'R' ) {
            direction = 'X';
        }


        int yPoint = currentLocation.getY();
        int xPoint = currentLocation.getX();

        switch (direction) {
            case 'Y':
                yPoint = yPoint + steps;
                break;
            default:
                xPoint = xPoint + steps;
        }

        if (isWrappingNeeded(xPoint, yPoint)) {

            // Get notation on each point
            int xFactor = 1;
            int yFactor = 1;

            if (xPoint < 0) {
                xFactor = -1;
            }

            if (yPoint < 0) {
                yFactor = -1;
            }

            // Get furthest point on the circle in that direction
            Point maxPoint = getPointOnCircumference(xPoint, yPoint);

            // Check whether x & y individually are greater than the max point
            int absXPoint = Math.abs(xPoint);
            int absYPoint = Math.abs(yPoint);

            if (absXPoint > maxPoint.getCx()) {
                xPoint = absXPoint - maxPoint.getCx();
                while (xPoint > maxPoint.getCx()) {
                    xPoint = xPoint - maxPoint.getCx();
                }
            }

            if (absYPoint > maxPoint.getCy()) {
                yPoint = absYPoint - maxPoint.getCy();
                while (yPoint > maxPoint.getCy()) {
                    yPoint = yPoint - maxPoint.getCy();
                }
            }

            // Multiply x & y with their original factors
            xPoint = xPoint * xFactor;
            yPoint = yPoint * yFactor;

            // Multiply x & y with -1 to flip location i.e. implement wrapping
            xPoint = xPoint * -1;
            yPoint = yPoint * -1;

        }

        // Get the cardinal point
        cardinalPoint = getCardinalPoint(xPoint, yPoint);

        // Check for obstacles
        Point newPointLocation = new Point(xPoint, yPoint);
        if ( obstacles.size() > 0 ) {
            for ( Point point : obstacles ) {
                if ( point.toString().equals(newPointLocation.toString()) ) {

                    // Move up to last possible point and then report obstacle
                    if ( xPoint > 0 ) {
                        xPoint -= 1;
                    } else {
                        xPoint += 1;
                    }

                    if ( yPoint > 0 ) {
                        yPoint -= 1;
                    } else {
                        yPoint += 1;
                    }

                    // Report
                    System.out.println("Obstacle detected at Point(" + xPoint + ", " + yPoint + ")! ");
                    cardinalPoint = getCardinalPoint(xPoint, yPoint);
                    currentLocation = new Location(xPoint, yPoint, cardinalPoint);
                    return currentLocation;
                }
            }
        }

        // Set new location
        currentLocation = new Location(xPoint, yPoint, cardinalPoint);

        return currentLocation;
    }

    private boolean isWrappingNeeded(int x, int y) {
        int xInterval = Math.abs(x) - centerX;
        int yInterval = Math.abs(y) - centerY;

        int sum = (int) Math.pow(xInterval, 2) + (int) Math.pow(yInterval, 2);
        int radiusSquared = (int) Math.pow(radius, 2);
        if (sum <= radiusSquared) {
            return false; // the point is within the sphere
        }

        return true; // the point is outside of the sphere
    }

    private int getAngle(int x, int y) {
        int deltaX = Math.abs(x) - centerX;
        int deltaY = Math.abs(y) - centerY;

        double radians = Math.atan2(deltaY, deltaX);
        int angle = (int) ( radians * ( 180/Math.PI ) );
        return angle;
    }

    private Point getPointOnCircumference(int x, int y) {
        int angle = getAngle(x, y);
        int px = (int)(centerX + radius * Math.cos(angle));
        int py = (int)(centerY + radius * Math.sin(angle));

        // prevent an infinite loop
        if ( px == 0 ) {
            py = py - 2;
            px = px + 2;
        }

        if ( py == 0 ) {
            px = px - 2;
            py = py + 2;
        }

        return new Point(px, py);
    }

    private String getCardinalPoint(int xPoint, int yPoint) {
        String cardinalPoint = "N";

        // Get the cardinal point
        if ( xPoint == 0 && yPoint > 0 ) {
            cardinalPoint = "N";
        } else if ( xPoint > 0 && yPoint > 0 ) {
            cardinalPoint = "NE";
        } else if ( xPoint < 0 && yPoint > 0 ) {
            cardinalPoint = "NW";
        } else if ( xPoint == 0 && yPoint < 0 ) {
            cardinalPoint = "S";
        } else if ( xPoint < 0 && yPoint < 0 ) {
            cardinalPoint = "SW";
        } else if ( xPoint < 0 && yPoint == 0 ) {
            cardinalPoint = "W";
        } else if ( xPoint > 0 && yPoint < 0 ) {
            cardinalPoint = "SE";
        } else if ( xPoint > 0 && yPoint == 0 ) {
            cardinalPoint = "E";
        }
        return cardinalPoint;
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

    public Location(int x, int y, String cardinalPoint) {
        this.x = x;
        this.y = y;
        this.cardinalPoint = cardinalPoint;
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

    @Override
    public String toString() {
        return x + ":" + y + ":" + cardinalPoint;
    }
}

// Represents a point lying on the circle's edge
class Point {
    private int cx;
    private int cy;

    public Point() {
    }

    public Point(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;
    }

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public int getCy() {
        return cy;
    }

    public void setCy(int cy) {
        this.cy = cy;
    }

    @Override
    public String toString() {
        return cx + ":" + cy;
    }
}



