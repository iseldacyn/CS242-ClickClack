package data;

/**
 * The class MessageClackData is a subclass of ClackData and contains the following variables:
 * fileName - String representing name of file
 * fileContents - String representing contents of file
 * @author Iselda Aiello
 */
public class FileClackData extends ClackData{

  private String fileName;
  private String fileContents;

  /**
   * Constructor to set up username, fileName, and type
   * @param userName sets the username of the user
   * @param fileName sets the name of the file
   * @param type sets the kind of data exchanged between client and server
   */
  public FileClackData(String userName,String fileName,int type){
    super(userName,type);
    this.fileName = fileName;
    this.fileContents = null;
  }

  /**
   * Default constructor
   */
  public FileClackData(){
    super();
    this.fileName = null;
    this.fileContents = null;
  }

  /**
   * Sets the file name in this object
   * @param fileName the new file name
   */
  public void setFileName(String fileName){
    this.fileName = fileName;
  }

  /**
   * Returns the file name
   * @return file name of the object
   */
  public String getFileName(){
    return this.fileName;
  }

  /**
   * Implemented here to return file contents
   * @return the contents of the file
   */
  public String getData(){
    return this.fileContents;
  }

  /**
   * overloaded here to return decrypted file contents
   * @param key the decryption key used to decrypt file contents
   * @return the decrypted file contents
   */
  public String getData(String key){
    return decrypt(this.fileContents, key);
  }

  /**
   * Does not return anything
   * For now it should have no code, just a declaration
   */
  public void readFileContents(){}

  /**
   * Does not return anything
   * For now it should have no code, just a declaration
   */
  public void writeFileContents(){}

  @Override
  public int hashCode(){
    return this.toString().hashCode();
  }
  
  @Override
  public boolean equals(Object other){
    if(!(other instanceof FileClackData)) return false;
    FileClackData otherFileClackData = (FileClackData)other;
    return this.username.equals(otherFileClackData.username) &&
            this.type == otherFileClackData.type && 
            this.date.equals(otherFileClackData.date) &&
            this.fileName.equals(otherFileClackData.fileName) &&
            this.fileContents.equals(otherFileClackData.fileContents);
  }

  @Override
  public String toString(){
    return "The username is: " + this.username + '\n' +
            "The date is: " + this.date.toString() + '\n' +
            "The file name is: " + this.fileName + '\n' +
            "The file contents are: " + this.fileContents;
  }

}
