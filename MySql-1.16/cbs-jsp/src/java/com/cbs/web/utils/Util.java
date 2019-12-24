package com.cbs.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.GregorianCalendar;
import java.util.Random;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Dhirendra Chaudhary
 */
public class Util {

    /**
     *
     */
    public static String DATABASE_NAME = "fin_inclusion";
    public static String HOST;
    public static int PORT;
    // private static Database dataBase;

    /**
     *
     * @return
     */
//    public static Database getDatabase() {
//        Properties props = new Properties();
//        InputStream is = null;
//        try {
//            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("couchdb.properties");
//            props.load(is);
//            HOST = props.getProperty("host");
//            PORT = Integer.parseInt(props.getProperty("port"));
//
//            Server server = new ServerImpl(HOST, PORT);
//            List<String> databases = server.listDatabases();
//            if (!databases.contains(DATABASE_NAME)) {
//                server.createDatabase(DATABASE_NAME);
//            }
//            return new Database(server, DATABASE_NAME);
//        } catch (Exception e) {
//            Server server = new ServerImpl("192.168.2.64", Server.DEFAULT_PORT);
//            List<String> databases = server.listDatabases();
//            if (!databases.contains(DATABASE_NAME)) {
//                server.createDatabase(DATABASE_NAME);
//            }
//            return new Database(server, DATABASE_NAME);
//            //throw new RuntimeException(e);
//        } finally {
//            try {
//                is.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static List<CustomerDocument> getCustomers() throws IOException, ParseException {
//        List<CustomerDocument> dataList = new ArrayList<CustomerDocument>();
//
//        dataBase = getDatabase();
//        ViewResult<CustomerDocument> result = dataBase.queryView("customers/getCustomers", CustomerDocument.class, null, null);
//        for (int i = 0; i < result.getTotalRows(); i++) {
//            CustomerDocument fooDoc = result.getRows().get(i).getValue();
//            dataList.add(fooDoc);
//        }
//        return dataList;
//    }
//    public static void updateDocument(CustomerDocument doc) throws IOException {
//        dataBase.updateDocument(doc);
//    }
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

    public static String[] getReportOptionAndDescription(String reportIn) {
        String[] arr = new String[2];
        String reptOpt = "", reportDesc = "";
        if (reportIn.equalsIgnoreCase("T")) {
            reptOpt = "1000";
            reportDesc = "Amount (Rs. in Thousand)";
        } else if (reportIn.equalsIgnoreCase("L")) {
            reptOpt = "100000";
            reportDesc = "Amount (Rs. in Lac)";
        } else if (reportIn.equalsIgnoreCase("C")) {
            reptOpt = "10000000";
            reportDesc = "Amount (Rs. in Crore)";
        } else if (reportIn.equalsIgnoreCase("R")) {
            reptOpt = "1";
            reportDesc = "Amount (Rs.)";
        }
        arr[0] = reptOpt;
        arr[1] = reportDesc;
        return arr;
    }
    
    public static String postMFS100Client(String ip, String methodName, String data) throws MalformedURLException,IOException{
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            String url = "http://" + ip + ":7070/mfs100/"+methodName;
            HttpPost postRequest = new HttpPost(url);

            StringEntity input = new StringEntity(data);
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            StringBuffer result = new StringBuffer();
            String output = "";
            while ((output = br.readLine()) != null) {
                result.append(output);
            }
            httpClient.getConnectionManager().shutdown();
            return result.toString();
        } catch (MalformedURLException e) {
            throw new MalformedURLException(e.getMessage());
        }catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
