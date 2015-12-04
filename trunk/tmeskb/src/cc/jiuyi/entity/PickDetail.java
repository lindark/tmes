package cc.jiuyi.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 领料/退料从表
 * @param args
 */


@Entity
@Searchable
@Table(name = "PickDetail")
public class PickDetail extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5863643257488788810L;

	private String pickType;//领料类型
    private String pickAmount;//领料数量
    private Integer stockAmount;//库存数量
   // private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    private Admin confirmUser;//确认人
      
	private String materialCode;//组件编码
	private String materialName;//组件名称
	private String charg;//批号
	private String item_text;//项目文本
	private String orderid;//工单号
	private Pick pick;//领料主表
	private String meins;//单位
	
	
    private WorkingBill workingbill;//随工单
   
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}
	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	public Pick getPick() {
		return pick;
	}
	public void setPick(Pick pick) {
		this.pick = pick;
	}
	public String getPickType() {
		return pickType;
	}
	public void setPickType(String pickType) {
		this.pickType = pickType;
	}
	
	
	
	public String getCharg() {
		return charg;
	}
	public void setCharg(String charg) {
		this.charg = charg;
	}
	public String getItem_text() {
		return item_text;
	}
	public void setItem_text(String item_text) {
		this.item_text = item_text;
	}
	
	public String getMeins() {
		return meins;
	}
	public void setMeins(String meins) {
		this.meins = meins;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getPickAmount() {
		return pickAmount;
	}
	public void setPickAmount(String pickAmount) {
		this.pickAmount = pickAmount;
	}
	public Integer getStockAmount() {
		return stockAmount;
	}
	public void setStockAmount(Integer stockAmount) {
		this.stockAmount = stockAmount;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getConfirmUser() {
		return confirmUser;
	}
	public void setConfirmUser(Admin confirmUser) {
		this.confirmUser = confirmUser;
	}
	
//	public String getState() {
//		return state;
//	}
//	
//	public void setState(String state) {
//		this.state = state;
//	}
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
	

	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	
}
