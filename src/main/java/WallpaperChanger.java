import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WallpaperChanger {
    private static final String sourceFile = "screen.jpg";
    private static final String sourceLink = "http://v.img.com.ua/b/600x500/7/c6/f261e6e5bb56d3564c8e49d4662d9c67.jpg";
    private static User32 u;

    private static void downloadFileFromURL(String urlString, File destination) {
        try (FileOutputStream fos = new FileOutputStream(destination)) {
            URL website = new URL(urlString);
            ReadableByteChannel rbc;
            rbc = Channels.newChannel(website.openStream());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            rbc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ChangeDesktopBackgroundOnWindows(String ruta) throws ClassNotFoundException {
        u = User32.INSTANCE;
        u.SystemParametersInfo(0x0014, 0, ruta, 1);
    }

    private static void grabScreen() {
        try {
            System.out.println("screening...");
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "jpg", new File(sourceFile));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        grabScreen();
        boolean f = Files.isRegularFile(Paths.get("screen.jpg"));
        String ruta = "";
        if (!f) {
            downloadFileFromURL(sourceLink, new File(sourceFile));
            ruta = "1.jpg";
        }
        String filepath = (f) ? "screen.jpg" : ruta;
        new WallpaperChanger().ChangeDesktopBackgroundOnWindows(filepath);

    }

}