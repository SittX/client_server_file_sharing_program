package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        // While loop to keep the server listening to the
        // incoming requests after the clients disconnected.

        while(true) {
            try {
                System.out.println("Server listening on port 5000.");
                serverSocket = new ServerSocket(5000);

                // Create new sockets for every incoming request
                while(true){
                    Socket socket = serverSocket.accept();
                    System.out.println("A new socket is created at port : " + socket.getPort() );
                     new Thread(()->{
                        ObjectOutputStream output = null;
                        try {
                            // Get OutputStream of the socket
                             output = new ObjectOutputStream(socket.getOutputStream());

                            // Deserialized Image and send to OutputStream
                            byte[] imgData = ImageConverter.imageToByteArray("Big o chart.png");
                            output.writeObject(imgData);
                            output.flush();

                            System.out.println("Data sent to the client.");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        finally {
                           if(output != null && socket != null){
                               try {
                                   socket.close();
                                   output.close();
                               } catch (IOException e) {
                                   e.printStackTrace();
                                   System.err.println(e.getMessage());
                               }
                           }
                        }
                    }).start();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                if(serverSocket != null){
                    serverSocket.close();
                }
            }
        }
    }


}
