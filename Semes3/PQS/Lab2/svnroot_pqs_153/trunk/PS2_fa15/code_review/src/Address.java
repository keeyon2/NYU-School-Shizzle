/*
 * Address is the class that store all post address information like City, State and so on.
 */
public class Address {
  private String street;
  private String city;
  private String state;
  private String country;
  private String zipcode;
  
  /*
   * Constructor with complete parameters.
   */
  public Address(String theStreet, String theCity, String theState, String theCountry, 
      String theZipcode) {
    street = theStreet;
    city = theCity;
    state = theState;
    country = theCountry;
    zipcode = theZipcode;
  }
  
  /*
   * Default Constructor.
   */
  public Address() {
    street = "";
    city = "";
    state = "";
    country = "";
    zipcode = "";
  }
  
  /*
   * Gets the string of full address. 
   */
  public String getFullAddress() {
    return street + city + state + country + zipcode;
  }
  
  /*
   * Gets the Street string.
   */
  public String getStreet() {
    return street;
  }

  /*
   * Gets the City string.
   */
  public String getCity() {
    return city;
  }

  /*
   * Gets the State string.
   */
  public String getState() {
    return state;
  }
  
  /*
   * Gets the Country string.
   */  
  public String getCountry() {
    return country;
  }

  /*
   * Gets the Zipcode string.
   */
  public String getZipcode() {
    return zipcode;
  }
}
