/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ckycr;

import com.cbs.dto.CbsCustKycDetailsTo;
import com.cbs.dto.customer.CBSCustMISInfoHisTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailHisTO;
import com.cbs.facade.admin.customer.CustomerManagementFacadeRemote;
import com.cbs.facade.admin.customer.CustomerMgmtFacadeRemote;
import com.cbs.dto.ckycr.CKYCRRequestPojo;
import com.cbs.dto.ckycr.UploadResponseDTO;
import com.cbs.utils.ParseFileUtil;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName = "CkycrCommonMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CkycrCommonMgmtFacade implements CkycrCommonMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    CustomerManagementFacadeRemote customerRemote;
    @EJB
    CustomerMgmtFacadeRemote customerMgmtRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dMyyyy = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat yyyyMd = new SimpleDateFormat("yyyy-MM-dd");

    public int isApplicantNameUpdate(CKYCRRequestPojo currentObject) throws Exception {
        int updateCount = 0;
        try {
            List list = em.createNativeQuery("select ifnull(custname,''),ifnull(middle_name,''),ifnull(last_name,'') "
                    + "from cbs_customer_master_detail_his where customerid='" + currentObject.getCustomerId() + "' and "
                    + "txnid in(select max(txnid) from cbs_customer_master_detail_his where "
                    + "customerid='" + currentObject.getCustomerId() + "')").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Data not found to check the applicant name updation.");
            }
            Vector ele = (Vector) list.get(0);
            updateCount += currentObject.getCustName().trim().equalsIgnoreCase(ele.get(0).toString().trim()) ? 0 : 1;
            updateCount += currentObject.getMiddleName().trim().equalsIgnoreCase(ele.get(1).toString().trim()) ? 0 : 1;
            updateCount += currentObject.getLastName().trim().equalsIgnoreCase(ele.get(2).toString().trim()) ? 0 : 1;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return updateCount;
    }

    public int isPersonalEntityDetailUpdate(CKYCRRequestPojo currentObject) throws Exception {
        int updateCount = 0;
        try {
            //Check if data does not exist
            CBSCustomerMasterDetailHisTO hisTo = customerMgmtRemote.getCustomerLastChangeDetail(currentObject.getCustomerId().trim());
            if (hisTo == null) {
                throw new Exception("Data not found to check the customer detail updation.");
            }
            //Check if data does not exist
//            CBSCustMISInfoTO misTO = customerRemote.getMisInfoByCustId(currentObject.getCustomerId().trim());
//            if (misTO == null) {
//                return updateCount;
//            }
            //Check if data does not exist
            CBSCustMISInfoHisTO misHisTo = customerMgmtRemote.getCustomerLastChangeDetailForMis(currentObject.getCustomerId().trim());
            if (misHisTo == null) {
                throw new Exception("Data not found to check the customer mis detail updation.");
            }
            //Common(For Both) validation. We are not considering the CKYC no/FI reference No updation
            updateCount += currentObject.getConstitutionCode().equalsIgnoreCase((misHisTo.getConstitutionCode())) ? 0 : 1;
            updateCount += currentObject.getDateOfBirth().equalsIgnoreCase((hisTo.getDateOfBirth())) ? 0 : 1; //Here we have to check the history value.
            //Separate validation
            if (currentObject.getCustEntityType().equals("01")) { //Individual
                updateCount += currentObject.getAcType().equalsIgnoreCase(hisTo.getAcType()) ? 0 : 1;
                updateCount += currentObject.getTitle().equalsIgnoreCase(hisTo.getTitle()) ? 0 : 1;
                updateCount += currentObject.getFatherSpouseFlag().equalsIgnoreCase(hisTo.getFatherSpouseFlag()) ? 0 : 1;
                if (currentObject.getPanGirNumber().length() == 10
                        && ParseFileUtil.isValidPAN(currentObject.getPanGirNumber().trim())
                        && currentObject.getFatherSpouseFlag().equalsIgnoreCase("Y")) {
                    //For Spouse
                    updateCount += currentObject.getSpouseName().equalsIgnoreCase(hisTo.getSpouseName()) ? 0 : 1;
                    updateCount += currentObject.getSpouseMiddleName().equalsIgnoreCase(hisTo.getSpouseMiddleName()) ? 0 : 1;
                    updateCount += currentObject.getSpouseLastName().equalsIgnoreCase(hisTo.getSpouseLastName()) ? 0 : 1;
                } else {
                    //For Father
                    updateCount += currentObject.getFatherName().equalsIgnoreCase(hisTo.getFathername()) ? 0 : 1;
                    updateCount += currentObject.getFatherMiddleName().equalsIgnoreCase(hisTo.getFatherMiddleName()) ? 0 : 1;
                    updateCount += currentObject.getFatherLastName().equalsIgnoreCase(hisTo.getFatherLastName()) ? 0 : 1;
                }
                updateCount += currentObject.getMotherName().equalsIgnoreCase(hisTo.getMothername()) ? 0 : 1;
                updateCount += currentObject.getMotherMiddleName().equalsIgnoreCase(hisTo.getMotherMiddleName()) ? 0 : 1;
                updateCount += currentObject.getMotherLastName().equalsIgnoreCase(hisTo.getMotherLastName()) ? 0 : 1;
                updateCount += currentObject.getGender().equalsIgnoreCase(hisTo.getGender()) ? 0 : 1;
                updateCount += currentObject.getMaritalStatus().equalsIgnoreCase(hisTo.getMaritalstatus()) ? 0 : 1;
                updateCount += currentObject.getNationality().equalsIgnoreCase(hisTo.getNationality()) ? 0 : 1;
                updateCount += currentObject.getOccupationCode().equalsIgnoreCase(misHisTo.getOccupationCode()) ? 0 : 1;
                updateCount += currentObject.getNriFlag().equalsIgnoreCase(hisTo.getNriflag()) ? 0 : 1;
                //Check It.
                updateCount += currentObject.getMisJuriResidence().equalsIgnoreCase(misHisTo.getMisJuriResidence()) ? 0 : 1;
                if (!currentObject.getMisJuriResidence().equalsIgnoreCase("IN")) {
                    updateCount += currentObject.getMisTin().equalsIgnoreCase(misHisTo.getMisTin()) ? 0 : 1;
                    updateCount += currentObject.getMisBirthCountry().equalsIgnoreCase(misHisTo.getMisBirthCountry()) ? 0 : 1;
                    updateCount += currentObject.getMisCity().equalsIgnoreCase(misHisTo.getMisCity()) ? 0 : 1;
                }
            } else if (currentObject.getCustEntityType().equals("02")) {  //Legal Entity
                updateCount += currentObject.getAcHolderTypeFlag().equalsIgnoreCase(hisTo.getAcHolderTypeFlag()) ? 0 : 1;
                updateCount += currentObject.getAcHolderType().equalsIgnoreCase(hisTo.getAcHolderType()) ? 0 : 1;
                updateCount += currentObject.getIncorporationPlace().equalsIgnoreCase(misHisTo.getIncorpPlace()) ? 0 : 1;
                updateCount += currentObject.getCommencementDate().equalsIgnoreCase(misHisTo.getCommDt()) ? 0 : 1; //Here we have to check the history date
                updateCount += currentObject.getCountryIncorporation().equalsIgnoreCase(misHisTo.getCountryOfIncorp()) ? 0 : 1;
                updateCount += currentObject.getCountryResidenceTaxLaws().equalsIgnoreCase(misHisTo.getResidenceCountryTaxLaw()) ? 0 : 1;
                updateCount += currentObject.getLegalDocument().equalsIgnoreCase(hisTo.getLegalDocument()) ? 0 : 1;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return updateCount;
    }

    @Override
    public int isAddressDetailUpdate(CKYCRRequestPojo currentObject) throws Exception {
        int updateCount = 0;
        try {
            //Check if data does not exist
            CBSCustomerMasterDetailHisTO hisTo = customerMgmtRemote.getCustomerLastChangeDetail(currentObject.getCustomerId().trim());
            if (hisTo == null) {
                throw new Exception("Data not found to check the customer detail updation.");
            }
            //Common Validation
            updateCount += currentObject.getPerAddType().equalsIgnoreCase((hisTo.getPerAddType())) ? 0 : 1;
            updateCount += currentObject.getPerAddressLineOne().equalsIgnoreCase((hisTo.getPerAddressLine1())) ? 0 : 1;
            updateCount += currentObject.getPerVillage().equalsIgnoreCase((hisTo.getPerVillage())) ? 0 : 1;
            updateCount += currentObject.getPerDistrict().equalsIgnoreCase((hisTo.getPerDistrict())) ? 0 : 1;
            updateCount += currentObject.getPerStateCode().equalsIgnoreCase((hisTo.getPerStateCode())) ? 0 : 1;
            updateCount += currentObject.getPerCountryCode().equalsIgnoreCase((hisTo.getPerCountryCode())) ? 0 : 1;
            if (currentObject.getPerCountryCode().equalsIgnoreCase("IN")) {
                updateCount += currentObject.getPerPostalCode().equalsIgnoreCase((hisTo.getPerPostalCode())) ? 0 : 1;
            }
            updateCount += currentObject.getPoa().equalsIgnoreCase((hisTo.getPoa())) ? 0 : 1;

            if (currentObject.getCustEntityType().equals("01")) {//Individual
                if (currentObject.getPoa().equalsIgnoreCase("99")) { //For Others
                    updateCount += currentObject.getPerOtherPoa().equalsIgnoreCase((hisTo.getPerOtherPOA())) ? 0 : 1;
                }
            } else if (currentObject.getCustEntityType().equals("02")) { //Legal Entity
                //No Requirement
            }

            updateCount += currentObject.getPerMailAddSameFlagIndicate().equalsIgnoreCase((hisTo.getPerMailAddSameFlagIndicate())) ? 0 : 1;
            updateCount += currentObject.getMailAddType().equalsIgnoreCase((hisTo.getMailAddType())) ? 0 : 1;
            updateCount += currentObject.getMailAddressLineOne().equalsIgnoreCase((hisTo.getMailAddressLine1())) ? 0 : 1;
            updateCount += currentObject.getMailVillage().equalsIgnoreCase((hisTo.getMailVillage())) ? 0 : 1;
            updateCount += currentObject.getMailDistrict().equalsIgnoreCase((hisTo.getMailDistrict())) ? 0 : 1;
            updateCount += currentObject.getMailStateCode().equalsIgnoreCase((hisTo.getMailStateCode())) ? 0 : 1;
            updateCount += currentObject.getMailCountryCode().equalsIgnoreCase((hisTo.getMailCountryCode())) ? 0 : 1;
            if (currentObject.getMailCountryCode().equalsIgnoreCase("IN")) {
                updateCount += currentObject.getMailPostalCode().equalsIgnoreCase((hisTo.getMailPostalCode())) ? 0 : 1;
            }
            updateCount += currentObject.getMailPoa().equalsIgnoreCase((hisTo.getMailPoa())) ? 0 : 1;
            updateCount += currentObject.getJuriAddBasedOnFlag().equalsIgnoreCase((hisTo.getJuriAddBasedOnFlag())) ? 0 : 1;
            updateCount += currentObject.getJuriAddType().equalsIgnoreCase((hisTo.getJuriAddType())) ? 0 : 1;
            updateCount += currentObject.getJuriAddOne().equalsIgnoreCase((hisTo.getJuriAdd1())) ? 0 : 1;
            updateCount += currentObject.getJuriCity().equalsIgnoreCase((hisTo.getJuriCity())) ? 0 : 1;
            updateCount += currentObject.getJuriState().equalsIgnoreCase((hisTo.getJuriState())) ? 0 : 1;
            updateCount += currentObject.getJuriCountry().equalsIgnoreCase((hisTo.getJuriCountry())) ? 0 : 1;
            if (currentObject.getJuriCountry().equalsIgnoreCase("IN")) {
                updateCount += currentObject.getJuriPostal().equalsIgnoreCase((hisTo.getJuriPostal())) ? 0 : 1;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return updateCount;
    }

    @Override
    public int isContactDetailUpdate(CKYCRRequestPojo currentObject) throws Exception {
        int updateCount = 0;
        try {
            //Check if data does not exist
            CBSCustomerMasterDetailHisTO hisTo = customerMgmtRemote.getCustomerLastChangeDetail(currentObject.getCustomerId().trim());
            if (hisTo == null) {
                throw new Exception("Data not found to check the customer detail updation.");
            }
            updateCount += currentObject.getIsdCode().equalsIgnoreCase((hisTo.getIsdCode())) ? 0 : 1;
            updateCount += currentObject.getMobileNo().equalsIgnoreCase((hisTo.getMobilenumber())) ? 0 : 1;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return updateCount;
    }

    @Override
    public int isKycDetailUpdate(CKYCRRequestPojo currentObject) throws Exception {
        int updateCount = 0;
        try {
            //Check if data does not exist
            CBSCustomerMasterDetailHisTO hisTo = customerMgmtRemote.getCustomerLastChangeDetail(currentObject.getCustomerId().trim());
            if (hisTo == null) {
                throw new Exception("Data not found to check the customer detail updation.");
            }
            //We are not considering the following fields:
            //Place of Declaration,KYC Verification Designation,KYC Verification Branch,Organisation Name,Organisation Code

            //History of KYC Detail
            CbsCustKycDetailsTo kycHisTo = getCustomerKycDetailHistory(currentObject.getCustomerId());
            updateCount += currentObject.getCustomerUpdationDate().equalsIgnoreCase((hisTo.getCustUpdationDate())) ? 0 : 1;
            updateCount += currentObject.getTypeOfDocSubmitted().equalsIgnoreCase((kycHisTo.getTypeOfDocSubmitted())) ? 0 : 1;
            updateCount += currentObject.getKycVerifiedUserName().equalsIgnoreCase((kycHisTo.getKycVerifiedUserName())) ? 0 : 1;
            updateCount += currentObject.getKycVerifiedBy().equalsIgnoreCase((kycHisTo.getKycVerifiedBy())) ? 0 : 1;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return updateCount;
    }

    @Override
    public int isIdentityDetailUpdate(CKYCRRequestPojo currentObject) throws Exception {
        int updateCount = 0;
        try {
            CBSCustomerMasterDetailHisTO hisTo = customerMgmtRemote.getCustomerLastChangeDetail(currentObject.getCustomerId().trim());
            if (hisTo == null) {
                throw new Exception("Data not found to check the customer detail updation.");
            }
            updateCount += currentObject.getLegalDocument().equalsIgnoreCase(hisTo.getLegalDocument()) ? 0 : 1;
            updateCount += currentObject.getIdentityNo().equalsIgnoreCase(hisTo.getIdentityNo()) ? 0 : 1;
            updateCount += currentObject.getIdExpiryDate().equalsIgnoreCase(hisTo.getIdExpiryDate()) ? 0 : 1;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return updateCount;
    }

    @Override
    public int isImageDetailUpdate(CKYCRRequestPojo currentObject) throws Exception {
        int updateCount = 0;
        try {
            CBSCustomerMasterDetailHisTO hisTo = customerMgmtRemote.getCustomerLastChangeDetail(currentObject.getCustomerId().trim());
            if (hisTo == null) {
                throw new Exception("Data not found to check the customer detail updation.");
            }
            updateCount += currentObject.getCustImage().equalsIgnoreCase(hisTo.getCustImage()) ? 0 : 1;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return updateCount;
    }

    @Override
    public int isRelatedDetailUpdate(CKYCRRequestPojo currentObject) throws Exception {
        int updateCount = 0;
        try {
            List historyList = em.createNativeQuery("select a.customerid,a.related_custid,a.relation_type,a.del_flag,"
                    + "ifnull(p.title,'') as title,ifnull(p.custname,'') as custname,ifnull(p.middle_name,'') as middle_name,"
                    + "ifnull(p.last_name,'') as last_name,ifnull(p.ckycno,'') as ckyc_no from(select r.customerid,"
                    + "ifnull(r.person_customerid,'') as related_custid,ifnull(r.relationship_code,'') as relation_type,"
                    + "ifnull(r.del_flag,'') as del_flag from cbs_customer_master_detail c,cbs_related_persons_details_his r "
                    + "where c.customerid=r.customerid and r.customerid='" + currentObject.getCustomerId() + "' and "
                    + "txn_id in(select max(txn_id) from cbs_related_persons_details_his where "
                    + "customerid='" + currentObject.getCustomerId() + "')) a,cbs_customer_master_detail p where "
                    + "a.related_custid=p.customerid").getResultList();

            if (!historyList.isEmpty()) {
                Vector ele = (Vector) historyList.get(0);
                updateCount += currentObject.getRelationType().equalsIgnoreCase(ele.get(2).toString()) ? 0 : 1;
                updateCount += currentObject.getRelatedAdditionFlag().equalsIgnoreCase(ele.get(3).toString()) ? 0 : 1;
                updateCount += currentObject.getRelatedNamePrefix().equalsIgnoreCase(ele.get(4).toString()) ? 0 : 1;
                updateCount += currentObject.getRelatedFirstName().equalsIgnoreCase(ele.get(5).toString()) ? 0 : 1;
                updateCount += currentObject.getRelatedMiddleName().equalsIgnoreCase(ele.get(6).toString()) ? 0 : 1;
                updateCount += currentObject.getRelatedLastName().equalsIgnoreCase(ele.get(7).toString()) ? 0 : 1;
                updateCount += currentObject.getRelatedCkycNo().equalsIgnoreCase(ele.get(8).toString()) ? 0 : 1;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return updateCount;
    }

    public CbsCustKycDetailsTo getCustomerKycDetailHistory(String customerId) throws Exception {
        CbsCustKycDetailsTo kycTo = new CbsCustKycDetailsTo();
        try {
            List list = em.createNativeQuery("select ifnull(CustomerId,''),ifnull(RiskCategory,''),"
                    + "ifnull(TypeOfDocSubmitted,''),ifnull(BrnCode,''),ifnull(ErrorCode,''),ifnull(CkycrUpdateFlag,''),"
                    + "ifnull(KycCreatedBy,''),date_format(KycCreationDate,'%Y%m%d'),ifnull(KycVerifiedBy,''),"
                    + "ifnull(date_format(KycVerifiedDate,'%Y%m%d'),''),ifnull(KycVerifiedUserName,'') from "
                    + "cbs_cust_kyc_details where customerid='" + customerId + "' and txnid in(select "
                    + "ifnull(max(txnid),0) from cbs_cust_kyc_details where customerid='" + customerId + "' and "
                    + "txnid not in(select ifnull(max(txnid),0) from cbs_cust_kyc_details where "
                    + "customerid='" + customerId + "'))").getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                kycTo.setCustomerId(ele.get(0).toString());
                kycTo.setRiskCategory(ele.get(1).toString());
                kycTo.setTypeOfDocSubmitted(ele.get(2).toString());
                kycTo.setBrnCode(ele.get(3).toString());
                kycTo.setErrorCode(ele.get(4).toString());
                kycTo.setCkycrUpdateFlag(ele.get(5).toString());
                kycTo.setKycCreatedBy(ele.get(6).toString());
                kycTo.setKycCreationDate(ymd.parse(ele.get(7).toString()));
                kycTo.setKycVerifiedBy(ele.get(8).toString());
                kycTo.setKycVerifiedDate(ele.get(9).toString().equals("") ? ymd.parse(ymd.format(new Date())) : ymd.parse(ele.get(9).toString()));
                kycTo.setKycVerifiedUserName(ele.get(10).toString());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return kycTo;
    }

    /*
     * If true then it is valid otherwise invalid.
     */
    public boolean isValidIndFirstAndLastName(String str) throws Exception {
        try {
            return str.matches("[a-zA-Z']+");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /*
     * If true then it is valid otherwise invalid.
     */
    public boolean isValidIndMiddleName(String str) throws Exception {
        try {
            return str.matches("[a-zA-Z' ]+");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    /*
     * If true then it is valid otherwise invalid.
     */

    public boolean isValidApplicantNameEntity(String str) throws Exception {
        try {
            return str.matches("[a-zA-Z' ]+");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public String getBranchCity(int branchCode) throws Exception {
        String city = "";
        try {
            List list = em.createNativeQuery("select ifnull(city,'') from branchmaster where "
                    + "brncode=" + branchCode + "").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define city in branchmaster.");
            }
            Vector ele = (Vector) list.get(0);
            city = ele.get(0).toString().trim().length() > 50 ? ele.get(0).toString().trim().
                    substring(0, 50).trim() : ele.get(0).toString().trim();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return city;
    }

//    public void zipDir(String filesLocation, String zipFileName,
//            String zipFolderLocation) throws Exception {
//        File dirObj = new File(filesLocation);
//        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFolderLocation + zipFileName + ".zip"));
//        System.out.println("Creating : " + zipFileName);
//        addDir(dirObj, out);
//        out.close();
//    }
//
//    public void addDir(File dirObj, ZipOutputStream out) throws Exception {
//        File[] files = dirObj.listFiles();
//        byte[] tmpBuf = new byte[2048];
//        for (int i = 0; i < files.length; i++) {
//            if (files[i].isDirectory()) {
//                addDir(files[i], out);
//                continue;
//            }
//            FileInputStream in = new FileInputStream(files[i]);
//            System.out.println(" Adding: " + files[i]);
//            out.putNextEntry(new ZipEntry(files[i].getName()));
//
//            int len;
//            while ((len = in.read(tmpBuf)) > 0) {
//                out.write(tmpBuf, 0, len);
//            }
//            out.closeEntry();
//            in.close();
//        }
//    }
    //Recursively Deletion of Files
    public void delete(File file) throws Exception {
        if (file.isDirectory()) {
            //directory is empty, then delete it
            if (file.list().length == 0) {
                file.delete();
//                System.out.println("Directory is deleted : " + file.getAbsolutePath());
            } else {
                //list all the directory contents
                String files[] = file.list();
                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);
                    //recursive delete
                    delete(fileDelete);
                }
                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
//                    System.out.println("Directory is deleted : "+ file.getAbsolutePath());
                }
            }
        } else {
            //if file, then delete it
            file.delete();
//            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }

    public List<UploadResponseDTO> parseUploadResponseStageOne(File textFile) throws Exception {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<UploadResponseDTO> pojoList = new ArrayList<UploadResponseDTO>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));

            String batchNo = "", fiCode = "", branchRegionCode = "", createDate = "",
                    requestType = "", recordStatus = "", referenceNo = "", ckycNo = "", remarks = "";
            Integer totalDetailRecord = 0, lineNo = 0;

            String line = null;
            while ((line = br.readLine()) != null) {
                String[] readArray = line.split("\\|", -1);
                if (readArray[0].equalsIgnoreCase("10")) { //For Header
                    batchNo = readArray[1].trim();
                    fiCode = readArray[2].trim();
                    branchRegionCode = readArray[3].trim();
                    totalDetailRecord = Integer.parseInt(readArray[4].trim());
                    createDate = ymd.format(dMyyyy.parse(readArray[5].trim()));
                }

                if (readArray[0].equalsIgnoreCase("20")) { //Detail
                    UploadResponseDTO pojo = new UploadResponseDTO();

                    lineNo = Integer.parseInt(readArray[1].trim());
                    requestType = readArray[2].trim();
                    recordStatus = readArray[3].trim();
                    referenceNo = readArray[4].trim();
                    ckycNo = readArray[5].trim();

                    pojo.setFileName(textFile.getName());
                    pojo.setRecordType(readArray[0].trim());
                    pojo.setFiCode(fiCode);
                    pojo.setBranchRegionCode(branchRegionCode);
                    pojo.setBatchNo(batchNo);
                    pojo.setTotalDetailRecord(totalDetailRecord);
                    pojo.setCreateDate(createDate);
                    pojo.setLineNo(lineNo);
                    pojo.setRequestType(requestType);
                    pojo.setRecordStatus(recordStatus);
                    pojo.setReferenceNo(referenceNo);
                    pojo.setCkycNo(ckycNo);
                    
                    String tempRemarks = (readArray[6] == null || readArray[6].equals("")) ? "" : readArray[6].trim();
                    tempRemarks = tempRemarks.replaceAll("[\\W_]", " ");
                    
                    pojo.setRemarks(tempRemarks);

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    //Periodic Response
    public List<UploadResponseDTO> parsePeriodicUploadResponse(File textFile) throws Exception {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<UploadResponseDTO> pojoList = new ArrayList<UploadResponseDTO>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));

            String fiCode = "", createDate = "";
            Integer totalDetailRecord = 0;

            String line = null;
            while ((line = br.readLine()) != null) {
                String[] readArray = line.split("\\|", -1);
                if (readArray[0].equalsIgnoreCase("10")) { //For Header
                    fiCode = readArray[1].trim();
                    totalDetailRecord = Integer.parseInt(readArray[2].trim());
                    createDate = ymd.format(yyyyMd.parse(readArray[4].trim()));
                }

                if (readArray[0].equalsIgnoreCase("20")) { //Detail
                    UploadResponseDTO pojo = new UploadResponseDTO();

                    pojo.setFileName(textFile.getName());
                    pojo.setFiCode(fiCode);
                    pojo.setTotalDetailRecord(totalDetailRecord);
                    pojo.setCreateDate(createDate);

                    pojo.setRecordType(readArray[0].trim());
                    pojo.setLineNo(Integer.parseInt(readArray[1].trim()));
                    pojo.setBatchNo(readArray[2].trim());
                    pojo.setReferenceNo(readArray[3].trim());
                    pojo.setRequestType(readArray[4].trim());
                    pojo.setResponseType(readArray[5].trim());
                    pojo.setRecordStatus(readArray[6].trim());
                    pojo.setIdVerificationStatus((readArray[7] == null || readArray[7].equals("")) ? "" : readArray[7].trim());
                    pojo.setCkycNo((readArray[8] == null || readArray[8].equals("")) ? "" : readArray[8].trim());
                    pojo.setRemarks((readArray[9] == null || readArray[9].equals("")) ? "" : readArray[9].trim());

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public String ckycrOccupationCode(String occupationCode) throws Exception {
        String code = "";
        try {
            List list = em.createNativeQuery("select s_gno from cbs_ref_rec_mapping where gno='03' and "
                    + "ss_gno='" + occupationCode.trim() + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please create mapping of occupation.");
            }
            Vector ele = (Vector) list.get(0);
            code = ele.get(0).toString();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return code;
    }

    public String[] getCkycrRegionCodeAndBranchCode(String alphaCode) throws Exception {
        String[] arr = new String[2];
        try {
            List regionList = em.createNativeQuery("select ss_gno,sss_gno from cbs_ref_rec_mapping where gno='04' and "
                    + "s_gno='" + alphaCode + "'").getResultList();
            if (regionList.isEmpty()) {
                throw new Exception("Please map region code");
            }
            Vector regionVec = (Vector) regionList.get(0);
            arr[0] = regionVec.get(0).toString().trim();
            arr[1] = regionVec.get(1).toString().trim();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return arr;
    }

    public int getCkycrLimit() throws Exception {
        int limitValue = 0;
        try {
            List list = em.createNativeQuery("select a.code-b.code from ((select code from parameterinfo_report where "
                    + "reportname = 'CKYCR-BULK-COUNT-LIMIT')a,(select code from parameterinfo_report where "
                    + "reportname = 'CKYCR-COUNT-MINUS')b)").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define Ckycr limit parameters.");
            }
            Vector ele = (Vector) list.get(0);
            limitValue = Integer.parseInt(ele.get(0).toString());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return limitValue;
    }

    public void zipFolder(String srcFolder, String destZipFile) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;
        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);
        addFolderToZip("", srcFolder, zip);
        zip.flush();
        zip.close();
    }

    private void addFileToZip(String path, String srcFile, ZipOutputStream zip)
            throws Exception {
        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
            throws Exception {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
            }
        }
    }

    @Override
    public void addToZipWithOutFolder(String srcFileDirectory, String zipDirectory, String zipFileName) throws Exception {
        File sourceDirectory = new File(srcFileDirectory);
        String[] files = sourceDirectory.list();
        if (files.length < 1) {
            throw new Exception("There is no file create a zip file.");
        }

        FileOutputStream fos = new FileOutputStream(zipDirectory + zipFileName);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (int i = 0; i < files.length; i++) {
            File fileToZip = new File(srcFileDirectory + files[i]);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
    }
}
