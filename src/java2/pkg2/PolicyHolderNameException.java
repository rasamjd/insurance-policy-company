package java2.pkg2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolicyHolderNameException extends Exception {
  
  String holderName;
  
  public PolicyHolderNameException(String name) {
    this.holderName = name;
  }

  static public boolean validateName(String holderName) {
    Pattern p = Pattern.compile("[A-Z][a-z]{2,} [A-Z][a-z]{2,}$");
    Matcher m = p.matcher(holderName);
    return m.matches();
  }

  public String toString() {
    return "the policy holder name: " + holderName  + " doesnt't satisfy the expected conditions";
  }
}