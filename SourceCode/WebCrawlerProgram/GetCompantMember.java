/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getcompantmember;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author YuehJuLin
 */
public class GetCompantMember {

    public static ArrayList<String> CompanyNumber;
    public static WebClient webClient;
    public static FileOutputStream fos;
    public static byte[] BOM_UTF8 = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        CompanyNumber = new ArrayList<String>();
        parseCSV();
        webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true  
        webClient.getOptions().setCssEnabled(false); //禁用css支持          
        webClient.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常          
        webClient.getOptions().setTimeout(1000000);
        try {
            HtmlPage page = (HtmlPage) webClient.getPage("http://mops.twse.com.tw/mops/web/stapap1");//找城市
            for (int i = 760; i < CompanyNumber.size(); i++) {

               // if (Integer.parseInt(CompanyNumber.get(i)) >= 9945) {
                    fos = new FileOutputStream("member/" + CompanyNumber.get(i) + ".csv");
                    fos.write(BOM_UTF8);
                    fos.write("職稱,姓名\n".getBytes("utf8"));
                    
                    HtmlInput intputBox = (HtmlInput) page.getHtmlElementById("co_id");
                    System.out.println(CompanyNumber.get(i));
                    intputBox.setValueAttribute(CompanyNumber.get(i));
                    HtmlElement ele = (HtmlElement) page.getByXPath("//input[@type='button' ]").get(1);
                    HtmlPage newpage = (HtmlPage) ele.click();
                    Thread.sleep(5000);

                    Document doc = Jsoup.parse(newpage.asXml());
                    Element table = doc.select("tbody").get(4);
                    Iterator<Element> ite = table.select("tr").iterator();
                    while (ite.hasNext()) {
                        Element e = ite.next();
                        // System.out.println(e.attr("class"));
                        if (e.attr("class").equals("odd") || e.attr("class").equals("even")) {
                            String arr[] = e.text().split(" ");
                            System.out.println(arr[1]);
                            fos.write(arr[1].getBytes("utf8"));
                            fos.write("\n".getBytes("utf8"));
                        }

                    }
                    fos.close();
                }
            
        } catch (IOException e) {
            System.out.println("url wrong");
        } catch (ElementNotFoundException e) {
            System.out.println("not found wrong");
        }

    }

    public static void parseCSV() {
        String csvFile = "CompanyList.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] info = line.split(cvsSplitBy);

                // System.out.println("Company [code= " + info[0] + " , name=" + info[1] + "]");
                String n = info[0].substring(1);
                CompanyNumber.add(n);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
