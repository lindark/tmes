package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.compass.annotations.Searchable;

@Entity
@Searchable
@Table(name = "PositionManagement")
public class PositionManagement extends BaseEntity{

	/**
	 * 实体类 - 仓位管理
	 * @param args
	 */
		
	private static final long serialVersionUID = -4012609509119465713L;
	
	private String element;//单元
	private String warehouse;//仓库地点
	private String supermarketWarehouse;//超市仓库
	private String trimWareHouse;//裁切仓库
	private String isDel;//是否删除
	
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getSupermarketWarehouse() {
		return supermarketWarehouse;
	}
	public void setSupermarketWarehouse(String supermarketWarehouse) {
		this.supermarketWarehouse = supermarketWarehouse;
	}
	public String getTrimWareHouse() {
		return trimWareHouse;
	}
	public void setTrimWareHouse(String trimWareHouse) {
		this.trimWareHouse = trimWareHouse;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	@Override
	public String toString() {
		return "PositionManagement [element=" + element + ", warehouse="
				+ warehouse + ", supermarketWarehouse=" + supermarketWarehouse
				+ ", trimWareHouse=" + trimWareHouse + ", isDel=" + isDel + "]";
	}
	
	

}
