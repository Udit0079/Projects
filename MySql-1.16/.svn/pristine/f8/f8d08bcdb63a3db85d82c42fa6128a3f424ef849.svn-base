<%-- 
    Document   : npcictsmodification
    Created on : 5 Jun, 2017, 11:44:23 AM
    Author     : root
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>CTS Modification</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".txtDatein").mask("99/99/9999");
                }
                function setCaretToEnd(e) {
                    var control = $((e.target ? e.target : e.srcElement).id);
                    if (control.createTextRange) {
                        var range = control.createTextRange();
                        range.collapse(false);
                        range.select();
                    }
                    else if (control.setSelectionRange) {
                        control.focus();
                        var length = control.value.length;
                        control.setSelectionRange(length, length);
                    }
                    control.selectionStart = control.selectionEnd = control.value.length;
                }
            </script>
        </head>
        <body>
            <a4j:form id="form">
                <a4j:region>
                    <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                        <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                            <h:panelGroup id="groupPanel1" layout="block">
                                <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                                <h:outputText id="stxtDate" styleClass="output" value="#{NPCIctsModif.todayDate}"/>
                            </h:panelGroup>
                            <h:outputLabel id="label" styleClass="headerLabel" value="CTS Modification"/>
                            <h:panelGroup id="groupPanel2" layout="block">
                                <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                                <h:outputText id="stxtUser" styleClass="output" value="#{NPCIctsModif.userName}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel2" columnClasses="col8,col8" columns="2" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{NPCIctsModif.errMessage}"/>
                        </h:panelGrid>

                        <h:panelGrid id="gridPanel3" columns="4" columnClasses="col3,col21,col2,col20" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblFunction" styleClass="label" value="Function"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:150px" value="#{NPCIctsModif.function}">
                                <f:selectItems value="#{NPCIctsModif.functionList}"/>
                                <a4j:support event="onblur" action="#{NPCIctsModif.onBlurFunction()}" reRender="stxtMsg,mainPanel,txtDatein,gridPanel4"
                                             focus="txtDatein" oncomplete="setMask();"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblDatein" styleClass="label" value="Date"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtDatein" styleClass="input txtDatein" maxlength = "255"  style="width:134px" value="#{NPCIctsModif.date}" >
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel4" columns="4" columnClasses="col3,col21,col2,col20" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblChequeType" styleClass="label" value="Transation Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddChequeType" styleClass="ddlist" size="1" style="width:150px" value="#{NPCIctsModif.chequeType}">
                                <f:selectItems value="#{NPCIctsModif.chequeTypeList}"/>
                                <a4j:support event="onblur" action="#{NPCIctsModif.getScheduleNoOnBlur}" reRender="stxtMsg,ddSchedule" 
                                             focus="ddSchedule"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblSchedule" styleClass="label" value="Schedule No."><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddSchedule" styleClass="ddlist" size="1" style="width:150px" value="#{NPCIctsModif.scheduleNo}">
                                <f:selectItems value="#{NPCIctsModif.scheduleNoList}"/>
                                <a4j:support event="onblur" action="#{NPCIctsModif.getDataToEditInGrid()}" reRender="stxtMsg,detailPanal,tablePanel" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <rich:panel id="detailPanal" style="text-align:left;display:#{NPCIctsModif.displayModifyUploadDate}">
                            <h:panelGrid  id="image" columns="2" columnClasses="col11,col10" width="100%" style="height:250px;vertical-align:top;" styleClass="row1">
                                <h:panelGrid id="imageGrid" columns="1" style="width:100%;text-align:center;" columnClasses="vtop">
                                    <h:graphicImage id="chqImage" value="/cts-image/dynamic/?file=#{NPCIctsModif.imageData}&orgn=#{NPCIctsModif.folderName}" height="250" width="600"/>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanel51" columnClasses="col15,col15,col15,col15,col15,col15,col23,col15" columns="8" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblSanMode" styleClass="label" value="Nature"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddSanMode" styleClass="ddlist" size="1" style="width:150px" value="#{NPCIctsModif.sanNature}">
                                    <f:selectItems value="#{NPCIctsModif.sanNatureList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblsan" styleClass="label" value="#{NPCIctsModif.sanLabel}"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtsan" styleClass="input" maxlength = "255"  style="width:134px" value="#{NPCIctsModif.sanNo}">
                                    <a4j:support  event="onblur"  action="#{NPCIctsModif.onBlurSerialNo()}" reRender="stxtMsg,detailPanal" focus="txtserNo"/>
                                </h:inputText>
                                <h:outputLabel id="lblserNo" styleClass="label" value="Serial No."><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtserNo" styleClass="input" maxlength = "255"  style="width:134px" value="#{NPCIctsModif.serialNo}">
                                    <a4j:support  event="onblur" action="#{NPCIctsModif.onBlurSerialNo()}" reRender="stxtMsg,detailPanal" focus="btnSave"/>
                                </h:inputText>
                                <h:outputLabel id="lblInstDate" styleClass="label" value="Inst Date"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtInstDate" styleClass="input txtDatein" maxlength = "255"  style="width:134px" disabled="#{NPCIctsModif.disableInstDt}" value="#{NPCIctsModif.instDate}">
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanel5" columnClasses="col1,col4,col1,col4,col1,col4" columns="6" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblFaver" styleClass="label" value="In Favor Of"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtFaver" styleClass="input" maxlength = "255"  style="width:134px" value="#{NPCIctsModif.favorOf}">
                                </h:inputText>
                                <h:outputLabel id="lblAcNo" styleClass="label" value="Account No."><font class="required" color="red">*</font></h:outputLabel>
                                <h:outputText id="txtAcNo" styleClass="output" value="#{NPCIctsModif.acno}" />
                                <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name"><font class="required" color="red">*</font></h:outputLabel>
                                <h:outputText id="txtCustName" styleClass="output" value="#{NPCIctsModif.custName}" />
                            </h:panelGrid>
                            <h:panelGrid id="gridPanel7" columnClasses="col1,col4,col1,col4,col1,col4" columns="6" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lbloprMode" styleClass="label" value="Operation Mode"></h:outputLabel>
                                <h:outputText id="txtoprMode" styleClass="output" value="#{NPCIctsModif.operMode}" />
                                <h:outputLabel id="lbldestBr" styleClass="label" value="Dest Branch"></h:outputLabel>
                                <h:outputText id="txtdestBr" styleClass="output" value="#{NPCIctsModif.destBranch}" />
                                <h:outputLabel id="lblSeqNo" styleClass="label" value="Seq No."></h:outputLabel>
                                <h:outputText id="txtSeqNo" styleClass="output" value="#{NPCIctsModif.seqNo}" />
                            </h:panelGrid>
                            <h:panelGroup id="btnPanel1" styleClass="vtop">
                                <h:outputText id="stxtInstruction1" styleClass="output" value="F8-White Image,   F9-Back Image,  F10-Gray Image" style="color:blue;"/>
                            </h:panelGroup>
                            <rich:messages></rich:messages>
                            <a4j:jsFunction name="showWhilteImage" action="#{NPCIctsModif.getWhiteImage}" reRender="chqImage" />
                            <a4j:jsFunction name="showBackImage" action="#{NPCIctsModif.getBackImage}" reRender="chqImage" />
                            <a4j:jsFunction name="showGrayImage" action="#{NPCIctsModif.getGrayImage}" reRender="chqImage" />
                            <rich:hotKey key="F8" handler="showWhilteImage();"/>
                            <rich:hotKey key="F9" handler="showBackImage();"/>
                            <rich:hotKey key="F10" handler="showGrayImage();"/>
                        </rich:panel>

                        <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{NPCIctsModif.displayModifyUploadDate}" styleClass="row2" width="100%">
                            <a4j:region id="tblRegion">
                                <rich:dataTable value="#{NPCIctsModif.gridDetail}" var="dataItem" 
                                                rowClasses="gridrow1,gridrow2" id="taskList" rows="3" 
                                                columnsWidth="100" rowKeyVar="row" 
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" 
                                                width="100%">


                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="18"><h:outputText value="Upload Data Detail" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Inst. No." /></rich:column>
                                            <rich:column ><h:outputText value="A/c Number" /></rich:column>
                                            <rich:column><h:outputText value="Customer Name" /></rich:column>
                                            <rich:column><h:outputText value="Inst Amt." /></rich:column>
                                            <rich:column><h:outputText value="Inst. Date" /></rich:column>
                                            <rich:column><h:outputText value="In Favor Of" /></rich:column>
                                            <rich:column><h:outputText value="Item Seq. No." /></rich:column>
                                            <rich:column><h:outputText value="Item Trn Code" /></rich:column>
                                            <rich:column><h:outputText value="Select"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column filterMethod="#{NPCIctsModif.filterInstrumentNoList}">
                                        <f:facet name="header">
                                            <h:inputText value="#{NPCIctsModif.instNoFilter}" id="input">
                                                <a4j:support event="onkeyup" reRender="tablePanel" ignoreDupResponses="true" 
                                                             requestDelay="700" oncomplete="setCaretToEnd(event);"/>
                                            </h:inputText>
                                        </f:facet>
                                        <h:outputText value="#{dataItem.instNo}" />
                                    </rich:column>
                                    <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.instAmt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.instDt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.favourOf}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.itemSeqNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.itemTransCode}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{NPCIctsModif.setTableDataToForm}" 
                                                         focus="txtProduct" reRender="mainPanel,detailPanal">
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{NPCIctsModif.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="30"/>
                            </a4j:region>
                        </h:panelGrid>

                        <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                                <a4j:commandButton id="btnSave" value="Update" action="#{NPCIctsModif.btnAction}" 
                                                   reRender="stxtMsg,mainPanel" style="display:#{NPCIctsModif.displayModifyUploadDate}" 
                                                   oncomplete="setMask();"/>
                                <a4j:commandButton id="btnViewReport" value="View Report" action="#{NPCIctsModif.viewReport}"
                                                   oncomplete="setMask();"
                                                   reRender="stxtMsg,mainPanel" style="display:#{NPCIctsModif.displayUploadReport}"/>
                                <a4j:commandButton id="btnDwnPDF" value="Download PDF" action="#{NPCIctsModif.viewPdfReport}" 
                                                   reRender="stxtMsg,mainPanel" style="display:#{NPCIctsModif.displayUploadReport}"
                                                   oncomplete="setMask();"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{NPCIctsModif.refreshForm}" 
                                                   reRender="mainPanel" oncomplete="setMask();"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{NPCIctsModif.exitForm}" 
                                                   reRender="mainPanel"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </a4j:region>
            </a4j:form>

        </body>
    </html>
</f:view>
