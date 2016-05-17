package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 同步单元
 * 
 * @param args
 */

@Entity
//@Searchable
public class FactoryUnitSyn extends BaseEntity{
	
	private static final long serialVersionUID = -8030009887776670155L;
	
	// 设置trigger名称
	private String triggername;  
	//设置表达式
	private String cronexpression;
	// 设置Job名称
	private String jobdetailname;
	//任务类名
	private String targetobject;
	//类名对应的方法名
	private String methodname;
	//方法名中的参数
	private String[] methodArguments;
	//设置是否并发启动任务 0是false 非0是true
	private String concurrent;
	// 如果计划任务不存在并且数据库里的任务状态为可用时,则创建计划任务 1为不存在
	private String state;
	private String readme;
	//是否是已经存在的springBean 1=是  非1是否
	private String isspringbean;
	private String factoryUnitCode;//单元编码
	private String factoryUnitName;//单元名称
	private String isDel;//是否删除
	/** 预留字段1 */
	private String reserved1;
	/** 预留字段2 */
	private String reserved2;
	/** 预留字段3 */
	private String reserved3;
	/** 预留字段4 */
	private String reserved4;
	/** 预留字段5 */
	private String reserved5;
	/** 预留字段6 */
	private String reserved6;
	/** 预留字段7 */
	private String reserved7;
	/** 预留字段8 */
	private String reserved8;
	/** 预留字段9 */
	private String reserved9;
	/** 预留字段10 */
	private String reserved10;
	// Constructors

	
	/**
	 * 假字段
	 */
	private String stateRemark;
	
	
	/** default constructor */
	public FactoryUnitSyn() {
	}

	/** full constructor */
	public FactoryUnitSyn(String triggername, String cronexpression,
			String jobdetailname, String targetobject, String methodname,
			String concurrent, String state, String readme,String isspringbean,String[] methodArguments) {
		this.triggername = triggername;
		this.cronexpression = cronexpression;
		this.jobdetailname = jobdetailname;
		this.targetobject = targetobject;
		this.methodname = methodname;
		this.concurrent = concurrent;
		this.state = state;
		this.readme = readme;
		this.isspringbean=isspringbean;
		this.methodArguments= methodArguments;
	}

	public String getTriggername() {
		return this.triggername;
	}

	public void setTriggername(String triggername) {
		this.triggername = triggername;
	}
	
	public String getCronexpression() {
		return this.cronexpression;
	}

	public void setCronexpression(String cronexpression) {
		this.cronexpression = cronexpression;
	}
	
	public String getJobdetailname() {
		return this.jobdetailname;
	}

	public void setJobdetailname(String jobdetailname) {
		this.jobdetailname = jobdetailname;
	}
	public String getTargetobject() {
		return this.targetobject;
	}

	public void setTargetobject(String targetobject) {
		this.targetobject = targetobject;
	}
	public String getMethodname() {
		return this.methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}
	public String getConcurrent() {
		return this.concurrent;
	}

	public void setConcurrent(String concurrent) {
		this.concurrent = concurrent;
	}
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}
	public String getReadme() {
		return this.readme;
	}

	public void setReadme(String readme) {
		this.readme = readme;
	}
	public String getIsspringbean() {
		return isspringbean;
	}

	public void setIsspringbean(String isspringbean) {
		this.isspringbean = isspringbean;
	}
	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}
	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	public String getReserved3() {
		return reserved3;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}
	public String getReserved4() {
		return reserved4;
	}

	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}
	public String getReserved5() {
		return reserved5;
	}

	public void setReserved5(String reserved5) {
		this.reserved5 = reserved5;
	}
	public String getReserved6() {
		return reserved6;
	}

	public void setReserved6(String reserved6) {
		this.reserved6 = reserved6;
	}
	public String getReserved7() {
		return reserved7;
	}

	public void setReserved7(String reserved7) {
		this.reserved7 = reserved7;
	}
	public String getReserved8() {
		return reserved8;
	}

	public void setReserved8(String reserved8) {
		this.reserved8 = reserved8;
	}
	public String getReserved9() {
		return reserved9;
	}

	public void setReserved9(String reserved9) {
		this.reserved9 = reserved9;
	}
	public String getReserved10() {
		return reserved10;
	}

	public void setReserved10(String reserved10) {
		this.reserved10 = reserved10;
	}

	public String[] getMethodArguments() {
		return methodArguments;
	}

	public void setMethodArguments(String[] methodArguments) {
		this.methodArguments = methodArguments;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	public String getFactoryUnitCode() {
		return factoryUnitCode;
	}

	public void setFactoryUnitCode(String factoryUnitCode) {
		this.factoryUnitCode = factoryUnitCode;
	}

	public String getFactoryUnitName() {
		return factoryUnitName;
	}

	public void setFactoryUnitName(String factoryUnitName) {
		this.factoryUnitName = factoryUnitName;
	}



}
