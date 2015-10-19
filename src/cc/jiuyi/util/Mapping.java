package cc.jiuyi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * 用以输入结构
 * strutName为表名
 * map以键值对的形势存储结构的字段名与值
 * -----------by ZhangChunhao V1.0--------
 */
public class Mapping {
	private String strutName="";
	private Map map= new HashMap();
	public String getStrutName() {
		return strutName;
	}
	public void setStrutName(String strutName) {
		this.strutName = strutName;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}


}
