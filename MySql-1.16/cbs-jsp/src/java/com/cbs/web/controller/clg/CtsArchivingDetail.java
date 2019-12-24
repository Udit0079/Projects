/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.clg;

import com.cbs.dto.npci.cts.FileHeader;
import com.cbs.dto.npci.cts.FileHeaderDTO;
import com.cbs.dto.npci.cts.FileSummaryDTO;
import com.cbs.dto.npci.cts.ItemDTO;
import com.cbs.dto.npci.cts.ItemImageDTO;
import com.cbs.dto.npci.cts.reverse.ReturnDTO;
import com.cbs.facade.clg.NpciClearingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.TIFFEncodeParam;
import com.sun.media.jai.codec.TIFFField;
import com.sun.media.jai.codecimpl.TIFFImageEncoder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class CtsArchivingDetail extends BaseBean {

    private String firstdate;
    private String secondDate;
    private String displayoncheque;
    private String displayOnGenerate;
    private String displayimg;
    private String errMessage;
    private String frdt;
    private String todt;
    private List<SelectItem> fileTypeList;
    private String chequeNo;
    private String scheduleNo;
    private String imgFileName;
    private String imgDoneFileName;
    private String branchCode;
    private String clgType;
    private String imageData;
    private String folderName;
    private String focusField;
    private ReturnDTO CurrentItem;
    private String fileType;
    private List<ReturnDTO> gridDetail;
    private HashSet imgSet;
    private final static String BINARY_IMAGE_LOCATION = "BinaryImage";
    private NpciClearingMgmtFacadeRemote beanRemote;
    private CommonReportMethodsRemote commonRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public CtsArchivingDetail() {
        try {
            beanRemote = (NpciClearingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciClearingMgmtFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            getListValues();
            gridDetail = new ArrayList<>();
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
        }
    }

    public void getListValues() {
        try {
            fileTypeList = new ArrayList<SelectItem>();
            fileTypeList.add(new SelectItem("S", "--Select--"));
            fileTypeList.add(new SelectItem("CH", "CHEQUE NO"));
            fileTypeList.add(new SelectItem("DT", "DATE"));

            firstdate = "none";
            secondDate = "none";
            displayoncheque = "none";
            displayOnGenerate = "none";
            displayimg = "none";
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void gridload() {
        try {
            if (fileType.equalsIgnoreCase("CH")) {
                if (chequeNo == null || chequeNo.trim().equals("")) {
                    this.setErrMessage("Please fill the cheque no. which detail you want.");
                    return;
                }
                Pattern p = Pattern.compile("[0-9]*");
                Matcher mt = p.matcher(chequeNo);
                if (!mt.matches()) {
                    setErrMessage("Please fill the numeric value of cheque no.");
                    return;
                }
                gridDetail = new ArrayList<>();
                gridDetail = beanRemote.getCtsArchivingData(this.frdt, this.todt, this.chequeNo, this.fileType);
                if (gridDetail.isEmpty()) {
                    this.setErrMessage("There is no data found");
                } else {
                    this.setErrMessage("Now you can generate the image.");
                }
            }

            if (fileType.equalsIgnoreCase("DT")) {
                if (frdt == null || frdt.trim().equals("")) {
                    this.setErrMessage("Please fill the from date.");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(frdt)) {
                    this.setErrMessage("Please fill proper from date.");
                    return;
                }
                if (dmy.parse(frdt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setErrMessage("From date can not be greater than current date.");
                    return;
                }
                if (todt == null || todt.trim().equals("")) {
                    this.setErrMessage("Please fill the to date .");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(todt)) {
                    this.setErrMessage("Please fill proper to date.");
                    return;
                }
                if (dmy.parse(todt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setErrMessage("To date can not be greater than current date.");
                    return;
                }
                if (dmy.parse(frdt).compareTo(dmy.parse(todt)) > 0) {
                    this.setErrMessage("From date can not be greater than to date.");
                    return;
                }

                gridDetail = new ArrayList<>();
                gridDetail = beanRemote.getCtsArchivingData(this.frdt, this.todt, this.chequeNo, this.fileType);
                if (gridDetail.isEmpty()) {
                    this.setErrMessage("There is no list between these Date.");
                } else {
                    this.setErrMessage("Now you can generate the image.");
                }
            }
            imgSet = new HashSet();
            for (int i = 0; i < gridDetail.size(); i++) {
                imgSet.add(gridDetail.get(i).getBinaryImgFileName().toString());
            }
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
        }
    }

    public void onblurFileType() {
        try {
            if (fileType.equalsIgnoreCase("CH")) {
                this.firstdate = "none";
                this.secondDate = "none";
                this.displayOnGenerate = "";
                this.displayoncheque = "";
                this.displayimg = "";
                this.focusField = "txtChequeNo";
            }
            if (fileType.equalsIgnoreCase("DT")) {
                this.firstdate = "";
                this.secondDate = "";
                this.displayoncheque = "none";
                this.displayOnGenerate = "";
                this.displayimg = "";
                this.focusField = "frDate";
            }
            if (fileType.equalsIgnoreCase("S")) {
                this.firstdate = "none";
                this.secondDate = "none";
                this.displayOnGenerate = "none";
                this.displayoncheque = "none";
                this.displayimg = "none";
            }
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
        }
    }

    public void processAction() {
        try {
            if (imgSet.isEmpty()) {
                this.setErrMessage("There is no image file.");
                return;
            }
            //Writing both files(data and img) in file system
            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            File ctsFileLocation = new File(context.getInitParameter("cts") + "/");
            if (!ctsFileLocation.exists()) {
                ctsFileLocation.mkdirs();
            }

            String binaryImgFolderName = ctsFileLocation + "/" + BINARY_IMAGE_LOCATION + "/";
            File binaryImageLocation = new File(binaryImgFolderName);
            if (!binaryImageLocation.exists()) {
                binaryImageLocation.mkdirs();
            }
            Iterator it = imgSet.iterator();
            while (it.hasNext()) {
                String imgFile = (String) it.next();
                System.out.println("Image File Name-->" + imgFile);
                String xmlFile = "BPXF_" + imgFile.substring(imgFile.indexOf("_") + 1, imgFile.lastIndexOf("_")) + ".XML";
                System.out.println("Xml File Name-->" + xmlFile);

                File xmlFileDirectory = new File(ctsFileLocation + "/" + xmlFile);
                File imgFileDirectory = new File(ctsFileLocation + "/" + imgFile);
                if (xmlFileDirectory.exists() && imgFileDirectory.exists()) {
                    //Parsing PXF XML File
                    FileHeaderDTO pxfDTO = parsePxfXmlFile(xmlFileDirectory);
                    //Reading Binary Image File
                    byte[] imgFileContent = readImageFileContent(imgFileDirectory);
                    write(binaryImgFolderName, imgFileContent, pxfDTO.getItems());
                    //Converting TIF/TIFF images into PNG and JPG respectively
                    convertTiffImages(binaryImgFolderName);
                }
            }
            this.setErrMessage("Images has been generated.");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }

    }

    public void setRowValues() {
        try {
            if (CurrentItem == null) {
                this.setErrMessage("Please select the row from the table.");
                return;
            }
//            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//            File ctsFileLocation = new File(context.getInitParameter("cts") + "/");
            String binaryImgFolderName = "/" + BINARY_IMAGE_LOCATION + "/";
            this.setFolderName(binaryImgFolderName);
            this.setImageData(CurrentItem.getItemSeqNo() + ".JPEG");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public static FileHeaderDTO parsePxfXmlFile(File pxfXmlFile) throws Exception {
        FileHeaderDTO pxfDTO = new FileHeaderDTO();

        JAXBContext jaxbContext = JAXBContext.newInstance(FileHeader.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        FileHeader fileHeader = (FileHeader) jaxbUnmarshaller.unmarshal(pxfXmlFile);

        pxfDTO.setCreationDate(fileHeader.getCreationDate());
        pxfDTO.setFileId(fileHeader.getFileID());
        pxfDTO.setSessionNo(fileHeader.getSessionNumber());
        pxfDTO.setSessionDate(fileHeader.getSessionDate());
        pxfDTO.setSettlementDate(fileHeader.getSettlementDate());
        pxfDTO.setVersionNo(fileHeader.getVersionNumber());
        pxfDTO.setTestFileIndicator(fileHeader.getTestFileIndicator());

        FileSummaryDTO fileSummaryDTO = new FileSummaryDTO();
        fileSummaryDTO.setTotalItemCount(fileHeader.getFileSummary().getTotalItemCount());
        fileSummaryDTO.setTotalAmount(fileHeader.getFileSummary().getTotalAmount());

        pxfDTO.setFileSummary(fileSummaryDTO);

        List<ItemDTO> items = new ArrayList<>();
        List<FileHeader.Item> xmlItems = fileHeader.getItem();
        for (int i = 0; i < xmlItems.size(); i++) {
            FileHeader.Item item = xmlItems.get(i);
            ItemDTO itemDTO = new ItemDTO();

            itemDTO.setPresentingBankRoutNo(item.getPresentingBankRoutNo());
            itemDTO.setPayorBankRoutNo(item.getPayorBankRoutNo());
            itemDTO.setAmount(Double.parseDouble(item.getAmount()) / 100);
            itemDTO.setSerialNo(item.getSerialNo());
            itemDTO.setItemSeqNo(item.getItemSeqNo());
            itemDTO.setTransCode(item.getTransCode());
            itemDTO.setAccountNo(item.getAccountNo());
            itemDTO.setUserField(item.getUserField() == null ? "" : item.getUserField());
            itemDTO.setPresentmentDate(item.getPresentmentDate());
            itemDTO.setClearingType(item.getClearingType());
            itemDTO.setCycleNo(item.getCycleNo());
            itemDTO.setDocType(item.getDocType());
            itemDTO.setAddendABofdRoutNo(item.getAddendA().getBOFDRoutNo());
            itemDTO.setAddendABofdBusDate(item.getAddendA().getBOFDBusDate());
            itemDTO.setAddendADepositorAcct(item.getAddendA().getDepositorAcct());
            itemDTO.setAddendAIfsc(item.getAddendA().getIFSC());

            List<ItemImageDTO> itemImages = new ArrayList<>();
            List<FileHeader.Item.ImageViewDetail> imageViewDetail = item.getImageViewDetail();

            for (int j = 0; j < imageViewDetail.size(); j++) {
                FileHeader.Item.ImageViewDetail images = imageViewDetail.get(j);
                ItemImageDTO itemImageDTO = new ItemImageDTO();

                itemImageDTO.setViewFormat(images.getViewFormat());
                itemImageDTO.setViewSideIndicator(images.getViewSideIndicator());

                if (itemImageDTO.getViewFormat().equalsIgnoreCase("TIFF")
                        && itemImageDTO.getViewSideIndicator().equalsIgnoreCase("Front BW")) {
                    itemImageDTO.setImageName(item.getItemSeqNo().toString() + ".TIF");
                } else if (itemImageDTO.getViewFormat().equalsIgnoreCase("TIFF")
                        && itemImageDTO.getViewSideIndicator().equalsIgnoreCase("Back BW")) {
                    itemImageDTO.setImageName(item.getItemSeqNo().toString() + ".TIFF");
                } else if (itemImageDTO.getViewFormat().equalsIgnoreCase("JFIF")
                        && itemImageDTO.getViewSideIndicator().equalsIgnoreCase("Front Gray")) {
                    itemImageDTO.setImageName(item.getItemSeqNo().toString() + ".JPEG");
                }

                itemImageDTO.setImageDataLength(images.getImageViewData().getImageDataLength().toString());
                itemImageDTO.setImageDataOffset(images.getImageViewData().getImageDataOffset().toString());
                itemImageDTO.setBinaryFileRefName(images.getImageViewData().getFileName());

                itemImages.add(itemImageDTO);
            }

            itemDTO.setItemImages(itemImages);
            items.add(itemDTO);
        }
        pxfDTO.setItems(items);

        return pxfDTO;
    }

    public byte[] readImageFileContent(File imageFile) throws Exception {
        Path path = Paths.get(imageFile.getPath());
        return Files.readAllBytes(path);
    }

    public void write(String binaryImgLocation, byte[] binaryImgContent, List<ItemDTO> items) throws Exception {
//        OutputStream output = null;
//        try {
//            for (ItemDTO item : items) {
//                List<ItemImageDTO> itemImages = item.getItemImages();
//                for (ItemImageDTO imageDTO : itemImages) {
//                    if (Integer.parseInt(imageDTO.getImageDataLength()) > 8192) {
//                        output = new BufferedOutputStream(new FileOutputStream(new File(binaryImgLocation + imageDTO.getImageName())));
//                        output.write(binaryImgContent, Integer.parseInt(imageDTO.getImageDataOffset()), Integer.parseInt(imageDTO.getImageDataLength()));
//                    } else {
//                        createAnnotatedTif(new String[]{""}, new FileOutputStream(new File(binaryImgLocation + imageDTO.getImageName())));
//                    }
//                }
//            }
//        } finally {
//            if (output != null) {
//                output.close();
//            }
//        }

        try {
            for (ItemDTO item : items) {
                List<ItemImageDTO> itemImages = item.getItemImages();
                for (ItemImageDTO imageDTO : itemImages) {
                    OutputStream output;
                    output = new BufferedOutputStream(new FileOutputStream(new File(binaryImgLocation + imageDTO.getImageName())));
                    output.write(binaryImgContent, Integer.parseInt(imageDTO.getImageDataOffset()), Integer.parseInt(imageDTO.getImageDataLength()));
                    output.close();
                }
            }
        } finally {
            System.out.println("Images has been copied from binary file.");
        }
    }

    public void createAnnotatedTif(String[] args, OutputStream out) throws Exception {
        byte[] byteArray = new byte[]{-1, 0};
        ColorModel colorModel = new IndexColorModel(1, 2, byteArray, byteArray, byteArray);

        WritableRaster writeableRaster = Raster.createPackedRaster(DataBuffer.TYPE_BYTE, 780, 360, 1, 1, null);
        BufferedImage bufImg = new BufferedImage(colorModel, writeableRaster, false, null);
        // -------------------------------------------------------------------        
        Graphics2D g2d = bufImg.createGraphics();
        g2d.setColor(Color.black);

        Font font = new Font("Arial Bold", Font.PLAIN, 36);
        g2d.setFont(font);
        int vertPos = 0;
        for (int i = 0; i < args.length; i++) {
            g2d.drawString(args[i], 75, vertPos);
            vertPos += 48;
        }

        PlanarImage planarImage = PlanarImage.wrapRenderedImage(bufImg);

        TIFFEncodeParam encodeParam = new TIFFEncodeParam();
        encodeParam.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);
        encodeParam.setWriteTiled(false);  // false means use strips.
        encodeParam.setTileSize(0, 0);  // tiling will make the file size much larger. 
        encodeParam.setLittleEndian(true);  // create an Intel (II) format image

        String SoftwareVersion[] = new String[]{"TIFF by Joe, " + this.getClass().getName()};
        String docName[] = new String[]{"JoesTifAnnotator"};

        // Create a new TIFF fields including a new TIFF ASCII TIFF tag.
        TIFFField tiffFields[] = new TIFFField[5];

        tiffFields[0] = new TIFFField(269, TIFFField.TIFF_ASCII, docName.length, docName);
        tiffFields[1] = new TIFFField(282, TIFFField.TIFF_RATIONAL, 1, new long[][]{{200, 1}});
        tiffFields[2] = new TIFFField(283, TIFFField.TIFF_RATIONAL, 1, new long[][]{{200, 1}});
        // resolution unit 
        tiffFields[3] = new TIFFField(296, TIFFField.TIFF_SHORT, 1, new char[]{2});
        tiffFields[4] = new TIFFField(305, TIFFField.TIFF_ASCII, SoftwareVersion.length, SoftwareVersion);

        encodeParam.setExtraFields(tiffFields);

        TIFFImageEncoder encoder = new TIFFImageEncoder(out, encodeParam);
        encoder.encode(planarImage);
    }

    public void convertTiffImages(String binaryImgLocation) throws Exception {
        FileSeekableStream stream;
        ImageDecoder dec;
        RenderedImage image;
        Integer dotIndex;
        String actualFileName;
        File parBrFile;

        File imageDir = new File(binaryImgLocation);
        File[] images = imageDir.listFiles();
        for (int i = 0; i < images.length; i++) {
            if (images[i].getName().endsWith("TIF") && images[i].length() != 0) {
                stream = new FileSeekableStream(binaryImgLocation + images[i].getName());
                dec = ImageCodec.createImageDecoder("tiff", stream, null);
                image = dec.decodeAsRenderedImage(0);
                dotIndex = images[i].getName().indexOf(".");

                actualFileName = images[i].getName().substring(0, dotIndex);
                JAI.create("filestore", image, binaryImgLocation + actualFileName + ".PNG", "PNG");
                parBrFile = new File(binaryImgLocation + images[i].getName());
                parBrFile.delete();
            } else if (images[i].getName().endsWith("TIFF") && images[i].length() != 0) {
                stream = new FileSeekableStream(binaryImgLocation + images[i].getName());
                dec = ImageCodec.createImageDecoder("tiff", stream, null);
                image = dec.decodeAsRenderedImage(0);
                dotIndex = images[i].getName().indexOf(".");

                actualFileName = images[i].getName().substring(0, dotIndex);
                JAI.create("filestore", image, binaryImgLocation + actualFileName + ".JPG", "JPEG");
                parBrFile = new File(binaryImgLocation + images[i].getName());
                parBrFile.delete();
            }
        }
    }

    public void getWhiteImage() {
        try {
//            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//            File ctsFileLocation = new File(context.getInitParameter("cts") + "/");
            String binaryImgFolderName = "/" + BINARY_IMAGE_LOCATION + "/";
            this.setFolderName(binaryImgFolderName);
            this.setImageData(CurrentItem.getItemSeqNo() + ".PNG");

        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }

    }

    public void getGrayImage() {
        try {
//            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//            File ctsFileLocation = new File(context.getInitParameter("cts") + "/");
            String binaryImgFolderName = "/" + BINARY_IMAGE_LOCATION + "/";
            this.setFolderName(binaryImgFolderName);
            this.setImageData(CurrentItem.getItemSeqNo() + ".JPEG");

        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void getBackImage() {
        try {
//            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//            File ctsFileLocation = new File(context.getInitParameter("cts") + "/");
            String binaryImgFolderName = "/" + BINARY_IMAGE_LOCATION + "/";
            this.setFolderName(binaryImgFolderName);
            this.setImageData(CurrentItem.getItemSeqNo() + ".JPG");

        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        try {
            this.errMessage = "";
            this.fileType = "S";
            this.firstdate = "none";
            this.secondDate = "none";
            this.displayoncheque = "none";
            this.displayOnGenerate = "";
            this.displayimg = "none";
            this.frdt = "";
            this.todt = "";
            this.chequeNo = "";
            gridDetail = new ArrayList<ReturnDTO>();
            this.CurrentItem = null;
            this.setImageData(null);
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";

    }

    //Getter And Setter
    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public ReturnDTO getCurrentItem() {
        return CurrentItem;
    }

    public void setCurrentItem(ReturnDTO CurrentItem) {
        this.CurrentItem = CurrentItem;
    }

    public String getFrdt() {
        return frdt;
    }

    public void setFrdt(String frdt) {
        this.frdt = frdt;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<SelectItem> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<SelectItem> fileTypeList) {
        this.fileTypeList = fileTypeList;
    }

    public String getTodt() {
        return todt;
    }

    public void setTodt(String todt) {
        this.todt = todt;
    }

    public List<ReturnDTO> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<ReturnDTO> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getDisplayOnGenerate() {
        return displayOnGenerate;
    }

    public void setDisplayOnGenerate(String displayOnGenerate) {
        this.displayOnGenerate = displayOnGenerate;
    }

    public String getFirstdate() {
        return firstdate;
    }

    public void setFirstdate(String firstdate) {
        this.firstdate = firstdate;
    }

    public String getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(String secondDate) {
        this.secondDate = secondDate;
    }

    public String getDisplayoncheque() {
        return displayoncheque;
    }

    public void setDisplayoncheque(String displayoncheque) {
        this.displayoncheque = displayoncheque;
    }

    public String getDisplayimg() {
        return displayimg;
    }

    public void setDisplayimg(String displayimg) {
        this.displayimg = displayimg;
    }

    public String getImgDoneFileName() {
        return imgDoneFileName;
    }

    public void setImgDoneFileName(String imgDoneFileName) {
        this.imgDoneFileName = imgDoneFileName;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getClgType() {
        return clgType;
    }

    public void setClgType(String clgType) {
        this.clgType = clgType;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public HashSet getImgSet() {
        return imgSet;
    }

    public void setImgSet(HashSet imgSet) {
        this.imgSet = imgSet;
    }

    public String getFocusField() {
        return focusField;
    }

    public void setFocusField(String focusField) {
        this.focusField = focusField;
    }
}
