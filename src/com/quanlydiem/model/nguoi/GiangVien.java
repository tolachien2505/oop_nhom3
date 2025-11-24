package com.quanlydiem.model.nguoi;

import com.quanlydiem.model.tochuc.Khoa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp GiangVien - Đại diện cho giảng viên trong hệ thống
 */
public class GiangVien extends Person {
    private static final long serialVersionUID = 1L;
    
    private Khoa khoa;
    private String chucVu;
    private String hocVi;
    private List<String> danhSachMaHocPhan; // Danh sách mã học phần giảng viên đang dạy
    
    public GiangVien() {
        super();
        this.danhSachMaHocPhan = new ArrayList<>();
    }
    
    public GiangVien(String maGiangVien, String hoTen, LocalDate ngaySinh, Khoa khoa) {
        super(maGiangVien, hoTen, ngaySinh);
        this.khoa = khoa;
        this.danhSachMaHocPhan = new ArrayList<>();
    }
    
    public GiangVien(String maGiangVien, String hoTen, LocalDate ngaySinh, 
                     String email, String soDienThoai, Khoa khoa, String chucVu, String hocVi) {
        super(maGiangVien, hoTen, ngaySinh, email, soDienThoai);
        this.khoa = khoa;
        this.chucVu = chucVu;
        this.hocVi = hocVi;
        this.danhSachMaHocPhan = new ArrayList<>();
    }
    
    @Override
    public String layThongTinChiTiet() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ma giang vien: ").append(ma).append("\n");
        sb.append("Ho ten: ").append(hoTen).append("\n");
        sb.append("Ngay sinh: ").append(ngaySinh).append("\n");
        sb.append("Email: ").append(email != null ? email : "N/A").append("\n");
        sb.append("So dien thoai: ").append(soDienThoai != null ? soDienThoai : "N/A").append("\n");
        sb.append("Khoa: ").append(khoa != null ? khoa.getTenKhoa() : "N/A").append("\n");
        sb.append("Chuc vu: ").append(chucVu != null ? chucVu : "N/A").append("\n");
        sb.append("Hoc vi: ").append(hocVi != null ? hocVi : "N/A").append("\n");
        sb.append("So hoc phan dang day: ").append(danhSachMaHocPhan.size());
        return sb.toString();
    }
    
    @Override
    public String layLoaiNguoi() {
        return "Giang vien";
    }
    
    public void themHocPhan(String maHocPhan) {
        if (!danhSachMaHocPhan.contains(maHocPhan)) {
            danhSachMaHocPhan.add(maHocPhan);
        }
    }
    
    public void xoaHocPhan(String maHocPhan) {
        danhSachMaHocPhan.remove(maHocPhan);
    }
    
    // Getters and Setters
    public Khoa getKhoa() {
        return khoa;
    }
    
    public void setKhoa(Khoa khoa) {
        this.khoa = khoa;
    }
    
    public String getChucVu() {
        return chucVu;
    }
    
    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
    
    public String getHocVi() {
        return hocVi;
    }
    
    public void setHocVi(String hocVi) {
        this.hocVi = hocVi;
    }
    
    public List<String> getDanhSachMaHocPhan() {
        return danhSachMaHocPhan;
    }
    
    public void setDanhSachMaHocPhan(List<String> danhSachMaHocPhan) {
        this.danhSachMaHocPhan = danhSachMaHocPhan;
    }
}

