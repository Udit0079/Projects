/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.utils;

public class AmtToWords
{

    public static String total = "";

    public AmtToWords()
    {
        
    }
    public static String calculate(double d)
    {
        if(d==0.0)
        {
            return "zero Rupees ";
        }
        total = "Rupees  ";
        if(d > 0.0D)
        {
            double d1 = d % 1.0D;
            f3((long)d);
            if(d != 0.0D && (long)d > 0L)
            {
                total = (new StringBuilder()).append(total).append("").toString();
            }
            if(d1 != 0.0D)
            {
                total+=" and ";
                double d2 = d1 * 100D;
                f2(Math.round(d2));
                total = (new StringBuilder()).append(total).append("Paisa ").toString();
            }
            total = (new StringBuilder()).append(total).append("Only ").toString();
        }
        return total;
    }

    public static void f3(long l)
    {
        if(l >= 0x3b9aca00L)
        {
            int i = (int)(l / 0x3b9aca00L);
            if(i >= 100)
            {
                f3(i);
            } else
            {
                f2(i - i % 1);
            }
            if(i > 0)
            {
                total = (new StringBuilder()).append(total).append("Arab ").toString();
            }
            l %= 0x3b9aca00L;
        }
        if(l >= 0x989680L)
        {
            double d = l / 0x989680L;
            f2((long)d);
            if(d > 0.0D)
            {
                total = (new StringBuilder()).append(total).append("Crore ").toString();
            }
            l %= 0x989680L;
        }
        if(l >= 0x186a0L)
        {
            double d1 = l / 0x186a0L;
            f2((long)d1);
            if(d1 > 0.0D)
            {
                total = (new StringBuilder()).append(total).append("Lakh ").toString();
            }
            l %= 0x186a0L;
        }
        if(l >= 1000L)
        {
            double d2 = l / 1000L;
            f2((long)d2);
            if(d2 > 0.0D)
            {
                total = (new StringBuilder()).append(total).append("Thousand ").toString();
            }
            l %= 1000L;
        }
        if(l >= 100L)
        {
            double d3 = l / 100L;
            f2((long)d3);
            if(d3 > 0.0D)
            {
                total = (new StringBuilder()).append(total).append("Hundred ").toString();
            }
            l %= 100L;
        }
        if(l > 0L)
        {
            f2(l / 1L);
        }
    }

    public static void f2(long l)
    {
        if(l >= 90L)
        {
            total = (new StringBuilder()).append(total).append("Ninety ").toString();
            f1(l % 90L);
        } else
        if(l >= 80L)
        {
            total = (new StringBuilder()).append(total).append("Eighty ").toString();
            f1(l % 80L);
        } else
        if(l >= 70L)
        {
            total = (new StringBuilder()).append(total).append("Seventy ").toString();
            f1(l % 70L);
        } else
        if(l >= 60L)
        {
            total = (new StringBuilder()).append(total).append("Sixty ").toString();
            f1(l % 60L);
        } else
        if(l >= 50L)
        {
            total = (new StringBuilder()).append(total).append("Fifty ").toString();
            f1(l % 50L);
        } else
        if(l >= 40L)
        {
            total = (new StringBuilder()).append(total).append("Forty ").toString();
            f1(l % 40L);
        } else
        if(l >= 30L)
        {
            total = (new StringBuilder()).append(total).append("Thirty ").toString();
            f1(l % 30L);
        } else
        if(l > 20L)
        {
            total = (new StringBuilder()).append(total).append("Twenty ").toString();
            f1(l % 20L);
        } else
        {
            f1(l);
        }
    }

    public static void f1(long l)
    {
        switch((int)l)
        {
        case 1: // '\001'
            total = (new StringBuilder()).append(total).append("One ").toString();
            break;

        case 2: // '\002'
            total = (new StringBuilder()).append(total).append("Two ").toString();
            break;

        case 3: // '\003'
            total = (new StringBuilder()).append(total).append("Three ").toString();
            break;

        case 4: // '\004'
            total = (new StringBuilder()).append(total).append("Four ").toString();
            break;

        case 5: // '\005'
            total = (new StringBuilder()).append(total).append("Five ").toString();
            break;

        case 6: // '\006'
            total = (new StringBuilder()).append(total).append("Six ").toString();
            break;

        case 7: // '\007'
            total = (new StringBuilder()).append(total).append("Seven ").toString();
            break;

        case 8: // '\b'
            total = (new StringBuilder()).append(total).append("Eight ").toString();
            break;

        case 9: // '\t'
            total = (new StringBuilder()).append(total).append("Nine ").toString();
            break;

        case 10: // '\n'
            total = (new StringBuilder()).append(total).append("Ten ").toString();
            break;

        case 11: // '\013'
            total = (new StringBuilder()).append(total).append("Eleven ").toString();
            break;

        case 12: // '\f'
            total = (new StringBuilder()).append(total).append("Twelve ").toString();
            break;

        case 13: // '\r'
            total = (new StringBuilder()).append(total).append("Thirteen ").toString();
            break;

        case 14: // '\016'
            total = (new StringBuilder()).append(total).append("Fourteen ").toString();
            break;

        case 15: // '\017'
            total = (new StringBuilder()).append(total).append("Fifteen ").toString();
            break;

        case 16: // '\020'
            total = (new StringBuilder()).append(total).append("Sixteen ").toString();
            break;

        case 17: // '\021'
            total = (new StringBuilder()).append(total).append("Seventeen ").toString();
            break;

        case 18: // '\022'
            total = (new StringBuilder()).append(total).append("Eighteen ").toString();
            break;

        case 19: // '\023'
            total = (new StringBuilder()).append(total).append("Nineteen ").toString();
            break;

        case 20: // '\024'
            total = (new StringBuilder()).append(total).append("Twenty ").toString();
            break;
        }
    }

}
