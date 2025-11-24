package com.quanlydiem.menu;

import com.quanlydiem.luutru.*;
import com.quanlydiem.model.nguoi.*;
import com.quanlydiem.model.hocphan.*;
import com.quanlydiem.model.tochuc.*;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Menu Quản lý - CRUD cho các entities
 */
public class MenuQuanLy {
    private Scanner scanner;
    private InputHelper inputHelper;
    private KhoaRepository khoaRepo;
    private LopHocRepository lopHocRepo;
    private GiangVienRepository giangVienRepo;
    private HocPhanRepository hocPhanRepo;
    private SinhVienRepository sinhVienRepo;
    
    public MenuQuanLy(Scanner scanner, KhoaRepository khoaRepo, LopHocRepository lopHocRepo,
                     GiangVienRepository giangVienRepo, HocPhanRepository hocPhanRepo,
                     SinhVienRepository sinhVienRepo) {
        this.scanner = scanner;
        this.inputHelper = new InputHelper(scanner);
        this.khoaRepo = khoaRepo;
        this.lopHocRepo = lopHocRepo;
        this.giangVienRepo = giangVienRepo;
        this.hocPhanRepo = hocPhanRepo;
        this.sinhVienRepo = sinhVienRepo;
    }
    
    public void hienThi() {
        System.out.println("\n===============================================");
        System.out.println("    QUAN LY DU LIEU");
        System.out.println("===============================================");
        System.out.println("1. Quan ly sinh vien");
        System.out.println("2. Quan ly giang vien");
        System.out.println("3. Quan ly hoc phan");
        System.out.println("4. Quan ly lop hoc");
        System.out.println("5. Quan ly khoa");
        System.out.println("0. Quay lai");
        System.out.println("===============================================");
        System.out.print("Nhap lua chon: ");
        
        try {
            int luaChon = Integer.parseInt(scanner.nextLine().trim());
            
            switch (luaChon) {
                case 1: quanLySinhVien(); break;
                case 2: quanLyGiangVien(); break;
                case 3: quanLyHocPhan(); break;
                case 4: quanLyLopHoc(); break;
                case 5: quanLyKhoa(); break;
                case 0: break;
                default: System.out.println("Lua chon khong hop le!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui long nhap so!");
        }
    }
    
    private void quanLySinhVien() {
        System.out.println("\n--- QUAN LY SINH VIEN ---");
        System.out.println("1. Them sinh vien");
        System.out.println("2. Xem danh sach");
        System.out.println("3. Cap nhat");
        System.out.println("4. Xoa");
        System.out.print("Lua chon: ");
        
        try {
            int luaChon = Integer.parseInt(scanner.nextLine().trim());
            switch (luaChon) {
                case 1: themSinhVien(); break;
                case 2: xemDanhSachSinhVien(); break;
                case 3: capNhatSinhVien(); break;
                case 4: xoaSinhVien(); break;
            }
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void themSinhVien() {
        try {
            System.out.println("\n--- THEM SINH VIEN MOI ---");
            
            String ma = inputHelper.nhapChuoi("Ma sinh vien: ");
            
            // Kiểm tra mã đã tồn tại chưa
            if (sinhVienRepo.tonTai(ma)) {
                System.out.println("Loi: Ma sinh vien da ton tai!");
                return;
            }
            
            String hoTen = inputHelper.nhapChuoi("Ho ten: ");
            
            // Nhập ngày sinh với xử lý lỗi tốt
            LocalDate ngaySinh = inputHelper.nhapNgayThang("Ngay sinh (yyyy-MM-dd, VD: 2004-01-15): ");
            
            String email = inputHelper.nhapChuoiTuyChon("Email: ");
            String sdt = inputHelper.nhapChuoiTuyChon("So dien thoai: ");
            
            // Nhập mã lớp với kiểm tra
            String maLop = inputHelper.nhapChuoi("Ma lop: ");
            var lop = lopHocRepo.timTheoMa(maLop).orElse(null);
            if (lop == null) {
                System.out.println("Canh bao: Khong tim thay lop voi ma: " + maLop);
                if (!inputHelper.xacNhan("Ban co muon tiep tuc? (y/n): ")) {
                    return;
                }
            }
            
            // Nhập mã khoa với kiểm tra
            String maKhoa = inputHelper.nhapChuoi("Ma khoa: ");
            var khoa = khoaRepo.timTheoMa(maKhoa).orElse(null);
            if (khoa == null) {
                System.out.println("Canh bao: Khong tim thay khoa voi ma: " + maKhoa);
                if (!inputHelper.xacNhan("Ban co muon tiep tuc? (y/n): ")) {
                    return;
                }
            }
            
            SinhVien sv = new SinhVien(ma, hoTen, ngaySinh, email, sdt, lop, khoa);
            
            sinhVienRepo.them(sv);
            System.out.println("\n========================================");
            System.out.println("✓ THEM SINH VIEN THANH CONG!");
            System.out.println("========================================");
            System.out.println("Ma sinh vien: " + sv.getMa());
            System.out.println("Ho ten: " + sv.getHoTen());
            System.out.println("Ngay sinh: " + sv.getNgaySinh());
            System.out.println("Lop: " + (lop != null ? lop.getTenLop() : "N/A"));
            System.out.println("Khoa: " + (khoa != null ? khoa.getTenKhoa() : "N/A"));
            System.out.println("========================================");
            
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Nguoi dung huy thao tac")) {
                System.out.println("\nDa huy thao tac them sinh vien.");
            } else {
                System.err.println("Loi: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void xemDanhSachSinhVien() {
        var danhSach = sinhVienRepo.layTatCa();
        System.out.println("\n Danh sach sinh vien: " + danhSach.size());
        System.out.println(String.format("%-12s %-25s %-30s %-8s %-15s",
                "Ma SV", "Ho ten", "Lop", "GPA", "Xep loai"));
        System.out.println("--------------------------------------------------------------------------------------------");
        for (var sv : danhSach) {
            System.out.println(String.format("%-12s %-25s %-30s %-8.2f %-15s",
                    sv.getMa(),
                    sv.getHoTen(),
                    sv.getLopHoc() != null ? sv.getLopHoc().getTenLop() : "N/A",
                    sv.getGpa(),
                    sv.getXepLoai()));
        }
    }
    
    private void capNhatSinhVien() {
        System.out.print("Ma sinh vien can cap nhat: ");
        String ma = scanner.nextLine().trim();
        var svOpt = sinhVienRepo.timTheoMa(ma);
        
        if (svOpt.isPresent()) {
            SinhVien sv = svOpt.get();
            System.out.println("Nhan Enter de giu nguyen gia tri cu");
            
            System.out.print("Ho ten [" + sv.getHoTen() + "]: ");
            String hoTen = scanner.nextLine().trim();
            if (!hoTen.isEmpty()) sv.setHoTen(hoTen);
            
            System.out.print("Email [" + sv.getEmail() + "]: ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) sv.setEmail(email);
            
            try {
                sinhVienRepo.capNhat(sv);
                System.out.println("Cap nhat thanh cong!");
            } catch (Exception e) {
                System.err.println("Loi: " + e.getMessage());
            }
        } else {
            System.out.println("Khong tim thay sinh vien!");
        }
    }
    
    private void xoaSinhVien() {
        System.out.print("Ma sinh vien can xoa: ");
        String ma = scanner.nextLine().trim();
        
        System.out.print("Ban co chac chan muon xoa? (y/n): ");
        String xacNhan = scanner.nextLine().trim();
        
        if (xacNhan.equalsIgnoreCase("y")) {
            try {
                sinhVienRepo.xoa(ma);
                System.out.println("Xoa thanh cong!");
            } catch (Exception e) {
                System.err.println("Loi: " + e.getMessage());
            }
        }
    }
    
    private void quanLyGiangVien() {
        System.out.println("\n--- QUAN LY GIANG VIEN ---");
        System.out.println("1. Them giang vien");
        System.out.println("2. Xem danh sach");
        System.out.println("3. Cap nhat");
        System.out.println("4. Xoa");
        System.out.println("0. Quay lai");
        System.out.print("Lua chon: ");
        
        try {
            int luaChon = Integer.parseInt(scanner.nextLine().trim());
            switch (luaChon) {
                case 1: themGiangVien(); break;
                case 2: xemDanhSachGiangVien(); break;
                case 3: capNhatGiangVien(); break;
                case 4: xoaGiangVien(); break;
                case 0: break;
                default: System.out.println("Lua chon khong hop le!");
            }
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void themGiangVien() {
        try {
            System.out.println("\n--- THEM GIANG VIEN MOI ---");
            
            String ma = inputHelper.nhapChuoi("Ma giang vien: ");
            if (giangVienRepo.tonTai(ma)) {
                System.out.println("Loi: Ma giang vien da ton tai!");
                return;
            }
            
            String hoTen = inputHelper.nhapChuoi("Ho ten: ");
            LocalDate ngaySinh = inputHelper.nhapNgayThang("Ngay sinh (yyyy-MM-dd, VD: 1980-05-15): ");
            String email = inputHelper.nhapChuoiTuyChon("Email: ");
            String sdt = inputHelper.nhapChuoiTuyChon("So dien thoai: ");
            
            String maKhoa = inputHelper.nhapChuoi("Ma khoa: ");
            var khoa = khoaRepo.timTheoMa(maKhoa).orElse(null);
            if (khoa == null) {
                System.out.println("Canh bao: Khong tim thay khoa voi ma: " + maKhoa);
                if (!inputHelper.xacNhan("Ban co muon tiep tuc? (y/n): ")) {
                    return;
                }
            }
            
            String chucVu = inputHelper.nhapChuoiTuyChon("Chuc vu: ");
            String hocVi = inputHelper.nhapChuoiTuyChon("Hoc vi: ");
            
            GiangVien gv = new GiangVien(ma, hoTen, ngaySinh, email, sdt, khoa, chucVu, hocVi);
            
            giangVienRepo.them(gv);
            System.out.println("\n========================================");
            System.out.println("✓ THEM GIANG VIEN THANH CONG!");
            System.out.println("========================================");
            System.out.println("Ma giang vien: " + gv.getMa());
            System.out.println("Ho ten: " + gv.getHoTen());
            System.out.println("Khoa: " + (khoa != null ? khoa.getTenKhoa() : "N/A"));
            System.out.println("========================================");
            
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Nguoi dung huy thao tac")) {
                System.out.println("\nDa huy thao tac them giang vien.");
            } else {
                System.err.println("Loi: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void capNhatGiangVien() {
        System.out.print("\nMa giang vien can cap nhat: ");
        String ma = scanner.nextLine().trim();
        var gvOpt = giangVienRepo.timTheoMa(ma);
        
        if (!gvOpt.isPresent()) {
            System.out.println("Khong tim thay giang vien!");
            return;
        }
        
        GiangVien gv = gvOpt.get();
        System.out.println("\nThong tin hien tai:");
        System.out.println("Ho ten: " + gv.getHoTen());
        System.out.println("Email: " + (gv.getEmail() != null ? gv.getEmail() : "N/A"));
        System.out.println("Chuc vu: " + (gv.getChucVu() != null ? gv.getChucVu() : "N/A"));
        System.out.println("\nNhap thong tin moi (Enter de giu nguyen):");
        
        String hoTen = inputHelper.nhapChuoiTuyChon("Ho ten [" + gv.getHoTen() + "]: ");
        if (!hoTen.isEmpty()) gv.setHoTen(hoTen);
        
        String email = inputHelper.nhapChuoiTuyChon("Email [" + (gv.getEmail() != null ? gv.getEmail() : "") + "]: ");
        if (!email.isEmpty()) gv.setEmail(email);
        
        String chucVu = inputHelper.nhapChuoiTuyChon("Chuc vu [" + (gv.getChucVu() != null ? gv.getChucVu() : "") + "]: ");
        if (!chucVu.isEmpty()) gv.setChucVu(chucVu);
        
        String hocVi = inputHelper.nhapChuoiTuyChon("Hoc vi [" + (gv.getHocVi() != null ? gv.getHocVi() : "") + "]: ");
        if (!hocVi.isEmpty()) gv.setHocVi(hocVi);
        
        try {
            giangVienRepo.capNhat(gv);
            System.out.println("\n✓ Cap nhat giang vien thanh cong!");
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void xoaGiangVien() {
        System.out.print("\nMa giang vien can xoa: ");
        String ma = scanner.nextLine().trim();
        
        var gvOpt = giangVienRepo.timTheoMa(ma);
        if (!gvOpt.isPresent()) {
            System.out.println("Khong tim thay giang vien!");
            return;
        }
        
        System.out.println("Giang vien: " + gvOpt.get().getHoTen() + " (" + ma + ")");
        if (!inputHelper.xacNhan("Ban co chac chan muon xoa? (y/n): ")) {
            return;
        }
        
        try {
            giangVienRepo.xoa(ma);
            System.out.println("✓ Xoa giang vien thanh cong!");
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void xemDanhSachGiangVien() {
        var danhSach = giangVienRepo.layTatCa();
        System.out.println("\nDanh sach giang vien: " + danhSach.size());
        System.out.println(String.format("%-10s %-25s %-25s %-15s %-15s",
                "Ma GV", "Ho ten", "Khoa", "Chuc vu", "Hoc vi"));
        System.out.println("---------------------------------------------------------------------------------------------");
        for (var gv : danhSach) {
            System.out.println(String.format("%-10s %-25s %-25s %-15s %-15s",
                    gv.getMa(),
                    gv.getHoTen(),
                    gv.getKhoa() != null ? gv.getKhoa().getTenKhoa() : "N/A",
                    gv.getChucVu() != null ? gv.getChucVu() : "N/A",
                    gv.getHocVi() != null ? gv.getHocVi() : "N/A"));
        }
    }
    
    private void quanLyHocPhan() {
        System.out.println("\n--- QUAN LY HOC PHAN ---");
        System.out.println("1. Them hoc phan");
        System.out.println("2. Xem danh sach");
        System.out.println("3. Cap nhat");
        System.out.println("4. Xoa");
        System.out.println("0. Quay lai");
        System.out.print("Lua chon: ");
        
        try {
            int luaChon = Integer.parseInt(scanner.nextLine().trim());
            switch (luaChon) {
                case 1: themHocPhan(); break;
                case 2: xemDanhSachHocPhan(); break;
                case 3: capNhatHocPhan(); break;
                case 4: xoaHocPhan(); break;
                case 0: break;
                default: System.out.println("Lua chon khong hop le!");
            }
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void themHocPhan() {
        try {
            System.out.println("\n--- THEM HOC PHAN MOI ---");
            
            String maHP = inputHelper.nhapChuoi("Ma hoc phan: ");
            if (hocPhanRepo.tonTai(maHP)) {
                System.out.println("Loi: Ma hoc phan da ton tai!");
                return;
            }
            
            String tenHP = inputHelper.nhapChuoi("Ten hoc phan: ");
            int soTinChi = inputHelper.nhapSoNguyen("So tin chi (1-10): ", 1, 10);
            
            String maGV = inputHelper.nhapChuoi("Ma giang vien: ");
            var gv = giangVienRepo.timTheoMa(maGV).orElse(null);
            if (gv == null) {
                System.out.println("Canh bao: Khong tim thay giang vien voi ma: " + maGV);
                if (!inputHelper.xacNhan("Ban co muon tiep tuc? (y/n): ")) {
                    return;
                }
            }
            
            System.out.println("\nNhap trong so diem (tong = 1.0):");
            double trongSoQuiz = inputHelper.nhapSoThuc("Trong so Quiz (0-1): ", 0, 1);
            double trongSoGiuaKy = inputHelper.nhapSoThuc("Trong so Giua ky (0-1): ", 0, 1);
            double trongSoCuoiKy = inputHelper.nhapSoThuc("Trong so Cuoi ky (0-1): ", 0, 1);
            double trongSoBaiTap = inputHelper.nhapSoThuc("Trong so Bai tap (0-1): ", 0, 1);
            
            double tongTrongSo = trongSoQuiz + trongSoGiuaKy + trongSoCuoiKy + trongSoBaiTap;
            if (Math.abs(tongTrongSo - 1.0) > 0.01) {
                System.out.println("Canh bao: Tong trong so = " + String.format("%.2f", tongTrongSo) + " (nen = 1.0)");
                if (!inputHelper.xacNhan("Ban co muon tiep tuc? (y/n): ")) {
                    return;
                }
            }
            
            HocPhan hp = new HocPhan(maHP, tenHP, soTinChi, gv, 
                                    trongSoQuiz, trongSoGiuaKy, trongSoCuoiKy, trongSoBaiTap);
            
            hocPhanRepo.them(hp);
            System.out.println("\n========================================");
            System.out.println("✓ THEM HOC PHAN THANH CONG!");
            System.out.println("========================================");
            System.out.println("Ma hoc phan: " + hp.getMaHocPhan());
            System.out.println("Ten hoc phan: " + hp.getTenHocPhan());
            System.out.println("So tin chi: " + hp.getSoTinChi());
            System.out.println("Giang vien: " + (gv != null ? gv.getHoTen() : "N/A"));
            System.out.println("========================================");
            
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Nguoi dung huy thao tac")) {
                System.out.println("\nDa huy thao tac them hoc phan.");
            } else {
                System.err.println("Loi: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void xemDanhSachHocPhan() {
        var danhSach = hocPhanRepo.layTatCa();
        System.out.println("\nDanh sach hoc phan: " + danhSach.size());
        System.out.println(String.format("%-10s %-35s %-10s %-25s",
                "Ma HP", "Ten hoc phan", "Tin chi", "Giang vien"));
        System.out.println("--------------------------------------------------------------------------------------");
        for (var hp : danhSach) {
            System.out.println(String.format("%-10s %-35s %-10d %-25s",
                    hp.getMaHocPhan(),
                    hp.getTenHocPhan(),
                    hp.getSoTinChi(),
                    hp.getGiangVien() != null ? hp.getGiangVien().getHoTen() : "N/A"));
        }
    }
    
    private void capNhatHocPhan() {
        System.out.print("\nMa hoc phan can cap nhat: ");
        String ma = scanner.nextLine().trim();
        var hpOpt = hocPhanRepo.timTheoMa(ma);
        
        if (!hpOpt.isPresent()) {
            System.out.println("Khong tim thay hoc phan!");
            return;
        }
        
        HocPhan hp = hpOpt.get();
        System.out.println("\nThong tin hien tai:");
        System.out.println("Ten hoc phan: " + hp.getTenHocPhan());
        System.out.println("So tin chi: " + hp.getSoTinChi());
        System.out.println("\nNhap thong tin moi (Enter de giu nguyen):");
        
        String tenHP = inputHelper.nhapChuoiTuyChon("Ten hoc phan [" + hp.getTenHocPhan() + "]: ");
        if (!tenHP.isEmpty()) hp.setTenHocPhan(tenHP);
        
        System.out.print("So tin chi [" + hp.getSoTinChi() + "] (Enter de giu nguyen): ");
        String tinChiStr = scanner.nextLine().trim();
        if (!tinChiStr.isEmpty()) {
            try {
                int tinChi = Integer.parseInt(tinChiStr);
                if (tinChi >= 1 && tinChi <= 10) {
                    hp.setSoTinChi(tinChi);
                } else {
                    System.out.println("Loi: So tin chi phai tu 1-10!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so nguyen!");
            }
        }
        
        try {
            hocPhanRepo.capNhat(hp);
            System.out.println("\n✓ Cap nhat hoc phan thanh cong!");
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void xoaHocPhan() {
        System.out.print("\nMa hoc phan can xoa: ");
        String ma = scanner.nextLine().trim();
        
        var hpOpt = hocPhanRepo.timTheoMa(ma);
        if (!hpOpt.isPresent()) {
            System.out.println("Khong tim thay hoc phan!");
            return;
        }
        
        System.out.println("Hoc phan: " + hpOpt.get().getTenHocPhan() + " (" + ma + ")");
        if (!inputHelper.xacNhan("Ban co chac chan muon xoa? (y/n): ")) {
            return;
        }
        
        try {
            hocPhanRepo.xoa(ma);
            System.out.println("✓ Xoa hoc phan thanh cong!");
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void quanLyLopHoc() {
        System.out.println("\n--- QUAN LY LOP HOC ---");
        System.out.println("1. Them lop hoc");
        System.out.println("2. Xem danh sach");
        System.out.println("3. Cap nhat");
        System.out.println("4. Xoa");
        System.out.println("0. Quay lai");
        System.out.print("Lua chon: ");
        
        try {
            int luaChon = Integer.parseInt(scanner.nextLine().trim());
            switch (luaChon) {
                case 1: themLopHoc(); break;
                case 2: xemDanhSachLopHoc(); break;
                case 3: capNhatLopHoc(); break;
                case 4: xoaLopHoc(); break;
                case 0: break;
                default: System.out.println("Lua chon khong hop le!");
            }
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void themLopHoc() {
        try {
            System.out.println("\n--- THEM LOP HOC MOI ---");
            
            String maLop = inputHelper.nhapChuoi("Ma lop: ");
            if (lopHocRepo.tonTai(maLop)) {
                System.out.println("Loi: Ma lop da ton tai!");
                return;
            }
            
            String tenLop = inputHelper.nhapChuoi("Ten lop: ");
            
            String maKhoa = inputHelper.nhapChuoi("Ma khoa: ");
            var khoa = khoaRepo.timTheoMa(maKhoa).orElse(null);
            if (khoa == null) {
                System.out.println("Canh bao: Khong tim thay khoa voi ma: " + maKhoa);
                if (!inputHelper.xacNhan("Ban co muon tiep tuc? (y/n): ")) {
                    return;
                }
            }
            
            int namNhapHoc = inputHelper.nhapSoNguyen("Nam nhap hoc (2000-2030): ", 2000, 2030);
            String giaoVienChuNhiem = inputHelper.nhapChuoiTuyChon("Giao vien chu nhiem: ");
            
            LopHoc lop = new LopHoc(maLop, tenLop, khoa, namNhapHoc, giaoVienChuNhiem);
            
            lopHocRepo.them(lop);
            System.out.println("\n========================================");
            System.out.println("✓ THEM LOP HOC THANH CONG!");
            System.out.println("========================================");
            System.out.println("Ma lop: " + lop.getMaLop());
            System.out.println("Ten lop: " + lop.getTenLop());
            System.out.println("Khoa: " + (khoa != null ? khoa.getTenKhoa() : "N/A"));
            System.out.println("Nam nhap hoc: " + lop.getNamNhapHoc());
            System.out.println("========================================");
            
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Nguoi dung huy thao tac")) {
                System.out.println("\nDa huy thao tac them lop hoc.");
            } else {
                System.err.println("Loi: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void xemDanhSachLopHoc() {
        var danhSach = lopHocRepo.layTatCa();
        System.out.println("\nDanh sach lop hoc: " + danhSach.size());
        System.out.println(String.format("%-10s %-30s %-25s %-10s",
                "Ma lop", "Ten lop", "Khoa", "Nam"));
        System.out.println("-----------------------------------------------------------------------------");
        for (var lop : danhSach) {
            System.out.println(String.format("%-10s %-30s %-25s %-10d",
                    lop.getMaLop(),
                    lop.getTenLop(),
                    lop.getKhoa() != null ? lop.getKhoa().getTenKhoa() : "N/A",
                    lop.getNamNhapHoc()));
        }
    }
    
    private void capNhatLopHoc() {
        System.out.print("\nMa lop can cap nhat: ");
        String ma = scanner.nextLine().trim();
        var lopOpt = lopHocRepo.timTheoMa(ma);
        
        if (!lopOpt.isPresent()) {
            System.out.println("Khong tim thay lop hoc!");
            return;
        }
        
        LopHoc lop = lopOpt.get();
        System.out.println("\nThong tin hien tai:");
        System.out.println("Ten lop: " + lop.getTenLop());
        System.out.println("Khoa: " + (lop.getKhoa() != null ? lop.getKhoa().getTenKhoa() : "N/A"));
        System.out.println("\nNhap thong tin moi (Enter de giu nguyen):");
        
        String tenLop = inputHelper.nhapChuoiTuyChon("Ten lop [" + lop.getTenLop() + "]: ");
        if (!tenLop.isEmpty()) lop.setTenLop(tenLop);
        
        String giaoVienCN = inputHelper.nhapChuoiTuyChon("Giao vien chu nhiem [" + (lop.getGiaoVienChuNhiem() != null ? lop.getGiaoVienChuNhiem() : "") + "]: ");
        if (!giaoVienCN.isEmpty()) lop.setGiaoVienChuNhiem(giaoVienCN);
        
        try {
            lopHocRepo.capNhat(lop);
            System.out.println("\n✓ Cap nhat lop hoc thanh cong!");
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void xoaLopHoc() {
        System.out.print("\nMa lop can xoa: ");
        String ma = scanner.nextLine().trim();
        
        var lopOpt = lopHocRepo.timTheoMa(ma);
        if (!lopOpt.isPresent()) {
            System.out.println("Khong tim thay lop hoc!");
            return;
        }
        
        System.out.println("Lop hoc: " + lopOpt.get().getTenLop() + " (" + ma + ")");
        if (!inputHelper.xacNhan("Ban co chac chan muon xoa? (y/n): ")) {
            return;
        }
        
        try {
            lopHocRepo.xoa(ma);
            System.out.println("✓ Xoa lop hoc thanh cong!");
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void quanLyKhoa() {
        System.out.println("\n--- QUAN LY KHOA ---");
        System.out.println("1. Them khoa");
        System.out.println("2. Xem danh sach");
        System.out.println("3. Cap nhat");
        System.out.println("4. Xoa");
        System.out.println("0. Quay lai");
        System.out.print("Lua chon: ");
        
        try {
            int luaChon = Integer.parseInt(scanner.nextLine().trim());
            switch (luaChon) {
                case 1: themKhoa(); break;
                case 2: xemDanhSachKhoa(); break;
                case 3: capNhatKhoa(); break;
                case 4: xoaKhoa(); break;
                case 0: break;
                default: System.out.println("Lua chon khong hop le!");
            }
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void themKhoa() {
        try {
            System.out.println("\n--- THEM KHOA MOI ---");
            
            String maKhoa = inputHelper.nhapChuoi("Ma khoa: ");
            if (khoaRepo.tonTai(maKhoa)) {
                System.out.println("Loi: Ma khoa da ton tai!");
                return;
            }
            
            String tenKhoa = inputHelper.nhapChuoi("Ten khoa: ");
            String truongKhoa = inputHelper.nhapChuoiTuyChon("Truong khoa: ");
            String dienThoai = inputHelper.nhapChuoiTuyChon("Dien thoai: ");
            
            Khoa khoa = new Khoa(maKhoa, tenKhoa, truongKhoa, dienThoai);
            
            khoaRepo.them(khoa);
            System.out.println("\n========================================");
            System.out.println("✓ THEM KHOA THANH CONG!");
            System.out.println("========================================");
            System.out.println("Ma khoa: " + khoa.getMaKhoa());
            System.out.println("Ten khoa: " + khoa.getTenKhoa());
            System.out.println("Truong khoa: " + (khoa.getTruongKhoa() != null ? khoa.getTruongKhoa() : "N/A"));
            System.out.println("========================================");
            
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Nguoi dung huy thao tac")) {
                System.out.println("\nDa huy thao tac them khoa.");
            } else {
                System.err.println("Loi: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void xemDanhSachKhoa() {
        var danhSach = khoaRepo.layTatCa();
        System.out.println("\nDanh sach khoa: " + danhSach.size());
        System.out.println(String.format("%-10s %-30s %-25s %-15s",
                "Ma khoa", "Ten khoa", "Truong khoa", "Dien thoai"));
        System.out.println("--------------------------------------------------------------------------------------------");
        for (var khoa : danhSach) {
            System.out.println(String.format("%-10s %-30s %-25s %-15s",
                    khoa.getMaKhoa(),
                    khoa.getTenKhoa(),
                    khoa.getTruongKhoa() != null ? khoa.getTruongKhoa() : "N/A",
                    khoa.getDienThoai() != null ? khoa.getDienThoai() : "N/A"));
        }
    }
    
    private void capNhatKhoa() {
        System.out.print("\nMa khoa can cap nhat: ");
        String ma = scanner.nextLine().trim();
        var khoaOpt = khoaRepo.timTheoMa(ma);
        
        if (!khoaOpt.isPresent()) {
            System.out.println("Khong tim thay khoa!");
            return;
        }
        
        Khoa khoa = khoaOpt.get();
        System.out.println("\nThong tin hien tai:");
        System.out.println("Ten khoa: " + khoa.getTenKhoa());
        System.out.println("Truong khoa: " + (khoa.getTruongKhoa() != null ? khoa.getTruongKhoa() : "N/A"));
        System.out.println("\nNhap thong tin moi (Enter de giu nguyen):");
        
        String tenKhoa = inputHelper.nhapChuoiTuyChon("Ten khoa [" + khoa.getTenKhoa() + "]: ");
        if (!tenKhoa.isEmpty()) khoa.setTenKhoa(tenKhoa);
        
        String truongKhoa = inputHelper.nhapChuoiTuyChon("Truong khoa [" + (khoa.getTruongKhoa() != null ? khoa.getTruongKhoa() : "") + "]: ");
        if (!truongKhoa.isEmpty()) khoa.setTruongKhoa(truongKhoa);
        
        String dienThoai = inputHelper.nhapChuoiTuyChon("Dien thoai [" + (khoa.getDienThoai() != null ? khoa.getDienThoai() : "") + "]: ");
        if (!dienThoai.isEmpty()) khoa.setDienThoai(dienThoai);
        
        try {
            khoaRepo.capNhat(khoa);
            System.out.println("\n✓ Cap nhat khoa thanh cong!");
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
    
    private void xoaKhoa() {
        System.out.print("\nMa khoa can xoa: ");
        String ma = scanner.nextLine().trim();
        
        var khoaOpt = khoaRepo.timTheoMa(ma);
        if (!khoaOpt.isPresent()) {
            System.out.println("Khong tim thay khoa!");
            return;
        }
        
        System.out.println("Khoa: " + khoaOpt.get().getTenKhoa() + " (" + ma + ")");
        System.out.println("Canh bao: Xoa khoa co the anh huong den lop hoc va sinh vien!");
        if (!inputHelper.xacNhan("Ban co chac chan muon xoa? (y/n): ")) {
            return;
        }
        
        try {
            khoaRepo.xoa(ma);
            System.out.println("✓ Xoa khoa thanh cong!");
        } catch (Exception e) {
            System.err.println("Loi: " + e.getMessage());
        }
    }
}

