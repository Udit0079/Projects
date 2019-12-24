/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.NpciFileDto;
import com.cbs.dto.NpciInwardDto;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface NpciYojnaMgmtFacadeRemote {

    public String uploadingOfYojnaSchemes(List<NpciInwardDto> inputList, String uploadBrCode,
            String uploadingUserName, String todayDt, String fileName) throws Exception;

    public List getYojnaSeqNoForSettlementDate(String settlementDt, String iwType) throws Exception;

    public String generateYojnaReturnFiles(String function, String fileGenerationDt, Double seqNo, String brCode,
            String userName, String todayDt) throws Exception;

    public List<NpciFileDto> showYojnaFiles(String fileShowDt, Double seqNo) throws Exception;
}
