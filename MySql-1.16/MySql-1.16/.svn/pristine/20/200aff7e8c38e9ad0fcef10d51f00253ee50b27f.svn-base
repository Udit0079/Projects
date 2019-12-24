/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.utils;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.exception.ApplicationException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author root
 */
public class CbsUtil {

    private static SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat inputFormat = new SimpleDateFormat("MMM");
    public static boolean newGlPattern;

    public static long dayDiff(Date fromDate, Date toDate) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(fromDate);
        cal2.setTime(toDate);
        long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        long div = 24 * 60 * 60 * 1000;
        long diffDays = (long) Math.round(diff / div);
        return diffDays;
    }

    public static int monthDiff(Date fromDt, Date toDt) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(fromDt);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(toDt);

        int month1 = cal1.get(Calendar.MONTH);
        int month2 = cal2.get(Calendar.MONTH);
        if (cal2.get(Calendar.YEAR) > cal1.get(Calendar.YEAR)) {
            int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
            month2 = month2 + yearDiff * 12;
        }
        int diff = month2 - month1;
        return diff;
    }

    public static int yearDiff(Date fromDt, Date toDt) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(fromDt);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(toDt);
        int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
        int monthDiff = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
        if (monthDiff < 0) {
            return --yearDiff;
        }
        return yearDiff;
    }

    public static double round(double d, int places) {
        return Math.round(d * Math.pow(10, (double) places)) / Math.pow(10, (double) places);
    }

    public static String dateAdd(String dt, int noOfDays) {
        try {
            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(dt);
            calendar.setTime(frDate);
            calendar.add(Calendar.DATE, noOfDays);
            dt = ymmd.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static String monthAdd(String dt, int noOfMonth) {
        try {
            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(dt);
            calendar.setTime(frDate);
            calendar.add(Calendar.MONTH, noOfMonth);
            dt = ymmd.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static String yearAdd(String dt, int noOfYear) {
        try {
            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(dt);
            calendar.setTime(frDate);
            calendar.add(Calendar.YEAR, noOfYear);
            dt = ymmd.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static int datePart(String option, String date) {
        int dtPart = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(date);
            calendar.setTime(frDate);
            if (option.equalsIgnoreCase("D")) {
                dtPart = calendar.get(Calendar.DATE);
            } else if (option.equalsIgnoreCase("M")) {
                dtPart = calendar.get(Calendar.MONTH) + 1;
            } else {
                dtPart = calendar.get(Calendar.YEAR);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dtPart;
    }

    public static Date getLastDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static int getEndDate(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getFirstDate(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        return cal.getActualMinimum(Calendar.DAY_OF_MONTH);
    }

    public static String getFirstDateOfGivenDate(Date date) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date dddd = calendar.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        return sdf1.format(dddd);
    }

    public static boolean isLastDay(String date) throws ApplicationException, ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ymmd.parse(date));
        return calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DATE);
    }

    public static String lPadding(int size, int number) {
        String format = "%0" + size + "d";
        return String.format(format, number);
    }

    /**
     *
     * @param month
     * @return
     */
    public static String getMonthName(int month) {
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
        return monthName[month - 1];
    }

    /**
     * Encodes the rawPass using a MessageDigest. If a salt is specified it will
     * be merged with the password before encoding.
     *
     * @param rawPass The plain text password
     * @param salt The salt to sprinkle
     * @return Hex string of password digest (or base64 encoded string if
     * encodeHashAsBase64 is enabled.
     */
    public static String encodePassword(String rawPass) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm [MD5]");
        }
        byte[] digest;

        try {
            digest = messageDigest.digest(rawPass.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 not supported!");
        }
        return new String(Hex.encode(digest));
    }

    /**
     * Takes a previously encoded password and compares it with a rawpassword
     * after mixing in the salt and encoding that value
     *
     * @param encPass previously encoded password
     * @param rawPass plain text password
     * @param salt salt to mix into password
     * @return true or false
     */
    public static boolean isPasswordValid(String encPass, String rawPass) {
        String pass1 = "" + encPass;
        String pass2 = encodePassword(rawPass);
        return equals(pass1, pass2);
    }

    public static boolean equals(String expected, String actual) {
        byte[] expectedBytes = bytesUtf8(expected);
        byte[] actualBytes = bytesUtf8(actual);
        int expectedLength = expectedBytes == null ? -1 : expectedBytes.length;
        int actualLength = actualBytes == null ? -1 : actualBytes.length;
        if (expectedLength != actualLength) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < expectedLength; i++) {
            result |= expectedBytes[i] ^ actualBytes[i];
        }
        return result == 0;
    }

    private static byte[] bytesUtf8(String s) {
        if (s == null) {
            return null;
        }
        try {
            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Could not get bytes in UTF-8 format", e);
        }
    }

    public static String getReconTableName(String acctnature) throws ApplicationException {
        try {
            if (acctnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                return "ca_recon";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctnature.equalsIgnoreCase(CbsConstant.SS_AC) || acctnature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                return "loan_recon";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                return "recon";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                return "td_recon";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                return "gl_recon";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                return "ddstransaction";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                return "rdrecon";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                return "of_recon";
            } else {
                return "recon";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public static String getAccMasterTableName(String acctnature) throws ApplicationException {
        try {
            if (acctnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                return "td_accountmaster";
            } else {
                return "accountmaster";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public static String getReconBalanTableName(String acctnature) throws ApplicationException {
        try {
            if (acctnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                return "ca_reconbalan";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctnature.equalsIgnoreCase(CbsConstant.SS_AC) || acctnature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                return "reconbalan";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                return "reconbalan";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                return "td_reconbalan";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                return "reconbalan";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                return "reconbalan";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                return "reconbalan";
            } else if (acctnature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                return "reconbalan";
            } else {
                return "reconbalan";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public static String toProperCase(String input) {
        Pattern p = Pattern.compile("\\b([\\p{javaLowerCase}])", Pattern.UNICODE_CASE);
        Matcher m = p.matcher(input);
        StringBuffer sb = new StringBuffer(input.length());
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * Find out 4 digits random number in java.
     */
    public static int fourDigitRandomNumber() {
        int pin = 0;
        try {
            Random random = new Random();
            long fraction = (long) (1000 * random.nextDouble());
            pin = (int) (fraction + 1000);
        } catch (Exception ex) {
            System.out.println("Problem in random number generation in java side Util.java file.");
        }
        return pin;
    }

    /**
     * Get Month Name By Passing Month Number in Int.
     */
    public static String getMonthForInt(int m) {
        String month = "invalid";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (m >= 0 && m <= 11) {
            month = months[m];
        }
        return month;
    }

    public static String getQuarterFirstAndLastDate(String month, String year, String option) throws ApplicationException {
        String requiredDate = "";
        try {
            int maxDt = getEndDate(Integer.parseInt(month) - 1, Integer.parseInt(year));
            if (month.length() == 1) {
                month = "0" + month;
            }

            String maxQuarterDt = String.valueOf(maxDt) + "/" + month + "/" + year;

            int fromMonth = Integer.parseInt(month) - 3;
            String dtFromMonth = String.valueOf(fromMonth + 1);

            int minDt = getFirstDate(fromMonth, Integer.parseInt(year));

            if (dtFromMonth.length() == 1) {
                dtFromMonth = "0" + dtFromMonth;
            }

            String minQuarterDt = String.valueOf(minDt) + "/" + dtFromMonth + "/" + year;

            if (option.equalsIgnoreCase("F")) {
                requiredDate = ymmd.format(dmy.parse(minQuarterDt));
            } else if (option.equalsIgnoreCase("L")) {
                requiredDate = ymmd.format(dmy.parse(maxQuarterDt));
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return requiredDate;
    }

    public static String getSignatureFilePath(String token) {
        return File.separatorChar + token.substring(0, 4) + File.separatorChar + token.substring(4, 6) + File.separatorChar
                + token.substring(6, 8);
    }

    public static String getSigFilePath(String acno, String bankCode) {
        return File.separatorChar + bankCode + File.separatorChar + acno.substring(0, 2) + File.separatorChar + acno.substring(2, 4) + File.separatorChar;
    }

    public static String encryptText(String str) {
        String strPwd = "stellar";
        String strBuff = "";
        int c;
        if (strPwd.length() > 0) {
            for (int i = 1; i <= str.length(); i++) {
                c = (int) str.charAt(i - 1);
                c = c + (int) strPwd.charAt(i % strPwd.length());
                while (c > 126) {
                    if (c > 500) {
                        c = 200;
                        int val = ((int) strPwd.charAt(i % strPwd.length()) - (int) str.charAt(i - 1)) - (i) * 2;
                        c = c - val;
                    }
                    c = c - Math.abs(((int) strPwd.charAt(i % strPwd.length()) - ((int) str.charAt(i - 1)))) - (i) * 2;
                    if (c < 33) {
                        c = c + i * 2;
                    }
                }
                if (c == 92 || c == 124 || c == 63 || c == 47 || c == 42 || c == 60 || c == 62 || c == 34 || c == 58 || c == 0) {
                    c = 33;
                }
                strBuff = strBuff + (char) c;
            }
        }
        return strBuff;
    }

    public static boolean generateSig(String acno, String img, String bankCode) throws Exception {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Signature");
            doc.appendChild(rootElement);

            Element imgEle = doc.createElement("Img");
            imgEle.appendChild(doc.createTextNode(img));
            rootElement.appendChild(imgEle);

            Attr attr = doc.createAttribute("acno");
            attr.setValue(acno);
            imgEle.setAttributeNode(attr);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            String filePath = getSigFilePath(acno, bankCode);
            File xmlDirs = new File(filePath);
            xmlDirs.mkdirs();
            File xmlFile = new File(filePath + encryptText(acno) + ".xml");
            xmlFile.createNewFile();

            StreamResult result = new StreamResult(xmlFile);

            transformer.transform(source, result);
            return true;
        } catch (ParserConfigurationException pce) {
            throw new Exception(pce.getMessage());
        } catch (TransformerException tfe) {
            throw new Exception(tfe.getMessage());
        }
    }

    public static String readImageFromXMLFile(File xmlFile) throws FileNotFoundException, Exception {
        String imageData = "";
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Img");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    imageData = eElement.getTextContent();
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return imageData;
    }

    public static List<String> getAllDateOfAMonth(int month, int year) throws Exception {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        List<String> monthList = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);

        month = month + 1;
        String tempMonth = String.valueOf(month).length() == 1 ? "0" + String.valueOf(month) : String.valueOf(month);
        String minDate = String.valueOf(year) + tempMonth + "01";
        String maxDate = String.valueOf(year) + tempMonth + String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        monthList.add(minDate);
        String tempDt = minDate;
        while (ymd.parse(tempDt).compareTo(ymd.parse(maxDate)) < 0) {
            Calendar minCal = Calendar.getInstance();
            minCal.setTime(ymd.parse(tempDt));
            minCal.add(Calendar.DATE, 1);
            tempDt = ymd.format(minCal.getTime());
            monthList.add(tempDt);
        }
        return monthList;
    }

    public static List<String> getAllDateOfAMonthFromDtToDt(String fromDt, String toDt) throws Exception {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        List<String> monthList = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        monthList.add(fromDt);
        String tempDt = fromDt;
        while (ymd.parse(tempDt).compareTo(ymd.parse(toDt)) < 0) {
            Calendar minCal = Calendar.getInstance();
            minCal.setTime(ymd.parse(tempDt));
            minCal.add(Calendar.DATE, 1);
            tempDt = ymd.format(minCal.getTime());
            monthList.add(tempDt);
        }
        return monthList;
    }

    public static Map<String, String> getIntOption(String code) throws Exception {
        try {
            Map<String, String> intOptMap = new HashMap<String, String>();
            for (int i = 0; i < code.length(); i++) {
                String ch = String.valueOf(code.charAt(i));
                if (ch.equals("C")) {
                    intOptMap.put("C", "Cumulative");
                } else if (ch.equals("Q")) {
                    intOptMap.put("Q", "Quaterly");
                } else if (ch.equals("M")) {
                    intOptMap.put("M", "Monthly");
                } else if (ch.equals("S")) {
                    intOptMap.put("S", "Simple");
                } else if (ch.equals("Y")) {
                    intOptMap.put("Y", "Yearly");
                }
            }
            return intOptMap;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static String monthLast(String dt, int noOfMonth) {
        try {
            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(dt);
            calendar.setTime(frDate);
            calendar.add(Calendar.MONTH, noOfMonth);
            Date fdt = calendar.getTime();
            calendar.setTime(fdt);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(calendar.DAY_OF_MONTH));
            dt = ymmd.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static String getMonthnoFromMonthName(String date) {
        try {
            String monthName = date.substring(3, 6);
            Date date1 = new SimpleDateFormat("MMM").parse(monthName.toUpperCase());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            String monthNumber = String.valueOf(cal.get(Calendar.MONTH) + 1);
            String monthNo = (Integer.parseInt(monthNumber) < 10 ? "0" : "") + monthNumber;
            date = date.substring(0, 2) + "-" + monthNo + "-" + date.substring(7, 11);

        } catch (Exception ex) {
            ex.printStackTrace();;
        }
        return date;
    }

    public static List getYrMonDayDiff(String fromDt, String toDt) {
        List arr = new ArrayList();
        try {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(ymmd.parse(toDt));

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(ymmd.parse(fromDt));

            int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
            int monthDiff = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
            int dayDiff = cal2.get(Calendar.DATE) - cal1.get(Calendar.DATE);

            Calendar tmpCal = Calendar.getInstance();
            tmpCal.setTime(ymmd.parse(toDt));
            if (dayDiff < 0) {
                tmpCal.set(Calendar.MONTH, cal2.get(Calendar.MONTH) - 1);
                dayDiff = tmpCal.getActualMaximum(Calendar.DATE) + dayDiff;
                if (monthDiff <= 0) {
                    monthDiff = 11 + monthDiff;
                    yearDiff = --yearDiff;
                } else {
                    monthDiff = --monthDiff;
                }
            } else {
                if (monthDiff < 0) {
                    monthDiff = 11 + monthDiff;
                    yearDiff = --yearDiff;
                }
            }
            arr.add(yearDiff);
            arr.add(monthDiff);
            arr.add(dayDiff);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static String getBranchWiseQuery(String brCode, String dt) throws ApplicationException {
        try {
            String query = "";
            if (brCode.equals("0A")) {
                query = "select substring(acno,3,8) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from gl_recon where dt <= '" + dt + "' and auth ='Y' and substring(acno,3,8) <> '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "' group by substring(acno,3,8) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from recon where dt <='" + dt + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from loan_recon where dt <='" + dt + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from of_recon where dt <='" + dt + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from rdrecon where dt <='" + dt + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from ddstransaction where dt <='" + dt + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno, cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from td_recon where dt <='" + dt + "'  and auth ='Y' and closeflag is null group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2), cast(sum(cramt- dramt) as decimal(25,2)) from ca_recon where acno in "
                        + "(select acno  from ca_recon where dt <= '" + dt + "' group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1) "
                        + "and dt <= '" + dt + "'   and auth ='Y' group by substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0) "
                        + "union all "
                        + "select substring(acno,3,2), cast(sum(cramt- dramt) as decimal(25,2)) from ca_recon where acno in "
                        + "(select acno  from ca_recon where dt <= '" + dt + "' group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) "
                        + "and dt <= '" + dt + "'  and auth ='Y' group by substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) < 0) "
                        + "union all "
                        + "select '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "',cast(sum(a.amt) as decimal(25,2))*-1 from "
                        + "(select ifnull(sum(amt),0) as amt from cashinhand where ldate =(SELECT date_format(MAX(ldate),'%Y%m%d') FROM cashinhand WHERE ldate<'" + dt + "' )"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from recon where dt = '" + dt + "' and trantype =0  and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ca_recon where dt = '" + dt + "' and trantype =0  and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from of_recon where dt = '" + dt + "' and trantype =0  and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from rdrecon where dt = '" + dt + "'  and trantype =0  and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ddstransaction where dt = '" + dt + "'  and trantype =0  and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from td_recon where dt = '" + dt + "'  and trantype =0  and auth ='Y' and closeflag is null "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from loan_recon where dt = '" + dt + "' and trantype =0  and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from gl_recon where dt = '" + dt + "'  and trantype =0  and auth ='Y' ) a";
                //" and substring(acno,1,2) = '" + brCode + "'";
            } else if (brCode.contains(",")) {
                query = "select substring(acno,3,8) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from gl_recon where dt <= '" + dt + "' and substring(acno,1,2) in ( " + brCode + ")  and auth ='Y' and substring(acno,3,8) <> '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "' group by substring(acno,3,8)  "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from recon where dt <='" + dt + "' and substring(acno,1,2) in ( " + brCode + ")  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from loan_recon where dt <='" + dt + "' and substring(acno,1,2) in ( " + brCode + ")  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from of_recon where dt <='" + dt + "' and substring(acno,1,2) in ( " + brCode + ")  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from rdrecon where dt <='" + dt + "' and substring(acno,1,2) in ( " + brCode + ")  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from ddstransaction where dt <='" + dt + "'  and substring(acno,1,2) in ( " + brCode + ")  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno, cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from td_recon where dt <='" + dt + "'  and substring(acno,1,2) in ( " + brCode + ")  and auth ='Y' and closeflag is null group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2), cast(sum(cramt- dramt) as decimal(25,2)) from ca_recon where acno in "
                        + "(select acno  from ca_recon where dt <= '" + dt + "'  and substring(acno,1,2) in ( " + brCode + ") group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1) "
                        + "and dt <= '" + dt + "'   and auth ='Y' group by substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0) "
                        + "union all "
                        + "select substring(acno,3,2), cast(sum(cramt- dramt) as decimal(25,2)) from ca_recon where acno in "
                        + "(select acno  from ca_recon where dt <= '" + dt + "'  and substring(acno,1,2) in ( " + brCode + ") group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) "
                        + "and dt <= '" + dt + "'  and auth ='Y' group by substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) < 0) "
                        + "union all "
                        + "select '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "',cast(sum(a.amt) as decimal(25,2))*-1 from "
                        + "(select ifnull(sum(amt),0) as amt from cashinhand where ldate =(SELECT date_format(MAX(ldate),'%Y%m%d') FROM cashinhand WHERE ldate<'" + dt + "' AND brncode in ( " + brCode + ")) and brncode in ( " + brCode + ") "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) in ( " + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ca_recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) in ( " + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from of_recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) in ( " + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from rdrecon where dt = '" + dt + "'  and trantype =0   and auth ='Y' and substring(acno,1,2) in ( " + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ddstransaction where dt = '" + dt + "'  and trantype =0   and auth ='Y' and substring(acno,1,2) in ( " + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from td_recon where dt = '" + dt + "'  and trantype =0  and auth ='Y' and closeflag is null  and substring(acno,1,2) in ( " + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from loan_recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) in ( " + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from gl_recon where dt = '" + dt + "'  and trantype =0   and auth ='Y' and substring(acno,1,2) in ( " + brCode + ") AND IY NOT IN(9999) "
                        + "and substring(ACNO,3,10) not in (select distinct acno from abb_parameter_info where purpose like '%CASH IN HAND%')) a ";

            } else {
                query = "select substring(acno,3,8) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from gl_recon where dt <= '" + dt + "' and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' and substring(acno,3,8) <> '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "' group by substring(acno,3,8)  "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from recon where dt <='" + dt + "' and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from loan_recon where dt <='" + dt + "' and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from of_recon where dt <='" + dt + "' and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from rdrecon where dt <='" + dt + "' and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from ddstransaction where dt <='" + dt + "'  and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno, cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from td_recon where dt <='" + dt + "'  and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' and closeflag is null group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2), cast(sum(cramt- dramt) as decimal(25,2)) from ca_recon where acno in "
                        + "(select acno  from ca_recon where dt <= '" + dt + "'  and substring(acno,1,2) = '" + brCode + "' group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1) "
                        + "and dt <= '" + dt + "'   and auth ='Y' group by substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0) "
                        + "union all "
                        + "select substring(acno,3,2), cast(sum(cramt- dramt) as decimal(25,2)) from ca_recon where acno in "
                        + "(select acno  from ca_recon where dt <= '" + dt + "'  and substring(acno,1,2) = '" + brCode + "' group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) "
                        + "and dt <= '" + dt + "'  and auth ='Y' group by substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) < 0) "
                        + "union all "
                        + "select '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "',cast(sum(a.amt) as decimal(25,2))*-1 from "
                        + "(select ifnull(sum(amt),0) as amt from cashinhand where ldate =(SELECT date_format(MAX(ldate),'%Y%m%d') FROM cashinhand WHERE ldate<'" + dt + "' AND brncode = '" + brCode + "') and brncode = '" + brCode + "'"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ca_recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from of_recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from rdrecon where dt = '" + dt + "'  and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ddstransaction where dt = '" + dt + "'  and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from td_recon where dt = '" + dt + "'  and trantype =0  and auth ='Y' and closeflag is null  and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from loan_recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from gl_recon where dt = '" + dt + "'  and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "and substring(ACNO,3,10) not in (select distinct acno from abb_parameter_info where purpose like '%CASH IN HAND%')) a ";

            }
            return query;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public static String getBranchWiseQueryForTrail(String brCode, String dt) throws ApplicationException {
        try {
            String query = "";
            if (brCode.equals("0A")) {
                query = "select substring(acno,3,8) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from gl_recon where dt <= '" + dt + "' and auth ='Y' and substring(acno,3,8) <> '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "' group by substring(acno,3,8) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from recon where dt <='" + dt + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from loan_recon where dt <='" + dt + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from of_recon where dt <='" + dt + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from rdrecon where dt <='" + dt + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from ddstransaction where dt <='" + dt + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno, cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from td_recon where dt <='" + dt + "'  and auth ='Y' and closeflag is null group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno, cast(ifnull(sum(cramt- dramt),0)as decimal(25,2)) as amt from ca_recon where dt <= '" + dt + "' and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "',cast(sum(a.amt) as decimal(25,2))*-1 from "
                        + "(select ifnull(sum(amt),0) as amt from cashinhand where ldate =(SELECT date_format(MAX(ldate),'%Y%m%d') FROM cashinhand WHERE ldate<'" + dt + "' )"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from recon where dt = '" + dt + "' and trantype =0  and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ca_recon where dt = '" + dt + "' and trantype =0  and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from of_recon where dt = '" + dt + "' and trantype =0  and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from rdrecon where dt = '" + dt + "'  and trantype =0  and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ddstransaction where dt = '" + dt + "'  and trantype =0  and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from td_recon where dt = '" + dt + "'  and trantype =0  and auth ='Y' and closeflag is null "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from loan_recon where dt = '" + dt + "' and trantype =0  and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from gl_recon where dt = '" + dt + "'  and trantype =0  and auth ='Y' ) a";
                //" and substring(acno,1,2) = '" + brCode + "'";
            }else if(brCode.contains(",")){
                query = "select substring(acno,3,8) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from gl_recon where dt <= '" + dt + "' and substring(acno,1,2) in( " + brCode + ")  and auth ='Y' and substring(acno,3,8) <> '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "' group by substring(acno,3,8)  "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from recon where dt <='" + dt + "' and substring(acno,1,2) in( " + brCode + ")  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from loan_recon where dt <='" + dt + "' and substring(acno,1,2) in( " + brCode + ")  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from of_recon where dt <='" + dt + "' and substring(acno,1,2) in( " + brCode + ")  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from rdrecon where dt <='" + dt + "' and substring(acno,1,2) in( " + brCode + ")  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from ddstransaction where dt <='" + dt + "'  and substring(acno,1,2) in( " + brCode + ")  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno, cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from td_recon where dt <='" + dt + "'  and substring(acno,1,2) in( " + brCode + ")  and auth ='Y' and closeflag is null group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno, cast(ifnull(sum(cramt- dramt),0)as decimal(25,2)) from ca_recon where dt <= '" + dt + "'  and substring(acno,1,2) in( " + brCode + ") and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "',cast(sum(a.amt) as decimal(25,2))*-1 from "
                        + "(select ifnull(sum(amt),0) as amt from cashinhand where ldate =(SELECT date_format(MAX(ldate),'%Y%m%d') FROM cashinhand WHERE ldate<'" + dt + "' AND brncode in( " + brCode + ")) and brncode in(" + brCode + ")"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) in(" + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ca_recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) in(" + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from of_recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) in( " + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from rdrecon where dt = '" + dt + "'  and trantype =0   and auth ='Y' and substring(acno,1,2) in( " + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ddstransaction where dt = '" + dt + "'  and trantype =0   and auth ='Y' and substring(acno,1,2) in( " + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from td_recon where dt = '" + dt + "'  and trantype =0  and auth ='Y' and closeflag is null  and substring(acno,1,2) in( " + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from loan_recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) in(" + brCode + ") AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from gl_recon where dt = '" + dt + "'  and trantype =0   and auth ='Y' and substring(acno,1,2) in(" + brCode + ") AND IY NOT IN(9999) "
                        + "and substring(ACNO,3,10) not in (select distinct acno from abb_parameter_info where purpose like '%CASH IN HAND%')) a ";
             
            
            }else {
                query = "select substring(acno,3,8) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from gl_recon where dt <= '" + dt + "' and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' and substring(acno,3,8) <> '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "' group by substring(acno,3,8)  "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from recon where dt <='" + dt + "' and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from loan_recon where dt <='" + dt + "' and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from of_recon where dt <='" + dt + "' and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from rdrecon where dt <='" + dt + "' and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from ddstransaction where dt <='" + dt + "'  and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno, cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from td_recon where dt <='" + dt + "'  and substring(acno,1,2) = '" + brCode + "'  and auth ='Y' and closeflag is null group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno, cast(ifnull(sum(cramt- dramt),0)as decimal(25,2)) from ca_recon where dt <= '" + dt + "'  and substring(acno,1,2) = '" + brCode + "' and auth ='Y' group by substring(acno,3,2) "
                        + "union all "
                        + "select '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "',cast(sum(a.amt) as decimal(25,2))*-1 from "
                        + "(select ifnull(sum(amt),0) as amt from cashinhand where ldate =(SELECT date_format(MAX(ldate),'%Y%m%d') FROM cashinhand WHERE ldate<'" + dt + "' AND brncode = '" + brCode + "') and brncode = '" + brCode + "'"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ca_recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from of_recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from rdrecon where dt = '" + dt + "'  and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ddstransaction where dt = '" + dt + "'  and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from td_recon where dt = '" + dt + "'  and trantype =0  and auth ='Y' and closeflag is null  and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from loan_recon where dt = '" + dt + "' and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from gl_recon where dt = '" + dt + "'  and trantype =0   and auth ='Y' and substring(acno,1,2) = '" + brCode + "' AND IY NOT IN(9999) "
                        + "and substring(ACNO,3,10) not in (select distinct acno from abb_parameter_info where purpose like '%CASH IN HAND%')) a ";

            }
            return query;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public static String getBranchWiseQueryBetweenDate(String brCode, String fromDt, String toDt) throws ApplicationException {
        try {
            String query = "";
            if (brCode.equals("0A")) {
                query = "select substring(acno,3,8) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from gl_recon where dt between  '" + fromDt + "' and '" + toDt + "'  and auth ='Y' and trandesc<>13 and substring(acno,3,8) <> '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "' group by substring(acno,3,8) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from recon where dt between  '" + fromDt + "' and '" + toDt + "'   and auth ='Y' and trandesc<>13 group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from loan_recon where dt between  '" + fromDt + "' and '" + toDt + "'   and auth ='Y' and trandesc<>13 group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from of_recon where dt between  '" + fromDt + "' and '" + toDt + "'   and auth ='Y' and trandesc<>13 group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from rdrecon where dt between  '" + fromDt + "' and '" + toDt + "'   and auth ='Y' and trandesc<>13 group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from ddstransaction where dt between  '" + fromDt + "' and '" + toDt + "'   and auth ='Y' and trandesc<>13 group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno, cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from td_recon where dt between  '" + fromDt + "' and '" + toDt + "'   and auth ='Y' and trandesc<>13 and closeflag is null group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2), cast(sum(cramt- dramt) as decimal(25,2)) from ca_recon where acno in "
                        + "(select acno  from ca_recon where dt between  '" + fromDt + "' and '" + toDt + "'  group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1) "
                        + "and dt between  '" + fromDt + "' and '" + toDt + "'    and auth ='Y' and trandesc<>13 group by substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0) "
                        + "union all "
                        + "select substring(acno,3,2), cast(sum(cramt- dramt) as decimal(25,2)) from ca_recon where acno in "
                        + "(select acno  from ca_recon where dt between  '" + fromDt + "' and '" + toDt + "'  group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) "
                        + "and dt between  '" + fromDt + "' and '" + toDt + "'   and auth ='Y' and trandesc<>13 group by substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) < 0) "
                        + "union all "
                        + "select '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "',cast(sum(a.amt) as decimal(25,2))*-1 from "
                        + "(select ifnull(sum(amt),0) as amt from cashinhand where ldate ='" + CbsUtil.dateAdd(toDt, -1) + "' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from recon where dt between  '" + fromDt + "' and '" + toDt + "'  and trantype =0  and auth ='Y'  and trandesc<>13 "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ca_recon where dt between  '" + fromDt + "' and '" + toDt + "'  and trantype =0  and auth ='Y'  and trandesc<>13 "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from of_recon where dt between  '" + fromDt + "' and '" + toDt + "'  and trantype =0  and auth ='Y'  and trandesc<>13 "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from rdrecon where dt between  '" + fromDt + "' and '" + toDt + "'   and trantype =0  and auth ='Y'  and trandesc<>13 "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ddstransaction where dt between  '" + fromDt + "' and '" + toDt + "'   and trantype =0  and trandesc<>13 and auth ='Y' "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from td_recon where dt between  '" + fromDt + "' and '" + toDt + "'   and trantype =0  and auth ='Y' and trandesc<>13 and closeflag is null "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from loan_recon where dt between  '" + fromDt + "' and '" + toDt + "'  and trantype =0  and auth ='Y' and trandesc<>13 "
                        + "union all "
                        + "select sum(cramt-dramt) as amt from gl_recon where dt between  '" + fromDt + "' and '" + toDt + "'   and trantype =0  and auth ='Y' and trandesc<>13 ) a";
                //" and substring(acno,1,2) = '" + brCode + "'";
            } else {
                query = "select substring(acno,3,8) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from gl_recon where dt between  '" + fromDt + "' and '" + toDt + "'  and substring(acno,1,2) = '" + brCode + "'  and auth ='Y'  and trandesc<>13 and substring(acno,3,8) <> '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "' group by substring(acno,3,8)  "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from recon where dt between  '" + fromDt + "' and '" + toDt + "'  and substring(acno,1,2) = '" + brCode + "'  and auth ='Y'  and trandesc<>13 group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from loan_recon where dt between  '" + fromDt + "' and '" + toDt + "'  and substring(acno,1,2) = '" + brCode + "'  and auth ='Y'  and trandesc<>13 group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from of_recon where dt between  '" + fromDt + "' and '" + toDt + "'  and substring(acno,1,2) = '" + brCode + "'  and auth ='Y'  and trandesc<>13 group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from rdrecon where dt between  '" + fromDt + "' and '" + toDt + "'  and substring(acno,1,2) = '" + brCode + "'  and auth ='Y'  and trandesc<>13 group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno,cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from ddstransaction where dt between  '" + fromDt + "' and '" + toDt + "'   and substring(acno,1,2) = '" + brCode + "'  and auth ='Y'  and trandesc<>13 group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2) as acno, cast(ifnull(sum(cramt-dramt),0)as decimal(25,2)) as amt from td_recon where dt between  '" + fromDt + "' and '" + toDt + "'   and substring(acno,1,2) = '" + brCode + "'  and auth ='Y'  and trandesc<>13 and closeflag is null group by substring(acno,3,2) "
                        + "union all "
                        + "select substring(acno,3,2), cast(sum(cramt- dramt) as decimal(25,2)) from ca_recon where acno in "
                        + "(select acno  from ca_recon where dt between  '" + fromDt + "' and '" + toDt + "'   and substring(acno,1,2) = '" + brCode + "' group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1) "
                        + "and dt between  '" + fromDt + "' and '" + toDt + "'    and auth ='Y'  and trandesc<>13 group by substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) > 0) "
                        + "union all "
                        + "select substring(acno,3,2), cast(sum(cramt- dramt) as decimal(25,2)) from ca_recon where acno in "
                        + "(select acno  from ca_recon where dt between  '" + fromDt + "' and '" + toDt + "'   and substring(acno,1,2) = '" + brCode + "' group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) "
                        + "and dt between  '" + fromDt + "' and '" + toDt + "'   and auth ='Y'  and trandesc<>13 group by substring(acno,3,2) having(cast(sum(cramt- dramt) as decimal(25,2)) < 0) "
                        + "union all "
                        + "select '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue() + "',cast(sum(a.amt) as decimal(25,2))*-1 from "
                        + "(select ifnull(sum(amt),0) as amt from cashinhand where ldate ='" + CbsUtil.dateAdd(toDt, -1) + "'  and brncode = '" + brCode + "'"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from recon where dt between  '" + fromDt + "' and '" + toDt + "'  and trantype =0   and auth ='Y'  and trandesc<>13 and substring(acno,1,2) = '" + brCode + "'"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ca_recon where dt between  '" + fromDt + "' and '" + toDt + "'  and trantype =0   and auth ='Y'  and trandesc<>13 and substring(acno,1,2) = '" + brCode + "'"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from of_recon where dt between '" + fromDt + "' and '" + toDt + "'  and trantype =0   and auth ='Y'  and trandesc<>13 and substring(acno,1,2) = '" + brCode + "'"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from rdrecon where dt   between '" + fromDt + "' and '" + toDt + "'   and trantype =0   and auth ='Y'  and trandesc<>13 and substring(acno,1,2) = '" + brCode + "'"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from ddstransaction where dt between '" + fromDt + "' and '" + toDt + "'   and trantype =0   and auth ='Y'  and trandesc<>13 and substring(acno,1,2) = '" + brCode + "'"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from td_recon where dt between '" + fromDt + "' and '" + toDt + "'   and trantype =0  and auth ='Y'  and trandesc<>13 and closeflag is null  and substring(acno,1,2) = '" + brCode + "'"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from loan_recon where dt between  '" + fromDt + "' and '" + toDt + "'  and trantype =0   and auth ='Y'  and trandesc<>13 and substring(acno,1,2) = '" + brCode + "'"
                        + "union all "
                        + "select sum(cramt-dramt) as amt from gl_recon where dt between '" + fromDt + "' and '" + toDt + "'   and trantype =0   and auth ='Y'  and trandesc<>13 and substring(acno,1,2) = '" + brCode + "') a ";

            }
            return query;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public static String getBrWiseComperativePlQuery(String brCode, String minDt, String compDt,
            String option) throws ApplicationException {
        String query = "", brCodeQuery = "";
        try {
            if (!brCode.equals("ALL")) {
                brCodeQuery = " and substring(acno,1,2) = '" + brCode + "'";
            }
            String plBeforeYearEnd = "select substring(acno,3,8) as acno,cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) "
                    + "as amt from gl_recon where substring(acno,5,6)>='" + SiplConstant.GL_PL_ST.getValue() + "' and "
                    + "substring(acno,5,6)<='" + SiplConstant.GL_PL_END.getValue() + "' and dt>='" + minDt + "' and "
                    + "dt<='" + compDt + "' and auth='Y' and trantype <> 27 and trandesc<>13 " + brCodeQuery + " group by substring(acno,3,8)";
            String plAfterYearEnd = "select substring(acno,3,8) as acno,cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) "
                    + "as amt from gl_recon where substring(acno,5,6)>='" + SiplConstant.GL_PL_ST.getValue() + "' and "
                    + "substring(acno,5,6)<='" + SiplConstant.GL_PL_END.getValue() + "' and dt>='" + minDt + "' and "
                    + "dt<='" + compDt + "' and auth='Y' and trantype <> 27 " + brCodeQuery + " group by substring(acno,3,8)";
            if (option.equals("0")) {
                query = plBeforeYearEnd;
            } else if (option.equals("1")) {
                query = plAfterYearEnd;
            }
            return query;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public static String getBrWisePLBalQuery(String brCode, String minDt, String compDt) throws ApplicationException {
        String brCodeQuery = "";
        try {
            if (!brCode.equals("0A")) {
                if(brCode.contains(",")) brCodeQuery = " and substring(acno,1,2) in(" + brCode + ")";
                else brCodeQuery = " and substring(acno,1,2) = '" + brCode + "'";
            }
            String query = "select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) "
                    + "as amt from gl_recon where substring(acno,5,6)>='" + SiplConstant.GL_PL_ST.getValue() + "' and "
                    + "substring(acno,5,6)<='" + SiplConstant.GL_PL_END.getValue() + "' and dt>='" + minDt + "' and "
                    + "dt<='" + compDt + "' and auth='Y' " + brCodeQuery;

            return query;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public static String getBrWisePlQuery(String brCode, String minDt, String compDt) throws ApplicationException {
        String brCodeQuery = "";
        try {
           if (!brCode.equals("0A")) {
                if(brCode.contains(",")) brCodeQuery = " and substring(acno,1,2) in(" + brCode + ")";
                else brCodeQuery = " and substring(acno,1,2) = '" + brCode + "'";
            }
            String query = "select  substring(acno,5,6),cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) "
                    + "as amt from gl_recon where substring(acno,5,6)>='" + SiplConstant.GL_PL_ST.getValue() + "' and "
                    + "substring(acno,5,6)<='" + SiplConstant.GL_PL_END.getValue() + "' and dt>='" + minDt + "' and "
                    + "dt<='" + compDt + "' and auth='Y' " + brCodeQuery + " group by substring(acno,5,6)";

            return query;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * Calculates the last date of a month-By Dinesh
     */
    public static java.util.Date calculateMonthEndDate(int month, int year) {
        int[] daysInAMonth = {29, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int day = daysInAMonth[month];
        boolean isLeapYear = new GregorianCalendar().isLeapYear(year);

        if (isLeapYear && month == 2) {
            day++;
        }

        GregorianCalendar gc = new GregorianCalendar(year, month - 1, day);
        java.util.Date monthEndDate = new java.util.Date(gc.getTime().getTime());
        return monthEndDate;
    }

    /**
     * For getting role description.
     */
    public static Map<String, String> getRoleMap() {
        Map<String, String> roleMap = new HashMap<String, String>();

        roleMap.put("1", "MANAGER");
        roleMap.put("2", "ASST. MANAGER");
        roleMap.put("3", "OFFICER");
        roleMap.put("4", "CLERK");
        roleMap.put("5", "DBA");
        roleMap.put("6", "SYSTEM ADMIN");
        roleMap.put("7", "SUPER USER");
        roleMap.put("99", "AUDITOR");

        return roleMap;
    }

    public static String getOrdinalSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    public static List getMonthEndDtFormCurDtInFinancialYear(String finStartDt, String reportDt) {
        List<String> mEndDateList = new ArrayList<String>();
        try {
            String mEndDate = "";

            mEndDate = ymmd.format(getLastDateOfMonth(ymmd.parse(reportDt)));;
            mEndDateList.add(mEndDate);
            for (int i = 0; i < monthDiff(ymmd.parse(finStartDt), ymmd.parse(reportDt)); i++) {
                mEndDate = monthAdd(mEndDate, -1);
                mEndDate = ymmd.format(getLastDateOfMonth(ymmd.parse(mEndDate)));
                mEndDateList.add(mEndDate);
            }
//            for (String strDt : mEndDateList) {
//                System.out.println("Now Date is::" + strDt);
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mEndDateList;
    }

    public static Map<Integer, String> getAllMonths() {
        Map<Integer, String> map = new TreeMap<Integer, String>();

        map.put(1, "January");
        map.put(2, "February");
        map.put(3, "March");
        map.put(4, "April");
        map.put(5, "May");
        map.put(6, "June");
        map.put(7, "July");
        map.put(8, "August");
        map.put(9, "September");
        map.put(10, "October");
        map.put(11, "November");
        map.put(12, "December");

        return map;
    }

    public static <T> Set<T> findDuplicates(Collection<T> list) {
        Set<T> duplicates = new LinkedHashSet<T>();
        Set<T> uniques = new HashSet<T>();
        for (T t : list) {
            if (!uniques.add(t)) {
                duplicates.add(t);
            }
        }
        return duplicates;
    }

    /**
     *
     * @return @throws Exception:Array Ist index will be First Date and 2nd
     * index will be last date.
     */
    public static String[] calculateIstAndLastDateOfPreviousMonth() throws Exception {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        String[] arr = new String[2];
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            cal.set(Calendar.DATE, 1);
            Date firstDateOfPreviousMonth = cal.getTime();

            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

            Date lastDateOfPreviousMonth = cal.getTime();

            arr[0] = ymd.format(firstDateOfPreviousMonth);
            arr[1] = ymd.format(lastDateOfPreviousMonth);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return arr;
    }
    //------- added by manish

    public static boolean generateXML(String filePath, String fileName, String encImg) throws Exception {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(fileName);
        doc.appendChild(rootElement);
        rootElement.appendChild(doc.createTextNode(encImg));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        //File xmlDirs = new File(filePath);
        //xmlDirs.mkdirs();
        File xmlFile = new File(filePath + ".xml");
        xmlFile.createNewFile();
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
        return true;
    }

    public static List<File> pdfToImage(String sourceDir, String destinationDir) throws Exception {
        List<File> files = new ArrayList<File>();
        try {
//            String sourceDir = "/root/Desktop/3_1.pdf";
//            String destinationDir = "/root/Desktop/PdfToImage/";
            File sourceFile = new File(sourceDir);
            File destinationFile = new File(destinationDir);
            if (!destinationFile.exists()) {
                destinationFile.mkdir();
            }
            if (sourceFile.exists()) {
                PDDocument document = PDDocument.load(sourceDir);
                List<PDPage> list = document.getDocumentCatalog().getAllPages();
                String fileName = sourceFile.getName().replace(".pdf", "");
                int pageNumber = 1;
                for (PDPage page : list) {
                    BufferedImage image = page.convertToImage();
                    File outputfile = new File(destinationDir + fileName + "_" + pageNumber + ".jpg");
                    ImageIO.write(image, "jpg", outputfile);
                    files.add(outputfile);
                    pageNumber++;
                }
                document.close();
                System.out.println("Converted Images from PDF are saved at -> " + destinationFile.getAbsolutePath());
            } else {
                System.out.println(sourceFile.getName() + " File not exists");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return files;
    }

    public static String getMonthNameIn3Alpha(int month) {
        String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};
        return monthName[month - 1];
    }
}
