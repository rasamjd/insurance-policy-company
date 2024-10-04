package java2.pkg2;

import java.util.Random;

public class PolicyException extends Exception {
    
    private int id;
    private int oldID;
    
    public PolicyException(int id) {
        this.id = generateID();
        oldID = id;
    }

    public int getNewID() {
        return id;
    }
    
    public int generateID() {
        Random r = new Random();
        int newID = 300000 + r.nextInt(100000);
        return newID;
    }

    public String toString() {
        return "the policy ID: " + oldID + " doesnt't satisfy the expected conditions and a new ID: " + id 
            + " is generated";
    }
}