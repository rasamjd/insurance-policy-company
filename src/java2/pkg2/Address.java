package java2.pkg2;

import java.io.Serializable;

public class Address implements Cloneable, Serializable, Comparable<Address> {

  private int streetNum;
  private String street;
  private String suburb;
  private String city;

  public Address(int streetNum, String street, String suburb, String city) {
    this.streetNum = streetNum;
    this.street = street;
    this.suburb = suburb;
    this.city = city; 
  }
  public Address(Address address) {
    this.streetNum = address.streetNum;
    this.street = address.street;
    this.suburb = address.suburb;
    this.city = address.city; 
  }
  
  public int getStreetNum() {
    return streetNum;
  }
  public String getStreet() {
    return street;
  }
  public String getSuburb() {
    return suburb;
  }
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String toString() {
    return "Street number: " + streetNum + ", Street: " + street
      + ", Suburb: " + suburb + ", City: " + city;
  }
    
  public String toDelimitedString() {
    return streetNum + "," + street + "," + suburb + "," + city;
  }

  @Override
  public Address clone() throws CloneNotSupportedException {
    Address output = (Address) super.clone();
    return output;
  }

  @Override
  public int compareTo(Address other) {
    return city.compareTo(other.city);
  }
}