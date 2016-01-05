package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Repair;
import cc.jiuyi.util.CustomerException;

public interface RepairRfc  extends BaserfcService{
/**
 * 反修接口
 */
public List<Repair> repairCrt(List<Repair> list) throws IOException,CustomerException;
}
