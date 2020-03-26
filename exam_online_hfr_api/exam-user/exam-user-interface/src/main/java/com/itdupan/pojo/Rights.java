package com.itdupan.pojo;

import javax.persistence.*;
import java.util.List;

@Table(name = "hfr_rights")
public class Rights{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rightsId;

    private String rightsAuthname;

    private String rightsPath;

    private String rigthsIcon;

    private Integer rightsLevel;

    @Transient
    private List<Rights> children;

    public Rights() {
    }

    public Long getRightsId() {
        return rightsId;
    }

    public void setRightsId(Long rightsId) {
        this.rightsId = rightsId;
    }

    public String getRightsAuthname() {
        return rightsAuthname;
    }

    public void setRightsAuthname(String rightsAuthname) {
        this.rightsAuthname = rightsAuthname;
    }

    public String getRightsPath() {
        return rightsPath;
    }

    public void setRightsPath(String rightsPath) {
        this.rightsPath = rightsPath;
    }

    public String getRigthsIcon() {
        return rigthsIcon;
    }

    public void setRigthsIcon(String rigthsIcon) {
        this.rigthsIcon = rigthsIcon;
    }

    public Integer getRightsLevel() {
        return rightsLevel;
    }

    public void setRightsLevel(Integer rightsLevel) {
        this.rightsLevel = rightsLevel;
    }

    public List<Rights> getChildren() {
        return children;
    }

    public void setChildren(List<Rights> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Rights{" +
                "rightsId=" + rightsId +
                ", rightsAuthname='" + rightsAuthname + '\'' +
                ", rightsPath='" + rightsPath + '\'' +
                ", rigthsIcon='" + rigthsIcon + '\'' +
                ", rightsLevel=" + rightsLevel +
                ", children=" + children +
                '}';
    }
}
