package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 实体类 - 返修部位
 * @param args
 */


@Entity
//@Searchable
@Table(name = "repairDetail")
public class RepairDetail extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String repirCode;//返修编码
    private String repirName;//返修名称
    private String state;//状态
    
    private Process process;//车间
    
    /**
     * 假字段
     * @return
     */
    private String xprocessName;//车间
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRepirCode() {
		return repirCode;
	}
	public void setRepirCode(String repirCode) {
		this.repirCode = repirCode;
	}
	public String getRepirName() {
		return repirName;
	}
	public void setRepirName(String repirName) {
		this.repirName = repirName;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Process getProcess() {
		return process;
	}
	public void setProcess(Process process) {
		this.process = process;
	}
	@Transient
	public String getXprocessName() {
		return xprocessName;
	}
	public void setXprocessName(String xprocessName) {
		this.xprocessName = xprocessName;
	}
	

   

    
	
}
