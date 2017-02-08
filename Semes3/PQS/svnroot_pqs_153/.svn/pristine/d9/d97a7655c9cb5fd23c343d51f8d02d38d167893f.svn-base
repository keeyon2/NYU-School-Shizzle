import java.util.HashMap;
import java.util.Map;

/*
 * The Phone Class stores the phone number and Phone type.
 */
public class Phone {
  private String number;
  private Label label;
  
  /*
   * Constructor with complete parameters.
   */
  public Phone(String theNum, Label theLabel) {
    number = theNum;
    label = theLabel;
  }
  
  /*
   * Constructor, with the label as a string type.
   */
  public Phone(String theNum, String theLabel) {
    number = theNum;
    label = getLabelFromStr(theLabel);
  }
  
  /*
   * Constructor, with only the number, set label default to Home.
   */
  public Phone(String theNum) {
    number = theNum;
    label = Label.HOME;
  }
  
  private void setNumber(String num) {
    number = num;
  }
  
  private void setLabel(Label theLabel) {
    label = theLabel;
  }
  
  /*
   * Gets the number of a phone.
   */
  public String getNumber() {
    return number;
  }
  
  /*
   *  Gets phone label, label can be home, work or other.
   */
  public Label getLabel() {
    return label;
  }
  
  /*
   * Gets the string value of the phone's current label.
   */
  public String getLabelStr() {    
    return getStrFromLabel(label);
  }
  
  /*
   * Gets label from string.
   * 
   * @param str  Lable's string form.
   * @return     Lable's enum form.
   */
  public static Label getLabelFromStr(String str) {
    Map<String, Label> labelNameMap = new HashMap<String, Label>();
    labelNameMap.put("Home", Label.HOME);
    labelNameMap.put("Work", Label.WORK);
    labelNameMap.put("Other", Label.OTHER);
    
    return labelNameMap.get(str);
  }
 
  /*
   * Gets label from string.
   * 
   * @param label  Lable's enum form.
   * @return     Lable's String form.
   */
  public static String getStrFromLabel(Label label) {
    Map<Label, String> labelNameMap = new HashMap<Label, String>();
    labelNameMap.put(Label.HOME, "Home");
    labelNameMap.put(Label.WORK, "Work");
    labelNameMap.put(Label.OTHER, "Other");
    
    return labelNameMap.get(label);
  }
  
}
