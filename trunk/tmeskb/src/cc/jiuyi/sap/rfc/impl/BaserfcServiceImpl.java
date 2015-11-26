package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Repository;
import com.sap.mw.jco.JCO.Structure;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.sap.rfc.BaserfcService;
import cc.jiuyi.util.Mapping;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.SAPUtil;
import cc.jiuyi.util.SystemConfigUtil;
import cc.jiuyi.util.TableModel;

public class BaserfcServiceImpl implements BaserfcService {

	private String property;
	private HashMap<String, Object> parameter = null;
	private Mapping mapping = null;
	private List<TableModel> tablemodelList = null;

	/**
	 * 从属性文件中读取
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	protected String getFunctionName() throws IOException {
		Properties prop = new Properties();
		InputStream in = Object.class
				.getResourceAsStream("/functionName.properties");
		prop.load(in);
		return prop.getProperty(property).trim();
	}

	/**
	 * 获取SAP链接
	 * 
	 * @return
	 */
	protected Client getSAPConnection() {
		Properties logonProperties = new Properties();
		logonProperties.put("jco.client.ashost", SystemConfigUtil
				.getSystemConfig().getSapHost());
		logonProperties.put("jco.client.client", SystemConfigUtil
				.getSystemConfig().getSapClient());
		logonProperties.put("jco.client.sysnr", SystemConfigUtil
				.getSystemConfig().getSapSysnr());
		logonProperties.put("jco.client.user", SystemConfigUtil
				.getSystemConfig().getSapUser());
		logonProperties.put("jco.client.passwd", SystemConfigUtil
				.getSystemConfig().getSapPasswd());
		logonProperties.put("jco.client.lang", SystemConfigUtil
				.getSystemConfig().getSapLang());
		logonProperties.put("jco.client.CodePage", SystemConfigUtil
				.getSystemConfig().getSapCodePage());
		JCO.Client myConnection = JCO.createClient(logonProperties);
		return myConnection;
	}

	/**
	 * 释放SAP链接
	 * 
	 * @param myConnection
	 */
	protected void releaseClient(JCO.Client myConnection) {
		if (null != myConnection) {
			JCO.releaseClient(myConnection);
		}
	}

	/**
	 * 设置输入参数
	 * 
	 * @param parameter
	 *            输入参数的 hashmap 集合
	 */
	protected void setParameter(HashMap<String, Object> parameter) {
		this.parameter = parameter;
		this.parameter = new HashMap<String, Object>();
	}

	/**
	 * 输入结构
	 */
	protected void setStructure(Mapping mapping) {
		this.mapping = mapping;
		this.mapping = new Mapping();
	}

	/**
	 * 输入表
	 * 
	 * @param tablemodelList
	 */
	protected void setTable(List<TableModel> tablemodelList) {
		this.tablemodelList = tablemodelList;
		this.tablemodelList = new ArrayList<TableModel>();
	}

	/**
	 * 执行BAPI
	 * 
	 * @throws IOException
	 */
	protected SAPModel execBapi() throws IOException {
		SAPModel mode = new SAPModel();
		Client myConnection = null;
		myConnection = getSAPConnection();// 获取SAP链接
		myConnection.connect();// 开启链接
		Repository myRepository = new JCO.Repository("Repository", myConnection); // 名字
		IFunctionTemplate ft = myRepository
				.getFunctionTemplate(getFunctionName());// 从SAP读取函数信息
		Function bapi = ft.getFunction();// 获得函数物件
		ParameterList parameterList = bapi.getImportParameterList();// 获得输入参数
		JCO.ParameterList inputtable = bapi.getTableParameterList();// 输入表的处理

		if (this.parameter != null) {
			Set set = this.parameter.keySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				parameterList.setValue((String) entry.getValue(),
						(String) entry.getKey());
			}
		}

		if (this.mapping != null) {
			Structure Structure01 = parameterList.getStructure(this.mapping
					.getStrutName());
			Map map = this.mapping.getMap();
			Set set = map.entrySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				Structure01.setValue((String) entry.getValue(),
						(String) entry.getKey());
			}
		}

		if (this.tablemodelList != null) {
			for (int i = 0; i < this.tablemodelList.size(); i++) {
				TableModel table = (TableModel) this.tablemodelList.get(i);
				Table IT_ITEM = inputtable.getTable(table.getData());
				List tablelist = table.getList();
				for (int j = 0; j < tablelist.size(); j++) {
					IT_ITEM.appendRow();
					Map map = (Map) tablelist.get(j);
					Set set = map.entrySet();
					for (Iterator it = set.iterator(); it.hasNext();) {
						Map.Entry entry = (Map.Entry) it.next();
						System.out.println(entry.getKey() + "-----------"
								+ (String) entry.getValue());
						IT_ITEM.setValue((String) entry.getValue(),
								(String) entry.getKey());

					}
				}
			}
		}
		myConnection.execute(bapi);
		mode.setOuts(bapi);
		mode.setOuttab(bapi);
		if (null != myConnection) {
			this.releaseClient(myConnection);
		}
		return mode;
	}

}
