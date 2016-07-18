package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 返修收货
 * @author lenovo
 *
 */
@Entity
public class RepairinPiece extends BaseEntity
{
	private static final long serialVersionUID = -8103183063577995350L;
	
	private String rpcode;//物料编码
	private String rpname;//物料名称
	private Double productnum;//产品数量
	private Double piecenum;//组件数量
	private Double rpcount;//组件总数量
	private String ITEM_TEXT;//项目文本
	private Repairin repairin;//主表
	private String CHARG;//批次
	public String getRpcode()
	{
		return rpcode;
	}
	public void setRpcode(String rpcode)
	{
		this.rpcode = rpcode;
	}
	public String getRpname()
	{
		return rpname;
	}
	public void setRpname(String rpname)
	{
		this.rpname = rpname;
	}
	public Double getRpcount()
	{
		return rpcount;
	}
	public void setRpcount(Double rpcount)
	{
		this.rpcount = rpcount;
	}
	public String getITEM_TEXT()
	{
		return ITEM_TEXT;
	}
	public void setITEM_TEXT(String iTEM_TEXT)
	{
		ITEM_TEXT = iTEM_TEXT;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Repairin getRepairin()
	{
		return repairin;
	}
	public void setRepairin(Repairin repairin)
	{
		this.repairin = repairin;
	}
	public Double getProductnum()
	{
		return productnum;
	}
	public void setProductnum(Double productnum)
	{
		this.productnum = productnum;
	}
	public Double getPiecenum()
	{
		return piecenum;
	}
	public void setPiecenum(Double piecenum)
	{
		this.piecenum = piecenum;
	}
	public String getCHARG()
	{
		return CHARG;
	}
	public void setCHARG(String cHARG)
	{
		CHARG = cHARG;
	}
	
}
