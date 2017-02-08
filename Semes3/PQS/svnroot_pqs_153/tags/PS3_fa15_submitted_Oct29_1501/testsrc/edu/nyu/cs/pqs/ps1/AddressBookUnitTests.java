package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.pqs.utils.AddressBookUtils.SearchBy;

public class AddressBookUnitTests {
 
  AddressBook testBook;
  AddressEntry testEntry;
  List<AddressEntry> emptyAddressEntrySet;
  
  @Before
  public void setUp() {
    testBook = new AddressBook("TestBookName");
    testEntry = new AddressEntry.Builder("name")
        .postalAddress("postalAddress").phoneNumber("0123456789")
        .emailAddress("test@testing.com").note("note").build();
    
    emptyAddressEntrySet = new ArrayList<AddressEntry>();
  }
  
  @Test
  public void AddressBook_correctName() {
    assertEquals("TestBookName", testBook.getAddressBookName());
  }
  
  @Test
  public void create_correctName() {
    testBook = AddressBook.create("newName");
    assertEquals("newName", testBook.getAddressBookName());
  }
  
  @Test
  public void AddressBook_emptyConstructor() {
    testBook = new AddressBook();
    assertNotNull(testBook.getAddressBookName());
  }
  
  @Test
  public void AddressBook_emptyConstructorChangeName() {
    testBook = new AddressBook();
    testBook.setAddressBookName("");
    assertNotNull(testBook.getAddressBookName());
  }
  
  @Test
  public void setAddressBookName_multipleNames() {
    testBook.setAddressBookName("TestName1");
    testBook.setAddressBookName("TestName2");
    testBook.setAddressBookName("TestName3");
    testBook.setAddressBookName("TestName4");
    testBook.setAddressBookName("TestName5");
    assertEquals("TestName5", testBook.getAddressBookName());
  }
  
  @Test
  public void addEntry_addSameMultipleTimes() {
    testBook.addEntry(testEntry);
    testBook.addEntry(testEntry);
    testBook.addEntry(testEntry);
    testBook.addEntry(testEntry);
    testBook.addEntry(testEntry);
    
    assertEquals(1, testBook.getEntry().size());
  }
  
  @Test
  public void addEntry_addMultipleEntry() {
    testBook.addEntry(testEntry);
    testEntry.setName("newName");
    testBook.addEntry(testEntry);
    
    assertEquals(2, testBook.getEntry().size());
  }
  
  @Test
  public void addEntry_singleEntry() {
    testBook.addEntry(testEntry);
    assertEquals(1, testBook.getEntry().size());
  }
  
  @Test
  public void removeEntry_addAndRemoveSingle() {
    testBook.addEntry(testEntry);
    testBook.remove(testEntry);
    assertEquals(0, testBook.getEntry().size());
  }
  
  @Test
  public void removeEntry_addAndRemoveMultiple() {
    testBook.addEntry(testEntry);
    testBook.addEntry(testEntry);
    testBook.addEntry(testEntry);
    testBook.addEntry(testEntry);
    testBook.addEntry(testEntry);
    testBook.remove(testEntry);

    assertEquals(0, testBook.getEntry().size()); 
  }
  
  @Test
  public void addEntry_addAndRemoveMultiple() {
    testEntry.setName("name1");
    testBook.addEntry(testEntry);
    testEntry.setName("name2");
    testBook.addEntry(testEntry);
    testEntry.setName("name3");
    testBook.addEntry(testEntry);
    testEntry.setName("name4");
    testBook.addEntry(testEntry);
    
    testBook.remove(testEntry);
    assertEquals(3, testBook.getEntry().size());
  }
  
  @Test
  public void remove_nonExistantEntry() {
    testBook.remove(testEntry);
  }
  
