package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 产品Bom
 * @param args
 */


@Entity
@Table(name = "Material")
public class Material extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 994338331353572158L;
	
    private String materialCode;//组件编码
    private String materialName;//组件名称
    private String materialUnit;//组件单位
    private Double materialAmount;//组件数量
    private Double spread;//展开层
    private String project;//项目
    private String runOver;//溢出指示符
    private String projectType;//项目类别
    private String exception;//例外
    private String batch;//批次
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    private String isCarton;//是否为纸箱
    private String stateCarton;//纸箱状态描述
    private String productsCode;//产品编码
    private String productsName;

	private Set<Products> products;// 产品Bom
	private Set<HandOverProcess> handoverprocessSet;//交接
	
	//假字段gaoyf
	private String xsmreson;//报废信息原因
	private String xsbids;//报废bug中缺陷表id
	private String xsbnums;//报废bug中缺陷数量
	private Double xmenge;//报废信息每条物料的缺陷总数量
	private String xsmduty;//报废信息责任划分
	private String xsmid;//报废信息表主键ID
	
	//假字段Reece
	private String xfailReason;//不合格原因信息
	private Double xfailAmount;//不合格数量
	private Double xtestAmount;//抽检数量
	private String xgoodsSzie1;//尺寸1
	private String xgoodsSzie2;//尺寸2
	private String xgoodsSzie3;//尺寸3
	private String xgoodsSzie4;//尺寸4
	private String xgoodsSzie5;//尺寸5
	private String xrecordid;//缺陷表id
	private String xrecordNum;//缺陷表中缺陷数量
	private String xitid;//半成品巡检从表表ID
	
	
	@ManyToMany(fetch = FetchType.LAZY,mappedBy="material")
	public Set<Products> getProducts() {
		return products;
	}
	public void setProducts(Set<Products> products) {
		this.products = products;
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

	public String getMaterialUnit() {
		return materialUnit;
	}
	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}
	
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
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

	public Double getMaterialAmount() {
		return materialAmount;
	}
	public void setMaterialAmount(Double materialAmount) {
		this.materialAmount = materialAmount;
	}
	public Double getSpread() {
		return spread;
	}
	public void setSpread(Double spread) {
		this.spread = spread;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getRunOver() {
		return runOver;
	}
	public void setRunOver(String runOver) {
		this.runOver = runOver;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	
	@Transient
	public String getProductsCode() {
		return productsCode;
	}
	public void setProductsCode(String productsCode) {
		this.productsCode = productsCode;
	}
	
	@Transient
	public String getProductsName() {
		return productsName;
	}
	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}
	
	public String getIsCarton() {
		return isCarton;
	}
	public void setIsCarton(String isCarton) {
		this.isCarton = isCarton;
	}
	
	@Transient
	public String getStateCarton() {
		return stateCarton;
	}
	public void setStateCarton(String stateCarton) {
		this.stateCarton = stateCarton;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="material")
	public Set<HandOverProcess> getHandoverprocessSet() {
		return handoverprocessSet;
	}
	public void setHandoverprocessSet(Set<HandOverProcess> handoverprocessSet) {
		this.handoverprocessSet = handoverprocessSet;
	}
	@Transient
	public String getXsmreson()
	{
		return xsmreson;
	}
	public void setXsmreson(String xsmreson)
	{
		this.xsmreson = xsmreson;
	}
	@Transient
	public String getXsbids()
	{
		return xsbids;
	}
	public void setXsbids(String xsbids)
	{
		this.xsbids = xsbids;
	}
	@Transient
	public String getXsbnums()
	{
		return xsbnums;
	}
	public void setXsbnums(String xsbnums)
	{
		this.xsbnums = xsbnums;
	}
	@Transient
	public Double getXmenge()
	{
		return xmenge;
	}
	public void setXmenge(Double xmenge)
	{
		this.xmenge = xmenge;
	}
	@Transient
	public String getXsmduty()
	{
		return xsmduty;
	}
	public void setXsmduty(String xsmduty)
	{
		this.xsmduty = xsmduty;
	}

	
	@Transient
	public String getXfailReason() {
		return xfailReason;
	}
	public void setXfailReason(String xfailReason) {
		this.xfailReason = xfailReason;
	}
	
	@Transient
	public String getXrecordid() {
		return xrecordid;
	}
	public void setXrecordid(String xrecordid) {
		this.xrecordid = xrecordid;
	}
	
	@Transient
	public String getXrecordNum() {
		return xrecordNum;
	}
	public void setXrecordNum(String xrecordNum) {
		this.xrecordNum = xrecordNum;
	}
	
	
	@Transient
	public String getXsmid()
	{
		return xsmid;
	}
	public void setXsmid(String xsmid)
	{
		this.xsmid = xsmid;
	}
	
	@Transient
	public Double getXfailAmount() {
		return xfailAmount;
	}
	public void setXfailAmount(Double xfailAmount) {
		this.xfailAmount = xfailAmount;
	}
	
	@Transient
	public Double getXtestAmount() {
		return xtestAmount;
	}
	public void setXtestAmount(Double xtestAmount) {
		this.xtestAmount = xtestAmount;
	}
	
	@Transient
	public String getXgoodsSzie1() {
		return xgoodsSzie1;
	}
	public void setXgoodsSzie1(String xgoodsSzie1) {
		this.xgoodsSzie1 = xgoodsSzie1;
	}
	
	@Transient
	public String getXgoodsSzie2() {
		return xgoodsSzie2;
	}
	public void setXgoodsSzie2(String xgoodsSzie2) {
		this.xgoodsSzie2 = xgoodsSzie2;
	}
	
	
	@Transient
	public String getXgoodsSzie3() {
		return xgoodsSzie3;
	}
	public void setXgoodsSzie3(String xgoodsSzie3) {
		this.xgoodsSzie3 = xgoodsSzie3;
	}
	
	@Transient
	public String getXgoodsSzie4() {
		return xgoodsSzie4;
	}
	public void setXgoodsSzie4(String xgoodsSzie4) {
		this.xgoodsSzie4 = xgoodsSzie4;
	}
	
	@Transient
	public String getXgoodsSzie5() {
		return xgoodsSzie5;
	}
	public void setXgoodsSzie5(String xgoodsSzie5) {
		this.xgoodsSzie5 = xgoodsSzie5;
	}
	
	@Transient
	public String getXitid() {
		return xitid;
	}
	public void setXitid(String xitid) {
		this.xitid = xitid;
	}
	
	
	
	
	
}
