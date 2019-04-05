package wusongqi.dbscan.util;

/**
 * 数据库连接信息
 */
public class DBInfo {

    /**数据库类型*/
    private DataBaseType type;
    /**地址*/
    private String host;
    /**端口号*/
    private int port;
    /**数据库名*/
    private String database;
    /**用户名*/
    private String user;
    /**密码*/
    private String password;

    /**有参构造函数*/
    public DBInfo(DataBaseType type, String host, int port, String database, String user, String password) {
        this.type = type;
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public DBInfo() {
    }

    /**
     * 获得数据库驱动Class
     * @return
     */
    public Class<?> getDriverClass(){
        try{
            if(type.equals(DataBaseType.PostgreSQL)){
                return Class.forName("org.postgresql.Driver");
            }
            if(type.equals(DataBaseType.MySQL)){
                return Class.forName("com.mysql.cj.jdbc.Driver");
            }
            if(type.equals(DataBaseType.Oracle)){
                return Class.forName("oracle.jdbc.driver.OracleDriver");
            }
            if(type.equals(DataBaseType.SQLServer)){
                return Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到连接数据库URL
     * @return
     */
    public String getURL(){
        String url = "";
        if(type.equals(DataBaseType.PostgreSQL)){
            url = "jdbc:postgresql://"+host+":"+(port==0 ? 5432 : port)+"/"+database;
        }
        if(type.equals(DataBaseType.MySQL)){
            url = "jdbc:mysql://"+host+":"+(port==0 ? 5432 : port)+"/"+database+"?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT";
        }
        if(type.equals(DataBaseType.Oracle)){
            url = "jdbc:oracle:thin:@"+host+":"+(port==0 ? 5432 : port)+"/"+database;
        }
        if(type.equals(DataBaseType.SQLServer)){
            url = "jdbc:microsoft:sqlserver://"+host+":"+(port==0 ? 5432 : port)+"/"+database;
        }
        return url;
    }


    /**
     * 数据库类型
     */
    public enum  DataBaseType{
        /*PostgreSQL*/
        PostgreSQL,
        /*MySQL*/
        MySQL,
        /*SQLServer*/
        SQLServer,
        /*Oracle*/
        Oracle

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





}
