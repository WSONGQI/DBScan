package wusongqi.dbscan.model;

import java.io.Serializable;

/**
 * 表信息实体
 * 建实体，方便以后要加入其它属性
 */
public class DBTable implements Serializable {
    /**表名*/
    private String name;
    /**表备注*/
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
