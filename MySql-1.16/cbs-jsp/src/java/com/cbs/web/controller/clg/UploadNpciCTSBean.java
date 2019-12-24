/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.clg;

import com.cbs.dto.npci.cts.FileHeader;
import com.cbs.dto.npci.cts.FileHeader.Item;
import com.cbs.dto.npci.cts.FileHeader.Item.ImageViewDetail;
import com.cbs.dto.npci.cts.FileHeaderDTO;
import com.cbs.dto.npci.cts.FileSummaryDTO;
import com.cbs.dto.npci.cts.ItemDTO;
import com.cbs.dto.npci.cts.ItemImageDTO;
import com.cbs.facade.clg.NpciClearingMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.hrms.web.utils.WebUtil;
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
import java.util.List;
import javax.faces.context.FacesContext;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class UploadNpciCTSBean extends BaseBean {

    private String errorMessage;
    private UploadedFile uploadedXmlFile;
    private UploadedFile uploadedImgFile;
    private NpciClearingMgmtFacadeRemote remote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public UploadNpciCTSBean() {
        this.setErrorMessage("Please select files to upload");
        try {
            remote = (NpciClearingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciClearingMgmtFacade");
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void uploadProcess() {
        System.out.println("Start Time-->" + new Date());
        this.setErrorMessage("");
        if (uploadedXmlFile == null || uploadedImgFile == null) {
            this.setErrorMessage("Please select appropriate files to upload");
            return;
        }
        try {
            String xmlFileName = uploadedXmlFile.getName();
            String imgFileName = uploadedImgFile.getName();
            if (xmlFileName == null || imgFileName == null
                    || xmlFileName.equals("") || imgFileName.equals("")) {
                this.setErrorMessage("Please select appropriate files to upload");
                return;
            }
            if (!xmlFileName.substring(xmlFileName.indexOf(".") + 1).equalsIgnoreCase("xml")) {
                this.setErrorMessage("In 1st field there should be XML Data File to upload");
                return;
            }
            if (!imgFileName.substring(imgFileName.indexOf(".") + 1).equalsIgnoreCase("img")) {
                this.setErrorMessage("In 2nd field there should be IMG Data File to upload");
                return;
            }
            if (!xmlFileName.split("\\_")[2].equalsIgnoreCase(imgFileName.split("\\_")[2])) {
                this.setErrorMessage("Either you can upload CTS or Non-CTS files of same type");
                return;
            }
            if (!xmlFileName.substring(0, xmlFileName.indexOf(".")).split("\\_")[6].equalsIgnoreCase(imgFileName.substring(0, imgFileName.indexOf(".")).split("\\_")[6])) {
                this.setErrorMessage("Please upload files of same clearing type of same session no");
                return;
            }
            //Is these files are already uploaded.
            boolean result = remote.isFileUploaded(xmlFileName.substring(xmlFileName.indexOf(".") + 1), xmlFileName);
            if (result == true) {
                this.setErrorMessage("This Pxf Xml Data file Is already uploaded");
                return;
            }
            result = remote.isFileUploaded(imgFileName.substring(imgFileName.indexOf(".") + 1), imgFileName);
            if (result == true) {
                this.setErrorMessage("This image file Is already uploaded");
                return;
            }
            //Writing both files(data and img) in file system
            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            File imageDirectory = new File(context.getInitParameter("cts") + "/");
            if (!imageDirectory.exists()) {
                imageDirectory.mkdirs();
            }

            File xmlFile = new File(imageDirectory.getCanonicalPath() + File.separatorChar + xmlFileName);
            byte[] b = uploadedXmlFile.getBytes();
            FileOutputStream fos = new FileOutputStream(xmlFile);
            fos.write(b);
            fos.close();

            File imageFile = new File(imageDirectory.getCanonicalPath() + File.separatorChar + imgFileName);
            b = uploadedImgFile.getBytes();
            fos = new FileOutputStream(imageFile);
            fos.write(b);
            fos.close();
            //Parsing PXF XML File
            FileHeaderDTO pxfDTO = parsePxfXmlFile(xmlFile);
            //Reading Binary Image File
            byte[] imgFileContent = readImageFileContent(imageFile);
            //Writing images from image file content.
            String clearingType = xmlFileName.split("\\_")[2].length() < 2 ? "0" + xmlFileName.split("\\_")[2] : xmlFileName.split("\\_")[2];
            Integer cbsScheduleNo = remote.getCbsGeneratedScheduleNo(ymd.format(new Date()), getOrgnBrCode(), clearingType);

            String binaryImgLocation = xmlFileName.split("\\_")[2] + "_" + cbsScheduleNo.toString() + "_" + getOrgnBrCode() + File.separatorChar;
            binaryImgLocation = imageDirectory.getCanonicalPath() + File.separatorChar + binaryImgLocation;
            if (!new File(binaryImgLocation).exists()) {
                new File(binaryImgLocation).mkdirs();
            }
            deleteOldImageFiles(binaryImgLocation);

            write(binaryImgLocation, imgFileContent, pxfDTO.getItems());


            //Converting TIF/TIFF images into PNG and JPG respectively
            convertTiffImages(binaryImgLocation);
            //Uploading of data related to clearing
            String dbResult = remote.uploadClearingData(pxfDTO, xmlFileName, imgFileName, cbsScheduleNo,
                    WebUtil.getClientIP(this.getHttpRequest()), getUserName(), getTodayDate(), getOrgnBrCode());
            if (!dbResult.equalsIgnoreCase("true")) {
                this.setErrorMessage(dbResult);
                return;
            }
            this.setErrorMessage("Files have been uploaded successfully.");
            System.out.println("Start Time-->" + new Date());
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setErrorMessage(ex.getMessage());
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
        List<Item> xmlItems = fileHeader.getItem();
        for (int i = 0; i < xmlItems.size(); i++) {
            Item item = xmlItems.get(i);
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
            List<ImageViewDetail> imageViewDetail = item.getImageViewDetail();

            for (int j = 0; j < imageViewDetail.size(); j++) {
                ImageViewDetail images = imageViewDetail.get(j);
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
                itemImageDTO.setImageReferenceData(images.getImageViewData().getImageReferenceData() == null
                        ? "" : images.getImageViewData().getImageReferenceData());

                itemImages.add(itemImageDTO);
            }

            itemDTO.setItemImages(itemImages);
            items.add(itemDTO);
        }
        pxfDTO.setItems(items);

        return pxfDTO;
    }

    public byte[] readImageFileContent(File imageFile) throws Exception {
//        Path path = Paths.get("/root/Desktop/image/BPIBF_110305000_1_12052017_12052017_195327_19546_00.img");
        Path path = Paths.get(imageFile.getPath());
        return Files.readAllBytes(path);
    }

    public void write(String binaryImgLocation, byte[] binaryImgContent, List<ItemDTO> items) throws Exception {
        try {
            for (ItemDTO item : items) {
                List<ItemImageDTO> itemImages = item.getItemImages();
                for (ItemImageDTO imageDTO : itemImages) {
                    try {
                        OutputStream output;
                        output = new BufferedOutputStream(new FileOutputStream(new File(binaryImgLocation + imageDTO.getImageName())));
                        output.write(binaryImgContent, Integer.parseInt(imageDTO.getImageDataOffset()), Integer.parseInt(imageDTO.getImageDataLength()));
                        output.close();
                    } catch (Exception ex) {
                        System.out.println("Image Not Converted Properly");
                    }
                }
            }
        } finally {
            System.out.println("Images has been copied from binary file.");
        }
    }

//    public void write(String binaryImgLocation, byte[] binaryImgContent, List<ItemDTO> items) throws Exception {
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
//    }
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
            try {
                if (images[i].getName().endsWith("TIF") && images[i].length() != 0) {
                    System.out.println("Image Name In TIF Convert-->" + images[i].getName());
                    stream = new FileSeekableStream(binaryImgLocation + images[i].getName());
                    dec = ImageCodec.createImageDecoder("tiff", stream, null);
                    image = dec.decodeAsRenderedImage(0);
                    dotIndex = images[i].getName().indexOf(".");

                    actualFileName = images[i].getName().substring(0, dotIndex);
                    JAI.create("filestore", image, binaryImgLocation + actualFileName + ".PNG", "PNG");
                    parBrFile = new File(binaryImgLocation + images[i].getName());
                    parBrFile.delete();
                } else if (images[i].getName().endsWith("TIFF") && images[i].length() != 0) {
                    System.out.println("Image Name In TIFF Convert-->" + images[i].getName());
                    stream = new FileSeekableStream(binaryImgLocation + images[i].getName());
                    dec = ImageCodec.createImageDecoder("tiff", stream, null);
                    image = dec.decodeAsRenderedImage(0);
                    dotIndex = images[i].getName().indexOf(".");

                    actualFileName = images[i].getName().substring(0, dotIndex);
                    JAI.create("filestore", image, binaryImgLocation + actualFileName + ".JPG", "JPEG");
                    parBrFile = new File(binaryImgLocation + images[i].getName());
                    parBrFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*if (images[i].getName().endsWith("TIF") && images[i].length() != 0) {
             System.out.println("Image Name In TIF Convert-->" + images[i].getName());
             stream = new FileSeekableStream(binaryImgLocation + images[i].getName());
             dec = ImageCodec.createImageDecoder("tiff", stream, null);
             image = dec.decodeAsRenderedImage(0);
             dotIndex = images[i].getName().indexOf(".");

             actualFileName = images[i].getName().substring(0, dotIndex);
             JAI.create("filestore", image, binaryImgLocation + actualFileName + ".PNG", "PNG");
             parBrFile = new File(binaryImgLocation + images[i].getName());
             parBrFile.delete();
             } else if (images[i].getName().endsWith("TIFF") && images[i].length() != 0) {
             System.out.println("Image Name In TIFF Convert-->" + images[i].getName());
             stream = new FileSeekableStream(binaryImgLocation + images[i].getName());
             dec = ImageCodec.createImageDecoder("tiff", stream, null);
             image = dec.decodeAsRenderedImage(0);
             dotIndex = images[i].getName().indexOf(".");

             actualFileName = images[i].getName().substring(0, dotIndex);
             JAI.create("filestore", image, binaryImgLocation + actualFileName + ".JPG", "JPEG");
             parBrFile = new File(binaryImgLocation + images[i].getName());
             parBrFile.delete();
             }*/
        }
    }

    public void deleteOldImageFiles(String binaryImgLocation) throws Exception {
        File[] files = new File(binaryImgLocation).listFiles();
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public void refreshForm() {
        this.setErrorMessage("Please select files to upload");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    //Getter And Setter
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public UploadedFile getUploadedXmlFile() {
        return uploadedXmlFile;
    }

    public void setUploadedXmlFile(UploadedFile uploadedXmlFile) {
        this.uploadedXmlFile = uploadedXmlFile;
    }

    public UploadedFile getUploadedImgFile() {
        return uploadedImgFile;
    }

    public void setUploadedImgFile(UploadedFile uploadedImgFile) {
        this.uploadedImgFile = uploadedImgFile;
    }
}
