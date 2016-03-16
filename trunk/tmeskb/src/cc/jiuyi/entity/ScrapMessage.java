package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 报废信息表
 * @author gaoyf
 *
 */
@Entity
public class ScrapMessage extends BaseEntity
{
	private static final long serialVersionUID = 6351103854134447817L;
	
	private String smmatterNum;//物料编码
	private String smmatterDes;//物料描述
	private String smduty;//责任划分
	private String smreson;//报废原因
	private String isDel;//删除
	private String materialCode;//物料编码
	private Scrap scrap;//报废表：外键
	private Double menge;//数量
	private String charg;//批号
	private String item_text;//项目文本
	private String meins;//单位
	private Set<ScrapBug>scrapBug;//报废原因表
	
	//假字段
	private String xsmduty;//责任划分描述
	private String xsbnums;//缺陷数量
	private String xsbids;//缺陷ID
	private String workingbill;//随工单
	private String productName;//产品名称
	private String productNo;//产品编号
	private String state;//状态
	private String xstate;//状态描述

	/**get/set*/
	@Column
	public String getSmmatterNum()
	{
		return smmatterNum;
	}
	public void setSmmatterNum(String smmatterNum)
	{
		this.smmatterNum = smmatterNum;
	}
	@Column
	public String getSmmatterDes()
	{
		return smmatterDes;
	}
	public void setSmmatterDes(String smmatterDes)
	{
		this.smmatterDes = smmatterDes;
	}
	@Column
	public String getSmduty()
	{
		return smduty;
	}
	public void setSmduty(String smduty)
	{
		this.smduty = smduty;
	}
	@Column
	public String getIsDel()
	{
		return isDel;
	}
	public void setIsDel(String isDel)
	{
		if(isDel==null)
		{
			isDel="N";
		}
		this.isDel = isDel;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Scrap getScrap()
	{
		return scrap;
	}
	public void setScrap(Scrap scrap)
	{
		this.scrap = scrap;
	}
	
	//假字段
	@Transient
	public String getXsmduty()
	{
		return xsmduty;
	}
	public void setXsmduty(String xsmduty)
	{
		this.xsmduty = xsmduty;
	}
	@Column
	public String getSmreson()
	{
		return smreson;
	}
	public void setSmreson(String smreson)
	{
		this.smreson = smreson;
	}
	@Column
	public Double getMenge() {
		return menge;
	}
	public void setMenge(Double menge) {
		this.menge = menge;
	}
	@Column
	public String getCharg() {
		return charg;
	}
	public void setCharg(String charg) {
		this.charg = charg;
	}
	@Column
	public String getItem_text() {
		return item_text;
	}
	public void setItem_text(String item_text) {
		this.item_text = item_text;
	}
	@Column
	public String getMeins() {
		return meins;
	}
	public void setMeins(String meins) {
		this.meins = meins;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="scrapMessage")
	public Set<ScrapBug> getScrapBug()
	{
		return scrapBug;
	}
	public void setScrapBug(Set<ScrapBug> scrapBug)
	{
		this.scrapBug = scrapBug;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
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
	public String getXsbids()
	{
		return xsbids;
	}
	public void setXsbids(String xsbids)
	{
		this.xsbids = xsbids;
	}
	
	@Transient
	public String getWorkingbill() {
		return workingbill;
	}
	public void setWorkingbill(String workingbill) {
		this.workingbill = workingbill;
	}
	
	@Transient
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Transient
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Transient
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	@Transient
	public String getXstate() {
		return xstate;
	}
	public void setXstate(String xstate) {
		this.xstate = xstate;
	}
	
	
}
