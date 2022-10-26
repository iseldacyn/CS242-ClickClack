package data;

import java.util.Date;

/**
 * The abstract class ClackData contains the following variables:
 * username - String representing name of client user
 * type - An integer, refer to section labeled About the type variable
 * date - Date object representing date when ClackData object was created
 * @author Iselda Aiello
 */
public abstract class ClackData {

  //int variables for the different values of "type" 
  public static final int CONSTANT_LISTUSERS = 0;
  public static final int CONSTANT_LOGOUT = 1;
  public static final int CONSTANT_SENDMESSAGE = 2;
  public static final int CONSTANT_SENDFILE = 3;

  protected String username;
  protected int type;
  protected Date date;

  /**
   * Constructor to set up userName and type, date should be created automatically here
   * @param userName sets the username of the user
   * @param type sets the kind of data exchanged between client and server
   */
  public ClackData(String userName, int type){
    this.username = userName;
    this.type = type;
    this.date = new Date();
  }

  /**
   * Constructor to create anonymous user, whose name should be Anon
   * @param type sets the kind of data exchanged between client and server
   */
  public ClackData(int type){
    this("Anon",type);
  }

  /**
   * Default constructor, sets type to "CONSTANT_LISTUSERS"
   */
  public ClackData(){
    this(CONSTANT_LISTUSERS);
  }

  /**
   * Return the type
   * @return type of the object
   */
  public int getType(){
    return this.type;
  }

  /**
   * Return the username
   * @return username of the user
   */
  public String getUserName(){
    return this.username;
  }

  /**
   * Returns the date
   * @return date the object was created
   */
  public Date getDate(){
    return this.date;
  }

  protected String encrypt( String inputStringToEncrypt, String key ){
    return "";
  }

  protected String decrypt( String inputStringToDecrypt, String key ){
    return "";
  }

  /**
   * Abstract method to return the data contained in this class (contents of instant message or contents of a file)
   * @return pass to subclasses
   */
  public abstract String getData();

}
