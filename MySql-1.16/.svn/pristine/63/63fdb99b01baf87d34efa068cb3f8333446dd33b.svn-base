<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Archiving Detail</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{Archieving.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Archiving Detail"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{Archieving.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="mainPanel1" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{Archieving.message}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel1" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblFuntion" styleClass="label" value="Mode" style="display:"/>
                        <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:90px;" value="#{Archieving.function}" >
                            <f:selectItems value="#{Archieving.functionList}"/>
                            <a4j:support event="onblur" oncomplete="if(#{rich:element('ddFunction')}.value=='IM'){#{rich:element('txtAcNo')}.focus();}
                                         else if((#{rich:element('ddFunction')}.value=='IL') || (#{rich:element('ddFunction')}.value=='CT')){#{rich:element('txtAcNo')}.focus();}"
                                         reRender="lblMsg,txtAcNo"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="labelAcNo" styleClass="label" value="Account No"/>
                        <h:panelGroup id = "rid">
                            <h:inputText id="txtAcNo" styleClass="input" value="#{Archieving.acNo}" maxlength="#{Archieving.acNoMaxLen}"  size="15">
                                <a4j:support  event="onblur" action="#{Archieving.gridload}" reRender="lblMsg,taskList,tablePanel,lblNewAcno,lblOldAcno,"/>
                            </h:inputText>
                            <h:outputLabel id="lblNewAcno" styleClass="label" value="#{Archieving.newAcno}" style="color:green"></h:outputLabel>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{Archieving.gridDetail}" var="dataItem" 
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="4" 
                                            columnsWidth="100" rowKeyVar="row" 
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" 
                                            width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="14"><h:outputText value="Archiving Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="UMRN" /></rich:column>
                                        <rich:column><h:outputText value="A/c Type" /></rich:column>
                                        <rich:column><h:outputText value="A/c No" /></rich:column>
                                        <rich:column><h:outputText value="A/c Name" /></rich:column>
                                        <rich:column><h:outputText value="Frequency" /></rich:column>
                                        <rich:column><h:outputText value="First Collection Date" /></rich:column>
                                        <rich:column><h:outputText value="Final Collection Date" /></rich:column>
                                        <rich:column><h:outputText value="Collection Amount" /></rich:column>
                                        <rich:column><h:outputText value="Maximum Amount" /></rich:column>
                                        <rich:column><h:outputText value="Debtor IFSC/MICR/IIN" /></rich:column>
                                        <rich:column><h:outputText value="Debtor Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Creditor Name" /></rich:column>
                                        <%--<rich:column><h:outputText value="Mandate Status" /></rich:column>--%>
                                        <rich:column><h:outputText value="Mandate Mode"/></rich:column>
                                        <%--<rich:column><h:outputText value="Accept Status" /></rich:column>
                                        <rich:column><h:outputText value="Reject Reason" /></rich:column>--%>
                                        <rich:column><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.mndtId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrAcctTpPrtry}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrAcctIdOthrId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrNm}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ocrncsFrqcy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ocrncsFrstColltnDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ocrncsFnlColltnDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.colltnAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.maxAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrAgtFinInstnIdClrSysMmbIdMmbId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrAgtFinInstnIdNm}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.cdtrNm}" /></rich:column>
                                <%--<rich:column><h:outputText value="#{dataItem.mandateStatus}" /></rich:column>--%>
                                <rich:column><h:outputText value="#{dataItem.mandateModeDescription}" /></rich:column>
                                <%--<rich:column><h:outputText value="#{dataItem.accept}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rejectCode}" /></rich:column>--%>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{Archieving.setTableDataToForm}" reRender="stxtMsg,txtAcno,image,imageGrid,chqImage,lblOldAcno" focus="txtAcno">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{Archieving.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" columnClasses="col19,col10,col19" columns="3" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel1" styleClass="vtop">
                            <h:outputText id="stxtInstruction1" styleClass="output" value="F8-White Image, F9-Gray Image" style="color:blue;text-align:left;"/>
                        </h:panelGroup>
                        <h:panelGroup  id="btnPanel2" style="width:100%;text-align:Right;" styleClass="vtop">
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{Archieving.refreshForm}"  reRender="mainPanel,txtAcNo,labalNewAcNo,labelOldAcNo"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{Archieving.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                        <h:panelGroup id="btnPanel3" styleClass="vtop"/>
                    </h:panelGrid>
                    <h:panelGrid  id="image" columns="2" columnClasses="col11,col10" width="100%" style="height:270px;vertical-align:top;" styleClass="row1">
                        <h:panelGrid id="imageGrid" columns="1" style="width:100%;text-align:left;" columnClasses="vtop">
                            <h:graphicImage id="chqImage" value="/mms/dynamic/?file=#{Archieving.imageName}&fileNo=#{Archieving.seqNo}&mandate=#{Archieving.mmsType}&fileUpDt=#{Archieving.fileUpDt}&mmsMode=#{Archieving.mmsMode}" height="350" width="650"/>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:jsFunction name="showWhilteImage" action="#{Archieving.getWhiteImage}" reRender="chqImage,lblMsg" />
                <a4j:jsFunction name="showGrayImage" action="#{Archieving.getGrayImage}" reRender="chqImage,lblMsg" />
                <rich:hotKey key="F8" handler="showWhilteImage();"/>
                <rich:hotKey key="F9" handler="showGrayImage();"/>
            </a4j:form>
        </body>
    </html>
</f:view>
