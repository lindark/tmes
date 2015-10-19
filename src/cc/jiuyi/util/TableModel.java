package cc.jiuyi.util;

import java.util.ArrayList;
import java.util.List;
/*
 * 用以输入表
 * data为输入表NAME
 * List中存放Map对象，以键值对的形势存储输入表字段与值
 * -----------by ZhangChunhao V1.0--------
 */
public class TableModel {
	private String data="";
	private List list=new ArrayList();
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
}
