package com.quanlydiem.menu;

import com.quanlydiem.luutru.*;
import com.quanlydiem.model.nguoi.SinhVien;
import com.quanlydiem.model.hocphan.*;
import com.quanlydiem.dichvu.*;
import com.quanlydiem.ngoaile.*;

import java.util.Scanner;

/**
 * Menu Quản lý điểm
 */
public class MenuDiem {
    private Scanner scanner;
    private InputHelper inputHelper;
    private SinhVienRepository sinhVienRepo;
    private HocPhanRepository hocPhanRepo;
    private DiemRepository diemRepo;
    private TinhDiemService tinhDiemService;
    private XepLoaiService xepLoaiService;
    
    public MenuDiem(Scanner scanner, SinhVienRepository sinhVienRepo,
                   HocPhanRepository hocPhanRepo, DiemRepository diemRepo,
                   TinhDiemService tinhDiemService, XepLoaiService xepLoaiService) {
        this.scanner = scanner;
        this.inputHelper = new InputHelper(scanner);
        this.sinhVienRepo = sinhVienRepo;
        this.hocPhanRepo = hocPhanRepo;
        this.diemRepo = diemRepo;
        this.tinhDiemService = tinhDiemService;
        this.xepLoaiService = xepLoaiService;
    }
    
    public void hienThi() {
        System.out.println("\n===============================================");
        System.out.println("    QUAN LY DIEM");
        System.out.println("===============================================");
        System.out.println("1. Nhap diem cho sinh vien");
        System.out.println("2. Sua diem");
        System.out.println("3. Xem bang diem sinh vien");
        System.out.println("4. Tinh GPA va xep loai");
        System.out.println("0. Quay lai");
        System.out.println("===============================================");
        System.out.print("Nhap lua chon: ");
        
        try {
            int luaChon = Integer.parseInt(scanner.nextLine().trim());
            
            switch (luaChon) {
                case 1: nhapDiem(); break;
                case 2: suaDiem(); break;
                case 3: xemBangDiem(); break;
                case 4: tinhGPAVaXepLoai(); break;
                case 0: break;
                default: System.out.println("Lua chon khong hop le!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui long nhap so!");
        }
    }
    
    private void nhapDiem() {
        System.out.print("\nMa sinh vien: ");
        String maSV = scanner.nextLine().trim();
        System.out.print("Ma hoc phan: ");
        String maHP = scanner.nextLine().trim();
        
        var svOpt = sinhVienRepo.timTheoMa(maSV);
        var hpOpt = hocPhanRepo.timTheoMa(maHP);
        
        if (!svOpt.isPresent()) {
            System.out.println("Khong tim thay sinh vien!");
            return;
        }
        if (!hpOpt.isPresent()) {
            System.out.println("Khong tim thay hoc phan!");
            return;
        }
        
        SinhVien sv = svOpt.get();
        HocPhan hp = hpOpt.get();
        
        // Kiểm tra đã có điểm chưa
        var diemCuOpt = diemRepo.timTheoSinhVienVaHocPhan(maSV, maHP);
        if (diemCuOpt.isPresent()) {
            System.out.println("Sinh vien da co diem cho hoc phan nay!");
            System.out.println("Vui long su dung chuc nang 'Sua diem'");
            return;
        }
        
        Diem diem = new Diem(null, sv, hp);
        
        try {
            System.out.println("\n--- NHAP DIEM THANH PHAN ---");
            System.out.println("Luu y: Tat ca diem phai nam trong khoang 0 - 10");
            System.out.println();
            
            // Sử dụng InputHelper để nhập điểm với validation
            double diemQuiz = inputHelper.nhapSoThuc("Diem quiz (0-10): ", 0, 10);
            diem.capNhatDiemQuiz(diemQuiz);
            
            double diemGiuaKy = inputHelper.nhapSoThuc("Diem giua ky (0-10): ", 0, 10);
            diem.capNhatDiemGiuaKy(diemGiuaKy);
            
            double diemCuoiKy = inputHelper.nhapSoThuc("Diem cuoi ky (0-10): ", 0, 10);
            diem.capNhatDiemCuoiKy(diemCuoiKy);
            
            double diemBaiTap = inputHelper.nhapSoThuc("Diem bai tap (0-10): ", 0, 10);
            diem.capNhatDiemBaiTap(diemBaiTap);
            
            // Tính điểm tổng kết
            tinhDiemService.tinhDiemTongKet(diem);
            
            // Lưu điểm
            diemRepo.them(diem);
            
            System.out.println("\n========================================");
            System.out.println("NHAP DIEM THANH CONG!");
            System.out.println("========================================");
            System.out.println("Sinh vien: " + sv.getHoTen() + " (" + sv.getMa() + ")");
            System.out.println("Hoc phan: " + hp.getTenHocPhan() + " (" + hp.getMaHocPhan() + ")");
            System.out.println("----------------------------------------");
            System.out.println("Diem quiz:    " + String.format("%.2f", diem.getDiemQuiz()));
            System.out.println("Diem giua ky: " + String.format("%.2f", diem.getDiemGiuaKy()));
            System.out.println("Diem cuoi ky: " + String.format("%.2f", diem.getDiemCuoiKy()));
            System.out.println("Diem bai tap: " + String.format("%.2f", diem.getDiemBaiTap()));
            System.out.println("----------------------------------------");
            System.out.println("Diem tong ket: " + String.format("%.2f", diem.getDiemTongKet()) + " (" + diem.getDiemChu() + ")");
            System.out.println("Trang thai: " + (diem.isDaDat() ? "DAT" : "KHONG DAT"));
            System.out.println("========================================");
            
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Nguoi dung huy thao tac")) {
                System.out.println("\nDa huy thao tac nhap diem.");
            } else {
                System.err.println("Loi: " + e.getMessage());
            }
        } catch (DiemKhongHopLeException e) {
            System.err.println("Loi: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void suaDiem() {
        System.out.print("\nMa sinh vien: ");
        String maSV = scanner.nextLine().trim();
        System.out.print("Ma hoc phan: ");
        String maHP = scanner.nextLine().trim();
        
        var diemOpt = diemRepo.timTheoSinhVienVaHocPhan(maSV, maHP);
        
        if (!diemOpt.isPresent()) {
            System.out.println("Khong tim thay diem!");
            return;
        }
        
        Diem diem = diemOpt.get();
        
        System.out.println("\n========================================");
        System.out.println("   DIEM HIEN TAI");
        System.out.println("========================================");
        System.out.println("Sinh vien: " + diem.getSinhVien().getHoTen());
        System.out.println("Hoc phan:  " + diem.getHocPhan().getTenHocPhan());
        System.out.println("----------------------------------------");
        System.out.println("Quiz:      " + String.format("%.2f", diem.getDiemQuiz()));
        System.out.println("Giua ky:   " + String.format("%.2f", diem.getDiemGiuaKy()));
        System.out.println("Cuoi ky:   " + String.format("%.2f", diem.getDiemCuoiKy()));
        System.out.println("Bai tap:   " + String.format("%.2f", diem.getDiemBaiTap()));
        System.out.println("Tong ket:  " + String.format("%.2f", diem.getDiemTongKet()) + " (" + diem.getDiemChu() + ")");
        System.out.println("========================================");
        
        System.out.println("\nNhap diem moi (Enter de giu nguyen):");
        
        try {
            // Sử dụng InputHelper để nhập điểm với validation
            double newQuiz = inputHelper.nhapSoThucTuyChon(
                "Diem quiz [" + String.format("%.2f", diem.getDiemQuiz()) + "]: ", 
                diem.getDiemQuiz(), 0, 10);
            diem.capNhatDiemQuiz(newQuiz);
            
            double newGiuaKy = inputHelper.nhapSoThucTuyChon(
                "Diem giua ky [" + String.format("%.2f", diem.getDiemGiuaKy()) + "]: ", 
                diem.getDiemGiuaKy(), 0, 10);
            diem.capNhatDiemGiuaKy(newGiuaKy);
            
            double newCuoiKy = inputHelper.nhapSoThucTuyChon(
                "Diem cuoi ky [" + String.format("%.2f", diem.getDiemCuoiKy()) + "]: ", 
                diem.getDiemCuoiKy(), 0, 10);
            diem.capNhatDiemCuoiKy(newCuoiKy);
            
            double newBaiTap = inputHelper.nhapSoThucTuyChon(
                "Diem bai tap [" + String.format("%.2f", diem.getDiemBaiTap()) + "]: ", 
                diem.getDiemBaiTap(), 0, 10);
            diem.capNhatDiemBaiTap(newBaiTap);
            
            // Tính lại điểm tổng kết
            tinhDiemService.tinhDiemTongKet(diem);
            
            // Cập nhật
            diemRepo.capNhat(diem);
            
            System.out.println("\n========================================");
            System.out.println("✓ CAP NHAT DIEM THANH CONG!");
            System.out.println("========================================");
            System.out.println("Diem tong ket moi: " + String.format("%.2f", diem.getDiemTongKet()) + " (" + diem.getDiemChu() + ")");
            System.out.println("Trang thai: " + (diem.isDaDat() ? "DAT" : "KHONG DAT"));
            System.out.println("========================================");
            
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Nguoi dung huy thao tac")) {
                System.out.println("\nDa huy thao tac sua diem.");
            } else {
                System.err.println("Loi: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void xemBangDiem() {
        System.out.print("\nMa sinh vien: ");
        String maSV = scanner.nextLine().trim();
        
        var svOpt = sinhVienRepo.timTheoMa(maSV);
        if (!svOpt.isPresent()) {
            System.out.println("Khong tim thay sinh vien!");
            return;
        }
        
        SinhVien sv = svOpt.get();
        var danhSachDiem = diemRepo.timTheoSinhVien(maSV);
        
        System.out.println("\n=== BANG DIEM CUA SINH VIEN ===");
        System.out.println("Ma SV: " + sv.getMa());
        System.out.println("Ho ten: " + sv.getHoTen());
        System.out.println("GPA: " + String.format("%.2f", sv.getGpa()));
        System.out.println("Xep loai: " + sv.getXepLoai());
        System.out.println("\nChi tiet diem:");
        System.out.println(String.format("%-10s %-35s %-7s %-7s %-7s %-7s %-8s %-6s",
                "Ma HP", "Ten hoc phan", "Quiz", "GK", "CK", "BT", "Tong", "Chu"));
        System.out.println("---------------------------------------------------------------------------------------------------");
        
        for (Diem diem : danhSachDiem) {
            HocPhan hp = diem.getHocPhan();
            System.out.println(String.format("%-10s %-35s %-7.2f %-7.2f %-7.2f %-7.2f %-8.2f %-6s",
                    hp.getMaHocPhan(),
                    hp.getTenHocPhan().length() > 35 ? hp.getTenHocPhan().substring(0, 32) + "..." : hp.getTenHocPhan(),
                    diem.getDiemQuiz(),
                    diem.getDiemGiuaKy(),
                    diem.getDiemCuoiKy(),
                    diem.getDiemBaiTap(),
                    diem.getDiemTongKet(),
                    diem.getDiemChu()));
        }
    }
    
    private void tinhGPAVaXepLoai() {
        System.out.println("\nDang tinh GPA va xep loai cho tat ca sinh vien...");
        
        var danhSachSinhVien = sinhVienRepo.layTatCa();
        var danhSachDiem = diemRepo.layTatCa();
        
        tinhDiemService.tinhGPAChoTatCaSinhVien(danhSachSinhVien, danhSachDiem);
        
        for (SinhVien sv : danhSachSinhVien) {
            xepLoaiService.xepLoaiHocLucChoSinhVien(sv);
        }
        
        // Lưu lại
        try {
            sinhVienRepo.luuFile();
            System.out.println("Hoan thanh!");
            System.out.println("Da tinh GPA va xep loai cho " + danhSachSinhVien.size() + " sinh vien");
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
}

