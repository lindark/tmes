package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 领料/退料从表
 * @param args
 */


@Entity
@Searchable
@Table(name = "PickType")
public class PickDetail extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5863643257488788810L;
	
	
	private String pickType;//领料类型
    private Integer pickAmount;//领料数量
    private Integer StockAmount;//库存数量
    private String ConfirmUser;//确认人
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
      
	
	public String getPickType() {
		return pickType;
	}
	public void setPickType(String pickType) {
		this.pickType = pickType;
	}
	public Integer getPickAmount() {
		return pickAmount;
	}
	public void setPickAmount(Integer pickAmount) {
		this.pickAmount = pickAmount;
	}
	public Integer getStockAmount() {
		return StockAmount;
	}
	public void setStockAmount(Integer stockAmount) {
		StockAmount = stockAmount;
	}
	public String getConfirmUser() {
		return ConfirmUser;
	}
	public void setConfirmUser(String confirmUser) {
		ConfirmUser = confirmUser;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
        if(isDel==null){
        	isDel="N";
        }
		this.isDel = isDel;
	}
	
	 @Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

   

    
	
}
