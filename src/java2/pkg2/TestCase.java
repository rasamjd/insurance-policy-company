package java2.pkg2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TestCase {


  public static void testCode() throws CloneNotSupportedException, PolicyException, IOException, PolicyHolderNameException {
    
    // Test basics: Creating few objects of MyDate, Car, Address, InsurancePolicy and User
    MyDate date1 = new MyDate(2022, 1, 15);
    MyDate date2 = new MyDate(2022, 3, 8);
    MyDate date3 = new MyDate(2022, 5, 20);
    MyDate date4 = new MyDate(2022, 7, 3);
    MyDate date5 = new MyDate(2022, 9, 12);
    MyDate date6 = new MyDate(2022, 11, 25);
    MyDate date7 = new MyDate(2022, 12, 31);
    MyDate date8 = new MyDate(2023, 2, 10);
    
    Car car1 = new Car("Toyota Camry", CarType.SEDAN, 2020, 25000.0);
    Car car2 = new Car("Honda Civic", CarType.SEDAN, 2018, 20000.0);
    Car car3 = new Car("Ford F-150", CarType.TRUCK, 2019, 35000.0);
    Car car4 = new Car("Chevrolet Cruze", CarType.SEDAN, 2017, 18000.0);
    Car car5 = new Car("Jeep Wrangler", CarType.SUV, 2021, 30000.0);
    Car car6 = new Car("BMW X5", CarType.SUV, 2019, 45000.0);
    Car car7 = new Car("Tesla Model S", CarType.ELECTRIC, 2022, 60000.0);
    Car car8 = new Car("Volkswagen Golf", CarType.HATCHBACK, 2020, 22000.0);
    Car car9 = new Car("Audi A4", CarType.SEDAN, 2016, 23000.0);
    Car car10 = new Car("Mercedes-Benz GLE", CarType.SUV, 2018, 50000.0);
    
    Address address1 = new Address(123, "Main Street", "Downtown", "Cityville");
    Address address2 = new Address(456, "Oak Avenue", "Greenwood", "Townsville");
    Address address3 = new Address(789, "Maple Drive", "Hillview", "Villageville");
    Address address4 = new Address(321, "Pine Road", "Riverdale", "Hamletsville");
    Address address5 = new Address(654, "Cedar Lane", "Sunnyvale", "Beachtown");
    Address address6 = new Address(987, "Elm Street", "Lakeview", "Mountainville");
    Address address7 = new Address(135, "Birch Circle", "Riverside", "Valleytown");
    Address address8 = new Address(246, "Walnut Avenue", "Meadowview", "Harborville");
   
    ThirdPartyPolicy policy1 = new ThirdPartyPolicy("Alice Smith", 300001, car1, date1, 2, "Good driver");
    ThirdPartyPolicy policy2 = new ThirdPartyPolicy("Bob Johnson", 300002, car2, date2, 1, "Minor accident last year");
    ThirdPartyPolicy policy3 = new ThirdPartyPolicy("Eve Brown", 300003, car3, date3, 0, "No claims history");
    ThirdPartyPolicy policy4 = new ThirdPartyPolicy("Dan Wilson", 300004, car4, date4, 3, "Multiple claims in past");
    ComprehensivePolicy policy5 = new ComprehensivePolicy("Alice Smith", 300005, car5, date5, 2, 35, 1);
    ComprehensivePolicy policy6 = new ComprehensivePolicy("Bob Johnson", 300006, car6, date6, 1, 40, 2);
    ComprehensivePolicy policy7 = new ComprehensivePolicy("Eve Brown", 300007, car7, date7, 1, 30, 3);
    ComprehensivePolicy policy8 = new ComprehensivePolicy("Dan Wilson", 300008, car5, date8, 6, 45, 5);

    User user1 = new User("Bob", "bobpass", address1); 
    User user2 = new User("Roohollah", "roohollahpass", address2); 
    User user3 = new User("Chen", "chenpass", address3); 
    User user4 = new User("Sara", "sarapass", address4); 

    user1.addPolicy(policy6);    
    user1.addPolicy(policy5);    
    user1.addPolicy(policy1);    
    user2.addPolicy(policy2);    
    user2.addPolicy(policy3);    
    user3.addPolicy(policy4);    

    InsuranceCompany company = new InsuranceCompany("ABC Insurance", "admin1", "pas1", 50);
    company.addUser(user1);
    company.addUser(user2);
    company.addUser(user3);

    // company.print();
    
    //////////////////////////////////////////////////////////////////////////// Test Assigment 2:
    
    // InsuranceCompany deepCopyCompany = company.clone();

    // ArrayList<InsurancePolicy> deepCopyPolicies = user1.deepCopyPolicies();
    // ArrayList<InsurancePolicy> shallowCopyPolicies = user1.shallowCopyPolicies();
    // ArrayList<InsurancePolicy> sortedPolicies = user1.sortPoliciesByDate();

    // ArrayList<User> deepCopyUsers = company.deepCopyUsers();
    // ArrayList<User> shallowCopyUsers = company.shallowCopyUsers();
    // ArrayList<User> sortedUsers = company.sortUsers();
    // ArrayList<User> sortedUsersByComparator = company.sortUsersByPremium();

    // // making changes to test deep and shallow copies
    // user1.getAddress().setCity("New York");
    // user1.sortPoliciesByDate();
    // user1.getPolicies().get(300006).setCarModel("An Unknown Car");
    // user1.getPolicies().get(300001).setPolicyHolderName("New Guy");
    // company.setName("New Name");

    // Tools.printInfoBold("User's original list of policies");
    // InsurancePolicy.printPolicies(user1.getPolicies());
    
    // Tools.printInfoBold("Shallow copy of policies");
    // InsurancePolicy.printPolicies(shallowCopyPolicies);
    
    // Tools.printInfoBold("Deep copy of policies");
    // InsurancePolicy.printPolicies(deepCopyPolicies);
    
    // Tools.printInfoBold("Sorted list of policies");
    // InsurancePolicy.printPolicies(sortedPolicies);
    
    // Tools.printInfoBold("Original list of users");
    // company.printUsers(company.getUsers());

    // Tools.printInfoBold("Shallow copy of users");
    // company.printUsers(shallowCopyUsers);
    
    // Tools.printInfoBold("Deep copy of users");
    // company.printUsers(deepCopyUsers);
    
    // Tools.printInfoBold("Sorted list of users (using CompareTo | based on address)");
    // company.printUsers(sortedUsers);
    
    // Tools.printInfoBold("Sorted list of users (using Comparator | based on premium payment)");
    // company.printUsers(sortedUsersByComparator);

    // Tools.printInfoBold("Original Company");
    // company.print();
    // Tools.printInfoBold("Cloned Company");
    // deepCopyCompany.print();

    // UITools.addPolicy(1, policy8, company);
   
    // Tools.printInfoBold("user 1");
    // user1.print(company.getFlatRate());

    // Tools.printInfoBold("report premium per car maodel | user 1");

    // HashMap<String, Integer> counts = user1.getTotalCountPerCarModel();
    // HashMap<String, Double> premiums = user1.getTotalPremiumPerCarModel();
    // User.reportPremiumPerCarModel(premiums, counts);

    // Tools.printInfoBold("report all premiums per car maodel | company");

    // HashMap<String, Integer> countsAll = company.getTotalCountPerCarModel();
    // HashMap<String, Double> premiumsAll = company.getTotalPremiumPerCarModel();
    // company.reportPremiumPerCarModel(premiumsAll, countsAll);

    // Tools.printInfoBold("report premiums per city | company");

    // company.reportPremiumPerCity();

    // HashMap<Integer, User> usersMy = new HashMap<Integer, User>(); 
    // HashMap<Integer, InsurancePolicy> policiesMy = new HashMap<Integer, InsurancePolicy>(); 

    // Tools.printInfoBold("Company");
    // company.print();

    // ComprehensivePolicy newPolicy1 = ComprehensivePolicy.createComprehensivePolicy("Alen Warner", 10, car4, date8, 3, 20, 4);
    // ComprehensivePolicy newPolicy2 = ComprehensivePolicy.createComprehensivePolicy("Sina Amiri", 300011, car9, date5, 2, 30, 3);
    // ThirdPartyPolicy newPolicy3 = ThirdPartyPolicy.createThirdPartyPolicy("Schwarz", 12, car10, date4, 3, "Hi!");

    
    // Tools.printInfoBold("Testing File Saving");      

    // Tools.printInfoBold("Binary Files | Policies");      
    // Tools.printInfoBold("user 1 policies");      
    // HashMap<Integer,InsurancePolicy> policies = user1.getPolicies();
    // InsurancePolicy.printPolicies(policies);

    // policies.put(12, ThirdPartyPolicy.createThirdPartyPolicy("Farbod Dara", 6, car10, date8, 3, "Not much!"));
    // Tools.printInfoBold("saving the policies list...");      
    // InsurancePolicy.save(policies,"policies.ser");
    // policies.clear();

    // Tools.printInfoBold("loading the policies list...");      
    // policies = InsurancePolicy.load("policies.ser");
    // InsurancePolicy.printPolicies(policies);

    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = 
    // Tools.printInfoBold("Binary Files | Users");      
    // Tools.printInfoBold("company users");      
    // HashMap<Integer,User> users = company.getUsers();
    // User.printUsers(users);

    // user3.addPolicy(policy3);
    // users.put(user3.getUserID(), user3);
    // Tools.printInfoBold("saving the users list...");      
    // User.save(users,"users.ser");
    // users.clear();

    // Tools.printInfoBold("loading the users list...");      
    // users = User.load("users.ser");
    // User.printUsers(users);

    // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = 
    // Tools.printInfoBold("Binary Files | Company");      
    // Tools.printInfoBold("company");      
    // InsuranceCompany company2 = company;
    // company2.print();

    // company2.addUser(user1);
    // company2.addUser(user2);
    // Tools.printInfoBold("saving the company...");      
    // company2.save("company.ser");
    // InsuranceCompany company3 = new InsuranceCompany();

    // Tools.printInfoBold("loading the company...");      
    // company3.load("company.ser");
    // System.out.println(company3);

    // Tools.printInfoBold("Text Files");      

    // // // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = 
  //   Tools.printInfoBold("Text Files | Policies");      
  //   Tools.printInfoBold("user 1 policies");      
  //   HashMap<Integer,InsurancePolicy> policies = new HashMap<Integer,InsurancePolicy>();
  //   InsurancePolicy.printPolicies(policies);

  //   policies.put(15, policy2);
  //   policies.put(10, policy3);
  //   policies.put(13, policy4);
  //   policies.put(12, policy5);
  //   InsurancePolicy.printPolicies(policies);
  //   Tools.printInfoBold("saving policies list...");      
  //   InsurancePolicy.saveTextFile(policies,"policies.txt");
  //   policies.clear();

  //   Tools.printInfoBold("loading policies list...");      
  //   policies = InsurancePolicy.loadTextFile("policies.txt");
  //   InsurancePolicy.printPolicies(policies);

  //   // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = 
  //   Tools.printInfoBold("Text Files | users");      
  //   HashMap<Integer, User> users = new HashMap<Integer, User>();
  //   User.printUsers(users);

  //   users.put(122, user1);
  //   users.put(132, user2);
  //   users.put(142, user3);
  //   Tools.printInfoBold("saving policies list...");      
  //   User.saveTextFile(users, "users.txt");
  //   users.clear();

  //   Tools.printInfoBold("loading policies list...");      
  //   users = User.loadTextFile("users.txt");
  //   User.printUsers(users);

  //   // // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = 
  //   Tools.printInfoBold("Text Files | company");      
  //   InsuranceCompany company2 = new InsuranceCompany(); // using default constructor
  //   company2.loadTextFile("company.txt"); // the whole company including all users and all policies are loaded
  //   System.out.println(company2);

  //   company.addUser(user1);
  //   company.addUser(user2);
  //   // company.addPolicy(12, policy6);
  //   Tools.printInfoBold("saving policies list...");      
  //   company.saveTextFile("company.txt");
  //   InsuranceCompany insuranceCompany2 = new InsuranceCompany();

  //   System.out.println("We passed saving!");
  //   Tools.printInfoBold("loading policies list...");      
  //   insuranceCompany2.loadTextFile("company.txt");
  //   insuranceCompany2.print();
  
  //////////////////////////////////////////////////////////////////////////// Test Lab 9 | Stream and Lambda:

  ArrayList<InsurancePolicy> policies = company.allPolicies();

    Tools.printTestExpectation("Search for the name containing 'Ali' and display the infos");
    policies.stream()
      .filter(policy -> policy.policyHolderName.equalsIgnoreCase("Ali"))
      .forEach(System.out::println);

    Tools.printTestExpectation("Search for the name containing 'Ali' and display total premiums");
    policies.stream()
      .forEach(policy -> System.out.println(policy.getId() + ": " + policy.calcPayment(company.getFlatRate())));

    Tools.printTestExpectation("Find the first policy with the premium between $200 to $500 and display the name, ID and premium");
    System.out.printf("%s %n", 
      policies.stream()
        .filter(policy -> 200 < policy.calcPayment(company.getFlatRate()) && policy.calcPayment(company.getFlatRate()) < 500)
        .findFirst()
        .map(policy -> String.format("%s: %s, %s", policy.getId(), policy.getPolicyHolderName(), policy.calcPayment(company.getFlatRate())))
        .get()
    ); 

    Tools.printTestExpectation("Find all policies with the premium between $200 to $500, sort them by ID and display the name, ID and premium for each policy");
    policies.stream()
      .filter(policy -> 200 < policy.calcPayment(company.getFlatRate()) && policy.calcPayment(company.getFlatRate()) < 500)
      .sorted(Comparator.comparing(InsurancePolicy::getId))
      .map(policy -> String.format("%s: %s, %s", policy.getId(), policy.getPolicyHolderName(), policy.calcPayment(company.getFlatRate())))
      .forEach(System.out::println);

    Tools.printTestExpectation("Calculate the total premium for all policies with the premium between $200 to $500");
    policies.stream()
      .filter(policy -> 200 < policy.calcPayment(company.getFlatRate()) && policy.calcPayment(company.getFlatRate()) < 500)
      .forEach(policy -> System.out.println(policy.getId() + ": " + policy.calcPayment(company.getFlatRate())));

    Predicate<InsurancePolicy> c1 = x -> x.getPolicyHolderName().equals("Ali");
    ArrayList<InsurancePolicy> policies1 = filterPolicies(policies, c1);
    InsurancePolicy.printPolicies(policies1);
    
    InsurancePolicy.printPolicies (filterPolicies(policies, x -> x.getExpiryDate().getYear() == 2020));
    InsurancePolicy.printPolicies (filterPolicies(policies, x -> x.getCar().getModel().contains("bmw")));      
  
    Predicate<InsurancePolicy> predicateByCarType = policy -> policy.getCar().getModel() == CarType.LUX.toString();  
    Comparator<InsurancePolicy> comparatorByCarPrice = Comparator.comparing(policy -> policy.getCar().getModel());

    Tools.printTestExpectation("Call your filterPolicy method to filter a list of policies with Car Type equal to LUX." 
      + "Then sort the filtered list based on car price and finally print the sorted filtered list");
    ArrayList<InsurancePolicy> policies2 = filterPolicies(policies, predicateByCarType);
    policies2.stream()
      .filter(predicateByCarType)
      .sorted(comparatorByCarPrice)
      .forEach(System.out::println);

    Tools.printTestExpectation("Aggregate (by using group by) the list of policies based on expiryDate year and print the report");
    InsurancePolicy.filterByExpiryDate(policies2, date8).stream()
      .forEach(System.out::println);
  }

  public static ArrayList<InsurancePolicy> filterPolicies(ArrayList<InsurancePolicy> policies, Predicate<InsurancePolicy> criteria) {
    return (ArrayList<InsurancePolicy>) policies.stream()
      .filter(criteria)
      .collect(Collectors.toList());
  }
  
}
