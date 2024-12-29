/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.demo.audiomixerapp.core;

import java.util.List;

/**
 *
 * @author pc
 */
public interface AudioMixer {

    void addSource(AudioSource source);

    void removeSource(AudioSource source);

    void mixAudio();

    void stopAll();

    List<AudioSource> getSources();
}
