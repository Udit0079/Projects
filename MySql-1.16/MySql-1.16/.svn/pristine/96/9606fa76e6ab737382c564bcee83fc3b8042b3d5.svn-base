<%@ page contentType="application/pdf" %>
<%@ page import="java.io.BufferedInputStream"%>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="com.cbs.exception.ApplicationException"%>
<%@ page import="com.cbs.web.controller.ReportBean"%>
<%
    String filePath = session.getAttribute("FilePath").toString();
    out.print(filePath);
    File file = new File(filePath);
    BufferedInputStream input = null;
    ServletOutputStream output = null;
    try {
        int size = (int) file.length();
        input = new BufferedInputStream(new FileInputStream(file), size);
        response.reset();
        response.setHeader("Content-Type", "application/pdf");
        response.setHeader("Content-Length", String.valueOf(size));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        output = response.getOutputStream();
        byte[] buffer = new byte[size];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
    } catch (Exception e) {
        throw new ApplicationException(e);
    } finally {
        ReportBean.close(output);
        ReportBean.close(input);
        //file.delete();
    }
%>

