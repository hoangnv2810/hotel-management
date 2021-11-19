package Model;

public class Phong {
    private String maPhong;
    private String loaiPhong;
    private String trangThai;
    private int gia;

    public Phong(String maPhong, String loaiPhong, String trangThai, int gia) {
        this.maPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.trangThai = trangThai;
        this.gia = gia;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public String getTrangThai() {
        if(trangThai.contains("0")){
            return "Đã Thuê";
        } else {
            return "Trống";
        }
    }

    public int getGia() {
        return gia;
    }
}