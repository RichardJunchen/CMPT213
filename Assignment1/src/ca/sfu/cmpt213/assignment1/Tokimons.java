package ca.sfu.cmpt213.assignment1;

public class Tokimons {
    private String name;
    private String type;
    private double weight;
    private double height;
    private int strength;

    public Tokimons() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String toString() {
        return getClass().getName() +" [ " +
                "Name = " + name  +
                ", Ability = " + type +
                ", Weight = " + weight +
                ", Height = " + height +
                ", Strength = " + strength +
                " ]";
    }
}
