package com.charan;

import com.charan.csv.ReadCSV;
import com.charan.email.Email;
import com.charan.email.EmailFromTemplate;
import com.charan.email.EmailSendService;

import java.util.Arrays;
import java.util.List;

public class TriggerSendEmails {

    public void process(String filePath, String templatePath, String fromEmail) {
        ReadCSV gm = new ReadCSV(filePath);
        List<String[]> list = gm.readFile();


        int i = 0;
        for(String[] contents : list) {
            List<String> row = Arrays.asList(contents);
            if(row.indexOf("Title") != -1) {
                continue;
            }
            EmailFromTemplate et = new EmailFromTemplate();
            et.setTemplateFilePath(templatePath);
            Email em =  et.extractEmail(contents);
            em.setFrom("editor.jcrm@clinicalstudiesjournal.com");
            em.setPwd("*********");

            EmailSendService emailSendService = new EmailSendService();
            emailSendService.sendEmail(em);
            i++;
            if (i ==2 ) {
                break;
            }
        }
//        gm.setEt(et);
    }
    public static void main(String[] args) {
        String filePath = "/Users/charan/Projects/sendemails/src/com/charan/csv/outputcsv1526668119123.csv";
        String templatePath = "/Users/charan/Projects/sendemails/src/com/charan/templates/emailtemplate.txt";
        String fromEmail = "charant.lgp+journalstest@gmail.com";
        TriggerSendEmails triggerSendEmails = new TriggerSendEmails();
        triggerSendEmails.process(filePath, templatePath, fromEmail);
    }
}
