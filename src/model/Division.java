package model;

import java.time.LocalDateTime;

public class Division {

    public int divisionID;

    public String division;

    public LocalDateTime createDate;

    public LocalDateTime createTime;

    public Division(int divisionID, String division, LocalDateTime createDate, LocalDateTime createTime) {
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createTime = createTime;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
