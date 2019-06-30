package ua.jland.classloaders.ex3;

public class Person {
    private int id;
    private String name;
    private double salary;
    private boolean hasDrivingLicense;

    public double getFinalSalary(double taxRate) {
        return salary - calculateTax(taxRate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public boolean isHasDrivingLicense() {
        return hasDrivingLicense;
    }

    public void setHasDrivingLicense(boolean hasDrivingLicense) {
        this.hasDrivingLicense = hasDrivingLicense;
    }

    private double calculateTax(double taxRate) {
        // some tax calculations
        return salary * taxRate;
    }

}