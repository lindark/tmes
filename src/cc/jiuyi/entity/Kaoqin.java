package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * 考勤
 * @author lenovo
 *
 */
@Entity
public class Kaoqin extends BaseEntity
{

	private static final long serialVersionUID = 6948365437771959825L;
	
	private String cardNum;//员工卡号
	private String empName;//员工姓名
	private String workState;//工作状态
	private String classtime;//班次
	private String teams;//班组
	private String kqdate;//日期
	private String skill;//技能
	private String adminId;//员工表主键
	//假字段
	private String state;//工作状态key
	private String xworkState;//工作状态
	@Column
	public String getCardNum()
	{
		return cardNum;
	}
	public void setCardNum(String cardNum)
	{
		this.cardNum = cardNum;
	}
	@Column
	public String getEmpName()
	{
		return empName;
	}
	public void setEmpName(String empName)
	{
		this.empName = empName;
	}
	@Column
	public String getWorkState()
	{
		return workState;
	}
	public void setWorkState(String workState)
	{
		this.workState = workState;
	}
	
	@Column
	public String getClasstime()
	{
		return classtime;
	}
	public void setClasstime(String classtime)
	{
		this.classtime = classtime;
	}
	@Column
	public String getTeams()
	{
		return teams;
	}
	public void setTeams(String teams)
	{
		this.teams = teams;
	}
	@Transient
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	@Column
	public String getKqdate()
	{
		return kqdate;
	}
	public void setKqdate(String kqdate)
	{
		this.kqdate = kqdate;
	}
	@Transient
	public String getXworkState()
	{
		return xworkState;
	}
	public void setXworkState(String xworkState)
	{
		this.xworkState = xworkState;
	}
	
	@Column
	public String getSkill()
	{
		return skill;
	}
	public void setSkill(String skill)
	{
		this.skill = skill;
	}
	
	@Column
	public String getAdminId()
	{
		return adminId;
	}
	public void setAdminId(String adminId)
	{
		this.adminId = adminId;
	}
	
}
