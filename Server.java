import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	public static void main(String args[]) {
		
		// args  provê o endereço  do grupo multicast (p. ex. "228.5.6.7")

    System.out.println("Inicializando Chat de Multicast...");
		
		MulticastSocket mSocket = null;
		
		try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);

      // Get group IP from args, the address of the network interface used for multicast packets
			InetAddress groupIp = InetAddress.getByName(args[0]);
			
			// Create a new MulticastSocket and bind it to a specific port (port = 6789).
      mSocket = new MulticastSocket(6789);

      // Join a multicast group using the group IP 
			mSocket.joinGroup(groupIp);
			
      // Get welcome message from the server and transform it into ByteArray
      String name = "Server";
      Message message = new Message(name, "Bem vindo a Sala de Bate-papo 01!");
			oos.writeObject(message);
      byte[] data = bos.toByteArray();

      // Constructs a DatagramPacket with the message  for sending to the specified port number (6789) on the specified host (IP).
			DatagramPacket messageOut = new DatagramPacket(data, data.length, groupIp, 6789);

      // Send the DatagramPacket
			mSocket.send(messageOut);

      //Create participants list
      List<String> participants = new ArrayList<String>();

      // Create a new buffer with 1000 bytes
			byte[] buffer = new byte[1000];

			while (true) {

        // Get messages from clients
				DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
        mSocket.receive(messageIn);
        ObjectInputStream inputObject = new ObjectInputStream(new ByteArrayInputStream(messageIn.getData()));
        Message newMessage = (Message) inputObject.readObject();
				System.out.println(newMessage.getAuthor()+" : " +newMessage.getText());

        // Check if it is a check-in
        if (newMessage.getText().equals("** Entrou no chat **")){
          participants.add(newMessage.getAuthor());
          System.out.println("Lista de Participantes: "+participants);

        // Check if it is a check-out
        } else if (newMessage.getText().equals("** Saiu do chat **")){
          participants.remove(newMessage.getAuthor());
          System.out.println("Lista de Participantes: "+participants);
        }

        // Clean buffer
        buffer = new byte[1000];
			}

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFound: " + e.getMessage());
		} finally {
			if (mSocket != null)
				mSocket.close();
		}
	}

}
