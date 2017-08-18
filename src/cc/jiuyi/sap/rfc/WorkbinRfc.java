package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cc.jiuyi.entity.Workbin;
import cc.jiuyi.entity.WorkbinSon;
import cc.jiuyi.util.CustomerException;

public interface WorkbinRfc extends BaserfcService{
	public Workbin WorkbinCrt(String testrun,List<WorkbinSon>list_cs) throws IOException,CustomerException;
	
	public Object[] WorkbinCrtNew(String testrun,List<WorkbinSon>list_cs) throws IOException,CustomerException;
	
	public List<WorkbinSon> getWorkbinRfc(String ordernumber);
	
	public Map<String,String> updateWorkbinRfc(String ordernumber);
}
