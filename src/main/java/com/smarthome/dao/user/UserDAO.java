package com.smarthome.dao.user;

import com.smarthome.commons.annotation.MapperRepository;
import com.smarthome.model.user.TabUserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by wushenjun on 2018/6/1.
 */
@MapperRepository
public interface UserDAO {

    /**
     * 通过电话号码获取用户信息
     * @param phone
     * @return
     */
    @Select(" select id, login, password, login_salt as loginSalt, phone from db_smart_home.ts_user where phone=#{phone} limit 1 ")
    TabUserInfo getUserInfoByPhone(@Param("phone") String phone);


    /**
     * 通过电话号码获取用户ID
     * @param phone
     * @return
     */
    @Select(" select id from db_smart_home.ts_user where phone=#{phone}")
    String getUserIdByPhone(@Param("phone") String phone);


}
