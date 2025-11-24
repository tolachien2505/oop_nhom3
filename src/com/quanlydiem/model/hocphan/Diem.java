package com.quanlydiem.model.hocphan;

import com.quanlydiem.model.nguoi.SinhVien;
import com.quanlydiem.ngoaile.DiemKhongHopLeException;

import java.io.Serializable;

/**
 * Lớp Diem - Đại diện cho bảng điểm của sinh viên trong một học phần
 */
public class Diem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String maDiem;
    private SinhVien sinhVien;
    private HocPhan hocPhan;
    
    // Điểm thành phần
    private double diemQuiz;
    private double diemGiuaKy;
    private double diemCuoiKy;
    private double diemBaiTap;
    
    // Điểm tổng kết
    private double diemTongKet;
    private String diemChu; // A, B+, B, C+, C, D+, D, F
    private boolean daDat;  // Đạt hay không đạt
    
    public Diem() {
        this.diemQuiz = 0.0;
        this.diemGiuaKy = 0.0;
        this.diemCuoiKy = 0.0;
        this.diemBaiTap = 0.0;
        this.diemTongKet = 0.0;
        this.diemChu = "F";
        this.daDat = false;
    }
    
    public Diem(String maDiem, SinhVien sinhVien, HocPhan hocPhan) {
        this();
        this.maDiem = maDiem;
        this.sinhVien = sinhVien;
        this.hocPhan = hocPhan;
    }
    
    /**
     * Kiểm tra tính hợp lệ của điểm
     */
    public static void kiemTraDiem(double diem) throws DiemKhongHopLeException {
        if (diem < 0 || diem > 10) {
            throw new DiemKhongHopLeException(diem);
        }
    }
    
    /**
     * Cập nhật điểm thành phần với kiểm tra hợp lệ
     */
    public void capNhatDiemQuiz(double diem) throws DiemKhongHopLeException {
        kiemTraDiem(diem);
        this.diemQuiz = diem;
    }
    
    public void capNhatDiemGiuaKy(double diem) throws DiemKhongHopLeException {
        kiemTraDiem(diem);
        this.diemGiuaKy = diem;
    }
    
    public void capNhatDiemCuoiKy(double diem) throws DiemKhongHopLeException {
        kiemTraDiem(diem);
        this.diemCuoiKy = diem;
    }
    
    public void capNhatDiemBaiTap(double diem) throws DiemKhongHopLeException {
        kiemTraDiem(diem);
        this.diemBaiTap = diem;
    }
    
    /**
     * Tính điểm tổng kết dựa trên trọng số của học phần
     */
    public void tinhDiemTongKet() {
        if (hocPhan != null) {
            this.diemTongKet = hocPhan.tinhDiemTongKet(diemQuiz, diemGiuaKy, diemCuoiKy, diemBaiTap);
            this.diemChu = chuyenDoiDiemChu(diemTongKet);
            this.daDat = diemTongKet >= 4.0;
        }
    }
    
    /**
     * Chuyển đổi điểm số sang điểm chữ
     */
    private String chuyenDoiDiemChu(double diem) {
        if (diem >= 9.0) return "A";
        if (diem >= 8.5) return "B+";
        if (diem >= 8.0) return "B";
        if (diem >= 7.0) return "C+";
        if (diem >= 6.5) return "C";
        if (diem >= 5.5) return "D+";
        if (diem >= 5.0) return "D";
        if (diem >= 4.0) return "D";
        return "F";
    }
    
    // Getters and Setters
    public String getMaDiem() {
        return maDiem;
    }
    
    public void setMaDiem(String maDiem) {
        this.maDiem = maDiem;
    }
    
    public SinhVien getSinhVien() {
        return sinhVien;
    }
    
    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }
    
    public HocPhan getHocPhan() {
        return hocPhan;
    }
    
    public void setHocPhan(HocPhan hocPhan) {
        this.hocPhan = hocPhan;
    }
    
    public double getDiemQuiz() {
        return diemQuiz;
    }
    
    public void setDiemQuiz(double diemQuiz) {
        this.diemQuiz = diemQuiz;
    }
    
    public double getDiemGiuaKy() {
        return diemGiuaKy;
    }
    
    public void setDiemGiuaKy(double diemGiuaKy) {
        this.diemGiuaKy = diemGiuaKy;
    }
    
    public double getDiemCuoiKy() {
        return diemCuoiKy;
    }
    
    public void setDiemCuoiKy(double diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
    }
    
    public double getDiemBaiTap() {
        return diemBaiTap;
    }
    
    public void setDiemBaiTap(double diemBaiTap) {
        this.diemBaiTap = diemBaiTap;
    }
    
    public double getDiemTongKet() {
        return diemTongKet;
    }
    
    public void setDiemTongKet(double diemTongKet) {
        this.diemTongKet = diemTongKet;
    }
    
    public String getDiemChu() {
        return diemChu;
    }
    
    public void setDiemChu(String diemChu) {
        this.diemChu = diemChu;
    }
    
    public boolean isDaDat() {
        return daDat;
    }
    
    public void setDaDat(boolean daDat) {
        this.daDat = daDat;
    }
    
    @Override
    public String toString() {
        return String.format("Diem[%s - %s: %.2f (%s)]", 
            sinhVien != null ? sinhVien.getMa() : "N/A",
            hocPhan != null ? hocPhan.getTenHocPhan() : "N/A",
            diemTongKet, diemChu);
    }
}

