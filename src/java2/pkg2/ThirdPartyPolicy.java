package java2.pkg2;

public class ThirdPartyPolicy extends InsurancePolicy {

  private String comments;
  
  public ThirdPartyPolicy(String policyHolderName, int id, Car car, MyDate date, int numberPfClaims, String comments) throws PolicyException, PolicyHolderNameException {
    super(policyHolderName, id, car, date, numberPfClaims);
    this.comments = comments;
  }
  public ThirdPartyPolicy(ThirdPartyPolicy policy) {
    super(policy);
    this.comments = policy.comments;
  }

  public String getComments() {
    return comments;
  }
  public void setComments(String comments) {
    this.comments = comments;
  }

  @Override
  public void print() {
    super.print();
    System.out.println(", Comments: " + comments);
  }

  @Override
  public String toString() {
    return super.toString() + ", Comments: " + comments; 
  }  
  
  @Override
  public String toDelimitedString() {
    return "tp," + super.toDelimitedString() + "," + comments;
  }

  public double calcPayment(double flatRate) {
    return (car.getPrice() / 100) + (numberOfClaims * 200) + flatRate;
  }
  
  public static ThirdPartyPolicy createThirdPartyPolicy(String policyHolderName, int id, Car car, MyDate date, int numberPfClaims, String comments) throws PolicyException, PolicyHolderNameException {
    try {
      return new ThirdPartyPolicy(policyHolderName, id, car, date, numberPfClaims, comments);
    } catch (PolicyException e) {
      System.out.println(e);
      return new ThirdPartyPolicy(policyHolderName, e.getNewID(), car, date, numberPfClaims, comments);
    } catch (PolicyHolderNameException e) {
      System.out.println(e);
      return null;
    }
  }
}