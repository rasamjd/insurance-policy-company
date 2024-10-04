package java2.pkg2;

import java.io.IOException;
import java.util.HashMap;

public class UITools {

  public static User findUser(InsuranceCompany company) {
    int userID = Tools.getInputInt("Enter a user ID: ");
    if (company.findUser(userID) != null) {
      Tools.printFailure("There is no user with the given ID!");
      return null;
    }
    return company.findUser(userID);
  }

  static public void createUser(InsuranceCompany company) {
    String name = Tools.getInputString("Enter a name: ");
    String password = Tools.getInputString("Enter a password: ");
    int id = Tools.getInputInt("Enter an ID: ");
    Address newAddress = getNewAddress();
    addUser(name, password, id, newAddress, company);
  }

  // For user menu. This one also has an auth step
  static public void createThirdPartyPolicy(int userID, InsuranceCompany company, String username, String passwrord) throws PolicyException, PolicyHolderNameException {
    if (!company.findUser(userID).isValid(username, passwrord)) {
      Tools.printFailure("Failed authorization!");
      return;
    }
    UITools.createThirdPartyPolicy(userID, company);
  }

  static public void createThirdPartyPolicy(int userID, InsuranceCompany company) throws PolicyException, PolicyHolderNameException {
    if (userID == -1) userID = Tools.getInputInt("Enter a user ID: ");      
    int policyID = Tools.getInputInt("Enter policy ID: ");
    String holderName = Tools.getInputString("Enter policy holder name: ");
    Car newCar = getNewCar();
    System.out.println("Enter an expiry date");
    MyDate newExpiryDate = getNewDate();
    int numOfClaims = Tools.getInputInt("Enter number of claims: ");
    String comments = Tools.getInputString("Enter comments: ");
    try {
      createThirdPartyPolicy(userID, company, holderName, policyID, newCar, numOfClaims, newExpiryDate, comments);
    } catch (PolicyException e) {
      createThirdPartyPolicy(userID, company, holderName, e.getNewID(), newCar, numOfClaims, newExpiryDate, comments);
      System.out.println(e);
    }
  }
  
  // For user menu. This one also has an auth step
  static public void createComprehensivePolicy(int userID, InsuranceCompany company, String username, String passwrord) throws PolicyException, PolicyHolderNameException {
    if (!company.findUser(userID).isValid(username, passwrord)) {
      Tools.printFailure("Failed authorization!");
      return;
    }
    UITools.createComprehensivePolicy(userID, company);
  }

  static public void createComprehensivePolicy(int userID, InsuranceCompany company) throws PolicyException, PolicyHolderNameException {
    if (userID == -1) userID = Tools.getInputInt("Enter a user ID: ");      
    int policyID = Tools.getInputInt("Enter policy ID: ");
    String holderName = Tools.getInputString("Enter policy holder name: ");
    Car newCar = getNewCar();
    System.out.println("Enter an expiry date");
    MyDate newExpiryDate = getNewDate();
    int numOfClaims = Tools.getInputInt("Enter number of claims: ");
    int diverAge = Tools.getInputInt("Enter driver's age: ");
    int level = Tools.getInputInt("Enter policy level: ");
    try {
      createComprehensivePolicy(userID, company, holderName, policyID, newCar, numOfClaims, newExpiryDate, diverAge, level);
    } catch (PolicyException e) {
      createComprehensivePolicy(userID, company, holderName, e.getNewID(), newCar, numOfClaims, newExpiryDate, diverAge, level);
      System.out.println(e);
    }
  }

  static public void printUserInformation(InsuranceCompany company) {
    int userID = Tools.getInputInt("Enter a user ID: ");
    if (company.findUser(userID) != null) {
      company.findUser(userID).print(company.getFlatRate());
    } else Tools.printFailure("There is no user with the given ID!");
  }

  static public void filterByCarModel(InsuranceCompany company) {
    int userID = Tools.getInputInt("Enter a user ID: ");
    String model = Tools.getInputString("Enter a car model");
    if (company.filterByCarModel(userID, model).size() > 0) {
      InsurancePolicy.printPolicies(company.filterByCarModel(userID, model));
    } else Tools.printFailure("User or matching policy didn't found!");
  }

  static public void filterByExpiryDate(InsuranceCompany company) {
    int userID = Tools.getInputInt("Enter a user ID: ");
    System.out.println("Enter an expiry date");
    MyDate date = getNewDate();
    if (company.filterByExpiryDate(userID, date).size() > 0) {
      InsurancePolicy.printPolicies(company.filterByExpiryDate(userID, date));
    } else Tools.printFailure("User or matching policy didn't found!");
  }

  static public void updateAddress(InsuranceCompany company) {
    int userID = Tools.getInputInt("Enter a user ID: ");
    if (company.findUser(userID) != null) {
      Address newAddress = getNewAddress();
      company.findUser(userID).setAddress(newAddress);
      Tools.printSuccess("Address updated successfully! => " + newAddress);
    } else Tools.printFailure("There is no user with the given ID!");
  }

