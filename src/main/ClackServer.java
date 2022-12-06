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
  private ArrayList<String> userList;

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
    this.userList = new ArrayList<>();
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
        serverSideClientIOList.add(newClient);
        Thread thread = new Thread(newClient);
        thread.start();
      }
      serverSocket.close();
    } catch (UnknownHostException uhe){
      System.err.println("Unknown host: " + uhe.getMessage() );
    } catch (NoRouteToHostException nrhe){
      System.err.println("Server unreachable: " + nrhe.getMessage() );
    } catch (ConnectException ce) {
      System.err.println("Connection refused: " + ce.getMessage() );
    } catch(IOException ioe){
      System.err.println("IO Exception occurred: " + ioe.getMessage() );
    }
  }

  /**
   * Takes in ClackData object 'dataToBroadcastToClients’, and does not return anything
   * @param dataToBroadcastToClients data to send to all clients on server
   */
  public synchronized void broadcast(ClackData dataToBroadcastToClients){
    for (ServerSideClientIO client : serverSideClientIOList) {
      client.setDataToSendToClient(dataToBroadcastToClients);
      client.sendData();
    }
  }

  /**
   * Takes in ClackData object 'dataToBroadcastToClients’ and int 'clientPort', and does not return anything
   * @param dataToBroadcastToClient data to send to specific client
   */
  public synchronized void broadcastTo(ClackData dataToBroadcastToClient){
    for(ServerSideClientIO client : serverSideClientIOList){
      if( dataToBroadcastToClient.equals( client.getData() ) ){
        dataToBroadcastToClient = new MessageClackData(dataToBroadcastToClient.getUserName(), getUserList(),
                ClackData.CONSTANT_LISTUSERS);
        client.setDataToSendToClient(dataToBroadcastToClient);
        client.sendData();
        break;
      }
    }
  }

  /**
   * Does not return anything, takes in a ServerSideClientIO object ‘serverSideClientToRemove’.
   * @param serverSideClientToRemove client to remove from server
   */
  public synchronized void remove(ServerSideClientIO serverSideClientToRemove){
    serverSideClientIOList.remove(serverSideClientToRemove);
  }

  /**
   * Adds a user to the userList
   * @param username username of user to add
   */
  public synchronized void addUser(String username){
    this.userList.add(username);
  }

  /**
   * Removes a user from the userList
   * @param username username of user to remove
   */
  public synchronized void removeUser(String username){ this.userList.remove(username); }

  /**
   * Returns the port
   * @return port of the server
   */
  public int getPort(){
    return this.port;
  }

  /**
   * Returns the list of users as a string
   * @return list of users
   */
  public String getUserList() {
    StringBuilder stringBuilder = new StringBuilder(20);
    for(String user : userList)
      stringBuilder.append(user).append('\n');
    stringBuilder.deleteCharAt(stringBuilder.length()-1);
    return "User List:\n" + stringBuilder;
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
      System.err.println("Expected type int: " + nfe.getMessage() );
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