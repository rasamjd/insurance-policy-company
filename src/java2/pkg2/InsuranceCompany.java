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
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class InsuranceCompany implements Cloneable, Serializable {
  
  private String name;
  // private ArrayList<User> users;
  private HashMap<Integer, User> users;
  private String adminUsername;
  private String adminPassword;
  private int flatRate;

  public InsuranceCompany(String name, String adminUsername, String adminPassword, int flatRate) {
    this.name = name;
    // this.users = new ArrayList<User>();
    this.users = new HashMap<Integer, User>();
    this.adminUsername = adminUsername;
    this.adminPassword = adminPassword;
    this.flatRate = flatRate;
  }  
  public InsuranceCompany(InsuranceCompany company) {
    this.name = company.name;
    this.users = new HashMap<Integer, User>();
    for (Integer id: company.users.keySet()) {
      this.users.put(id, new User(users.get(id)));
    }
    this.adminUsername = company.adminUsername;
    this.adminPassword = company.adminPassword;
    this.flatRate = company.flatRate;
  }
  public InsuranceCompany() {
    this.name = "";
    this.users = new HashMap<Integer, User>();
    this.adminUsername = "";
    this.adminPassword = "";
    this.flatRate = 0;
  }
 
  public HashMap<Integer, User> getUsers() {
    return users;
  }
  public int getFlatRate() {
    return flatRate;
  }
  
  // just for testing deep and shallow copy 
  public void setName(String name) {
    this.name = name;
  }

  public void print() {
    Tools.printInfo("Company: " + name);
    System.out.println(usersList(users));
  }

  public String toString() {
    String txt = "Insurance Company Name : " + this.name + ", Admin UserName : " + adminUsername + 
      ", Flat Rate: " + flatRate + "\n" + usersList(users);
    return txt;
  }
  
  public String toDelimitedString() {
    String output = name + "," + adminUsername + "," + adminPassword + "," + flatRate 
      + "," + users.size() + ",";
    for (User user: users.values()) {
      output += user.toDelimitedString() + ",";
    }
    return output;
  }
  
  @Override
  public InsuranceCompany clone() throws CloneNotSupportedException {
    InsuranceCompany output = (InsuranceCompany) super.clone();
    output.users = new HashMap<Integer, User>();
    for (Integer id: users.keySet()) {
      output.users.put(id, users.get(id).clone());
    }
    return output;
  }

  public boolean validateAdmin(String username, String password) {
    return (adminUsername.equals(username) && adminPassword.equals(password));
  }

  public User validateUser(String username, String password) {
    User validUser = null;
    for (User user: users.values()) {
      if (user.isValid(username, password)) 
        validUser = user;
    }
    return validUser;
  }

  public User findUser(int userID) {
    for (User user: users.values()) {
      if (user.getUserID() == userID) {
        return user;
      }
    }
    return null;
  }

  public boolean addUser(User user) {
    if (findUser(user.getUserID()) == null) {
      users.put(user.getUserID(), user);
      return true;
    } 
    return false;
  }

  public boolean addUser(String name, String password, int userID, Address address) {
    if (findUser(userID) == null) {
      users.put(userID, new User(name, password, address));
      return true;
    } 
    return false;
  }

  public String usersList(ArrayList<User> users) {
    String list = "";
    for (User user: users) {
      list += Tools.subInfoText("\n" + user.getUserID() + "=> ") + user + "\n"
        + "total payment: " + user.calcTotalPremiums(flatRate);
    }
    return list;
  }

  public void printUsers(ArrayList<User> users) {
    System.out.println(usersList(users));
  }

  public String usersList(HashMap<Integer, User> users) {
    String list = "";
    for (User user: users.values()) {
      list += Tools.subInfoText("\n" + user.getUserID() + "=> ") + user
        + "total payment: " + user.calcTotalPremiums(flatRate) + "\n";
    }
    return list;
  }

  public void printUsers(HashMap<Integer, User> users) {
    System.out.println(usersList(users));
  }

  public ArrayList<User> sortUsers() {
    ArrayList<User> list = new ArrayList<User>();
    for (User user: User.shallowCopyHashMap(users).values()) {
      list.add(user);
    }
    Collections.sort(list);
    return list;
  }

  public ArrayList<User> sortUsersByPremium() throws CloneNotSupportedException {
    ArrayList<User> list = shallowCopyUsers();
    Collections.sort(list, new TotalPaymentComparator());
    return list;
  }

  public InsurancePolicy findPolicy (int userID, int policyID) {
    User foundUser = findUser(userID);
    if (foundUser != null) {
      return foundUser.findPolicy(policyID);
    }
    return null;
  }
  
  public boolean addPolicy(int userID, InsurancePolicy policy) {
    User foundUser = findUser(userID);
    if (foundUser != null && foundUser.addPolicy(policy)) {
      return true;
    } 
    return false;
  }

  public void printPolicies(int userID) {
    User foundUser = findUser(userID);
    if (foundUser != null) {
      foundUser.print(flatRate);
    }
  }

  public boolean createThirdPartyPolicy(int userID, String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, String comments) throws PolicyException, PolicyHolderNameException {
    User foundUser = findUser(userID);
    if (foundUser != null && foundUser.createThirdPartyPolicy(policyHolderName, id, car, numberOfClaims, expiryDate, comments)) {
      return true;
    }
    return false;
  }

  public boolean createComprehensivePolicy(int userID, String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, int driverAge, int level) throws PolicyException, PolicyHolderNameException {
    User foundUser = findUser(userID);
    if (foundUser != null && foundUser.createComprehensivePolicy(policyHolderName, id, car, numberOfClaims, expiryDate, driverAge, level)) {
      return true;
    }
    return false;
  }
  
  public double calcTotalPayment(int userID) {
    User foundUser = findUser(userID);
    if (foundUser != null) {
      return foundUser.calcTotalPremiums(flatRate);
    }
    return -1;
  }
  
  public double calcTotalPayments() {
    // double total = 0;
    // for (User user: users.values()) {
    //   total += user.calcTotalPremiums(flatRate);
    // }
    // return total;
    return users.values().stream()
      .mapToDouble(user -> user.calcTotalPremiums(flatRate))
      .sum();
  }

  public boolean carPriceRise(int userID, double risePercent) {
    User foundUser = findUser(userID);
    if (foundUser != null) {
      foundUser.carPriceRiseAll(risePercent);
      return true;
    }
    return false;
  } 

  public void carPriceRise(double risePercent) {
    // for (User user: users.values()) {
    //   user.carPriceRiseAll(risePercent);
    // }
    users.values().forEach(user -> carPriceRise(risePercent));
  }

  public ArrayList<InsurancePolicy> allPolicies() {
    ArrayList<InsurancePolicy> allPolicies = new ArrayList<InsurancePolicy>();
    for (User user: users.values()) {
      for (InsurancePolicy policy: user.getPolicies().values()) {
        allPolicies.add(policy);
      }
    } 
    return allPolicies;
  }

  public ArrayList<InsurancePolicy> filterByCarModelList(String carModel) {
    ArrayList<InsurancePolicy> filteredPolicies = new ArrayList<InsurancePolicy>();
    // for (User user: users.values()) {
    //   for (InsurancePolicy policy: user.filterByCarModel(carModel).values()) {
    //     filteredPolicies.add(policy);
    //   }
    // }
    users.values().stream()
      .forEach(user -> filteredPolicies.addAll(user.filterByCarModel(carModel).values()));
    return filteredPolicies;
  }
  
  public HashMap<Integer, InsurancePolicy> filterByCarModel(int userID, String carModel) {
    HashMap<Integer, InsurancePolicy> filteredPolicies = new HashMap<Integer, InsurancePolicy>();
    User foundUser = findUser(userID);
    if (foundUser != null) {
      filteredPolicies = foundUser.filterByCarModel(carModel);
      // filteredPolicies.addAll(foundUser.filterByCarModel(carModel));
    }     
    return filteredPolicies;  
  }
  
  public HashMap<Integer, InsurancePolicy> filterByExpiryDate(int userID, MyDate date) {
    HashMap<Integer, InsurancePolicy> filteredPolicies = new HashMap<Integer, InsurancePolicy>();
    User foundUser = findUser(userID);
    if (foundUser != null) {
      filteredPolicies = foundUser.filterByExpiryDate(date);
      // filteredPolicies.addAll(foundUser.filterByExpiryDate(date));
    }
    return filteredPolicies;
  }

  public HashMap<Integer, InsurancePolicy> filterByCarModel(String carModel) {
    HashMap<Integer, InsurancePolicy> filteredPolicies = new HashMap<Integer, InsurancePolicy>();
    for (Integer userID: users.keySet()) {
      for (Integer policyID: users.get(userID).filterByCarModel(carModel).keySet()) {
        filteredPolicies.put(policyID, users.get(userID).filterByCarModel(carModel).get(policyID));
      }
    }
    return filteredPolicies;
  }

  public HashMap<Integer, InsurancePolicy> filterByExpiryDate(MyDate date) {
    HashMap<Integer, InsurancePolicy> filteredPolicies = new HashMap<Integer, InsurancePolicy>();
    for (Integer userID: users.keySet()) {
      for (Integer policyID: users.get(userID).filterByExpiryDate(date).keySet()) {
        filteredPolicies.put(policyID, users.get(userID).filterByExpiryDate(date).get(policyID));
      }
    }
    return filteredPolicies;
  }

  public HashMap<User, HashMap<Integer, InsurancePolicy>> filterPoliciesByExpiryDate(MyDate expiryDate) {
    HashMap<User, HashMap<Integer, InsurancePolicy>> expiredPerUser = new HashMap<User, HashMap<Integer, InsurancePolicy>>();
    for (User user: users.values()) {
      expiredPerUser.put(user, user.filterByExpiryDate(expiryDate));
    }
    return expiredPerUser;
  }

  public HashMap<Integer, User> deepCopyUsersHashMap() throws CloneNotSupportedException {
    return User.deepCopyHashMap(users);
  }
  
  public HashMap<Integer, User> shallowCopyUsersHashMap() {
    return User.shallowCopyHashMap(users);
  }

  public ArrayList<User> deepCopyUsers() throws CloneNotSupportedException {
    return User.deepCopy(users);
  } 

  public ArrayList<User> shallowCopyUsers() throws CloneNotSupportedException {
    return User.shallowCopy(users);
  } 

  public HashMap<String, Double> getTotalPremiumPerCity() {
    // HashMap<String, Double> cities = new HashMap<String, Double>();
    // for (User user: users.values()) {
    //   Double premium = cities.get(user.getAddress().getCity());
    //   if (premium != null) {
    //     cities.put(user.getAddress().getCity(), premium + user.calcTotalPremiums(flatRate));
    //   } else {
    //     cities.put(user.getAddress().getCity(), user.calcTotalPremiums(flatRate));
    //   }
    // }
    // return cities;
    return users.values().stream()
      .collect(Collectors.toMap(
        user -> user.getAddress().getCity(),
        user -> user.calcTotalPremiums(flatRate),
        Double::sum,
        HashMap::new
      ));    
  }

  public HashMap<String, Double> getTotalPremiumPerCarModel() {
    HashMap<String, Double> premiums = new HashMap<String, Double>();
    for (User user: users.values()) {
      for (InsurancePolicy policy: user.getPolicies().values()) {
        Double premium = premiums.get(policy.getCar().getModel());
        if (premium != null) {
          premiums.put(policy.getCar().getModel(), premium + policy.calcPayment(50));
        } else {
          premiums.put(policy.getCar().getModel(), policy.calcPayment(50));
        }
      }
    }
    return premiums;
  }

  public HashMap<String, Integer> getTotalCountPerCarModel() {
    HashMap<String, Integer> counts = new HashMap<String, Integer>();
    for (User user: users.values()) {
      for (InsurancePolicy policy: user.getPolicies().values()) {
        Integer count = counts.get(policy.getCar().getModel());
        if (count != null) {
          counts.put(policy.getCar().getModel(), count + 1);
        } else {
          counts.put(policy.getCar().getModel(), 1);
        }
      }
    }
    return counts;
  }

  public void reportPremiumPerCity() {
    System.out.println(Tools.subInfoText("City Name      Total Premium Payment"));
    HashMap<String, Double> cities = getTotalPremiumPerCity();
    for (String city: cities.keySet()) {
      System.out.print(city);
      for (int j = 0; j < 15 - city.length(); j++) {
        System.out.print(" ");
      }
      System.out.println("$" + cities.get(city));
    }
  }

  public void reportPremiumPerCarModel(HashMap<String, Double> premiums, HashMap<String, Integer> counts) {
    System.out.println(Tools.subInfoText("Car Model      Total Premium Payment      Average Premium Payment"));
    for (String carModel: premiums.keySet()) {
      System.out.println(carModel + "\t \t" + premiums.get(carModel) + "\t \t"
        + (premiums.get(carModel) / counts.get(carModel)));  
    }
  }

  public ArrayList<String> populateDistinctCityNames() {
    ArrayList<String> cities = new ArrayList<String>();
    for (User user: users.values()) {
      if (!cities.contains(user.getAddress().getCity())) {
        cities.add(user.getAddress().getCity());
      }
    }
    return cities;
  }

  public Boolean load(String fileName) {
    ObjectInputStream inputst = null;    
    InsuranceCompany company= new InsuranceCompany();
    try {
      inputst = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));           
    } catch(IOException ex) {
      System.err.println("error in create/open the file ");
      System.exit(1);
    }
    try {
      company = (InsuranceCompany) inputst.readObject();
      this.name = company.name;
      this.users = company.users;
      this.adminUsername = company.adminUsername;
      this.adminPassword = company.adminPassword;
      this.flatRate = company.flatRate;
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
      return false;
    } 
    return true;
  }
   
	public Boolean save(String fileName) {
    ObjectOutputStream outputst = null;    
    try {
      outputst = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));           
    } catch(IOException ex) {
      System.err.println("error in create/open the file ");
      System.exit(1);
      return false;
    }
    try {
      outputst.writeObject(this);
    } catch(IOException ex) {
      System.err.println("error in adding the objects to the file ");
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

  public Boolean loadTextFile(String fileName) throws IOException, PolicyException, PolicyHolderNameException {
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String line = in.readLine();
      line = line.trim();
      String[] field = line.split(",");
      String name = field[0];
      String adminUsername = field[1];
      String adminPassword = field[2];
      int flatRate = Integer.parseInt(field[3]);
      int numOfUsers = Integer.parseInt(field[4]);

      this.name = name;
      this.adminUsername = adminUsername;
      this.adminPassword = adminPassword;
      this.flatRate = flatRate;
      
      int nextField = 5;
      for (int i = 0; i < numOfUsers; i++) {
        String username = field[nextField];
        int id = Integer.parseInt(field[nextField + 1]);
        String password = field[nextField + 2];
        int streetNum = Integer.parseInt(field[nextField + 3]);
        String street = field[nextField + 4];
        String suburb = field[nextField + 5];
        String city = field[nextField + 6];
        int numOfPolicies = Integer.parseInt(field[nextField + 7]);
        nextField += 8;

        Address address = new Address(streetNum, street, suburb, city);
        User user = new User(username, id, password, address); 
        
        for (int j = 0; j < numOfPolicies; j++) {
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
        this.addUser(user);
      }
      in.close();
    return true;
  }

  public Boolean saveTextFile(String fileName) throws IOException {
    try {
      BufferedWriter out = new BufferedWriter (new FileWriter(fileName));
      out.write(this.toDelimitedString() + "\n");
      out.close ();
      return true;      
    } catch (IOException e) {
      System.out.println(e);
      return false;
    }
  }

  public class TotalPaymentComparator implements Comparator<User> {
    public int compare(User a, User b) {
      return (int) (a.calcTotalPremiums(flatRate) - b.calcTotalPremiums(flatRate));    
    }
  }

  public HashMap<String, ArrayList<User>> getUsersPerCity() {
    HashMap<String, ArrayList<User>> usersPerCity = new HashMap<String, ArrayList<User>>();
    ArrayList<String> cities = populateDistinctCityNames();
    for (String city: cities) {
      ArrayList<User> usersInCity = new ArrayList<User>();
      for (User user: users.values()) {
        if (user.getAddress().getCity().equals(city)) {
          usersInCity.add(user);
        }
      }
      usersPerCity.put(city, usersInCity);
    }
    return usersPerCity;
  }

  public String usersLoginData() {
    String out = "";
    for (User user: users.values()) {
      out += user.getName() + "  |  " + user.getPassword() + "\n";
    }
    return out;
  }

  // comments

  // public void carPriceRise(double risePercent) {
  //   for (User user: users) {
  //     user.carPriceRiseAll(risePercent);
  //   }
  // }

  // public ArrayList<InsurancePolicy> filterByCarModelList(int userID, String carModel) {
  //   ArrayList<InsurancePolicy> filteredPolicies = new ArrayList<InsurancePolicy>();
  //   User foundUser = findUser(userID);
  //   if (foundUser != null) {
  //     filteredPolicies = (ArrayList<InsurancePolicy>) foundUser.filterByCarModel(carModel).values();
  //   }     
  //   return filteredPolicies;  
  // }
  
  // public ArrayList<InsurancePolicy> filterByExpiryDate(int userID, MyDate date) {
  //   ArrayList<InsurancePolicy> filteredPolicies = new ArrayList<InsurancePolicy>();
  //   User foundUser = findUser(userID);
  //   if (foundUser != null) {
  //     filteredPolicies = foundUser.filterByExpiryDate(date);
  //   }
  //   return filteredPolicies;
  // }

  // public ArrayList<InsurancePolicy> filterByExpiryDate(MyDate date) {
  //   ArrayList<InsurancePolicy> filteredPolicies = new ArrayList<InsurancePolicy>();
  //   for (User user: users) {
  //     for (InsurancePolicy policy: user.filterByExpiryDate(date)) {
  //       filteredPolicies.add(policy);
  //     }
  //   }
  //   return filteredPolicies;
  // }

  // public double getTotalPaymentForCity(String city) {
  //   double total = 0;
  //   for (User user: users) {
  //     if (user.getAddress().getCity(
        
  //     ).equals(city)) {
  //       total += user.calcTotalPremiums(flatRate);
  //     }
  //   }
  //   return total;
  // }

  // public ArrayList<Double> getTotalPaymentPerCity(ArrayList<String> cities) {
  //   ArrayList<Double> totalPayments = new ArrayList<Double>();
  //   for (String city: populateDistinctCityNames()) {
  //     totalPayments.add(getTotalPaymentForCity(city));
  //   } 
  //   return totalPayments;
  // }

  // public void reportPaymentPerCity(ArrayList<String> cities, ArrayList<Double> payments) {
  //   System.out.println(Tools.subInfoText("City Name      Total Premium Payment"));
  //   for (int i = 0; i < cities.size(); i++) {
  //     System.out.print(cities.get(i));
  //     for (int j = 0; j < 15 - cities.get(i).length(); j++) {
  //       System.out.print(" ");
  //     }
  //     System.out.println("$" + payments.get(i));
  //   }
  // }
}