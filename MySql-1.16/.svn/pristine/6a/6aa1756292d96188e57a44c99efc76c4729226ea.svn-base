<%-- 
    Document   : kycAccountDetail
    Created on : Aug 28, 2014, 12:10:48 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Kyc Account Detail Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{KycAccountDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Kyc Update Account Detail"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{KycAccountDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{KycAccountDetail.message}"/>
                    </h:panelGrid>
                    <h:panelGrid style="height:30px" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel1"  styleClass="row1" width="100%" >
                        <h:outputText value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="listbranch" value="#{KycAccountDetail.branch}" styleClass="ddlist" size="1"  style="width:80px">
                            <f:selectItems value="#{KycAccountDetail.branchList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="ddReportType" value="#{KycAccountDetail.reportType}" styleClass="ddlist" size="1" style="width:140px">
                            <f:selectItems value="#{KycAccountDetail.reportTypeList}"/> 
                            <a4j:support action="#{KycAccountDetail.reportTypeProcess}"  event="onchange" ajaxSingle="true" 
                                         reRender="toDt,lbltoDate,lblFromDate,ddReportOption,gridpanel11" limitToList="true" />
                        </h:selectOneListbox>
                        <h:outputLabel value="Report Option" styleClass="label"/>
                        <h:selectOneListbox id="ddReportOption" value="#{KycAccountDetail.reportOption}" styleClass="ddlist" size="1" style="width:140px">
                            <f:selectItems value="#{KycAccountDetail.reportOptionList}"/> 
                            <a4j:support  action="#{KycAccountDetail.actionOnReportOption}" event="onchange"reRender="gridpanel11" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid style="height:30px;display:#{KycAccountDetail.naturePanalDisplay}" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel11"  styleClass="row2" width="100%" >
                        <h:outputText value="A/c Nature"styleClass="label" />
                        <h:selectOneListbox id="ddacType" size="1" value="#{KycAccountDetail.acnoNature}" styleClass="ddlist" style="width: 70px" >
                            <f:selectItems value="#{KycAccountDetail.acnoNatureList}"/>
                            <a4j:support  action="#{KycAccountDetail.getAcTypeByAcNature}" event="onchange"reRender="selectListBox" oncomplete="setMask();"/>
                        </h:selectOneListbox>                    
                        <h:outputLabel id="lblAcType" styleClass="headerLabel"  value="Account Type"  />
                        <h:selectOneListbox id="selectListBox" value="#{KycAccountDetail.selectAcType}" styleClass="ddlist"  style="width:70px" size="1" >
                            <f:selectItems id="selectAcTypeList" value="#{KycAccountDetail.selectAcTypeList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>


                    <h:panelGrid style="height:30px" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel2"  styleClass="row1" width="100%" >
                        <%--h:outputText value="As On Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtFrmDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{KycAccountDetail.asOnDate}"/>
                            <font color="purple">DD/MM/YYYY</font>
                        </h:panelGroup--%> 

                        <%--h:outputLabel id="lblAsonDate" value="As On Date" style="display:#{KycAccountDetail.display}" styleClass="label"/>
                        <h:inputText id="asDt" styleClass="input calInstDate" maxlength="10" value="#{KycAccountDetail.asOnDate}" style="width:70px;display:#{KycAccountDetail.display}" /--%>
                        <h:outputLabel id="lblFromDate" value="#{KycAccountDetail.dateButton}" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input calInstDate" maxlength="10" value="#{KycAccountDetail.calFromDate}" style="width:70px;"/>
                        <h:outputLabel id="lbltoDate"  value="To Date" style="display:#{KycAccountDetail.display}" styleClass="label" />
                        <h:inputText id="toDt" styleClass="input calInstDate" maxlength="10" value="#{KycAccountDetail.caltoDate}" style="width:70px;display:#{KycAccountDetail.display}" />
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnView" value="Print Report"   action="#{KycAccountDetail.viewReport}" reRender="stxtError,mainPanelGrid,ddReportType" oncomplete="setMask()">
                            </a4j:commandButton>
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{KycAccountDetail.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                            <h:commandButton id="btnExcel"  value="Download Excel"  actionListener="#{KycAccountDetail.downloadAction}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{KycAccountDetail.refreshForm}"  reRender="stxtError,mainPanelGrid,ddReportType" oncomplete="setMask()">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{KycAccountDetail.exitAction}" reRender="mainPanelGrid" >
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>
