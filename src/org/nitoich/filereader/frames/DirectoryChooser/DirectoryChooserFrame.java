package org.nitoich.filereader.frames.DirectoryChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DirectoryChooserFrame extends JFrame {

    private final JFileChooser fileChooser;

    public DirectoryChooserFrame() {
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMaximumSize(new Dimension(0, 0));
        this.setTitle("Choose a directory...");
        this.setLocationRelativeTo(null);
        this.fileChooser = new JFileChooser();
        this.fileChooser.setDialogTitle("Choose a directory...");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    public File choose() {
        this.setVisible(true);
        int result = this.fileChooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION) {
            this.onComplete();
            return this.fileChooser.getSelectedFile();
        } else {
            this.onCancel();
            return null;
        }
    }

    public void onCancel() {}
    public void onComplete() {}
}
