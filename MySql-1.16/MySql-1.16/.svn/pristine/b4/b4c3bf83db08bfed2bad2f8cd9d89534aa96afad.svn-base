package com.cbs.web.controller.report.share;

import com.cbs.dto.report.ShareEnquiryPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Zeeshan Waris
 */
public final class ShareEnquiry extends BaseBean {

    private String message;
    private Date date = new Date();
    ShareReportFacadeRemote beanRemote;
    CommonReportMethodsRemote commonRemote;
    private String ddFolio, folioFrom, folioTo, ddFirstName, txtFirstname, ddLastName, txtLastName, ddHouseNo, txtHouseNo, ddSector, txtSector, ddCity, txtCity, ddEnterBy, txtEnterBy, txtFatherName, folioFromShow, folioToShow, acNoLen;
    private boolean folioNoAs, shareHolderName, firstName,
            lastName, addressWise, houseNo, sector, folioToDisable, ddFolioDisable,
            city, enterBy, fatherName, txtFatherNameDisable, txtEnterByDisable, txtCityDisable, cityDisable, txtSectorDisable, sectorDisable, txtHouseNoDisable, houseNoDisable, firstNameDisable, ddFirstNameDisable, lastNameDisable, ddLastNameDisable;
    private List<SelectItem> enterByList;
    private List<SelectItem> modeList;
    private List<SelectItem> typeList;
    FtsPostingMgmtFacadeRemote ftsPostRemote;

    public String getFolioToShow() {
        return folioToShow;
    }

    public void setFolioToShow(String folioToShow) {
        this.folioToShow = folioToShow;
    }

    public String getFolioFromShow() {
        return folioFromShow;
    }

    public void setFolioFromShow(String folioFromShow) {
        this.folioFromShow = folioFromShow;
    }

    public boolean isDdFolioDisable() {
        return ddFolioDisable;
    }

    public void setDdFolioDisable(boolean ddFolioDisable) {
        this.ddFolioDisable = ddFolioDisable;
    }

    public boolean isFolioToDisable() {
        return folioToDisable;
    }

    public void setFolioToDisable(boolean folioToDisable) {
        this.folioToDisable = folioToDisable;
    }

    public boolean isCityDisable() {
        return cityDisable;
    }

    public void setCityDisable(boolean cityDisable) {
        this.cityDisable = cityDisable;
    }

    public boolean isDdFirstNameDisable() {
        return ddFirstNameDisable;
    }

    public void setDdFirstNameDisable(boolean ddFirstNameDisable) {
        this.ddFirstNameDisable = ddFirstNameDisable;
    }

    public boolean isDdLastNameDisable() {
        return ddLastNameDisable;
    }

    public void setDdLastNameDisable(boolean ddLastNameDisable) {
        this.ddLastNameDisable = ddLastNameDisable;
    }

    public boolean isFirstNameDisable() {
        return firstNameDisable;
    }

    public void setFirstNameDisable(boolean firstNameDisable) {
        this.firstNameDisable = firstNameDisable;
    }

    public String getFolioFrom() {
        return folioFrom;
    }

    public void setFolioFrom(String folioFrom) {
        this.folioFrom = folioFrom;
    }

    public String getFolioTo() {
        return folioTo;
    }

    public void setFolioTo(String folioTo) {
        this.folioTo = folioTo;
    }

    public boolean isHouseNoDisable() {
        return houseNoDisable;
    }

    public void setHouseNoDisable(boolean houseNoDisable) {
        this.houseNoDisable = houseNoDisable;
    }

    public boolean isLastNameDisable() {
        return lastNameDisable;
    }

    public void setLastNameDisable(boolean lastNameDisable) {
        this.lastNameDisable = lastNameDisable;
    }

    public boolean isSectorDisable() {
        return sectorDisable;
    }

    public void setSectorDisable(boolean sectorDisable) {
        this.sectorDisable = sectorDisable;
    }

    public boolean isTxtCityDisable() {
        return txtCityDisable;
    }

    public void setTxtCityDisable(boolean txtCityDisable) {
        this.txtCityDisable = txtCityDisable;
    }

    public boolean isTxtEnterByDisable() {
        return txtEnterByDisable;
    }

    public void setTxtEnterByDisable(boolean txtEnterByDisable) {
        this.txtEnterByDisable = txtEnterByDisable;
    }

    public boolean isTxtFatherNameDisable() {
        return txtFatherNameDisable;
    }

