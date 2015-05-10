package com.aleshka;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

@Mojo(name = "helloworld")
public class HelloworldMojo extends AbstractMojo
{
    @Parameter(property = "helloworld.ideaProjectDir", defaultValue = ".idea")
    private File ideaProjectDir;

    public void execute() throws MojoExecutionException
    {
        getLog().info("Hello, World");
        String ideaProjectDirPath = ideaProjectDir.getAbsolutePath();
        String ideaProjectNameFilePath = ideaProjectDirPath + "/.name";
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(ideaProjectNameFilePath));
            String content = new String(encoded, Charset.defaultCharset());
            getLog().warn(content);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
