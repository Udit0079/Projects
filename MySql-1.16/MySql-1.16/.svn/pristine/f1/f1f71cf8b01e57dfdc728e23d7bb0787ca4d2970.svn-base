<%-- 
    Document   : venderInvoiceDetail
    Created on : 7 Dec, 2018, 11:03:49 AM
    Author     : root
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
            <title>Vender Invoice Details</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("99/99/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{VenderInvoiceBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Vender Invoice Details"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{VenderInvoiceBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="message" styleClass="msg" value="#{VenderInvoiceBean.message}"/>
                    </h:panelGrid>
                   <h:panelGrid id="PanelGrid1" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;" styleClass="row1" width="100%">
                    <h:outputLabel id="lblFuntion" styleClass="label" value="Function" ><font class="required" style="color:red;">*</font></h:outputLabel>
                          <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:165px;" value="#{VenderInvoiceBean.function}">
                                    <f:selectItems value="#{VenderInvoiceBean.functionList}"/>
                             <a4j:support action="#{VenderInvoiceBean.funtionAction}" event="onblur" oncomplete="setMask();"
                                                 reRender="PanelGrid2,a19,btnSave,taskList,message"/>
                                </h:selectOneListbox> 
                       <h:outputLabel id="lblGstInforMod" styleClass="label" value="Gst No." ><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtGstInforMod" styleClass="input" value="#{VenderInvoiceBean.gstNo}" maxlength="15" style="width:120px;"> 
                                    <a4j:support action="#{VenderInvoiceBean.gstAction}" event="onblur" oncomplete="setMask();"
                                                 reRender="stxtName,stxtState,message"/>
                                </h:inputText>
                         <h:outputLabel id="labelName" styleClass="label" value="Vender Name"/>
                         <h:outputText id="stxtName" styleClass="output" value="#{VenderInvoiceBean.venderName}"/>
                         
                        </h:panelGrid>
                     <h:panelGrid id="PanelGrid2" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;" styleClass="row2" width="100%">
                    <h:outputLabel id="lblState" styleClass="label" value="State"/>
                    <h:outputText id="stxtState" styleClass="output" value="#{VenderInvoiceBean.state}"/>
                     <h:outputLabel id="lblInvoice" styleClass="label" value="Invoice No." ><font class="required" style="color:red;">*</font></h:outputLabel>
                    <h:inputText id="txtInvoice" styleClass="input"  value="#{VenderInvoiceBean.invoiceNo}" maxlength="50" style="width:120px;"/> 
                     <h:outputLabel id="lblInvoiceAmt" styleClass="label" value="Invoice Amt" ><font class="required" style="color:red;">*</font></h:outputLabel>
                     <h:inputText id="txtInvoiceAmt" styleClass="input"  value="#{VenderInvoiceBean.invoiceAmt}" maxlength="15" style="width:100px;">
                     <a4j:support action="#{VenderInvoiceBean.invoiceAmtAction}" event="onblur" oncomplete="setMask();"
                                                 reRender="message,stxtCgst,stxtSgst,stxtIgst"/>
                     </h:inputText>
                 </h:panelGrid> 
                    <h:panelGrid id="PanelGrid3" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;" styleClass="row1" width="100%">
                    
                    <h:outputLabel id="lblcgst" styleClass="label" value="CGST" ><font class="required" style="color:red;">*</font></h:outputLabel>
                     <h:outputText id="stxtCgst" styleClass="output" value="#{VenderInvoiceBean.cgst}"/>
                     <h:outputLabel id="lblsgst" styleClass="label" value="SGST" ><font class="required" style="color:red;">*</font></h:outputLabel>
                     <h:outputText id="stxtSgst" styleClass="output" value="#{VenderInvoiceBean.sgst}"/>
                     <h:outputLabel id="lblIgst" styleClass="label" value="IGST" ><font class="required" style="color:red;">*</font></h:outputLabel>
                     <h:outputText id="stxtIgst" styleClass="output" value="#{VenderInvoiceBean.igst}"/>
                </h:panelGrid> 
            <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" tabindex="12" value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="message,a19,taskList"/>
                                <a4j:commandButton id="btnRefresh" tabindex="13" ajaxSingle="true" value="Refresh" action="#{VenderInvoiceBean.resetForm}"
                                                   reRender="taskList,message,lpg,a19,ddFunction,txtAAdharNo,btnSave,txtGstInforMod,PanelGrid2,a1" oncomplete="setMask();">
                                </a4j:commandButton>
                                <a4j:commandButton id="btnExit" tabindex="14" ajaxSingle="true" value="Exit" action="#{VenderInvoiceBean.exitForm}" reRender="taskList,message,lpg,a19"/>
                            </h:panelGroup>
                        </h:panelGrid>
                     <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;display:#{VenderInvoiceBean.disPlayGrid}" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{VenderInvoiceBean.gridDetail}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList" rows="7" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="10"><h:outputText value="Vender Invoice Authorization Details" /></rich:column>
                                            <rich:column breakBefore="true" width="200px;"><h:outputText value="Name" /></rich:column>
                                            <rich:column><h:outputText value="State" /></rich:column>
                                            <rich:column><h:outputText value="Gst No." /></rich:column>
                                            <rich:column><h:outputText value="Invoice No." /></rich:column>
                                            <rich:column><h:outputText value="Invoice Amt" /></rich:column>
                                            <rich:column><h:outputText value="CGST Amt" /></rich:column>
                                            <rich:column><h:outputText value="SGST Amt" /></rich:column>
                                            <rich:column><h:outputText value="IGST Amt" /></rich:column>
                                            <rich:column><h:outputText value="Authorize"/></rich:column>
                                            <rich:column><h:outputText value="Delete"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.name}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.stateDes}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.gstno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.invoiceNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.invoiceAmt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.cgst}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.sgst}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.igst}" /></rich:column>
                                  <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink ajaxSingle="true" id="selectLink" oncomplete="#{rich:component('authPanel')}.show()"  reRender="message">
                                    <h:graphicImage value="/resources/images/select.gif" style="border:0" width="15" height="15"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{VenderInvoiceBean.currentItem}"/>
                                </a4j:commandLink>
                                 </rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink ajaxSingle="true" id="deleteLink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="message">
                                    <h:graphicImage value="/resources/images/error.gif" style="border:0" width="15" height="15"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{VenderInvoiceBean.currentItem}"/>
                                </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
                <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('save')}.focus()">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog"/>
                    </f:facet>
                    <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Process It ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="save" ajaxSingle="true" action="#{VenderInvoiceBean.postDetail}" oncomplete="#{rich:component('savePanel')}.hide();return false;"
                                                       reRender="message,gridpanel0,PanelGrid2,a19,a1" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="authPanel" autosized="true" width="250" onshow="#{rich:element('auth')}.focus()">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog"/>
                    </f:facet>
                    <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To authorize It ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="auth" ajaxSingle="true" action="#{VenderInvoiceBean.authAction}" oncomplete="#{rich:component('authPanel')}.hide();return false;"
                                                       reRender="message,taskList,gridpanel0,PanelGrid2,a19,a1" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('authPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('delete')}.focus()">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog"/>
                    </f:facet>
                    <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To delete It ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="delete" ajaxSingle="true" action="#{VenderInvoiceBean.deleteAction}" oncomplete="#{rich:component('deletePanel')}.hide();return false;"
                                                       reRender="message,gridpanel0,PanelGrid2,a19,a1" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>           
            <a4j:status onstart="#{rich:component('waitPost')}.show()" onstop="#{rich:component('waitPost')}.hide()"/>
            <rich:modalPanel id="waitPost" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>                
        </body>
    </html>
</f:view>
