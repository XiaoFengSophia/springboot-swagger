package com.zxf.service;

import com.zxf.entity.City;

public interface CityService {
	/**
     * 根据城市 ID,查询城市信息
     *
     * @param id
     * @return
     */
    City findCityById(int id);

    /**
     * 新增城市信息
     *
     * @param city
     * @return
     */
    int saveCity(City city);

    /**
     * 更新城市信息
     *
     * @param city
     * @return
     */
    int updateCity(City city);

    /**
     * 根据城市 ID,删除城市信息
     *
     * @param id
     * @return
     */
    int deleteCity(int id);

}
