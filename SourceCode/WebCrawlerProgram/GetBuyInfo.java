/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getbuyinfo;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
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
public class GetBuyInfo {

    public static ArrayList<String> Company;
    public static ArrayList<String> CompanyPlanNumber;
    public static WebClient webClient;
    public static FileOutputStream fos;
    public static HtmlPage page;
    public static byte[] BOM_UTF8 = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
    public static String count;
    public static String name;
    public static String amount;
    public static int year;
    public static int month;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Company = new ArrayList<String>();
        CompanyPlanNumber = new ArrayList<String>();
        year = 92;
        month = 12;
        // TODO code application logic here
        webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true  
        webClient.getOptions().setCssEnabled(false); //禁用css支持          
        webClient.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常          
        webClient.getOptions().setTimeout(100000);
        parseCSV();
        int count = 0;
       
         for (int i = 65719; i < Company.size(); ++i) {
            if (Integer.parseInt(CompanyPlanNumber.get(i)) > 200) {
                System.out.println(i+": "+Company.get(i));
                search(Company.get(i),Integer.parseInt(CompanyPlanNumber.get(i)));
            }

        }

        // search("一一一又清潔服務有限公司");
        /* try {

            while (year > 91) {
                if (month == 0) {
                    month = 12;
                    year--;
                }

                page = (HtmlPage) webClient.getPage("https://www.taiwanbuying.com.tw/CloseCaseMonthlyStatementAction.ASP?yy=" + Integer.toString(year) + "&mm=" + Integer.toString(month));//找日月
                if (month < 10) {
                    fos = new FileOutputStream("Buy/" + Integer.toString(year) + "0" + Integer.toString(month) + ".csv");
                } else {
                    fos = new FileOutputStream("Buy/" + Integer.toString(year) + Integer.toString(month) + ".csv");
                }
                fos.write(BOM_UTF8);
                fos.write("決標廠商名稱,案件數量,金額合計\n".getBytes("utf8"));
                Document doc = Jsoup.parse(page.asXml());
                Element table = doc.select("body").first();
                Iterator<Element> ite = table.select("td").iterator();
                boolean check = false;
                while (ite.hasNext()) {

                    if (check) {
                        if (ite.hasNext()) {
                            name = ite.next().text();
                        }

                        if (ite.hasNext()) {
                            count = ite.next().text();
                        }
                        if (ite.hasNext()) {
                            amount = ite.next().text();
                        }
                        if (!name.isEmpty()) {
                            amount = amount.replace(',', '.');

                            String temp = name + "," + count + "," + amount + "\n";
                            fos.write(temp.getBytes("utf8"));

                            System.out.println("name: " + name + "count:" + count + "amount:" + amount);
                        }

                    } else if (ite.next().text().equals("決標廠商名稱")) {
                        check = true;
                        ite.next();
                        ite.next();

                    }

                }
                fos.close();
                month--;
            }
        } catch (IOException e) {
            System.out.println("url wrong");
        }*/
    }

    public static void parseCSV() {
        String csvFile = "buy_company.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\t";

        try {

            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] info = line.split("\t");
                // System.out.println(info[0]);
                Company.add(info[0]);
                CompanyPlanNumber.add(info[1]);
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

    public static void search(String text,int c) throws InterruptedException {
        int total = 1;
        try {
             fos = new FileOutputStream("Company_plan/" + text + ".csv");
            fos.write(BOM_UTF8);
             fos.write("招標單位,招標案名\n".getBytes("utf8"));
            int pageNUM =1;
            page = (HtmlPage) webClient.getPage("https://www.taiwanbuying.com.tw/QueryCloseCase.ASP");
            HtmlInput intputBox = (HtmlInput) page.getByXPath("//*[@name='keyword']").get(0);
            intputBox.setValueAttribute(text);
            HtmlElement ele = (HtmlElement) page.getByXPath("//*[@type='submit']").get(0);
            page = (HtmlPage) ele.click();

            while (true) {
                int count = 0;
                System.out.println("Total: "+total);
                if (total != 1) {
                    HtmlAnchor htmlAnchor = page.getAnchorByHref("?PageNo="+Integer.toString(pageNUM));
                    page = (HtmlPage)  htmlAnchor.click();
                    Thread.sleep(1000);
                   
                }
                
                ////*[@href="?PageNo=3"]
                Document doc = Jsoup.parse(page.asXml());
                Element table = doc.select("tbody").get(6);
                Iterator<Element> ite = table.select("td").iterator();

                if (ite.hasNext()) {
                    ite.next();
                }
                while (ite.hasNext()) {
                    ite.next();
                    if (ite.hasNext()) {
                        String arr[] = ite.next().text().split(" ");
                        // fos.write(arr[2].getBytes("utf8"));
                        // fos.write("\n".getBytes("utf8"));
                        String temp=arr[2] + "," + arr[5]+"\n";
                        System.out.println(total + ":" + "招標單位:" + arr[2] + "招標案名:" + arr[5]);
                        fos.write(temp.getBytes("utf8"));
                        total++;
                        count++;
                    }

                }
                System.out.println(pageNUM);
                if (count != 0) {
                    pageNUM++;
                } else {
                    break;
                }

                if (total == 1000||total>=c) {
                    break;
                }
            }
            fos.close();
        } catch (IOException e) {
            System.out.println("search fail");
        }
    }

}
