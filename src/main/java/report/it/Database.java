package report.it;

import java.sql.Connection;

public class Database {

    protected Connection conn = null;

    public Database(Connection conn) {
        this.conn = conn;
    }


}
