package cc.jiuyi.bean;

import java.util.List;

/**
 * Bean类 - jqgrid 复杂搜索功能
 * author:weitao li
 */

@SuppressWarnings("unchecked")
public class jqGridSearchDetailTo {
	private String field;   //查询字段  
    private String op;      //查询操作  
    private String data;    //选择的查询值 
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
    
}