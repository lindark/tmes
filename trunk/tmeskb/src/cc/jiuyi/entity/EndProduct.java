package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * 实体类——成品入库
 * 
 */
@Entity
public class EndProduct extends BaseEntity {

	private static final long serialVersionUID = 3182546927519079192L;
	
	private Double stockMout;//入库数量
	private String repertorySite;//库存地点
	private String receiveRepertorySite;//接收库存地点
	private Admin createUser;// 创建人
	private String createName;// 创建人的名字
	private Admin confirmUser;// 确认人
	private String confirmName;// 确认人的名字
	private String state;// 状态描述
	private String isDel;// 是否删除
	private String materialCode;//物料编码
	private String materialDesp;//物料描述
	private String materialBatch;//批次
	private Double ActualMaterialMount;//实际数量
	
	
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

	public Admin getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Admin createUser) {
		this.createUser = createUser;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Admin getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(Admin confirmUser) {
		this.confirmUser = confirmUser;
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

	public Double getActualMaterialMount() {
		return ActualMaterialMount;
	}

	public void setActualMaterialMount(Double actualMaterialMount) {
		ActualMaterialMount = actualMaterialMount;
	}



	
	
	
}
