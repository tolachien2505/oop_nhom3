package com.quanlydiem.model.nguoi;

import com.quanlydiem.model.tochuc.LopHoc;
import com.quanlydiem.model.tochuc.Khoa;
import com.quanlydiem.giaodien.XepHang;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp SinhVien - Đại diện cho sinh viên trong hệ thống
 */
public class SinhVien extends Person implements XepHang {
    private static final long serialVersionUID = 1L;
    
    private LopHoc lopHoc;
    private Khoa khoa;
    private List<String> danhSachMaDiem; // Danh sách mã điểm của sinh viên
    private double gpa;
    private String xepLoai;
    
    public SinhVien() {
        super();
        this.danhSachMaDiem = new ArrayList<>();
        this.gpa = 0.0;
        this.xepLoai = "Chua xep loai";
    }
    
    public SinhVien(String maSinhVien, String hoTen, LocalDate ngaySinh, LopHoc lopHoc, Khoa khoa) {
        super(maSinhVien, hoTen, ngaySinh);
        this.lopHoc = lopHoc;
        this.khoa = khoa;
        this.danhSachMaDiem = new ArrayList<>();
        this.gpa = 0.0;
        this.xepLoai = "Chua xep loai";
    }
    
    public SinhVien(String maSinhVien, String hoTen, LocalDate ngaySinh, 
                    String email, String soDienThoai, LopHoc lopHoc, Khoa khoa) {
        super(maSinhVien, hoTen, ngaySinh, email, soDienThoai);
        this.lopHoc = lopHoc;
        this.khoa = khoa;
        this.danhSachMaDiem = new ArrayList<>();
        this.gpa = 0.0;
        this.xepLoai = "Chua xep loai";
    }
    
    @Override
    public double tinhGPA() {
        // GPA sẽ được tính bởi service dựa trên danh sách điểm
        return this.gpa;
    }
    
    @Override
    public String xepLoaiHocLuc() {
        return this.xepLoai;
    }
    
    @Override
    public String layThongTinChiTiet() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ma sinh vien: ").append(ma).append("\n");
        sb.append("Ho ten: ").append(hoTen).append("\n");
        sb.append("Ngay sinh: ").append(ngaySinh).append("\n");
        sb.append("Email: ").append(email != null ? email : "N/A").append("\n");
        sb.append("So dien thoai: ").append(soDienThoai != null ? soDienThoai : "N/A").append("\n");
        sb.append("Lop: ").append(lopHoc != null ? lopHoc.getTenLop() : "N/A").append("\n");
        sb.append("Khoa: ").append(khoa != null ? khoa.getTenKhoa() : "N/A").append("\n");
        sb.append("GPA: ").append(String.format("%.2f", gpa)).append("\n");
        sb.append("Xep loai: ").append(xepLoai);
        return sb.toString();
    }
    
    @Override
    public String layLoaiNguoi() {
        return "Sinh vien";
    }
    
    public void themMaDiem(String maDiem) {
        if (!danhSachMaDiem.contains(maDiem)) {
            danhSachMaDiem.add(maDiem);
        }
    }
    
    public void xoaMaDiem(String maDiem) {
        danhSachMaDiem.remove(maDiem);
    }
    
    // Getters and Setters
    public LopHoc getLopHoc() {
        return lopHoc;
    }
    
    public void setLopHoc(LopHoc lopHoc) {
        this.lopHoc = lopHoc;
    }
    
    public Khoa getKhoa() {
        return khoa;
    }
    
    public void setKhoa(Khoa khoa) {
        this.khoa = khoa;
    }
    
    public List<String> getDanhSachMaDiem() {
        return danhSachMaDiem;
    }
    
    public void setDanhSachMaDiem(List<String> danhSachMaDiem) {
        this.danhSachMaDiem = danhSachMaDiem;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    
    public String getXepLoai() {
        return xepLoai;
    }
    
    public void setXepLoai(String xepLoai) {
        this.xepLoai = xepLoai;
    }
}

