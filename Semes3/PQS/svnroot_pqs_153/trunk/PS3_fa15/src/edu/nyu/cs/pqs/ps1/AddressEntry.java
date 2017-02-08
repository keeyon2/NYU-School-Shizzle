package edu.nyu.cs.pqs.ps1;

import java.util.Objects;

/**
 * This public class contains basic address entry information as described in the problem set.
 * 
 * @author Suruchi
 *
 */
public class AddressEntry {
  /**
   * Builder class
   */
  public static class Builder {
    // Required parameters
    private String name;
    
    // Optional Parameters - initialized to default values
    private String postalAddress = "";
    private String phoneNumber = "";
    private String emailAddress = "";
    private String note = "";
    
    /**
     * Parameterized constructor accepting the name of the AddressEntry
     * 
     * @param name
     */
    public Builder(String name) {
      this.name = name;
    }
    
    /**
     * This is method is used to create an instance of the AddressEntry object
     * 
     * @return
     */
    public AddressEntry build() {
      return new AddressEntry(this);
    }
    
    /**
     * This method is used to set the value of the email address.
     * 
     * @param val
     * @return
     */
    public Builder emailAddress(String val) {
      emailAddress = val;
      return this;
    }
    
    /**
     * This method is used to set the value of the note
     * 
     * @param val
     * @return
     */
    public Builder note(String val) {
      note = val;
      return this;
    }
    
    /**
     * This method is used to set the value of the phone number
     * 
     * @param val
     * @return
     */
    public Builder phoneNumber(String val) {
      phoneNumber = val;
      return this;
    }
    
    /**
     * This method is used to set the value of the postal address
     * 
     * @param val
     * @return
     */
    public Builder postalAddress(String val) {
      postalAddress = val;
      return this;
    }
    
  }
  
  private String name;
  private String postalAddress;
  private String phoneNumber;
  private String emailAddress;
  private String note;
  
  private AddressEntry(Builder builder) {
    this.name = builder.name;
    this.postalAddress = builder.postalAddress;
    this.phoneNumber = builder.phoneNumber;
    this.emailAddress = builder.emailAddress;
    this.note = builder.note;
  }
  
  @Override
  public boolean equals(Object entryObj) {
    if (entryObj == null) {
      return false;
    }
    
    if (!(entryObj instanceof AddressEntry)) {
      return false;
    }
    
    AddressEntry addrEntryObject = (AddressEntry) entryObj;
    
    if (this.name.equals(addrEntryObject.getName())
            && this.postalAddress.equals(addrEntryObject.getPostalAddress())
            && this.phoneNumber.equals(addrEntryObject.getPhoneNumber())
            && this.emailAddress.equals(addrEntryObject.getEmailAddress())
            && this.note.equals(addrEntryObject.getNote())) {
      return true;
    }
    return false;
  }
  
  /**
   * Getter method for the email address
   * 
   * @return
   */
  public String getEmailAddress() {
    return emailAddress;
  }
  
  /**
   * Getter method for the name
   * 
   * @return
   */
  public String getName() {
    return name;
  }
  
  /**
   * Getter method for the note
   * 
   * @return
   */
  public String getNote() {
    return note;
  }
  
  /**
   * Getter method for phone number
   * 
   * @return
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  /**
   * Getter method for postal address
   * 
   * @return
   */
  public String getPostalAddress() {
    return postalAddress;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(name.hashCode(), postalAddress.hashCode(), phoneNumber.hashCode(),
        emailAddress.hashCode(), note.hashCode());
  }
  
  /**
   * Setter method for email address
   * 
   * @param emailAddress
   */
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }
  
  /**
   * Setter method for name
   * 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Setter method for note
   * 
   * @param note
   */
  public void setNote(String note) {
    this.note = note;
  }
  
  /**
   * Setter method for phone number
   * 
   * @param phoneNumber
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  
  /**
   * Setter method for postal address
   * 
   * @param postalAddress
   */
  public void setPostalAddress(String postalAddress) {
    this.postalAddress = postalAddress;
  }
  
  @Override
  public String toString() {
    return "AddressEntry [name=" + name + ", postalAddress=" + postalAddress + ", phoneNumber="
        + phoneNumber + ", emailAddress=" + emailAddress + ", note=" + note + "]\n";
  }
  
}
