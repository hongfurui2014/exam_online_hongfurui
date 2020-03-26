package com.itdupan.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "hfr_role_rights")
public class RoleRights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleRightId;

    private Long fkRoleId;

    private Long fkRightsId;

    public RoleRights() {
    }

    public Long getRoleRightId() {
        return roleRightId;
    }

    public void setRoleRightId(Long roleRightId) {
        this.roleRightId = roleRightId;
    }

    public Long getFkRoleId() {
        return fkRoleId;
    }

    public void setFkRoleId(Long fkRoleId) {
        this.fkRoleId = fkRoleId;
    }

    public Long getFkRightsId() {
        return fkRightsId;
    }

    public void setFkRightsId(Long fkRightsId) {
        this.fkRightsId = fkRightsId;
    }

    @Override
    public String toString() {
        return "RoleRights{" +
                "roleRightId=" + roleRightId +
                ", fkRoleId=" + fkRoleId +
                ", fkRightsId=" + fkRightsId +
                '}';
    }
}
