package com.aleshka;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

@Mojo(name = "helloworld")
public class HelloworldMojo extends AbstractMojo {

    @Parameter(property = "helloworld.gitDir", defaultValue = ".git")
    private File ideaProjectDir;

    public void execute() throws MojoExecutionException {
        getLog().info("Hello, World");
        String ideaProjectDirPath = ideaProjectDir.getAbsolutePath();
        String ideaProjectNameFilePath = ideaProjectDirPath + "/logs/HEAD";
        String data;
        try {
            data = tail(new File(ideaProjectNameFilePath));
            getLog().info(data);
            //TODO: Regex parse
        } catch (IOException e) {
            getLog().warn("Git logs not found");
        }
    }

    private String tail(File file) throws IOException{
        RandomAccessFile fileHandler = null;
        try {
            fileHandler = new RandomAccessFile(file, "r");
            long fileLength = fileHandler.length() - 1;
            StringBuilder sb = new StringBuilder();

            for (long filePointer = fileLength; filePointer != -1; filePointer--) {
                fileHandler.seek(filePointer);
                int readByte = fileHandler.readByte();

                if (readByte == 0xA) {
                    if (filePointer == fileLength) {
                        continue;
                    }
                    break;

                } else if (readByte == 0xD) {
                    if (filePointer == fileLength - 1) {
                        continue;
                    }
                    break;
                }

                sb.append((char) readByte);
            }

            String lastLine = sb.reverse().toString();
            return lastLine;
        } finally {
            if (fileHandler != null)
                try {
                    fileHandler.close();
                } catch (IOException e) {
                /* ignore */
                }
        }
    }
}