  static public void addUser(String name, String password, int userID, Address address, InsuranceCompany company) {
    if (company.addUser(name, password, userID, address)) { 
        Tools.printSuccess("New User " + name + " added successfully.");
    } else Tools.printFailure("Failed to add the user " + name + ".");
  }

  static public void addPolicy(int userID, InsurancePolicy policy, InsuranceCompany company) {
    if (company.addPolicy(userID, policy)) {
        Tools.printSuccess("The policy has been added to user's list.");
    } else Tools.printFailure("Failed to add the policy.");
  }

  static public void createThirdPartyPolicy(int userID, InsuranceCompany company, String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, String comments) throws PolicyException, PolicyHolderNameException {
    if (company.createThirdPartyPolicy(userID, policyHolderName, id, car, numberOfClaims, expiryDate, comments)) {
        Tools.printSuccess("Third party policy added successfully.");
    } else Tools.printFailure("Failed to add the Third party policy.");
  }

  static public void createComprehensivePolicy(int userID, InsuranceCompany company, String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, int driverAge, int level) throws PolicyException, PolicyHolderNameException {
    if (company.createComprehensivePolicy(userID, policyHolderName, id, car, numberOfClaims, expiryDate, driverAge, level)) {
        Tools.printSuccess("Comprehensive policy added successfully.");
    } else Tools.printFailure("Failed to add the comprehensive policy.");
  }

  static public void changeAddress(int userID, InsuranceCompany company,String username, String password) {
    if (!company.findUser(userID).isValid(username, password)) {
      Tools.printFailure("Failed authorization!");
    } else company.findUser(userID).setAddress(UITools.getNewAddress()); 
  } 

  static public Address getNewAddress() {
    String city = Tools.getInputString("Enter user's city: ");
    String suburb = Tools.getInputString("Enter user's suburb: ");
    String street = Tools.getInputString("Enter user's street: ");
    int streetNumber = Tools.getInputInt("Enter user's street number: ");
    return new Address(streetNumber, street, suburb, city);
  }

  static public Car getNewCar() {
    String model = Tools.getInputString("Enter car model: ");
    int manufactoringYear = Tools.getInputInt("Enter car's manufactoring year: ");
    int price = Tools.getInputInt("Enter car's price: ");
    return new Car(model, null, manufactoringYear, price);
  }

  static public MyDate getNewDate() {
    int year = 0;
    int day = 0;
    int month = 0;
    while (year < 2000) year = Tools.getInputInt("Enter a valid year: ");      
    while (day < 1  || day > 31) day = Tools.getInputInt("Enter a valid day: ");
    while (month < 1 || month > 12) month = Tools.getInputInt("Enter a valid month: ");      
    return new MyDate(year, month, day);
  }

  public static void printPolicies(int userID, InsuranceCompany company, String username, String passwrord) {
    if (company.findUser(userID).isValid(username, passwrord)) {
      company.printPolicies(userID); 
    } else Tools.printFailure("Failed authorization");
  }

  public static void reportUserPremiumPerCarModel(InsuranceCompany company) {
    User user = findUser(company);
    if (user == null) return;
    HashMap<String, Integer> totalCount = company.findUser(user.getUserID()).getTotalCountPerCarModel(); 
    HashMap<String, Double> totalPremium = company.findUser(user.getUserID()).getTotalPremiumPerCarModel(); 
    User.reportPremiumPerCarModel(totalPremium, totalCount);
  }

  public static void reportPremiumPerCity(InsuranceCompany company) {
    company.reportPremiumPerCity();
  }

  public static void reportAllPremiumPerCarModel(InsuranceCompany compay) {
    HashMap<String, Integer> totalCount = compay.getTotalCountPerCarModel(); 
    HashMap<String, Double> totalPremium = compay.getTotalPremiumPerCarModel(); 
    User.reportPremiumPerCarModel(totalPremium, totalCount);
  }

  public static void savePolicesBinary(InsuranceCompany company) {
    User targetUser = findUser(company);
    if (targetUser == null) return;
    InsurancePolicy.save(targetUser.getPolicies(), "policies.ser");
  }

  public static void savePolicesText(InsuranceCompany company) throws IOException {
    User targetUser = findUser(company);
    if (targetUser == null) return;
    InsurancePolicy.saveTextFile(targetUser.getPolicies(), "policies.txt");
  }

  public static void saveUsersBinary(InsuranceCompany company) {
    User.save(company.getUsers(), "users.ser");
  }

  public static void saveUsersText(InsuranceCompany company) throws IOException {
    User.saveTextFile(company.getUsers(), "users.txt");
  }

  public static void saveCompanyBinary(InsuranceCompany company) {
    company.save("company.ser");
  }

  public static void saveCompanyText(InsuranceCompany company) throws IOException {
    company.saveTextFile("company.txt");    
  }
}
