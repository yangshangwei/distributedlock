package com.artisan.distributedlock.mapper;

import com.artisan.distributedlock.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface ProductMapper {

    @Select(" select * from product where id=#{id}  ")
    Product getProduct(@Param("id") Integer id);

    @Update(" update product set stock=stock-1    where id=#{id}  ")
    int deductStock(@Param("id") Integer id);
}
