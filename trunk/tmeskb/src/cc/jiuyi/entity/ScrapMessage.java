package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
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
	private String isDel;//删除
	private Scrap scrap;//报废表：外键
	private Double menge;//数量
	private String charg;//批号
	private String item_text;//项目文本
	private String meins;//单位
	//假字段
	private String xsmduty;//责任划分描述
	private String xscrapreson;//报废原因
	
	
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
		else
		{
			this.isDel = isDel;
		}
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
	@Transient
	public String getXscrapreson()
	{
		return xscrapreson;
	}
	public void setXscrapreson(String xscrapreson)
	{
		this.xscrapreson = xscrapreson;
	}
	public Double getMenge() {
		return menge;
	}
	public void setMenge(Double menge) {
		this.menge = menge;
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
	
}
