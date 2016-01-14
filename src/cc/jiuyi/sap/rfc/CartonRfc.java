package cc.jiuyi.sap.rfc;

import java.io.IOException;

import cc.jiuyi.entity.CartonSon;
import cc.jiuyi.util.CustomerException;

public interface CartonRfc extends BaserfcService{
	public CartonSon CartonCrt(CartonSon cs) throws IOException,CustomerException;
}
