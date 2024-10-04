package java2.pkg2;

public class ComprehensivePolicy extends InsurancePolicy {

  private int driverAge;
  private int level;

  public ComprehensivePolicy(String policyHolderName, int id, Car car, MyDate date, int numberPfClaims, int driverAge, int level) throws PolicyException, PolicyHolderNameException {
    super(policyHolderName, id, car, date, numberPfClaims);
    this.driverAge = driverAge;
    this.level = level;
  } 
  public ComprehensivePolicy(ComprehensivePolicy policy) {
    super(policy);
    this.driverAge = policy.driverAge;
    this.level = policy.level;
  }

  public int getDriverAge() {
    return driverAge;
  }
  public int getLevel() {
    return level;
  }
  public void setDriverAge(int driverAge) {
    this.driverAge = driverAge;
  }
  public void setLevel(int level) {
    this.level = level;
  }

  @Override
  public void print() {
    super.print();
    System.out.println(", Driver age: " + driverAge + ", Level: " + level);
  }
  
  @Override
  public String toString() {
    return super.toString() + ", Driver age: " + driverAge + ", Level: " + level; 
  }
  
  @Override
  public String toDelimitedString() {
    return "cp," + super.toDelimitedString() + "," + driverAge + "," + level;
  }

  public static ComprehensivePolicy createComprehensivePolicy(String policyHolderName, int id, Car car, MyDate date, int numberPfClaims, int driverAge, int level) throws PolicyException, PolicyHolderNameException {
    try {
      System.out.println("Policy: " + id + " created successfully!");
      return new ComprehensivePolicy(policyHolderName, id, car, date, numberPfClaims, driverAge, level);
    } catch (PolicyException e) {
      System.out.println(e);
      return new ComprehensivePolicy(policyHolderName, e.getNewID(), car, date, numberPfClaims, driverAge, level);
    } catch (PolicyHolderNameException e) {
      System.out.println(e);
      return null;
    }
  }

  public double calcPayment(double flatRate) {
    double payment = (car.getPrice() / 50) + (numberOfClaims * 200) + flatRate; 
    if (driverAge < 30) payment += ((30 - driverAge) * 50); 
    return payment;
  }
}
