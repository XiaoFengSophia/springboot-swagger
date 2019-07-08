package com.zxf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zxf.entity.City;
@Mapper
public interface CityDao {
	 /**
     * 获取城市信息列表
     *
     * @return
     */
    List<City> findAllCity();

    /**
     * 根据城市 ID，获取城市信息
     *
     * @param id
     * @return
     */
    @Select("select * from city where id=#{id}")
    City findById(@Param("id") Long id);
    
    @Insert("insert into city(id,provinceId,cityName,description) values(#{id}, #{provinceId}, #{cityName},#{description})")
    Long saveCity(City city);

    Long updateCity(City city);
    
    @Delete("delete from city where id=#{id}")
    Long deleteCity(Long id);

}
