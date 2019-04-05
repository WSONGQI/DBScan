package wusongqi.dbscan;

import wusongqi.dbscan.model.DBColumn;
import wusongqi.dbscan.model.DBTable;
import wusongqi.dbscan.util.CreateEntity;
import wusongqi.dbscan.util.DBInfo;
import wusongqi.dbscan.util.DBConnectionUtil;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("v.11请选择数据库类型：" );
        System.out.println("1.Oracle 2.MySQL 3.PostgreSQL 4.SQLServer");
        int dbTypeNum = in.nextInt();

        System.out.print("请输入Host：");
        String host = in.next();

        System.out.print("请输入端口号：");
        int port = in.nextInt();

        System.out.print("请输入数据库名称：");
        String dbName = in.next();

        System.out.print("请输入用户名：");
        String userName = in.next();

        System.out.print("请输入密码：");
        String password = in.next();

        DBInfo.DataBaseType dbType = null;
        switch (dbTypeNum){
            case 1:
                dbType = DBInfo.DataBaseType.Oracle;
                break;
            case 2:
                dbType = DBInfo.DataBaseType.MySQL;
                break;
            case 3:
                dbType = DBInfo.DataBaseType.PostgreSQL;
                break;
            case 4:
                dbType = DBInfo.DataBaseType.SQLServer;
                break;
        }

        /*******************实例化DBInfo************************/
        DBInfo dbInfo = new DBInfo(dbType,host,port,dbName,userName,password);

        /*******************实例化DBConnectionUtil************************/
        DBConnectionUtil dbConnectionUtil = new DBConnectionUtil(dbInfo);


        /*******************查询数据库中的表并输出************************/
        List<DBTable> list = dbConnectionUtil.getTableName();
        System.out.println("————————————————————————————————————");
        System.out.println("|数据库有这些表:\t\t\t\t\t\t|");
        System.out.println("————————————————————————————————————");
        for(int i = 0;i < list.size();i++){
            System.out.println("|序号"+i+" "+list.get(i).getName());
        }
        System.out.println("获取全部请键入999");
        System.out.println("————————————————————————————————————");
        //请输入请求表名，获取表中字段
        System.out.println("请输入请求表名：");
        int i = in.nextInt();

        /*******************获取表中字段************************/
        if(i == 999){ //批量操作
            for(int j = 0; j < list.size(); j++){
                List<DBColumn> listc = dbConnectionUtil.getColumnInfo(list.get(j).getName());
                CreateEntity createEntity = new CreateEntity();
                //生成实体
                boolean result = createEntity.create(list.get(j).getName(),listc);
                System.out.println("生成是否成功："+result);
            }
        }else{  //单条操作
            List<DBColumn> listc = dbConnectionUtil.getColumnInfo(list.get(i).getName());
            System.out.println("————————————————————————————————————");
            System.out.println("|表中有这些字段：");
            System.out.println("————————————————————————————————————");
            for(int j = 0;j < listc.size();j++){
                System.out.println("|字段名："+listc.get(j).getName()+"   数据类型是："+listc.get(j).getJavaType());
            }
            System.out.println("————————————————————————————————————");

            /*******************实例化CreateEntity************************/
            CreateEntity createEntity = new CreateEntity();
            //生成实体
            boolean result = createEntity.create(list.get(i).getName(),listc);
            System.out.println("生成是否成功："+result);
        }
        /*****************The end************************/

    }
}
