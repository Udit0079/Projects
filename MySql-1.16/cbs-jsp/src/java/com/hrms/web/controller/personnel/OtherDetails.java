package com.hrms.web.controller.personnel;

import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrPersonnelHobbyPKTO;
import com.hrms.common.to.HrPersonnelHobbyTO;
import com.hrms.common.to.HrPersonnelLangPKTO;
import com.hrms.common.to.HrPersonnelLangTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.HobbyGrid;
import com.hrms.web.pojo.LanguageGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class OtherDetails {

    private static final Logger logger = Logger.getLogger(MembershipDetails.class);
    private int compCode,
            defaultComp,
            currentILanguageRow,
            currentIHobbyRow;
    private long empCode;
    private String messageHobby,
            messageLanguage,
            message,
            hobbyMode,
            languageMode,
            statFlag,
            statUpFlag,
            language,
            hobby,
            authBy,
            enteredBy;
    private List<String> languageOptions;
    private PersonnelDelegate personnelDelegate;
    private PersonnelUtil personnelUtil;
    private boolean showHobbyGrid,
            showLanguageGrid,
            disableAddButton,
            disableSaveButton,
            disableEditButton,
            disableDeleteButton,
            disableLanguage,
            disableLanguageOptions,
            disableHobbies;
    private Character langRead, langSpeak, langWrite;
    List<LanguageGrid> languageTable;
    LanguageGrid currentLanguageItem = new LanguageGrid();
    List<HobbyGrid> hobbyTable;
    HobbyGrid currentHobbyItem = new HobbyGrid();

    public OtherDetails() {
        try {
            statFlag = "Y";
            statUpFlag = "Y";
            personnelDelegate = new PersonnelDelegate();
            personnelUtil = new PersonnelUtil();
            String user = (String) getRequest().getSession().getAttribute("USER_NAME");
            authBy = user;
            enteredBy = user;
            onLoadMode();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method OtherDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method OtherDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveOtherDetails() {
        try {
            String languageResult = saveLanguage();
            String hobbyResult = saveHobby();
            cancelOtherDetails();
            setMessageHobby(hobbyResult);
            setMessageLanguage(languageResult);
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveOtherDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String saveLanguage() {
        String result = "";
        try {
            if (!language.equalsIgnoreCase("")) {
                if (!languageOptions.isEmpty()) {
                    for (String value : languageOptions) {
                        if (value.equalsIgnoreCase("R")) {
                            langRead = 'T';
                            break;
                        } else {
                            langRead = 'F';
                        }
                    }
                    for (String value : languageOptions) {
                        if (value.equalsIgnoreCase("W")) {
                            langWrite = 'T';
                            break;
                        } else {
                            langWrite = 'F';
                        }
                    }
                    for (String value : languageOptions) {
                        if (value.equalsIgnoreCase("S")) {
                            langSpeak = 'T';
                            break;
                        } else {
                            langSpeak = 'F';
                        }
                    }
                    if (languageMode.equalsIgnoreCase("save")) {
                        result = personnelDelegate.saveHrPersonnelLang(getHrPersonnelLangTO(language), "save");
                    } else if (languageMode.equalsIgnoreCase("update")) {
                        String oldLang = currentLanguageItem.getLanguage();
                        personnelDelegate.saveHrPersonnelLang(getHrPersonnelLangTO(oldLang), "delete");
                        personnelDelegate.saveHrPersonnelLang(getHrPersonnelLangTO(language), "save");
                        result = "Language Is Successfully Updated !!";
                    }
                } else {
                    return "Language Cannot Be Saved Because Language Options Are Not Selected !!";
                }
            } else {
                return "";
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveLanguage()", e);
            result = "Exception occured while saving language. You may be trying to save duplicate language!!";
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveLanguage()", e);
            result = "Exception occured while saving language. You may be trying to save duplicate language!!";
        }
        return result;
    }

    public String saveHobby() {
        String result = "";
        try {
            if (!hobby.equalsIgnoreCase("")) {
                if (hobbyMode.equalsIgnoreCase("save")) {
                    result = personnelDelegate.saveHrPersonnelHobby(getHrPersonnelHobbyTO(hobby), "save");
                } else if (hobbyMode.equalsIgnoreCase("update")) {
                    String oldHobby = currentHobbyItem.getHobby();
                    personnelDelegate.saveHrPersonnelHobby(getHrPersonnelHobbyTO(oldHobby), "delete");
                    personnelDelegate.saveHrPersonnelHobby(getHrPersonnelHobbyTO(hobby), "save");
                    result = "Hobby Is Successfully Updated !!";
                }
            } else {
                return "";
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveHobby()", e);
            result = "Exception occured while saving hobby. You may be trying to save duplicate hobby!!";
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveHobby()", e);
            result = "Exception occured while saving hobby. You may be trying to save duplicate hobby!!";
        }
        return result;
    }

    public void deleteOtherDetails() {
        empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
        compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
        defaultComp = (Integer) getRequest().getSession().getAttribute("DEFAULT_COMP");
        String result1 = "", result2 = "";
        //deleting language
        try {
            if (!language.equalsIgnoreCase("")) {
                HrPersonnelLangTO hrPersonnelLangTO = new HrPersonnelLangTO();
                HrPersonnelLangPKTO hrPersonnelLangPKTO = new HrPersonnelLangPKTO();
                hrPersonnelLangPKTO.setCompCode(compCode);
                hrPersonnelLangPKTO.setEmpCode(empCode);
                hrPersonnelLangPKTO.setLang(language);
                hrPersonnelLangTO.setHrPersonnelLangPKTO(hrPersonnelLangPKTO);
                result1 = personnelDelegate.saveHrPersonnelLang(hrPersonnelLangTO, "delete");
            } else {
                result1 = "";
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteOtherDetails()", e);
            result2 = "Exception occured while deleting language!!";
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteOtherDetails()", e);
            result2 = "Exception occured while deleting language!!";
        }

        //deleting hobby
        try {
            if (!hobby.equalsIgnoreCase("")) {
                HrPersonnelHobbyTO hrPersonnelHobbyTO = new HrPersonnelHobbyTO();
                HrPersonnelHobbyPKTO hrPersonnelHobbyPKTO = new HrPersonnelHobbyPKTO();
                hrPersonnelHobbyPKTO.setCompCode(compCode);
                hrPersonnelHobbyPKTO.setEmpCode(empCode);
                hrPersonnelHobbyPKTO.setHobbyName(hobby);
                hrPersonnelHobbyTO.setHrPersonnelHobbyPKTO(hrPersonnelHobbyPKTO);
                result2 = personnelDelegate.saveHrPersonnelHobby(hrPersonnelHobbyTO, "delete");
            } else {
                result2 = "";
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteOtherDetails()", e);
            result2 = "Exception occured while deleting hobby!!";
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteOtherDetails()", e);
            result2 = "Exception occured while deleting hobby!!";
        }
        cancelOtherDetails();
        onLoadMode();
        setMessageLanguage(result1);
        setMessageHobby(result2);
    }

    public void editOtherDetails() {
        try {
            hobby = "";
            language = "";
            languageOptions = new ArrayList<String>();
            setMessage("");
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
            defaultComp = (Integer) getRequest().getSession().getAttribute("DEFAULT_COMP");
            if (empCode != 0) {
                boolean hobbyB = getHobbyData();
                boolean langB = getLanguageData();
                if (hobbyB || langB) {
                    setDisableDeleteButton(true);
                    setDisableSaveButton(false);
                } else {
                    setDisableDeleteButton(true);
                    setDisableSaveButton(false);
                }
            } else {
                this.setMessage("Please Select An Employee From General Details Page");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method editOtherDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelOtherDetails() {
        try {
            setHobby("");
            setLanguage("");
            setLanguageOptions(null);
            onLoadMode();
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelOtherDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String exitOtherDetails() {
        try {
            cancelOtherDetails();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitOtherDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    public boolean getLanguageData() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            languageTable = personnelUtil.getLanguageData(empCode);
            if (!languageTable.isEmpty()) {
                setShowLanguageGrid(true);
                setMessageLanguage(languageTable.size() + " languages found !!");
                return true;
            } else {
                setShowLanguageGrid(false);
                setMessageLanguage("No languages found !!");
                return false;
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getLanguageData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLanguageData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return false;
    }

    public void setLanguageRowValues() {
        try {
            setMessageLanguage("");
            setLanguageMode("update");
            setDisableDeleteButton(false);
            setLanguage(currentLanguageItem.getLanguage());
            languageOptions = new ArrayList<String>();
            if (currentLanguageItem.getLangRead() == 'T') {
                languageOptions.add("R");
            }
            if (currentLanguageItem.getLangWrite() == 'T') {
                languageOptions.add("W");
            }
            if (currentLanguageItem.getLangSpeak() == 'T') {
                languageOptions.add("S");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method setLanguageRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public boolean getHobbyData() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            hobbyTable = personnelUtil.getHobbyData(empCode);
            if (!hobbyTable.isEmpty()) {
                setShowHobbyGrid(true);
                setMessageHobby(hobbyTable.size() + " hobbies found !!");
                return true;
            } else {
                setShowHobbyGrid(false);
                setMessageHobby("No Hobbies Found!!");
                return false;
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getHobbyData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHobbyData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return false;
    }

    public void setHobbyRowValues() {
        try {
             setDisableDeleteButton(false);
            setMessageHobby("");
            setHobbyMode("update");
            setHobby(currentHobbyItem.getHobby());
        } catch (Exception e) {
            logger.error("Exception occured while executing method setHobbyRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void onLoadMode() {
        setMessage("");
        setMessageHobby("");
        setMessageLanguage("");
        setHobbyMode("save");
        setLanguageMode("save");
        setDisableSaveButton(false);
        setDisableDeleteButton(true);
    }

  
    public HrPersonnelLangTO getHrPersonnelLangTO(String language) {
        HrPersonnelLangTO hrPersonnelLangTO = new HrPersonnelLangTO();
        HrPersonnelLangPKTO hrPersonnelLangPKTO = new HrPersonnelLangPKTO();
        try {
            hrPersonnelLangPKTO.setCompCode(compCode);
            hrPersonnelLangPKTO.setEmpCode(empCode);
            hrPersonnelLangPKTO.setLang(language);
            hrPersonnelLangTO.setAuthBy(authBy);
            hrPersonnelLangTO.setDefaultComp(defaultComp);
            hrPersonnelLangTO.setEnteredBy(enteredBy);
            hrPersonnelLangTO.setHrPersonnelLangPKTO(hrPersonnelLangPKTO);
            hrPersonnelLangTO.setHrPersonnelDetailsTO(personnelUtil.getHrPersonnelDetailsTO(compCode, empCode));
            hrPersonnelLangTO.setLangRead(langRead);
            hrPersonnelLangTO.setLangSpeak(langSpeak);
            hrPersonnelLangTO.setLangWrite(langWrite);
            hrPersonnelLangTO.setModDate(Date.class.newInstance());
            hrPersonnelLangTO.setStatFlag(statFlag);
            hrPersonnelLangTO.setStatUpFlag(statUpFlag);

        } catch (Exception e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrPersonnelLangTO;
    }

    public HrPersonnelHobbyTO getHrPersonnelHobbyTO(String hobby) {
        HrPersonnelHobbyTO hrPersonnelHobbyTO = new HrPersonnelHobbyTO();
        HrPersonnelHobbyPKTO hrPersonnelHobbyPKTO = new HrPersonnelHobbyPKTO();
        try {
            hrPersonnelHobbyPKTO.setCompCode(compCode);
            hrPersonnelHobbyPKTO.setEmpCode(empCode);
            hrPersonnelHobbyPKTO.setHobbyName(hobby);
            hrPersonnelHobbyTO.setAuthBy(authBy);
            hrPersonnelHobbyTO.setDefaultComp(defaultComp);
            hrPersonnelHobbyTO.setEnteredBy(enteredBy);
            hrPersonnelHobbyTO.setHrPersonnelHobbyPKTO(hrPersonnelHobbyPKTO);
            hrPersonnelHobbyTO.setHrPersonnelDetailsTO(personnelUtil.getHrPersonnelDetailsTO(compCode, empCode));
            hrPersonnelHobbyTO.setModDate(Date.class.newInstance());
            hrPersonnelHobbyTO.setStatFlag(statFlag);
            hrPersonnelHobbyTO.setStatUpFlag(statUpFlag);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrPersonnelHobbyTO;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public boolean isDisableAddButton() {
        return disableAddButton;
    }

    public void setDisableAddButton(boolean disableAddButton) {
        this.disableAddButton = disableAddButton;
    }

    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }

    public boolean isDisableEditButton() {
        return disableEditButton;
    }

    public void setDisableEditButton(boolean disableEditButton) {
        this.disableEditButton = disableEditButton;
    }

    public boolean isDisableHobbies() {
        return disableHobbies;
    }

    public void setDisableHobbies(boolean disableHobbies) {
        this.disableHobbies = disableHobbies;
    }

    public boolean isDisableLanguage() {
        return disableLanguage;
    }

    public void setDisableLanguage(boolean disableLanguage) {
        this.disableLanguage = disableLanguage;
    }

    public boolean isDisableLanguageOptions() {
        return disableLanguageOptions;
    }

    public void setDisableLanguageOptions(boolean disableLanguageOptions) {
        this.disableLanguageOptions = disableLanguageOptions;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getLanguage() {
        return language;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Character getLangRead() {
        return langRead;
    }

    public void setLangRead(Character langRead) {
        this.langRead = langRead;
    }

    public Character getLangSpeak() {
        return langSpeak;
    }

    public void setLangSpeak(Character langSpeak) {
        this.langSpeak = langSpeak;
    }

    public Character getLangWrite() {
        return langWrite;
    }

    public void setLangWrite(Character langWrite) {
        this.langWrite = langWrite;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getLanguageOptions() {
        return languageOptions;
    }

    public void setLanguageOptions(List<String> languageOptions) {
        this.languageOptions = languageOptions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageHobby() {
        return messageHobby;
    }

    public void setMessageHobby(String messageHobby) {
        this.messageHobby = messageHobby;
    }

    public String getMessageLanguage() {
        return messageLanguage;
    }

    public void setMessageLanguage(String messageLanguage) {
        this.messageLanguage = messageLanguage;
    }

    public String getHobbyMode() {
        return hobbyMode;
    }

    public void setHobbyMode(String hobbyMode) {
        this.hobbyMode = hobbyMode;
    }

    public String getLanguageMode() {
        return languageMode;
    }

    public void setLanguageMode(String languageMode) {
        this.languageMode = languageMode;
    }

    public PersonnelDelegate getPersonnelDelegate() {
        return personnelDelegate;
    }

    public void setPersonnelDelegate(PersonnelDelegate personnelDelegate) {
        this.personnelDelegate = personnelDelegate;
    }

    public PersonnelUtil getPersonnelUtil() {
        return personnelUtil;
    }

    public void setPersonnelUtil(PersonnelUtil personnelUtil) {
        this.personnelUtil = personnelUtil;
    }

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

    public HobbyGrid getCurrentHobbyItem() {
        return currentHobbyItem;
    }

    public void setCurrentHobbyItem(HobbyGrid currentHobbyItem) {
        this.currentHobbyItem = currentHobbyItem;
    }

    public LanguageGrid getCurrentLanguageItem() {
        return currentLanguageItem;
    }

    public void setCurrentLanguageItem(LanguageGrid currentLanguageItem) {
        this.currentLanguageItem = currentLanguageItem;
    }

    public List<HobbyGrid> getHobbyTable() {
        return hobbyTable;
    }

    public void setHobbyTable(List<HobbyGrid> hobbyTable) {
        this.hobbyTable = hobbyTable;
    }

    public List<LanguageGrid> getLanguageTable() {
        return languageTable;
    }

    public void setLanguageTable(List<LanguageGrid> languageTable) {
        this.languageTable = languageTable;
    }

    public int getCurrentIHobbyRow() {
        return currentIHobbyRow;
    }

    public void setCurrentIHobbyRow(int currentIHobbyRow) {
        this.currentIHobbyRow = currentIHobbyRow;
    }

    public int getCurrentILanguageRow() {
        return currentILanguageRow;
    }

    public void setCurrentILanguageRow(int currentILanguageRow) {
        this.currentILanguageRow = currentILanguageRow;
    }

    public boolean isShowHobbyGrid() {
        return showHobbyGrid;
    }

    public void setShowHobbyGrid(boolean showHobbyGrid) {
        this.showHobbyGrid = showHobbyGrid;
    }

    public boolean isShowLanguageGrid() {
        return showLanguageGrid;
    }

    public void setShowLanguageGrid(boolean showLanguageGrid) {
        this.showLanguageGrid = showLanguageGrid;
    }
}
