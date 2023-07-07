package org.nitoich.filereader;

import org.nitoich.filereader.frames.DirectoryChooser.DirectoryChooserFrame;
import org.nitoich.filereader.frames.Loading.LoadingFrame;
import org.nitoich.filereader.frames.Main.FilesTreeModelGenerator;
import org.nitoich.filereader.frames.Main.MainFrame;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

public class BaseScenario implements Callable<Void> {
    private static int fileIterator = 0;

    @Override
    public Void call() throws Exception {
        File root = this.startDirectoryChooser();
        int countFiles = (int) Files
                .walk(Path.of(root.getAbsolutePath()))
                .count();

        LoadingFrame loadingFrame = new LoadingFrame() {
            @Override
            public void update(JProgressBar bar) {
                bar = this.getProgressBar();
                bar.setMinimum(0);
                bar.setMaximum(countFiles);
                bar.setValue(fileIterator);
            }
        };

        DefaultTreeModel treeModel = new FilesTreeModelGenerator(root) {
            @Override
            public void onComplete(DefaultTreeModel treeModel) {
                treeModel.reload();
                loadingFrame.dispose();
                MainFrame frame = new MainFrame(treeModel);
            }

            @Override
            public void onFileComplete(DefaultTreeModel treeModel) {
                fileIterator++;
                loadingFrame.update(loadingFrame.getProgressBar());
            }
        }.generateTreeModel();
        return null;
    }

    public File startDirectoryChooser() {
        return new DirectoryChooserFrame() {
            @Override
            public void onCancel() {
                JOptionPane.showMessageDialog(null, "Directory not selected! Close app!");
                System.exit(0);
            }

            @Override
            public void onComplete() {
                this.dispose();
            }
        }.choose();
    }
}


