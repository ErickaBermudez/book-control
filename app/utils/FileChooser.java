package utils;

import com.thoughtworks.xstream.io.path.Path;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * @author dsi
 */
public class FileChooser {

    public static String getRute() {
        String rute = "";

        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
        fileChooser.setDialogTitle("Choose a directory to save your file: ");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if(fileChooser.getSelectedFile().isDirectory()){
                System.out.println("DIRECTORY SELECTED: " + fileChooser.getSelectedFile());
                rute = (""+fileChooser.getSelectedFile());
            }
        }
        return rute;
    }
}
