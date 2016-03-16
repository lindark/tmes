package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.CartonSon;
import cc.jiuyi.util.CustomerException;

public interface CartonRfc extends BaserfcService{
	public Carton CartonCrt(String testrun,List<CartonSon>list_cs) throws IOException,CustomerException;
	
	public Object[] CartonCrtNew(String testrun,List<CartonSon>list_cs) throws IOException,CustomerException;
}
