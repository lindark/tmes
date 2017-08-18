package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;
import cc.jiuyi.entity.Sark;
import cc.jiuyi.entity.SarkSon;
import cc.jiuyi.util.CustomerException;

public interface SarkRfc extends BaserfcService{
	public Sark SarkCrt(String testrun,List<SarkSon>list_cs) throws IOException,CustomerException;
	
	public Object[] SarkCrtNew(String testrun,List<SarkSon>list_cs) throws IOException,CustomerException;
}
