package com.cbs.dto.report;

public class AlmTdRoiWiseReportPojo implements java.io.Serializable, Comparable {

    private double roi;
    private double d1to14days;
    private double d15to28days;
    private double d29daysandupto3months;
    private double over3monthsandupto6months;
    private double over6monthsandupto12months;
    private double over1yearandupto2years;
    private double over2yearsandupto5years;
    private double over5years;
    private double total;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getOver1yearandupto2years() {
        return over1yearandupto2years;
    }

    public double getD15to28days() {
        return d15to28days;
    }

    public void setD15to28days(double d15to28days) {
        this.d15to28days = d15to28days;
    }

    public double getD1to14days() {
        return d1to14days;
    }

    public void setD1to14days(double d1to14days) {
        this.d1to14days = d1to14days;
    }

    public double getD29daysandupto3months() {
        return d29daysandupto3months;
    }

    public void setD29daysandupto3months(double d29daysandupto3months) {
        this.d29daysandupto3months = d29daysandupto3months;
    }

    public void setOver1yearandupto2years(double over1yearandupto2years) {
        this.over1yearandupto2years = over1yearandupto2years;
    }

    public double getOver2yearsandupto5years() {
        return over2yearsandupto5years;
    }

    public void setOver2yearsandupto5years(double over2yearsandupto5years) {
        this.over2yearsandupto5years = over2yearsandupto5years;
    }

    public double getOver3monthsandupto6months() {
        return over3monthsandupto6months;
    }

    public void setOver3monthsandupto6months(double over3monthsandupto6months) {
        this.over3monthsandupto6months = over3monthsandupto6months;
    }

    public double getOver5years() {
        return over5years;
    }

    public void setOver5years(double over5years) {
        this.over5years = over5years;
    }

    public double getOver6monthsandupto12months() {
        return over6monthsandupto12months;
    }

    public void setOver6monthsandupto12months(double over6monthsandupto12months) {
        this.over6monthsandupto12months = over6monthsandupto12months;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    @Override
    public int compareTo(Object o) {

        return new Double(this.roi).compareTo(new Double(((AlmTdRoiWiseReportPojo) o).roi));
    }
}
