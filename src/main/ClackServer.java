package main;

import data.ClackData;

/**
 * The ClackServer class contains the following variables:
 * port - Integer representing port number on server connected to
 * closeConnection - boolean representing whether connection is closed or not
 * dataToReceiveFromClient - ClackData object representing data received from the client
 * dataToSendToClient - ClackData object representing data sent to the client
 */
public class ClackServer{
  //default port number
  final static int DEFAULT_PORT = 7000;

  private int port;
  private boolean closeConnection;
  private ClackData dataToReceiveFromClient;
  private ClackData dataToSendToClient;

  /**
   * Constructor that sets port number
   * Should set dataToReceiveFromClient and dataToSendToClient as null
   * @param port sets the port to be connected to on the server
   */
  public ClackServer(int port){
    this.port = port;
    this.closeConnection = false;
    this.dataToReceiveFromClient = null;
    this.dataToSendToClient = null;
  }

  /**
   * Default constructor that sets port to default port number 7000
   */
  public ClackServer(){
    this(DEFAULT_PORT);
  }

  /**
   * Does not return anything
   * For now it should have no code, just a declaration
   */
  public void start(){}

  /**
   * Does not return anything
   * For now it should have no code, just a declaration
   */
  public void receiveData(){}

  /**
   * Does not return anything
   * For now it should have no code, just a declaration
   */
  public void sendData(){}

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
    if(!(other instanceof ClackServer)) return false;
    ClackServer otherClackServer = (ClackServer)other;
    return this.port == otherClackServer.port &&
            this.closeConnection == otherClackServer.closeConnection &&
            this.dataToReceiveFromClient.equals(otherClackServer.dataToReceiveFromClient) &&
            this.dataToSendToClient.equals(otherClackServer.dataToSendToClient);
  }

  @Override
  public String toString(){
    String connection = closeConnection ? "closed" : "open";
    return "The port number is: " + this.port + "\n" +
            "The connection is " + connection;
  }

}