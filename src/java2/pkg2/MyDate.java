package java2.pkg2;

import java.io.Serializable;

public class MyDate implements Cloneable, Serializable, Comparable<MyDate> {
  
  private int year;
  private int month;
  private int day;
  
  public MyDate(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }
  public MyDate(MyDate date) {
    this.year = date.year;
    this.month = date.month;
    this.day = date.day;
  }

  public int getYear() {
    return year;
  }
  public int getMonth() {
    return month;
  }
  public int getDay() {
    return day;
  }

  public void setDate(int newYear, int newMonth, int newDay) {
    year = newYear;
    month = newMonth;
    day = newDay;
  }

  public String toString() {
    return month + " / " + day + " / " +  year;
  }

  public String toDelimitedString() {
    return year + "," + month + "," + day;
  }
  
  @Override
  public MyDate clone() throws CloneNotSupportedException {
    MyDate output = (MyDate) super.clone();
    return output;
  }

  public int compareTo(MyDate other) {
    if (year == other.year && month == other.month && day == other.day) return 0;
    return isExpired(other) ? -1 : 1;
  }

  public boolean isExpired(MyDate expiryDate) {
    if (year < expiryDate.year) {
      return true;
    } else if (year == expiryDate.year && month < expiryDate.month) {
      return true;
    } else if (year == expiryDate.year && month == expiryDate.month && day < expiryDate.day) {
      return true;
    } 
    return false;
  }
}