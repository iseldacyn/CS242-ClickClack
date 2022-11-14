package main;

import data.ClackData;
import data.FileClackData;
import data.MessageClackData;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * The ClackClient class contains the following variables:
 * <ul>
 * <li>userName - String representing name of the client</li>
 * <li>hostName - String representing name of the computer representing the server</li>
 * <li>port - Integer representing port number on server connected to</li>
 * <li>closeConnection - boolean representing whether connection is closed or not</li>
 * <li>dataToSendToServer - ClackData object representing data sent to server</li>
 * <li>dataToReceiveFromServer - ClackData object representing data received from the server</li>
 * <li>inFromStd - Scanner object representing standard input</li>
 * </ul>
 * @author Iselda Aiello, Sydney DeCyllis
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
  private ObjectInputStream inFromServer;
  private ObjectOutputStream outToServer;

  /**
   * Constructor for username, host name, and port, connection should be set to be open <br>
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
    this.outToServer = null;
    this.inFromServer = null;
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

    try {
      Socket local = new Socket(hostName, port);
      local.setSoTimeout(400000);

      outToServer = new ObjectOutputStream(local.getOutputStream());
      inFromServer = new ObjectInputStream(local.getInputStream());

      //Reads user input into inFromStd
      this.inFromStd = new Scanner(System.in);
      while(!this.closeConnection){
        readClientData();
        this.dataToReceiveFromServer = this.dataToSendToServer;
        sendData();
        receiveData();
        printData();
      }
      local.close();
      outToServer.close();
      inFromServer.close();
      inFromStd.close();
    }
    catch(UnknownHostException uhe){
      System.out.println("Unknown host Exception");
    }
    catch(IOException ioe){
      System.out.println("IOException");
    }
    //this.inFromStd.close();
  }

  /**
   * Reads the data from the client
   */
  public void readClientData(){
    String userInput = this.inFromStd.next();
    if(userInput.equals("DONE")){
      this.closeConnection = true;
      this.dataToSendToServer = new MessageClackData(this.userName,userInput,ClackData.CONSTANT_SENDMESSAGE);
    } else if (userInput.equals("SENDFILE")) {
      String fileName = this.inFromStd.next();
      this.dataToSendToServer = new FileClackData(this.userName, fileName, ClackData.CONSTANT_SENDFILE);
      try{
        ((FileClackData) this.dataToSendToServer).readFileContents(key);
      } catch(IOException ioe) {
        this.dataToSendToServer = null;
        System.err.println("Error reading the file");
      }
    } else if (userInput.equals("LISTUSERS")){
      System.err.println("Cannot test LISTUSERS");
      System.exit(0);
    } else {
      this.dataToSendToServer = new MessageClackData(this.userName,userInput,key,ClackData.CONSTANT_SENDMESSAGE);
    }
  }

  /**
   * Does not return anything<br>
   * For now it should have no code, just a declaration
   */
  public void sendClientData(){}

  /**
   * Does not return anything<br>
   * For now it should have no code, just a declaration
   */
  public void sendData(){
    try{
      outToServer.writeObject(dataToSendToServer);
    }
    catch(IOException ioe){
      System.err.println("ObjectInputStream Exception");
    }


  }

  /**
   * Does not return anything<br>
   * For now it should have no code, just a declaration
   */
  public void receiveData(){
    try{
      dataToReceiveFromServer = (ClackData) inFromServer.readObject();
    }
    catch(ClassNotFoundException cfe){
      System.err.println("Class could not be found");
    }
    catch(IOException ioe){
      System.err.println("ObjectInputStream Exception");
    }

  }

  /**
   * Prints the received data to standard output
   */
  public void printData(){
    System.out.println("The username is: " + this.dataToReceiveFromServer.getUserName());
    System.out.println("The date is: " + this.dataToReceiveFromServer.getDate());
    System.out.println("The data received is: \"" + this.dataToReceiveFromServer.getData(key) + "\"");
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

  /**
   * Initializes a ClackClient object and connects to a server
   * @param args command line arguments
   */
  public static void main(String []args){
    String parse;
    ClackClient clackClient;
    try {
      if (args.length == 0) {
        parse = "";
        clackClient = new ClackClient();
      } else {
        parse = args[0];
        if (parse.contains("@")) {
          String[] title = parse.split("@");
          parse = title[0];
          if (title[1].contains(":")) {
            title = title[1].split(":");
            clackClient = new ClackClient(parse, title[0], Integer.parseInt(title[1]));
          } else {
            clackClient = new ClackClient(parse, title[1]);
          }
        } else {
          clackClient = new ClackClient(parse);
        }
      }
      clackClient.start();
    } catch(NumberFormatException nfe){
      System.err.println("Excepted type int after ':'");
    }
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