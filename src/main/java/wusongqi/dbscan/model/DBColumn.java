package wusongqi.dbscan.model;

/**
 * 存放数据库列字段
 */
public class DBColumn {
    private String name;
    private String comment;
    /**
     * 字段名的类型
     */
    private String javaType;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }


}
