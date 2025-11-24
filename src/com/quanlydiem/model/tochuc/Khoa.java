package com.quanlydiem.model.tochuc;

import java.io.Serializable;

/**
 * Lớp Khoa - Đại diện cho một khoa trong trường
 */
public class Khoa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String maKhoa;
    private String tenKhoa;
    private String truongKhoa;
    private String dienThoai;
    
    public Khoa() {
    }
    
    public Khoa(String maKhoa, String tenKhoa) {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
    }
    
    public Khoa(String maKhoa, String tenKhoa, String truongKhoa, String dienThoai) {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
        this.truongKhoa = truongKhoa;
        this.dienThoai = dienThoai;
    }
    
    // Getters and Setters
    public String getMaKhoa() {
        return maKhoa;
    }
    
    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }
    
    public String getTenKhoa() {
        return tenKhoa;
    }
    
    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }
    
    public String getTruongKhoa() {
        return truongKhoa;
    }
    
    public void setTruongKhoa(String truongKhoa) {
        this.truongKhoa = truongKhoa;
    }
    
    public String getDienThoai() {
        return dienThoai;
    }
    
    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s", maKhoa, tenKhoa);
    }
}

