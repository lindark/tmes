package cc.jiuyi.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

import cc.jiuyi.bean.SystemConfig;
import cc.jiuyi.util.SAPUtil;
import cc.jiuyi.util.TableModel;

/*
 * 访问SAP
 * -----------by ZhangChunhao V1.0--------
 */
public class SAPUtil {
	public static JCO.Client  getSAPcon(){
		Properties logonProperties = new Properties();
        logonProperties.put("jco.client.ashost",SystemConfigUtil.getSystemConfig().getSapHost());
        logonProperties.put("jco.client.client",SystemConfigUtil.getSystemConfig().getSapClient());          
        logonProperties.put("jco.client.sysnr",SystemConfigUtil.getSystemConfig().getSapSysnr());          
        logonProperties.put("jco.client.user",SystemConfigUtil.getSystemConfig().getSapUser());        
        logonProperties.put("jco.client.passwd",SystemConfigUtil.getSystemConfig().getSapPasswd()); 

        logonProperties.put("jco.client.lang",SystemConfigUtil.getSystemConfig().getSapLang()); 
        logonProperties.put("jco.client.CodePage",SystemConfigUtil.getSystemConfig().getSapCodePage()); 
        JCO.Client myConnection = JCO.createClient( logonProperties );
		return myConnection;
	}

	public  static void releaseClient(JCO.Client myConnection){
		if(null!=myConnection){
			JCO.releaseClient(myConnection);
		}
	}
	/*
	 * 适用范围：函数为单结构、单表输入数据
	 * 参数一次为：输入参数、输入结构、输入表名、输入表数据、函数名
	 */
	public static SAPModel OperSAP(Map  strMap,Mapping pp,String impTab,List inplist,String functionName){
		SAPModel mode=new SAPModel();
		JCO.Client myConnection =null;
		try{
			myConnection =SAPUtil.getSAPcon();
		    myConnection.connect(); 
		    JCO.Repository myRepository = new JCO.Repository("Repository",myConnection); //只是一個名字
		    IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);
		    //從這個函數範本獲得該SAP函數的物件
		    JCO.Function bapi = ft.getFunction();
	    	JCO.ParameterList  parameterList=bapi.getImportParameterList();//获得输入表的参数
			JCO.ParameterList   inputtable= bapi.getTableParameterList();//输入表的处理
			
			//JCO.Table  IT_ITEM=inputtable.getTable("IT_ITEM");
			/*
			 * 输入参数
			 */
			if(strMap!=null){
				Set set=strMap.entrySet();
				for(Iterator it=set.iterator();it.hasNext();){
					Map.Entry entry=(Map.Entry)it.next();
					parameterList.setValue((String) entry.getValue(),(String)entry.getKey());
				}
			}
			
			/*
			 * 输入结构
			 */
			JCO.Structure Structure01 = null;
			if(pp!=null){
				Structure01 = parameterList.getStructure(pp.getStrutName());
				Map map=pp.getMap();
				Set set=map.entrySet();
				for(Iterator it=set.iterator();it.hasNext();){
					Map.Entry entry=(Map.Entry)it.next();
					Structure01.setValue((String) entry.getValue(),(String)entry.getKey());
				}
			}
			/*
			 * 输入入表
			 */
			JCO.Table  IT_ITEM=null;
			if(!Util.null2String(impTab).isEmpty() && inplist!=null){
				IT_ITEM = inputtable.getTable(impTab);
				for(int j=0;j<inplist.size();j++){
					Map map=(Map)inplist.get(j);
					Set set=map.entrySet();
					for(Iterator it=set.iterator();it.hasNext();){
						IT_ITEM.appendRow();
						Map.Entry entry=(Map.Entry)it.next();
						IT_ITEM.setValue((String) entry.getValue(), (String)entry.getKey());
						System.out.println(entry.getKey()+"-----------"+ (String) entry.getValue());
					}
				}
			}
			myConnection.execute(bapi);
		
			mode.setOuts(bapi);
			mode.setOuttab(bapi);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=myConnection){
				SAPUtil.releaseClient(myConnection);
			}
		}
		return mode;
	}
	/*
	 * 适用范围：函数为单结构、多表输入数据
	 * 参数一次为：输入参数、输入结构、输入表数据、函数名
	 */
	public static SAPModel OperSAP(Map  strMap,Mapping pp,List list,String functionName){
		SAPModel mode=new SAPModel();
		JCO.Client myConnection =null;
		try{
			myConnection =SAPUtil.getSAPcon();
		    myConnection.connect(); 
		    JCO.Repository myRepository = new JCO.Repository("Repository",myConnection); //只是一個名字
		    IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);
		    //從這個函數範本獲得該SAP函數的物件
		    JCO.Function bapi = ft.getFunction();
	    	JCO.ParameterList  parameterList=bapi.getImportParameterList();//获得输入表的参数
			JCO.ParameterList   inputtable= bapi.getTableParameterList();//输入表的处理
			
			//JCO.Table  IT_ITEM=inputtable.getTable("IT_ITEM");
			/*
			 * 输入参数
			 */
			if(strMap!=null){
				Set set=strMap.entrySet();
				for(Iterator it=set.iterator();it.hasNext();){
					Map.Entry entry=(Map.Entry)it.next();
					parameterList.setValue((String) entry.getValue(),(String)entry.getKey());
				}
			}
			
			/*
			 * 输入结构
			 */
			JCO.Structure Structure01 = null;
			if(pp!=null){
				Structure01 = parameterList.getStructure(pp.getStrutName());
				System.out.println("Structure:");
				Map map=pp.getMap();
				Set set=map.entrySet();
				for(Iterator it=set.iterator();it.hasNext();){
					Map.Entry entry=(Map.Entry)it.next();
					Structure01.setValue((String) entry.getValue(),(String)entry.getKey());
				}
			}
			/*
			 * 输入入表
			 */
			JCO.Table  IT_ITEM=null;
			if(list!=null){
				for(int i=0;i<list.size();i++){
					TableModel table=(TableModel)list.get(i);
					IT_ITEM = inputtable.getTable(table.getData());
					List tablelist=table.getList();
					for(int j=0;j<tablelist.size();j++){
						IT_ITEM.appendRow();
						Map map=(Map)tablelist.get(j);
						Set set=map.entrySet();
						for(Iterator it=set.iterator();it.hasNext();){
							
							Map.Entry entry=(Map.Entry)it.next();
							System.out.println(entry.getKey()+"-----------"+ (String) entry.getValue());
							IT_ITEM.setValue((String) entry.getValue(), (String)entry.getKey());
							
						}
					}
				}
			}
			myConnection.execute(bapi);
		
			mode.setOuts(bapi);
			mode.setOuttab(bapi);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=myConnection){
				SAPUtil.releaseClient(myConnection);
			}
		}
		return mode;
	}
	 
}
