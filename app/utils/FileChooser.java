package utils;

import com.thoughtworks.xstream.io.path.Path;
import java.io.File;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * @author dsi
 */
public class FileChooser {

    public static String getPath() {
        String path = "";

        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
        fileChooser.setDialogTitle("Choose a directory to save your file: ");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if(fileChooser.getSelectedFile().isDirectory()){
                System.out.println("DIRECTORY SELECTED: " + fileChooser.getSelectedFile());
                path = (""+fileChooser.getSelectedFile());
            }
        }
        return path;
    }
    
    public static String getFilePath(){
        String path = "";
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = jfc.showOpenDialog(null);
            
            if(returnValue == JFileChooser.APPROVE_OPTION){
                File selectedFile = jfc.getSelectedFile();
                path = selectedFile.getAbsolutePath();
            }
            
        return path; 
    }
}
