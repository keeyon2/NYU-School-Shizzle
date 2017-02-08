import java.util.Vector;

/*
 * The Contact class stores all the meta data of a contact.
 */
public class Contact {
  private Name name;
  private Vector<Phone> phones;
  private Address address;
  private String email;
  private String note;
  
  /*
   * Constructor
   */
  public Contact() {
    name = new Name();
    address = new Address();
    phones = new Vector<Phone>();
    email = "";
    note = "";
  }
  
  /*
   * Constructor with initial parameters.
   */
  public Contact(Name theName, Address theAddress, Vector<Phone> thePhone, String theEmail, 
      String theNote) {
    name = theName;
    address = theAddress;
    phones = thePhone;
    email = theEmail;
    note = theNote;
  }
  
  /*
   * Gets the full name of the contact.
   */
  public String getFullName() {    
    return name.getFullName();
  }
  
  /*
   * Gets all the phone numbers of the contact.
   */
  public Vector<Phone> getAllPhone() {
    return phones;
  }
  
  /*
   * Gets the name of the contact in Name class.
   */
  public Name getName() {
    return name;
  }
  
  /*
   * Gets the address of the contact.
   */
  public Address getAddress() {
    return address;
  }
  
  /*
   * Gets the email of the contact.
   */
  public String getEmail() {
    return email;
  }
  
  /*
   * Gets the note string of the contact.
   */
  public String getNote() {
    return note;
  }
  
}
