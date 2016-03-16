package cc.jiuyi.entity;

import java.util.Set;

/**
 * 实体类——超市领料
 * 
 */
public class Market extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8693792102098962871L;

	private String materialCode;//物料编码
	private String materialDes;//物料描述
	private String productionDate;//生产日期
	private String shift;//班次
	private Admin creater;//创建人
	private Admin confirmer;//确认人
	private String state;//状态
	private String allCount;//物料总数量
	private String bringBack;//领/退 料
	private Set<MarketSon>martketSon;//从表

	//假字段
	private String xcreater;//创建人
	private String xconfirmer;//确认人
	private String xstate;//状态
	
	/**===================================*/
	
	public String getMaterialCode()
	{
		return materialCode;
	}
	public void setMaterialCode(String materialCode)
	{
		this.materialCode = materialCode;
	}
	public String getMaterialDes()
	{
		return materialDes;
	}
	public void setMaterialDes(String materialDes)
	{
		this.materialDes = materialDes;
	}
	public String getProductionDate()
	{
		return productionDate;
	}
	public void setProductionDate(String productionDate)
	{
		this.productionDate = productionDate;
	}
	public String getShift()
	{
		return shift;
	}
	public void setShift(String shift)
	{
		this.shift = shift;
	}
	public Admin getCreater()
	{
		return creater;
	}
	public void setCreater(Admin creater)
	{
		this.creater = creater;
	}
	public Admin getConfirmer()
	{
		return confirmer;
	}
	public void setConfirmer(Admin confirmer)
	{
		this.confirmer = confirmer;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public String getAllCount()
	{
		return allCount;
	}
	public void setAllCount(String allCount)
	{
		this.allCount = allCount;
	}
	public String getBringBack()
	{
		return bringBack;
	}
	public void setBringBack(String bringBack)
	{
		this.bringBack = bringBack;
	}
	public String getXcreater()
	{
		return xcreater;
	}
	public void setXcreater(String xcreater)
	{
		this.xcreater = xcreater;
	}
	public String getXconfirmer()
	{
		return xconfirmer;
	}
	public void setXconfirmer(String xconfirmer)
	{
		this.xconfirmer = xconfirmer;
	}
	public String getXstate()
	{
		return xstate;
	}
	public void setXstate(String xstate)
	{
		this.xstate = xstate;
	}
	public Set<MarketSon> getMartketSon()
	{
		return martketSon;
	}
	public void setMartketSon(Set<MarketSon> martketSon)
	{
		this.martketSon = martketSon;
	}
}
