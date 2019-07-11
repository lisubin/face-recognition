package ijinbu.recognition.time;

import java.io.Serializable;

public class Coupon implements Serializable {


    private static final long serialVersionUID = -8820106448948528102L;
    private String couName;
    private String couInfo;
    private Integer maxFare;
    private Integer free;
    private String proType;
    private Double discount;
    private String production;
    private Integer num;
    private boolean add;

    public Coupon(String couName, String couInfo, Integer maxFare, Integer free, String proType, Double discount, String production, Integer num, boolean add) {
        this.couName = couName;
        this.couInfo = couInfo;
        this.maxFare = maxFare;
        this.free = free;
        this.proType = proType;
        this.discount = discount;
        this.production = production;
        this.num = num;
        this.add = add;
    }

    public String getCouName() {
        return couName;
    }

    public void setCouName(String couName) {
        this.couName = couName;
    }

    public String getCouInfo() {
        return couInfo;
    }

    public void setCouInfo(String couInfo) {
        this.couInfo = couInfo;
    }

    public Integer getMaxFare() {
        return maxFare;
    }

    public void setMaxFare(Integer maxFare) {
        this.maxFare = maxFare;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "couName='" + couName + '\'' +
                ", couInfo='" + couInfo + '\'' +
                ", maxFare=" + maxFare +
                ", free=" + free +
                ", proType='" + proType + '\'' +
                ", discount=" + discount +
                ", production='" + production + '\'' +
                ", num=" + num +
                ", add=" + add +
                '}';
    }
}
