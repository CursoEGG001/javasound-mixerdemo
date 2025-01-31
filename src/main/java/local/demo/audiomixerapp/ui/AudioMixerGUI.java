/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.demo.audiomixerapp.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import local.demo.audiomixerapp.audio.FileAudioSource;
import local.demo.audiomixerapp.audio.MultiTrackMixer;
import local.demo.audiomixerapp.io.AudioFileChooser;

/**
 *
 * @author pc
 */
public class AudioMixerGUI extends JFrame {

    private final MultiTrackMixer mixer;
    private JPanel trackPanel;

    public AudioMixerGUI() {
        mixer = new MultiTrackMixer();
        setupGUI();
    }

    private void setupGUI() {
        setTitle("Mezclador de Audio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crea Panel principal
        trackPanel = new JPanel();
        trackPanel.setLayout(new BoxLayout(trackPanel, BoxLayout.Y_AXIS));

        JPanel controlPanel = new JPanel();
        JButton addTrackButton = new JButton("Suma Pista");
        JButton playAllButton = new JButton("Escuchar todas");
        JButton stopAllButton = new JButton("Detener todas");

        controlPanel.add(addTrackButton);
        controlPanel.add(playAllButton);
        controlPanel.add(stopAllButton);

        // Agrega escucha de acciones
        addTrackButton.addActionListener(e -> addNewTrack());
        playAllButton.addActionListener(e -> mixer.mixAudio());
        stopAllButton.addActionListener(e -> mixer.stopAll());

        // Agrega paneles al marco
        add(new JScrollPane(trackPanel), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private void addNewTrack() {
        AudioFileChooser selFile = new AudioFileChooser();
        try {
            File audioFile = selFile.getAudioFile(this);
            if (audioFile == null) {
                return; // Usuario canceló la selección.
            }

            System.out.println("Intentando cargar archivo de audio: " + audioFile.getAbsolutePath());
            FileAudioSource source = new FileAudioSource(audioFile);
            mixer.addSource(source);

            // Crea panel de controles de la pista
            JPanel trackControls = new JPanel();
            trackControls.setLayout(new FlowLayout(FlowLayout.LEFT));

            JButton playButton = new JButton("Escuchar");
            JButton stopButton = new JButton("Parar");
            JSlider volumeSlider = new JSlider(0, 100, 100);

            playButton.addActionListener(e -> source.play());
            stopButton.addActionListener(e -> source.stop());
            volumeSlider.addChangeListener(e -> source.setVolume(volumeSlider.getValue() / 100.0f));

            trackControls.add(playButton);
            trackControls.add(stopButton);
            trackControls.add(volumeSlider);

            JLabel trackLabel = new JLabel(audioFile.getName());
            trackControls.add(trackLabel);

            // Agrega botón de remover la pista
            JButton closeButton = new JButton("Sacar");
            closeButton.addActionListener(e -> {
                // Saca la pista del mezclador
                mixer.removeSource(source);

                // Saca el panel de la pista del contenedor
                trackPanel.remove(trackControls);
                trackPanel.revalidate();
                trackPanel.repaint();
            });
            trackControls.add(closeButton);

            trackPanel.add(trackControls);
            trackPanel.revalidate();
            trackPanel.repaint();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            JOptionPane.showMessageDialog(this,
                    "Error adding track: " + e.getClass().getSimpleName() + "\n"
                    + "Message: " + e.getMessage(),
                    "Error Loading Audio",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
