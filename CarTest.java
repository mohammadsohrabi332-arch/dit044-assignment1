import java.awt.*;

import static java.awt.Color.*;

public class CarTest {
    private static void reportResult(String testName, boolean success) {
        IO.println((success ? "SUCCESS: " : "FAILURE: ") + testName);
    }

    /** Tests whether a Saab95 with turbo enabled
     *  gains more speed than one without.
     */
    private static void testTurbo() {
        Saab95 slowSaab = new Saab95();
        Saab95 fastSaab = new Saab95();
        fastSaab.setTurboOn();
        slowSaab.setTurboOff();
        fastSaab.gas(0.5);
        slowSaab.gas(0.5);
        boolean result = slowSaab.getCurrentSpeed() < fastSaab.getCurrentSpeed();
        reportResult("Turbo yields more speed", result);
    }

    public static void testStartEngine() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        boolean result = volvo.getCurrentSpeed() == 0.1;
        reportResult("Speed at starting engine", result);
    }

    public static void testStopEngine() {
        Saab95 saab = new Saab95();
        saab.startEngine();
        double speedBeforeStopping = saab.getCurrentSpeed();
        saab.stopEngine();
        boolean results = speedBeforeStopping > 0 && saab.getCurrentSpeed() == 0;
        reportResult( "Speed at stopping engine", results);
    }

    private static void testnrDoorsSaab95() {
        Saab95 saab = new Saab95();
        boolean result = saab.getNrDoors() == 2;
        reportResult( "Number of doors on a saab95", result);
    }

    private static void testnrDoorsVolvo240() {
        Volvo240 volvo = new Volvo240();
        boolean result = volvo.getNrDoors() == 4;
        reportResult( "Number of doors on a volvo 240", result);
    }

    private static void testColorSaab95() {
        Saab95 saab = new Saab95();
        boolean result = saab.getColor().equals(Color.red);
        reportResult( "color on Saab95",result);

    }

    private static void testColorVolvo240() {
        Volvo240 volvo = new Volvo240();
        boolean result = volvo.getColor().equals(Color.black);
        reportResult( "color on Volvo240",result);

    }

    // Position calculations can have tiny floating-point rounding differences.
    private static boolean closeTo(double actual, double expected) {
        return Math.abs(actual - expected) < 0.000000001;
    }

    private static void testMoveUp() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        volvo.gas(0.5);
        double speed = volvo.getCurrentSpeed();
        double xBefore = volvo.getX();
        double yBefore = volvo.getY();

        volvo.move();

        boolean result = speed > 0
                && closeTo(volvo.getX(), xBefore)
                && closeTo(volvo.getY(), yBefore + speed);
        reportResult("Moving up increases y by current speed", result);
    }

    private static void testMoveRight() {
        Saab95 saab = new Saab95();
        saab.startEngine();
        saab.gas(0.5);
        double speed = saab.getCurrentSpeed();
        double xBefore = saab.getX();
        double yBefore = saab.getY();

        saab.turnRight();
        saab.move();

        boolean result = speed > 0
                && closeTo(saab.getX(), xBefore + speed)
                && closeTo(saab.getY(), yBefore);
        reportResult("Turning right makes movement increase x", result);
    }

    private static void testMoveDown() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        volvo.gas(0.5);
        double speed = volvo.getCurrentSpeed();
        double xBefore = volvo.getX();
        double yBefore = volvo.getY();

        volvo.turnRight();
        volvo.turnRight();
        volvo.move();

        boolean result = speed > 0
                && closeTo(volvo.getX(), xBefore)
                && closeTo(volvo.getY(), yBefore - speed);
        reportResult("Two right turns make movement decrease y", result);
    }

    private static void testMoveLeft() {
        Saab95 saab = new Saab95();
        saab.startEngine();
        saab.gas(0.5);
        double speed = saab.getCurrentSpeed();
        double xBefore = saab.getX();
        double yBefore = saab.getY();

        saab.turnLeft();
        saab.move();

        boolean result = speed > 0
                && closeTo(saab.getX(), xBefore - speed)
                && closeTo(saab.getY(), yBefore);
        reportResult("Turning left makes movement decrease x", result);
    }

    private static void testFullRotation() {
        Volvo240 volvo = new Volvo240();
        Saab95 saab = new Saab95();
        int volvoDirection = volvo.getDirection();
        int saabDirection = saab.getDirection();

        volvo.turnRight();
        saab.turnLeft();
        boolean directionsChanged = volvo.getDirection() != volvoDirection
                && saab.getDirection() != saabDirection;
        for (int turn = 0; turn < 3; turn++) {
            volvo.turnRight();
            saab.turnLeft();
        }

        boolean result = directionsChanged
                && volvo.getDirection() == volvoDirection
                && saab.getDirection() == saabDirection;
        reportResult("Four turns restore the original directions", result);
    }

    private static void testStoppedCarDoesNotMove() {
        Volvo240 volvo = new Volvo240();
        volvo.startEngine();
        volvo.move();
        double xBeforeStopping = volvo.getX();
        double yBeforeStopping = volvo.getY();

        volvo.stopEngine();
        volvo.move();

        boolean result = closeTo(volvo.getCurrentSpeed(), 0)
                && closeTo(volvo.getX(), xBeforeStopping)
                && closeTo(volvo.getY(), yBeforeStopping);
        reportResult("A stopped car stays at its current position", result);
    }

    // JOUD: Tests that gas() throws an exception for an amount above 1.
    private static void testGasRejectsTooLarge() {
        Volvo240 volvo = new Volvo240();
        boolean rejected = false;
        try {
            volvo.gas(5);

        } catch (IllegalArgumentException e) {
            rejected = true;
        }
        reportResult("Gas rejects amount above 1", rejected);
    }

    // JOUD: Tests that brake() throws an exception for an amount above 1.
    private static void testBrakeRejectsTooLarge() {
        Volvo240 volvo = new Volvo240();
        boolean rejected = false;
        try {
            volvo.brake(5);

        } catch (IllegalArgumentException e) {
            rejected = true;
        }
        reportResult("Brake rejects amount above 1", rejected);
    }

    /* JOUD: Tests that brake() throws an exception for a negative amount;
        since braking with a negative amount would increase speed.*/
        private static void testBrakeRejectsNegative() {
            Volvo240 volvo = new Volvo240();
            boolean rejected = false;
            try {
                volvo.brake(-1);

            } catch (IllegalArgumentException e) {
                rejected = true;
            }
            reportResult("Brake rejects negative amount", rejected);
    }
    // MOMO - Tests that gas() throws an exception for a negative amount.
    private static void testGasRejectsNegative() {
            Volvo240 volvo = new Volvo240();
            boolean rejected = false;
            try {
                volvo.gas(-1);
            } catch(IllegalArgumentException e) {
                rejected = true;
            }
            reportResult("Gas rejects negative amount", rejected);
    }

    private static void testSpeedDoesNotExceedEnginePower() {
            Volvo240 volvo = new Volvo240();
            volvo.startEngine();
            for (int i = 0; i < 200; i++) {
                volvo.gas(1);
            }
            boolean result = volvo.getCurrentSpeed() <= volvo.getEnginePower();
            reportResult("Current speed does not exceed enginePower", result);
    }
    private static void testSpeedDoesNotDropBelowZero() {
            Volvo240 volvo = new Volvo240();
            volvo.startEngine();
            for (int i = 0; i < 10; i++) {
                volvo.brake(1);
            }
            boolean result = volvo.getCurrentSpeed() >= 0;
            reportResult("Current speed does not drop below zero", result);
    }


    private static void testGasDoesNotDecreaseSpeed() {
        Car[] cars = {new Volvo240(), new Saab95()};
        boolean result = true;
        for (Car car : cars) {
            car.startEngine();
            for (int i = 0; i < 200; i++) {
                double speedBefore = car.getCurrentSpeed();
                car.gas(1);
                result = result && car.getCurrentSpeed() >= speedBefore
                        && car.getCurrentSpeed() <= car.getEnginePower();
            }
        }
        reportResult("Gas never decreases speed on either car", result);
    }

    private static void testBrakeDoesNotIncreaseSpeed() {
        Car[] cars = {new Volvo240(), new Saab95()};
        boolean result = true;
        for (Car car : cars) {
            car.startEngine();
            for (int i = 0; i < 200; i++) {
                car.gas(1);
            }
            for (int i = 0; i < 200; i++) {
                double speedBefore = car.getCurrentSpeed();
                car.brake(1);
                result = result && car.getCurrentSpeed() <= speedBefore
                        && car.getCurrentSpeed() >= 0;
            }
        }
        reportResult("Brake never increases speed on either car", result);
    }

    private static void testZeroPedalAmount() {
        Car[] cars = {new Volvo240(), new Saab95()};
        boolean result = true;
        for (Car car : cars) {
            car.startEngine();
            double speedBefore = car.getCurrentSpeed();
            car.gas(0);
            result = result && closeTo(car.getCurrentSpeed(), speedBefore);
            car.brake(0);
            result = result && closeTo(car.getCurrentSpeed(), speedBefore);
        }
        reportResult("Zero gas and brake leave speed unchanged on either car", result);
    }

    static void main() {
        testTurbo();
        testStartEngine();
        testStopEngine();
        testnrDoorsSaab95();
        testnrDoorsVolvo240();
        testColorVolvo240();
        testColorSaab95();
        testMoveUp();
        testMoveRight();
        testMoveDown();
        testMoveLeft();
        testFullRotation();
        testStoppedCarDoesNotMove();
        testGasRejectsTooLarge();  // JOUD: Task 4: sanity checks for gas().
        testBrakeRejectsNegative(); // JOUD: Task 4: sanity checks for brake().
        testBrakeRejectsTooLarge(); // JOUD: Task 4: sanity checks for brake().
        testSpeedDoesNotDropBelowZero(); // MOMO: Task 4: sanity checks for minimum speed.
        testSpeedDoesNotExceedEnginePower(); // MOMO: Task 4: sanity checks for maximum speed.
        testGasRejectsNegative(); // MOMO: Task 4: sanity check for gas():
        testGasDoesNotDecreaseSpeed();
        testBrakeDoesNotIncreaseSpeed();
        testZeroPedalAmount();
    }
}
