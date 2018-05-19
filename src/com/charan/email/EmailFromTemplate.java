package com.charan.email;

import com.charan.csv.HeaderIndex;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class EmailFromTemplate {
    String title;
    String author;
    String email;

    String templatePath;

    public EmailFromTemplate(String title, String author, String email) {
        this.title = title;
        this.author = author;
        this.email = email;
    }

    public EmailFromTemplate() {
        
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = "\"" + title + "\"";
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTemplateFilePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    private Email process() {
        String template = readTemplate();
        template = updateTemplate(template);
        Email email = new Email();
        email.setTo(getEmail());
        // TODO: Update the subject after discussing with Pasdu
        email.setSubject(getTitle());
        email.setBody(template);
        return email;
    }

    public String readTemplate() {
        String template = "";
        try{
//            byte[] stream = Files.readAllBytes(Paths.get(templatePath));
//            template = new String(stream);
            List<String> allLines = Files.readAllLines(Paths.get(templatePath));
            template = String.join("<br/>", allLines);
        } catch (Exception e) {
            System.out.println("Exception while reading template: " + e.getMessage());
        }
        return template;
    }

    private String updateTemplate(String template) {
        template = template.replaceAll("<NAME>", getAuthor());
        template = template.replaceAll("<TITLE>", getTitle());
        return template;
    }

    public Email extractEmail(String[] contents) {
        setTitle(contents[HeaderIndex.TITLE.getPosition()]);
        setAuthor(contents[HeaderIndex.AUTHOR.getPosition()]);
        setEmail(contents[HeaderIndex.EMAIL.getPosition()]);
        return process();
    }
}
