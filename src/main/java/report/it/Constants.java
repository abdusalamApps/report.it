package report.it;

import java.util.HashMap;

import java.util.Map;
/**
 * A class to hold some constant values
 * and present them as static variables.
 * @author Abdusalam Yabrak
 * @version 0.3
 */

public class Constants {

    public static final int LEADER = 1;
    public static final int MEMBER = 2;
    public static final int SG = 3;
    public static final int UG = 4;
    public static final int TG = 5;


    public static final String LEADER_STRING = "Leader";
    public static final String MEMBER_STRING = "Member";
    public static final String SG_STRING = "SG";
    public static final String UG_STRING = "UG";
    public static final String TG_STRING = "TG";

    public static HashMap<Integer, String> rolesMap;
    static {
        rolesMap = new HashMap<>();
        rolesMap.put(LEADER, LEADER_STRING);
        rolesMap.put(MEMBER, MEMBER_STRING);
        rolesMap.put(SG,SG_STRING);
        rolesMap.put(UG,UG_STRING);
        rolesMap.put(TG,TG_STRING);
    }
}
