/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ckycr;

import com.cbs.dto.ckycr.CkycrRealTimeRequestPojo;
import com.cbs.dto.ckycr.CkycrRealTimeSearchDetailsPojo;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ckycr.CkycrViewMgmtFacadeRemote;
import com.cbs.pojo.CKYCRDownloadPojo;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.delegate.CustomerMasterDelegate;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.routines.checkdigit.VerhoeffCheckDigit;

public class CKYCRCustomerDetails extends BaseBean {

    private String ckycrNo;
    private String ckycrNoDwn;
    private String msg;
    private String constitutionType;
    private String accHolderTypeFlag;
    private String accHolderType;
    private String accType;
    private String custFullName;
    private String maidenFullName;
    private String father_spouse_flag;
    private String fatherSpouseFullName;
    private String motherFullName;
    private String gender;
    private String maritalStatus;
    private String nationality;
    private String occupationType;
    private String dateOfBirth;
    private String placeOfIncorporation;
    private String dateOfCommBusiness;
    private String countryOfIncorporation;
    private String residenceCountryTaxLaw;
    private String identificationType;
    private String tINNo;
    private String tINIssuingCountry;
    private String pAN;
    private String residentialStatus;
    private String flagCustResiForTaxJuriOutsideIN;
    private String juriOfResidence;
    private String juriTaxIdentificationNo;
    private String countryOfBirth;
    private String placeOfBirth;
    private String perAddType;
    private String perAddressLine1;
    private String peraddressline2;
    private String peraddressline3;
    private String perCityVillage;
    private String perDistrict;
    private String perState;
    private String perCountryCode;
    private String perPostalCode;
    private String perPOA;
    private String perOtherPOA;
    private String perMailAddSameFlagIndicate;
    private String mailAddType;
    private String mailAddressLine1;
    private String mailAddressLine2;
    private String mailAddressLine3;
    private String mailCityVillage;
    private String mailDistrict;
    private String mailState;
    private String mailCountry;
    private String mailPostalCode;
    private String mailPOA;
    private String juriAddBasedOnFlag;
    private String juriAddType;
    private String juriAddressLine1;
    private String juriAddressLine2;
    private String juriAddressLine3;
    private String juriCityVillage;
    private String juriState;
    private String juriCountry;
    private String juriPostCode;
    private String juriPOA;
    private String residenceTelSTDCode;
    private String residenceTelNo;
    private String officeTeleSTDCode;
    private String officeTelNo;
    private String iSDCode;
    private String mobileNo;
    private String faxSTDCode;
    private String faxNo;
    private String emailID;
    private String remarks;
    private String dateOfDeclaration;
    private String placeOfDeclaration;
    private String displayOnIndividual = "none";
    private String displayOnEntity = "none";
    private String displayOnSearch = "none";
    private String displayOnDownload = "none";
    private String displayGenerateCustId = "none";
    private String displayDownloadViewDetails = "none";
    private String displaySearchDetails = "none";
    private String displayOnView = "none";
    private List<SelectItem> requestTypeList;
    private List<SelectItem> idTypeList;
    private String requestType;
    private String idType;
    private String idNo;
    private String dobInput;
    private boolean localCKYCRExistFlag;
    private boolean cKYCRExistCheckFlag;
    private String requestDate;
    private String requestId;
    private String cKYCNo;
    private String name;
    private String fatherName;
    private String age;
    private String kycDate;
    private String updatedDate;
    private String reason;
    List<String> fileNames;
    private CkycrViewMgmtFacadeRemote ckycrRemote;
    CustomerMasterDelegate masterDelegate;
    SimpleDateFormat ymdWihtoutSeparator = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dmyHyphen = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public CKYCRCustomerDetails() {
        try {
            ckycrRemote = (CkycrViewMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CkycrViewMgmtFacade");
            masterDelegate = new CustomerMasterDelegate();
            displayDownloadViewDetails = "none";
            displayOnSearch = "none";
            displayOnDownload = "none";
            displayGenerateCustId = "none";
            displaySearchDetails = "none";
            List<CbsRefRecTypeTO> functionList = null;
            localCKYCRExistFlag = false;

            requestTypeList = new ArrayList<SelectItem>();
            requestTypeList.add(new SelectItem("SELECT", "--Select--"));
            requestTypeList.add(new SelectItem("SEARCH", "SEARCH"));
            requestTypeList.add(new SelectItem("DOWNLOAD", "DOWNLOAD"));
            requestTypeList.add(new SelectItem("VIEW", "VIEW"));

            idTypeList = new ArrayList<SelectItem>();
            idTypeList.add(new SelectItem("SELECT", "--Select--"));
            functionList = masterDelegate.getFunctionValues("097");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                idTypeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }


    public void onChangeRequestType() throws ApplicationException {
        if (requestType.equalsIgnoreCase("SEARCH")) {
            displayOnSearch = "";
            displayOnDownload = "none";
            displayOnView = "none";
        } else if (requestType.equalsIgnoreCase("DOWNLOAD")) {
            displayOnSearch = "none";
            displayOnView = "none";
            displayOnDownload = "";
        } else if (requestType.equalsIgnoreCase("VIEW")) {
            displayOnSearch = "none";
            displayOnDownload = "none";
            displayOnView = "";
        } else {
            displayOnSearch = "none";
            displayOnDownload = "none";
            displayOnView = "none";
        }
    }

    public String validation() {
        this.setMsg("");
        if (requestType.equalsIgnoreCase("SELECT")) {
            setMsg("Please Select Request Type. !");
            return "Please Select Request Type. !";
        }
        if (requestType.equalsIgnoreCase("SEARCH")) {
            if (idType.equalsIgnoreCase("SELECT")) {
                setMsg("Please Select ID Type. !");
                return "Please Select ID Type. !";
            }
            if (idNo.equalsIgnoreCase("")) {
                setMsg("Please Fill ID No. !");
                return "Please Fill ID No. !";
            } else {
                return validateProofDocument(idType);
            }
        }
        if (requestType.equalsIgnoreCase("DOWNLOAD")) {
            if (ckycrNo.equalsIgnoreCase("")) {
                setMsg("Please Fill CKYCR No. !");
                return "Please Fill CKYCR No. !";
            }
            if (dobInput.equalsIgnoreCase("")) {
                setMsg("Please Fill Date of Birth!");
                return "Please Fill Date of Birth!";
            }
        }
        if (requestType.equalsIgnoreCase("VIEW")) {
            if (ckycrNo.equalsIgnoreCase("")) {
                setMsg("Please Fill CKYCR No. !");
                return "Please Fill CKYCR No. !";
            }
        }
        return "ok";
    }

    public void onSubmitSearchCkyc() throws ApplicationException {
        try {
            displaySearchDetails = "none";
            displayDownloadViewDetails = "none";

            CkycrRealTimeRequestPojo pojo = new CkycrRealTimeRequestPojo();
            pojo.setRequestType(this.requestType);
            pojo.setSearchIdType(this.idType);
            pojo.setSearchDownloadNo(this.idNo);
            pojo.setDownloadDOB(dobInput);
            pojo.setEnterBy(getUserName());

            if (validation().equalsIgnoreCase("ok")) {

                if (requestType.equalsIgnoreCase("SEARCH")) {
                    CkycrRealTimeSearchDetailsPojo realTimeSearchDetail = ckycrRemote.getCkycrSearchResponse(pojo);

                    requestDate = dmyFormat.format(ymdTimeFormat.parse(realTimeSearchDetail.getRequestDate()));
                    requestId = realTimeSearchDetail.getRequestId();
                    cKYCNo = realTimeSearchDetail.getcKYCNo();
                    name = realTimeSearchDetail.getName();
                    fatherName = realTimeSearchDetail.getFatherName();
                    age = realTimeSearchDetail.getAge();
                    kycDate = dmyFormat.format(ymdTimeFormat.parse(realTimeSearchDetail.getKycDate()));
                    updatedDate = dmyFormat.format(ymdTimeFormat.parse(realTimeSearchDetail.getUpdatedDate()));
                    reason = realTimeSearchDetail.getReason();

                    //Image processing
                    ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//                    String ckycrImageLocation = context.getInitParameter("ckycrImageLocation");
                    String ckycrTempImageLocation = context.getInitParameter("ckycrTempImageLocation");
                    File ckycrTempImageDirectory = new File(ckycrTempImageLocation + File.separator + realTimeSearchDetail.getRequestId() + File.separator);
                    if (!ckycrTempImageDirectory.exists()) {
                        ckycrTempImageDirectory.mkdirs();
                    }
                    byte[] bytearray = Base64.decode(realTimeSearchDetail.getPhoto(), 0, realTimeSearchDetail.getPhoto().length);
                    BufferedImage imag = ImageIO.read(new ByteArrayInputStream(bytearray));
                    ImageIO.write(imag, "jpg", new File(ckycrTempImageDirectory + File.separator, realTimeSearchDetail.getRequestId() + ".jpg"));
                    displaySearchDetails = "";
                    displayDownloadViewDetails = "none";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMsg(ex.getMessage());
        }
    }

    public void callFromDownloadCkycAlert() {
        try {
            CkycrRealTimeRequestPojo pojo = new CkycrRealTimeRequestPojo();
            pojo.setRequestType(this.requestType);
            pojo.setSearchIdType("");
            pojo.setSearchDownloadNo(this.ckycrNo);
            pojo.setDownloadDOB(dobInput);
            pojo.setEnterBy(getUserName());
            if (ckycrRemote.getCkycrDownloadResponse(pojo).equalsIgnoreCase("success")) {
                getlocalCKYCR(ckycrNo, dobInput);
            }
        } catch (ApplicationException ex) {
            ex.printStackTrace();
            this.setMsg(ex.getMessage());
        }
    }

    public void onSubmitDownloadCkyc() throws ApplicationException {
        try {
            this.ckycrNo = this.ckycrNoDwn;
            if (requestType.equalsIgnoreCase("DOWNLOAD")) {
                cKYCRExistCheckFlag = ckycrRemote.isCKYCRDataExist(ckycrNo);
            }
            if (cKYCRExistCheckFlag == false) {
                CkycrRealTimeRequestPojo pojo = new CkycrRealTimeRequestPojo();
                pojo.setRequestType(this.requestType);
                pojo.setSearchIdType("");
                pojo.setSearchDownloadNo(this.ckycrNo);
                pojo.setDownloadDOB(dobInput);
                pojo.setEnterBy(getUserName());
                if (ckycrRemote.getCkycrDownloadResponse(pojo).equalsIgnoreCase("success")) {
                    getlocalCKYCR(ckycrNo, dobInput);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMsg(ex.getMessage());
        }
    }

    public void onSubmitView() throws ApplicationException {
        if (validation().equalsIgnoreCase("ok")) {
            if (requestType.equalsIgnoreCase("VIEW")) {
                this.getlocalCKYCR(ckycrNo, "");
                if (localCKYCRExistFlag) {
                    //if ckycr data not exist locally then do something hare//
                }
            }
        }
    }

    public void getlocalCKYCR(String ckycrNo, String dob) throws ApplicationException {
        try {
            CKYCRDownloadPojo pojo = ckycrRemote.getCKYCRClientDetails(ckycrNo, dob);
            this.localCKYCRExistFlag = pojo.isDataExist();
            this.ckycrNo = pojo.getcKYCno();
            if (!pojo.isDataExist()) {
                this.localCKYCRExistFlag = pojo.isDataExist();
                this.setMsg("CKYCR No. " + ckycrNo + " Not Exist Locally!");
                displayDownloadViewDetails = "none";
                displayOnIndividual = "none";
                displayOnEntity = "none";
                displayGenerateCustId = "none";
                displaySearchDetails = "none";
            } else {
                this.setMsg("");
                displayDownloadViewDetails = "";
                displaySearchDetails = "none";
                displayGenerateCustId = "";
                if (pojo.getConstitutionCode().equals("01")) {
                    displayOnIndividual = "";
                    displayOnEntity = "none";
                } else {
                    displayOnIndividual = "none";
                    displayOnEntity = "";
                }
                this.constitutionType = pojo.getConstitutionType();
                this.accHolderTypeFlag = pojo.getAccHolderTypeFlag();
                this.accHolderType = pojo.getAccHolderType();
                this.accType = pojo.getAccType();
                this.custFullName = pojo.getCustFullName();
                this.maidenFullName = pojo.getMaidenFullName();
                this.father_spouse_flag = pojo.getFather_spouse_flag();
                this.fatherSpouseFullName = pojo.getFatherSpouseFullName();
                this.motherFullName = pojo.getMotherFullName();
                this.gender = pojo.getGender();
                this.maritalStatus = pojo.getMaritalStatus();
                this.nationality = pojo.getNationality();
                this.occupationType = pojo.getOccupationType();
                this.dateOfBirth = pojo.getDateOfBirth();
                this.placeOfIncorporation = pojo.getPlaceOfIncorporation();
                this.dateOfCommBusiness = pojo.getDateOfCommBusiness();
                this.countryOfIncorporation = pojo.getCountryOfIncorporation();
                this.residenceCountryTaxLaw = pojo.getResidenceCountryTaxLaw();
                this.identificationType = pojo.getIdentificationType();
                this.tINNo = pojo.gettINNo();
                this.tINIssuingCountry = pojo.gettINIssuingCountry();
                this.pAN = pojo.getpAN();
                this.residentialStatus = pojo.getResidentialStatus();
                this.flagCustResiForTaxJuriOutsideIN = pojo.getFlagCustResiForTaxJuriOutsideIN();
                this.juriOfResidence = pojo.getJuriOfResidence();
                this.juriTaxIdentificationNo = pojo.getJuriTaxIdentificationNo();
                this.countryOfBirth = pojo.getCountryOfBirth();
                this.placeOfBirth = pojo.getPlaceOfBirth();
                this.perAddType = pojo.getPerAddType();
                this.perAddressLine1 = pojo.getPerAddressLine1();
                this.peraddressline2 = pojo.getPeraddressline2();
                this.peraddressline3 = pojo.getPeraddressline3();
                this.perCityVillage = pojo.getPerCityVillage();
                this.perDistrict = pojo.getPerDistrict();
                this.perState = pojo.getPerState();
                this.perCountryCode = pojo.getPerCountryCode();
                this.perPostalCode = pojo.getPerPostalCode();
                this.perPOA = pojo.getPerPOA();
                this.perOtherPOA = pojo.getPerOtherPOA();
                this.perMailAddSameFlagIndicate = pojo.getPerMailAddSameFlagIndicate();
                this.mailAddType = pojo.getMailAddType();
                this.mailAddressLine1 = pojo.getMailAddressLine1();
                this.mailAddressLine2 = pojo.getMailAddressLine2();
                this.mailAddressLine3 = pojo.getMailAddressLine3();
                this.mailCityVillage = pojo.getMailCityVillage();
                this.mailDistrict = pojo.getMailDistrict();
                this.mailState = pojo.getMailState();
                this.mailCountry = pojo.getMailCountry();
                this.mailPostalCode = pojo.getMailPostalCode();
                this.mailPOA = pojo.getMailPOA();
                this.juriAddBasedOnFlag = pojo.getJuriAddBasedOnFlag();
                this.juriAddType = pojo.getJuriAddType();
                this.juriAddressLine1 = pojo.getJuriAddressLine1();
                this.juriAddressLine2 = pojo.getJuriAddressLine2();
                this.juriAddressLine3 = pojo.getJuriAddressLine3();
                this.juriCityVillage = pojo.getJuriCityVillage();
                this.juriState = pojo.getJuriState();
                this.juriCountry = pojo.getJuriCountry();
                this.juriPostCode = pojo.getJuriPostCode();
                this.juriPOA = pojo.getJuriPOA();
                this.residenceTelSTDCode = pojo.getResidenceTelSTDCode();
                this.residenceTelNo = pojo.getResidenceTelSTDCode() + "-" + pojo.getResidenceTelNo();
                this.officeTeleSTDCode = pojo.getOfficeTeleSTDCode();
                this.officeTelNo = pojo.getOfficeTeleSTDCode() + "-" + pojo.getOfficeTelNo();
                this.iSDCode = pojo.getiSDCode();
                this.mobileNo = pojo.getiSDCode() + "-" + pojo.getMobileNo();
                this.faxSTDCode = pojo.getFaxSTDCode();
                this.faxNo = pojo.getFaxSTDCode() + "-" + pojo.getFaxNo();
                this.emailID = pojo.getEmailID();
                this.remarks = pojo.getRemarks();
                this.dateOfDeclaration = pojo.getDateOfDeclaration();
                this.placeOfDeclaration = pojo.getPlaceOfDeclaration();

                //Image processing
                ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String ckycrImageLocation = context.getInitParameter("ckycrImageLocation");
                String ckycrTempImageLocation = context.getInitParameter("ckycrTempImageLocation");

                File ckycrImageDirectory = new File(ckycrImageLocation + File.separator + pojo.getcKYCno() + File.separator);
                if (!ckycrImageDirectory.exists()) {
                    this.setMsg("There is no image for this CKYC No.!");
                    return;
                }
                File ckycrTempImageDirectory = new File(ckycrTempImageLocation + File.separator + pojo.getcKYCno() + File.separator);
                if (!ckycrTempImageDirectory.exists()) {
                    ckycrTempImageDirectory.mkdirs();
                }

                File[] files = ckycrImageDirectory.listFiles();
                if (files.length == 0) {
                    this.setMsg("There is no image for this CKYC No.!");
                    return;
                }
                fileNames = new ArrayList<String>();
//-------------------------------------------------------------------------------------//
                FileSeekableStream stream = null;
                ImageDecoder dec = null;
                RenderedImage image = null;
                for (File file : files) {
                    String fileName = file.getName().trim();
                    String dumpFileName = "";
                    if (fileName.trim().toLowerCase().endsWith("tif") || fileName.trim().toLowerCase().endsWith("tiff")) {
                        stream = new FileSeekableStream(ckycrImageDirectory + File.separator + fileName);
                        dec = ImageCodec.createImageDecoder("tiff", stream, null);
                        image = dec.decodeAsRenderedImage(0);

                        dumpFileName = fileName.substring(0, fileName.indexOf(".")) + ".jpg";
                        JAI.create("filestore", image, ckycrTempImageDirectory + File.separator + dumpFileName, "JPEG");
                        fileNames.add(dumpFileName);
                    } else if (fileName.trim().toLowerCase().endsWith("jpg") || fileName.trim().toLowerCase().endsWith("jpeg")) {
                        File source = new File(ckycrImageDirectory + File.separator + fileName);
                        dumpFileName = fileName;
                        File destination = new File(ckycrTempImageDirectory + File.separator + fileName);
                        IOUtils.copy(new FileInputStream(source), new FileOutputStream(destination));
                        fileNames.add(dumpFileName);
                    } else if (fileName.trim().toLowerCase().endsWith("pdf")) {
                        List<File> pdfImages = CbsUtil.pdfToImage(file.getCanonicalPath(), ckycrTempImageDirectory + File.separator);
                        for (File pdfImage : pdfImages) {
                            fileNames.add(pdfImage.getName());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMsg(ex.getMessage());
        }
    }

    public void onClickGenerateCustId() {
        if (localCKYCRExistFlag) {
            try {
                String msg1 = "";
                if (requestType.equalsIgnoreCase("DOWNLOAD")) {
                    msg1 = ckycrRemote.createCustomerIdByCKYCR(this.ckycrNo, this.dobInput, this.getUserName(), this.getOrgnBrCode());
                } else if (requestType.equalsIgnoreCase("SEARCH")) {
//                    msg1 = ckycrRemote.createCustomerIdByCKYCR(this.idNo, this.dobInput, this.getUserName(), this.getOrgnBrCode());
                } else if (requestType.equalsIgnoreCase("VIEW")) {
                    msg1 = ckycrRemote.createCustomerIdByCKYCR(this.ckycrNo, "", this.getUserName(), this.getOrgnBrCode());
                }
                this.setMsg(msg1);
                System.out.println("Customer Generation ---------->" + msg1);
            } catch (ApplicationException ex) {
                this.setMsg(ex.getMessage());
            }
        }
        this.displayDownloadViewDetails = "none";
        localCKYCRExistFlag = false;
        displayGenerateCustId = "none";
    }

    public String validateProofDocument(String doc) {


        if (doc.equalsIgnoreCase("A")) {
            if (this.idNo.length() != 8) {
                this.setMsg("Please Fill 8 digit Passport No. in Identity No.");
                return "Please Fill 8 digit Passport No. in Identity No.";
            }
            Pattern passportNoPattern = Pattern.compile("(([a-zA-Z]{1})\\d{7})");
            Matcher matcher = passportNoPattern.matcher(this.idNo);
            if (!matcher.matches()) {
                return "Please fill valid Passport No. in Identity No.";
            }
        } else if (doc.equalsIgnoreCase("E")) {
            if (this.idNo.length() != 12) {
                this.setMsg("Please Fill 12 digit Aadhaar No. in Identity No.");
                return "Please Fill 12 digit Aadhaar No. in Identity No.";
            }
            if (!VerhoeffCheckDigit.VERHOEFF_CHECK_DIGIT.isValid(this.idNo)) {
                this.setMsg("Please Fill Valid Aadhaar No. in Identity No.");
                return "Please Fill Valid Aadhaar No. in Identity No.";
            }
        } else if (doc.equalsIgnoreCase("C")) {
            if (this.idNo.length() != 10) {
                this.setMsg("Please Fill 10 digit PAN/GIR No. in Identity No.");
                return "Please Fill 10 digit PAN/GIR No. in Identity No.";
            }
            Pattern panNoPattern = Pattern.compile("((([a-zA-Z]{5})\\d{4})[a-zA-Z]{1})");
            Matcher matcher = panNoPattern.matcher(this.idNo);
            if (!matcher.matches()) {
                this.setMsg("Please Fill Valid PAN/GIR No. in Identity No.");
                return "Please Fill Valid PAN/GIR No. in Identity No.";
            }
        } else if (doc.equalsIgnoreCase("Z")) {
            if (this.idNo.length() != 14) {
                this.setMsg("Please Fill 14 digit CKYC No. in Identity No.");
                return "Please Fill 14 digit CKYC No. in Identity No.";
            }
            Pattern numberPattern = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher matcher = numberPattern.matcher(this.idNo);
            if (!matcher.matches()) {
                return "Please fill valid CKYC No. in Identity No.";
            }
        }
        return "ok";
    }

    public void refreshPage() {
        displayDownloadViewDetails = "none";
        displayOnDownload = "none";
        displayOnSearch = "none";
        displayGenerateCustId = "none";
        displaySearchDetails = "none";
        displayOnView = "none";
        localCKYCRExistFlag = false;
        this.setMsg("");
        this.requestType = "SELECT";
        this.idType = "SELECT";
        this.idNo = "";
        this.dobInput = "";
        this.ckycrNo = "";
        this.displayDownloadViewDetails = "none";
        this.constitutionType = "";
        this.accHolderTypeFlag = "";
        this.accHolderType = "";
        this.accType = "";
        this.custFullName = "";
        this.maidenFullName = "";
        this.father_spouse_flag = "";
        this.fatherSpouseFullName = "";
        this.motherFullName = "";
        this.gender = "";
        this.maritalStatus = "";
        this.nationality = "";
        this.occupationType = "";
        this.dateOfBirth = "";
        this.placeOfIncorporation = "";
        this.dateOfCommBusiness = "";
        this.countryOfIncorporation = "";
        this.residenceCountryTaxLaw = "";
        this.identificationType = "";
        this.tINNo = "";
        this.tINIssuingCountry = "";
        this.pAN = "";
        this.residentialStatus = "";
        this.flagCustResiForTaxJuriOutsideIN = "";
        this.juriOfResidence = "";
        this.juriTaxIdentificationNo = "";
        this.countryOfBirth = "";
        this.placeOfBirth = "";
        this.perAddType = "";
        this.perAddressLine1 = "";
        this.peraddressline2 = "";
        this.peraddressline3 = "";
        this.perCityVillage = "";
        this.perDistrict = "";
        this.perState = "";
        this.perCountryCode = "";
        this.perPostalCode = "";
        this.perPOA = "";
        this.perOtherPOA = "";
        this.perMailAddSameFlagIndicate = "";
        this.mailAddType = "";
        this.mailAddressLine1 = "";
        this.mailAddressLine2 = "";
        this.mailAddressLine3 = "";
        this.mailCityVillage = "";
        this.mailDistrict = "";
        this.mailState = "";
        this.mailCountry = "";
        this.mailPostalCode = "";
        this.mailPOA = "";
        this.juriAddBasedOnFlag = "";
        this.juriAddType = "";
        this.juriAddressLine1 = "";
        this.juriAddressLine2 = "";
        this.juriAddressLine3 = "";
        this.juriCityVillage = "";
        this.juriState = "";
        this.juriCountry = "";
        this.juriPostCode = "";
        this.juriPOA = "";
        this.residenceTelSTDCode = "";
        this.residenceTelNo = "";
        this.officeTeleSTDCode = "";
        this.officeTelNo = "";
        this.iSDCode = "";
        this.mobileNo = "";
        this.faxSTDCode = "";
        this.faxNo = "";
        this.emailID = "";
        this.remarks = "";
        this.dateOfDeclaration = "";
        this.placeOfDeclaration = "";




        this.requestDate = "";
        this.requestId = "";
        this.cKYCNo = "";
        this.name = "";
        this.fatherName = "";
        this.age = "";
        this.kycDate = "";
        this.updatedDate = "";
        this.reason = "";
    }

    public String exitForm() {
        return "case1";
    }

    public String getCkycrNo() {
        return ckycrNo;
    }

    public void setCkycrNo(String ckycrNo) {
        this.ckycrNo = ckycrNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getConstitutionType() {
        return constitutionType;
    }

    public void setConstitutionType(String constitutionType) {
        this.constitutionType = constitutionType;
    }

    public String getAccHolderTypeFlag() {
        return accHolderTypeFlag;
    }

    public void setAccHolderTypeFlag(String accHolderTypeFlag) {
        this.accHolderTypeFlag = accHolderTypeFlag;
    }

    public String getAccHolderType() {
        return accHolderType;
    }

    public void setAccHolderType(String accHolderType) {
        this.accHolderType = accHolderType;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getCustFullName() {
        return custFullName;
    }

    public void setCustFullName(String custFullName) {
        this.custFullName = custFullName;
    }

    public String getMaidenFullName() {
        return maidenFullName;
    }

    public void setMaidenFullName(String maidenFullName) {
        this.maidenFullName = maidenFullName;
    }

    public String getFather_spouse_flag() {
        return father_spouse_flag;
    }

    public void setFather_spouse_flag(String father_spouse_flag) {
        this.father_spouse_flag = father_spouse_flag;
    }

    public String getFatherSpouseFullName() {
        return fatherSpouseFullName;
    }

    public void setFatherSpouseFullName(String fatherSpouseFullName) {
        this.fatherSpouseFullName = fatherSpouseFullName;
    }

    public String getMotherFullName() {
        return motherFullName;
    }

    public void setMotherFullName(String motherFullName) {
        this.motherFullName = motherFullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getOccupationType() {
        return occupationType;
    }

    public void setOccupationType(String occupationType) {
        this.occupationType = occupationType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfIncorporation() {
        return placeOfIncorporation;
    }

    public void setPlaceOfIncorporation(String placeOfIncorporation) {
        this.placeOfIncorporation = placeOfIncorporation;
    }

    public String getDateOfCommBusiness() {
        return dateOfCommBusiness;
    }

    public void setDateOfCommBusiness(String dateOfCommBusiness) {
        this.dateOfCommBusiness = dateOfCommBusiness;
    }

    public String getCountryOfIncorporation() {
        return countryOfIncorporation;
    }

    public void setCountryOfIncorporation(String countryOfIncorporation) {
        this.countryOfIncorporation = countryOfIncorporation;
    }

    public String getResidenceCountryTaxLaw() {
        return residenceCountryTaxLaw;
    }

    public void setResidenceCountryTaxLaw(String residenceCountryTaxLaw) {
        this.residenceCountryTaxLaw = residenceCountryTaxLaw;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String gettINNo() {
        return tINNo;
    }

    public void settINNo(String tINNo) {
        this.tINNo = tINNo;
    }

    public String gettINIssuingCountry() {
        return tINIssuingCountry;
    }

    public void settINIssuingCountry(String tINIssuingCountry) {
        this.tINIssuingCountry = tINIssuingCountry;
    }

    public String getpAN() {
        return pAN;
    }

    public void setpAN(String pAN) {
        this.pAN = pAN;
    }

    public String getResidentialStatus() {
        return residentialStatus;
    }

    public void setResidentialStatus(String residentialStatus) {
        this.residentialStatus = residentialStatus;
    }

    public String getFlagCustResiForTaxJuriOutsideIN() {
        return flagCustResiForTaxJuriOutsideIN;
    }

    public void setFlagCustResiForTaxJuriOutsideIN(String flagCustResiForTaxJuriOutsideIN) {
        this.flagCustResiForTaxJuriOutsideIN = flagCustResiForTaxJuriOutsideIN;
    }

    public String getJuriOfResidence() {
        return juriOfResidence;
    }

    public void setJuriOfResidence(String juriOfResidence) {
        this.juriOfResidence = juriOfResidence;
    }

    public String getJuriTaxIdentificationNo() {
        return juriTaxIdentificationNo;
    }

    public void setJuriTaxIdentificationNo(String juriTaxIdentificationNo) {
        this.juriTaxIdentificationNo = juriTaxIdentificationNo;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPerAddType() {
        return perAddType;
    }

    public void setPerAddType(String perAddType) {
        this.perAddType = perAddType;
    }

    public String getPerAddressLine1() {
        return perAddressLine1;
    }

    public void setPerAddressLine1(String perAddressLine1) {
        this.perAddressLine1 = perAddressLine1;
    }

    public String getPeraddressline2() {
        return peraddressline2;
    }

    public void setPeraddressline2(String peraddressline2) {
        this.peraddressline2 = peraddressline2;
    }

    public String getPeraddressline3() {
        return peraddressline3;
    }

    public void setPeraddressline3(String peraddressline3) {
        this.peraddressline3 = peraddressline3;
    }

    public String getPerCityVillage() {
        return perCityVillage;
    }

    public void setPerCityVillage(String perCityVillage) {
        this.perCityVillage = perCityVillage;
    }

    public String getPerDistrict() {
        return perDistrict;
    }

    public void setPerDistrict(String perDistrict) {
        this.perDistrict = perDistrict;
    }

    public String getPerState() {
        return perState;
    }

    public void setPerState(String perState) {
        this.perState = perState;
    }

    public String getPerCountryCode() {
        return perCountryCode;
    }

    public void setPerCountryCode(String perCountryCode) {
        this.perCountryCode = perCountryCode;
    }

    public String getPerPostalCode() {
        return perPostalCode;
    }

    public void setPerPostalCode(String perPostalCode) {
        this.perPostalCode = perPostalCode;
    }

    public String getPerPOA() {
        return perPOA;
    }

    public void setPerPOA(String perPOA) {
        this.perPOA = perPOA;
    }

    public String getPerOtherPOA() {
        return perOtherPOA;
    }

    public void setPerOtherPOA(String perOtherPOA) {
        this.perOtherPOA = perOtherPOA;
    }

    public String getPerMailAddSameFlagIndicate() {
        return perMailAddSameFlagIndicate;
    }

    public void setPerMailAddSameFlagIndicate(String perMailAddSameFlagIndicate) {
        this.perMailAddSameFlagIndicate = perMailAddSameFlagIndicate;
    }

    public String getMailAddType() {
        return mailAddType;
    }

    public void setMailAddType(String mailAddType) {
        this.mailAddType = mailAddType;
    }

    public String getMailAddressLine1() {
        return mailAddressLine1;
    }

    public void setMailAddressLine1(String mailAddressLine1) {
        this.mailAddressLine1 = mailAddressLine1;
    }

    public String getMailAddressLine2() {
        return mailAddressLine2;
    }

    public void setMailAddressLine2(String mailAddressLine2) {
        this.mailAddressLine2 = mailAddressLine2;
    }

    public String getMailAddressLine3() {
        return mailAddressLine3;
    }

    public void setMailAddressLine3(String mailAddressLine3) {
        this.mailAddressLine3 = mailAddressLine3;
    }

    public String getMailCityVillage() {
        return mailCityVillage;
    }

    public void setMailCityVillage(String mailCityVillage) {
        this.mailCityVillage = mailCityVillage;
    }

    public String getMailDistrict() {
        return mailDistrict;
    }

    public void setMailDistrict(String mailDistrict) {
        this.mailDistrict = mailDistrict;
    }

    public String getMailState() {
        return mailState;
    }

    public void setMailState(String mailState) {
        this.mailState = mailState;
    }

    public String getMailCountry() {
        return mailCountry;
    }

    public void setMailCountry(String mailCountry) {
        this.mailCountry = mailCountry;
    }

    public String getMailPostalCode() {
        return mailPostalCode;
    }

    public void setMailPostalCode(String mailPostalCode) {
        this.mailPostalCode = mailPostalCode;
    }

    public String getMailPOA() {
        return mailPOA;
    }

    public void setMailPOA(String mailPOA) {
        this.mailPOA = mailPOA;
    }

    public String getJuriAddBasedOnFlag() {
        return juriAddBasedOnFlag;
    }

    public void setJuriAddBasedOnFlag(String juriAddBasedOnFlag) {
        this.juriAddBasedOnFlag = juriAddBasedOnFlag;
    }

    public String getJuriAddType() {
        return juriAddType;
    }

    public void setJuriAddType(String juriAddType) {
        this.juriAddType = juriAddType;
    }

    public String getJuriAddressLine1() {
        return juriAddressLine1;
    }

    public void setJuriAddressLine1(String juriAddressLine1) {
        this.juriAddressLine1 = juriAddressLine1;
    }

    public String getJuriAddressLine2() {
        return juriAddressLine2;
    }

    public void setJuriAddressLine2(String juriAddressLine2) {
        this.juriAddressLine2 = juriAddressLine2;
    }

    public String getJuriAddressLine3() {
        return juriAddressLine3;
    }

    public void setJuriAddressLine3(String juriAddressLine3) {
        this.juriAddressLine3 = juriAddressLine3;
    }

    public String getJuriCityVillage() {
        return juriCityVillage;
    }

    public void setJuriCityVillage(String juriCityVillage) {
        this.juriCityVillage = juriCityVillage;
    }

    public String getJuriState() {
        return juriState;
    }

    public void setJuriState(String juriState) {
        this.juriState = juriState;
    }

    public String getJuriCountry() {
        return juriCountry;
    }

    public void setJuriCountry(String juriCountry) {
        this.juriCountry = juriCountry;
    }

    public String getJuriPostCode() {
        return juriPostCode;
    }

    public void setJuriPostCode(String juriPostCode) {
        this.juriPostCode = juriPostCode;
    }

    public String getJuriPOA() {
        return juriPOA;
    }

    public void setJuriPOA(String juriPOA) {
        this.juriPOA = juriPOA;
    }

    public String getResidenceTelSTDCode() {
        return residenceTelSTDCode;
    }

    public void setResidenceTelSTDCode(String residenceTelSTDCode) {
        this.residenceTelSTDCode = residenceTelSTDCode;
    }

    public String getResidenceTelNo() {
        return residenceTelNo;
    }

    public void setResidenceTelNo(String residenceTelNo) {
        this.residenceTelNo = residenceTelNo;
    }

    public String getOfficeTeleSTDCode() {
        return officeTeleSTDCode;
    }

    public void setOfficeTeleSTDCode(String officeTeleSTDCode) {
        this.officeTeleSTDCode = officeTeleSTDCode;
    }

    public String getOfficeTelNo() {
        return officeTelNo;
    }

    public void setOfficeTelNo(String officeTelNo) {
        this.officeTelNo = officeTelNo;
    }

    public String getiSDCode() {
        return iSDCode;
    }

    public void setiSDCode(String iSDCode) {
        this.iSDCode = iSDCode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFaxSTDCode() {
        return faxSTDCode;
    }

    public void setFaxSTDCode(String faxSTDCode) {
        this.faxSTDCode = faxSTDCode;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDateOfDeclaration() {
        return dateOfDeclaration;
    }

    public void setDateOfDeclaration(String dateOfDeclaration) {
        this.dateOfDeclaration = dateOfDeclaration;
    }

    public String getPlaceOfDeclaration() {
        return placeOfDeclaration;
    }

    public void setPlaceOfDeclaration(String placeOfDeclaration) {
        this.placeOfDeclaration = placeOfDeclaration;
    }

    public String getDisplayOnIndividual() {
        return displayOnIndividual;
    }

    public void setDisplayOnIndividual(String displayOnIndividual) {
        this.displayOnIndividual = displayOnIndividual;
    }

    public String getDisplayOnEntity() {
        return displayOnEntity;
    }

    public void setDisplayOnEntity(String displayOnEntity) {
        this.displayOnEntity = displayOnEntity;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public String getDisplayOnSearch() {
        return displayOnSearch;
    }

    public void setDisplayOnSearch(String displayOnSearch) {
        this.displayOnSearch = displayOnSearch;
    }

    public String getDisplayOnDownload() {
        return displayOnDownload;
    }

    public void setDisplayOnDownload(String displayOnDownload) {
        this.displayOnDownload = displayOnDownload;
    }

    public List<SelectItem> getRequestTypeList() {
        return requestTypeList;
    }

    public void setRequestTypeList(List<SelectItem> requestTypeList) {
        this.requestTypeList = requestTypeList;
    }

    public List<SelectItem> getIdTypeList() {
        return idTypeList;
    }

    public void setIdTypeList(List<SelectItem> idTypeList) {
        this.idTypeList = idTypeList;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getDobInput() {
        return dobInput;
    }

    public void setDobInput(String dobInput) {
        this.dobInput = dobInput;
    }

    public boolean isLocalCKYCRExistFlag() {
        return localCKYCRExistFlag;
    }

    public void setLocalCKYCRExistFlag(boolean localCKYCRExistFlag) {
        this.localCKYCRExistFlag = localCKYCRExistFlag;
    }

    public String getDisplayGenerateCustId() {
        return displayGenerateCustId;
    }

    public void setDisplayGenerateCustId(String displayGenerateCustId) {
        this.displayGenerateCustId = displayGenerateCustId;
    }

    public String getDisplayDownloadViewDetails() {
        return displayDownloadViewDetails;
    }

    public void setDisplayDownloadViewDetails(String displayDownloadViewDetails) {
        this.displayDownloadViewDetails = displayDownloadViewDetails;
    }

    public String getDisplaySearchDetails() {
        return displaySearchDetails;
    }

    public void setDisplaySearchDetails(String displaySearchDetails) {
        this.displaySearchDetails = displaySearchDetails;
    }

    public String getDisplayOnView() {
        return displayOnView;
    }

    public void setDisplayOnView(String displayOnView) {
        this.displayOnView = displayOnView;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getcKYCNo() {
        return cKYCNo;
    }

    public void setcKYCNo(String cKYCNo) {
        this.cKYCNo = cKYCNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getKycDate() {
        return kycDate;
    }

    public void setKycDate(String kycDate) {
        this.kycDate = kycDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCkycrNoDwn() {
        return ckycrNoDwn;
    }

    public void setCkycrNoDwn(String ckycrNoDwn) {
        this.ckycrNoDwn = ckycrNoDwn;
    }

    public boolean iscKYCRExistCheckFlag() {
        return cKYCRExistCheckFlag;
    }

    public void setcKYCRExistCheckFlag(boolean cKYCRExistCheckFlag) {
        this.cKYCRExistCheckFlag = cKYCRExistCheckFlag;
    }
}
