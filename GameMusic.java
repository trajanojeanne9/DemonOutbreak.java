import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/** Loops scene music from assets/music as WAV files. */
public final class GameMusic {

    private static final Set<String> REPORTED_MISSING = new HashSet<>();
    private static Clip current;
    private static String currentTrack;

    private GameMusic() {}

    public static void play(String track) {
        if (track == null) {
            stop();
            return;
        }
        if (track.equals(currentTrack) && current != null && current.isOpen()) return;

        stop();
        File file = new File("assets/music/" + track + ".wav");
        if (!file.isFile()) {
            if (REPORTED_MISSING.add(track))
                System.err.println("Music file not found: " + file.getPath());
            return;
        }

        try (AudioInputStream audio = AudioSystem.getAudioInputStream(file)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            current = clip;
            currentTrack = track;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Could not play music " + file + ": " + e.getMessage());
        }
    }

    public static void stop() {
        if (current != null) {
            current.stop();
            current.close();
            current = null;
        }
        currentTrack = null;
    }
}
