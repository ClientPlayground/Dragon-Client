package Benz.mod;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class ExternalGUI {

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ExternalGUI e = new ExternalGUI();

                    e.frame.setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
    }

    public ExternalGUI() {
        this.initialize();
    }

    private void initialize() {
        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 450, 300);
        this.frame.setDefaultCloseOperation(3);
    }
}
