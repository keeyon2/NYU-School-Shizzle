import java.util.Vector;

/*
 * The AddressBookView Class serve for print all address book operation infomation to the console.
 */
public class AddressBookView {
  
  /*
   * Print the contact infomation of the addressbook.
   * 
   * @param addressBook  The addressbook to be printed.
   */
  public void printAddressBook(AddressBookModule addressBook) {
    Vector<Contact> contacts = addressBook.getContacts();
    System.out.println(" -------------------------------------------");
    System.out.println("|                                           |");
    System.out.println("|               Address Book                |");
    System.out.println("|                                           |");
    System.out.println(" -------------------------------------------");
    printContacts(contacts);
  }
  
  /*
   * Prints the search result to the console.
   * 
   * @param contacts  The contacts from the search result.
   * @param keyword   The keyword used in the search. 
   */
  public void printSearchResult(Vector<Contact> contacts, String keyword) {
    System.out.println(" +++++++++++++++++++++++++++++++++++++++++++");
    System.out.println("|                                           |");
    System.out.println("  Search Result of key word: " + keyword);
    System.out.println("|                                           |");
    System.out.println(" +++++++++++++++++++++++++++++++++++++++++++");
    printContacts(contacts);
  }
  
  /*
   * Print a list of contacts' infomation.
   * 
   * @param contacts Contacts to be printed.  
   */
  public void printContacts(Vector<Contact> contacts) {
    if (contacts.size() == 0) {
      System.out.println("|                                           |");
      System.out.println("|            There is no contacts           |");
      System.out.println("|                                           |");
      System.out.println(" -------------------------------------------");
    }
    
    for (int i = 0; i < contacts.size(); i++) {
      System.out.println("               Contact No." + i);
      System.out.println(" -------------------------------------------");
      Contact contact = contacts.get(i);
      printName(contact.getName());
      printPhones(contact.getAllPhone());
      System.out.println("Email: " + contact.getEmail());
      System.out.println("Note: " + contact.getNote());
      printAddress(contact.getAddress());
      System.out.println(" -------------------------------------------");
    }
    
    System.out.println("\n\n");
  }
  
  /*
   * Print address information in good format.
   * 
   * @param address The address to be printed.
   */
  public void printAddress(Address address) {
    System.out.println("Address: \n" + address.getStreet() + "\n" + address.getCity() + ", " +
        address.getState() + "\n" + address.getCountry() + ", " + address.getZipcode());  
  }
  
  /*
   * Print full name.
   * 
   * @param name  Name to be printed.
   */
  public void printName(Name name) {
    System.out.println(name.getFullName());
  }
  
  /*
   * Print a contact's all phone numbers.
   * 
   * @param phones  A vector of phones to be printed.
   */
  public void printPhones(Vector<Phone> phones) {
    for (int i = 0; i < phones.size(); i++) {
      Phone phone = phones.get(i);
      System.out.println("Phone(" + phone.getLabelStr() + "): " + phone.getNumber());  
    }
  }
  
}
