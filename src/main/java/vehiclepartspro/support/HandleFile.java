package vehiclepartspro.support;

import org.springframework.web.multipart.MultipartFile;
import vehiclepartspro.Strings;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HandleFile
{
    public static void uploadFile(MultipartFile file, int productId) throws IOException
    {
        if (file.isEmpty())
        {
            throw new FileNotFoundException();
        }

        File uploadFile = new File(Strings.imageProductDirPath+"/"+productId+".png");
        file.transferTo(uploadFile);
        //handleResizeImage(uploadFile);

    }

    /**
    private static void handleResizeImage(File file) throws IOException {

        BufferedImage inputImage = ImageIO.read(file);

        // Dimensioni desiderate
        int scaledWidth = 800;
        int scaledHeight = 600;

        // Ridimensiona l'immagine
        BufferedImage outputImage = resizeImage(inputImage, scaledWidth, scaledHeight);

        // Percorso dell'immagine di output
        File outputFile = new File(file.getPath());
        ImageIO.write(outputImage, "jpg", outputFile);

    }
    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height)
    {
        Image resultingImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();

        return outputImage;
    }
    */


    public static void deleteImageProduct(int productId)
    {
        File file = new File(Strings.imageProductDirPath+"/"+productId+".png");
        if(file.exists())
        {
            file.delete();
        }
    }
}