  @Test
  public void setEntry() {
    Set<AddressEntry> testSet = new HashSet<AddressEntry>();
    testEntry.setName("name1");
    testSet.add(testEntry);
    testEntry.setName("name2");
    testSet.add(testEntry);
    testEntry.setName("name3");
    testSet.add(testEntry);
    testEntry.setName("name4");
    testSet.add(testEntry);
    
    testBook.setEntry(testSet);
    assertEquals(testSet, testBook.getEntry());
  }
  
  @Test
  public void toString_notNull() {
    assertNotNull(testBook.toString());
  }
  
  @Test
  public void searchByName_single() {
    testEntry.setName("nameToSearchOn");
    testBook.addEntry(testEntry);
    
    assertEquals(1, testBook.searchBy(SearchBy.NAME, "nameToSearchOn").size());
  }
  
  @Test
  public void searchByName_multiple() {
    Set<AddressEntry> entrySet = createSet(3, SearchBy.NAME, "nameToSearchOn"); 
    testBook.setEntry(entrySet);
    assertEquals(3, testBook.searchBy(SearchBy.NAME, "nameToSearchOn").size());
  }
  
  @Test
  public void searchByName_multipleWithRemove() {
    Set<AddressEntry> entrySet = createSet(3, SearchBy.NAME, "nameToSearchOn"); 
    testEntry.setEmailAddress("newEmailToDifferentiate");
    entrySet.add(testEntry);
    testBook.setEntry(entrySet);
    testBook.remove(testEntry);
    
    assertEquals(3, testBook.searchBy(SearchBy.NAME, "nameToSearchOn").size());
  }
  
  @Test
  public void searchByName_nonExistant() {
    testBook.addEntry(testEntry);
    assertEquals(emptyAddressEntrySet, testBook.searchBy(SearchBy.NAME, 
        "NonExistantName"));
  }
  
  @Test
  public void searchByEmail_single() {
    testEntry.setEmailAddress("emailToSearchOn");
    testBook.addEntry(testEntry);
    
    assertEquals(1, testBook.searchBy(SearchBy.EMAIL, "emailToSearchOn").size());    
  }
  
  @Test
  public void searchByEmail_multiple() {
    Set<AddressEntry> entrySet = createSet(7, SearchBy.EMAIL, "emailToSearchOn"); 
    testBook.setEntry(entrySet);
    
    assertEquals(7, testBook.searchBy(SearchBy.EMAIL, "emailToSearchOn").size());    
  }
  
  @Test
  public void searchByEmail_nonExistant() {
    testBook.addEntry(testEntry);
    assertEquals(emptyAddressEntrySet, testBook.searchBy(SearchBy.EMAIL, 
        "NonExistantEmail"));
  }
   
  @Test
  public void searchByNote_single() {
    testEntry.setNote("noteToSearchOn");
    testBook.addEntry(testEntry);
    
    assertEquals(1, testBook.searchBy(SearchBy.NOTE, "noteToSearchOn").size());    
  }
  
  @Test
  public void searchByNote_multiple() {
    Set<AddressEntry> entrySet = createSet(75, SearchBy.NOTE, "noteToSearchOn"); 
    testBook.setEntry(entrySet);
    
    assertEquals(75, testBook.searchBy(SearchBy.NOTE, "noteToSearchOn").size());    
  }
  
  @Test
  public void searchByNote_nonExistant() {
    testBook.addEntry(testEntry);
    assertEquals(emptyAddressEntrySet, testBook.searchBy(SearchBy.NOTE, 
        "NonExistantNote"));
  }
  
  @Test
  public void searchByPostalAddress_single() {
    testEntry.setPostalAddress("postalAddressToSearchOn");
    testBook.addEntry(testEntry);
    
    assertEquals(1, testBook.searchBy(SearchBy.POSTAL_ADDRESS, 
        "postalAddressToSearchOn").size());    
  }
  
