package cn.activiti.cn.activiti.bean;

import java.io.Serializable;

/**
 * Administrator
 * 2019/2/20
 */
public class Holiday implements Serializable {

    private String xiaozong;

    private String name;

    private Integer num;

    public String getXiaozong() {
        return xiaozong;
    }

    public void setXiaozong(String xiaozong) {
        this.xiaozong = xiaozong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
