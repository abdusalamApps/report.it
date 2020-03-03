package report.it;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String PG = "Project Leader";
    public static final String SG = "System Group";
    public static final String UG = "Development Group";
    public static final String TG = "Testing Group";


    public static final Map<Integer, String> groupsMap = Map.of(
            1, PG,
            2, SG,
            3, UG,
            4, TG
    );

}
