package com.cbs.utils;

public class Spellar {

    public final String INVALID_INPUT_GIVEN = "Invalid input given";

    public final String[] ones = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

    public final String[] tens = {
        "", // 0
        "", // 1
        "Twenty", // 2
        "Thirty", // 3
        "Forty", // 4
        "Fifty", // 5
        "Sixty", // 6
        "Seventy", // 7
        "Eighty", // 8
        "Ninety" // 9
    };

   // public String total;

    public Spellar() {
       // total = "";
    }

    public String spellAmount(double money) {
        long rupee = (long) money;
        long paisa = Math.round((money - rupee) * 100);
        if (money == 0D) {
            return "";
        }
        if (money < 0) {
            return INVALID_INPUT_GIVEN;
        }
        String rupeePart = "";
        if (rupee > 0) {
            rupeePart = "Rupee" + (rupee == 1 ? " " : "s ") + convert(rupee);
        }
        String paisaPart = "";
        if (paisa > 0) {
            if (rupeePart.length() > 0) {
                paisaPart = " and ";
            }
            paisaPart += convert(paisa) + " Paisa";
        }
        return rupeePart + paisaPart + " Only";
    }

    private String convert(final long n) {
        if (n < 0) {
            return INVALID_INPUT_GIVEN;
        }
        if (n < 20) {
            return ones[(int) n];
        }
        if (n < 100) {
            return tens[(int) n / 10] + ((n % 10 != 0) ? " " : "") + ones[(int) n % 10];
        }
        if (n < 1000) {
            return ones[(int) n / 100] + " Hundred" + ((n % 100 != 0) ? " " : "") + convert(n % 100);
        }
        if (n < 100000) {
            return convert(n / 1000) + " Thousand" + ((n % 1000 != 0) ? " " : "") + convert(n % 1000);
        }
        if (n < 10000000) {
            return convert(n / 100000) + " Lakh" + ((n % 100000 != 0) ? " " : "") + convert(n % 100000);
        }
        if (n < 1000000000) {
            return convert(n / 10000000) + " Crore" + ((n % 10000000 != 0) ? " " : "") + convert(n % 10000000);
        }
        return convert(n / 1000000000) + " Arab" + ((n % 1000000000 != 0) ? " " : "") + convert(n % 1000000000);
    }

//    public String spellAmount(double money) {
//        total = "Rupees ";
//        if (money == 0.0D) {
//            total = (new StringBuilder()).append(total).append("Zero Only").toString();
//            return total;
//        }
//        if (money > 0.0) {
//            long rupees = (long) money;
//            long paisa = Math.round((money - rupees) * 100);
//            //double d1 = round(money % 1.0D, 2);
//            cat3(rupees);
//            if (rupees != 0) {
//                if (rupees > 0L) {
//                    total = (new StringBuilder()).append(total).append("").toString();
//                } else if (rupees == 0) {
//                    total = (new StringBuilder()).append(total).append("Zero ").toString();
//                }
//            }
//            if (paisa != 0) {
//                total = (new StringBuilder()).append(total).append("and ").toString();
//                cat2(paisa);
//                total = (new StringBuilder()).append(total).append("Paisa ").toString();
//            }
//            total = (new StringBuilder()).append(total).append("Only").toString();
//        }
//        return total;
//    }
//
//    private double round(double amount, int places) {
//        return (double) Math.round(amount * Math.pow(10D, places)) / Math.pow(10D, places);
//    }
//
//    private void cat3(long l) {
//        if (l >= 0x3b9aca00L) {
//            int i = (int) (l / 0x3b9aca00L);
//            if (i >= 100) {
//                cat3(i);
//            } else {
//                cat2(i - i % 1);
//            }
//            if (i > 0) {
//                total = (new StringBuilder()).append(total).append("Arab ").toString();
//            }
//            l %= 0x3b9aca00L;
//        }
//        if (l >= 0x989680L) {
//            double amount = l / 0x989680L;
//            cat2((long) amount);
//            if (amount > 0.0D) {
//                total = (new StringBuilder()).append(total).append("Crore ").toString();
//            }
//            l %= 0x989680L;
//        }
//        if (l >= 0x186a0L) {
//            double d1 = l / 0x186a0L;
//            cat2((long) d1);
//            if (d1 > 0.0D) {
//                total = (new StringBuilder()).append(total).append("Lakh ").toString();
//            }
//            l %= 0x186a0L;
//        }
//        if (l >= 1000L) {
//            double d2 = l / 1000L;
//            cat2((long) d2);
//            if (d2 > 0.0D) {
//                total = (new StringBuilder()).append(total).append("Thousand ").toString();
//            }
//            l %= 1000L;
//        }
//        if (l >= 100L) {
//            double d3 = l / 100L;
//            cat2((long) d3);
//            if (d3 > 0.0D) {
//                total = (new StringBuilder()).append(total).append("Hundred ").toString();
//            }
//            l %= 100L;
//        }
//        if (l > 0L) {
//            cat2(l / 1L);
//        }
//    }
//
//    private void cat2(long l) {
//        if (l >= 90L) {
//            total = (new StringBuilder()).append(total).append("Ninety ").toString();
//            cat1(l % 90L);
//        } else if (l >= 80L) {
//            total = (new StringBuilder()).append(total).append("Eighty ").toString();
//            cat1(l % 80L);
//        } else if (l >= 70L) {
//            total = (new StringBuilder()).append(total).append("Seventy ").toString();
//            cat1(l % 70L);
//        } else if (l >= 60L) {
//            total = (new StringBuilder()).append(total).append("Sixty ").toString();
//            cat1(l % 60L);
//        } else if (l >= 50L) {
//            total = (new StringBuilder()).append(total).append("Fifty ").toString();
//            cat1(l % 50L);
//        } else if (l >= 40L) {
//            total = (new StringBuilder()).append(total).append("Forty ").toString();
//            cat1(l % 40L);
//        } else if (l >= 30L) {
//            total = (new StringBuilder()).append(total).append("Thirty ").toString();
//            cat1(l % 30L);
//        } else if (l > 20L) {
//            total = (new StringBuilder()).append(total).append("Twenty ").toString();
//            cat1(l % 20L);
//        } else {
//            cat1(l);
//        }
//    }
//
//    private void cat1(long l) {
//        switch ((int) l) {
//            case 1: // '\001'
//                total = (new StringBuilder()).append(total).append("One ").toString();
//                break;
//
//            case 2: // '\002'
//                total = (new StringBuilder()).append(total).append("Two ").toString();
//                break;
//
//            case 3: // '\003'
//                total = (new StringBuilder()).append(total).append("Three ").toString();
//                break;
//
//            case 4: // '\004'
//                total = (new StringBuilder()).append(total).append("Four ").toString();
//                break;
//
//            case 5: // '\005'
//                total = (new StringBuilder()).append(total).append("Five ").toString();
//                break;
//
//            case 6: // '\006'
//                total = (new StringBuilder()).append(total).append("Six ").toString();
//                break;
//
//            case 7: // '\007'
//                total = (new StringBuilder()).append(total).append("Seven ").toString();
//                break;
//
//            case 8: // '\b'
//                total = (new StringBuilder()).append(total).append("Eight ").toString();
//                break;
//
//            case 9: // '\t'
//                total = (new StringBuilder()).append(total).append("Nine ").toString();
//                break;
//
//            case 10: // '\n'
//                total = (new StringBuilder()).append(total).append("Ten ").toString();
//                break;
//
//            case 11: // '\013'
//                total = (new StringBuilder()).append(total).append("Eleven ").toString();
//                break;
//
//            case 12: // '\f'
//                total = (new StringBuilder()).append(total).append("Twelve ").toString();
//                break;
//
//            case 13: // '\r'
//                total = (new StringBuilder()).append(total).append("Thirteen ").toString();
//                break;
//
//            case 14: // '\016'
//                total = (new StringBuilder()).append(total).append("Fourteen ").toString();
//                break;
//
//            case 15: // '\017'
//                total = (new StringBuilder()).append(total).append("Fifteen ").toString();
//                break;
//
//            case 16: // '\020'
//                total = (new StringBuilder()).append(total).append("Sixteen ").toString();
//                break;
//
//            case 17: // '\021'
//                total = (new StringBuilder()).append(total).append("Seventeen ").toString();
//                break;
//
//            case 18: // '\022'
//                total = (new StringBuilder()).append(total).append("Eighteen ").toString();
//                break;
//
//            case 19: // '\023'
//                total = (new StringBuilder()).append(total).append("Nineteen ").toString();
//                break;
//
//            case 20: // '\024'
//                total = (new StringBuilder()).append(total).append("Twenty ").toString();
//                break;
//        }
//    }
}
