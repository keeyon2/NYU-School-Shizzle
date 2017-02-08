package edu.nyu.cs.pqs.ps1;

import java.util.List;

import edu.nyu.cs.pqs.utils.AddressBookUtils.SearchBy;

/**
 * This is the main class that can be used to test the functionality of the AddressBook class.
 * 
 * @author Suruchi
 *
 */
public class AddressBookTest {
  
  public static void main(String[] args) {
    // Create an empty address book
    AddressBook addressBook = AddressBook.create("TestBook");
    System.out.println(addressBook.getAddressBookName() + " is created!\n");
    
    // Add entry to the AddressBook object
    AddressEntry test1 = new AddressEntry.Builder("TestName")
        .postalAddress("Apt Test, Testing Ave, Test City, 12345").phoneNumber("0123456789")
        .emailAddress("test@testing.com").note("TestNote").build();
    AddressEntry test2 = new AddressEntry.Builder("TestName")
        .postalAddress("Apt Test, Testing Ave, Test City, 12345").phoneNumber("0123456789")
        .emailAddress("test@testing.com").note("TestNote").build();
    AddressEntry test3 = new AddressEntry.Builder("TestName")
        .postalAddress("Apt Test, Testing Ave, Test City, 07306").phoneNumber("0123456789")
        .emailAddress("test@testing.com").note("TestNote3").build();
    AddressEntry test4 = new AddressEntry.Builder("TestName4")
         .postalAddress("Apt Test, Testing Ave, Test City, 58796").phoneNumber("1234567890")
         .emailAddress("test4@testing.com").note("TestNote4").build();
    AddressEntry test5 = new AddressEntry.Builder("TestName5")
         .postalAddress("Apt Test, Testing Ave, Test City, 12345").phoneNumber("0123456789")
         .emailAddress("test5@testing.com").note("TestNote").build();
    
    addressBook.addEntry(test1);
    addressBook.addEntry(test2);
    addressBook.addEntry(test3);
    addressBook.addEntry(test4);
    addressBook.addEntry(test5);
    
    System.out.println("****************************************");
    List<AddressEntry> entries = addressBook.searchBy(SearchBy.NAME, "TestName5");
    System.out.println("Entries with name = TestName5 are as follows :\n");
    printRetreivedEntries(entries);
    
    System.out.println("****************************************");
    entries = addressBook.searchBy(SearchBy.NOTE, "TestNote");
    System.out.println("Entries with note = TestNote are as follows :\n");
    printRetreivedEntries(entries);
    
    System.out.println("****************************************");
    entries = addressBook.searchBy(SearchBy.EMAIL, "test@testing.com");
    System.out.println("Entries with email address = test@testing.com are as follows :\n");
    printRetreivedEntries(entries);
    
    System.out.println("****************************************");
    entries = addressBook.searchBy(SearchBy.PHONE_NUMBER, "0123456789");
    System.out.println("Entries with phone number = 0123456789 are as follows :\n");
    printRetreivedEntries(entries);
    
    System.out.println("****************************************");
    entries = addressBook.searchBy(SearchBy.POSTAL_ADDRESS,
            "Apt Test, Testing Ave, Test City, 12345");
    System.out.println("Entries with postal address = Apt Test, Testing Ave, Test City, 12345 are as follows :\n");
    printRetreivedEntries(entries);
    
    String path = "output.xml";
    addressBook.saveToFile(path, addressBook);
    AddressBook objectFromFile = addressBook.loadFromFile(path);
    System.out.println("****************************************");
    if (objectFromFile == null) {
      System.out.println("Object could not be found");
    } else {
      System.out.println("Object found in the file is : \n" + objectFromFile);
    }
    
  }

  private static void printRetreivedEntries(List<AddressEntry> entries) {
    for(AddressEntry addressEntry : entries){
      System.out.println(addressEntry);
    }
    
  }
}
