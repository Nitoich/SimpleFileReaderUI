package org.nitoich.filereader.frames.Main;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;

public class FilesTreeModelGenerator {

    private final File rootDirectory;
    private final DefaultTreeModel model;
    private final DefaultMutableTreeNode mutableTreeNode;

    public  FilesTreeModelGenerator(File directory) {
        this.rootDirectory = directory;
        this.mutableTreeNode = new DefaultMutableTreeNode(new FileNode(rootDirectory));
        this.model = new DefaultTreeModel(this.mutableTreeNode);
    }

    public DefaultTreeModel getModel() {
        return this.model;
    }

    public void onComplete(DefaultTreeModel treeModel) {
        return;
    }
    public void onFolderComplete(DefaultTreeModel treeModel) {
        return;
    }
    public void onFileComplete(DefaultTreeModel treeModel) {
        return;
    }

    public DefaultTreeModel generateTreeModel() {
        new Thread(new CreateChildNodes(this.mutableTreeNode, this.rootDirectory)).start();
        return this.model;
    }

    public class CreateChildNodes implements Runnable {
        private DefaultMutableTreeNode root;
        private File rootFile;

        public CreateChildNodes(DefaultMutableTreeNode treeNode, File rootFile) {
            this.root = treeNode;
            this.rootFile = rootFile;
        }

        @Override
        public void run() {
            this.createChildren(this.root, this.rootFile);
            onComplete(model);
        }

        private void createChildren(DefaultMutableTreeNode treeNode, File rootFile) {
            File[] files = rootFile.listFiles();
            if(files == null) { return; }
            for(File file : files) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(new FileNode(file));
                treeNode.add(node);
                onFileComplete(model);
                if(file.isDirectory()) {
                    this.createChildren(node, file);
                }
            }
            onFolderComplete(model);
        }
    }

    public static class FileNode {

        private final File file;

        public FileNode(File file) {
            this.file = file;
        }

        public File getFile() {
            return this.file;
        }

        @Override
        public String toString() {
            String name = this.file.getName();
            return name.equals("") ? this.file.getAbsolutePath() : name;
        }
    }
}
