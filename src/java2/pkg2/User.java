package java2.pkg2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

public class User implements Cloneable, Serializable, Comparable<User> {

  private String name;
  private int userID;
  private String password;
  private Address address;
  // private ArrayList<InsurancePolicy> policies;
  private HashMap<Integer, InsurancePolicy> policies;

  private static int nextID = 1;

  public User(String name, String password, Address address) {
    this.name = name;
    this.userID = nextID;
    this.password = password;
    this.address = address;
    // this.policies = new ArrayList<InsurancePolicy>();
    this.policies = new HashMap<Integer, InsurancePolicy>();
 
    nextID++;
  }
  public User(String name, int id, String password, Address address) {
    this.name = name;
    this.userID = id;
    this.password = password;
    this.address = address;
    // this.policies = new ArrayList<InsurancePolicy>();
    this.policies = new HashMap<Integer, InsurancePolicy>();
  }
  public User(User user) {
    this.name = user.name;
    this.userID = user.userID;
    this.password = user.password;
    this.address = new Address(address);
    this.policies = new HashMap<Integer, InsurancePolicy>();
    for (Integer id: policies.keySet()) {
      if (policies.get(id) instanceof ComprehensivePolicy) {
        this.policies.put(id, new ComprehensivePolicy((ComprehensivePolicy) policies.get(id)));
      } else this.policies.put(id, new ThirdPartyPolicy((ThirdPartyPolicy) policies.get(id)));
    } 
  }
  
