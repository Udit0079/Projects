/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.dds;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.master.MultiGLTo;
import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class UploadDDS extends BaseBean {

    private UploadedFile uploadedFile;
    private String message;
    Date dt = new Date();
    private final String jndiNameDds = "DDSManagementFacade";
    private DDSManagementFacadeRemote ddsRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private final String jndiNameNeftRtgs = "UploadNeftRtgsMgmtFacade";
    private UploadNeftRtgsMgmtFacadeRemote remote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd.MM.yy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public UploadDDS() {
        try {
            this.setMessage("Click on browse button to upload the dds file...");
            ddsRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiNameDds);
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            remote = (UploadNeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiNameNeftRtgs);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void uploadProcess() {
        this.setMessage("");
        if (uploadedFile == null) {
            this.setMessage("Please select appropriate file to upload");
            return;
        }
        try {
            String uploadedFileName = uploadedFile.getName();
            if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
                this.setMessage("Please select appropriate file to upload");
                return;
            }
            List<MbSmsSenderBankDetail> bankList = remote.getBankCode();
            if (bankList.isEmpty()) {
                this.setMessage("Please define bank code.");
                return;
            }
            ServletContext context = (ServletContext) getFacesContext().getCurrentInstance().getExternalContext().getContext();
//            String directory = context.getInitParameter("cts");
            String directory = context.getInitParameter("cbsFiles");
            File dir = new File(directory + "/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File pcrxFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);
            String fileContentType = uploadedFile.getContentType();
            System.out.println("File content type " + fileContentType);
            if (!(fileContentType.equals("text/plain")
                    || fileContentType.equals("application/octet-stream")
                    || fileContentType.equals("application/x-ns-proxy-autoconfig"))) {
                this.setMessage("Please select appropriate file to upload");
                return;
            }
            MbSmsSenderBankDetail bankEntity = bankList.get(0);
            String actualFileName = uploadedFileName.trim().substring(0, uploadedFileName.indexOf("."));
            int actualFileLength = actualFileName.length();

            String accode = ddsRemote.getAccountCode(CbsConstant.DEPOSIT_SC);
            if (accode == null || accode.equals("")) {
                this.setMessage("Agent Code is not valid for your branch.");
                return;
            }
            String tokenPaid = "";
            Boolean[] arr = ftsRemote.ddsCashReceivedFlag(getOrgnBrCode());
            tokenPaid = (arr[0] == true) ? "N" : "Y";

            String result = "";
            if (bankEntity.getBankCode().equalsIgnoreCase("eucb")
                    || bankEntity.getBankCode().equalsIgnoreCase("sric")
                    || bankEntity.getBankCode().equalsIgnoreCase("nabu")
                    || bankEntity.getBankCode().equalsIgnoreCase("hisr")) {
                //Jamshedpur-Balaji
                if (actualFileLength != 4) {
                    this.setMessage("Please select appropriate file to upload");
                    return;
                }
                List<String> dataList = getPcrxData(pcrxFile, bankEntity.getBankCode().trim());
                if (dataList.isEmpty()) {
                    this.setMessage("There is no data to process.");
                    return;
                }
                String agentCode = "";
                for (int i = 0; i < dataList.size(); i++) {
                    String[] row = dataList.get(i).split(",");
                    if (i == 0) {
                        Integer intAgCode = Integer.parseInt(row[3].trim().substring(3));
                        if (intAgCode.toString().length() == 2) {
                            agentCode = intAgCode.toString();
                        } else if (intAgCode.toString().length() == 1) {
                            agentCode = "0" + intAgCode.toString();
                        } else {
                            this.setMessage("Agent Code is not valid.");
                            return;
                        }
                        String agentName = ddsRemote.getAgentName(agentCode, getOrgnBrCode());
                        if (agentName == null || agentName.equals("")) {
                            this.setMessage("Agent Code is not valid for your branch.");
                            return;
                        }
                    } else {
                        String acno = ParseFileUtil.addTrailingZeros(row[0].trim(), 6);
                        acno = getOrgnBrCode() + accode + acno + agentCode;
                        double amount = Double.parseDouble(row[1].trim());
                        if (amount > 0) {
                            result = ddsRemote.pushPcrxData(acno, ymd.format(new Date()), amount, String.valueOf(i), getUserName(), ftsRemote.getRecNo(), tokenPaid);
                        }

                    }
                }
            } else if (bankEntity.getBankCode().equalsIgnoreCase("ukcb")
                    || bankEntity.getBankCode().equalsIgnoreCase("haqb")
                    || bankEntity.getBankCode().equalsIgnoreCase("sicb")
                    || bankEntity.getBankCode().equalsIgnoreCase("pucb")
                    || bankEntity.getBankCode().equalsIgnoreCase("ucbb")
                    || bankEntity.getBankCode().equalsIgnoreCase("ucbn")) { //For utti and hope. Old Type of pune machine. KCCBL(dhanbad) has been removed from here
                //Pune-PratiNidhi-Old
                if (actualFileLength != 8) {
                    this.setMessage("Please select appropriate file to upload");
                    return;
                }
                List<String> dataList = getPcrxData(pcrxFile, bankEntity.getBankCode().trim());
                if (dataList.isEmpty()) {
                    this.setMessage("There is no data to process.");
                    return;
                }
                String agentCode = "";
                for (int i = 0; i < dataList.size(); i++) {
                    String[] row = dataList.get(i).split(",");
                    if (i == 0) {
                        Integer intAgCode = Integer.parseInt(row[1].trim());
                        if (intAgCode.toString().length() == 2) {
                            agentCode = intAgCode.toString();
                        } else if (intAgCode.toString().length() == 1) {
                            agentCode = "0" + intAgCode.toString();
                        } else {
                            this.setMessage("Agent Code is not valid.");
                            return;
                        }
                        String agentName = ddsRemote.getAgentName(agentCode, getOrgnBrCode());
                        if (agentName == null || agentName.equals("")) {
                            this.setMessage("Agent Code is not valid for your branch.");
                            return;
                        }
                    } else {
                        String acno = ParseFileUtil.addTrailingZeros(row[0].trim(), 6);
                        acno = getOrgnBrCode() + accode + acno + agentCode;
                        double amount = Double.parseDouble(row[1].trim()) + Double.parseDouble(row[2].trim()) + Double.parseDouble(row[3].trim())
                                + Double.parseDouble(row[4].trim()) + Double.parseDouble(row[5].trim()) + Double.parseDouble(row[6].trim())
                                + Double.parseDouble(row[7].trim());
                        if (amount > 0) {
                            result = ddsRemote.pushPcrxData(acno, ymd.format(new Date()), amount, String.valueOf(i), getUserName(), ftsRemote.getRecNo(), tokenPaid);
                        }
                    }
                }
            } else if (bankEntity.getBankCode().equalsIgnoreCase("nauc")) { //For new agra.
                //Pune-Multi GL PratiNidhi
                List<MultiGLTo> multiList = new ArrayList<MultiGLTo>();
                if (actualFileLength != 8) {
                    this.setMessage("Please select appropriate file to upload");
                    return;
                }
                List<String> dataList = getMultiGlData(pcrxFile);
                if (dataList.isEmpty()) {
                    this.setMessage("There is no data to process.");
                    return;
                }
                String fileAgentCode = "", fileBrCode = "";
                Map<String, String> glMap = new HashMap<String, String>();
                for (int i = 0; i < dataList.size(); i++) {
                    String[] row = dataList.get(i).split(",");
                    if (i == 0) {
                        //Header.
                        fileBrCode = row[0];
                        if (!fileBrCode.equalsIgnoreCase(getOrgnBrCode())) {
                            this.setMessage("This file is of other branch. So it can not be upload.");
                            return;
                        }
                        fileAgentCode = row[1].substring(1);
                    } else if (i == 1 || i == 2) {
                        //Creating The GL Map.
                        if (!row[1].trim().equals("")) {
                            glMap.put(row[0].substring(1), row[1].trim());
                        }
                        if (!row[4].trim().equals("")) {
                            glMap.put(row[3].substring(1), row[4].trim());
                        }
                        if (!row[7].trim().equals("")) {
                            glMap.put(row[6].substring(1), row[7].trim());
                        }
                        if (!row[10].trim().equals("")) {
                            glMap.put(row[9].substring(1), row[10].trim());
                        }
                        if (!row[13].trim().equals("")) {
                            glMap.put(row[12].substring(1), row[13].trim());
                        }
                    } else {
                        //DataList
                        String acno = fileBrCode + glMap.get(row[0].substring(0, 1)) + row[0].substring(2) + fileAgentCode;
                        BigDecimal collectedAmt = new BigDecimal(row[1]).add(new BigDecimal(row[2]).
                                add(new BigDecimal(row[3]).add(new BigDecimal(row[4]).add(new BigDecimal(row[5])))));

                        if (collectedAmt.compareTo(new BigDecimal(0)) == 1) {
                            MultiGLTo to = new MultiGLTo();
                            to.setAcno(acno);
                            to.setBalance(collectedAmt.toString());
                            multiList.add(to);
                        }
                    }
                }
                //Now Data Processing.
                if (multiList.isEmpty()) {
                    this.setMessage("There is no collection. So it can not be upload.");
                    return;
                }
                result = ddsRemote.pushMultiGLData(multiList, ymd.format(new Date()), getUserName(), tokenPaid, getOrgnBrCode());
            } else if (bankEntity.getBankCode().equalsIgnoreCase("kcbl")) {
                //Pune-Prati Nidhi New Machine
                if (actualFileLength != 15) {
                    this.setMessage("Please select appropriate file to upload");
                    return;
                }
                String[] fileNameParts = actualFileName.split("_");
                if (Integer.parseInt(getOrgnBrCode()) != Integer.parseInt(fileNameParts[0])) {
                    this.setMessage("Please select file of your branch only.");
                    return;
                }
                String agentCode = String.valueOf(Integer.parseInt(fileNameParts[1]));
                if (agentCode.trim().length() != 2) {
                    agentCode = "0" + agentCode;
                }
                String agentName = ddsRemote.getAgentName(agentCode, getOrgnBrCode());
                if (agentName.equals("")) {
                    this.setMessage("Agent does not exist.");
                    return;
                }
                List<String> dataList = getPcrxData(pcrxFile, bankEntity.getBankCode().trim());
                if (dataList.isEmpty()) {
                    this.setMessage("There is no data to process.");
                    return;
                }
                for (int i = 0; i < dataList.size(); i++) {
                    String[] row = dataList.get(i).split(",");
                    if (i != 0) {
                        String acno = ParseFileUtil.addTrailingZeros(row[0].trim(), 6);
                        acno = getOrgnBrCode() + accode + acno + agentCode;
                        double amount = Double.parseDouble(row[1].trim()) + Double.parseDouble(row[2].trim()) + Double.parseDouble(row[3].trim())
                                + Double.parseDouble(row[4].trim()) + Double.parseDouble(row[5].trim());
                        if (amount > 0) {
                            result = ddsRemote.pushPcrxData(acno, ymd.format(new Date()), amount, String.valueOf(i), getUserName(), ftsRemote.getRecNo(), tokenPaid);
                        }
                    }
                }
            }
            if (!result.equalsIgnoreCase("true")) {
                this.setMessage(result);
                return;
            }
            this.setMessage("File has been uploaded successfully.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    /**
     *
     * @param pcrxFile
     * @return
     * @throws ApplicationException
     */
    public List<String> getPcrxData(File pcrxFile, String bankcode) throws ApplicationException {
        List<String> dataList = new ArrayList<String>();
        try {
            FileInputStream fis = null;
            DataInputStream dis = null;
            BufferedReader br = null;
            try {
                byte[] b = uploadedFile.getBytes();
                FileOutputStream fos = new FileOutputStream(pcrxFile);
                fos.write(b);
                fos.close();

                fis = new FileInputStream(pcrxFile);
                dis = new DataInputStream(fis);
                br = new BufferedReader(new InputStreamReader(dis));

                String line = null;
                if (bankcode.equalsIgnoreCase("eucb")
                        || bankcode.equalsIgnoreCase("sric")
                        || bankcode.equalsIgnoreCase("ucbn")
                        || bankcode.equalsIgnoreCase("nabu")) { //Jamshedpur
                    while ((line = br.readLine()) != null) {
                        if (line.length() == 55) {
                            dataList.add(line);
                        }
                    }
                } else if (bankcode.equalsIgnoreCase("kcbl")) { //Prati Nidhi Pune New Type Of Machine
                    while ((line = br.readLine()) != null) {
                        dataList.add(line);
                    }
                } else { //Prati Nidhi Pune Old Type Of Machine
                    while ((line = br.readLine()) != null) {
                        if (line.length() == 89) {
                            dataList.add(line);
                        }
                    }
                }
            } catch (FileNotFoundException fnex) {
                System.out.println("fnex :" + fnex.getMessage());
            } catch (IOException ioex) {
                System.out.println("ioex :" + ioex.getMessage());
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
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<String> getMultiGlData(File textFile) throws ApplicationException {
        List<String> dataList = new ArrayList<String>();
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            byte[] b = uploadedFile.getBytes();
            FileOutputStream fos = new FileOutputStream(textFile);
            fos.write(b);
            fos.close();

            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));

            String line = null;
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public void refreshForm() {
        this.setMessage("Click on browse button to upload the dds file...");
    }

    public String exitForm() {
        return "case1";
    }
}
