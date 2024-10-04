package java2.pkg2;

import java.io.Serializable;

public class Car implements Cloneable, Serializable {

  private String model;
  private CarType type;
  private int manufacturingYear;
  private double price;

  public Car(String model, CarType type, int manufacturingYear, double price) {    
    this.model = model;
    this.type = type;
    this.manufacturingYear = manufacturingYear;
    this.price = price;  
  }
  public Car(Car car) {    
    this.model = car.model;
    this.type = car.type;
    this.manufacturingYear = car.manufacturingYear;
    this.price = car.price;  
  }
  
  public String getModel() {
    return this.model;
  }
  public CarType getType() {
    return type;
  }
  public int getManufacturingYear() {
    return manufacturingYear;
  }
  public double getPrice() {
    return this.price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
  public void setModel(String model) {
    this.model = model;
  }
  public void setManufacturingYear(int manufacturingYear) {
    this.manufacturingYear = manufacturingYear;
  }
  public void setType(CarType type) {
    this.type = type;
  }

  public String toString() {
    return "\nModel: " + model + ", type: " + type + ", Manufacturing year: "
      + manufacturingYear + ", Price: " + price;
  }

  public String toDelimitedString() {
    return model + "," + type + "," + manufacturingYear + "," + price;
  }

  @Override
  public Car clone() throws CloneNotSupportedException {
    Car output = (Car) super.clone();
    return output; 
  }
}