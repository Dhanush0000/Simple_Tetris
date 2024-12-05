package main;

import java.net.URL;
import javax.sound.sampled.*;

public class Sound {
    Clip musicClip;
    URL[] url = new URL[4];

    public Sound() {
        url[0] = getClass().getResource("/delete line.wav");
        url[1] = getClass().getResource("/gameover.wav");
        url[2] = getClass().getResource("/rotation.wav");
        url[3] = getClass().getResource("/touch floor.wav");

        for (int i = 0; i < url.length; i++) {
            if (url[i] == null) {
                System.err.println("Sound file not found: index " + i);
            }
        }
    }

    public void play(int i, boolean music) {
        try {
            if (url[i] == null) {
                System.err.println("Sound file not available at index " + i);
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();
            if (music) {
                musicClip = clip;
            }
            clip.open(ais);
            ais.close();

            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });

            clip.start();
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loop() {
        if (musicClip != null) {
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.err.println("No music clip loaded to loop.");
        }
    }

    public void stop() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
        } else {
            System.err.println("No music clip to stop.");
        }
    }
}
