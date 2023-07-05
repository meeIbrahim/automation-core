package jdbc;

public class DatabaseBuilder {
    protected String userName;
    protected String password;
    protected String rdbms;
    protected String host;
    protected Integer port;
    protected String db;

    public DatabaseBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }

    public DatabaseBuilder password(String password) {
        this.password = password;
        return this;
    }

    public DatabaseBuilder rdbms(String rdbms) {
        this.rdbms = rdbms;
        return this;
    }

    public DatabaseBuilder host(String host) {
        this.host = host;
        return this;
    }

    public DatabaseBuilder port(Integer port) {
        this.port = port;
        return this;
    }

    public DatabaseBuilder db(String db) {
        this.db = db;
        return this;
    }

    public Database build() {
        return new Database(this);
    }
}
