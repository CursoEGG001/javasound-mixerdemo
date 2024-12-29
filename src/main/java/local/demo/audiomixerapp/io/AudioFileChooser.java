/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.demo.audiomixerapp.io;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author pc
 */

public class AudioFileChooser {

    private JFileChooser fileChooser;
    private static final String[] SUPPORTED_FORMATS = {"wav", "au", "aiff"};

    public AudioFileChooser() {
        initializeFileChooser();
    }

    private void initializeFileChooser() {
        fileChooser = new JFileChooser();
        // Set filter for audio files
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Audio Files", SUPPORTED_FORMATS);
        fileChooser.setFileFilter(filter);

        // Start in user's music folder if possible
        String musicFolderPath = System.getProperty("user.home") + File.separator + "Music";
        File musicFolder = new File(musicFolderPath);
        if (musicFolder.exists()) {
            fileChooser.setCurrentDirectory(musicFolder);
        }
    }

    public File getAudioFile(Component parent) {
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
