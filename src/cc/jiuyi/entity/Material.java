package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
/**
 * 实体类 - 物料基本表
 * @param args
 */


@Entity
public class Material extends BaseEntity{

	private static final long serialVersionUID = -7988553859353286910L;
	private String materialCode;//物料编码
    private String materialName;//物料名称
    private String orderUnit;//订单单位
    private String materialUnit;//基本单位
    private String materialType;//物料类型
    private String productmanager;//生产管理员
    private FactoryUnit factoryunit;//单元
    private String cqmultiple;//裁切倍数
    private String isRepair;//是否返修发货
    
    //假字段
    private String xfactoryunit;//单元名称
    private String xworkshop;//车间
    private String xfactory;//工厂名称
    
	public String getProductmanager() {
		return productmanager;
	}
	public void setProductmanager(String productmanager) {
		this.productmanager = productmanager;
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
	public String getOrderUnit() {
		return orderUnit;
	}
	public void setOrderUnit(String orderUnit) {
		this.orderUnit = orderUnit;
	}
	public String getMaterialUnit() {
		return materialUnit;
	}
	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public FactoryUnit getFactoryunit()
	{
		return factoryunit;
	}
	public void setFactoryunit(FactoryUnit factoryunit)
	{
		this.factoryunit = factoryunit;
	}
	@Transient
	public String getXfactoryunit()
	{
		return xfactoryunit;
	}
	public void setXfactoryunit(String xfactoryunit)
	{
		this.xfactoryunit = xfactoryunit;
	}
	@Transient
	public String getXworkshop()
	{
		return xworkshop;
	}
	public void setXworkshop(String xworkshop)
	{
		this.xworkshop = xworkshop;
	}
	@Transient
	public String getXfactory()
	{
		return xfactory;
	}
	public void setXfactory(String xfactory)
	{
		this.xfactory = xfactory;
	}
	public String getCqmultiple() {
		return cqmultiple;
	}
	public void setCqmultiple(String cqmultiple) {
		this.cqmultiple = cqmultiple;
	}
	public String getIsRepair() {
		return isRepair;
	}
	public void setIsRepair(String isRepair) {
		this.isRepair = isRepair;
	}
	
	
}
