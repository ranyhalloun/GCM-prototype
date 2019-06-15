package Entities;

import java.io.Serializable;

public class Coordinates implements Serializable {

    private double x_cord;
    private double y_cord;

    public Coordinates() {
        this.x_cord = 0.0;
        this.y_cord = 0.0;
    }
    public Coordinates(double x_cord, double y_cord) {
        this.setX_cord(x_cord);
        this.setY_cord(y_cord);
    }

    // Getters
    public double getX_cord() {
        return x_cord;
    }

    public double getY_cord() {
        return y_cord;
    }

    // Setters
    public void setX_cord(double x_cord) {
        this.x_cord = x_cord;
    }

    public void setY_cord(double y_cord) {
        this.y_cord = y_cord;
    }

}