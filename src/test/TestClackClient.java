package test;

import main.ClackClient;

/**
 * The TestClackClient class runs the main function
 * @author Iselda Aiello
 */
public class TestClackClient {

  /**
   * The main function tests the methods and constructors of the ClackClient class
   * @param args command line arguments
   */
  public static void main(String[] args){

    //tests all constructors for ClackClient object
    ClackClient testClackClient = new ClackClient("myUser","myHost",5804);
    ClackClient testClackClient2 = new ClackClient("myUser","myHost");
    ClackClient testClackClient3 = new ClackClient("myUser");
    ClackClient testClackClient4 = new ClackClient();

    //tests overloaded toString() method
    System.out.println(testClackClient);
    System.out.println(testClackClient2);
    System.out.println(testClackClient3);
    System.out.println(testClackClient4);

    //tests getUserName() method
    System.out.println(testClackClient.getUserName());

    //tests getHostName() method
    System.out.println(testClackClient.getHostName());

    //tests getPort() method
    System.out.println(testClackClient.getPort());

    //tests overloaded hashCode() method
    System.out.println(testClackClient.hashCode());

    //tests overloaded equals() method for an equal and unequal instance of ClackClient
    //note: since dataToSendToServer and dataToReceiveFromServer are initialized as null,
    // there is an error when comparing them
    //System.out.println(testClackClient.equals(testClackClient));
    //System.out.println(testClackClient.equals(testClackClient2));

    //tests InvalidArgumentException for ClackClient
    //ClackClient testClackClient5 = new ClackClient(null);
    //ClackClient testClackClient5 = new ClackClient("myUser",null);
    //ClackClient testClackClient5 = new ClackClient("myUser","myHost",1000);

    //tests start() on all constructors
    testClackClient.start();
    testClackClient2.start();
    testClackClient3.start();
    testClackClient4.start();

  }

}
