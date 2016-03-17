
package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Pick;
import cc.jiuyi.sap.rfc.UpDownRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.Mapping;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;

@Component
public class UpDownRfcImpl extends BaserfcServiceImpl implements UpDownRfc{

	public List<HashMap<String,String>> undown(HashMap<String,String> hash,List<HashMap<String,String>> hashList) throws IOException, CustomerException{
		super.setProperty("undown");//根据配置文件读取到函数名称
		/**输入参数**/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("S_WERKS", hash.get("werks"));//工厂
		parameter.put("S_LGORT", hash.get("lgort"));//库存地点
		parameter.put("FLAG", hash.get("flag"));//0 上架 2下架
		/**输入结构**/
		Mapping mapping = new Mapping();
		HashMap<String,String> map = new HashMap<String,String>();
		/**输入表**/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
		tablemodel.setData("GT_LQUA");//表名
		for(int i=0;i<hashList.size();i++){
			HashMap<String,String> hashput = hashList.get(i);
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR", hashput.get("matnr"));//物料编码
			item.put("CHARG", hashput.get("charg"));//批次
			item.put("MEINS", "PC");//单位
			item.put("LGPLA", hashput.get("lgpla"));//发出仓位
			item.put("DWNUM", hashput.get("dwnum"));//数量
			item.put("NLPLA", hashput.get("nlpla"));//目的地仓位
			arrList.add(item);
		}
		tablemodel.setList(arrList);
		tablemodelList.add(tablemodel);
		/*******执行******/
		super.setParameter(parameter);//输入参数
		super.setStructure(null);//输入结构
		super.setTable(tablemodelList);
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList out = model.getOuts();//返回参数
		ParameterList outs = model.getOuttab();//返回表
		String type =  out.getString("E_TYPE");//返回类型
		String message = out.getString("E_MESSAGE");//返回消息
		if(type.equals("E")){//如果是E，抛出自定义异常
			throw new CustomerException("1400001", "上架/下架失败,"+message);
		}
		Table t_data = outs.getTable("GT_LTAP");//列表
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < t_data.getNumRows(); i++) {
			t_data.setRow(i);
			HashMap<String,String> hashmap = new HashMap<String,String>();
			hashmap.put("tanum", t_data.getString("TANUM"));//转储单号
			hashmap.put("tapos", t_data.getString("TAPOS"));//行项目号 
			hashmap.put("matnr", t_data.getString("MATNR"));//物料号
			hashmap.put("werks", t_data.getString("WERKS"));//工厂
			hashmap.put("charg", t_data.getString("CHARG"));//批次
			list.add(hashmap);
		}
		
		return list;
	}
}
