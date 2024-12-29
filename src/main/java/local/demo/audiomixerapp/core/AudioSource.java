/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.demo.audiomixerapp.core;

/**
 *
 * @author pc
 */
public interface AudioSource {

    void play();

    void stop();

    void setVolume(float volume);

    boolean isPlaying();
}
