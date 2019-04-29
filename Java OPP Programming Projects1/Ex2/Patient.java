/**
 * Class creates a medical patient object that is defined by the name, age and weight of a person
 */

public class Patient implements Measurable {

    private String name;
    private int age;
    private double weight;

    /**
     * Constructor for the patient object
     * @param name of the patient
     * @param age of the patient
     * @param weight  of the patient
     */
    public Patient(String name, int age, double weight){

        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    /**
     * Overrides the Measurable interface getValue method
     * @return the weight of the current patient object
     */
    @Override
    public double getValue(){
        return this.weight;
    }

    /**
     * getter for name
     * @return patient name
     */
    public String getName() {
        return name;
    }

    /**
     * setter patient name
     * @param name new patient name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter patient age
     * @return the current age of a patient object
     */
    public int getAge() {
        return age;
    }

    /**
     * setter patient age
     * @param age new patient objects age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * getter patient weight
     * @return the current patient objects weight value
     */
    public double getWeight() {
        return weight;
    }

    /**
     * setter the  weight value of a patient object.
     * @param weight new weight value for the current objects weight field.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
}
