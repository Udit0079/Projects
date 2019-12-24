/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2006 JasperSoft Corporation http://www.jaspersoft.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 303 Second Street, Suite 450 North
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */
package net.sf.jasperreports.charts.xml;

import net.sf.jasperreports.charts.util.JRMeterInterval;
import net.sf.jasperreports.engine.xml.JRBaseFactory;
import net.sf.jasperreports.engine.xml.JRXmlConstants;

import org.xml.sax.Attributes;


/**
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 * @version $Id: JRMeterIntervalFactory.java 1387 2006-09-05 21:33:40Z bklawans $
 */
public class JRMeterIntervalFactory extends JRBaseFactory
{
    private static final String ATTRIBUTE_label = "label";
    private static final String ATTRIBUTE_color = "color";
    private static final String ATTRIBUTE_alpha = "alpha";
    
    /**
     *
     */
    public Object createObject(Attributes atts)
    {
        JRMeterInterval interval = new JRMeterInterval();
        
        String value = atts.getValue(ATTRIBUTE_label);
        if (value != null && value.length() > 0)
        {
            interval.setLabel(value);
        }
        
        value = atts.getValue(ATTRIBUTE_color);
        if (value != null && value.length() > 0)
        {
            interval.setBackgroundColor(JRXmlConstants.getColor(value, null));
        }
        
        value = atts.getValue(ATTRIBUTE_alpha);
        if (value != null && value.length() > 0)
        {
            interval.setAlpha(Double.valueOf(value).doubleValue());
        }
        
        return interval;
    }
}
