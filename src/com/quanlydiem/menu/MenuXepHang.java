package com.quanlydiem.menu;

import com.quanlydiem.luutru.*;
import com.quanlydiem.model.nguoi.SinhVien;
import com.quanlydiem.dichvu.BangXepHangService;

import java.util.List;
import java.util.Scanner;

/**
 * Menu Bảng xếp hạng
 */
public class MenuXepHang {
    private Scanner scanner;
    private KhoaRepository khoaRepo;
    private LopHocRepository lopHocRepo;
    private SinhVienRepository sinhVienRepo;
    private DiemRepository diemRepo;
    private BangXepHangService bangXepHangService;
    
    public MenuXepHang(Scanner scanner, KhoaRepository khoaRepo, LopHocRepository lopHocRepo,
                      SinhVienRepository sinhVienRepo, DiemRepository diemRepo,
                      BangXepHangService bangXepHangService) {
        this.scanner = scanner;
        this.khoaRepo = khoaRepo;
        this.lopHocRepo = lopHocRepo;
        this.sinhVienRepo = sinhVienRepo;
        this.diemRepo = diemRepo;
        this.bangXepHangService = bangXepHangService;
    }
    
    public void hienThi() {
        System.out.println("\n===============================================");
        System.out.println("    BANG XEP HANG");
        System.out.println("===============================================");
        System.out.println("1. Xep hang toan truong");
        System.out.println("2. Xep hang theo khoa");
        System.out.println("3. Xep hang theo lop");
        System.out.println("4. Top N sinh vien");
        System.out.println("5. Loc theo GPA toi thieu");
        System.out.println("0. Quay lai");
        System.out.println("===============================================");
        System.out.print("Nhap lua chon: ");
        
        try {
            int luaChon = Integer.parseInt(scanner.nextLine().trim());
            
            switch (luaChon) {
                case 1: xepHangToanTruong(); break;
                case 2: xepHangTheoKhoa(); break;
                case 3: xepHangTheoLop(); break;
                case 4: topNSinhVien(); break;
                case 5: locTheoGPA(); break;
                case 0: break;
                default: System.out.println("Lua chon khong hop le!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui long nhap so!");
        }
    }
    
    private void xepHangToanTruong() {
        var danhSachSinhVien = sinhVienRepo.layTatCa();
        var danhSachDiem = diemRepo.layTatCa();
        
        List<SinhVien> xepHang = bangXepHangService.xepHangToanTruong(danhSachSinhVien, danhSachDiem);
        
        String bangXepHang = bangXepHangService.taoBangXepHang(xepHang, "BANG XEP HANG TOAN TRUONG");
        System.out.println(bangXepHang);
    }
    
    private void xepHangTheoKhoa() {
        System.out.print("\nNhap ma khoa: ");
        String maKhoa = scanner.nextLine().trim();
        
        var khoaOpt = khoaRepo.timTheoMa(maKhoa);
        if (!khoaOpt.isPresent()) {
            System.out.println("Khong tim thay khoa!");
            return;
        }
        
        var danhSachSinhVien = sinhVienRepo.layTatCa();
        var danhSachDiem = diemRepo.layTatCa();
        
        List<SinhVien> xepHang = bangXepHangService.xepHangTheoKhoa(maKhoa, danhSachSinhVien, danhSachDiem);
        
        String tieuDe = "BANG XEP HANG KHOA " + khoaOpt.get().getTenKhoa().toUpperCase();
        String bangXepHang = bangXepHangService.taoBangXepHang(xepHang, tieuDe);
        System.out.println(bangXepHang);
    }
    
    private void xepHangTheoLop() {
        System.out.print("\nNhap ma lop: ");
        String maLop = scanner.nextLine().trim();
        
        var lopOpt = lopHocRepo.timTheoMa(maLop);
        if (!lopOpt.isPresent()) {
            System.out.println("Khong tim thay lop!");
            return;
        }
        
        var danhSachSinhVien = sinhVienRepo.layTatCa();
        var danhSachDiem = diemRepo.layTatCa();
        
        List<SinhVien> xepHang = bangXepHangService.xepHangTheoLop(maLop, danhSachSinhVien, danhSachDiem);
        
        String tieuDe = "BANG XEP HANG LOP " + lopOpt.get().getTenLop().toUpperCase();
        String bangXepHang = bangXepHangService.taoBangXepHang(xepHang, tieuDe);
        System.out.println(bangXepHang);
    }
    
    private void topNSinhVien() {
        System.out.print("\nNhap so luong sinh vien (N): ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        
        var danhSachSinhVien = sinhVienRepo.layTatCa();
        var danhSachDiem = diemRepo.layTatCa();
        
        List<SinhVien> topN = bangXepHangService.layTopN(n, danhSachSinhVien, danhSachDiem);
        
        String tieuDe = "TOP " + n + " SINH VIEN";
        String bangXepHang = bangXepHangService.taoBangXepHang(topN, tieuDe);
        System.out.println(bangXepHang);
    }
    
    private void locTheoGPA() {
        System.out.print("\nNhap GPA toi thieu: ");
        double gpaToiThieu = Double.parseDouble(scanner.nextLine().trim());
        
        var danhSachSinhVien = sinhVienRepo.layTatCa();
        var danhSachDiem = diemRepo.layTatCa();
        
        List<SinhVien> xepHang = bangXepHangService.xepHangToanTruong(danhSachSinhVien, danhSachDiem);
        List<SinhVien> locGPA = bangXepHangService.locTheoGPAToiThieu(gpaToiThieu, xepHang);
        
        String tieuDe = "SINH VIEN CO GPA >= " + String.format("%.2f", gpaToiThieu);
        String bangXepHang = bangXepHangService.taoBangXepHang(locGPA, tieuDe);
        System.out.println(bangXepHang);
    }
}

