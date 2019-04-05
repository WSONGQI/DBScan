package wusongqi.dbscan.model;

import java.util.List;

public class Entity {
    /**实体所在的包名*/
    private String javaPackage;
    /**实体类名*/
    private String className;
    /**父类名*/
    private String superclass;
    /**是否有构造函数*/
    private boolean constructors;
    /**属性集合*/
    private List<DBColumn> properties;



    public List<DBColumn> getProperties() {
        return properties;
    }
    public void setProperties(List<DBColumn> properties) {
        this.properties = properties;
    }
    public String getJavaPackage() {
        return javaPackage;
    }

    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSuperclass() {
        return superclass;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }
    public boolean isConstructors() {
        return constructors;
    }

    public void setConstructors(boolean constructors) {
        this.constructors = constructors;
    }
}
