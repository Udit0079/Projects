<%-- 
    Document   : SecAccrIntPost
    Created on : Aug 1, 2015, 11:34:14 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Interest Post Security</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".chkDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{SecAccrIntPost.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Accrued Interest Post"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{SecAccrIntPost.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{SecAccrIntPost.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="secTypeGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="secType" styleClass="label" value="Security Type"/>
                        <h:selectOneListbox id="secTypeDD" styleClass="ddlist" size="1" style="width: 200px" value="#{SecAccrIntPost.secTpDd}">
                            <f:selectItems value="#{SecAccrIntPost.secTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblTillDate" styleClass="label" value="Till Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="chkDt" value="#{SecAccrIntPost.tilDate}" size="10" styleClass="input chkDt" disabled="#{SecAccrIntPost.disTillDt}">
                            <a4j:support action="#{SecAccrIntPost.onBlurTillDate}" event="onblur" reRender="message,tablePanel,debitAcnoGrid,creditAcnoGrid,chkDt,txtTotReceAmt" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="tillDateGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Control No"/>
                        <h:outputText id="stxtCtrlNo" styleClass="output" value="#{SecAccrIntPost.controlNo}"/>
                        <h:outputLabel id="lblTotReceAmt" styleClass="label" value="Interest Amount"/>
                        <h:inputText id="txtTotReceAmt" styleClass="input" value="#{SecAccrIntPost.txtReceivedAmount}" disabled="#{SecAccrIntPost.disRecAmt}">
                            <a4j:support event="onblur" focus="btnUpdate"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="debitAcnoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblDebitAcno" styleClass="label" value="Debit A/c No."></h:outputLabel>
                        <h:outputText id="stxtDebitAcno" styleClass="output" value="#{SecAccrIntPost.drAcno}"/>
                        <h:outputText id="stxtDebitAcnoDesc" styleClass="output" value="#{SecAccrIntPost.drHeadDesc}"/>
                        <h:outputLabel id="lblDebitAmt" styleClass="label" value="Total Debit Amount"></h:outputLabel>
                        <h:outputText id="stxtDebitAmt" styleClass="output" value="#{SecAccrIntPost.drAmt}"/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="creditAcnoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblCreditAcno" styleClass="label" value="Credit A/c No."></h:outputLabel>
                        <h:outputText id="stxtCreditAcno" styleClass="output" value="#{SecAccrIntPost.crAcno}"/>
                        <h:outputText id="stxtCreditAcnoDesc" styleClass="output" value="#{SecAccrIntPost.crHeadDesc}"/>
                        <h:outputLabel id="lblCreditAmt" styleClass="label" value="Total Credit Amount"></h:outputLabel>
                        <h:outputText id="stxtCreditAmt" styleClass="output" value="#{SecAccrIntPost.crAmt}"/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{SecAccrIntPost.tableList}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12"><h:outputText value="Control Number Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Control No."/></rich:column>
                                        <rich:column><h:outputText value="Security Desc"/></rich:column>
                                        <rich:column><h:outputText value="Settle Date"/></rich:column>
                                        <rich:column><h:outputText value="IP Date"/></rich:column>
                                        <rich:column><h:outputText value="YTM"/></rich:column>
                                        <rich:column><h:outputText value="Face Value"/></rich:column>
                                        <rich:column><h:outputText value="Book Value"/></rich:column>
                                        <rich:column><h:outputText value="Roi"/></rich:column>
                                        <rich:column><h:outputText value="No Of Days."/></rich:column>
                                        <rich:column><h:outputText value="Interest"/></rich:column>                                        
                                        <rich:column><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.ctrlNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.particulars}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.settleDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ipDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ytm}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.faceVal}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bookVal}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.period}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.intAmt}" /></rich:column>                                
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{SecAccrIntPost.setTableDataToForm}" reRender="message,maingrid,txtTotReceAmt" focus="txtTotReceAmt">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{SecAccrIntPost.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnUpdate" value="Update" oncomplete="#{rich:component('processUpdate')}.show();setMask();" style="display:#{SecAccrIntPost.btnHide};" disabled="#{SecAccrIntPost.disUpdate}" reRender="tillDateGrid,debitAcnoGrid,creditAcnoGrid,btnUpdate,processUpdate"/>
                            <a4j:commandButton id="btnPrint" value="Print" action="#{SecAccrIntPost.print}" oncomplete="if(#{SecAccrIntPost.calcFlag==true}){
                                               #{rich:component('popUpRepPanel')}.show();
                                               }
                                               else if(#{SecAccrIntPost.calcFlag==false})
                                               {
                                               #{rich:component('popUpRepPanel')}.hide();
                                               }" reRender="msggrid,debitAcnoGrid,creditAcnoGrid,popUpRepPanel,btnUpdate,btnPost"/>
                            <a4j:commandButton id="btnPost" value="Post" oncomplete="#{rich:component('processPanel')}.show();setMask();" disabled="#{SecAccrIntPost.disPost}" reRender="maingrid,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SecAccrIntPost.resetForm}" reRender="maingrid,processPanel,tablePanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SecAccrIntPost.exitBtnAction}" reRender="maingrid,processPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Interest Received Post FDR" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{SecAccrIntPost.viewID}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
            <rich:modalPanel id="processUpdate" autosized="true" width="250" onshow="#{rich:element('btnUNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to Update Record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnUYes" action="#{SecAccrIntPost.updateAction}" oncomplete="#{rich:component('processUpdate')}.hide();" reRender="tillDateGrid,debitAcnoGrid,creditAcnoGrid,btnUpdate,processUpdate,tablePanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnUNo" onclick="#{rich:component('processUpdate')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to post ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{SecAccrIntPost.postAction}" oncomplete="#{rich:component('processPanel')}.hide();" reRender="maingrid"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>