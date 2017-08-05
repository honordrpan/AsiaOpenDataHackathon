/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchcompany;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author YuehJuLin
 */
public class MatchCompany {



    
    public static FileOutputStream fos;
    public static byte[] BOM_UTF8 = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
       
        // TODO code application logic here
        File f = new File("Company_Plan");
        File file[] = f.listFiles();
       /* fos = new FileOutputStream("Company_Plan/Matching/" + 1 + ".csv");
            fos.write(BOM_UTF8);
            fos.write("單位,數量\n".getBytes("utf8"));
        parseCSV("Company_Plan/一一一又清潔服務有限公司.csv", 0);*/
        for (int i = 1; i < file.length; ++i) {
            System.out.println(i + " : " + file[i].getName());
            fos = new FileOutputStream("Company_Plan/Matching/M_" + file[i].getName() );
            fos.write(BOM_UTF8);
            fos.write("單位,數量\n".getBytes("utf8"));
            parseCSV(file[i].getName(), i);
            fos.close();
        }

    }

    public static void parseCSV(String csvFile, int count) {
        ArrayList<String> department = new ArrayList<String>();;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int[] a =new int [1000];

        try {

            br = new BufferedReader(new FileReader("Company_Plan/"+csvFile));
            while ((line = br.readLine()) != null) {
                
                if(department.size()==0)
                {
                    line=line.substring(1);
                    department.add(line);
                    
                }
                else
                {
                    boolean check=false;
                    for(int j=0;j<department.size();j++)
                    {
                       // System.out.println(department.get(j)+" : "+line+" : "+department.get(j).equals(line));
                       if(department.get(j).equals(line))   
                       {
                           a[j]++;
                           check=true;
                       }
                    }
                    if(!check)
                    {
                        department.add(line);
                    }
                    
                }
                // use comma as separator
                // System.out.println("Company [code= " + info[0] + " , name=" + info[1] + "]");
             
            }
            for(int i=0;i<department.size();++i)
            {
                String temp=department.get(i)+","+a[i]+"\n";
                //System.out.println(department.get(i)+" :amount "+a[i]);
                fos.write(temp.getBytes("utf8"));
               
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
