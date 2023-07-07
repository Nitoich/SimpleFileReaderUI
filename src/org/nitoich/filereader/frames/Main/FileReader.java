package org.nitoich.filereader.frames.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileReader implements Runnable {
    private String content;
    private File file;

    public FileReader(File file) {
        this.file = file;
    }

    public String getContent() {
        return this.content;
    }


    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(this.file))) {
            String line;
            while((line = br.readLine()) != null) {
                this.content += line + "\n";
                this.onLineRead(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLineRead(String line) {
        return;
    }
}
