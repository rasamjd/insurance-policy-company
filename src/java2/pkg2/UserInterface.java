package java2.pkg2;

import java.io.IOException;

public class UserInterface {
  
  private InsuranceCompany insuranceCompany;

  public UserInterface(InsuranceCompany company) {
    this.insuranceCompany = company;
  }
  
  public void runLoginUI() throws PolicyException, PolicyHolderNameException {
      fillData();
      LoginUI login = new LoginUI(insuranceCompany);
      login.run();
  }    

  public void displayMainMenu() {
    Tools.clearConsole();
    System.out.println("1- Admin Login");
    System.out.println("2- User Login");
    System.out.println("3- Exit the Program");
  }

  public void displayAdminMenu() {
    Tools.clearConsole();
    System.out.println("1- Test Code");
    System.out.println("2- Create User");
    System.out.println("3- Create Third Party Policy");
    System.out.println("4- Create Comprehensive Policy");
    System.out.println("5- Print User Information");
    System.out.println("6- Filter by Car Model");
    System.out.println("7- Filter by Expiry Date");
    System.out.println("8- Update Address");
    System.out.println("9- Report User Payment per Car Model");
    System.out.println("10- Report Payments per City");
    System.out.println("11- Report Payments by Car Model");
    System.out.println("12- Save Policies in Binary File");
    System.out.println("13- Save Policies in Text File");
    System.out.println("14- Save Users in Binary File");
    System.out.println("15- Save Users in text File");
    System.out.println("16- Save Company in Binary File");
    System.out.println("17- Save Company in Text File");
    System.out.println("18- Logout");
  }

  public void displayUserMenu() {
    Tools.clearConsole();
    System.out.println("1- Print Policies");
    System.out.println("2- Add Policy");
    System.out.println("3- Change Address");
    System.out.println("4- Logout");
  }

  public void mainMenu() throws CloneNotSupportedException, PolicyException, IOException, PolicyHolderNameException {
    try {
      fillData();
    } catch (PolicyException e) {
      System.out.println(e);
    }
    int option = 0;
    while (option != 3) {
      displayMainMenu();
      option = Tools.getInputInt("Please choose an option from 1 to 3");
      switch (option) {
        case 1:
          Tools.printTitle("Admin Login");
          loginAdmin();
          break;
        case 2:
          Tools.printTitle("User Login");
          loginUser();
          break;
        case 3:
          Tools.printTitle("Exit the Program");            
          break;
        default:
          System.out.println("\nWrong option!\n");   
      }
    }
  }

  public void adminMenu() throws CloneNotSupportedException, PolicyException, IOException, PolicyHolderNameException {
    int option = 0;
    while (option != 18) {  
      displayAdminMenu();
      option = Tools.getInputInt("Please choose an option from 1 to 12");
      switch (option) {
        case 1:               
          Tools.printTitle("Test Code");  
          try {        
            TestCase.testCode();
          } catch (PolicyException e) {
            System.out.println(e);
          }
          Tools.backToMenu();
          break;
        case 2:
          Tools.printTitle("Create User");  
          UITools.createUser(insuranceCompany);
          Tools.backToMenu();
          break;
        case 3:
          Tools.printTitle("Create Third Party Policy");  
          UITools.createThirdPartyPolicy(-1, insuranceCompany);
          Tools.backToMenu();
          break;
        case 4:
          Tools.printTitle("Create Comprehensive Policy");  
          UITools.createComprehensivePolicy(-1, insuranceCompany);
          Tools.backToMenu();
          break;
        case 5:
          Tools.printTitle("Print User Information");  
          UITools.printUserInformation(insuranceCompany);
          Tools.backToMenu();
          break;
        case 6:
          Tools.printTitle("Filter by Car Model");  
          UITools.filterByCarModel(insuranceCompany);
          Tools.backToMenu();
          break;
        case 7:
          Tools.printTitle("Filter by Expiry Date");  
          UITools.filterByExpiryDate(insuranceCompany);
          Tools.backToMenu();
          break;
        case 8:
          Tools.printTitle("Update Address");  
          UITools.updateAddress(insuranceCompany);
          Tools.backToMenu();
          break;
        case 9:
          Tools.printTitle("Report User Payment per Car Model");  
          UITools.reportUserPremiumPerCarModel(insuranceCompany);
          Tools.backToMenu();
          break;
        case 10:
          Tools.printTitle("Report Payments per City");  
          UITools.reportPremiumPerCity(insuranceCompany);
          Tools.backToMenu();
          break;
        case 11:
          Tools.printTitle("Report Payments per Car Model");  
          UITools.reportAllPremiumPerCarModel(insuranceCompany);
          Tools.backToMenu();
          break;
        case 12:
          Tools.printTitle("Save Policies in Binary File");  
          UITools.savePolicesBinary(insuranceCompany);
          Tools.backToMenu();
          break;
        case 13:
          Tools.printTitle("Save Policies in Text File");  
          UITools.savePolicesText(insuranceCompany);
          Tools.backToMenu();
          break;
        case 14:
          Tools.printTitle("Save users in Binary File");  
          UITools.saveUsersBinary(insuranceCompany);
          Tools.backToMenu();
          break;
        case 15:
          Tools.printTitle("Save Policies in Text File");  
          UITools.saveUsersText(insuranceCompany);
          Tools.backToMenu();
          break;
        case 16:
          Tools.printTitle("Save Company in Binary File");  
          UITools.saveCompanyBinary(insuranceCompany);
          Tools.backToMenu();
          break;
        case 17:
          Tools.printTitle("Save Policies in Text File");  
          UITools.saveCompanyText(insuranceCompany);
          Tools.backToMenu();
          break;
        case 18:
          Tools.printTitle("Logout");  
          Tools.backToMenu();
          break;
        default:
          System.out.println("\nWrong option!\n");   
      }
    }
  }

