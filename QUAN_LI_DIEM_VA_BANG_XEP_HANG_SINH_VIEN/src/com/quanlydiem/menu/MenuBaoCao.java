package com.quanlydiem.menu;

import com.quanlydiem.luutru.*;
import com.quanlydiem.model.nguoi.SinhVien;
import com.quanlydiem.model.hocphan.HocPhan;
import com.quanlydiem.dichvu.*;
import com.quanlydiem.model.hocphan.Diem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Menu Báo cáo và Thống kê
 */
public class MenuBaoCao {
    private Scanner scanner;
    private SinhVienRepository sinhVienRepo;
    private HocPhanRepository hocPhanRepo;
    private DiemRepository diemRepo;
    private ThongKeService thongKeService;
    private BangXepHangService bangXepHangService;
    
    public MenuBaoCao(Scanner scanner, SinhVienRepository sinhVienRepo,
                     HocPhanRepository hocPhanRepo, DiemRepository diemRepo,
                     ThongKeService thongKeService, BangXepHangService bangXepHangService) {
        this.scanner = scanner;
        this.sinhVienRepo = sinhVienRepo;
        this.hocPhanRepo = hocPhanRepo;
        this.diemRepo = diemRepo;
        this.thongKeService = thongKeService;
        this.bangXepHangService = bangXepHangService;
    }
    
