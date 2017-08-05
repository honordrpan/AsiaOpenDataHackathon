package companyanalysis;

public class CaseInfo {

    public String Region;
    public String Organizer;
    public String CaseName;
    public String Supervisor;
    public String Duration;
    public int Money;

    public CaseInfo(String r, String o, String c, String s, String d, int m) {
        Region = r;
        Organizer = o;
        CaseName = c;
        Supervisor = s;
        Duration = d;
        Money = m;
    }
}
