/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.demo.audiomixerapp.audio;

import java.util.ArrayList;
import java.util.List;
import local.demo.audiomixerapp.core.AudioMixer;
import local.demo.audiomixerapp.core.AudioSource;

/**
 *
 * @author pc
 */
public class MultiTrackMixer implements AudioMixer {

    private final List<AudioSource> sources;

    public MultiTrackMixer() {
        sources = new ArrayList<>();
    }

    @Override
    public void addSource(AudioSource source) {
        sources.add(source);
    }

    @Override
    public void removeSource(AudioSource source) {
        sources.remove(source);
        source.stop();
    }

    @Override
    public void mixAudio() {
        for (AudioSource source : sources) {
            source.play();
        }
    }

    @Override
    public void stopAll() {
        for (AudioSource source : sources) {
            source.stop();
        }
    }

    @Override
    public List<AudioSource> getSources() {
        return sources;
    }
}
