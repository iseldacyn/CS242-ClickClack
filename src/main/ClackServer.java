package main;

import data.ClackData;
import java.io.*;
import java.net.*;
import java.util.InputMismatchException;

/**
 * The ClackServer class contains the following variables:
 * port - Integer representing port number on server connected to
 * closeConnection - boolean representing whether connection is closed or not
 * dataToReceiveFromClient - ClackData object representing data received from the client
 * dataToSendToClient - ClackData object representing data sent to the client
 * inFromClient - ObjectInputStream to receive information from client
 * outToClient - ObjectOutputStream to send information to client
 */
public class ClackServer{
  //default port number
  final static int DEFAULT_PORT = 7000;

  private int port;
  private boolean closeConnection;
  private ClackData dataToReceiveFromClient;
  private ClackData dataToSendToClient;
  private ObjectInputStream inFromClient;
  private ObjectOutputStream outToClient;

  /**
   * Constructor that sets port number
   * Should set dataToReceiveFromClient and dataToSendToClient as null
   * inFromClient and outToClient also set to null
   * @param port sets the port to be connected to on the server
   */
  public ClackServer(int port){
    if(port < 1024)
      throw new IllegalArgumentException("Port number must be greater than 1024");
    this.port = port;
    this.closeConnection = false;
    this.dataToReceiveFromClient = null;
    this.dataToSendToClient = null;
    this.inFromClient = null;
    this.outToClient = null;
  }

  /**
   * Default constructor that sets port to default port number 7000
   */
  public ClackServer(){
    this(DEFAULT_PORT);
  }

  /**
   * Does not return anything, gets connections from the client and echoes client data
   */
  public void start(){
    try {
      ServerSocket sskt = new ServerSocket(this.port);
      Socket clientskt = sskt.accept();
      this.inFromClient = new ObjectInputStream( clientskt.getInputStream() );
      this.outToClient = new ObjectOutputStream( clientskt.getOutputStream() );
      while(!this.closeConnection){
        receiveData();
        this.dataToSendToClient = this.dataToReceiveFromClient;
        sendData();
      }
      this.inFromClient.close();
      this.outToClient.close();
      sskt.close();
      clientskt.close();
    } catch (UnknownHostException uhe){
      System.err.println("Unknown host.");
    } catch (NoRouteToHostException nrhe){
      System.err.println("Server unreachable.");
    } catch (ConnectException ce) {
      System.err.println("Connection refused.");
    } catch(IOException ioe){
      System.err.println("IO Exception occurred");
    }
  }

  /**
   * Receives data from client, does not return anything
   */
  public void receiveData(){
    try{
      this.dataToReceiveFromClient = (ClackData)this.inFromClient.readObject();
      System.out.println( this.dataToReceiveFromClient );
      if( this.dataToReceiveFromClient.getData().equals("DONE") )
        this.closeConnection = true;
    } catch (ClassNotFoundException cnfe) {
      System.err.println("Class not found");
    } catch(IOException ioe) {
      System.err.println("IO exception occurred");
    }
  }

  /**
   * Sends data to client, does not return anything
   */
  public void sendData(){
    try{
      this.outToClient.writeObject( this.dataToSendToClient );
    } catch(IOException ioe) {
      System.err.println("IO exception occurred");
    }
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

  /**
   * Initializes a ClackServer object and connects to a client
   * @param args command line arguments
   */
  public static void main(String[] args){
    ClackServer clackServer;
    try{
      if(args.length == 0)
        clackServer = new ClackServer();
      else clackServer = new ClackServer( Integer.parseInt(args[0]) );
      clackServer.start();
    } catch (InputMismatchException ime){
      System.err.println("Expected type int");
    }
  }

}