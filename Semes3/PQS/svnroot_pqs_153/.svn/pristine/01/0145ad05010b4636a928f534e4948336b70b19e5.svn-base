 
import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * The AddressBookXMLFile handles all XML file operations, like write and read.
 */
public class AddressBookXMLFile {
  private String path;
  
  /*
   * Constructor
   * 
   * @param thePath Path of the XML file.
   */
  public AddressBookXMLFile(String thePath) {
    path = thePath;
  }
  
  /*
   * Writes a list of contacts out to a XML file.
   * 
   * @param contacts  Contacts to be wrote out.
   */
  public void write(Vector<Contact> contacts) {
    DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder icBuilder;
    
    try {
      icBuilder = icFactory.newDocumentBuilder();
      Document doc = icBuilder.newDocument();
      Element mainRootElement = doc.createElementNS("PQS hw1", "Contacts");
      doc.appendChild(mainRootElement);
      
      for (int i = 0; i < contacts.size(); i++) {
        mainRootElement.appendChild(getContactNode(doc,contacts.get(i)));
      }
      
      writeToFile(doc);
      
    } catch (Exception e) {
        e.printStackTrace();
    }
  }
  
  /*
   * Read a vector of contacts infomation from a XML file.
   * 
   * @return  A vector that contains all contacts from file.
   */
  public Vector<Contact> read() {
    Vector<Contact> contacts = new Vector<Contact>();
    
    try {
      File XmlFile = new File(path);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(XmlFile);
      NodeList root = doc.getChildNodes();
      
      if (root.getLength() > 0) {
        Node allContacts = root.item(0);
        
        if (allContacts.getNodeType() == Node.ELEMENT_NODE) {
          NodeList contactList = allContacts.getChildNodes();
          
          for (int i = 0; i < contactList.getLength(); i++) {
            Node theContact = contactList.item(i);
            
            if (theContact.getNodeType() == Node.ELEMENT_NODE) {                      
              Element e = (Element) theContact;
              Contact contact = getContactFromElement(e); 
              
              contacts.addElement(contact);
            }
          } 
        }
      }     
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return contacts;
  }
  
  /*
   * Get a Contact instance from DOM Element.
   * 
   * @param e The DOM element to read.
   * @return   A Contact instance.
   */
  private Contact getContactFromElement(Element e) {
    String firstName = e.getElementsByTagName("FirstName").item(0).getTextContent();
    String middleName = e.getElementsByTagName("MiddleName").item(0).getTextContent();
    String lastName = e.getElementsByTagName("LastName").item(0).getTextContent();
    String street = e.getElementsByTagName("Street").item(0).getTextContent();
    String city = e.getElementsByTagName("City").item(0).getTextContent();
    String state = e.getElementsByTagName("State").item(0).getTextContent();
    String country = e.getElementsByTagName("Country").item(0).getTextContent();
    String zipcode = e.getElementsByTagName("Zipcode").item(0).getTextContent();
    String email = e.getElementsByTagName("Email").item(0).getTextContent();
    String note = e.getElementsByTagName("Note").item(0).getTextContent();
    
    Name name = new Name(firstName, middleName, lastName);
    Address address = new Address(street, city, state, country, zipcode);
    NodeList allPhones = e.getElementsByTagName("Phone");
    Vector<Phone> phones = new Vector<Phone>();
    
    for (int i = 0; i < allPhones.getLength(); i++) {
      String num = allPhones.item(i).getTextContent();
      String label = allPhones.item(i).getAttributes().getNamedItem("label").getNodeValue();
      
      Phone phone = new Phone(num, label);
      phones.addElement(phone);
    }
    
    Contact contact = new Contact(name, address, phones, email, note);
    
    return contact;
  }
  
  /*
   * Gets a contact DOM node.
   * 
   * @param doc  Document.
   * @param theContact  The contact used to the create the DOM Node.
   * @return A DOM node that contains the contact infomation.
   */
  private static Node getContactNode(Document doc, Contact theContact) {
    Element contact = doc.createElement("Contact");
    contact = appendNameInfo(doc, contact, theContact.getName());
    contact = appendAddressInfo(doc, contact, theContact.getAddress());
    contact.appendChild(getNode(doc, "Email", theContact.getEmail()));
    contact.appendChild(getNode(doc, "Note", theContact.getNote()));
    contact = appendPhoneInfo(doc, contact, theContact.getAllPhone());
    
    return contact;
  }
  
  /*
   * Append name information to the contact Element.
   */
  private static Element appendNameInfo(Document doc, Element contact, Name name) {
    contact.appendChild(getNode(doc, "FirstName", name.getFirstName()));
    contact.appendChild(getNode(doc, "MiddleName", name.getMiddleName()));
    contact.appendChild(getNode(doc, "LastName", name.getLastName()));
    
    return contact;
  }
  
  /*
   * Append address information to the contact Element.
   */
  private static Element appendAddressInfo(Document doc, Element contact, Address address) {
    contact.appendChild(getNode(doc, "Street", address.getStreet()));
    contact.appendChild(getNode(doc, "City", address.getCity()));
    contact.appendChild(getNode(doc, "State", address.getState()));
    contact.appendChild(getNode(doc, "Country", address.getCountry()));
    contact.appendChild(getNode(doc, "Zipcode", address.getZipcode()));
    
    return contact;
  }
  
  /*
   * Append the contact's phones' information to the contact Element.
   */  
  private static Element appendPhoneInfo(Document doc, Element contact, Vector<Phone> phones) {
    for (int i = 0; i < phones.size(); i++) {
      String label = phones.get(i).getLabelStr();
      String number = phones.get(i).getNumber();
      Node phoneNode = getNode(doc, "Phone", number, "label", label);
      
      contact.appendChild(phoneNode);
    }
    
    return contact;
  }
  
  /*
   * Gets a DOM node by key and value.
   */
  private static Node getNode(Document doc, String key, String value) {
    Element node = doc.createElement(key);
    node.appendChild(doc.createTextNode(value));
    
    return node;
  }
  
  /*
   * Gets a DOM node by key, value and attribute infomation.
   */
  private static Node getNode(Document doc, String key, String value, 
      String attrKey, String attrVal) {
    Element node = doc.createElement(key);
    node.appendChild(doc.createTextNode(value));
    node.setAttribute(attrKey, attrVal);
    
    return node;
  }
  
  /*
   * Write the Document to XML file.
   * 
   * @param doc The document to be written.
   */
  private void writeToFile(Document doc) {
    try {
      Transformer transformer = 
          TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
      DOMSource source = new DOMSource(doc);      
      StreamResult result = new StreamResult(new File(path));
      transformer.transform(source, result);      
    } catch (Exception e) {
      // Catch Transformer Exception.
      e.printStackTrace();
    }
  }
  
}