  public String getName() {
    return name;
  }
  public String getPassword() {
    return password;
  }
  public int getUserID() {
    return userID;
  }
  public Address getAddress() {
    return address;
  }
  public HashMap<Integer, InsurancePolicy> getPolicies() {
    return policies;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  public void setAddress(Address address) {
    this.address = address;
  }

  public void print(int flatRate) {
    System.out.println(Tools.subInfoText("User: ") + "Name: " + name + ", ID: " + userID + ", Address: "
      + address + ", Policies: " + "\n" + InsurancePolicy.policiesList(policies) 
      + Tools.subInfoText("Total payment: ") + calcTotalPremiums(flatRate) + "\n");
  }

  public String toString() {
    return "Name: " + name + ", ID: " + userID + ", Address: "
    + address + ", Policies: " + "\n" + InsurancePolicy.policiesList(policies);
  }
  
  public String toDelimitedString() {
    String output = name + "," + userID + "," + password + "," + address.toDelimitedString() + "," + policies.size();
    for (InsurancePolicy policy: policies.values()) {
      output += "," + policy.toDelimitedString();
    } 
    return output;
  }
 
  @Override
  public User clone() throws CloneNotSupportedException {
    User output = (User) super.clone();
    output.address = address.clone();
    output.policies = new HashMap<Integer, InsurancePolicy>();
    for (Integer id: policies.keySet()) {
      output.policies.put(id, policies.get(id).clone());
    }
    return output;
  }
  
  public boolean isValid(String username, String password) {
    return (username.equalsIgnoreCase(this.name) && password.equals(this.password)) ? true : false;
  }
  
  public InsurancePolicy findPolicy(int policyID) {
    for (InsurancePolicy policy: policies.values()) {
      if (policy.getId() == policyID) {
        return policy;
      }
    }
    return null;
  }

  public boolean addPolicy(InsurancePolicy policy) {
    if (findPolicy(policy.getId()) == null) {
      policies.put(policy.getId(), policy);
      return true;
    } 
    return false;
  }

  public void printPolicies(int flatRate) {
    // for (InsurancePolicy policy: policies.values()) {
    //   policy.print();
    //   System.out.print("Premium Payment: " + policy.calcPayment(flatRate));
    // }
    policies.values().forEach(policy -> {
      policy.print();
      System.out.print("Premium Payment: " + policy.calcPayment(flatRate));  
    });
  }

  public static String usersList(ArrayList<User> users) {
    String list = "";
    for (User user: users) {
      list += Tools.subInfoText("\n" + user.getUserID() + "=> ") + user + "\n";
    }
    return list;
  }

  public static void printUsers(ArrayList<User> users) {
    System.out.println(usersList(users));
  }

  public static String usersList(HashMap<Integer, User> users) {
    String list = "";
    for (Integer id: users.keySet()) {
      list += Tools.subInfoText("\n" + id + "=> ") + users.get(id) + "\n";
    }
    return list;
  }

  public static void printUsers(HashMap<Integer, User> users) {
    System.out.println(usersList(users));
  }

  public double calcTotalPremiums(int flatRate) {
    double total = 0;
    for (InsurancePolicy policy: policies.values()) {
      total += policy.calcPayment(flatRate);
    }
    return total;
  }

  public void carPriceRiseAll(double risePercent) {
    InsurancePolicy.carPriceRiseAll(policies, risePercent);
  }

  public HashMap<Integer, InsurancePolicy> filterByCarModel(String carModel) {
    return InsurancePolicy.filterByCarModel(policies, carModel);
  }

  public HashMap<Integer, InsurancePolicy> filterByExpiryDate(MyDate date) {
    return InsurancePolicy.filterByExpiryDate(policies, date);
  }

  public boolean createThirdPartyPolicy(String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, String comments) throws PolicyException, PolicyHolderNameException {
    return addPolicy(new ThirdPartyPolicy(policyHolderName, id, car, expiryDate, numberOfClaims, comments));
  }

  public boolean createComprehensivePolicy(String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, int driverAge, int level) throws PolicyException, PolicyHolderNameException {
    return addPolicy(new ComprehensivePolicy(policyHolderName, id, car, expiryDate, numberOfClaims, driverAge, level));
  }

  public int compareTo(User other) {
    return address.compareTo(other.address);
  }

  public static ArrayList<User> deepCopy(ArrayList<User> users) throws CloneNotSupportedException {
    // ArrayList<User> deepCopy = new ArrayList<User>();
    // for (User user: users) {
    //   deepCopy.add(user.clone());
    // }
    // return deepCopy;
    return (ArrayList<User>) users.stream()
      .map(user -> {
        try {
          return user.clone();
        } catch (CloneNotSupportedException e) {
          throw new RuntimeException(e);
        }
      })
      .collect(Collectors.toList());
  }

  public static ArrayList<User> shallowCopy(ArrayList<User> users) {
    // ArrayList<User> shallowCopy = new ArrayList<User>();
    // for (User user: users) {
    //   shallowCopy.add(user);
    // }
    // return shallowCopy;
    return users.stream()
      .collect(Collectors.toCollection(ArrayList :: new));
  } 

  public ArrayList<InsurancePolicy> deepCopyPolicies() throws CloneNotSupportedException {
    return InsurancePolicy.deepCopy(policies);
  }

  public ArrayList<InsurancePolicy> shallowCopyPolicies() {
    return InsurancePolicy.shallowCopy(policies);
  }

  public HashMap<Integer, InsurancePolicy> deepCopyPoliciesHashMap() throws CloneNotSupportedException {
    return InsurancePolicy.deepCopyHashMap(policies);
  }

  public HashMap<Integer, InsurancePolicy> shallowCopyPoliciesHashMap() {
    return InsurancePolicy.shallowCopyHashMap(policies);
  }
  
  public static ArrayList<User> deepCopy(HashMap<Integer, User> users) throws CloneNotSupportedException {
    ArrayList<User> deepCopy = new ArrayList<User>();
    for (User user: users.values()) {
      deepCopy.add(user.clone());
    }
    return deepCopy;
  }

  public static ArrayList<User> shallowCopy(HashMap<Integer, User> users) {
    ArrayList<User> shallowCopy = new ArrayList<User>();
    for (User user: users.values()) {
      shallowCopy.add(user);
    }
    return shallowCopy;
  } 
  
  public static HashMap<Integer, User> deepCopyHashMap(HashMap<Integer, User> users) throws CloneNotSupportedException {
    HashMap<Integer, User> deepCopy = new HashMap<Integer, User>();
    for (Integer id: users.keySet()) {
      deepCopy.put(id, users.get(id).clone());
    }
    return deepCopy;
  }

  public static HashMap<Integer, User> shallowCopyHashMap(HashMap<Integer, User> users) {
    HashMap<Integer, User> shallowCopy = new HashMap<Integer, User>();
    for (Integer id: users.keySet()) {
      shallowCopy.put(id, users.get(id));
    }
    return shallowCopy;
  } 
    
  public ArrayList<InsurancePolicy> sortPoliciesByDate() {
    ArrayList<InsurancePolicy> shallowCopy = InsurancePolicy.shallowCopy(policies);
    Collections.sort(shallowCopy);
    return shallowCopy;
    // ArrayList<InsurancePolicy> shallowCopy = new ArrayList<InsurancePolicy>();
    // for (InsurancePolicy policy: InsurancePolicy.shallowCopyHashMap(policies).values()) {
    //   shallowCopy.add(policy);
    // }
    // Collections.sort(shallowCopy);
    // return shallowCopy;
  }

  public HashMap<String, Integer> getTotalCountPerCarModel() {
    HashMap<String, Integer> counts = new HashMap<>();
    for (InsurancePolicy policy: policies.values()) {
      Integer count = counts.get(policy.getCar().getModel());
      if (count != null) {
        counts.put(policy.getCar().getModel(), count + 1);
      } else {
        counts.put(policy.getCar().getModel(), 1);
      }
    }
    return counts;
  }
  
  public HashMap<String, Double> getTotalPremiumPerCarModel() {
    HashMap<String, Double> premiums = new HashMap<String, Double>();
    for (InsurancePolicy policy: policies.values()) {
      Double premium = premiums.get(policy.getCar().getModel());
      if (premium != null) {
        premiums.put(policy.getCar().getModel(), premium + policy.calcPayment(50));
      } else {
        premiums.put(policy.getCar().getModel(), policy.calcPayment(50));
      }
    }
    return premiums;
  }

  public static void reportPremiumPerCarModel(HashMap<String, Double> premiums, HashMap<String, Integer> counts) {
    System.out.println(Tools.subInfoText("Car Model      Total Premium Payment      Average Premium Payment"));
    for (String carModel: premiums.keySet()) {
      System.out.println(carModel + "\t \t" + premiums.get(carModel) + "\t \t"
        + (premiums.get(carModel) / counts.get(carModel)));  
    }
  }

  public static HashMap<Integer, User> load(String fileName) {
    ObjectInputStream inputst = null;    
    HashMap<Integer, User> users = new HashMap<Integer, User>();
    try {
      inputst = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));           
    } catch(IOException ex) {
      System.err.println("error in create/open the file ");
      System.exit(1);
    }
    try {
      while(true) {
        User user = (User) inputst.readObject();
        users.put(user.getUserID(), user);
      }
    } catch(EOFException ex) {
      System.out.println("no more record!");
    } catch (ClassNotFoundException ex) {
      System.err.println("error in wrong class in the file ");
    } catch(IOException ex) {
      System.err.println("error in add object to the file ");
      System.exit(1);
    }
    try {
      if(inputst !=null)
      inputst.close();           
    } catch(IOException ex) {
      System.err.println("error in close the file ");
      System.exit(1);
    } 
    return users;
  }
	
  public static Boolean save(HashMap<Integer, User> users, String fileName) {
    ObjectOutputStream outputst = null;    
    try {
      outputst = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));           
    } catch(IOException ex) {
      System.err.println("error in create/open the file ");
      System.exit(1);
      return false;
    }
    try {
      for (User user: users.values()) {
        outputst.writeObject(user);
      }
    } catch(IOException ex) {
      System.err.println("error in adding the objects to the file ");
      System.out.println(ex);
      System.exit(1);
      return false;
    } 
    try {
      if(outputst !=null)
      outputst.close();           
    } catch(IOException ex) {
      System.err.println("error in closing the file ");
      System.exit(1);
      return false;
    }   
    return true;
  }

  public static HashMap<Integer, User> loadTextFile (String fileName) throws IOException, PolicyException, PolicyHolderNameException {
    HashMap<Integer, User> users = new HashMap<Integer, User>();
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line = in.readLine();
    while (line != null) {
      line = line.trim();
      String[] field = line.split(",");
      String name = field[0];
      int id = Integer.parseInt(field[1]);
      String password = field[2];
      int streetNum = Integer.parseInt(field[3]);
      String street = field[4];
      String suburb = field[5];
      String city = field[6];
      int numOfPolicies = Integer.parseInt(field[7]);

      Address address = new Address(streetNum, street, suburb, city);
      User user = new User(name, id, password, address); 

      int nextField = 8;
      for (int i = 0; i < numOfPolicies; i++) {
        switch (field[nextField]) {
        case "cp":
          String policyHolderName = field[nextField + 1];
          int policyID = Integer.parseInt(field[nextField + 2]);
          String model = field[nextField + 3];
          CarType type = CarType.valueOf(field[nextField + 4]);
          int manufacturingYear = Integer.parseInt(field[nextField + 5]);
          double price = Double.parseDouble(field[nextField + 6]);
          int numberOfClaims = Integer.parseInt(field[nextField + 7]);
          int year = Integer.parseInt(field[nextField + 8]);
          int month = Integer.parseInt(field[nextField + 9]);
          int day = Integer.parseInt(field[nextField + 10]);
          int driverAge = Integer.parseInt(field[nextField + 11]);
          int level = Integer.parseInt(field[nextField + 12]);
          nextField += 13;

          Car car = new Car(model, type, manufacturingYear, price);
          MyDate expiryDate = new MyDate(year, month, day);
          ComprehensivePolicy cPolicy = ComprehensivePolicy.createComprehensivePolicy(policyHolderName, policyID, car, expiryDate, numberOfClaims, driverAge, level);
          user.addPolicy(cPolicy);
          break;
        case "tp":
          policyHolderName = field[nextField + 1];
          policyID = Integer.parseInt(field[nextField + 2]);
          model = field[nextField + 3];
          type = CarType.valueOf(field[nextField + 4]);
          manufacturingYear = Integer.parseInt(field[nextField + 5]);
          price = Double.parseDouble(field[nextField + 6]);
          numberOfClaims = Integer.parseInt(field[nextField + 7]);
          year = Integer.parseInt(field[nextField + 8]);
          month = Integer.parseInt(field[nextField + 9]);
          day = Integer.parseInt(field[nextField + 10]);
          String comments = field[nextField + 11];
          nextField += 12;

          car = new Car(model, type, manufacturingYear, price);
          expiryDate = new MyDate(year, month, day);
          ThirdPartyPolicy tPolicy = ThirdPartyPolicy.createThirdPartyPolicy(policyHolderName, policyID, car, expiryDate, numberOfClaims, comments);
          user.addPolicy(tPolicy);
          break;
        }
      }
      users.put(user.getUserID(), user);
      line = in.readLine();
    }
    in.close();
    return users;
  }

  public static Boolean saveTextFile(HashMap<Integer, User> users, String fileName) throws IOException {
    try {
      BufferedWriter out = new BufferedWriter (new FileWriter(fileName));
      for (User user: users.values()) {
        out.write(user.toDelimitedString() + "\n");
      }
      out.close ();
      return true;      
    } catch (IOException e) {
      System.out.println(e);
      return false;
    }
  }
}