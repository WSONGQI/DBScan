package wusongqi.dbscan.util;

import wusongqi.dbscan.model.DBColumn;
import wusongqi.dbscan.model.DBTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库连接查询
 */
public class DBConnectionUtil {
    private DBInfo dbInfo;

    public DBConnectionUtil(DBInfo dbInfo){
        this.dbInfo = dbInfo;
    }

    /**
     * 打开数据库
     * @return
     * @throws SQLException
     */
    public Connection openConnection() throws SQLException {
        dbInfo.getDriverClass();
        Connection connection = DriverManager.getConnection(dbInfo.getURL(),
                dbInfo.getUser(),dbInfo.getPassword());
        return connection;
    }

    /**
     * 关闭连接
     * @param conn
     */
    public void closeConnection(Connection conn){
        try{
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
    }

    /**
     * 关闭查询对象
     * @param stmt
     */
    public void closeStatement(PreparedStatement stmt){
        try{
            if(stmt != null && !stmt.isClosed()){
                stmt.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 取数据库表信息(表名列表)
     * @return
     */
    public List<DBTable> getTableName(){
        Connection conn = null;
        List<DBTable> list = new ArrayList<DBTable>();
        try{
            conn = openConnection();
            DatabaseMetaData dbMetaData = conn.getMetaData();
            String[] types = { "TABLE" };

            /**
             *这个方法带有四个参数
             *String catalog：要获得表所在的编目。"“”"意味着没有任何编目，Null表示所有编目。
             *String schema：要获得表所在的模式。"“”"意味着没有任何模式，Null表示所有模式。
             *String tableName：指出要返回表名与该参数匹配的那些表，
             *String types：一个指出返回何种表的数组。
             */
            ResultSet tabs = dbMetaData.getTables(null,"%","%",types);
            while(tabs.next()){
                //表名
                String tableName = tabs.getString("TABLE_NAME");
                //表备注
                String remarks = tabs.getString("REMARKS");
                //数据库名
                String tableSchem = tabs.getString(1);

                //比对，用户所使用的数据库里的表名存入
                if (dbInfo.getDatabase().equalsIgnoreCase(tableSchem)) {
                    DBTable dbTable = new DBTable();
                    dbTable.setName(tableName);
                    dbTable.setRemark(remarks);
                    list.add(dbTable);
                }
            }
            //关闭ResultSet
            tabs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }
        return list;
    }

    /**
     * 获得数据表列信息（字段列表）
     * @return
     */
    public List<DBColumn> getColumnInfo(String tableName){
        Connection conn = null;
        PreparedStatement pStemt = null;

        List<DBColumn> list = new ArrayList<DBColumn>();
        System.out.println(tableName);

        try{
            conn = openConnection();
            pStemt = conn.prepareStatement("SELECT * FROM "+tableName);
            ResultSetMetaData rsmd = pStemt.getMetaData();

            for(int i = 0;i<rsmd.getColumnCount();i++){
                DBColumn dbColumn = new DBColumn();
                int type = rsmd.getColumnType(i+1);

                //映射字段数据类型
                String jtype = String.class.toString();
                if(type == Types.INTEGER){
                    jtype = "Integer";
                }
                if(type == Types.BIGINT){
                    jtype = "Long";
                }
                if(type==Types.DECIMAL || type==Types.DOUBLE || type==Types.FLOAT || type==Types.NUMERIC){
                    jtype = "Double";
                }
                if(type==Types.TIMESTAMP){
                    jtype = "Date";
                }
                if(type==Types.DATE){
                    jtype = "Date";
                }
                if(type==Types.VARCHAR || type==Types.CHAR){
                    jtype = "String";
                }

                dbColumn.setName(formatColumnName(rsmd.getColumnName(i+1)));
                dbColumn.setComment(rsmd.getColumnLabel(i+1));
                dbColumn.setJavaType(jtype);
                list.add(dbColumn);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeStatement(pStemt);
            closeConnection(conn);
        }
        return list;
    }

    /**
     * 格式化字段名，将字段名驼峰命名
     * 先判断字段名中有没有“_”，没有直接返回
     * 有的话按照“_”分割成数组
     * 遍历数组，第一个单词不管，第二个单词起将每个首字母转大写
     * @param name
     * @return
     */
    public String formatColumnName(String name) {
        String reusltName = "";

        //看是否存在_
        if (name.contains("_")) {
            //按照_转化为数组
            String[] namearr = name.split("_");
            if (namearr.length > 0) {

                for (int i = 0; i < namearr.length; i++) {
                    //第一轮i>0,开始把单词首字母转大写
                    if (i > 0) {
                        //获得单词substring(0, 1)得到首位转大写，再用substring(1, namearr[i].length()获得大写以后的追加上去
                        String tname = namearr[i].substring(0, 1).toUpperCase() + namearr[i].substring(1, namearr[i].length());
                        reusltName = reusltName + tname;
                    } else {
                        //第一轮i=0也就是字段名的第一个单词，不需要转化
                        reusltName = reusltName + namearr[i];
                    }
                }
                //返回格式化后的字段名
                return reusltName;
            }else{
                //不存在直接返回原有的字段名
                return name;
            }
        }
        return name;
    }
}
