package edu.nyu.cs;

import java.util.ArrayList;

public class Main {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    StartWorking();
  }
  
  public static void StartWorking () {
    System.out.println("Program Started" + "\n");
    Contact c1  = new Contact.Builder("Keeyon").lastName("Ebrahimi")
        .phoneNumber(123).emailAddress("Keeyon2@gmail.com").build();
    System.out.println("Contact is: ");
    System.out.println(c1.toString());
    System.out.println("");
    
    AddressBook ad = AddressBook.newInstance();
    ad.AddContact(c1);
    ad.AddContact(c1);
    ad.AddContact(c1);
    System.out.println(ad.GetJSONAddressBook().toJSONString());
    
    String file = "/Users/Keeyon/Desktop/ad.json";
    SaveFile(ad, file);
    Read(file);
  }
  
  public static void SaveFile(AddressBook ad, String file) {
    ad.SaveToFile(file);
  }

  public static void Read(String file) {
    AddressBook ad = AddressBook.newInstance();
    ad.ReadFromFile(file);
    System.out.println("READ THIS FROM FILE");
    ad.toString();
    System.out.println("Now we will try to delete" + "\n");
    ArrayList<Contact> foundContacts = ad.SearchForContacts("Keeyon");
    Contact deleteContact = foundContacts.get(0);
    ad.RemoveContact(deleteContact);
    ad.RemoveContact(deleteContact);
    System.out.println(ad);
    
  }
}
