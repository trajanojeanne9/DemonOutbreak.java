import javax.swing.*;
import java.awt.*;

/** Entry point: javac *.java  then  java NoMercyForDemons */
public class NoMercyForDemons {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("No Mercy for Demons");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.getContentPane().setBackground(Color.BLACK);
            GamePanel p = new GamePanel(f);
            f.add(p);
            f.setSize(1100, 640);
            f.setLocationRelativeTo(null);
            p.setFullscreen(true);     // starts in full screen: F11 or ESC switches to a window
            p.start();
            p.requestFocusInWindow();
        });
    }
}
