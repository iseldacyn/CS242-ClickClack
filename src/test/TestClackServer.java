package test;

import main.ClackServer;

/**
 * The TestClackServer class runs the main function
 * @author Iselda Aiello
 */
public class TestClackServer {

  /**
   * The main function tests the methods and constructors of the ClackServer class
   * @param args command line arguments
   */
  public static void main(String[] args){

    //tests all constructors for ClackServer object
    ClackServer testClackServer = new ClackServer(5804);
    ClackServer testClackServer2 = new ClackServer();

    //tests overloaded toString() method
    System.out.println(testClackServer);
    System.out.println(testClackServer2);

    //tests getPort() method
    System.out.println(testClackServer.getPort());

    //tests overloaded hashCode() method
    System.out.println(testClackServer.hashCode());

    //tests overloaded equals() method for an equal and unequal instance of ClackServer
    //note: since dataToReceiveFromClient and dataToSendToClient are initialized as null,
    // there is an error when comparing them
    System.out.println(testClackServer.equals(testClackServer));
    System.out.println(testClackServer.equals(testClackServer2));

  }

}
