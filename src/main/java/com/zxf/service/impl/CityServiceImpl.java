package com.zxf.service.impl;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.zxf.entity.City;
import com.zxf.mapper.CityDao;
import com.zxf.service.CityService;

@Service
public class CityServiceImpl implements CityService{
	private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

	@Autowired
    private CityDao cityDao;

    @Autowired
    private RedisTemplate redisTemplate;
    
    /**
     * 获取城市逻辑：
     * 如果缓存存在，从缓存中获取城市信息
     * 如果缓存不存在，从 DB 中获取城市信息，然后插入缓存
     */
    public City findCityById(int id) {
        // 从缓存中获取城市信息
        String key = "city_" + id;
        ValueOperations<String, City> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            City city = operations.get(key);
            System.out.println("从缓存中获取....");
            logger.info("CityServiceImpl.findCityById() : 从缓存中获取了城市 >> " + city.toString());
            return city;
        }

        // 从 DB 中获取城市信息
        City city = cityDao.findById(id);
        System.out.println("从DB中获取....");
        // 插入缓存
        operations.set(key, city, 60, TimeUnit.SECONDS);
        logger.info("CityServiceImpl.findCityById() : 城市插入缓存 >> " + city.toString());

        return city;
    }

    @Override
    public int saveCity(City city) {
        return cityDao.saveCity(city);
    }

    /**
     * 更新城市逻辑：
     * 如果缓存存在，删除
     * 如果缓存不存在，不操作
     */
    @Override
    public int updateCity(City city) {
        int ret = cityDao.updateCity(city);

        // 缓存存在，删除缓存
        String key = "city_" + city.getId();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);

            logger.info("CityServiceImpl.updateCity() : 从缓存中删除城市 >> " + city.toString());
        }

        return ret;
    }

    @Override
    public int deleteCity(int id) {

        int ret = cityDao.deleteCity(id);
        // 缓存存在，删除缓存
        String key = "city_" + id;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);

            logger.info("CityServiceImpl.deleteCity() : 从缓存中删除城市 ID >> " + id);
        }
        return ret;
    }
}
