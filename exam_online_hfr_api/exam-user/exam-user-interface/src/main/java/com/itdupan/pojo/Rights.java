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

    private String rightsIcon;

    private Integer rightsLevel;

    private Long fkPRightsId;

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

    public String getRightsIcon() {
        return rightsIcon;
    }

    public void setRightsIcon(String rightsIcon) {
        this.rightsIcon = rightsIcon;
    }

    public Integer getRightsLevel() {
        return rightsLevel;
    }

    public void setRightsLevel(Integer rightsLevel) {
        this.rightsLevel = rightsLevel;
    }

    public Long getFkPRightsId() {
        return fkPRightsId;
    }

    public void setFkPRightsId(Long fkPRightsId) {
        this.fkPRightsId = fkPRightsId;
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
                ", rightsIcon='" + rightsIcon + '\'' +
                ", rightsLevel=" + rightsLevel +
                ", fkPRightsId=" + fkPRightsId +
                ", children=" + children +
                '}';
    }
}
