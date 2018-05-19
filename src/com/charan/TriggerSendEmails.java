package com.charan;

import com.charan.csv.ReadCSV;
import com.charan.email.Email;
import com.charan.email.EmailFromTemplate;
import com.charan.email.EmailSendService;

import java.util.Arrays;
import java.util.List;

public class TriggerSendEmails {

    public static void main(String[] args) {
        String filePath = "/Users/charan/Projects/sendemails/src/com/charan/csv/outputcsv1526668119123.csv";
        String templatePath = "/Users/charan/Projects/sendemails/src/com/charan/templates/emailtemplate.txt";
        String fromEmail = "charant.lgp+journalstest@gmail.com";
        ReadCSV gm = new ReadCSV(filePath);
        List<String[]> list = gm.readFile();


        for(String[] contents : list) {
            List<String> row = Arrays.asList(contents);
            if(row.indexOf("Title") != -1) {
                continue;
            }
            EmailFromTemplate et = new EmailFromTemplate();
            et.setTemplateFilePath(templatePath);
            Email em =  et.extractEmail(contents);
            em.setFrom(fromEmail);

            EmailSendService emailSendService = new EmailSendService();
            emailSendService.sendEmail(em);
        }
//        gm.setEt(et);
    }
}
