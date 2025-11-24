package com.quanlydiem.model.hocphan;

import com.quanlydiem.model.nguoi.GiangVien;

import java.io.Serializable;

/**
 * Lớp HocPhan - Đại diện cho một học phần trong hệ thống
 */
public class HocPhan implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String maHocPhan;
    private String tenHocPhan;
    private int soTinChi;
    private GiangVien giangVien;
    
    // Trọng số điểm thành phần (mặc định)
    private double trongSoQuiz;      // 10%
    private double trongSoGiuaKy;    // 20%
    private double trongSoCuoiKy;    // 50%
    private double trongSoBaiTap;    // 20%
    
    public HocPhan() {
        // Trọng số mặc định
        this.trongSoQuiz = 0.1;
        this.trongSoGiuaKy = 0.2;
        this.trongSoCuoiKy = 0.5;
        this.trongSoBaiTap = 0.2;
    }
    
    public HocPhan(String maHocPhan, String tenHocPhan, int soTinChi, GiangVien giangVien) {
        this();
        this.maHocPhan = maHocPhan;
        this.tenHocPhan = tenHocPhan;
        this.soTinChi = soTinChi;
        this.giangVien = giangVien;
    }
    
    public HocPhan(String maHocPhan, String tenHocPhan, int soTinChi, GiangVien giangVien,
                   double trongSoQuiz, double trongSoGiuaKy, double trongSoCuoiKy, double trongSoBaiTap) {
        this.maHocPhan = maHocPhan;
        this.tenHocPhan = tenHocPhan;
        this.soTinChi = soTinChi;
        this.giangVien = giangVien;
        this.trongSoQuiz = trongSoQuiz;
        this.trongSoGiuaKy = trongSoGiuaKy;
        this.trongSoCuoiKy = trongSoCuoiKy;
        this.trongSoBaiTap = trongSoBaiTap;
    }
    
    /**
     * Tính điểm tổng kết theo công thức trọng số
     */
    public double tinhDiemTongKet(double diemQuiz, double diemGiuaKy, double diemCuoiKy, double diemBaiTap) {
        return diemQuiz * trongSoQuiz + 
               diemGiuaKy * trongSoGiuaKy + 
               diemCuoiKy * trongSoCuoiKy + 
               diemBaiTap * trongSoBaiTap;
    }
    
    // Getters and Setters
    public String getMaHocPhan() {
        return maHocPhan;
    }
    
    public void setMaHocPhan(String maHocPhan) {
        this.maHocPhan = maHocPhan;
    }
    
    public String getTenHocPhan() {
        return tenHocPhan;
    }
    
    public void setTenHocPhan(String tenHocPhan) {
        this.tenHocPhan = tenHocPhan;
    }
    
    public int getSoTinChi() {
        return soTinChi;
    }
    
    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }
    
    public GiangVien getGiangVien() {
        return giangVien;
    }
    
    public void setGiangVien(GiangVien giangVien) {
        this.giangVien = giangVien;
    }
    
    public double getTrongSoQuiz() {
        return trongSoQuiz;
    }
    
    public void setTrongSoQuiz(double trongSoQuiz) {
        this.trongSoQuiz = trongSoQuiz;
    }
    
    public double getTrongSoGiuaKy() {
        return trongSoGiuaKy;
    }
    
    public void setTrongSoGiuaKy(double trongSoGiuaKy) {
        this.trongSoGiuaKy = trongSoGiuaKy;
    }
    
    public double getTrongSoCuoiKy() {
        return trongSoCuoiKy;
    }
    
    public void setTrongSoCuoiKy(double trongSoCuoiKy) {
        this.trongSoCuoiKy = trongSoCuoiKy;
    }
    
    public double getTrongSoBaiTap() {
        return trongSoBaiTap;
    }
    
    public void setTrongSoBaiTap(double trongSoBaiTap) {
        this.trongSoBaiTap = trongSoBaiTap;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s (%d TC)", maHocPhan, tenHocPhan, soTinChi);
    }
}

