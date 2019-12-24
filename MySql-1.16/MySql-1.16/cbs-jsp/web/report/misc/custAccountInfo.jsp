<%-- 
    Document   : custAccountInfo
    Created on : Feb 19, 2016, 11:23:43 AM
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
            <title>Risk Category and Turnover Profile</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{CustAccountInfo.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Risk Category and Turnover Profile"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CustAccountInfo.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{CustAccountInfo.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel1"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{CustAccountInfo.branchCode}" styleClass="ddlist" style="width:70px">
                            <f:selectItems value="#{CustAccountInfo.branchCodeList}" /> 
                        </h:selectOneListbox>         
                        <h:outputText value="A/c Nature"styleClass="label" />
                        <h:selectOneListbox id="ddacType" size="1" value="#{CustAccountInfo.acnoNature}" styleClass="ddlist" style="width:70px" >
                            <f:selectItems value="#{CustAccountInfo.acnoNatureList}"/>
                            <a4j:support  action="#{CustAccountInfo.getAcTypeByAcNature}" event="onchange"
                                          reRender="selectListBox" oncomplete="setMask();"/>
                        </h:selectOneListbox>                    
                        <h:outputLabel id="lblAcType" styleClass="headerLabel"  value="Account Type"  />
                        <h:selectOneListbox id="selectListBox" value="#{CustAccountInfo.selectAcType}" styleClass="ddlist"  style="width:70px" size="1" >
                            <f:selectItems id="selectAcTypeList" value="#{CustAccountInfo.selectAcTypeList}" />
                        </h:selectOneListbox>  
                    </h:panelGrid>

                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel2"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblFrDate" styleClass="headerLabel"  value="From Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtfrDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{CustAccountInfo.frDate}"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup> 
                            <h:outputLabel id="lblToDate" styleClass="headerLabel"  value="To Date"  />
                            <h:panelGroup styleClass="label">
                                <h:inputText id="txttoDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{CustAccountInfo.toDate}"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>         

                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                     
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnView" value="View"   action="#{CustAccountInfo.viewReport}" reRender="stxtError,mainPanelGrid,txtDate" /> 
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{CustAccountInfo.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;" /> 
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{CustAccountInfo.refreshForm}"  reRender="mainPanelGrid,txtDate" oncomplete="setMask()"/> 
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{CustAccountInfo.exitAction}" reRender="mainPanelGrid" /> 
                        </h:panelGroup>
                      
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
