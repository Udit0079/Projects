<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title><h:outputText value="#{ReportPage.reportName}"/></title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">                
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">

                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{ReportPage.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel"  value="#{ReportPage.reportName}"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{ReportPage.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGroup layout="block" style="width:100%;height:450px;overflow:auto;">
                        <%
                            if (session.getAttribute("HTMLReport") != null) {
                        %>
                        <iframe src="/cbs-jsp/report/ReportFrame.jsp" frameborder="0" width="100%" height="430"></iframe>
                        <%	} 
                            else if (session.getAttribute("FilePath") != null) {
                        %>
                        <iframe src="/cbs-jsp/report/PDFFrame.jsp" frameborder="0" width="100%" height="430"></iframe>
                        <%	} else {
                                response.sendRedirect("/cbs-jsp/");
                            }
                        %>
                    </h:panelGroup>

                    <h:panelGrid  id="errMsgPanel" width="100%" style="width:100%;text-align:center">
                        <h:outputText id="errMsg" value="#{ReportPage.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid columns="5" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="a1" layout="block" >
                            <a4j:commandButton id="btnReport" value="Print" action="#{ReportPage.printReport}" oncomplete="if(#{ReportPage.flag=='true'}){#{rich:component('chargesModalPanel')}.show();}" reRender="chargesModalPanel,accChargeDeduct,errMsg"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ReportPage.exitAction}"
                                               oncomplete="if(#{ReportPage.winClose==true}){window.close();return;}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>

                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel> 

                <rich:modalPanel id="chargesModalPanel" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                    <f:facet name="header">
                        <h:outputText value="Confirmation DialogBox" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Do you want to deduct charges for account statement?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:region id="yesBtn">
                                            <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{ReportPage.accountStatChargDeductCode}" oncomplete="{#{rich:component('accChargeDeduct')}.show();#{rich:component('chargesModalPanel')}.hide();}" reRender="accChargeDeduct"/>
                                        </a4j:region>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton id="No" value="No" oncomplete="{#{rich:component('reasonsModalPanel')}.show();#{rich:component('chargesModalPanel')}.hide();}"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>

                <rich:modalPanel id="accChargeDeduct" autosized="true" width="250">
                    <f:facet name="header">
                        <h:outputText value="Confirmation DialogBox" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td>
                                        <h:outputText value="#{ReportPage.message}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton id="OK" value="OK" onclick="if(#{ReportPage.reasons=='false'}){#{rich:component('reasonsModalPanel')}.show();}else{#{rich:component('accChargeDeduct')}.hide();return false;}" reRender="accChargeDeduct,reasonsModalPanel,resonsText"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>

                <rich:modalPanel id="reasonsModalPanel" autosized="true" width="350" onshow="#{rich:element('resonsText')}.focus()">
                    <f:facet name="header">
                        <h:outputText value="Prompt DialogBox" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td> 
                                        <h:panelGrid columns="1" id="gridPanel1" style="width:100%;text-align:left;" styleClass="row1" width="100%">
                                            <h:outputLabel value="Enter the reasons for not deducting charges"/>
                                            <h:outputLabel/>
                                            <h:inputText id="resonsText" size="50" value="#{ReportPage.remarks}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                                <a4j:support event="onblur"  ajaxSingle="true"/>
                                            </h:inputText>
                                        </h:panelGrid>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton id="OK" value="OK" action="#{ReportPage.noReasonData}" ajaxSingle="true"oncomplete="{#{rich:component('accChargeDeduct')}.show();#{rich:component('reasonsModalPanel')}.hide();}" reRender="accChargeDeduct,resonsText"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table> 
                    </h:form>
                </rich:modalPanel>

            </a4j:form>
        </body>
    </html>
</f:view>