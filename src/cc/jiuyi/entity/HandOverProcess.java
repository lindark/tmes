package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 工序交接
 * @param args
 */


@Entity
@Searchable
@Table(name = "HandOverProcess")
public class HandOverProcess extends BaseEntity{
	
	private static final long serialVersionUID = -3676085256696731274L;
	
	
	private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
      

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
        if(isDel==null){
        	isDel="N";
        }
		this.isDel = isDel;
	}
	
	 @Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

   

    
	
}
