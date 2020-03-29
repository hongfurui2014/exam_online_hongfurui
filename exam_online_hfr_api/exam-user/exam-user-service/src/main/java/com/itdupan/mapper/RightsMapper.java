package com.itdupan.mapper;

import com.itdupan.pojo.Rights;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RightsMapper extends Mapper<Rights> {

    /**
     * 查询所有菜单
     * @param fkPRightsId
     * @return
     */
    @Select("SELECT * FROM hfr_rights WHERE fk_p_rights_id = #{fkPRightsId};")
    List<Rights> findRightsListByPid(@Param("fkPRightsId") Long fkPRightsId);

    /**
     * 根据id查询一个权限
     * @param rightsId
     * @return
     */
    @Select("SELECT * FROM hfr_rights WHERE rights_id = #{rightsId}")
    Rights findRightsById(@Param("rightsId") Long rightsId);
}