    public void hienThi() {
        System.out.println("\n===============================================");
        System.out.println("    BAO CAO VA THONG KE");
        System.out.println("===============================================");
        System.out.println("1. Bao cao tong hop");
        System.out.println("2. Thong ke ty le xep loai");
        System.out.println("3. Danh sach sinh vien hoc lai");
        System.out.println("4. Histogram phan bo diem GPA");
        System.out.println("5. Histogram phan bo diem hoc phan");
        System.out.println("6. Xuat bang xep hang ra CSV");
        System.out.println("0. Quay lai");
        System.out.println("===============================================");
        System.out.print("Nhap lua chon: ");
        
        try {
            int luaChon = Integer.parseInt(scanner.nextLine().trim());
            
            switch (luaChon) {
                case 1: baoCaoTongHop(); break;
                case 2: thongKeTyLeXepLoai(); break;
                case 3: danhSachHocLai(); break;
                case 4: histogramGPA(); break;
                case 5: histogramHocPhan(); break;
                case 6: xuatBangXepHangCSV(); break;
                case 0: break;
                default: System.out.println("Lua chon khong hop le!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui long nhap so!");
        }
    }
    
    private void baoCaoTongHop() {
        var danhSachSinhVien = sinhVienRepo.layTatCa();
        var danhSachDiem = diemRepo.layTatCa();
        
        String baoCao = thongKeService.taoBaoCaoTongHop(danhSachSinhVien, danhSachDiem);
        System.out.println(baoCao);
    }
    
    private void thongKeTyLeXepLoai() {
        var danhSachSinhVien = sinhVienRepo.layTatCa();
        
        Map<String, Long> soLuong = thongKeService.thongKeSoLuongXepLoai(danhSachSinhVien);
        Map<String, Double> tyLe = thongKeService.thongKeTyLeXepLoai(danhSachSinhVien);
        
        System.out.println("\n===============================================");
        System.out.println("   THONG KE TY LE XEP LOAI");
        System.out.println("===============================================");
        System.out.println(String.format("%-20s %-15s %-15s", "Xep loai", "So luong", "Ty le (%)"));
        System.out.println("-----------------------------------------------");
        
        String[] cacXepLoai = {"Xuat sac", "Gioi", "Kha", "Trung binh", "Yeu"};
        for (String xepLoai : cacXepLoai) {
            System.out.println(String.format("%-20s %-15d %-15.2f",
                    xepLoai,
                    soLuong.getOrDefault(xepLoai, 0L),
                    tyLe.getOrDefault(xepLoai, 0.0)));
        }
        System.out.println("===============================================");
        System.out.println(String.format("Tong: %d sinh vien", danhSachSinhVien.size()));
        System.out.println("===============================================");
    }
    
    private void danhSachHocLai() {
        var danhSachDiem = diemRepo.layTatCa();
        var danhSachHocLai = thongKeService.danhSachHocLai(danhSachDiem);
        
        System.out.println("\n===============================================");
        System.out.println("   DANH SACH SINH VIEN CAN HOC LAI");
        System.out.println("===============================================");
        System.out.println("Tong so: " + danhSachHocLai.size() + " sinh vien\n");
        
        for (Map.Entry<SinhVien, List<HocPhan>> entry : danhSachHocLai) {
            SinhVien sv = entry.getKey();
            List<HocPhan> cacMonHocLai = entry.getValue();
            
            System.out.println(String.format("%-12s - %-25s (GPA: %.2f)",
                    sv.getMa(), sv.getHoTen(), sv.getGpa()));
            System.out.println("  Mon hoc lai:");
            for (HocPhan hp : cacMonHocLai) {
                System.out.println("    - " + hp.getMaHocPhan() + ": " + hp.getTenHocPhan());
            }
            System.out.println();
        }
        System.out.println("===============================================");
    }
    
    private void histogramGPA() {
        var danhSachSinhVien = sinhVienRepo.layTatCa();
        String histogram = thongKeService.taoHistogramPhanBoDiem(danhSachSinhVien);
        System.out.println(histogram);
    }
    
    private void histogramHocPhan() {
        System.out.print("\nNhap ma hoc phan: ");
        String maHP = scanner.nextLine().trim();
        
        var hpOpt = hocPhanRepo.timTheoMa(maHP);
        if (!hpOpt.isPresent()) {
            System.out.println("Khong tim thay hoc phan!");
            return;
        }
        
        HocPhan hp = hpOpt.get();
        var danhSachDiem = diemRepo.layTatCa();
        
        String histogram = thongKeService.taoHistogramTheoHocPhan(maHP, hp.getTenHocPhan(), danhSachDiem);
        System.out.println(histogram);
    }
    
    private void xuatBangXepHangCSV() {
        System.out.println("\n1. Xuat bang xep hang toan truong");
        System.out.println("2. Xuat bang xep hang theo khoa");
        System.out.println("3. Xuat bang xep hang theo lop");
        System.out.print("Lua chon: ");
        
        try {
            int luaChon = Integer.parseInt(scanner.nextLine().trim());
            List<SinhVien> xepHang = null;
            String tenFile = null;
            
            var danhSachSinhVien = sinhVienRepo.layTatCa();
            var danhSachDiem = diemRepo.layTatCa();
            
            switch (luaChon) {
                case 1:
                    xepHang = bangXepHangService.xepHangToanTruong(danhSachSinhVien, danhSachDiem);
                    tenFile = "data/bang_xep_hang_toan_truong.csv";
                    break;
                case 2:
                    System.out.print("Nhap ma khoa: ");
                    String maKhoa = scanner.nextLine().trim();
                    xepHang = bangXepHangService.xepHangTheoKhoa(maKhoa, danhSachSinhVien, danhSachDiem);
                    tenFile = "data/bang_xep_hang_khoa_" + maKhoa + ".csv";
                    break;
                case 3:
                    System.out.print("Nhap ma lop: ");
                    String maLop = scanner.nextLine().trim();
                    xepHang = bangXepHangService.xepHangTheoLop(maLop, danhSachSinhVien, danhSachDiem);
                    tenFile = "data/bang_xep_hang_lop_" + maLop + ".csv";
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
                    return;
            }
            
            if (xepHang != null && tenFile != null) {
                xuatCSV(xepHang, tenFile);
                System.out.println("\nXuat file thanh cong: " + tenFile);
            }
            
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void xuatCSV(List<SinhVien> danhSach, String tenFile) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tenFile))) {
            // Header
            writer.write("ThuHang,MaSinhVien,HoTen,Lop,Khoa,GPA,XepLoai\n");
            
            // Data
            for (int i = 0; i < danhSach.size(); i++) {
                SinhVien sv = danhSach.get(i);
                writer.write(String.format("%d,%s,%s,%s,%s,%.2f,%s\n",
                        (i + 1),
                        sv.getMa(),
                        sv.getHoTen(),
                        sv.getLopHoc() != null ? sv.getLopHoc().getTenLop() : "N/A",
                        sv.getKhoa() != null ? sv.getKhoa().getTenKhoa() : "N/A",
                        sv.getGpa(),
                        sv.getXepLoai()));
            }
        }
    }
}

