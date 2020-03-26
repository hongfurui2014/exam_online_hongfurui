package com.itdupan.mapper;

import com.itdupan.pojo.RoleRights;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleRightsMapper extends Mapper<RoleRights> {


    @Select("select * from hfr_role_rights where fk_role_id = #{fkRoleId}")
    List<RoleRights> getRightsIdsByRoleId(@Param("fkRoleId") Long fkRoleId);

    @Delete("delete from hfr_role_rights where fk_role_id = #{fkRoleId}")
    void delRolesAndRights(@Param("fkRoleId")Long fkRoleId);

    @Insert("INSERT INTO hfr_role_rights(fk_role_id, fk_rights_id) VALUES (#{fkRoleId}, #{fkRightsId})")
    void insertRoleAndRights(@Param("fkRoleId")Long fkRoleId, @Param("fkRightsId")Long fkRightsId);
}
