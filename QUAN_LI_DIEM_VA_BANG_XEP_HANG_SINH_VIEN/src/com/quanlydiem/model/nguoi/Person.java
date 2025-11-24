package com.quanlydiem.model.nguoi;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Lớp trừu tượng Person - Đại diện cho một người trong hệ thống
 */
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String ma;
    protected String hoTen;
    protected LocalDate ngaySinh;
    protected String email;
    protected String soDienThoai;
    
    public Person() {
    }
    
    public Person(String ma, String hoTen, LocalDate ngaySinh) {
        this.ma = ma;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
    }
    
    public Person(String ma, String hoTen, LocalDate ngaySinh, String email, String soDienThoai) {
        this.ma = ma;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.soDienThoai = soDienThoai;
    }
    
    // Abstract methods
    public abstract String layThongTinChiTiet();
    public abstract String layLoaiNguoi();
    
    // Getters and Setters
    public String getMa() {
        return ma;
    }
    
    public void setMa(String ma) {
        this.ma = ma;
    }
    
    public String getHoTen() {
        return hoTen;
    }
    
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    
    public LocalDate getNgaySinh() {
        return ngaySinh;
    }
    
    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSoDienThoai() {
        return soDienThoai;
    }
    
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s (%s)", ma, hoTen, layLoaiNguoi());
    }
}

