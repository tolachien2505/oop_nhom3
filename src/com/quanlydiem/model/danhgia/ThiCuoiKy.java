package com.quanlydiem.model.danhgia;

/**
 * Lớp ThiCuoiKy - Đại diện cho kỳ thi cuối kỳ
 * Trọng số mặc định: 50%
 */
public class ThiCuoiKy extends DanhGia {
    private static final long serialVersionUID = 1L;
    private static final double TRONG_SO_MAC_DINH = 0.5; // 50%
    
    private double trongSo;
    private String phongThi;
    private int thoiGian; // Thời gian làm bài (phút)
    private boolean batBuoc; // Bắt buộc dự thi
    
    public ThiCuoiKy() {
        super();
        this.trongSo = TRONG_SO_MAC_DINH;
        this.batBuoc = true;
    }
    
    public ThiCuoiKy(String maDanhGia, String tenDanhGia) {
        super(maDanhGia, tenDanhGia);
        this.trongSo = TRONG_SO_MAC_DINH;
        this.batBuoc = true;
    }
    
    public ThiCuoiKy(String maDanhGia, String tenDanhGia, double diem, String moTa, 
                     String phongThi, int thoiGian) {
        super(maDanhGia, tenDanhGia, diem, moTa);
        this.trongSo = TRONG_SO_MAC_DINH;
        this.phongThi = phongThi;
        this.thoiGian = thoiGian;
        this.batBuoc = true;
    }
    
    public ThiCuoiKy(String maDanhGia, String tenDanhGia, double diem, String moTa, 
                     double trongSo, String phongThi, int thoiGian, boolean batBuoc) {
        super(maDanhGia, tenDanhGia, diem, moTa);
        this.trongSo = trongSo;
        this.phongThi = phongThi;
        this.thoiGian = thoiGian;
        this.batBuoc = batBuoc;
    }
    
    @Override
    public double tinhTrongSo() {
        return trongSo;
    }
    
    @Override
    public String layLoaiDanhGia() {
        return "Thi Cuoi Ky";
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
    
    public boolean isBatBuoc() {
        return batBuoc;
    }
    
    public void setBatBuoc(boolean batBuoc) {
        this.batBuoc = batBuoc;
    }
}

