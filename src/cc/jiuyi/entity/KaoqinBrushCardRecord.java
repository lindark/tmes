package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 开启考勤(刷卡)记录表
 * @author gaoyf
 *
 */
@Entity
public class KaoqinBrushCardRecord extends BaseEntity
{

	private static final long serialVersionUID = -7239816916750090334L;

	private String cardnum;//卡号
	private String empname;//姓名
	private Admin admin;
	
	@Column
	public String getCardnum()
	{
		return cardnum;
	}
	public void setCardnum(String cardnum)
	{
		this.cardnum = cardnum;
	}
	@Column
	public String getEmpname()
	{
		return empname;
	}
	public void setEmpname(String empname)
	{
		this.empname = empname;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getAdmin()
	{
		return admin;
	}
	public void setAdmin(Admin admin)
	{
		this.admin = admin;
	}
	
}
