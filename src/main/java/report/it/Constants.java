package report.it;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final int LEADER = 1;
    public static final int MEMBER = 2;

    public static final String LEADER_STRING = "Leader";
    public static final String MEMBER_STRING = "Member";

    public static HashMap<Integer, String> rolesMap;
    static {
        rolesMap = new HashMap<>();
        rolesMap.put(LEADER, LEADER_STRING);
        rolesMap.put(MEMBER, MEMBER_STRING);
    }

}
