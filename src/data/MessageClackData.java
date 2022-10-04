package data;

/**
 * The class MessageClackData is a subclass of ClackData and contains the following variables:
 * message - String representing instant message
 * @author Iselda Aiello
 */
public class MessageClackData extends ClackData{

  private String message;

  /**
   * Constructor to set up username, message, and type
   * @param userName sets the username of the user
   * @param message sets the instant message of the user
   * @param type sets the kind of data exchanged between client and server
   */
  public MessageClackData(String userName,String message,int type){
    super(userName,type);
    this.message = message;
  }

  /**
   * Default constructor
   */
  public MessageClackData(){
    super();
    this.message = null;
  }

  /**
   * Implemented here to return instant message
   * @return the instant message of the user
   */
  public String getData(){
    return this.message;
  }

  @Override
  public int hashCode(){
    return this.toString().hashCode();
  }

  @Override
  public boolean equals(Object other){
    if(!(other instanceof MessageClackData)) return false;
    MessageClackData otherMessageClackData = (MessageClackData)other;
    return this.username.equals(otherMessageClackData.username) &&
            this.type == otherMessageClackData.type &&
            this.date.equals(otherMessageClackData.date) &&
            this.message.equals(otherMessageClackData.message);
  }

  @Override
  public String toString(){
    return "The username is: " + this.username + '\n' +
            "The date is: " + this.date.toString() + '\n' +
            "The message is: " + this.message;
  }

}
