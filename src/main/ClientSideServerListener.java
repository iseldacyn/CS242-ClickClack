package main;

/**
 * The ClientSideServerListener class contains the following variables:
 * <ul>
 *   <li>client - ClackClient instance variable representing a client that calls this class to make a threaded Runnable object.</li>
 * </ul>
 * @author Sydney DeCyllis, Iselda Aiello
 */
public class ClientSideServerListener implements Runnable {
  ClackClient client;

  /**
   * Constructor that takes in a ClackClient object ‘client’ as a parameter.
   * @param client sets the ClackClient object to run
   */
  ClientSideServerListener(ClackClient client){ this.client = client; }

  @Override
  public void run(){
    while( !client.getCloseConnection() ){
      client.receiveData();
      client.printData();
    }
  }

}
