package com.smarthome.dao.log;

import com.smarthome.model.log.TabAppVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AppVersionDAO {

    /**
     * 根据app类型获取最新版本信息
     * @param appType
     * @return
     */
    @Select(" select " +
            "   id, device_type as deviceType,version," +
            "   update_time as updateTime,content,img,down_url as downUrl,package_url as packageUrl, " +
            "   is_mustupdate as isMustupdate,force_update_ver as forceUpdateVer,ctime " +
            " from db_smart_home.ts_app_version " +
            " where device_type = #{appType} " +
            " order by ctime DESC " +
            " limit 1  ")
    @Cacheable(value = "getNewAppVersionInfoByType", sync = true)
    TabAppVersion getNewAppVersionInfoByType(@Param("appType") int appType);

}
