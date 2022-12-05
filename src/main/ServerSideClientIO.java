package main;

import data.*;
import java.io.*;
import java.net.*;

/**
 * The ServerSideClientIO class contains the following variables:
 * <ul>
 * <li>closeConnection - boolean representing whether connection is closed or not</li>
 * <li>dataToReceiveFromClient - ClackData object representing data received from the client</li>
 * <li>dataToSendToClient - ClackData object representing data sent to the client</li>
 * <li>inFromClient - ObjectInputStream to receive information from client</li>
 * <li>outToClient - ObjectOutputStream to send information to client</li>
 * <li>server - ClackServer object representing the master server</li>
 * <li>clientSocket - Socket object representing the socket accepted from the client</li>
 * </ul>
 * @author Iselda Aiello
 */
public class ServerSideClientIO implements Runnable{
  private boolean closeConnection;
  private ClackData dataToReceiveFromClient;
  private ClackData dataToSendToClient;
  private ObjectInputStream inFromClient;
  private ObjectOutputStream outToClient;
  private ClackServer server;
  private Socket clientSocket;

  /**
   * Constructor that takes ClackServer object and Socket object as parameters
   * @param server sets the ClackServer object to run
   * @param clientSocket sets the Socket object of the client
   */
  ServerSideClientIO(ClackServer server, Socket clientSocket){
    this.server = server;
    this.clientSocket = clientSocket;
    this.closeConnection = false;
    this.dataToReceiveFromClient = null;
    this.dataToSendToClient = null;
    this.inFromClient = null;
    this.outToClient = null;
  }

  @Override
  public void run() {
    try {
      this.inFromClient = new ObjectInputStream(this.clientSocket.getInputStream());
      this.outToClient = new ObjectOutputStream(this.clientSocket.getOutputStream());
      while (!this.closeConnection) {
        receiveData();
        this.server.broadcast(dataToReceiveFromClient);
        sendData();
      }
      this.inFromClient.close();
      this.outToClient.close();
      this.clientSocket.close();
    } catch (UnknownHostException uhe) {
      System.err.println("Unknown host.");
    } catch (NoRouteToHostException nrhe) {
      System.err.println("Server unreachable.");
    } catch (ConnectException ce) {
      System.err.println("Connection refused.");
    } catch (IOException ioe) {
      System.err.println("IO Exception occurred");
    }
  }

  /**
   * Does not return anything, receives data from the client
   */
  public void receiveData(){
    try{
      this.dataToReceiveFromClient = (ClackData)this.inFromClient.readObject();
      System.out.println( this.dataToReceiveFromClient );
      if( this.dataToReceiveFromClient.getData().equals("DONE") ) {
        this.closeConnection = true;
        this.server.remove(this);
      }
    } catch (ClassNotFoundException cnfe) {
      System.err.println("Class not found");
    } catch(IOException ioe) {
      this.closeConnection = true;
      System.err.println("IO exception occurred" + ioe);
    }
  }


  /**
   * Does not return anything, sends data to the client
   */
  public void sendData(){
    try{
      this.outToClient.writeObject( this.dataToSendToClient );
    } catch(IOException ioe) {
      System.err.println("IO exception occurred");
    }
  }

  /**
   * Mutator method to set the ClackData variable dataToSendToClient
   */
  public void setDataToSendToClient(ClackData dataToSendToClient){ this.dataToSendToClient = dataToSendToClient; }

}
