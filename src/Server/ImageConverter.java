package Server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageConverter {
    public static byte[] imageToByteArray(String filePath) throws IOException {
        String imageFormat = "";
        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            imageFormat = filePath.substring(i + 1);
        }else{
            throw new FileNotFoundException("Invalid file format.");
        }

        File imgURL = new File(filePath);
        if (!imgURL.exists()) {
            throw new FileNotFoundException("File is not found in the path " + filePath);
        } else {
            BufferedImage image = ImageIO.read(imgURL);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(image, imageFormat, output);
            return output.toByteArray();
        }
    }

    public static BufferedImage byteArrayToImage(byte[] byteArray) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(byteArray));
    }
}
