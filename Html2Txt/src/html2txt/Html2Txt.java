/*
 * Name: Sadman Ahmed Shanto
 * Course: CS 2365
 * Section: 002
 * Semester: Spring 2020
 * Title: Project 1
 */
package html2txt;

// Libraries used
import java.io.*;
import java.util.*;

/**
 *
 * @author Sadman Ahmed Shanto
 */
public class Html2Txt {
    
    // Private variables
    private String fname;
    private final ArrayList<String> fcontent = new ArrayList<>();
    private Object s;
    
    /**
     *
     * @param data: all strings that start with <h
     * @param writer: Variable needed to write text file
     * @purpose: extracts all the text from the html header and writes it in text file
     */
    public void extracHeader(String data, PrintWriter writer){
        String[] str = data.split("<[^>]*>");
        data = str[1];
        writer.println(data+"\n");
    }
            
    /**
     *
     * @param data: all strings that start with <th>
     * @param writer: Variable needed to write text file
     * @purpose: extracts all the text from the html <th> and writes it in text file
     */
    public void extracTable1(String data, PrintWriter writer){   
            int size = data.length();
            if (size != 7){
                String[] str;
                str = data.split("<[^>]*>");
                data = str[1];
                writer.print(data.trim()+", ");
            }       
    }

    /**
     *
     * @param data: all strings that start with <td>
     * @param writer: Variable needed to write text file
     * @purpose: extracts all the text from the html <td> and writes it in text file
     */
    public void extracTable2(String data, PrintWriter writer){
        String[] str = data.split("<[^>]*>");
        data = str[1];
        writer.print(data.trim()+", ");
    }

    /**
     *
     * @param data: all strings that start with <p>
     * @param writer: Variable needed to write text file
     * @purpose: extracts all the text from the <p> header and writes it in text file
     */
    public void extractParagraph(String data, PrintWriter writer){
        String[] str = data.split("<[^>]*>");
        for (String s: str){
            writer.println(s.trim());
        }
    }
    
    /**
     *
     * @param data: all strings that start with </tr>
     * @param writer: Variable needed to write text file
     * @purpose: creates new line in the text file whenever </tr> is seen 
     */
    public void endLine(String data, PrintWriter writer){
        writer.print("\n");
    }
    
    /**
     *
     * @param oname: String name of output text file
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @purpose: creates desired output text file 
     */
    public void createTextOutput(String oname) throws FileNotFoundException, UnsupportedEncodingException{
        try (PrintWriter writer = new PrintWriter(oname, "UTF-8")) {
            analyzeContent(writer);
            writer.close();
        }
    }

    /**
     *
     * @param data: String that contains information from each line of input file
     * @purpose: reads data from input html files and stores in a vector array data structure of strings
     */
    public void analyzeFile(String data){
        fcontent.add(data);
    }
    
    /**
     * @purpose: displays the contents of the HTML files in the console (used for debugging)
     */
    public void showContent(){
        for (int i=0; i<fcontent.size(); i++){  
            String line = fcontent.get(i).trim();  
            System.out.println(line);
        }
    }
    
    /**
     *
     * @param writer: Variable needed to write text file
     * @purpose: Executes the main process of identifying markup, extracting text
     * and writing to text file
     */
    public void analyzeContent(PrintWriter writer){
      // System.out.println(fcontent);
        for (int i=0; i<fcontent.size(); i++){ 
            String line = fcontent.get(i).trim();
            identifyMarkUp(line, writer);
        }
    }
    

    /**
     *
     * @param data: String that contains information from each line of input file without 
     * leading white space
     * @param writer: Variable needed to write text file
     * @purpose: identifies key characters to select desired lines from the html file
     */
    public void identifyMarkUp(String data, PrintWriter writer){
        String firstFourChars = ""; 
        if (data.length() > 3) 
            firstFourChars = data.substring(0, 3);
        else
            firstFourChars = data;

        if (firstFourChars.contains("<p>"))
                extractParagraph(data,writer);
        else if (firstFourChars.contains("<h1"))
                extracHeader(data,writer);
        else if (firstFourChars.contains("<h2"))
                extracHeader(data,writer);
        else if (firstFourChars.contains("<h3"))
                extracHeader(data,writer);
        else if (firstFourChars.contains("<h4"))
                extracHeader(data,writer); 
        else if (firstFourChars.contains("</b"))
                return;
        else if (firstFourChars.contains("<tr"))
                return;
        else if (firstFourChars.contains("<th")){
                extracTable1(data,writer);
        }
        else if (firstFourChars.contains("</t"))
                endLine(data,writer);
        else if (firstFourChars.contains("<td"))
                extracTable2(data,writer);
        else
            return;
        }
       
    /**
     *
     * @param fname: String that contains the name of input html file 
     * @purpose: reads html file and converts each line to string to be added to data structure
     */ 
    public void readFile(String fname){
      try {
      File myObj = new File(fname);
          try (Scanner myReader = new Scanner(myObj)) {
              while (myReader.hasNextLine()) {
                  String data = myReader.nextLine();
                  analyzeFile(data);
                }   
          }
        } 
      catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
    }
    }
    

    /**
     *
     * @param args
     * @throws java.io.FileNotFoundException
     * @throws java.io.UnsupportedEncodingException
     * @purpose initiates and executes the program
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Html2Txt link1, link2, link3;
        
        link1 = new Html2Txt();
        link1.readFile("Matador Song | History & Traditions | TTU.html");
      //  link1.showContent();
        link1.createTextOutput("MatadorSong.txt");
        
        link3 = new Html2Txt();
        link3.readFile("Major Clients.html");
     //   link3.showContent();
        link3.createTextOutput("MajorClients.txt");
       
        link2 = new Html2Txt();
        link2.readFile("Table Example.html");
      //  link2.showContent();
        link2.createTextOutput("TableExample.txt");        
               
  }
    
}
