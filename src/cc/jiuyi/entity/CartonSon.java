package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 纸箱收货子表
 * @author lenovo
 *
 */
@Entity
public class CartonSon extends BaseEntity
{

	private static final long serialVersionUID = -3497011097392288625L;

	private String MATNR;//物料编码
	private String MATNRDES;//物料描述
	private String LIFNR;//供应商
	private String cscount;//纸箱数量
	private String LGORT;//库存地点
	private String WERKS;//工厂
	private String BUDAT;//过账日期
	private Carton carton;//纸箱收货-主表
	private String wbid;//随工单ID
	private String wbcode;//随工单编码
	private String productcode;//产品编码
	private String productname;//产品名称
	//假字段
	private String MOVE_TYPE;//移动类型
	private String xcstotal;//累计数量
	@ManyToOne(fetch=FetchType.LAZY)
	public Carton getCarton()
	{
		return carton;
	}
	public void setCarton(Carton carton)
	{
		this.carton = carton;
	}
	public String getMATNR()
	{
		return MATNR;
	}
	public void setMATNR(String mATNR)
	{
		MATNR = mATNR;
	}
	public String getMATNRDES()
	{
		return MATNRDES;
	}
	public void setMATNRDES(String mATNRDES)
	{
		MATNRDES = mATNRDES;
	}
	public String getLIFNR()
	{
		return LIFNR;
	}
	public void setLIFNR(String lIFNR)
	{
		LIFNR = lIFNR;
	}
	
	@Transient
	public String getMOVE_TYPE()
	{
		return MOVE_TYPE;
	}
	public void setMOVE_TYPE(String mOVE_TYPE)
	{
		MOVE_TYPE = mOVE_TYPE;
	}
	public String getLGORT()
	{
		return LGORT;
	}
	public void setLGORT(String lGORT)
	{
		LGORT = lGORT;
	}
	public String getWERKS()
	{
		return WERKS;
	}
	public void setWERKS(String wERKS)
	{
		WERKS = wERKS;
	}
	public String getBUDAT()
	{
		return BUDAT;
	}
	public void setBUDAT(String bUDAT)
	{
		BUDAT = bUDAT;
	}
	public String getWbid()
	{
		return wbid;
	}
	public void setWbid(String wbid)
	{
		this.wbid = wbid;
	}
	@Transient
	public String getXcstotal()
	{
		return xcstotal;
	}
	public void setXcstotal(String xcstotal)
	{
		this.xcstotal = xcstotal;
	}
	public String getWbcode()
	{
		return wbcode;
	}
	public void setWbcode(String wbcode)
	{
		this.wbcode = wbcode;
	}
	public String getProductcode()
	{
		return productcode;
	}
	public void setProductcode(String productcode)
	{
		this.productcode = productcode;
	}
	public String getProductname()
	{
		return productname;
	}
	public void setProductname(String productname)
	{
		this.productname = productname;
	}
	public String getCscount()
	{
		return cscount;
	}
	public void setCscount(String cscount)
	{
		this.cscount = cscount;
	}
	
	
}
