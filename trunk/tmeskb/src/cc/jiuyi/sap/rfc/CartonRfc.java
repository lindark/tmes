package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Carton;
import cc.jiuyi.util.CustomerException;

public interface CartonRfc extends BaserfcService{
	public List<Carton> CartonCrt(List<Carton> list) throws IOException,CustomerException;
}
