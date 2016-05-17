package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * ʵ���� - ͬ����Ԫ
 * 
 * @param args
 */

@Entity
//@Searchable
public class FactoryUnitSyn extends BaseEntity{
	
	private static final long serialVersionUID = -8030009887776670155L;
	
	// ����trigger����
	private String triggername;  
	//���ñ��ʽ
	private String cronexpression;
	// ����Job����
	private String jobdetailname;
	//��������
	private String targetobject;
	//������Ӧ�ķ�����
	private String methodname;
	//�������еĲ���
	private String[] methodArguments;
	//�����Ƿ񲢷��������� 0��false ��0��true
	private String concurrent;
	// ����ƻ����񲻴��ڲ������ݿ��������״̬Ϊ����ʱ,�򴴽��ƻ����� 1Ϊ������
	private String state;
	private String readme;
	//�Ƿ����Ѿ����ڵ�springBean 1=��  ��1�Ƿ�
	private String isspringbean;
	private String factoryUnitCode;//��Ԫ����
	private String factoryUnitName;//��Ԫ����
	private String isDel;//�Ƿ�ɾ��
	/** Ԥ���ֶ�1 */
	private String reserved1;
	/** Ԥ���ֶ�2 */
	private String reserved2;
	/** Ԥ���ֶ�3 */
	private String reserved3;
	/** Ԥ���ֶ�4 */
	private String reserved4;
	/** Ԥ���ֶ�5 */
	private String reserved5;
	/** Ԥ���ֶ�6 */
	private String reserved6;
	/** Ԥ���ֶ�7 */
	private String reserved7;
	/** Ԥ���ֶ�8 */
	private String reserved8;
	/** Ԥ���ֶ�9 */
	private String reserved9;
	/** Ԥ���ֶ�10 */
	private String reserved10;
	// Constructors

	
	/**
	 * ���ֶ�
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
