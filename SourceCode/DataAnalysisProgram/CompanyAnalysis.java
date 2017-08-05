package companyanalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CompanyAnalysis {

    private static String[] regions = {"南投縣", "台中市", "台北市", "台南市", "台東縣", "嘉義市", "嘉義縣", "基隆市", "宜蘭縣", "屏東縣", "彰化縣", "新北市", "新竹市", "新竹縣", "桃園市", "澎湖縣", "花蓮縣", "苗栗縣", "連江縣", "金門縣", "雲林縣", "高雄市", "中部地區", "北部地區", "南部地區", "台灣地區", "東部地區", "西部地區"};
    private static List<CompanyInfo> Company = new ArrayList<CompanyInfo>();
    private static List<RegionCompany> regionCompany = new ArrayList<RegionCompany>();
    private static List<OrganizerCompany> organizerCompany = new ArrayList<OrganizerCompany>();

    public static void main(String[] args) throws Exception {
        File dir1 = new File("C:\\users\\Eric\\Desktop\\City");
        File dir2 = new File("C:\\users\\Eric\\Desktop\\AcrossCity");
        File[] dir1files = dir1.listFiles();
        File[] dir2files = dir2.listFiles();
        analysis(dir1files);
        analysis(dir2files);
        Company.sort(new Comparator() {
            @Override
            public int compare(Object t, Object t1) {
                CompanyInfo c = (CompanyInfo) t, c1 = (CompanyInfo) t1;
                return c1.Money - c.Money;
            }
        });
        File f = new File("company.txt");
        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        Scanner sc = new Scanner(f);

        //File f = new File("BuildCase.json");
        /*File f = new File("relationGraphData.js");
        f.createNewFile();
        FileWriter fw = new FileWriter(f);*/
        /*fw.write("{");

        fw.write("\"CompanyToCase\":[");
        for (int i = 0; i < Company.size(); ++i) {
            CompanyInfo com = Company.get(i);
            fw.write("{");
            fw.write("\"CompanyName\":\"" + com.Name + "\",");
            fw.write("\"TotalCaseNum\":" + com.Amount + ",");
            fw.write("\"TotalMoney\":" + com.Money + ",");
            fw.write("\"Cases\":[");
            for (int j = 0; j < com.Cases.size(); ++j) {
                CaseInfo c = com.Cases.get(j);
                fw.write("{");
                fw.write("\"Region\":\"" + c.Region + "\",\"Organizer\":\"" + c.Organizer + "\",\"CaseName\":\"" + c.CaseName + "\",\"Supervisor\":\"" + c.Supervisor + "\",\"Duration\":\"" + c.Duration + "\",\"Price\":" + c.Money);
                fw.write(j != com.Cases.size() - 1 ? "}," : "}");
            }
            fw.write("]");
            fw.write(i != Company.size() - 1 ? "}," : "}");
        }
        fw.write("],");

        for (int i = 0; i < Company.size(); ++i) {
            CompanyInfo com = Company.get(i);
            for (int j = 0; j < com.Cases.size(); ++j) {
                CaseInfo c = com.Cases.get(j);
                int find;
                for (find = 0; find < organizerCompany.size(); ++find) {
                    if (organizerCompany.get(find).Organizer.equals(c.Organizer)) {
                        organizerCompany.get(find).AddCom(com.Name);
                        break;
                    }
                }
                if (find == organizerCompany.size()) {
                    organizerCompany.add(new OrganizerCompany(c.Organizer, com.Name));
                }
            }
        }
        fw.write("\"OrganizerToCompany\":{");
        for (int i = 0; i < organizerCompany.size(); ++i) {
            OrganizerCompany oc = organizerCompany.get(i);
            fw.write("\"" + oc.Organizer + "\":[");
            for (int j = 0; j < oc.Companies.size(); ++j) {
                fw.write("\"" + oc.Companies.get(j) + "\"");
                fw.write(j != oc.Companies.size() - 1 ? "," : "");
            }
            fw.write("]");
            fw.write(i != organizerCompany.size() - 1 ? "," : "");
        }
        fw.write("},");

        for (int i = 0; i < 28; ++i) {
            regionCompany.add(new RegionCompany());
        }
        for (int i = 0; i < Company.size(); ++i) {
            CompanyInfo com = Company.get(i);
            for (int j = 0; j < com.Cases.size(); ++j) {
                CaseInfo c = com.Cases.get(j);
                for (int k = 0; k < regions.length; ++k) {
                    if (regions[k].equals(c.Region)) {
                        for (int l = j + 1; l < com.Cases.size(); ++l) {
                            if (com.Cases.get(l).Region.equals(c.Region)) {
                                com.Cases.remove(l);
                                --l;
                            }
                        }
                        regionCompany.get(k).Companies.add(com.Name);
                        break;
                    }
                }
            }
        }
        fw.write("\"MapToCompany\":{");
        for (int i = 0; i < regionCompany.size(); ++i) {
            fw.write("\"" + regions[i] + "\":[");
            List<String> com = regionCompany.get(i).Companies;
            for (int j = 0; j < com.size(); ++j) {
                fw.write("\"" + com.get(j) + "\"");
                fw.write(j != com.size() - 1 ? "," : "");
            }
            fw.write(i != regionCompany.size() - 1 ? "]," : "]");
        }
        fw.write("}");

        fw.write("}");*/

 /*fw.write("var cy = [");

        for (int i = 0; i < organizerCompany.size(); ++i) {
            fw.write("{");

            fw.write("container: relationGraphCY,");
            fw.write("elements:{");
            fw.write("nodes:[");
            fw.write("{data:{id:'unit',title:'" + organizerCompany.get(i).Organizer + "'}}");
            List<String> com = organizerCompany.get(i).Companies;
            for (int j = 0; j < com.size(); ++j) {
                fw.write(",");
                fw.write("{");
                fw.write("data:{");
                fw.write("id:'c" + j + "',title:'" + com.get(j) + "'");
                fw.write("}");
                fw.write("}");
            }
            fw.write("],");
            fw.write("edges:[");
            for (int j = 0; j < com.size(); ++j) {
                fw.write(",");
                fw.write("{");
                fw.write("data:{");
                fw.write("id:'e" + j + "',source:'unit',target:'c" + j + "'");
                fw.write("}");
                fw.write("}");
            }
            fw.write("]");
            fw.write("},");
            fw.write("layout:relationGraph_layout,style:relationGraph_style");

            fw.write("}");
            fw.write(i != organizerCompany.size() - 1 ? "," : "");
        }

        fw.write("]");
        fw.close();*/
    }

    public static void analysis(File[] dirFiles) throws Exception {
        for (int i = 0; i < dirFiles.length; ++i) {
            String regionName = dirFiles[i].getName().replace(".csv", "");
            Scanner sc = new Scanner(dirFiles[i]);
            sc.nextLine();
            while (sc.hasNextLine()) {
                String aLine = sc.nextLine();
                String[] splited = aLine.split(",");
                if (splited.length == 6) {
                    int find;
                    for (find = 0; find < Company.size(); ++find) {
                        if (Company.get(find).Name.equals(splited[3])) {
                            ++Company.get(find).Amount;
                            Company.get(find).Money += parseMoney(splited[5]);
                            Company.get(find).Cases.add(new CaseInfo(regionName, splited[0], splited[1], splited[2], splited[4], parseMoney(splited[5])));
                            break;
                        }
                    }
                    if (find == Company.size()) {
                        Company.add(new CompanyInfo(new String(splited[3]), 1, parseMoney(splited[5]), new CaseInfo(regionName, splited[0], splited[1], splited[2], splited[4], parseMoney(splited[5]))));
                    }
                } else {
                    //System.out.println("Column Error: " + aLine);
                }
            }
            sc.close();
        }
    }

    public static int parseMoney(String money) {
        money = money.replace("千元", "");
        money = money.replace(".", "");
        return Integer.parseInt(money);
    }
}
