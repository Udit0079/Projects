<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Misc Long Book ST Report</title>
              <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="misclongbooksubtotalreport">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>

                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;width:100%" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{miscLongBookSTReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Misc Long Book ST Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{miscLongBookSTReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{miscLongBookSTReport.message}" styleClass="error"/>
                    </h:panelGrid>

                     <h:panelGrid columnClasses="col3,col3,col16,col17,col3" columns="6" id="gridPanel1" style="text-align:left;" styleClass="row2">
                       <h:outputLabel/>
                       <h:outputLabel/>
                        <h:outputLabel/>
                            <h:outputLabel id="calDatelbl" value="As On Date :" styleClass="output">
                                <font class="required" style="color:red">*</font>
                            </h:outputLabel>
                           <h:panelGroup styleClass="label">
                            <h:inputText id="txtDate"   styleClass="input calInstDate" style="width:70px;"  value="#{miscLongBookSTReport.calDate}"/>
                            <font color="purple">DD/MM/YYYY</font>
                        </h:panelGroup>
                            <h:outputLabel/>
                            <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid  columns="5" id="gridPanel2" style="text-align:center;width:100%" styleClass="row1">
                       
                            <h:panelGroup layout="block">
                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <h:selectBooleanCheckbox id="chkTime" value="#{miscLongBookSTReport.timeBased}">
                                    <a4j:support event="onchange" ajaxSingle="true" reRender="gridPanel2b"/>
                                </h:selectBooleanCheckbox>
                                <h:outputLabel value="Time Based Report" styleClass="label"/>
                            </h:panelGroup>
                       
                       
                            <h:panelGroup layout="block" id="gridPanel2b" style="padding-right:157px;">
                                <h:outputLabel value="From :" styleClass="label"/>
                                &nbsp;&nbsp;
                                <h:outputLabel value="HH" styleClass="label"/>
                                &nbsp;
                                <h:selectOneListbox styleClass="ddlist" size="1" value="#{miscLongBookSTReport.fromHour}" disabled="#{!miscLongBookSTReport.timeBased}">
                                    <f:selectItems value="#{miscLongBookSTReport.fromHourList}"/>
                                </h:selectOneListbox>
                                &nbsp;&nbsp;
                                <h:outputLabel value="MM" styleClass="label"/>
                                &nbsp;
                                <h:selectOneListbox styleClass="ddlist" size="1" value="#{miscLongBookSTReport.fromMinute}" disabled="#{!miscLongBookSTReport.timeBased}">
                                    <f:selectItems value="#{miscLongBookSTReport.fromMinuteList}"/>
                                </h:selectOneListbox>
                                &nbsp;&nbsp;
                                <h:selectOneListbox styleClass="ddlist" size="1" value="#{miscLongBookSTReport.fromMeridiem}" disabled="#{!miscLongBookSTReport.timeBased}">
                                    <f:selectItem itemValue="AM" itemLabel="AM"/>
                                    <f:selectItem itemValue="PM" itemLabel="PM"/>
                                </h:selectOneListbox>
                                &nbsp;&nbsp; &nbsp;&nbsp;
                                <h:outputLabel value="To :" styleClass="label"/>
                                &nbsp;&nbsp;
                                <h:outputLabel value="HH" styleClass="label"/>
                                &nbsp;
                                <h:selectOneListbox styleClass="ddlist" size="1" value="#{miscLongBookSTReport.toHour}" disabled="#{!miscLongBookSTReport.timeBased}">
                                    <f:selectItems value="#{miscLongBookSTReport.toHourList}"/>
                                </h:selectOneListbox>
                                &nbsp;&nbsp;
                                <h:outputLabel value="MM" styleClass="label"/>
                                &nbsp;
                                <h:selectOneListbox styleClass="ddlist" size="1" value="#{miscLongBookSTReport.toMinute}" disabled="#{!miscLongBookSTReport.timeBased}">
                                    <f:selectItems value="#{miscLongBookSTReport.toMinuteList}"/>
                                </h:selectOneListbox>
                                &nbsp;&nbsp;
                                <h:selectOneListbox styleClass="ddlist" size="1" value="#{miscLongBookSTReport.toMeridiem}" disabled="#{!miscLongBookSTReport.timeBased}">
                                    <f:selectItem itemValue="AM" itemLabel="AM"/>
                                    <f:selectItem itemValue="PM" itemLabel="PM"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;width:100%" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" action="#{miscLongBookSTReport.printAction}" type="submit" value="Print Report" reRender="errmsg,txtDate" oncomplete="setMask()">
                            </a4j:commandButton>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{miscLongBookSTReport.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnExit" action="#{miscLongBookSTReport.exitAction}" value="Exit" reRender="errmsg,txtDate" oncomplete="setMask()">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>