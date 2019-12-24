<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<h:form>
    <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
        <h:panelGroup layout="block" style="width:100%;height:450px;overflow:auto;">
            <%if (session.getAttribute("HTMLReport") != null) {%>
            <iframe src="/cbs-jsp/faces/report/ReportFrame.jsp" frameborder="0" width="100%" height="430"></iframe>
            <%}%>
        </h:panelGroup>
        <h:panelGrid  id="errMsgPanel" width="100%" style="width:100%;text-align:center">
            <h:outputText id="errMsg" value="#{ReportPagePopUp.message}" styleClass="error"/>
        </h:panelGrid>
           <h:panelGrid columns="1" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="a1" layout="block" >
                            <a4j:commandButton id="btnReport" value="Print" action="#{ReportPagePopUp.printReport}" reRender="errMsgPanel"/>
                            <a4j:commandButton id="btnClose" value="Close" onclick="#{rich:component('popUpRepPanel')}.hide()"/>
                        </h:panelGroup>
          </h:panelGrid>
    </h:panelGrid>
</h:form>
