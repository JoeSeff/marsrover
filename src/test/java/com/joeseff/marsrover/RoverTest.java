package com.joeseff.marsrover;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RoverTest {

    Rover rover;

    @Test
    public void testMoveForwards() {
        rover = new Rover();
        Location currentLocation = rover.forwards(5);

        String expectedValue = "0:5:N";
        assertEquals(expectedValue, currentLocation.toString());
    }

    @Test
    public void testMoveBackwards() {
        rover = new Rover();
        Location currentLocation = rover.backwards(5);

        String expectedValue = "0:-5:S";
        assertEquals(expectedValue, currentLocation.toString());
    }

    @Test
    public void testMoveLeft() {
        rover = new Rover();
        Location currentLocation = rover.left(5);

        String expectedValue = "-5:0:W";
        assertEquals(expectedValue, currentLocation.toString());
    }

    @Test
    public void testMoveRight() {
        rover = new Rover();
        Location currentLocation = rover.right(5);

        String expectedValue = "5:0:E";
        assertEquals(expectedValue, currentLocation.toString());
    }

    @Test
    public void testNoMovement() {
        rover = new Rover();
        Location currentLocation = rover.right(0);

        String expectedValue = "0:0:N";
        assertEquals(expectedValue, currentLocation.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceedsOrbitAroundSphere() {
        rover = new Rover();
        Location currentLocation = rover.right(23);

        String expectedValue = "0:0:N";
        assertEquals(expectedValue, currentLocation.toString());
    }

    @Test
    public void testMovement() {
        rover = new Rover();

        Location currentLocation = rover.right(4);
        String expectedValue = "4:0:E";
        assertEquals(expectedValue, currentLocation.toString());

        currentLocation = rover.forwards(4);
        expectedValue = "4:4:NE";
        assertEquals(expectedValue, currentLocation.toString());

        currentLocation = rover.left(9);
        expectedValue = "-5:4:NW";
        assertEquals(expectedValue, currentLocation.toString());

        currentLocation = rover.backwards(7);
        expectedValue = "-5:-3:SW";
        assertEquals(expectedValue, currentLocation.toString());

        currentLocation = rover.right(8);
        expectedValue = "3:-3:SE";
        assertEquals(expectedValue, currentLocation.toString());
    }

    @Test
    public void testMovementWithWrapping() {
        rover = new Rover();

        Location currentLocation = rover.right(5);
        String expectedValue = "5:0:E";
        assertEquals(expectedValue, currentLocation.toString());

        currentLocation = rover.forwards(5);
        expectedValue = "-2:-5:SW";
        assertEquals(expectedValue, currentLocation.toString());
    }

    @Test
    public void testObstacleDetection() {
        rover = new Rover();
        List<Point> obstaclesList = new ArrayList<>();
        obstaclesList.add(new Point(3, 3));
        rover.setObstacles(obstaclesList);

        Location currentLocation = rover.right(3);
        currentLocation = rover.forwards(3);
        String expectedValue = "2:2:NE";
        assertEquals(expectedValue, currentLocation.toString());


    }
}
