<%-- 
    Document   : venderDetails
    Created on : 6 Dec, 2018, 11:10:32 AM
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
            <title>Vender Details</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{VenderDetailBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Vender Details"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{VenderDetailBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="message" styleClass="msg" value="#{VenderDetailBean.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridpanel0" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:165px;" value="#{VenderDetailBean.function}">
                                    <f:selectItems value="#{VenderDetailBean.functionList}"/>
                             <a4j:support action="#{VenderDetailBean.funtionAction}" event="onblur" oncomplete="setMask();"
                                                 reRender="PanelGrid2,lblGstInforMod,txtGstInforMod,a19,btnSave"/>
                                </h:selectOneListbox>
                             <h:outputLabel id="lblGstInforMod" styleClass="label" value="Gst No." style="display:#{VenderDetailBean.disGst}" ><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtGstInforMod" styleClass="input" value="#{VenderDetailBean.gstNo}" maxlength="15" style="width:120px;display:#{VenderDetailBean.disGst}"> 
                                    <a4j:support action="#{VenderDetailBean.gridLoadAction}" event="onblur" oncomplete="setMask();"
                                                 reRender="a19,taskList,message,btnSave"/>
                                </h:inputText>
                            <h:outputLabel></h:outputLabel>
                            <h:outputText></h:outputText>
                            </h:panelGrid>
                     <h:panelGrid id="PanelGrid2" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;display:#{VenderDetailBean.displayPanel}" styleClass="row2" width="100%">
                    <h:outputLabel id="lblName" styleClass="label" value="Name" ><font class="required" style="color:red;">*</font></h:outputLabel>
                 <h:inputText id="txtName" styleClass="input"  onkeydown="this.value=this.value.toUpperCase();" value="#{VenderDetailBean.name}" maxlength="50" style="width:120px;"/> 
                 <h:outputLabel id="lblGstIn" styleClass="label" value="Gst No." ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtGstIn" styleClass="input" value="#{VenderDetailBean.gstIn}" disabled="#{VenderDetailBean.gstFlag}"   maxlength="15" style="width:120px;"> 
                             <a4j:support event="onblur" action="#{VenderDetailBean.isgstexits}" reRender = "a19,txtGstIn,message"/>  
                            </h:inputText> 
                  <h:outputLabel id="lblPrState" styleClass="label" value="State"><font class="required" style="color:red;">*</font></h:outputLabel>
                  <h:selectOneListbox value="#{VenderDetailBean.state}" id="ddPrState" style="width:120px;" styleClass="ddlist"  size="1">
                                <f:selectItems value="#{VenderDetailBean.stateOption}"/>
                                <a4j:support event="onblur" action="#{VenderDetailBean.stateSelectAction}" reRender = "a19,taskList,message"/>
                            </h:selectOneListbox>
                </h:panelGrid>     
              <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row1" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{VenderDetailBean.gridDetail}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList" rows="7" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="5"><h:outputText value="Vender Details" /></rich:column>
                                            <rich:column breakBefore="true" width="200px;"><h:outputText value="Name" /></rich:column>
                                            <rich:column><h:outputText value="State" /></rich:column>
                                            <rich:column><h:outputText value="Gst No." /></rich:column>
                                            <rich:column rendered="#{VenderDetailBean.selectRender}"><h:outputText value="Select"/></rich:column>
                                            <rich:column rendered="#{VenderDetailBean.deleteRender}"><h:outputText value="Delete"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.name}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.stateDes}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.gstno}" /></rich:column>
                                  <rich:column style="text-align:center;width:40px" rendered="#{VenderDetailBean.selectRender}">
                                <a4j:commandLink ajaxSingle="true" id="selectLink" action="#{VenderDetailBean.setRowValues}" reRender="txtName,PanelGrid2,txtGstIn,ddPrState,lblGstInforMod,txtGstInforMod" oncomplete="setMask();">
                                    <h:graphicImage value="/resources/images/select.gif" style="border:0" width="15" height="15"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{VenderDetailBean.currentItem}"/>
                                </a4j:commandLink>
                                 </rich:column>
                                    <rich:column style="text-align:center;width:40px" rendered="#{VenderDetailBean.deleteRender}">
                                <a4j:commandLink ajaxSingle="true" id="deleteLink" action="#{VenderDetailBean.deleteAction}" reRender="taskList" oncomplete="setMask();">
                                    <h:graphicImage value="/resources/images/error.gif" style="border:0" width="15" height="15"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{VenderDetailBean.currentItem}"/>
                                </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" tabindex="12" value="#{VenderDetailBean.btnValue}" oncomplete="#{rich:component('savePanel')}.show()" reRender="message,a19,taskList"/>
                                <a4j:commandButton id="btnRefresh" tabindex="13" ajaxSingle="true" value="Refresh" action="#{VenderDetailBean.resetForm}"
                                                   reRender="taskList,message,lpg,a19,ddFunction,txtAAdharNo,btnSave,txtGstInforMod,PanelGrid2" oncomplete="setMask();">
                                </a4j:commandButton>
                                <a4j:commandButton id="btnExit" tabindex="14" ajaxSingle="true" value="Exit" action="#{VenderDetailBean.exitForm}" reRender="taskList,message,lpg,a19"/>
                            </h:panelGroup>
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
                                    <a4j:commandButton value="Yes" id="save" ajaxSingle="true" action="#{VenderDetailBean.postDetail}" oncomplete="#{rich:component('savePanel')}.hide();return false;"
                                                       reRender="message,gridpanel0,PanelGrid2,a19" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
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

