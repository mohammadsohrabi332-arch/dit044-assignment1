import java.awt.*;

//Car is the dad and has two children Volvo240 and Saab95.
//Car preserves all the shared features of both cars.
//Combined all the shared features into one file.
public abstract class Car implements Movable {
    private int nrDoors;
    private double enginePower;
    private double currentSpeed;
    private Color color;
    private String modelName;


    private double x;
    private double y;
    private int direction; // 0 = UP, 1 = RIGHT, 2 = DOWN, 3 = LEFT

    public Car(int nrDoors, double enginePower, double currentSpeed, Color color, String modelName) {
        this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.currentSpeed = currentSpeed;
        this.color = color;
        this.modelName = modelName;
    }

    public int getNrDoors() {
        return nrDoors;
    }

    public double getEnginePower() {
        return enginePower;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color clr) {
        color = clr;
    }

    public void startEngine() {
        currentSpeed = 0.1;
    }

    public void stopEngine() {
        currentSpeed = 0;
    }
    // MOMO: Increases speed but ensures it never exceeds enginePower.
    private void incrementSpeed(double amount) {
        currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount, enginePower);
    }
    // MOMO: Decreases speed but ensures it never drops below 0.
    private void decrementSpeed(double amount) {
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount, 0);
    }

    // JOUD: Increases speed. Only accepts amounts in [0,1];
    // JOUD: Anything else throws IllegalArgumentException (Task 4).
    public void gas(double amount) {
        if ( amount < 0 || amount > 1) {
            throw new IllegalArgumentException("amount must be a number between 0 and 1");
        }
        incrementSpeed(amount);
    }

    // JOUD: Decreases speed. only accepts amounts in [0,1];
    // JOUD: A negative amount would otherwise increase speed (Task 4).
    public void brake(double amount) {
        if ( amount < 0 || amount > 1) {
            throw new IllegalArgumentException("amount must be a number between 0 and 1");
        }
        decrementSpeed(amount);
    }

    //Different speedFactor calculation for each car model.
    //Every subclass must have its own version.
    //speedFactor is limited use for the dad and children.
    //abstract: used because the children can calculate the speedFactor but not the dad.
    //We will implement their own versions inside it later.
    protected abstract double speedFactor();

    // Turn left
    @Override
    public void turnLeft() {
        direction = (direction + 3) % 4;
    }
    // Turn right
    @Override
    public void turnRight() {
        direction = (direction + 1) % 4;
    }

    /* Implements Movable:
    Moves the car in the correct direction
    For example: direction = 0
    the car moves up and y-coordinate increases
    Note from Momo: Think of a graph
    from MATH with x and y axis. */
    @Override
    public void move() {
        if (direction == 0) {
            y += currentSpeed;
        } else if (direction == 1) {
            x += currentSpeed;
        } else if (direction == 2) {
            y -= currentSpeed;
        } else if (direction == 3) {
            x -= currentSpeed;
        }
    }
    // Gets the cars x-position
    public double getX() {
        return x;
    }
    // Gets the cars y-position
    public double getY() {
        return y;
    }
    /* Gets the cars direction
    (0 = Up, 1 = Right,
    2 = Down, 3 = Left) */
    public int getDirection() {
        return direction;
    }
}