  @Test
  public void searchByPostalAddress_multiple() {
    Set<AddressEntry> entrySet = createSet(33, SearchBy.POSTAL_ADDRESS, 
        "postalAddressToSearchOn");
    testBook.setEntry(entrySet);
    
    assertEquals(33, testBook.searchBy(SearchBy.POSTAL_ADDRESS, 
        "postalAddressToSearchOn").size());
  }
  
  @Test
  public void searchByPostalAddress_nonExistant() {
    testBook.addEntry(testEntry);
    assertEquals(emptyAddressEntrySet, testBook.searchBy(SearchBy.POSTAL_ADDRESS, 
        "NonExistantPostalAddress"));
  }
  
  @Test
  public void searchByPhoneNumber_single() {
    testEntry.setPhoneNumber("phoneNumberToSearchOn");
    testBook.addEntry(testEntry);
    
    assertEquals(1, testBook.searchBy(SearchBy.PHONE_NUMBER, 
        "phoneNumberToSearchOn").size());    
  }
  
  @Test
  public void searchByPhoneNumber_multiple() {
    Set<AddressEntry> entrySet = createSet(323, SearchBy.PHONE_NUMBER, 
        "phoneNumberToSearchOn"); 
    testBook.setEntry(entrySet);
    
    assertEquals(323, testBook.searchBy(SearchBy.PHONE_NUMBER, 
        "phoneNumberToSearchOn").size());
  }
  
  @Test
  public void searchByPhoneNumber_nonExistant() {
    testBook.addEntry(testEntry);
    assertEquals(emptyAddressEntrySet, testBook.searchBy(SearchBy.PHONE_NUMBER, 
        "NonExistantPhoneNumber"));
  }
  
  /**
   * Test if saving and loading creates the same object
   * This does not return true even though all of the elements within
   * the AddressBook is the same.
   * This bug exist because the AddressBook does not have a Equals Function   * 
   * 
   * We also do not get coverage on the entire catch.  Code review comment
   * would be to make the try block smaller so the atcual catch could be simulated
   * accurately.
   */
  @Test
  public void saveAndLoadTest() {
    testBook.addEntry(testEntry);
    testBook.saveToFile("output.xml", testBook);
    AddressBook newTestBook = new AddressBook();
    newTestBook = newTestBook.loadFromFile("output.xml");
    assertEquals(newTestBook, testBook);
  }
  
  public Set<AddressEntry> createSet(int elements, SearchBy type, String searchOn) {
    Set<AddressEntry> entrySet = new HashSet<AddressEntry>();
    String[] differentiatingString = new String[elements];
    for (int i = 0; i < elements; i++) {
      differentiatingString[i] = "differentiate" + i;
    }
    
    switch(type) {
      case NAME:
        testEntry.setName(searchOn);
        for (int i = 0; i < elements; i++) {
          testEntry.setEmailAddress(differentiatingString[i]);
          entrySet.add(testEntry);
        }
        return entrySet;
      case POSTAL_ADDRESS:
        testEntry.setPostalAddress(searchOn);
        for (int i = 0; i < elements; i++) {
          testEntry.setEmailAddress(differentiatingString[i]);
          entrySet.add(testEntry);
        }
        return entrySet;
      case PHONE_NUMBER:
        testEntry.setPhoneNumber(searchOn);
        for (int i = 0; i < elements; i++) {
          testEntry.setEmailAddress(differentiatingString[i]);
          entrySet.add(testEntry);
        }
        return entrySet;        
      case EMAIL:
        testEntry.setEmailAddress(searchOn);
        for (int i = 0; i < elements; i++) {
          testEntry.setName(differentiatingString[i]);
          entrySet.add(testEntry);
        }
        return entrySet;
      case NOTE:
        testEntry.setNote(searchOn);
        for (int i = 0; i < elements; i++) {
          testEntry.setEmailAddress(differentiatingString[i]);
          entrySet.add(testEntry);
        }
        return entrySet;
       default:
         return entrySet;
    }
  }
}
