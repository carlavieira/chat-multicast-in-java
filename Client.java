import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
	public static void main(String args[]) {
		
		// args  provê o nome identificador do usuário e o endereço  do grupo multicast (p. ex. "228.5.6.7")
		
		MulticastSocket mSocket = null;
    Scanner input = new Scanner(System.in);
		
		try {

      // Get group IP from args, the address of the network interface used for multicast packets
			InetAddress groupIp = InetAddress.getByName(args[1]);
			
			// Create a new MulticastSocket and bind it to a specific port (port = 6789).
      mSocket = new MulticastSocket(6789);

      // Join a multicast group using the group IP 
			mSocket.joinGroup(groupIp);

      // Print welcome message
      System.out.println("Bem-vindo(a) a sala de Bate-papo 01! \nPara sair digite \"Sair\".");

      //Get Client name from args
			String name = args[0];

      // Create check-in message
      Message message = new Message(name, "** Entrou no chat **");

      // Create ByteArrayOutputStream and ObjectOutputStream to send an Message object
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);

      //Wirte message in the ObjectOutputStream
      oos.writeObject(message);

      // Transform ByteArrayOutputStream data into ByteArray
      byte[] data = bos.toByteArray();

      // Constructs a DatagramPacket with the message  for sending to the specified port number (6789) on the specified host (IP).
			DatagramPacket messageOut = new DatagramPacket(data, data.length, groupIp, 6789);

      // Send the DatagramPacket
			mSocket.send(messageOut);

      // Create a new buffer with 1000 bytes
			byte[] buffer = new byte[1000];

      // Get messages from others in group
      Boolean leave = false;
			while (leave != true) {

        //Recive message from others
				DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
        mSocket.receive(messageIn);
        ObjectInputStream inputData = new ObjectInputStream(new ByteArrayInputStream(messageIn.getData()));
        Message newMessage = (Message) inputData.readObject();
				System.out.println(newMessage.getAuthor()+" : " +newMessage.getText());
        buffer = new byte[1000];

        // Get message from the client
        String userMessage = input.nextLine();

        // Check if is check-out
        if (userMessage.equals("Sair")){
          System.out.println("Você saiu do chat.");
          message = new Message(name, "** Saiu do chat **");
          leave = true;
        } else {
          message = new Message(name, userMessage);
        }

        // Send the message
        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        ObjectOutputStream oos2 = new ObjectOutputStream(bos2);
        oos2.writeObject(message);
        data = bos2.toByteArray();
        messageOut = new DatagramPacket(data, data.length, groupIp, 6789);
        mSocket.send(messageOut);
        }

			mSocket.leaveGroup(groupIp);

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
