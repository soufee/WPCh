package com;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class WallpaperChanger {
    public static interface User32 extends Library {
        User32 INSTANCE = (User32) Native.loadLibrary("user32",User32.class, W32APIOptions.DEFAULT_OPTIONS);
        boolean SystemParametersInfo (int one, int two, String s ,int three);
    }

    public static void downloadFileFromURL(String urlString, File destination) {
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

    public static void ChangeDesktopBackgroundOnWindows(String ruta){
        User32.INSTANCE.SystemParametersInfo(0x0014, 0, ruta , 1);
    }

//http://uraldaily.ru/sites/default/files/1325077714.jpg
    public static void main(String[] args) {

        downloadFileFromURL("http://uraldaily.ru/sites/default/files/1325077714.jpg", new File("1.jpg"));
        String ruta="1.jpg";
            ChangeDesktopBackgroundOnWindows(ruta);
               try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}