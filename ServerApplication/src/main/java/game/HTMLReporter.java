package game;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class HTMLReporter {

    File htmlTemplateFIle;
    String htmlString;
    String title;
    String body = new String();

    HTMLReporter(String player1, String player2){
        try {
            File htmlTemplateFile = new File("../templateGame.html");
            htmlString = FileUtils.readFileToString(htmlTemplateFile);
            title = player1+ " vs " + player2;
            body = "";
            htmlString = htmlString.replace("$title", title);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addToHtmlReport(String gameAction){
        body += "<p>"+gameAction+"</p>";
    }

   public void saveHtmlReport(){
        try {
            htmlString = htmlString.replace("$body", body);
            File newHtmlFile = new File("../"+title+".html");
            FileUtils.writeStringToFile(newHtmlFile, htmlString);
            SSHTransfer fileTransfetProtocol = new SSHTransfer();
            fileTransfetProtocol.transferFile("../"+title+".html");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        }
}
