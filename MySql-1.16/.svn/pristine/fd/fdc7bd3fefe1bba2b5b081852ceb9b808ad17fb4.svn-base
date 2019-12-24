/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.dto.other.NpciOacDto;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class SyncController extends BaseBean {

    private String message;
    private UploadedFile uploadedFile;
    private List<NpciOacDto> tableList;
    private List<NpciOacDto> activeTableList;
    private OtherNpciMgmtFacadeRemote otherNpciRemote = null;

    public SyncController() {
        try {
            this.setMessage("Please select your file...");
            otherNpciRemote = (OtherNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherNpciMgmtFacade");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void uploadProcess() {
        try {
            this.setMessage("");
            if (uploadedFile == null) {
                this.setMessage("Please select appropriate file to upload");
            } else {
                String uploadedFileName = uploadedFile.getName();    //With extension
                if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
                    this.setMessage("Please select appropriate file to upload");
                    return;
                }
                String fileContentType = uploadedFile.getContentType();
                System.out.println("The Content Tpye of the uploded file is >>>>>>>>>>>>>> " + fileContentType);
                if (!(fileContentType.equals("text/plain")
                        || fileContentType.equals("application/octet-stream")
                        || fileContentType.equals("text/xml"))) {
                    this.setMessage("It is not a proper file type.");
                    return;
                }
                ServletContext context = (ServletContext) getFacesContext().getCurrentInstance().
                        getExternalContext().getContext();
//                String directory = context.getInitParameter("cts");
                String directory = context.getInitParameter("cbsFiles");
                File dir = new File(directory + "/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File inwFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);
                //Writing the file
                byte[] b = uploadedFile.getBytes();
                FileOutputStream fos = new FileOutputStream(inwFile);
                fos.write(b);
                fos.close();
                //Parsing the file
//                List<String> aadharNoList = parseFile(inwFile);

                List<List<String>> aadharNoList = parseFile(inwFile);
                List<String> inActiveList = aadharNoList.get(0);
                List<String> activeList = aadharNoList.get(1);

//                tableList = otherNpciRemote.getRegisteredAadharAtCbs(aadharNoList);

                List<List<NpciOacDto>> dataList = otherNpciRemote.getRegisteredAadharAtCbs(activeList, inActiveList);
                if (dataList.get(0).isEmpty() && dataList.get(1).isEmpty()) {
                    this.setMessage("There is no data.");
                    return;
                }
                tableList = dataList.get(0);
                activeTableList = dataList.get(1);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void post() {
        try {
            if (tableList.isEmpty()) {
                this.setMessage("There is no data to mark inactive.");
                return;
            }
            String msg = otherNpciRemote.markAadharInactive(tableList, getUserName());
            if (msg.equalsIgnoreCase("true")) {
                this.setMessage("Making has been successful.");
                tableList = new ArrayList<NpciOacDto>();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setTodayDate(getTodayDate());
        this.setMessage("Please select your file...");
        tableList = new ArrayList<NpciOacDto>();
        activeTableList = new ArrayList<NpciOacDto>();
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

//    private List<String> parseFile(File file) {
//        List<String> list = new ArrayList<String>();
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            DataInputStream dis = new DataInputStream(fis);
//            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                String[] arr = line.split("\\|");
//                if (arr[1].equalsIgnoreCase("1")) {  //1 for inactive aadhar at NPCI.
//                    list.add(arr[0]);
//                }
//            }
//        } catch (Exception ex) {
//            this.setMessage(ex.getMessage());
//        }
//        return list;
//    }
    private List<List<String>> parseFile(File file) {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> inActiveList = new ArrayList<String>();
        List<String> activeList = new ArrayList<String>();
        try {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split("\\|");
                if (arr[1].equalsIgnoreCase("1")) {  //1 for inactive aadhar at NPCI.
                    inActiveList.add(arr[0]);
                } else if (arr[1].equalsIgnoreCase("0")) { //0 for active aadhar at NPCI.
                    activeList.add(arr[0]);
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        list.add(inActiveList);
        list.add(activeList);
        return list;
    }

    //Getter And Setter
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NpciOacDto> getTableList() {
        return tableList;
    }

    public void setTableList(List<NpciOacDto> tableList) {
        this.tableList = tableList;
    }

    public List<NpciOacDto> getActiveTableList() {
        return activeTableList;
    }

    public void setActiveTableList(List<NpciOacDto> activeTableList) {
        this.activeTableList = activeTableList;
    }
}
