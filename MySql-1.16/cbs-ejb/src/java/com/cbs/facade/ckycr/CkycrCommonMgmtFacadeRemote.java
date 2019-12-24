/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ckycr;

import com.cbs.dto.CbsCustKycDetailsTo;
import com.cbs.dto.ckycr.CKYCRRequestPojo;
import com.cbs.dto.ckycr.UploadResponseDTO;
import java.io.File;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface CkycrCommonMgmtFacadeRemote {

    public int isApplicantNameUpdate(CKYCRRequestPojo currentObject) throws Exception;

    public int isPersonalEntityDetailUpdate(CKYCRRequestPojo currentObject) throws Exception;

    public int isAddressDetailUpdate(CKYCRRequestPojo currentObject) throws Exception;

    public int isContactDetailUpdate(CKYCRRequestPojo currentObject) throws Exception;

    public int isKycDetailUpdate(CKYCRRequestPojo currentObject) throws Exception;

    public int isIdentityDetailUpdate(CKYCRRequestPojo currentObject) throws Exception;

    public int isImageDetailUpdate(CKYCRRequestPojo currentObject) throws Exception;
    
    public int isRelatedDetailUpdate(CKYCRRequestPojo currentObject) throws Exception;

    public CbsCustKycDetailsTo getCustomerKycDetailHistory(String customerId) throws Exception;

    public boolean isValidIndFirstAndLastName(String str) throws Exception;

    public boolean isValidIndMiddleName(String str) throws Exception;

    public boolean isValidApplicantNameEntity(String str) throws Exception;

    public String getBranchCity(int branchCode) throws Exception;

//    public void zipDir(String filesLocation, String zipFileName, String zipFolderLocation) throws Exception;
    public void delete(File file) throws Exception;

//    public void addDir(File dirObj, ZipOutputStream out) throws Exception;
    public List<UploadResponseDTO> parseUploadResponseStageOne(File textFile) throws Exception;

    public List<UploadResponseDTO> parsePeriodicUploadResponse(File textFile) throws Exception;

    public String ckycrOccupationCode(String occupationCode) throws Exception;

    public String[] getCkycrRegionCodeAndBranchCode(String alphaCode) throws Exception;

    public int getCkycrLimit() throws Exception;

    public void zipFolder(String srcFolder, String destZipFile) throws Exception;

    public void addToZipWithOutFolder(String srcFileDirectory, String zipDirectory, String zipFileName) throws Exception;
}
