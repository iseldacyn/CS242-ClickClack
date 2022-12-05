package main;

import data.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * The ClackServer class contains the following variables:
 * <ul>
 * <li>port - Integer representing port number on server connected to</li>
 * <li>closeConnection - boolean representing whether connection is closed or not</li>
 * <li>serverSideClientIOList  - an ArrayList consisting of ServerSideClientIO objects</li>
 * </ul>
 * @author Iselda Aiello
 */
public class ClackServer{
  //default port number
  final static int DEFAULT_PORT = 7000;

  private int port;
  private boolean closeConnection;
  private ArrayList<ServerSideClientIO> serverSideClientIOList;

  /**
   * Constructor that sets port number<br>
   * Should set dataToReceiveFromClient and dataToSendToClient as null
   * @param port sets the port to be connected to on the server
   */
  public ClackServer(int port){
    if(port < 1024)
      throw new IllegalArgumentException("Port number must be greater than 1024");
    this.port = port;
    this.closeConnection = false;
    this.serverSideClientIOList = new ArrayList<>();
  }

  /**
   * Default constructor that sets port to default port number 7000
   */
  public ClackServer(){
    this(DEFAULT_PORT);
  }

  /**
   * Does not return anything, creates and runs threads wrapped around ServerSideClientIO Runnable objects
   */
  public void start(){
    try {
      ServerSocket serverSocket = new ServerSocket(this.port);
      while (!closeConnection) {
        Socket newClientSocket = serverSocket.accept();
        ServerSideClientIO newClient = new ServerSideClientIO(this, newClientSocket);
        Thread thread = new Thread(newClient);
        thread.start();
      }
      serverSocket.close();
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
   * Takes in ClackData object 'dataToBroadcastToClients’, and does not return anything.
   */
  public synchronized void broadcast(ClackData dataToBroadcastToClients){
    for(ServerSideClientIO client : serverSideClientIOList){
      client.setDataToSendToClient(dataToBroadcastToClients);
      client.sendData();
    }
  }

  /**
   * Does not return anything, takes in a ServerSideClientIO object ‘serverSideClientToRemove’.
   */
  public synchronized void remove(ServerSideClientIO serverSideClientToRemove){ serverSideClientIOList.remove(serverSideClientToRemove); }

  /**
   * Returns the port
   * @return port of the server
   */
  public int getPort(){
    return this.port;
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
    } catch (NumberFormatException nfe){
      System.err.println("Expected type int");
    }
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
            this.serverSideClientIOList.equals(otherClackServer.serverSideClientIOList);
  }

  @Override
  public String toString(){
    String connection = closeConnection ? "closed" : "open";
    return "The port number is: " + this.port + "\n" +
            "The connection is " + connection;
  }

}