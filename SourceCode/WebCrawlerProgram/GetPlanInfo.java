/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getplaninfo;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;
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
public class GetPlanInfo {

    public static ArrayList<String> CityLink;
    public static WebClient webClient;
    public static FileOutputStream fos;
    public static HtmlPage page;
    public static byte[] BOM_UTF8 = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
    public static final String[] CityNumber = {"99", "98", "97", "96", "95", "94", "72", "71", "63", "20", "18", "17", "16", "15", "14", "13", "12",
        "11", "10", "09", "08", "07", "06", "05", "04", "03", "02", "01"};

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        CityLink = new ArrayList<String>();

        webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true  
        webClient.getOptions().setCssEnabled(false); //禁用css支持          
        webClient.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常          
        webClient.getOptions().setTimeout(50000);

        int count = 0;
         
       for(int i=0;i<CityNumber.length;i++)
        {
            getPlanList("guesmap.rnap4?iplscode="+CityNumber[i]+"00&iwebcod=&iuid=3Q3E494B");
            
        }
        
        
/*
        try {
            page = (HtmlPage) webClient.getPage("http://cmdweb.pcc.gov.tw/pccms/owa/guesmap.rnap2?icnt=1&iuid=3Q3E494B");//找城市
            Document doc = Jsoup.parse(page.asXml());
            Element table = doc.select("tbody").first();
            Iterator<Element> ite = table.select("area").iterator();
            while (ite.hasNext()) {
                Element e = ite.next();
                String href = e.attr("href");
                CityLink.add(href);
                System.out.println(">>" + href);
                count++;
            }
            for (int i = 0; i < count; i++) {
                fos = new FileOutputStream("City/" + i + ".csv");
                fos.write(BOM_UTF8);
                fos.write("工程主辦機關,工程名稱,監造單位,施工廠商,施工期間,決標金額\n".getBytes("utf8"));
                page = (HtmlPage) webClient.getPage("http://cmdweb.pcc.gov.tw/pccms/owa/" + CityLink.get(i));//找地方行政區
                doc = Jsoup.parse(page.asXml());
                table = doc.select("tbody").first();
                ite = table.select("area").iterator();
                while (ite.hasNext()) {
                    Element e = ite.next();
                    String href = e.attr("href");
                    System.out.println(i + " :" + href);
                    getPlanList(href);
                }
                fos.close();
            }

        } catch (IOException e) {
            System.out.println("url wrong");
        }*/
    }

    public static void getPlanList(String href) {//找標案的link
        try {
            int count = 1;
            HtmlPage page = (HtmlPage) webClient.getPage("http://cmdweb.pcc.gov.tw/pccms/owa/" + href + "&ishowb=" + count);
            Document InsideDoc = Jsoup.parse(page.asXml());
            Element InsideTable = InsideDoc.select("tbody").get(1);
            Iterator<Element> InsideIte = InsideTable.select("font").iterator();
            String area = InsideIte.next().text();
            System.out.println("open: " + area);
            fos = new FileOutputStream("AcrossCity/" + area + ".csv");
            fos.write(BOM_UTF8);
            fos.write("工程主辦機關,工程名稱,監造單位,施工廠商,施工期間,決標金額\n".getBytes("utf8"));
            while (true) {

                page = (HtmlPage) webClient.getPage("http://cmdweb.pcc.gov.tw/pccms/owa/" + href + "&ishowb=" + count);
                InsideDoc = Jsoup.parse(page.asXml());
                InsideTable = InsideDoc.select("tbody").get(1);
                InsideIte = InsideTable.select("font").iterator();
                area = InsideIte.next().text();

                InsideTable = InsideDoc.select("tbody").get(1);
                InsideIte = InsideTable.select("a").iterator();
                int check = 0;
                while (InsideIte.hasNext()) {
                    Element e2 = InsideIte.next();
                    String InsideHref = e2.attr("href");
                    System.out.println(count + " : " + InsideHref);
                    getPlanDetail(InsideHref);
                    fos.write("\n".getBytes("utf8"));

                    count++;
                    check++;
                }
                if (check == 0) {
                    break;
                }
            }
            fos.close();
        } catch (IOException e) {
            System.out.println("getPlanList wrong");

        }
    }

    public static void getPlanDetail(String href) {
        try {
            page = (HtmlPage) webClient.getPage("http://cmdweb.pcc.gov.tw/pccms/owa/" + href);
            Document InsideDoc = Jsoup.parse(page.asXml());
            Element InsideTable = InsideDoc.select("tbody").last();
            Iterator<Element> InsideIte = InsideTable.select("font").iterator();
            while (InsideIte.hasNext()) {
                InsideIte.next();
                Element e2 = InsideIte.next();
                System.out.println(e2.text());
                fos.write(e2.text().getBytes("utf8"));
            }
            InsideTable = InsideDoc.select("tbody").first();
            InsideIte = InsideTable.select("font").iterator();
            while (InsideIte.hasNext()) {
                Element e2 = InsideIte.next();
                if (e2.text().equals("工程名稱")) {
                    InsideIte.next();
                    Element e3 = InsideIte.next();
                    System.out.println(e3.text());
                    fos.write(",".getBytes());
                    fos.write(e3.text().getBytes("utf8"));
                } else if (e2.text().equals("監造單位")) {
                    InsideIte.next();
                    Element e3 = InsideIte.next();
                    System.out.println(e3.text());
                    fos.write(",".getBytes());
                    fos.write(e3.text().getBytes("utf8"));
                } else if (e2.text().equals("施工廠商")) {
                    InsideIte.next();
                    Element e3 = InsideIte.next();
                    System.out.println(e3.text());
                    fos.write(",".getBytes());
                    fos.write(e3.text().getBytes("utf8"));
                } else if (e2.text().equals("施工期間")) {
                    InsideIte.next();
                    Element e3 = InsideIte.next();
                    System.out.println(e3.text());
                    fos.write(",".getBytes());
                    fos.write(e3.text().getBytes("utf8"));
                } else if (e2.text().equals("決標金額：")) {
                    String money = InsideIte.next().text();
                    money = money.replace(',', '.');
                    System.out.println(money);

                    fos.write(",".getBytes());
                    fos.write(money.getBytes("utf8"));

                }
                // fos.write(e2.text().getBytes());
            }

        } catch (IOException e) {
            System.out.println("getPlanDetail wrong");
        }
    }
}
