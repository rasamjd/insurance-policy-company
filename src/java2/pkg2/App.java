package java2.pkg2;

public class App {
    public static void main(String[] args) throws Exception {

        InsuranceCompany insuranceCompany = new InsuranceCompany("ABC Insurance", "admin1", "pass1", 50);
        UserInterface UI = new UserInterface(insuranceCompany);
        UI.runLoginUI();
        // UI.mainMenu();
        // TestCase.testCode();

        // Node.testNode();
        // Node2.testNode2();
    }
}