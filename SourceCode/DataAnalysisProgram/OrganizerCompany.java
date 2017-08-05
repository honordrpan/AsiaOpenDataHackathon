package companyanalysis;

import java.util.LinkedList;
import java.util.List;

public class OrganizerCompany {

    public String Organizer;
    public List<String> Companies = new LinkedList<String>();

    OrganizerCompany(String o, String c) {
        Organizer = o;
        AddCom(c);
    }

    public void AddCom(String c) {
        int find;
        for (find = 0; find < Companies.size(); ++find) {
            if (Companies.get(find).equals(c)) {
                break;
            }
        }
        if (find == Companies.size()) {
            Companies.add(c);
        }
    }
}
