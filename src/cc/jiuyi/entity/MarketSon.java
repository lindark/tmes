package cc.jiuyi.entity;

public class MarketSon extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9002116237662855076L;
	
	private String materialCode;//物料编码
	private String materialDes;//物料描述
	private String stockSites;//库存地点
	private String bringNum;//领用数量
	private Market market;//主表
	
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
	public String getStockSites()
	{
		return stockSites;
	}
	public void setStockSites(String stockSites)
	{
		this.stockSites = stockSites;
	}
	public String getBringNum()
	{
		return bringNum;
	}
	public void setBringNum(String bringNum)
	{
		this.bringNum = bringNum;
	}
	public Market getMarket()
	{
		return market;
	}
	public void setMarket(Market market)
	{
		this.market = market;
	}
}