  public void userMenu(int userID, String username, String password) throws PolicyException, PolicyHolderNameException {
    int option = 0;
    while (option != 5) {  
      displayUserMenu();
      option = Tools.getInputInt("Please choose an option from 1 to 5");
      switch (option) {
        case 1:
          Tools.printTitle("Print Policies");  
          UITools.printPolicies(userID, insuranceCompany, username, password);
          Tools.backToMenu();
          break;
        case 2:
          Tools.printTitle("Add Comprehensive Policy");  
          UITools.createComprehensivePolicy(userID, insuranceCompany, username, password);
          Tools.backToMenu();
          break;
        case 3:
          Tools.printTitle("Add Third Party Policy");  
          UITools.createThirdPartyPolicy(userID, insuranceCompany, username, password);
          Tools.backToMenu();
          break;
        case 4:
          Tools.printTitle("Change Address");  
          UITools.changeAddress(userID, insuranceCompany, username, password);
          Tools.backToMenu();
          break;
        case 5:
          Tools.printTitle("Logout");  
          Tools.backToMenu();
          break;
        default:
          System.out.println("\nWrong option!\n");   
      }
    }
  }

  public void validateAdmin(String username, String password) {
    System.out.println(username + " => " + password);
    if (insuranceCompany.validateAdmin(username, password)) {
        Tools.printSuccess("Welcome " + username + "!");
    } else Tools.printFailure("Invalid credentials");
  } 

  public void loginAdmin() throws CloneNotSupportedException, PolicyException, IOException, PolicyHolderNameException {
    String username = "";
    String password = "";
    while (!username.equals("-1") || !password.equals("-1")) {
      username = Tools.getInputString("Enter username (or '-1' to go back): ");
      password = Tools.getInputString("Enter password (or '-1' to go back): ");
      if (insuranceCompany.validateAdmin(username, password)) {
        adminMenu();
        return;
      } 
    }
  }

  public void validateUser(String username, String password) {
    System.out.println(username + " => " + password);
    if (insuranceCompany.validateUser(username, password) != null) {
        Tools.printSuccess("Welcome " + username + "!");
    } else Tools.printFailure("Invalid credentials");
  }

  public void loginUser() throws PolicyException, PolicyHolderNameException {
    String username = "";
    String password = "";
    while (!username.equals("-1") || !password.equals("-1")) {
      username = Tools.getInputString("Enter username (or '-1' to go back): ");
      password = Tools.getInputString("Enter password (or '-1' to go back): ");
      User foundUser = insuranceCompany.validateUser(username, password);
      if (foundUser != null) {
        userMenu(foundUser.getUserID(), username, password);
        return;
      } 
    }
  }  

  public void fillData() throws PolicyException, PolicyHolderNameException {
    Car car1 = new Car("BMW m4", CarType.LUX, 2020, 60000.00);
    Car car2 = new Car("Mercedes-Benz C-Class", CarType.LUX, 2021, 65000.00);
    Car car3 = new Car("Tesla Model S", CarType.ELECTRIC, 2022, 80000.00);
    Car car4 = new Car("Toyota Camry", CarType.SEDAN, 2021, 35000.00);
    Car car5 = new Car("Ford F-150", CarType.TRUCK, 2021, 45000.00);
    MyDate date1 = new MyDate(2020, 1, 1);
    MyDate date2 = new MyDate(2023, 8, 2);
    MyDate date3 = new MyDate(2025, 11, 5);
    MyDate date4 = new MyDate(2026, 3, 15);
    Address address1 = new Address(123, "Main Street", "Downtown", "New York");
    Address address2 = new Address(456, "Oak Avenue", "Westside", "Los Angeles");
    Address address3 = new Address(789, "Elm Road", "North End", "Chicago");
    Address address4 = new Address(1011, "Maple Lane", "East Village", "San Francisco");
    User user1 = new User("Ali", "alipass", address1);
    User user2 = new User("John", "johnpass", address2);
    User user3 = new User("Roohollah", "Roohollahpass", address3);
    User user4 = new User("Alex", "alexpass", address4);
    ThirdPartyPolicy policy1 = ThirdPartyPolicy.createThirdPartyPolicy("Ali Molavi", 300001, car1, date1, 2, "No commments!");
    ThirdPartyPolicy policy2 = ThirdPartyPolicy.createThirdPartyPolicy("John Jackson", 300002, car2, date2, 3, "Bye!");
    ComprehensivePolicy policy3 = ComprehensivePolicy.createComprehensivePolicy("John Roberts", 300003, car4, date3, 1, 20, 3);
    ComprehensivePolicy policy4 = ComprehensivePolicy.createComprehensivePolicy("Roohollah Kabiri", 300006, car5, date3, 1, 25, 2);

    user1.addPolicy(policy1);
    user1.addPolicy(policy2);
    user1.addPolicy(policy4);
    user2.addPolicy(policy3);
    // user3.createComprehensivePolicy(user3.getName(), 4, car5, 2, date3, 25, 3);
  
    this.insuranceCompany.addUser(user1);
    this.insuranceCompany.addUser(user2);
    this.insuranceCompany.addUser(user3);
  }

 
  }