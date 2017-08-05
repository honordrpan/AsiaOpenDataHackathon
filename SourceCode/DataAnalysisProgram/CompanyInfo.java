package companyanalysis;

import java.util.LinkedList;
import java.util.List;

public class CompanyInfo {

    public String Name;
    public int Amount;
    public int Money;
    public List<CaseInfo> Cases = new LinkedList<CaseInfo>();

    public CompanyInfo() {
        Name = new String();
        Amount = 0;
        Money = 0;
    }

    public CompanyInfo(String n, int a, int m, CaseInfo c) {
        Name = n;
        Amount = a;
        Money = m;
        Cases.add(c);
    }
}