    public void setTxtFatherNameDisable(boolean txtFatherNameDisable) {
        this.txtFatherNameDisable = txtFatherNameDisable;
    }

    public boolean isTxtHouseNoDisable() {
        return txtHouseNoDisable;
    }

    public void setTxtHouseNoDisable(boolean txtHouseNoDisable) {
        this.txtHouseNoDisable = txtHouseNoDisable;
    }

    public boolean isTxtSectorDisable() {
        return txtSectorDisable;
    }

    public void setTxtSectorDisable(boolean txtSectorDisable) {
        this.txtSectorDisable = txtSectorDisable;
    }

    public boolean isAddressWise() {
        return addressWise;
    }

    public void setAddressWise(boolean addressWise) {
        this.addressWise = addressWise;
    }

    public boolean isCity() {
        return city;
    }

    public void setCity(boolean city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDdCity() {
        return ddCity;
    }

    public void setDdCity(String ddCity) {
        this.ddCity = ddCity;
    }

    public String getDdEnterBy() {
        return ddEnterBy;
    }

    public void setDdEnterBy(String ddEnterBy) {
        this.ddEnterBy = ddEnterBy;
    }

    public String getDdFirstName() {
        return ddFirstName;
    }

    public void setDdFirstName(String ddFirstName) {
        this.ddFirstName = ddFirstName;
    }

    public String getDdFolio() {
        return ddFolio;
    }

    public void setDdFolio(String ddFolio) {
        this.ddFolio = ddFolio;
    }

    public String getDdHouseNo() {
        return ddHouseNo;
    }

    public void setDdHouseNo(String ddHouseNo) {
        this.ddHouseNo = ddHouseNo;
    }

    public String getDdLastName() {
        return ddLastName;
    }

    public void setDdLastName(String ddLastName) {
        this.ddLastName = ddLastName;
    }

    public String getDdSector() {
        return ddSector;
    }

    public void setDdSector(String ddSector) {
        this.ddSector = ddSector;
    }

    public boolean isEnterBy() {
        return enterBy;
    }

    public void setEnterBy(boolean enterBy) {
        this.enterBy = enterBy;
    }

    public List<SelectItem> getEnterByList() {
        return enterByList;
    }

    public void setEnterByList(List<SelectItem> enterByList) {
        this.enterByList = enterByList;
    }

    public boolean isFatherName() {
        return fatherName;
    }

    public void setFatherName(boolean fatherName) {
        this.fatherName = fatherName;
    }

    public boolean isFirstName() {
        return firstName;
    }

    public void setFirstName(boolean firstName) {
        this.firstName = firstName;
    }

    public boolean isFolioNoAs() {
        return folioNoAs;
    }

    public void setFolioNoAs(boolean folioNoAs) {
        this.folioNoAs = folioNoAs;
    }

    public boolean isHouseNo() {
        return houseNo;
    }

    public void setHouseNo(boolean houseNo) {
        this.houseNo = houseNo;
    }

    public boolean isLastName() {
        return lastName;
    }

    public void setLastName(boolean lastName) {
        this.lastName = lastName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public boolean isSector() {
        return sector;
    }

    public void setSector(boolean sector) {
        this.sector = sector;
    }

    public boolean isShareHolderName() {
        return shareHolderName;
    }

    public void setShareHolderName(boolean shareHolderName) {
        this.shareHolderName = shareHolderName;
    }

    public String getTxtCity() {
        return txtCity;
    }

    public void setTxtCity(String txtCity) {
        this.txtCity = txtCity;
    }

    public String getTxtEnterBy() {
        return txtEnterBy;
    }

    public void setTxtEnterBy(String txtEnterBy) {
        this.txtEnterBy = txtEnterBy;
    }

    public String getTxtFatherName() {
        return txtFatherName;
    }

    public void setTxtFatherName(String txtFatherName) {
        this.txtFatherName = txtFatherName;
    }

    public String getTxtFirstname() {
        return txtFirstname;
    }

    public void setTxtFirstname(String txtFirstname) {
        this.txtFirstname = txtFirstname;
    }

    public String getTxtHouseNo() {
        return txtHouseNo;
    }

    public void setTxtHouseNo(String txtHouseNo) {
        this.txtHouseNo = txtHouseNo;
    }

    public String getTxtLastName() {
        return txtLastName;
    }

    public void setTxtLastName(String txtLastName) {
        this.txtLastName = txtLastName;
    }

    public String getTxtSector() {
        return txtSector;
    }

    public void setTxtSector(String txtSector) {
        this.txtSector = txtSector;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

    public ShareEnquiry() {
        try {
            beanRemote = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("0", "Equal To"));
            typeList.add(new SelectItem("1", "Between"));
            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("S", "--Select--"));
            modeList.add(new SelectItem("0", "Starting With"));
            modeList.add(new SelectItem("1", "Equal To"));
            modeList.add(new SelectItem("2", "Ending With"));
            ddFolioDisable = true;
            folioToDisable = true;
            firstNameDisable = true;
            lastNameDisable = true;
            ddFirstNameDisable = true;
            ddLastNameDisable = true;
            houseNoDisable = true;
            sectorDisable = true;
            cityDisable = true;
            txtHouseNoDisable = true;
            txtSectorDisable = true;
            txtCityDisable = true;
            txtEnterByDisable = true;
            txtFatherNameDisable = true;
            getAccountypeList();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        setMessage("");
        try {
            if (folioNoAs == true && ddFolio.equalsIgnoreCase("0")) {
                if (folioFrom.equalsIgnoreCase("")) {
                    setMessage("Please fill from folio No");
                    return null;
                }
                //if (folioFrom.length() < 12) {
                if (!this.folioFrom.equalsIgnoreCase("") && ((this.folioFrom.length() != 12) && (this.folioFrom.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    setMessage("Please fill proper folio No");
                    return null;
                }

                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (folioNoAs == true && ddFolio.equalsIgnoreCase("1")) {
                if (folioFrom.equalsIgnoreCase("")) {
                    setMessage("Please fill from folio No");
                    return null;
                }
                //if (folioFrom.length() < 12) {
                if (!this.folioFrom.equalsIgnoreCase("") && ((this.folioFrom.length() != 12) && (this.folioFrom.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    setMessage("Please fill proper from folio No");
                    return null;
                }
                if (folioTo.equalsIgnoreCase("")) {
                    setMessage("Please fill To folio No");
                    return null;
                }
                //if (folioTo.length() < 12) {
                if (!this.folioTo.equalsIgnoreCase("") && ((this.folioTo.length() != 12) && (this.folioTo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    setMessage("Please fill proper To folio No");
                    return null;
                }
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (firstName == true) {
                if (txtFirstname.equalsIgnoreCase("")) {
                    setMessage("Please fill First Name");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (lastName == true) {
                if (txtLastName.equalsIgnoreCase("")) {
                    setMessage("Please fill Last Name");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (houseNo == true) {
                if (txtHouseNo.equalsIgnoreCase("")) {
                    setMessage("Please fill House No");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (sector == true) {
                if (txtSector.equalsIgnoreCase("")) {
                    setMessage("Please fill Sector");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddCity = "S";
                txtCity = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (city == true) {
                if (txtCity.equalsIgnoreCase("")) {
                    setMessage("Please fill Sector");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (enterBy == true && !ddEnterBy.equalsIgnoreCase("0")) {
                if (ddEnterBy.equalsIgnoreCase("0")) {
                    setMessage("Please select Enter By");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
                txtFatherName = null;
            } else if (fatherName == true) {
                if (txtFatherName.equalsIgnoreCase("")) {
                    setMessage("Please fill Father Name");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
            }
            List<ShareEnquiryPojo> shareEnquiryReport = beanRemote.shareEnquiryReport(folioFrom, folioTo, ddFirstName, txtFirstname, ddLastName, txtLastName, ddHouseNo, txtHouseNo, ddSector, txtSector, ddCity, txtCity, ddEnterBy, txtFatherName);
            if (!shareEnquiryReport.isEmpty()) {
                String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                String repName = "Ho Enquiry";
                Map fillParams = new HashMap();
                fillParams.put("pReportDate", ymdFormat.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", repName);
                fillParams.put("pBankName", s[0]);
                fillParams.put("pBankAddress", s[1]);
                new ReportBean().jasperReport("ShareEnquiryReport", "text/html", new JRBeanCollectionDataSource(shareEnquiryReport), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }
    
    public String pdfDownLoad(){
          setMessage("");
        try {
            if (folioNoAs == true && ddFolio.equalsIgnoreCase("0")) {
                if (folioFrom.equalsIgnoreCase("")) {
                    setMessage("Please fill from folio No");
                    return null;
                }
                //if (folioFrom.length() < 12) {
                if (!this.folioFrom.equalsIgnoreCase("") && ((this.folioFrom.length() != 12) && (this.folioFrom.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    setMessage("Please fill proper folio No");
                    return null;
                }

                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (folioNoAs == true && ddFolio.equalsIgnoreCase("1")) {
                if (folioFrom.equalsIgnoreCase("")) {
                    setMessage("Please fill from folio No");
                    return null;
                }
                //if (folioFrom.length() < 12) {
                if (!this.folioFrom.equalsIgnoreCase("") && ((this.folioFrom.length() != 12) && (this.folioFrom.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    setMessage("Please fill proper from folio No");
                    return null;
                }
                if (folioTo.equalsIgnoreCase("")) {
                    setMessage("Please fill To folio No");
                    return null;
                }
                //if (folioTo.length() < 12) {
                if (!this.folioTo.equalsIgnoreCase("") && ((this.folioTo.length() != 12) && (this.folioTo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    setMessage("Please fill proper To folio No");
                    return null;
                }
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (firstName == true) {
                if (txtFirstname.equalsIgnoreCase("")) {
                    setMessage("Please fill First Name");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (lastName == true) {
                if (txtLastName.equalsIgnoreCase("")) {
                    setMessage("Please fill Last Name");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (houseNo == true) {
                if (txtHouseNo.equalsIgnoreCase("")) {
                    setMessage("Please fill House No");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (sector == true) {
                if (txtSector.equalsIgnoreCase("")) {
                    setMessage("Please fill Sector");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddCity = "S";
                txtCity = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (city == true) {
                if (txtCity.equalsIgnoreCase("")) {
                    setMessage("Please fill Sector");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddEnterBy = null;
                txtFatherName = null;
            } else if (enterBy == true && !ddEnterBy.equalsIgnoreCase("0")) {
                if (ddEnterBy.equalsIgnoreCase("0")) {
                    setMessage("Please select Enter By");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
                txtFatherName = null;
            } else if (fatherName == true) {
                if (txtFatherName.equalsIgnoreCase("")) {
                    setMessage("Please fill Father Name");
                    return null;
                }
                folioFrom = null;
                folioTo = null;
                ddFirstName = "S";
                txtFirstname = null;
                ddLastName = "S";
                txtLastName = null;
                ddHouseNo = "S";
                txtHouseNo = null;
                ddSector = "S";
                txtSector = null;
                ddCity = "S";
                txtCity = null;
            }
            List<ShareEnquiryPojo> shareEnquiryReport = beanRemote.shareEnquiryReport(folioFrom, folioTo, ddFirstName, txtFirstname, ddLastName, txtLastName, ddHouseNo, txtHouseNo, ddSector, txtSector, ddCity, txtCity, ddEnterBy, txtFatherName);
            if (!shareEnquiryReport.isEmpty()) {
                String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                String repName = "Ho Enquiry";
                Map fillParams = new HashMap();
                fillParams.put("pReportDate", ymdFormat.format(new Date()));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", repName);
                fillParams.put("pBankName", s[0]);
                fillParams.put("pBankAddress", s[1]);
                new ReportBean().openPdf("Ho Enquiry", "ShareEnquiryReport", new JRBeanCollectionDataSource(shareEnquiryReport), fillParams);
                  ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                  HttpSession httpSession = getHttpSession();
                  httpSession.setAttribute("ReportName", repName);
               // return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void getAccountypeList() {
        try {
            List levelId = beanRemote.listEnterBy();
            enterByList = new ArrayList<SelectItem>();
            if (!levelId.isEmpty()) {
                for (int i = 0; i < levelId.size(); i++) {
                    Vector ele = (Vector) levelId.get(i);
                    enterByList.add(new SelectItem("0", "--Select--"));
                    enterByList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
                }
            } else {
                enterByList.add(new SelectItem("0", "--Select--"));
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void folioNoValueChange() {
        folioNoAs = true;
        shareHolderName = false;
        firstName = false;
        lastName = false;
        addressWise = false;
        houseNo = false;
        sector = false;
        city = false;
        enterBy = false;
        fatherName = false;
    }

    public void ddFolioNoValueChange() {
        ddFolioDisable = false;
        folioToDisable = false;
    }

    public void shareHolderNameValueChange() {
        shareHolderName = true;
        folioNoAs = false;
        firstName = false;
        lastName = false;
        addressWise = false;
        houseNo = false;
        sector = false;
        city = false;
        enterBy = false;
        fatherName = false;
    }

    public void firstNameValueChange() {
        firstName = true;
        shareHolderName = false;
        folioNoAs = false;
        lastName = false;
        addressWise = false;
        houseNo = false;
        sector = false;
        city = false;
        enterBy = false;
        fatherName = false;
        ddFirstNameDisable = false;
        firstNameDisable = false;
    }

    public void lastNameValueChange() {
        lastName = true;
        firstName = false;
        shareHolderName = false;
        folioNoAs = false;
        addressWise = false;
        houseNo = false;
        sector = false;
        city = false;
        enterBy = false;
        fatherName = false;
        lastNameDisable = false;
        ddLastNameDisable = false;
    }

    public void addressWiseValueChange() {
        addressWise = true;
        lastName = false;
        firstName = false;
        shareHolderName = false;
        folioNoAs = false;
        houseNo = false;
        sector = false;
        city = false;
        enterBy = false;
        fatherName = false;
    }

    public void houseNoValueChange() {
        houseNo = true;
        addressWise = false;
        lastName = false;
        firstName = false;
        shareHolderName = false;
        folioNoAs = false;
        sector = false;
        city = false;
        enterBy = false;
        fatherName = false;
        txtHouseNoDisable = false;
    }

    public void sectorValueChange() {
        sector = true;
        houseNo = false;
        addressWise = false;
        lastName = false;
        firstName = false;
        shareHolderName = false;
        folioNoAs = false;
        city = false;
        enterBy = false;
        fatherName = false;
        txtSectorDisable = false;
    }

    public void cityValueChange() {
        city = true;
        sector = false;
        houseNo = false;
        addressWise = false;
        lastName = false;
        firstName = false;
        shareHolderName = false;
        folioNoAs = false;
        enterBy = false;
        fatherName = false;
        txtCityDisable = false;
    }

    public void enterByValueChange() {
        enterBy = true;
        city = false;
        sector = false;
        houseNo = false;
        addressWise = false;
        lastName = false;
        firstName = false;
        shareHolderName = false;
        folioNoAs = false;
        fatherName = false;
        txtEnterByDisable = false;
    }

    public void fatherNameValueChange() {
        fatherName = true;
        enterBy = false;
        city = false;
        sector = false;
        houseNo = false;
        addressWise = false;
        lastName = false;
        firstName = false;
        shareHolderName = false;
        folioNoAs = false;
        txtFatherNameDisable = false;
    }

    public void refreshAction() {
        try {
            setMessage("");
            ddFolioDisable = true;
            ddFolioDisable = true;
            folioToDisable = true;
            firstNameDisable = true;
            lastNameDisable = true;
            ddFirstNameDisable = true;
            ddLastNameDisable = true;
            houseNoDisable = true;
            sectorDisable = true;
            cityDisable = true;
            txtHouseNoDisable = true;
            txtSectorDisable = true;
            txtCityDisable = true;
            txtEnterByDisable = true;
            txtFatherNameDisable = true;
            folioNoAs = false;
            shareHolderName = false;
            addressWise = false;
            fatherName = false;
            enterBy = false;
            setFolioFrom("");
            setFolioFromShow("");
            setFolioTo("");
            setFolioToShow("");
            setDdFirstName("S");
            setTxtFirstname("");
            setDdLastName("S");
            setTxtLastName("");
            setDdHouseNo("S");
            setTxtHouseNo("");
            setDdSector("S");
            setTxtSector("");
            setDdCity("S");
            setTxtCity("");
            setDdEnterBy("0");
            setTxtFatherName("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }

    public void folioFromMethod() {
        setMessage("");
        if (folioFromShow == null || folioFromShow.equalsIgnoreCase("")) {
            setMessage("Please fill Folio From ");
            return;
        }
        //if (folioFromShow.length() < 12) {
        if (!this.folioFromShow.equalsIgnoreCase("") && ((this.folioFromShow.length() != 12) && (this.folioFromShow.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            this.setMessage("Please enter proper Folio From");
            return;
        }
        try {
            folioFrom = ftsPostRemote.getNewAccountNumber(folioFromShow);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void folioToMethod() {
        setMessage("");
        if (folioToShow == null || folioToShow.equalsIgnoreCase("")) {
            setMessage("Please fill Folio To");
            return;
        }
        //if (folioToShow.length() < 12) {
        if (!this.folioToShow.equalsIgnoreCase("") && ((this.folioToShow.length() != 12) && (this.folioToShow.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            this.setMessage("Please enter proper Folio To");
            return;
        }
        try {
            folioTo = ftsPostRemote.getNewAccountNumber(folioToShow);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
}
