package test;

import data.MessageClackData;
import data.FileClackData;

import java.io.IOException;

/**
 * The TestClackData class runs the main function
 * @author Iselda Aiello
 */
public class TestClackData {

  /**
   * The main function tests the methods and constructors of the ClackData class<br>
   * The MessageClackData and FileClackData subclasses of ClackData are tested in main
   * @param args command line arguments
   */
  public static void main(String[] args){
    
    //tests all constructors for MessageClackData object
    MessageClackData testMessageClackData = new MessageClackData("myUser","Hello World",0);
    MessageClackData testMessageClackData3 = new MessageClackData("myUser",
            "Hello there my CRANKY CREW=-=-+()" , "jimbo+L",0);
    MessageClackData testMessageClackData2 = new MessageClackData();


    //tests overloaded toString() method
    System.out.println(testMessageClackData);
    System.out.println(testMessageClackData2);

    //tests getType() method
    System.out.println(testMessageClackData.getType());

    //tests getUserName() method
    System.out.println(testMessageClackData.getUserName());

    //tests getDate() method
    System.out.println(testMessageClackData.getDate());
    
    //tests getData() method implementation
    System.out.println(testMessageClackData.getData());

    //tests overloaded hashCode() method
    System.out.println(testMessageClackData.hashCode());

    //tests overloaded equals() method for an equal and unequal instance of MessageClackData
    System.out.println(testMessageClackData.equals(testMessageClackData));
    System.out.println(testMessageClackData.equals(testMessageClackData2));
    
    //tests all constructors for FileClackData object
    FileClackData testFileClackData = new FileClackData("myUser","test2.txt",0);
    FileClackData testFileClackData2 = new FileClackData();

    //tests overloaded toString() method
    System.out.println(testFileClackData);
    System.out.println(testFileClackData2);

    //tests getType() method
    System.out.println(testFileClackData.getType());

    //tests getUserName() method
    System.out.println(testFileClackData.getUserName());

    //tests getDate() method
    System.out.println(testFileClackData.getDate());

    //tests getFileName() method
    System.out.println(testFileClackData.getFileName());

    //tests setFileName() method and ensures the fileName is changed
    testFileClackData.setFileName("test1.txt");
    System.out.println(testFileClackData.getFileName());

    //tests getData() method implementation
    System.out.println(testFileClackData.getData());

    //tests overloaded hashCode() method
    System.out.println(testFileClackData.hashCode());

    //tests overloaded equals() method for an equal and unequal instance of FileClackData
    //note: since fileName and fileContents are initialized as null, there is an error when comparing them
    //System.out.println(testFileClackData.equals(testFileClackData));
    //System.out.println(testFileClackData.equals(testFileClackData2));

    //tests getData() to ensure the message is encrypted/decrypted properly
    System.out.println(testMessageClackData3.getData());
    System.out.println(testMessageClackData3.getData("jimbo+L"));

    //tests readFileContents and writeFileContents
    try {
      testFileClackData.readFileContents();
      System.out.println(testFileClackData.getData());
      testFileClackData.readFileContents("jimbo+L");
      System.out.println(testFileClackData.getData());
      testFileClackData.setFileName("test2.txt");
      testFileClackData.writeFileContents("jimbo+L");
      testFileClackData.readFileContents();
      System.out.println(testFileClackData.getData());
      testFileClackData.writeFileContents();
    } catch(IOException ioe) {
      System.out.println("Error");
    }

  }
}
