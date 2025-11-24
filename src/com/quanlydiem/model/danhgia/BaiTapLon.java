package com.quanlydiem.model.danhgia;

import java.time.LocalDate;

/**
 * Lớp BaiTapLon (Project) - Đại diện cho bài tập lớn/đồ án
 * Trọng số mặc định: 20%
 */
public class BaiTapLon extends DanhGia {
    private static final long serialVersionUID = 1L;
    private static final double TRONG_SO_MAC_DINH = 0.2; // 20%
    
    private double trongSo;
    private LocalDate ngayNop;
    private boolean nopTreHan;
    private int soThanhVienNhom;
    
    public BaiTapLon() {
        super();
        this.trongSo = TRONG_SO_MAC_DINH;
        this.nopTreHan = false;
        this.soThanhVienNhom = 1;
    }
    
    public BaiTapLon(String maDanhGia, String tenDanhGia) {
        super(maDanhGia, tenDanhGia);
        this.trongSo = TRONG_SO_MAC_DINH;
        this.nopTreHan = false;
        this.soThanhVienNhom = 1;
    }
    
    public BaiTapLon(String maDanhGia, String tenDanhGia, double diem, String moTa, 
                     LocalDate ngayNop, int soThanhVienNhom) {
        super(maDanhGia, tenDanhGia, diem, moTa);
        this.trongSo = TRONG_SO_MAC_DINH;
        this.ngayNop = ngayNop;
        this.nopTreHan = false;
        this.soThanhVienNhom = soThanhVienNhom;
    }
    
    public BaiTapLon(String maDanhGia, String tenDanhGia, double diem, String moTa, 
                     double trongSo, LocalDate ngayNop, boolean nopTreHan, int soThanhVienNhom) {
        super(maDanhGia, tenDanhGia, diem, moTa);
        this.trongSo = trongSo;
        this.ngayNop = ngayNop;
        this.nopTreHan = nopTreHan;
        this.soThanhVienNhom = soThanhVienNhom;
    }
    
    @Override
    public double tinhTrongSo() {
        // Giảm trọng số 10% nếu nộp trễ hạn
        if (nopTreHan) {
            return trongSo * 0.9;
        }
        return trongSo;
    }
    
    @Override
    public String layLoaiDanhGia() {
        return "Bai Tap Lon";
    }
    
    // Getters and Setters
    public void setTrongSo(double trongSo) {
        this.trongSo = trongSo;
    }
    
    public LocalDate getNgayNop() {
        return ngayNop;
    }
    
    public void setNgayNop(LocalDate ngayNop) {
        this.ngayNop = ngayNop;
    }
    
    public boolean isNopTreHan() {
        return nopTreHan;
    }
    
    public void setNopTreHan(boolean nopTreHan) {
        this.nopTreHan = nopTreHan;
    }
    
    public int getSoThanhVienNhom() {
        return soThanhVienNhom;
    }
    
    public void setSoThanhVienNhom(int soThanhVienNhom) {
        this.soThanhVienNhom = soThanhVienNhom;
    }
}

