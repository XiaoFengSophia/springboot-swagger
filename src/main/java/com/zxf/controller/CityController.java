package com.zxf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zxf.entity.City;
import com.zxf.service.CityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
@Api(value="CityController",tags= {"城市文档接口"})
@RestController
public class CityController {
	@Autowired
    private CityService cityService;

	@GetMapping("/index")
	public String Index(String index) {
		
		return index;
	}
	@ApiOperation(value = "查询城市", notes = "根据id查询城市")
	@ApiImplicitParam(name = "id", value = "城市id", required = true, dataType = "Long", paramType = "path")
    @GetMapping(value = "/findCityById/{id}")
    public City findCityById(@PathVariable("id") Long id) {
        return cityService.findCityById(id);
    }

	@ApiOperation("新增城市信息")
	@ApiImplicitParam(name = "城市对象", value = "传入json格式", required = true)
    @PostMapping(value = "/saveCity")
    public void saveCity(@RequestBody City city) {
		
        cityService.saveCity(city);
    }

    @GetMapping(value = "updateCity")
    public void updateCity(@RequestBody City city) {
        cityService.updateCity(city);
    }
    
    @ApiOperation(value = "删除城市", notes = "根据id删除城市")
	@ApiImplicitParam(name = "id", value = "城市id", required = true, dataType = "Long", paramType = "path")
    @GetMapping(value = "/deleteCity/{id}")
    public void deleteCity(@PathVariable("id") Long id) {
        cityService.deleteCity(id);
    }

}
