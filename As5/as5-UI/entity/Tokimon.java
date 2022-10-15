package ca.sfu.cmpt213.Assignment5.entity;

/**
 * 实体类
 */
public class Tokimon implements Cloneable {
    private String id;
    private String name;
    private String weight;
    private String height;
    private String ability;
    private String strength;
    private String colour;
    private boolean selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Tokimon() {
    }

    public Tokimon(String id, String name, String weight, String height, String ability, String strength, String colour) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.ability = ability;
        this.strength = strength;
        this.colour = colour;
    }

    @Override
    public Tokimon clone() throws CloneNotSupportedException {
        return (Tokimon) super.clone();
    }

    @Override
    public String toString() {

        return id+","+name+","+weight+","+height+","+ability+","+strength+","+colour+"\n";
    }
}
