package org.nitoich.filereader.frames.Loading;

import javax.swing.*;

public class LoadingFrame extends JFrame {
    private JProgressBar progressBar;
    private JPanel panel1;

    public LoadingFrame() {
        this.setUndecorated(true);
        this.setTitle("Loading...");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JProgressBar getProgressBar() {
        return this.progressBar;
    }

    public void update(JProgressBar bar) {

    }
}
