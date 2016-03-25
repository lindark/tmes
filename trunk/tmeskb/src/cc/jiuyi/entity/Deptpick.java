package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类——部门领料
 * 
 */
@Entity
public class Deptpick extends BaseEntity {

	private static final long serialVersionUID = 6135149670281339011L;
	
	private Admin createUser;//创建人员
	private Admin comfirmUser;//审批人员
	private String state;//状态
	private String isDel;//标记删除
	private String mblnr;//物料凭证
	private String materialCode;//物料编码
	private String materialName;//物料描述
	private String materialBatch;//批次
	private String repertorySite;//库存地点
	private Double actualMaterialMount;//库存数量
	private Double stockMount;//数量
	private String costcenter;//成本中心
	private String productDate;//生产日期
	private String shift;//班次
	private String movetype;//移动类型
	private String departid;//部门ID
	private String departmentName;//部门描述
	private String movetype1;//退料移动类型
	private String type;
	
	private String e_type;
	private String e_message;
	private String ex_mblnr;
	
	private String xstate;//状态 描述 假字段
	private String xcreateUser;//提交人
	private String xcomfirmUser;//审批人
	private String xtype;//类型
	private String shiftx;
	
	
	
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid = departid;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Admin createUser) {
		this.createUser = createUser;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getComfirmUser() {
		return comfirmUser;
	}
	public void setComfirmUser(Admin comfirmUser) {
		this.comfirmUser = comfirmUser;
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
		if(isDel == null)
			isDel = "N";
		this.isDel = isDel;
	}
	public String getMblnr() {
		return mblnr;
	}
	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
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
	public String getMaterialBatch() {
		return materialBatch;
	}
	public void setMaterialBatch(String materialBatch) {
		this.materialBatch = materialBatch;
	}
	public String getRepertorySite() {
		return repertorySite;
	}
	public void setRepertorySite(String repertorySite) {
		this.repertorySite = repertorySite;
	}
	public Double getActualMaterialMount() {
		return actualMaterialMount;
	}
	public void setActualMaterialMount(Double actualMaterialMount) {
		this.actualMaterialMount = actualMaterialMount;
	}
	public Double getStockMount() {
		return stockMount;
	}
	public void setStockMount(Double stockMount) {
		this.stockMount = stockMount;
	}
	
	public String getCostcenter() {
		return costcenter;
	}
	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}
	@Transient
	public String getXstate() {
		return xstate;
	}
	public void setXstate(String xstate) {
		this.xstate = xstate;
	}
	@Transient
	public String getXcreateUser() {
		return xcreateUser;
	}
	public void setXcreateUser(String xcreateUser) {
		this.xcreateUser = xcreateUser;
	}
	@Transient
	public String getXcomfirmUser() {
		return xcomfirmUser;
	}
	public void setXcomfirmUser(String xcomfirmUser) {
		this.xcomfirmUser = xcomfirmUser;
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
	public String getE_type() {
		return e_type;
	}
	public void setE_type(String e_type) {
		this.e_type = e_type;
	}
	public String getE_message() {
		return e_message;
	}
	public void setE_message(String e_message) {
		this.e_message = e_message;
	}
	public String getEx_mblnr() {
		return ex_mblnr;
	}
	public void setEx_mblnr(String ex_mblnr) {
		this.ex_mblnr = ex_mblnr;
	}
	public String getMovetype() {
		return movetype;
	}
	public void setMovetype(String movetype) {
		this.movetype = movetype;
	}
	public String getMovetype1() {
		return movetype1;
	}
	public void setMovetype1(String movetype1) {
		this.movetype1 = movetype1;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Transient
	public String getXtype() {
		return xtype;
	}
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
	@Transient
	public String getShiftx() {
		return shiftx;
	}
	public void setShiftx(String shiftx) {
		this.shiftx = shiftx;
	}
	
	
}
