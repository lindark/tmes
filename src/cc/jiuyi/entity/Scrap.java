package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 报废
 * @author gaoyf
 *
 */
@Entity
public class Scrap extends BaseEntity
{
	private static final long serialVersionUID = 5285944433984846235L;
	//表字段
	private String isDel;//是否已删除
	private String state;//状态
	private WorkingBill workingBill;//随工单
	private Admin creater;//提交人
	private Admin confirmation;//确认人
	private String move_type;//移动类型
	private String lgort;//库存地点
	private String ztext;//抬头文本
	private String budat;//过账日期
	private String werks;//工厂
	private String e_type;//类型S/E
	private String e_message;//反馈消息
	private String mblnr;//物料凭证
	
	//从表
	private Set<ScrapMessage> scrapMsgSet;//报废信息表
	private Set<ScrapLater> scrapLaterSet;//报废后产出的记录表
	
	//假字段
	private String xstate;//状态描述
	private String xcreater;//提交人
	private String xconfirmation;//确认人
	private String xorderid;//订单号
	
	//表字段
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
	@Column
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	//外键表
	@ManyToOne(fetch=FetchType.LAZY)
	public WorkingBill getWorkingBill()
	{
		return workingBill;
	}
	public void setWorkingBill(WorkingBill workingBill)
	{
		this.workingBill = workingBill;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getCreater()
	{
		return creater;
	}
	public void setCreater(Admin creater)
	{
		this.creater = creater;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getConfirmation()
	{
		return confirmation;
	}
	public void setConfirmation(Admin confirmation)
	{
		this.confirmation = confirmation;
	}
	
	//从表
	@OneToMany(fetch=FetchType.LAZY,mappedBy="scrap")
	public Set<ScrapMessage> getScrapMsgSet()
	{
		return scrapMsgSet;
	}
	public void setScrapMsgSet(Set<ScrapMessage> scrapMsgSet)
	{
		this.scrapMsgSet = scrapMsgSet;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="scrap")
	public Set<ScrapLater> getScrapLaterSet()
	{
		return scrapLaterSet;
	}
	public void setScrapLaterSet(Set<ScrapLater> scrapLaterSet)
	{
		this.scrapLaterSet = scrapLaterSet;
	}
	
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getMove_type() {
		return move_type;
	}
	public void setMove_type(String move_type) {
		this.move_type = move_type;
	}
	public String getLgort() {
		return lgort;
	}
	public void setLgort(String lgort) {
		this.lgort = lgort;
	}
	public String getZtext() {
		return ztext;
	}
	public void setZtext(String ztext) {
		this.ztext = ztext;
	}
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
	}
	//假字段
	@Transient
	public String getXstate()
	{
		return xstate;
	}
	public void setXstate(String xstate)
	{
		this.xstate = xstate;
	}
	@Transient
	public String getXcreater()
	{
		return xcreater;
	}
	public void setXcreater(String xcreater)
	{
		this.xcreater = xcreater;
	}
	@Transient
	public String getXconfirmation()
	{
		return xconfirmation;
	}
	public void setXconfirmation(String xconfirmation)
	{
		this.xconfirmation = xconfirmation;
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
	public String getMblnr() {
		return mblnr;
	}
	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}
	@Transient
	public String getXorderid()
	{
		return xorderid;
	}
	public void setXorderid(String xorderid)
	{
		this.xorderid = xorderid;
	}
}
