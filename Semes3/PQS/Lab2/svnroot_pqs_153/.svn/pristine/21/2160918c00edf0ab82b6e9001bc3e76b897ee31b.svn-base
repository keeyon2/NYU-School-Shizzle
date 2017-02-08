
/*
 * The Name class stores first name, middle name and last name.
 */
public class Name {
  private String firstName;
  private String middleName;
  private String lastName;
  
  /*
   * Constructor.
   */
  public Name() {
    firstName = "";
    middleName = "";
    lastName = "";
  }
  
  /*
   * Constructor with initial parameters' value. Complete Name with middle name.
   */
  public Name(String first, String middle, String last) {
    firstName = first;
    middleName = middle;
    lastName = last;
  }
  
  /*
   * Constructor with initial parameters' value, without middle name.
   */
  public Name(String first, String last) {
    firstName = first;
    middleName = "";
    lastName = last;
  }
  
  /*
   * Gets the full name.
   * 
   * @return  Returns the full name in a string.
   */
  public String getFullName() {
    if (middleName == "") {
      return firstName + "." + lastName;
    }
    
    return firstName + "." + middleName + "." + lastName;
  }

  private void setFirstName(String name) {
    firstName = name;
  }
  
  private void setMiddleName(String name) {
    middleName = name;
  }
  
  private void setLastName(String name) {
    lastName = name;
  }
  
  /*
   * Gets the first name.
   */
  public String getFirstName() {
    return firstName;
  }
  
  /*
   * Gets the middle name.
   */
  public String getMiddleName() {
    return middleName;
  }
  
  /*
   * Gets the last name.
   */
  public String getLastName() {
    return lastName;
  }
  
}
