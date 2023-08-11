package com.hua.community.mapper;

import com.hua.community.model.MyUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface  UserMapper {
    @Insert("insert into MYUSER (ACCOUNT_ID, NAME, TOKEN, GMT_CREATE, GMT_MODIFIED)  values (#{accountid},#{name},#{token},#{gmtcreate},#{gmtmodified})")
    void insert(MyUser myuser);
}
