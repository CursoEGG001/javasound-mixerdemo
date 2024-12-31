/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.demo.audiomixerapp.audio;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import local.demo.audiomixerapp.core.AudioSource;

/**
 *
 * @author pc
 */
public class FileAudioSource implements AudioSource {

    private final Clip clip;
    private boolean isPlaying;

    public FileAudioSource(File audioFile) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        super();
        try {
            AudioInputStream auStreamSelection = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(auStreamSelection);
        } catch (UnsupportedAudioFileException ex) {
            System.err.println("Error: Unsupported audio format");
            System.err.println("Details: " + ex.getMessage());
            throw ex;
        } catch (IOException ex) {
            System.err.println("Error: Cannot read audio file");
            System.err.println("File path: " + audioFile.getAbsolutePath());
            System.err.println("Details: " + ex.getMessage());
            throw ex;
        } catch (LineUnavailableException ex) {
            System.err.println("Error: Audio line unavailable");
            System.err.println("Details: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void play() {
        if (!isPlaying) {
            clip.start();
            isPlaying = true;
        }
    }

    @Override
    public void stop() {
        if (isPlaying) {
            clip.stop();
            clip.setFramePosition(0);
            isPlaying = false;
        }
    }

    @Override
    public void setVolume(float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(
                FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }

    @Override
    public boolean isPlaying() {
        return clip.isRunning();
    }

}
