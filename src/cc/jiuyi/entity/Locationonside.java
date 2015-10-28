package cc.jiuyi.entity;

import javax.persistence.Entity;

import org.compass.annotations.Searchable;

/**
 * 实体类——线边仓
 * 
 */
@Entity
@Searchable
public class Locationonside extends BaseEntity {

	private static final long serialVersionUID = 8226157605987504110L;

	private String locationCode;// 线边仓编码
	private String locationName;// 线边仓名称

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}
