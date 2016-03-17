package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 报废之后产出添加的记录
 * @author gaoyf
 *
 */
@Entity
public class ScrapLater extends BaseEntity
{
	private static final long serialVersionUID = 6837385092658277937L;
	
	private String isDel;//是否删除
	private String slmatterNum;//物料编码
	private String slmatterDes;//物料描述
	private String slmatterCount;//物料数量
	private Scrap scrap;//报废表id外键
	private String charg;//批号
	private String item_text;//项目文本
	private String orderid;//订单号
	
	private String workingbill;//随工单
	private String productName;//产品名称
	private String productNo;//产品编码
	private String state;//状态
	private String xstate;//状态描述
	
	
	/**get/set*/
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
	public String getSlmatterNum()
	{
		return slmatterNum;
	}
	public void setSlmatterNum(String slmatterNum)
	{
		this.slmatterNum = slmatterNum;
	}
	@Column
	public String getSlmatterDes()
	{
		return slmatterDes;
	}
	public void setSlmatterDes(String slmatterDes)
	{
		this.slmatterDes = slmatterDes;
	}
	@Column
	public String getSlmatterCount()
	{
		return slmatterCount;
	}
	public void setSlmatterCount(String slmatterCount)
	{
		this.slmatterCount = slmatterCount;
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
	public String getCharg()
	{
		return charg;
	}
	public void setCharg(String charg)
	{
		this.charg = charg;
	}
	public String getItem_text()
	{
		return item_text;
	}
	public void setItem_text(String item_text)
	{
		this.item_text = item_text;
	}
	public String getOrderid()
	{
		return orderid;
	}
	public void setOrderid(String orderid)
	{
		this.orderid = orderid;
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
	public String getXstate() {
		return xstate;
	}
	public void setXstate(String xstate) {
		this.xstate = xstate;
	}
	
	@Transient
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	
	
}
