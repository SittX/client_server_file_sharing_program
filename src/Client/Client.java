package Client;

import Server.ImageConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//         Load external img format to internal img object
//         Serialize image
//            BufferedImage image = ImageIO.read(new File("Big o chart.png"));
//            byte[] bytes =  imageToByteArray(image);
//            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("SerializedImg.txt"));
//            output.writeObject(bytes);
//
////            Deserialize image
//            ObjectInputStream input = new ObjectInputStream(new FileInputStream("SerializedImg.txt"));
//            byte[] imageArray = (byte[]) input.readObject();
//
//            BufferedImage deserializedImg = byteArrayToImage(imageArray);
//            ImageIO.write(deserializedImg,"png",new File("DeserializedImage.png"));
        try (
                Socket socket = new Socket("localhost", 5000);
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        ) {
            System.out.println("Client program is started.");

            // Read data from InputStream and convert to BufferedImage
            byte[] imageArray = (byte[]) input.readObject();
            BufferedImage img = ImageConverter.byteArrayToImage(imageArray);

            // Write BufferedImage to external file
            ImageIO.write(img, "png", new File("ClientImage.png"));

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.err.println("UnknownHostException : " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
