import java.util.Vector;

/*
 * AddressBookModule is the class that handler all data manipulation of address book, for
 * example, adding new contact, deleting contacts, search contacts by keyword and so on.
 */
public class AddressBookModule {
  private Vector<Contact> contacts;
  
  /*
   * Constructor
   */
  public AddressBookModule() {
    contacts = new Vector<Contact>();
  }
  
  /*
   * Gets all contacts from the address book
   * 
   * @return  A vector that contains all contacts in the address book.
   */
  public Vector<Contact> getContacts() {
    return contacts;
  }
  
  /*
   * Load contacts' data from a specific path.
   * 
   * @param path  The path to load data.
   */
  public void loadFromFile(String path) {
    AddressBookXMLFile XMLFile = new AddressBookXMLFile(path);
    Vector<Contact> contactsInFile = XMLFile.read();
    contacts.addAll(contactsInFile);
  }
  
  /*
   * Save all current contact data to file.
   * 
   * @param path  The destination path to save the file. 
   */
  public void saveToFile(String path) {
    AddressBookXMLFile XMLFile = new AddressBookXMLFile(path);
    XMLFile.write(contacts);
  }
  
  /*
   * Add one contact to address book.
   * 
   * @param contact  A contact instance.
   */
  public void addContact(Contact contact) {
    String fullName = contact.getFullName();
    boolean isInserted = false;
    
    // Find insert position by the lexicographic order of contact's full name.
    for (int i = 0; i < contacts.size(); i++) {
      String curName = contacts.get(i).getFullName();
      
      if (fullName.compareTo(curName) < 0 ) {
        contacts.insertElementAt(contact, i);
        isInserted = true;
        break;
      }
    }
    
    if (isInserted == false) {
      contacts.addElement(contact);
    }
  }
  
  /*
   * Delete one contact from address book.
   * 
   * @param contact  The contact to be deleted.
   */
  public void deleteContact(Contact contact) {    
    for (int i = 0; i < contacts.size(); i++) {
      if (contacts.get(i) == contact) {
        contacts.removeElementAt(i);
        
        // Break to guarantee that only delete one contact at a time.
        break;
      }
    }
  }
  
  /*
   * Search for contact by keyword inputed by users.
   * 
   * @param input  Keyword for search.
   * @return       A vector of contacts that meet the keyword requirement.
   */
  public Vector<Contact> searchContact(String input) {
    Vector<Contact> result = new Vector<Contact>();
    input = input.toLowerCase();
    
    result.addAll(getContactByPhone(input));
    result.addAll(getContactByName(input));
    result.addAll(getContactByAddress(input));
    result.addAll(getContactByEmail(input));
    result.addAll(getContactByNote(input));
    
    return result;
  }
  
  /*
   * Get contacts by phone number. Find all contacts whose phone number contains the input string.
   * 
   * @param input  Keyword to be searched.
   * @return       A vector of contacts whose phone number contains the keyword.
   */
  private Vector<Contact> getContactByPhone(String input) {
    Vector<Contact> result = new Vector<Contact>();
    
    for (int i = 0; i < contacts.size(); i++) {
      Contact contact = contacts.get(i);
      Vector<Phone> phones = contact.getAllPhone();
      
      for (int j = 0; j < phones.size(); j++) {
        if (phones.get(j).getNumber().contains(input)) {
          result.addElement(contact);
          break;
        }
      }
    }
    
    return result;
  }

  /*
   * Find all contacts whose name contains the keyword string.
   * 
   * @param input  Keyword to be searched.
   * @return       A vector of contacts whose name contains the keyword.
   */
  private Vector<Contact> getContactByName(String input) {
    Vector<Contact> result = new Vector<Contact>();
    
    for (int i = 0; i < contacts.size(); i++) {
      Contact contact = contacts.get(i);
      String fullName = contact.getFullName().toLowerCase();
      
      if (fullName.contains(input)) {
        result.addElement(contact);
      }
    }
    
    return result;
  }

  /*
   * Find all contacts whose email contains the inputed string.
   * 
   * @param input  Keyword to be searched.
   * @return       A vector of contacts whose email contains the keyword.
   */  
  private Vector<Contact> getContactByEmail(String input) {
    Vector<Contact> result = new Vector<Contact>();
    
    for (int i = 0; i < contacts.size(); i++) {
      Contact contact = contacts.get(i);
      String email = contact.getEmail().toLowerCase();
      
      if (email == input) {
        result.addElement(contact);
      }
    }
    
    return result;
  }
 
  /*
   * Find all contacts whose address contains the inputed string.
   * 
   * @param input  Keyword to be searched.
   * @return       A vector of contacts whose address contains the keyword.
   */    
  private Vector<Contact> getContactByAddress(String input) {
    Vector<Contact> result = new Vector<Contact>();
    
    for (int i = 0; i < contacts.size(); i++) {
      Contact contact = contacts.get(i);
      String address = contact.getAddress().getFullAddress().toLowerCase();
      
      if (address.contains(input)) {
        result.addElement(contact);
      }
    }
    
    return result;
  }
 
  /*
   * Find all contacts whose note contains the inputed string.
   * 
   * @param input  Keyword to be searched.
   * @return       A vector of contacts whose note contains the keyword.
   */    
  private Vector<Contact> getContactByNote(String input) {
    Vector<Contact> result = new Vector<Contact>();
    
    for (int i = 0; i < contacts.size(); i++) {
      Contact contact = contacts.get(i);
      String note = contact.getNote().toLowerCase();
      
      if (note.contains(input)) {
        result.addElement(contact);
      }
    }
    
    return result;
  }

}
