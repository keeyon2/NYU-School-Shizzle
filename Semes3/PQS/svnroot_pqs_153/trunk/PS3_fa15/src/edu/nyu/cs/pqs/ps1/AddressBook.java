package edu.nyu.cs.pqs.ps1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.nyu.cs.pqs.utils.AddressBookUtils.SearchBy;

/**
 * This class contains methods that can be used to add, delete, search, save and load objects.
 * 
 * @author Suruchi
 *
 */
public class AddressBook {
  
  private String addressBookName;
  
  private Set<AddressEntry> entrySet = new HashSet<AddressEntry>();
  
  AddressBook() {
  }
  
  AddressBook(String bookName) {
    this.addressBookName = bookName;
  }
  
  /**
   * This method allows the user to create an empty AddressBook object.
   * 
   * @param bookName
   * @return
   */
  public static AddressBook create(String bookName) {
    return new AddressBook(bookName);
  }
  
  /**
   * This method allows the user to add an AddressEntry object to an AddressBook object.
   * 
   * @param addressEntry
   * @return
   */
  public boolean addEntry(AddressEntry addressEntry) {
    return this.entrySet.add(addressEntry);
  }
  
  /**
   * This method allows the user to remove an entry from the AddressBook object.
   * 
   * @param addressEntry
   */
  public boolean remove(AddressEntry addressEntry) {
    return entrySet.remove(addressEntry);
  }
  
  /**
   * This method allows the user to search for an AddressBook object using different search
   * parameters.
   * 
   * @param field
   * @param value
   * @return
   */
  public List<AddressEntry> searchBy(SearchBy field, String value) {
    
    switch (field) {
      case NAME:
        return searchByName(value);
      case POSTAL_ADDRESS:
        return searchByPostalAddress(value);
      case EMAIL:
        return searchByEmail(value);
      case NOTE:
        return searchByNote(value);
      case PHONE_NUMBER:
        return searchByPhoneNumber(value);
      default:
        return null;
    }
  }
  
  List<AddressEntry> searchByPhoneNumber(String value) {
    List<AddressEntry> resultEntries = new ArrayList<AddressEntry>();
    
    for (AddressEntry entry : entrySet) {
      if (entry.getPhoneNumber().equals(value)) {
        resultEntries.add(entry);
      }
    }
    return resultEntries;
  }
  
  List<AddressEntry> searchByEmail(String value) {
    List<AddressEntry> resultEntries = new ArrayList<AddressEntry>();
    
    for (AddressEntry entry : entrySet) {
      if (entry.getEmailAddress().equals(value)) {
        resultEntries.add(entry);
      }
    }
    return resultEntries;
  }
  
  List<AddressEntry> searchByNote(String value) {
    List<AddressEntry> resultEntries = new ArrayList<AddressEntry>();
    
    for (AddressEntry entry : entrySet) {
      if (entry.getNote().equals(value)) {
        resultEntries.add(entry);
      }
    }
    return resultEntries;
  }
  
  List<AddressEntry> searchByPostalAddress(String value) {
    List<AddressEntry> resultEntries = new ArrayList<AddressEntry>();
    
    for (AddressEntry entry : entrySet) {
      if (entry.getPostalAddress().equals(value)) {
        resultEntries.add(entry);
      }
    }
    return resultEntries;
  }
  
  List<AddressEntry> searchByName(String value) {
    List<AddressEntry> resultEntries = new ArrayList<AddressEntry>();
    
    for (AddressEntry entry : entrySet) {
      if (entry.getName().equals(value)) {
        resultEntries.add(entry);
      }
    }
    return resultEntries;
  }
  
  // The code for the below method is inspired from
  // http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
  /**
   * This method allows the user to save the AddressBook object to a specified location. Objects are
   * stored in XML format.
   * 
   * @param path
   * @param addressBook
   */
  public void saveToFile(String path, AddressBook addressBook) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.newDocument();
      
      Element rootElement = doc.createElement("addressBook");
      doc.appendChild(rootElement);
      
      Attr attr = doc.createAttribute("addressBookName");
      attr.setValue(addressBook.getAddressBookName());
      rootElement.setAttributeNode(attr);
      
      for (AddressEntry entry : entrySet) {
        
        Element addressEntries = doc.createElement("addressEntry");
        rootElement.appendChild(addressEntries);
        
        Element nameElement = doc.createElement("name");
        nameElement.appendChild(doc.createTextNode(entry.getName()));
        addressEntries.appendChild(nameElement);
        
        Element postalAddressElement = doc.createElement("postalAddress");
        postalAddressElement.appendChild(doc.createTextNode(entry.getPostalAddress()));
        addressEntries.appendChild(postalAddressElement);
        
        Element phoneNumberElement = doc.createElement("phoneNumber");
        phoneNumberElement.appendChild(doc.createTextNode(entry.getPhoneNumber()));
        addressEntries.appendChild(phoneNumberElement);
        
        Element emailElement = doc.createElement("emailAddress");
        emailElement.appendChild(doc.createTextNode(entry.getEmailAddress()));
        addressEntries.appendChild(emailElement);
        
        Element noteElement = doc.createElement("note");
        noteElement.appendChild(doc.createTextNode(entry.getNote()));
        addressEntries.appendChild(noteElement);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));
        transformer.transform(source, result);
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public String toString() {
    return addressBookName + entrySet.toString();
  }
  
  // Code for the below method is inspired from
  // http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
  /**
   * This method is used to load an AddressBook object from the specified location. It is assumed
   * that the data being read is in XML format.
   * 
   * @param path
   * @return
   */
  public AddressBook loadFromFile(String path) {
    AddressBook addressBook = null;
    try {
      addressBook = new AddressBook();
      
      File inputFile = new File(path);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);
      doc.getDocumentElement().normalize();
      
      Element addressBookNameElement = doc.getDocumentElement();
      addressBook.setAddressBookName(addressBookNameElement.getAttribute("addressBookName"));
      
      NodeList addressEntriesList = doc.getElementsByTagName("addressEntry");
      
      for (int i = 0; i < addressEntriesList.getLength(); i++) {
        Node nNode = addressEntriesList.item(i);
        
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          AddressEntry addrEntryFromFile = new AddressEntry.Builder(eElement
              .getElementsByTagName("name").item(0).getTextContent()).postalAddress(eElement
                  .getElementsByTagName("postalAddress").item(0).getTextContent()).phoneNumber(
                      eElement.getElementsByTagName("phoneNumber").item(0).getTextContent())
                          .emailAddress(eElement.getElementsByTagName("emailAddress").item(0)
                              .getTextContent()).note(eElement.getElementsByTagName("note").item(0)
                                  .getTextContent()).build();
          addressBook.addEntry(addrEntryFromFile);
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return addressBook;
  }
  
  /**
   * This is a getter for addressBookName
   * 
   * @return
   */
  public String getAddressBookName() {
    return addressBookName;
  }
  
  /**
   * This is a getter method for the set of AddressEntry objects
   * 
   * @return
   */
  public Set<AddressEntry> getEntry() {
    return entrySet;
  }
  
  /**
   * This is the setter for addressBookName
   * 
   * @param addressBookName
   */
  public void setAddressBookName(String addressBookName) {
    this.addressBookName = addressBookName;
  }
  
  /**
   * This is the setter for the set of AddressEntry objects
   * 
   * @param entry
   */
  public void setEntry(Set<AddressEntry> entry) {
    this.entrySet = entry;
  }
  
}
