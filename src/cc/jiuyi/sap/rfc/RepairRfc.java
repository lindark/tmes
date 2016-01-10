package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.RepairPiece;
import cc.jiuyi.util.CustomerException;

public interface RepairRfc  extends BaserfcService{
/**
 * 反修接口
 */
public Repair repairCrt(Repair repair,List<RepairPiece> list_rp) throws IOException,CustomerException;
}
