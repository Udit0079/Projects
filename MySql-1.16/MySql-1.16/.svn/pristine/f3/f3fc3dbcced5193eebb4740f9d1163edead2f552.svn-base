<%-- 
    Document   : totalIntRec
    Created on : Jul 21, 2012, 2:37:57 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Total Int. Rec. Report</title>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{totalIntRec.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Interest Rec. Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{totalIntRec.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="bankAndTypeGrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblBank" styleClass="label" value="Bank Name"/>
                        <h:selectOneListbox id="ddBankType" style="width: 120px" styleClass="ddlist" value="#{totalIntRec.bnkType}" size="1">
                            <f:selectItems value="#{totalIntRec.bnkTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblSelect" styleClass="label" value="Select Status"/>
                        <h:selectOneListbox id="ddSelect" size="1" styleClass="ddlist" value="#{totalIntRec.selectStatus}">
                            <f:selectItems value="#{totalIntRec.selectStatusList}"/>                            
                        </h:selectOneListbox>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{totalIntRec.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{totalIntRec.printAction}" id="btnProcess" value="Print Report" reRender="mainPanel"/>
                            <a4j:commandButton action="#{totalIntRec.exitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
