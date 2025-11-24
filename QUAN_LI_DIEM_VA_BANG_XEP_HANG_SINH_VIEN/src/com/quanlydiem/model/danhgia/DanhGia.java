package com.quanlydiem.model.danhgia;

import java.io.Serializable;

/**
 * Lớp trừu tượng DanhGia - Đại diện cho một hình thức đánh giá
 * Các lớp con sẽ override method tinhTrongSo() để xác định trọng số riêng
 */
public abstract class DanhGia implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String maDanhGia;
    protected String tenDanhGia;
    protected double diem;
    protected String moTa;
    
    public DanhGia() {
        this.diem = 0.0;
    }
    
    public DanhGia(String maDanhGia, String tenDanhGia) {
        this.maDanhGia = maDanhGia;
        this.tenDanhGia = tenDanhGia;
        this.diem = 0.0;
    }
    
    public DanhGia(String maDanhGia, String tenDanhGia, double diem, String moTa) {
        this.maDanhGia = maDanhGia;
        this.tenDanhGia = tenDanhGia;
        this.diem = diem;
        this.moTa = moTa;
    }
    
    /**
     * Phương thức trừu tượng tính trọng số - được override bởi các lớp con
     * @return Trọng số của hình thức đánh giá này
     */
    public abstract double tinhTrongSo();
    
    /**
     * Phương thức trừu tượng lấy loại đánh giá
     * @return Loại đánh giá (Quiz, Giữa kỳ, Cuối kỳ, Bài tập lớn)
     */
    public abstract String layLoaiDanhGia();
    
    /**
     * Tính điểm có trọng số
     * @return Điểm sau khi nhân với trọng số
     */
    public double tinhDiemCoTrongSo() {
        return diem * tinhTrongSo();
    }
    
    // Getters and Setters
    public String getMaDanhGia() {
        return maDanhGia;
    }
    
    public void setMaDanhGia(String maDanhGia) {
        this.maDanhGia = maDanhGia;
    }
    
    public String getTenDanhGia() {
        return tenDanhGia;
    }
    
    public void setTenDanhGia(String tenDanhGia) {
        this.tenDanhGia = tenDanhGia;
    }
    
    public double getDiem() {
        return diem;
    }
    
    public void setDiem(double diem) {
        this.diem = diem;
    }
    
    public String getMoTa() {
        return moTa;
    }
    
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    @Override
    public String toString() {
        return String.format("%s - Diem: %.2f - Trong so: %.0f%%", 
            layLoaiDanhGia(), diem, tinhTrongSo() * 100);
    }
}

