package com.zxf.entity;

import java.io.Serializable;
/**
 * 城市实体类
 * @author lancoo
 *
 */
public class City implements Serializable {
	
	private static final long serialVersiomUID = -1L;
	
    private int id;//城市编号
    private int provinceId;//省份编号
    private String cityName;//城市名称
    private String description;//描述
    
    /**
     * 无参构造方法
     */
    public City() {
		super();
		// TODO Auto-generated constructor stub
	}
    /**
     * 全参数构造方法
     * @param id
     * @param provinceId
     * @param cityName
     * @param description
     */
	public City(int id, int provinceId, String cityName, String description) {
		super();
		this.id = id;
		this.provinceId = provinceId;
		this.cityName = cityName;
		this.description = description;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	@Override
	public String toString() {
		return "City [id=" + id + ", provinceId=" + provinceId + ", cityName=" + cityName + ", description="
				+ description + "]";
	}

}
