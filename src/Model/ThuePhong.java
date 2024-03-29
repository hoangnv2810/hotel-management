package Model;

import java.text.ParseException;
import java.util.Date;

public class ThuePhong {
    private String maThue;
    private String maKH;
    private String maPhong;
    private Date ngayDen;
    private Date ngayDi;
    private String thanhToan;
    private int tienCoc;

    public ThuePhong() {
    }

    public ThuePhong(String maThue, int maKH, String maPhong, Date ngayDen, Date ngayDi, String thanhToan, int tienCoc) throws ParseException {
        this.maThue = maThue;
        this.maKH = "KH" + String.format("%02d", maKH);
        this.maPhong = maPhong;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
        this.thanhToan = thanhToan;
        this.tienCoc = tienCoc;
    }

    public String getMaThue() {
        return maThue;
    }

    public String getMaKH() {
        return maKH;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public Date getNgayDen() {
        return ngayDen;
    }

    public Date getNgayDi() {
        return ngayDi;
    }

    public int getTienCoc() {
        return tienCoc;
    }

    public String getThanhToan() {
        if(thanhToan.contains("0")){
            return "Chưa thanh toán";
        } else {
            return "Đã thanh toán";
        }
    }
}
