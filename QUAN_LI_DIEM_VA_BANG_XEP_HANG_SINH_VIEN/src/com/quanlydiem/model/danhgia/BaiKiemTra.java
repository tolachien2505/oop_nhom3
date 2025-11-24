package com.quanlydiem.model.danhgia;

/**
 * Lớp BaiKiemTra (Quiz) - Đại diện cho bài kiểm tra nhỏ
 * Trọng số mặc định: 10%
 */
public class BaiKiemTra extends DanhGia {
    private static final long serialVersionUID = 1L;
    private static final double TRONG_SO_MAC_DINH = 0.1; // 10%
    
    private double trongSo;
    private int thoiGian; // Thời gian làm bài (phút)
    
    public BaiKiemTra() {
        super();
        this.trongSo = TRONG_SO_MAC_DINH;
    }
    
    public BaiKiemTra(String maDanhGia, String tenDanhGia) {
        super(maDanhGia, tenDanhGia);
        this.trongSo = TRONG_SO_MAC_DINH;
    }
    
    public BaiKiemTra(String maDanhGia, String tenDanhGia, double diem, String moTa, int thoiGian) {
        super(maDanhGia, tenDanhGia, diem, moTa);
        this.trongSo = TRONG_SO_MAC_DINH;
        this.thoiGian = thoiGian;
    }
    
    public BaiKiemTra(String maDanhGia, String tenDanhGia, double diem, String moTa, 
                      double trongSo, int thoiGian) {
        super(maDanhGia, tenDanhGia, diem, moTa);
        this.trongSo = trongSo;
        this.thoiGian = thoiGian;
    }
    
    @Override
    public double tinhTrongSo() {
        return trongSo;
    }
    
    @Override
    public String layLoaiDanhGia() {
        return "Bai Kiem Tra (Quiz)";
    }
    
    // Getters and Setters
    public void setTrongSo(double trongSo) {
        this.trongSo = trongSo;
    }
    
    public int getThoiGian() {
        return thoiGian;
    }
    
    public void setThoiGian(int thoiGian) {
        this.thoiGian = thoiGian;
    }
}

