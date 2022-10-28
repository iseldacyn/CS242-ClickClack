package main;

import data.ClackData;
import data.FileClackData;
import data.MessageClackData;

import java.io.*;
import java.util.Scanner;

/**
 * The ClackClient class contains the following variables:
 * userName - String representing name of the client
 * hostName - String representing name of the computer representing the server
 * port - Integer representing port number on server connected to
 * closeConnection - boolean representing whether connection is closed or not
 * dataToSendToServer - ClackData object representing data sent to server
 * dataToReceiveFromServer - ClackData object representing data received from the server
 * inFromStd - Scanner object representing standard input
 */
public class ClackClient{
  //default port number
  final static int DEFAULT_PORT = 7000;
  final static String key = "follow me @xxiaie on twitter.com";

  private Scanner inFromStd;
  private String userName;
  private String hostName;
  private int port;
  private boolean closeConnection;
  private ClackData dataToSendToServer;
  private ClackData dataToReceiveFromServer;

  /**
   * Constructor for username, host name, and port, connection should be set to be open
   * Should set dataToSendToServer and dataToReceiveFromServer as null
   * @param userName sets the username of the user
   * @param hostName sets the hostname of the server
   * @param port sets the port to be connected to on the server
   */
  public ClackClient(String userName,String hostName,int port){
    if(userName == null) throw new IllegalArgumentException("Username cannot be null");
    if(hostName == null) throw new IllegalArgumentException("Hostname cannot be null");
    if(port < 1024) throw new IllegalArgumentException("Port must be 1024 or greater");
    this.userName = userName;
    this.hostName = hostName;
    this.port = port;
    this.closeConnection = false;
    this.dataToSendToServer = null;
    this.dataToReceiveFromServer = null;
  }

  /**
   * Constructor to set up port to default port number 7000
   * @param userName sets the username of the user
   * @param hostName sets the hostname of the server
   */
  public ClackClient(String userName,String hostName){
    this(userName,hostName,DEFAULT_PORT);
  }

  /**
   * Constructor that sets host name to be localhost
   * @param userName sets the username of the user
   */
  public ClackClient(String userName){
    this(userName,"localhost");
  }

  /**
   * Default constructor that sets anonymous user
   */
  public ClackClient(){
    this("Anon");
  }

  /**
   * Starts the connection, reads data from the client, and prints the data out.
   */
  public void start(){
    inFromStd = new Scanner(System.in);
    while(!closeConnection){
      readClientData();
      dataToReceiveFromServer = dataToSendToServer;
      printData();
    }
  }

  /**
   * Reads the data from the client
   */
  public void readClientData(){
    String userInput = inFromStd.next();
    if(userInput.equals("DONE")){
      closeConnection = true;
    } else if (userInput.equals("SENDFILE")) {
      String fileName = inFromStd.next();
      dataToSendToServer = new FileClackData(userName, fileName, ClackData.CONSTANT_SENDFILE);
      try{
        File file = new File(fileName);
        Scanner scanFile = new Scanner(file);
        if(scanFile.hasNext()) scanFile.next();
        scanFile.close();
      } catch(IOException ioe) {
        dataToSendToServer = null;
        System.err.println("Error reading the file");
      }
    } else if (userInput.equals("LISTUSERS")){
      System.err.println("Cannot test LISTUSERS");
    } else {
      dataToSendToServer = new MessageClackData(userName, "", ClackData.CONSTANT_SENDMESSAGE);
    }
  }

  /**
   * Does not return anything
   * For now it should have no code, just a declaration
   */
  public void sendClientData(){}

  /**
   * Does not return anything
   * For now it should have no code, just a declaration
   */
  public void sendData(){}

  /**
   * Does not return anything
   * For now it should have no code, just a declaration
   */
  public void receiveData(){}

  /**
   * Prints the received data to standard output
   */
  public void printData(){
    System.out.println("The username is: " + dataToReceiveFromServer.getUserName());
    System.out.println("The data is: " + dataToReceiveFromServer.getData());
    System.out.println("The data received is " + dataToReceiveFromServer.getData(key));
  }

  /**
   * Returns the username
   * @return username of the user
   */
  public String getUserName(){
    return this.userName;
  }

  /**
   * Returns the hostname
   * @return hostname of the server
   */
  public String getHostName(){
    return this.hostName;
  }

  /**
   * Returns the port
   * @return port of the server
   */
  public int getPort(){
    return this.port;
  }

  @Override
  public int hashCode(){
    return this.toString().hashCode();
  }

  @Override
  public boolean equals(Object other){
    if(!(other instanceof ClackClient)) return false;
    ClackClient otherClackClient = (ClackClient)other;
    return this.userName.equals(otherClackClient.userName) &&
            this.hostName.equals(otherClackClient.hostName) &&
            this.port == otherClackClient.port &&
            this.closeConnection == otherClackClient.closeConnection &&
            this.dataToSendToServer.equals(otherClackClient.dataToSendToServer) &&
            this.dataToReceiveFromServer.equals(otherClackClient.dataToReceiveFromServer);
  }

  @Override
  public String toString(){
    String connection = this.closeConnection ? "closed" : "open";
    return "The username is: " + this.userName + "\n" +
            "The hostname is: " + this.hostName + "\n" +
            "The port number is: " + this.port + "\n" +
            "The connection is " + connection;
  }

}