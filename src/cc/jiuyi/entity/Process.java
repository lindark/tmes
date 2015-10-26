package cc.jiuyi.entity;

import javax.persistence.Entity;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 工序
 * @param args
 */


@Entity
@Searchable
public class Process extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String ProcessCode;//工序编码
    private String ProcessName;//工序名称
    private String State;//状态

    
   
    
	public String getProcessCode() {
		return ProcessCode;
	}
	public void setProcessCode(String processCode) {
		ProcessCode = processCode;
	}
	public String getProcessName() {
		return ProcessName;
	}
	public void setProcessName(String processName) {
		ProcessName = processName;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
    
}
