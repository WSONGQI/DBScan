package wusongqi.dbscan.util;

import wusongqi.dbscan.model.DBColumn;
import wusongqi.dbscan.model.Entity;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建实体
 */
public class CreateEntity {

    private static File[] javaFile = null;

    public boolean create(String tableName, List<DBColumn> dbColumns){
        Configuration cfg = new Configuration();

        try{
            // 步骤一：指定 模板文件从何处加载的数据源，这里设置一个文件目录
            File templateFile = new File("src/main/resources/template");
            if(!templateFile.exists()){
                templateFile.mkdirs();
            }
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/template"));

            cfg.setObjectWrapper(new DefaultObjectWrapper());

            // 步骤二：获取 模板文件 顺序要和javaFile一致
            Template template[] = {cfg.getTemplate("entityTemplate.ftl"),
                    cfg.getTemplate("daoTemplate.ftl"),
                    cfg.getTemplate("serviceITemplate.ftl"),
                    cfg.getTemplate("serviceImplTemplate.ftl")};

            // 步骤三：创建 数据模型
            Map<String, Object> root = createDataModel(tableName,dbColumns);

            // 步骤四：合并 模板 和 数据模型
            // 创建.java类文件
            if(javaFile != null){
                for(int i = 0;i < javaFile.length;i++){
                    Writer javaWriter = new FileWriter(javaFile[i]);
                    template[i].process(root, javaWriter);

                    javaWriter.flush();
                    System.out.println("|文件生成路径：" + javaFile[i].getCanonicalPath());
                    System.out.println("————————————————————————————————————");

                    javaWriter.close();
                }
            }
            // 输出到Console控制台
//            Writer out = new OutputStreamWriter(System.out);
//            template.process(root, out);
//            out.flush();
//            out.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (TemplateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Object> createDataModel(String tableName, List<DBColumn> dbColumns){
        Map<String, Object> root = new HashMap<String, Object>();
        Entity entity = new Entity();

        //是否有构造函数
        entity.setConstructors(true);
        //包名
        entity.setJavaPackage("dbscan");
        //把首字母大写，设置类名
        entity.setClassName(formatName(tableName));
        //属性字段传入
        entity.setProperties(dbColumns);

        //创建java类文件
        File outDirFile = new File("D:/Desktop");
        if(!outDirFile.exists()){
            outDirFile.mkdirs();
        }

        // 这里是写入地址，由于我们要创建多个文件dao、entity、service所以用数组
        javaFile = new File[4];
        javaFile[0] = toJavaFilename(outDirFile, entity.getJavaPackage(), entity.getClassName());
        javaFile[1] = toJavaFilename(outDirFile, entity.getJavaPackage(), entity.getClassName()+"Dao");
        javaFile[2] = toJavaFilename(outDirFile, entity.getJavaPackage(), entity.getClassName()+"ServiceI");
        javaFile[3] = toJavaFilename(outDirFile, entity.getJavaPackage(), entity.getClassName()+"ServiceImpl");

        //这个map就是传入ftl页面的
        root.put("entity", entity);
        return root;
    }

    /**
     * 格式化表名
     * 就是首字母大写
     * @param name
     * @return
     */
    public String formatName(String name){
        String resultName="";

        if(name.contains("_")){
            String[] namearr = name.split("_");
            if(namearr.length>0){
                for(int i = 0;i<namearr.length;i++){
                    resultName = resultName + namearr[i].substring(0,1).toUpperCase() + namearr[i].substring(1,namearr[i].length());
                }
                return resultName;
            }
        }
        return name.substring(0,1).toUpperCase() + name.substring(1,name.length());
    }

    /**
     * 创建.java文件所在路径 和 返回.java文件File对象
     * @param outDirFile 生成文件路径
     * @param javaPackage java包名
     * @param javaClassName java类名
     * @return
     */
    private static File toJavaFilename(File outDirFile, String javaPackage, String javaClassName) {
        String packageSubPath = javaPackage.replace('.', '/');
        File packagePath = new File(outDirFile, packageSubPath);
        File file = new File(packagePath, javaClassName + ".java");
        if(!packagePath.exists()){
            packagePath.mkdirs();
        }
        return file;
    }
}
