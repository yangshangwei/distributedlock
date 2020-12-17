package com.artisan.distributedlock.mapper;

import com.artisan.distributedlock.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface OrderMapper {

    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    @Insert(" insert into `order`(user_id,pid) values(#{userId},#{pid}) ")
    int insert(Order order);
}
