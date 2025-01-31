/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package local.demo.audiomixerapp;

import javax.swing.SwingUtilities;
import local.demo.audiomixerapp.ui.AudioMixerGUI;

/**
 *
 * @author pc
 */
//// Abstract base class for audio sources
//abstract class AudioSource {
//
//    protected AudioInputStream audioStream;
//    protected float volume;
//    protected boolean isPlaying;
//
//    public AudioSource() {
//        this.volume = 1.0f;
//        this.isPlaying = false;
//    }
//
//    abstract void play();
//
//    abstract void stop();
//
//    abstract void setVolume(float volume);
//
//    public boolean isPlaying() {
//        return isPlaying;
//    }
//}
//
//// Concrete implementation for file-based audio
//class FileAudioSource extends AudioSource {
//
//    private final File audioFile;
//    private final Clip clip;
//
//    public FileAudioSource(File audioFile) throws Exception {
//        super();
//        this.audioFile = audioFile;
//        try {
//            AudioInputStream auStreamSelection = AudioSystem.getAudioInputStream(audioFile);
//            clip = AudioSystem.getClip();
//            clip.open(auStreamSelection);
//        } catch (UnsupportedAudioFileException ex) {
//            System.err.println("Error: Unsupported audio format");
//            System.err.println("Details: " + ex.getMessage());
//            throw ex;
//        } catch (IOException ex) {
//            System.err.println("Error: Cannot read audio file");
//            System.err.println("File path: " + audioFile.getAbsolutePath());
//            System.err.println("Details: " + ex.getMessage());
//            throw ex;
//        } catch (LineUnavailableException ex) {
//            System.err.println("Error: Audio line unavailable");
//            System.err.println("Details: " + ex.getMessage());
//            throw ex;
//        }
//    }
//
//    @Override
//    public void play() {
//        if (!isPlaying) {
//            clip.start();
//            isPlaying = true;
//        }
//    }
//
//    @Override
//    public void stop() {
//        if (isPlaying) {
//            clip.stop();
//            clip.setFramePosition(0);
//            isPlaying = false;
//        }
//    }
//
//    @Override
//    public void setVolume(float volume) {
//        FloatControl gainControl = (FloatControl) clip.getControl(
//                FloatControl.Type.MASTER_GAIN);
//        float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
//        gainControl.setValue(dB);
//        this.volume = volume;
//    }
//}
//
//// Interface for audio mixing operations
//interface AudioMixer {
//
//    void addSource(AudioSource source);
//
//    void removeSource(AudioSource source);
//
//    void mixAudio();
//}
//
//// Implementation of the AudioMixer interface
//class MultiTrackMixer implements AudioMixer {
//
//    private List<AudioSource> sources;
//
//    public MultiTrackMixer() {
//        sources = new ArrayList<>();
//    }
//
//    @Override
//    public void addSource(AudioSource source) {
//        sources.add(source);
//    }
//
//    @Override
//    public void removeSource(AudioSource source) {
//        sources.remove(source);
//        source.stop();
//    }
//
//    @Override
//    public void mixAudio() {
//        for (AudioSource source : sources) {
//            source.play();
//        }
//    }
//
//    public void stopAll() {
//        for (AudioSource source : sources) {
//            source.stop();
//        }
//    }
//
//    public List<AudioSource> getSources() {
//        return sources;
//    }
//}
//
//// Main application window
//class AudioMixerGUI extends JFrame {
//
//    private final MultiTrackMixer mixer;
//    private JPanel trackPanel;
//
//    public AudioMixerGUI() {
//        mixer = new MultiTrackMixer();
//        setupGUI();
//    }
//
//    private void setupGUI() {
//        setTitle("Audio Mixer");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Create main panels
//        trackPanel = new JPanel();
//        trackPanel.setLayout(new BoxLayout(trackPanel, BoxLayout.Y_AXIS));
//
//        JPanel controlPanel = new JPanel();
//        JButton addTrackButton = new JButton("Suma Pista");
//        JButton playAllButton = new JButton("Escuchar todas");
//        JButton stopAllButton = new JButton("Detener todas");
//
//        controlPanel.add(addTrackButton);
//        controlPanel.add(playAllButton);
//        controlPanel.add(stopAllButton);
//
//        // Add action listeners
//        addTrackButton.addActionListener(e -> addNewTrack());
//        playAllButton.addActionListener(e -> mixer.mixAudio());
//        stopAllButton.addActionListener(e -> mixer.stopAll());
//
//        // Add panels to frame
//        add(new JScrollPane(trackPanel), BorderLayout.CENTER);
//        add(controlPanel, BorderLayout.SOUTH);
//
//        setSize(400, 300);
//        setLocationRelativeTo(null);
//    }
//
//private void addNewTrack() {
//        AudioFileChooser selFile = new AudioFileChooser();
//        try {
//            File audioFile = selFile.getAudioFile(this);
//            if (audioFile == null) {
//                return; // Usuario canceló la selección.
//            }
//
//            System.out.println("Intentando cargar archivo de audio: " + audioFile.getAbsolutePath());
//            FileAudioSource source = new FileAudioSource(audioFile);
//            mixer.addSource(source);
//
//            // Crea panel de controles de la pista
//            JPanel trackControls = new JPanel();
//            trackControls.setLayout(new FlowLayout(FlowLayout.LEFT));
//
//            JButton playButton = new JButton("Escuchar");
//            JButton stopButton = new JButton("Parar");
//            JSlider volumeSlider = new JSlider(0, 100, 100);
//
//            playButton.addActionListener(e -> source.play());
//            stopButton.addActionListener(e -> source.stop());
//            volumeSlider.addChangeListener(e -> source.setVolume(volumeSlider.getValue() / 100.0f));
//
//            trackControls.add(playButton);
//            trackControls.add(stopButton);
//            trackControls.add(volumeSlider);
//
//            JLabel trackLabel = new JLabel(audioFile.getName());
//            trackControls.add(trackLabel);
//
//            // Agrega botón de remover la pista
//            JButton closeButton = new JButton("Sacar");
//            closeButton.addActionListener(e -> {
//                // Saca la pista del mezclador
//                mixer.removeSource(source);
//
//                // Saca el panel de la pista del contenedor
//                trackPanel.remove(trackControls);
//                trackPanel.revalidate();
//                trackPanel.repaint();
//            });
//            trackControls.add(closeButton);
//
//            trackPanel.add(trackControls);
//            trackPanel.revalidate();
//            trackPanel.repaint();
//
//        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
//            JOptionPane.showMessageDialog(this,
//                    "Error adding track: " + e.getClass().getSimpleName() + "\n"
//                    + "Message: " + e.getMessage(),
//                    "Error Loading Audio",
//                    JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//class AudioFileChooser {
//
//    private JFileChooser fileChooser;
//    private static final String[] SUPPORTED_FORMATS = {"wav", "au", "aiff"};
//
//    public AudioFileChooser() {
//        initializeFileChooser();
//    }
//
//    private void initializeFileChooser() {
//        fileChooser = new JFileChooser();
//        // Set filter for audio files
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                "Audio Files", SUPPORTED_FORMATS);
//        fileChooser.setFileFilter(filter);
//
//        // Start in user's music folder if possible
//        String musicFolderPath = System.getProperty("user.home") + File.separator + "Music";
//        File musicFolder = new File(musicFolderPath);
//        if (musicFolder.exists()) {
//            fileChooser.setCurrentDirectory(musicFolder);
//        }
//    }
//
//    public File getAudioFile(Component parent) {
//        int result = fileChooser.showOpenDialog(parent);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            return fileChooser.getSelectedFile();
//        }
//        return null;
//    }
//
//}

// Main class
public class DemoAudiomixerapp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AudioMixerGUI gui = new AudioMixerGUI();
            gui.setVisible(true);
        });
    }

}
