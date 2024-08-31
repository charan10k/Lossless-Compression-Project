import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.*;
import javax.imageio.stream.*;

public class Compresssion {

  public static void main(String[] args) {
    if (args.length != 3) {
      System.out.println("Usage: java Compresssion <input_file> <output_file> <compression_quality>");
      return;
    }

    String inputFile = args[0];
    String outputFile = args[1];
    float compressionQuality = Float.parseFloat(args[2]);

    try {
      compressImage(inputFile, outputFile, compressionQuality);
    } catch (IOException e) {
      System.err.println("Error compressing image: " + e.getMessage());
    }
  }

  public static void compressImage(String inputFile, String outputFile, float compressionQuality) throws IOException {
    File input = new File(inputFile);
    BufferedImage image = ImageIO.read(input);

    File compressedImageFile = new File(outputFile);
    OutputStream os = new FileOutputStream(compressedImageFile);

    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
    ImageWriter writer = (ImageWriter) writers.next();

    ImageOutputStream ios = ImageIO.createImageOutputStream(os);
    writer.setOutput(ios);

    ImageWriteParam param = writer.getDefaultWriteParam();

    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    param.setCompressionQuality(compressionQuality);  // Use the provided compression quality value
    writer.write(null, new IIOImage(image, null, null), param);

    os.close();
    ios.close();
    writer.dispose();
  }
}
