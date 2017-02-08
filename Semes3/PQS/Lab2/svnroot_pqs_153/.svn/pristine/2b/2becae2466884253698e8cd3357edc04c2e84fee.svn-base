import java.util.Vector;

/*
 * AddressBookController is the controller class that handles all operation on address book.
 * @auther Jiajie Tang
 */
public class AddressBookController {
  
  /**
   * Control users' operations in address book.
   * 
   * @param args  default parameter of main function
   */
  public static void main(String[] args) {
    System.out.println("Welcome to Fiona's address book!\n");
    
    // Create an empty address Book and print it to console.
    AddressBookModule myAddressBook = new AddressBookModule();
    AddressBookView myAddressBookView = new AddressBookView();
    myAddressBookView.printAddressBook(myAddressBook);
    
    // Create a new contact Jessica.Lim.White
    Name name = new Name("Jessica", "Lim", "White");
    Address address = new Address("89 Spring Street", "New York", "NY", "US", "10231");
    Phone phone1 = new Phone("547-253-1028", Label.WORK);
    Phone phone2 = new Phone("347-752-2098");
    Phone phone3 = new Phone("787-723-1898", Label.OTHER);
    Vector<Phone> phones = new Vector<Phone>();
    phones.addElement(phone1);
    phones.addElement(phone2);
    phones.addElement(phone3);
    
    Contact jessica = new Contact(name, address, phones, "jesscia82@gmail.com", 
        "Smart, lovely, hardworking");
    
    // Add the new contact to our address book.
    myAddressBook.addContact(jessica);
    
    // Display address book's current content.
    myAddressBookView.printAddressBook(myAddressBook);
    
    // Delete Jessica from my address book and display address book's current content.
    myAddressBook.deleteContact(jessica);
    myAddressBookView.printAddressBook(myAddressBook);
    
    // Add Jessica, Candy and Bob to the address book.
    name = new Name("Candy", "Kim", "White");
    address = new Address("722 Hack Street", "New York", "NY", "US", "10831");
    phone1 = new Phone("347-753-1098");
    phone2 = new Phone("387-053-1998", Label.WORK);
    phones = new Vector<Phone>();
    phones.addElement(phone1);
    phones.addElement(phone2);
    
    Contact candy = new Contact(name, address, phones, "candy@gmail.com", "Sweet, funny, pizza");
    
    name = new Name("Bob", "Li");
    address = new Address("2 Water Street", "Sunnyvale", "CA", "US", "31298");
    phone1 = new Phone("237-293-0078");
    phone2 = new Phone("147-753-1898", Label.WORK);
    phones = new Vector<Phone>();
    phones.addElement(phone1);
    phones.addElement(phone2);
    
    Contact bob = new Contact(name, address, phones, "bob@gmail.com", "Love Swimming!");
    
    myAddressBook.addContact(candy);
    myAddressBook.addContact(bob);
    myAddressBook.addContact(jessica);
    myAddressBookView.printAddressBook(myAddressBook);
    
    // Search my address book by key word Kim.
    String input = "Kim";
    Vector<Contact> searchResult = myAddressBook.searchContact(input);
    myAddressBookView.printSearchResult(searchResult, "Kim");
    
    // Search my address book by key word New York.
    input = "New York";
    searchResult = myAddressBook.searchContact(input);
    myAddressBookView.printSearchResult(searchResult, input);
    
    // Search my address book by key word 347.
    input = "347";
    searchResult = myAddressBook.searchContact(input);
    myAddressBookView.printSearchResult(searchResult, input);
    
    // Search my address book by key word 347-753.
    input = "347-753";
    searchResult = myAddressBook.searchContact(input);
    myAddressBookView.printSearchResult(searchResult, input);
    
    // Search my address book by key word swim.
    input = "swim";
    searchResult = myAddressBook.searchContact(input);
    myAddressBookView.printSearchResult(searchResult, input);
    
    // Search my address book by key word candy@gmail.com.
    input = "candy@gmail.com";
    searchResult = myAddressBook.searchContact(input);
    myAddressBookView.printSearchResult(searchResult, input);
    
    // Load new contacts data from a specific path.
    String pathToLoad = "fileToLoad.xml";
    myAddressBook.loadFromFile("fileToLoad.xml");
    myAddressBookView.printAddressBook(myAddressBook);
    System.out.println("Address Info loaded from " + pathToLoad + "!");
    
    // Save address book data to a specific path.
    String pathForSave = "fileForSaving.xml";
    myAddressBook.saveToFile(pathForSave);
    System.out.println("AddressBook saved to " + pathForSave + "!");
  }
  
}
