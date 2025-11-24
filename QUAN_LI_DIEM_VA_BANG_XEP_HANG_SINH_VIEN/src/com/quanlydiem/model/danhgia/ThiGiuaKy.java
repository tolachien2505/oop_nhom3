package com.quanlydiem.model.danhgia;

/**
 * Lớp ThiGiuaKy - Đại diện cho kỳ thi giữa kỳ
 * Trọng số mặc định: 20%
 */
public class ThiGiuaKy extends DanhGia {
    private static final long serialVersionUID = 1L;
    private static final double TRONG_SO_MAC_DINH = 0.2; // 20%
    
    private double trongSo;
    private String phongThi;
    private int thoiGian; // Thời gian làm bài (phút)
    
    public ThiGiuaKy() {
        super();
        this.trongSo = TRONG_SO_MAC_DINH;
    }
    
    public ThiGiuaKy(String maDanhGia, String tenDanhGia) {
        super(maDanhGia, tenDanhGia);
        this.trongSo = TRONG_SO_MAC_DINH;
    }
    
    public ThiGiuaKy(String maDanhGia, String tenDanhGia, double diem, String moTa, 
                     String phongThi, int thoiGian) {
        super(maDanhGia, tenDanhGia, diem, moTa);
        this.trongSo = TRONG_SO_MAC_DINH;
        this.phongThi = phongThi;
        this.thoiGian = thoiGian;
    }
    
    public ThiGiuaKy(String maDanhGia, String tenDanhGia, double diem, String moTa, 
                     double trongSo, String phongThi, int thoiGian) {
        super(maDanhGia, tenDanhGia, diem, moTa);
        this.trongSo = trongSo;
        this.phongThi = phongThi;
        this.thoiGian = thoiGian;
    }
    
    @Override
    public double tinhTrongSo() {
        return trongSo;
    }
    
    @Override
    public String layLoaiDanhGia() {
        return "Thi Giua Ky";
    }
    
    // Getters and Setters
    public void setTrongSo(double trongSo) {
        this.trongSo = trongSo;
    }
    
    public String getPhongThi() {
        return phongThi;
    }
    
    public void setPhongThi(String phongThi) {
        this.phongThi = phongThi;
    }
    
    public int getThoiGian() {
        return thoiGian;
    }
    
    public void setThoiGian(int thoiGian) {
        this.thoiGian = thoiGian;
    }
}

