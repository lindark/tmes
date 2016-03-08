package cc.jiuyi.entity;

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
public class PickDetail extends BaseEntity implements Comparable<PickDetail>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5863643257488788810L;

	private String pickType;//领料类型
    private String pickAmount;//实际领料数量
    private String stockAmount;//库存数量
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
	private String xh;//序号
	
	private String cqPickAmount;//裁切领退数
	private String cqmultiple;//裁切倍数
	private String cqhStockAmount;//裁切后库存
	
	/**假字段**/
	private String productDate;//生产日期
	private String workingbillCode;//随工单编号
	private String xpickType;//领料类型描述
	private String maktx;//产品描述
	private String xconfirmUser;//确认人
	private String xcreateUser;//创建人
	private String xmblnr;//物料凭证号

	private String xuh;

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
	
	public String getXuh() {
		return xuh;
	}
	public void setXuh(String xuh) {
		this.xuh = xuh;
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

	
	public String getStockAmount() {
		return stockAmount;
	}
	public void setStockAmount(String stockAmount) {
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
	
	 @Transient
	public String getXpickType() {
		return xpickType;
	}
	public void setXpickType(String xpickType) {
		this.xpickType = xpickType;
	}
	
	 @Transient
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getCqPickAmount() {
		return cqPickAmount;
	}
	public void setCqPickAmount(String cqPickAmount) {
		this.cqPickAmount = cqPickAmount;
	}
	public String getCqmultiple() {
		return cqmultiple;
	}
	public void setCqmultiple(String cqmultiple) {
		this.cqmultiple = cqmultiple;
	}
	public String getCqhStockAmount() {
		return cqhStockAmount;
	}
	public void setCqhStockAmount(String cqhStockAmount) {
		this.cqhStockAmount = cqhStockAmount;
	}
	@Override
	public int compareTo(PickDetail o) {
		return this.materialCode.compareTo(o.getMaterialCode());
	}
	
	@Transient
	public String getWorkingbillCode() {
		return workingbillCode;
	}
	public void setWorkingbillCode(String workingbillCode) {
		this.workingbillCode = workingbillCode;
	}
	
	@Transient
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	
	@Transient
	public String getXconfirmUser() {
		return xconfirmUser;
	}
	public void setXconfirmUser(String xconfirmUser) {
		this.xconfirmUser = xconfirmUser;
	}
	
	@Transient
	public String getXcreateUser() {
		return xcreateUser;
	}
	public void setXcreateUser(String xcreateUser) {
		this.xcreateUser = xcreateUser;
	}
	
	@Transient
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	
	@Transient
	public String getXmblnr() {
		return xmblnr;
	}
	public void setXmblnr(String xmblnr) {
		this.xmblnr = xmblnr;
	}

	
}
