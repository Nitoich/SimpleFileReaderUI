package org.nitoich.filereader.frames.Main;


import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {

    private JPanel content_panel;
    private JTree directory_tree;
    private JTextArea text;

    public MainFrame() {
        this.init();
    }

    public MainFrame(DefaultTreeModel treeModel) {
        this.setTitle("File Reader");
        this.init();
        this.directory_tree.setModel(treeModel);
    }

    public void init() {
        this.setContentPane(this.content_panel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(640, 480));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setJMenuBar(this.getJMenuBar());
        directory_tree.addTreeSelectionListener(new TreeSelectionListener() {
            private Thread frThread;

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (this.frThread != null && this.frThread.isAlive()) {
                    this.frThread.interrupt();
                }
                text.setText("");
                TreePath path = e.getPath();
                File file = ((FilesTreeModelGenerator.FileNode) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject()).getFile();
                if(file.isDirectory()) { return; }
                FileReader fr = new FileReader(file) {
                    @Override
                    public void onLineRead(String line) {
                        text.append(line + "\n");
                    }
                };
                this.frThread = new Thread(fr);
                this.frThread.start();
            }
        });
    }

    public JMenuBar getJMenuBar() {
        return new JMenuBar() {{
            add(new JMenu("File") {{
                add(new JMenuItem("Open") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, "Coming soon...");
                        }
                    });
                }});
                add(new JMenuItem("Exit") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                }});
            }});
        }};
    }

    public JPanel getContent_panel() {
        return content_panel;
    }
}
