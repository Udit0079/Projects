<%--
Document   : EntryFrTxMast
Created on : Aug 31, 2010, 1:22:00 PM
Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>Tax Master</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="form3">
                <h:panelGrid id="mainPanel3" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPane60" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{EntryFrTxMast.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Entry For Tax Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{EntryFrTxMast.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="errorMsg5"   width="100%" style="height:30px;text-align:center;" styleClass="row2">
                        <h:outputText id="errMsg5" styleClass="error" value="#{EntryFrTxMast.message5}"/>
                    </h:panelGrid>


                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel49" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="OL31" styleClass="output" value="GL Head"><font color="red">*</font></h:outputLabel>
                            <h:inputText  id="stxtuser32" styleClass="input" value="#{EntryFrTxMast.txtGLHd}" maxlength="6" onkeyup="this.value = this.value.toUpperCase();" style="width:100px">
                                <a4j:support reRender="btnModify,stxtuser40,ddAcNo39,ddAcNo38,stxtuser37,stxtuser36,ddAcNo34,stxtDate,errMsg5,stxtuser32,message" event="onblur"  
                                             action="#{EntryFrTxMast.txtGlhdKeyDown}" focus="stxtuser36"/>
                            </h:inputText>
                            <h:outputLabel id="OL33" styleClass="output" value="Name Of Tax "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{EntryFrTxMast.lbTax}"/>
                        </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel63" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel id="OL34" styleClass="output" value="Applied On"/>
                        <h:selectOneListbox id="ddAcNo34" styleClass="ddlist" size="1" style="width: 60px" value="#{EntryFrTxMast.applyOnItem}">
                            <f:selectItems value="#{EntryFrTxMast.applyOn}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="OL35" styleClass="output" value="Applicable Date "/>
                        <rich:calendar  enableManualInput="true" id="calToDatenew" datePattern="dd/MM/yyyy" value="#{EntryFrTxMast.todayDt}" inputSize="10"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel65" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="OL36" styleClass="output" value="Rate of Tax(ROT%) "><font color="red">*</font></h:outputLabel>
                            <h:inputText id="stxtuser36" styleClass="input" value="#{EntryFrTxMast.txtROT}" style="width:100px"/>
                            <h:outputLabel id="OL37" styleClass="output" value="Minimum Amount(Rs)"><font color="red">*</font></h:outputLabel>
                            <h:inputText id="stxtuser37" styleClass="input" value="#{EntryFrTxMast.txtMinTax}" style="width:100px"/>
                        </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel67" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel id="OL38" styleClass="output" value="Round Off Upto "/>
                        <h:selectOneListbox id="ddAcNo38" styleClass="ddlist" size="1" style="width: 60px" value="#{EntryFrTxMast.listitem}">
                            <f:selectItems value="#{EntryFrTxMast.roundListOff}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="OL39" styleClass="output" value="Nature Of Tax "/>
                        <h:selectOneListbox id="ddAcNo39" styleClass="ddlist" size="1" style="width: 100px" value="#{EntryFrTxMast.taxNature}">
                            <f:selectItems value="#{EntryFrTxMast.taxNatureList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel69" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="OL40" styleClass="output" value="Maximum Amount(Rs.)"><font color="red">*</font></h:outputLabel>
                            <h:inputText id="stxtuser40" styleClass="input" value="#{EntryFrTxMast.txtMaxTax}" style="width:100px">
                                <a4j:support event="onblur" focus="btnModify"/>
                            </h:inputText>
                            <h:outputText id="errMsg51" value=""/>
                            <h:outputText id="errMsg52" value=""/>
                        </h:panelGrid>

                    <h:panelGrid columnClasses="col8" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:200px;">
                        <rich:dataTable  value="#{EntryFrTxMast.gridData}" var="dataItem"
                                         rowClasses="row1, row2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column width="250px"><h:outputText value="Name Of Tax"/></rich:column>
                                    <rich:column><h:outputText value="Applicable Date"/></rich:column>
                                    <rich:column><h:outputText value="GL Head"/></rich:column>
                                    <rich:column><h:outputText value="ROT"/></rich:column>
                                    <rich:column><h:outputText value="ROT Applied On"/></rich:column>
                                    <rich:column><h:outputText value="Min Tax"/></rich:column>
                                    <rich:column><h:outputText value="Max Tax"/></rich:column>
                                    <rich:column><h:outputText value="Apply"/></rich:column>
                                    <rich:column><h:outputText value="Update"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>

                            <rich:column><h:outputText value="#{dataItem.taxName}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.dateApplic}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.glHead}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.rot}"/></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.rotApplOn}"/></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.minTaxe}"/></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.maxTaxe}"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.app}"/></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink  ajaxSingle="true" id="selectlink"  reRender="taskList" onclick="#{rich:component('showPanelForGrid')}.show();">
                                    <h:graphicImage   id="imagerender"  value="/resources/images/passed.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{EntryFrTxMast.authorized}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{EntryFrTxMast.currentRow}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="20"/>
                        <rich:modalPanel id="showPanelForGrid" autosized="true" width="200">
                            <f:facet name="header">
                                <h:outputText value="Are u sure to update the record ?" style="padding-right:15px;" />
                            </f:facet>
                            <f:facet name="controls">
                                <h:panelGroup>
                                    <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink1" />
                                    <rich:componentControl for="showPanelForGrid" attachTo="hidelink1" operation="hide" event="onclick" />
                                </h:panelGroup>
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" ajaxSingle="true" action="#{EntryFrTxMast.setRowValues}"
                                                                   onclick="#{rich:component('showPanelForGrid')}.hide();" reRender="taskList,errMsg5"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" onclick="#{rich:component('showPanelForGrid')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid  id="messagepanelgrid"   width="100%" style="height:1px;" styleClass="error">
                        <h:outputText id="message" value="#{EntryFrTxMast.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:outputLabel id="OL45" styleClass="output" value="To Not Apply Any Tax Click On That Update check , If apply is y"/>
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton reRender="btnModify,stxtuser40,ddAcNo39,ddAcNo38,stxtuser37,stxtuser36,ddAcNo34,stxtDate,stxtuser32,errMsg5,message,gridPanel103,taskList" id="btnModify"  
                                               disabled="#{EntryFrTxMast.saveValue}" value="Save" action="#{EntryFrTxMast.save}"/>
                            <a4j:commandButton id="btnRefresh1"  value="Refresh" action="#{EntryFrTxMast.refresh}"  
                                               reRender="btnModify,stxtuser40,ddAcNo39,ddAcNo38,stxtuser37,stxtuser36,ddAcNo34,stxtDate,stxtuser32,errMsg5,message,gridPanel103,taskList"/>
                            <a4j:commandButton id="btnRefresh"  value="Exit" action="#{EntryFrTxMast.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>







