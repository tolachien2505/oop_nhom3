package com.quanlydiem.model.tochuc;

import java.io.Serializable;

/**
 * Lớp LopHoc - Đại diện cho một lớp học trong trường
 */
public class LopHoc implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String maLop;
    private String tenLop;
    private Khoa khoa;
    private int namNhapHoc;
    private String giaoVienChuNhiem;
    
    public LopHoc() {
    }
    
    public LopHoc(String maLop, String tenLop, Khoa khoa) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.khoa = khoa;
    }
    
    public LopHoc(String maLop, String tenLop, Khoa khoa, int namNhapHoc, String giaoVienChuNhiem) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.khoa = khoa;
        this.namNhapHoc = namNhapHoc;
        this.giaoVienChuNhiem = giaoVienChuNhiem;
    }
    
    // Getters and Setters
    public String getMaLop() {
        return maLop;
    }
    
    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }
    
    public String getTenLop() {
        return tenLop;
    }
    
    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }
    
    public Khoa getKhoa() {
        return khoa;
    }
    
    public void setKhoa(Khoa khoa) {
        this.khoa = khoa;
    }
    
    public int getNamNhapHoc() {
        return namNhapHoc;
    }
    
    public void setNamNhapHoc(int namNhapHoc) {
        this.namNhapHoc = namNhapHoc;
    }
    
    public String getGiaoVienChuNhiem() {
        return giaoVienChuNhiem;
    }
    
    public void setGiaoVienChuNhiem(String giaoVienChuNhiem) {
        this.giaoVienChuNhiem = giaoVienChuNhiem;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s", maLop, tenLop);
    }
}

