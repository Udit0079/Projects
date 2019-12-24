<%-- 
    Document   : UpdatePlMaster
    Created on : Nov 1, 2010, 11:13:06 AM
    Author     : Sudhir kr Bisht
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Update Pl master</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="HoExtractForm">
                <h:panelGrid id="HoextractPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HoextractPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="HoextractPanel2" layout="block">
                            <h:outputLabel id="HoextractPanel3" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="HoextractPanel4" styleClass="output" value=" #{HoExtractEntry.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="HoextractPanel5" styleClass="headerLabel" value="Update Pl Master"/>
                        <h:panelGroup id="HoextractPanel6" layout="block">
                            <h:outputLabel id="HoextractPanel7" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="HoextractPanel8" styleClass="output" value="#{HoExtractEntry.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="labelgrid"   width="100%"  styleClass="row2">
                        <h:outputText id="outputlabel"  value="You  can  update  the  Group  description  and  sub  group  description  through  this  form"  styleClass="label"/>
                    </h:panelGrid>
                    <h:panelGrid  id="errorMsg11"   width="100%"   style="height:1px;" styleClass="error">
                        <h:outputText id="errMsg11"  value="#{UpdatePlMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="newpanel" style="text-align:left;" width="100%" styleClass="row1">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel58" style="text-align:left;" width="100%">
                            <h:outputLabel id="HoextractPanel13" styleClass="output" value="Group Code"/>
                            <h:panelGroup>
                                <h:selectOneListbox id="nwHoextractPanel13" styleClass="ddlist" size="1" style="width:120px" value="#{UpdatePlMaster.grpitem}">
                                    <f:selectItems value="#{UpdatePlMaster.grpCodeList}"/>
                                    <a4j:support reRender="nwHoextractPanel14,errMsg11"  event="onblur" action="#{UpdatePlMaster.getsubgrpcode}"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="HoextractPanel57" style="text-align:left;" width="100%">
                            <h:outputLabel id="HoextractPanel15" styleClass="output" value="Sub Group Code "/>
                            <h:panelGroup>
                                <h:selectOneListbox  id="nwHoextractPanel14" styleClass="ddlist" size="1" style="width:120px"  value="#{UpdatePlMaster.subgrpitem}">
                                    <f:selectItems  value="#{UpdatePlMaster.subGrpCodeList}"/>
                                    <a4j:support event="onblur" reRender="nwHoextractPanel16,errMsg11" action="#{UpdatePlMaster.getGroupDesc}"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="newexpanel5" style="text-align:left;" width="100%" styleClass="row2">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel59" style="text-align:left;" width="100%">
                            <h:outputLabel id="HoextractPanel37" styleClass="output" value="Group Desc"/>
                            <h:panelGroup>
                                <h:selectOneListbox id="nwHoextractPanel16" styleClass="ddlist" size="1" style="width:120px" value="#{UpdatePlMaster.grpDescItem}">
                                    <f:selectItems value="#{UpdatePlMaster.grpDesc}"/>
                                    <a4j:support  ajaxSingle="true" event="onblur" oncomplete="#{rich:component('showPanelForGrid2')}.show();"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel46" style="text-align:left;" width="100%">
                            <h:outputLabel id="HoextractPanel39" styleClass="output" value="Sub GL Code"/>
                            <h:panelGroup>
                                <h:selectOneListbox  disabled="#{UpdatePlMaster.listsubglcode}" id="newHoextractPanel15" styleClass="ddlist" size="1" style="width:120px" value="#{UpdatePlMaster.subGlCodeItem}">
                                    <f:selectItems  value="#{UpdatePlMaster.subglcode}"/>
                                    <a4j:support  ajaxSingle="true" event="onblur" reRender="input1,errMsg11,update" action="#{UpdatePlMaster.subgrpdesc}"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel5914" style="text-align:left;" width="100%" styleClass="row1">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel5913" style="text-align:left;" width="100%">
                            <h:outputLabel id="HoextractPanel3713" styleClass="output" value="New Group Desc"/>
                            <h:inputText  disabled="#{UpdatePlMaster.newValueGrpDesc}" id="input2" styleClass="input" value="#{UpdatePlMaster.textgrpdesc}">
                                <f:validateLength maximum="60" minimum="3"/>
                                <rich:ajaxValidator event="onblur"/>
                                <rich:message  for="input2"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel591" style="text-align:left;" width="100%">
                            <h:outputLabel id="HoextractPanel371" styleClass="output" value="Sub Group Desc"/>
                            <h:inputText   disabled="#{UpdatePlMaster.valueSubDesc}" id="input1" styleClass="input" value="#{UpdatePlMaster.textvalue1}">
                                <f:validateLength maximum="60" minimum="3"/>
                                <rich:ajaxValidator event="onblur"/>
                                <rich:message  for="input1"/>
                            </h:inputText>
                        </h:panelGrid>
                    </h:panelGrid>
                    <rich:modalPanel id="showPanelForGrid" autosized="true" width="200">
                        <f:facet name="header">
                            <h:outputText value="Are you sure u want to update record ?" style="padding-right:15px;" />
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
                                            <a4j:commandButton value="Yes" ajaxSingle="true" action="#{UpdatePlMaster.update}"
                                                               onclick="#{rich:component('showPanelForGrid')}.hide();" reRender="errMsg11"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" onclick="#{rich:component('showPanelForGrid')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <rich:modalPanel id="showPanelForGrid2" autosized="true" width="200">
                        <f:facet name="header">
                            <h:outputText value="Press Yes To update Group Description No For SubGroup Description Else Cancel" style="padding-right:15px;" />
                        </f:facet>
                        <f:facet name="controls">
                            <h:panelGroup>
                                <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink2" />
                                <rich:componentControl for="showPanelForGrid" attachTo="hidelink2" operation="hide" event="onclick" />
                            </h:panelGroup>
                        </f:facet>
                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="33%">
                                            <a4j:commandButton value="Yes" ajaxSingle="true" action="#{UpdatePlMaster.changegrpdesc}"
                                                               onclick="#{rich:component('showPanelForGrid2')}.hide();" reRender="errMsg11,newHoextractPanel15,input1,input2,update"/>
                                        </td>
                                        <td align="center" width="33%">
                                            <a4j:commandButton value="No" ajaxSingle="true" action="#{UpdatePlMaster.subGlCode}"
                                                               onclick="#{rich:component('showPanelForGrid2')}.hide();" reRender="newHoextractPanel15,errMsg11,input1,input2,update"/>
                                        </td>

                                        <td align="center" width="34%">
                                            <a4j:commandButton value="Cancel" onclick="#{rich:component('showPanelForGrid2')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>                                        
                    <h:panelGrid id="footerPanel21" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel21">
                            <a4j:commandButton disabled="#{UpdatePlMaster.update}" id="update"  reRender="errMsg11"  oncomplete="#{rich:component('showPanelForGrid')}.show();" value="Update"/>
                            <a4j:commandButton  id="extractRefresh" value="Refresh" action="#{UpdatePlMaster.refresh()}"  reRender="update,errMsg11,newHoextractPanel15,nwHoextractPanel16,nwHoextractPanel14,nwHoextractPanel13,input1,input2"/>
                            <a4j:commandButton  id="extractExit" value=" Exit" action="#{UpdatePlMaster.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>


