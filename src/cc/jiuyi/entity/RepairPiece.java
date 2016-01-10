package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 返修--组件
 * @author lenovo
 *
 */
@Entity
public class RepairPiece extends BaseEntity
{
	private static final long serialVersionUID = 369610835326079316L;
	
	private String rpcode;//组件编码
	private String rpname;//组件名称
	private Double productnum;//产品数量
	private Double piecenum;//组件数量
	private String rpcount;//组件总数量
	private String ITEM_TEXT;//项目文本
	private Repair repair;//主表
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
	public String getRpcount()
	{
		return rpcount;
	}
	public void setRpcount(String rpcount)
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
	public Repair getRepair()
	{
		return repair;
	}
	public void setRepair(Repair repair)
	{
		this.repair = repair;
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
	
}
