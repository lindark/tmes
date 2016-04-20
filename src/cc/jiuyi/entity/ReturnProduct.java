package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * 实体类——中转仓入库
 * 
 */
@Entity
public class ReturnProduct extends BaseEntity {
	
	private static final long serialVersionUID = -3682905813737033723L;
	
	private Double stockMout;//退回数量
	private String repertorySite;//库存地点
	private String repertorySiteDesp;// 库存地点描述
	private String receiveRepertorySite;//接收库存地点
	private String receiveRepertorySiteDesp;// 库存地点描述
	private String createUser;// 创建人
	private String createName;// 创建人的名字
	private String confirmUser;// 确认人
	private String confirmName;// 确认人的名字
	private String state;// 状态描述
	private String isDel;// 是否删除
	private String materialCode;//物料编码
	private String materialDesp;//物料描述
	private String materialBatch;//批次
	private Double actualMaterialMount;//实际数量
	private String type;//类型（成品、半成品）
	private String mblnr;
	
	private String xstate;
	
	
	private String productDate;//生产日期
	private String shift;//班次
	private String factoryCode;//单元编号
	private String factoryDesp;//单元名称
	
	/**
	 * 假字段
	 * @return
	 */
	
	private String budate;//过账日期
	private String werks;//工厂
	private String moveType;//移动类型 311
	private String e_type;
	private String e_message;
	private String ex_mblnr;
	
	public Double getStockMout() {
		return stockMout;
	}

	public void setStockMout(Double stockMout) {
		this.stockMout = stockMout;
	}

	public String getRepertorySite() {
		return repertorySite;
	}

	public void setRepertorySite(String repertorySite) {
		this.repertorySite = repertorySite;
	}

	public String getReceiveRepertorySite() {
		return receiveRepertorySite;
	}

	public void setReceiveRepertorySite(String receiveRepertorySite) {
		this.receiveRepertorySite = receiveRepertorySite;
	}

	
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}



	public String getConfirmName() {
		return confirmName;
	}

	public void setConfirmName(String confirmName) {
		this.confirmName = confirmName;
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
			isDel = "0";
		}
		this.isDel = isDel;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialDesp() {
		return materialDesp;
	}

	public void setMaterialDesp(String materialDesp) {
		this.materialDesp = materialDesp;
	}

	public String getMaterialBatch() {
		return materialBatch;
	}

	public void setMaterialBatch(String materialBatch) {
		this.materialBatch = materialBatch;
	}



	public String getReceiveRepertorySiteDesp() {
		return receiveRepertorySiteDesp;
	}

	public void setReceiveRepertorySiteDesp(String receiveRepertorySiteDesp) {
		this.receiveRepertorySiteDesp = receiveRepertorySiteDesp;
	}

	public String getRepertorySiteDesp() {
		return repertorySiteDesp;
	}

	public void setRepertorySiteDesp(String repertorySiteDesp) {
		this.repertorySiteDesp = repertorySiteDesp;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMblnr() {
		return mblnr;
	}

	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}

	public Double getActualMaterialMount() {
		return actualMaterialMount;
	}

	public void setActualMaterialMount(Double actualMaterialMount) {
		this.actualMaterialMount = actualMaterialMount;
	}
	@Transient
	public String getBudate() {
		return budate;
	}

	public void setBudate(String budate) {
		this.budate = budate;
	}
	@Transient
	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}
	@Transient
	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}
	@Transient
	public String getE_type() {
		return e_type;
	}

	public void setE_type(String e_type) {
		this.e_type = e_type;
	}
	@Transient
	public String getE_message() {
		return e_message;
	}

	public void setE_message(String e_message) {
		this.e_message = e_message;
	}
	@Transient
	public String getEx_mblnr() {
		return ex_mblnr;
	}

	public void setEx_mblnr(String ex_mblnr) {
		this.ex_mblnr = ex_mblnr;
	}

	@Transient
	public String getXstate() {
		return xstate;
	}

	public void setXstate(String xstate) {
		this.xstate = xstate;
	}

	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getFactoryDesp() {
		return factoryDesp;
	}

	public void setFactoryDesp(String factoryDesp) {
		this.factoryDesp = factoryDesp;
	}

	
}
