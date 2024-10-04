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
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class InsurancePolicy implements Cloneable, Serializable, Comparable<InsurancePolicy> {

  protected String policyHolderName;
  protected int id; 
  protected Car car;
  protected int numberOfClaims;
  protected MyDate expiryDate;

  public InsurancePolicy(String policyHolderName, int id, Car car, MyDate date, int numberOfClaims) throws PolicyException, PolicyHolderNameException {
    if (!PolicyHolderNameException.validateName(policyHolderName)) throw new PolicyHolderNameException(policyHolderName);
    this.policyHolderName = policyHolderName;
    if (id < 300000 || id > 399999) throw new PolicyException(id);
    this.id = id;
    this.car = car;
    this.expiryDate = date;
    this.numberOfClaims = numberOfClaims;
  }  
  public InsurancePolicy(InsurancePolicy policy) {
    this.policyHolderName = policy.policyHolderName;
    this.id = policy.id;
    this.car = new Car(car);
    this.numberOfClaims = policy.numberOfClaims;
    this.expiryDate = new MyDate(expiryDate);
  }

  public String getPolicyHolderName() {
    return policyHolderName;
  }
  public int getId() {
    return id;
  }
  public Car getCar() {
    return car;
  }
  public int getNumberOfClaims() {
    return numberOfClaims;
  }
  public MyDate getExpiryDate() {
    return expiryDate;
  }

  public void setPolicyHolderName(String policyHolderName) {
    this.policyHolderName = policyHolderName;
  }
  public void setCarModel(String model) {
    car.setModel(model);
  }
  public void setNumberOfClaims(int numberOfClaims) {
    this.numberOfClaims = numberOfClaims;
  }

  public void print() {
    System.out.print("=> Policy holder name: " + policyHolderName 
      + ", ID: " + id + ", Car: " + car + ", Expiry date: " + expiryDate + ", Number of claims: " + numberOfClaims);
  }

  public String toString() {
    return "Policy holder name: " + policyHolderName 
      + ", ID: " + id + ", Car: " + car + ", Expiry date: " + expiryDate + ", Number of claims: " + numberOfClaims;
  }

  public String toDelimitedString() {
    return policyHolderName + "," + id + "," + car.toDelimitedString()
      + "," + expiryDate.toDelimitedString() + "," + numberOfClaims ;
  }

  @Override
  public InsurancePolicy clone() throws CloneNotSupportedException {
    InsurancePolicy output = (InsurancePolicy) super.clone();
    output.car = car.clone();
    output.expiryDate = expiryDate.clone();
    return output;
  }

  @Override
  public int compareTo(InsurancePolicy other) {
    return expiryDate.compareTo(other.expiryDate);
  }

  public abstract double calcPayment(double flatRate);

  public static String policiesList(ArrayList<InsurancePolicy> policies) {
    String list = "";
    for (InsurancePolicy policy: policies) {
      list += Tools.subInfoText("\n=> ") + policy + "\n";
    }
    return list;
  }

  public static void printPolicies(ArrayList<InsurancePolicy> policies) {
    // System.out.println(policiesList(policies));
    policies.forEach(policy -> policy.print());
  }

  public static String policiesList(HashMap<Integer, InsurancePolicy> policies) {
    String list = "";
    for (InsurancePolicy policy: policies.values()) {
      list += Tools.subInfoText(policy.getId() +" => ") + policy + "\n" + "payment: " + policy.calcPayment(50) + "\n";
    }
    return list;
  }

  public static void printPolicies(HashMap<Integer, InsurancePolicy> policies) {
    System.out.println(policiesList(policies));
  }
  
  public void carPriceRise(double risePercent) {
    car.setPrice(car.getPrice() * (risePercent + 1));  
  }
  
  public static void carPriceRiseAll(HashMap<Integer, InsurancePolicy> policies, double risePercent) {
    // for (InsurancePolicy policy: policies.values()) {
    //   policy.carPriceRise(risePercent);
    // }
    policies.values().forEach(policy -> policy.carPriceRise(risePercent));
  }

  public static void carPriceRiseAll(ArrayList<InsurancePolicy> policies, double risePercent) {
    for (InsurancePolicy policy: policies) {
      policy.carPriceRise(risePercent);
    }
  }

  public static double calcTotalPayments(HashMap<Integer, InsurancePolicy> policies, int flatRate) {
    double total = 0;
    for (InsurancePolicy policy: policies.values()) {
      total += policy.calcPayment(20);
    }
    return total;
  }

  public static double calcTotalPayments(ArrayList<InsurancePolicy> policies, int flatRate) {
    // double total = 0;
    // for (InsurancePolicy policy: policies) {
    //   total += policy.calcPayment(20);
    // }
    // return total;
    return policies.stream()
      .map(policy -> policy.calcPayment(flatRate))
      .reduce(0.0, (policyX, policyY) -> policyX + policyY);
  }

  public static ArrayList<InsurancePolicy> filterByCarType(ArrayList<InsurancePolicy> policies, CarType carType) {
    ArrayList<InsurancePolicy> filteredPolicies = new ArrayList<InsurancePolicy>();
    for (InsurancePolicy policy: policies) {
      if (policy.car.getType() == carType) {
        filteredPolicies.add(policy);
      }
    }
    return filteredPolicies;
  }

  public static ArrayList<InsurancePolicy> filterByCarModel(ArrayList<InsurancePolicy> policies, String carModel) {
    // ArrayList<InsurancePolicy> filteredPolicies = new ArrayList<InsurancePolicy>();
    // for (InsurancePolicy policy: policies) {
    //   if (policy.car.getModel().toUpperCase().contains(carModel.toUpperCase())) {
    //     filteredPolicies.add(policy);
    //   }
    // }
    // return filteredPolicies;
    Predicate<InsurancePolicy> con = policy -> policy.car.getModel().toUpperCase().contains(carModel.toUpperCase());
    return (ArrayList<InsurancePolicy>) policies.stream()
      .filter(con)
      .collect(Collectors.toList());
  }

  public static ArrayList<InsurancePolicy> filterByExpiryDate(ArrayList<InsurancePolicy> policies, MyDate date) {
    // ArrayList<InsurancePolicy> expiredPolicies = new ArrayList<InsurancePolicy>();
    // for (InsurancePolicy policy: policies) {
    //   if (policy.expiryDate.isExpired(date)) {
    //     expiredPolicies.add(policy);
    //   }
    // }
    // return expiredPolicies;
    return (ArrayList<InsurancePolicy>) policies.stream()
      .filter(policy -> policy.getExpiryDate().isExpired(date))
      .collect(Collectors.toList());
  }

  public static ArrayList<InsurancePolicy> filterByCarType(HashMap<Integer, InsurancePolicy> policies, CarType carType) {
    ArrayList<InsurancePolicy> filteredPolicies = new ArrayList<InsurancePolicy>();
    for (InsurancePolicy policy: policies.values()) {
      if (policy.car.getType() == carType) {
        filteredPolicies.add(policy);
      }
    }
    return filteredPolicies;
  }

  public static HashMap<Integer, InsurancePolicy> filterByCarModel(HashMap<Integer, InsurancePolicy> policies, String carModel) {
    HashMap<Integer, InsurancePolicy> filteredPolicies = new HashMap<Integer, InsurancePolicy>();
    for (Integer id: policies.keySet()) {
      if (policies.get(id).car.getModel().toUpperCase().contains(carModel.toUpperCase())) {
        filteredPolicies.put(id, policies.get(id));
      }
    }
    return filteredPolicies;
  }

  public static HashMap<Integer, InsurancePolicy> filterByExpiryDate(HashMap<Integer, InsurancePolicy> policies, MyDate date) {
    HashMap<Integer, InsurancePolicy> expiredPolicies = new HashMap<Integer, InsurancePolicy>();
    for (Integer id: policies.keySet()) {
      if (policies.get(id).expiryDate.isExpired(date)) {
        expiredPolicies.put(id, policies.get(id));
      }
    }
    return expiredPolicies;
  }

  public static ArrayList<InsurancePolicy> deepCopy(ArrayList<InsurancePolicy> policies) throws CloneNotSupportedException {
    // ArrayList<InsurancePolicy> deepCopy = new ArrayList<InsurancePolicy>();
    // for (InsurancePolicy policy: policies) {
    //   deepCopy.add(policy.clone());
    // }
    // return deepCopy;
    return (ArrayList<InsurancePolicy>) policies.stream()
      .map(policy -> {
        try {
          return policy.clone();
        } catch (CloneNotSupportedException e) {
          throw new RuntimeException(e);
        }
      })
      .collect(Collectors.toList());
  }

  public static ArrayList<InsurancePolicy> shallowCopy(ArrayList<InsurancePolicy> policies) {
    // ArrayList<InsurancePolicy> shallowCopy = new ArrayList<InsurancePolicy>();
    // for (InsurancePolicy policy: policies) {
    //   shallowCopy.add(policy);
    // }
    // return shallowCopy;
    return policies.stream()
      .collect(Collectors.toCollection(ArrayList :: new));
  } 

  public static ArrayList<InsurancePolicy> deepCopy(HashMap<Integer, InsurancePolicy> policies) throws CloneNotSupportedException {
    ArrayList<InsurancePolicy> deepCopy = new ArrayList<InsurancePolicy>();
    for (InsurancePolicy policy: policies.values()) {
      deepCopy.add(policy.clone());
    }
    return deepCopy;
  }

  public static ArrayList<InsurancePolicy> shallowCopy(HashMap<Integer, InsurancePolicy> policies) {
    ArrayList<InsurancePolicy> shallowCopy = new ArrayList<InsurancePolicy>();
    for (InsurancePolicy policy: policies.values()) {
      shallowCopy.add(policy);
    }
    return shallowCopy;
  } 
  
  public static HashMap<Integer, InsurancePolicy> deepCopyHashMap(HashMap<Integer, InsurancePolicy> policies) throws CloneNotSupportedException {
    HashMap<Integer, InsurancePolicy> deepCopy = new HashMap<Integer, InsurancePolicy>();
    for (Integer id: policies.keySet()) {
      deepCopy.put(id, policies.get(id).clone());
    }
    return deepCopy;
  }

  public static HashMap<Integer, InsurancePolicy> shallowCopyHashMap(HashMap<Integer, InsurancePolicy> policies) {
    HashMap<Integer, InsurancePolicy> shallowCopy = new HashMap<Integer, InsurancePolicy>();
    for (Integer id: policies.keySet()) {
      shallowCopy.put(id, policies.get(id));
    }
    return shallowCopy;
  } 

  public static HashMap<Integer, InsurancePolicy> load(String fileName) {
    ObjectInputStream inputst = null;    
    HashMap<Integer, InsurancePolicy> policies = new HashMap<Integer, InsurancePolicy>();
    try {
      inputst = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));           
    } catch(IOException ex) {
      System.err.println("error in create/open the file ");
      System.exit(1);
    }
    try {
      while(true) {
        InsurancePolicy policy = (InsurancePolicy) inputst.readObject();
        policies.put(policy.getId(), policy);
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
    return policies;
  }
	
  public static Boolean save(HashMap<Integer, InsurancePolicy> policies, String fileName) {
    ObjectOutputStream outputst = null;    
    try {
      outputst = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));           
    } catch(IOException ex) {
      System.err.println("error in create/open the file ");
      System.exit(1);
      return false;
    }
    try {
      for (InsurancePolicy policy: policies.values()) {
        outputst.writeObject(policy);
      }
    } catch(IOException ex) {
      System.err.println("error in adding the objects to the file ");
      System.exit(1);
      return false;
    } 
    try {
      if (outputst !=null)
      outputst.close();           
    } catch (IOException ex) {
      System.err.println("error in closing the file ");
      System.exit(1);
      return false;
    }   
    return true;
  }
 
  public static HashMap<Integer, InsurancePolicy> loadTextFile (String fileName) throws IOException, PolicyException, PolicyHolderNameException {
    HashMap<Integer, InsurancePolicy> policies = new HashMap<Integer, InsurancePolicy>();
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line = in.readLine();
    while (line != null) {
      line = line.trim();
      String[] field = line.split(",");
      switch (field[0]) {
        case "cp":
          String policyHolderName = field[1];
          int id = Integer.parseInt(field[2]);
          String model = field[3];
          CarType type = CarType.valueOf(field[4]);
          int manufacturingYear = Integer.parseInt(field[5]);
          double price = Double.parseDouble(field[6]);
          int numberOfClaims = Integer.parseInt(field[7]);
          int year = Integer.parseInt(field[8]);
          int month = Integer.parseInt(field[9]);
          int day = Integer.parseInt(field[10]);
          int driverAge = Integer.parseInt(field[11]);
          int level = Integer.parseInt(field[12]);

          Car car = new Car(model, type, manufacturingYear, price);
          MyDate expiryDate = new MyDate(year, month, day);
          ComprehensivePolicy cPolicy = ComprehensivePolicy.createComprehensivePolicy(policyHolderName, id, car, expiryDate, numberOfClaims, driverAge, level);
          policies.put(id, cPolicy);
          break;
        case "tp":
          policyHolderName = field[1];
          id = Integer.parseInt(field[2]);
          model = field[3];
          type = CarType.valueOf(field[4]);
          manufacturingYear = Integer.parseInt(field[5]);
          price = Double.parseDouble(field[6]);
          numberOfClaims = Integer.parseInt(field[7]);
          year = Integer.parseInt(field[8]);
          month = Integer.parseInt(field[9]);
          day = Integer.parseInt(field[10]);
          String comments = field[11];
          
          car = new Car(model, type, manufacturingYear, price);
          expiryDate = new MyDate(year, month, day);
          ThirdPartyPolicy tPolicy = ThirdPartyPolicy.createThirdPartyPolicy(policyHolderName, id, car, expiryDate, numberOfClaims, comments);
          policies.put(id, tPolicy);
          break;
        }
      line = in.readLine();
    }
    in.close();
    return policies;
  }

  public static Boolean saveTextFile(HashMap<Integer, InsurancePolicy> policies, String fileName) throws IOException {
    try {
      BufferedWriter out = new BufferedWriter (new FileWriter(fileName));
      for (InsurancePolicy policy: policies.values()) {
      out.write (policy.toDelimitedString() + "\n");
      }
      out.close ();
      return true;      
    } catch (IOException e) {
      System.out.println(e);
      return false;
    }
  }
}