import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
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
    private static void downloadFileFromURL(String urlString, File destination) {
        try {
            URL website = new URL(urlString);
            ReadableByteChannel rbc;
            rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(destination);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ChangeDesktopBackgroundOnWindows(String ruta) {
        User32.INSTANCE.SystemParametersInfo(0x0014, 0, ruta, 1);
    }

    private static File getHomeDir() {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        return fsv.getHomeDirectory();
    }

    private static BufferedImage grabScreen() {
        try {
            System.out.println("Снимаем скрин с экрана");
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "jpg", new File("screen.jpg"));
           } catch (Exception e) {

        }
        return null;
    }

    public static void main(String[] args) throws IOException {

        downloadFileFromURL("http://v.img.com.ua/b/600x500/7/c6/f261e6e5bb56d3564c8e49d4662d9c67.jpg", new File("1.jpg"));
        String ruta = "1.jpg";
        grabScreen();
        boolean f = Files.isRegularFile(Paths.get("screen.jpg"));
        String filepath = (f) ? "screen.jpg" : ruta;
        System.out.println(filepath);
        System.out.println(f);
        ChangeDesktopBackgroundOnWindows(filepath);
        System.out.println("Экран сменен ");

    }

}