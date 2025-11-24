package com.quanlydiem.menu;

import com.quanlydiem.luutru.*;
import com.quanlydiem.dichvu.*;

import java.util.Scanner;

/**
 * Lớp MenuChinh - Menu chính của ứng dụng
 */
public class MenuChinh {
    private Scanner scanner;
    
    // Repositories
    private KhoaRepository khoaRepo;
    private LopHocRepository lopHocRepo;
    private GiangVienRepository giangVienRepo;
    private HocPhanRepository hocPhanRepo;
    private SinhVienRepository sinhVienRepo;
    private DiemRepository diemRepo;
    
    // Services
    private TinhDiemService tinhDiemService;
    private XepLoaiService xepLoaiService;
    private BangXepHangService bangXepHangService;
    private ThongKeService thongKeService;
    
    // Sub-menus
    private MenuQuanLy menuQuanLy;
    private MenuDiem menuDiem;
    private MenuXepHang menuXepHang;
    private MenuBaoCao menuBaoCao;
    
    public MenuChinh(KhoaRepository khoaRepo, LopHocRepository lopHocRepo,
                     GiangVienRepository giangVienRepo, HocPhanRepository hocPhanRepo,
                     SinhVienRepository sinhVienRepo, DiemRepository diemRepo) {
        this.scanner = new Scanner(System.in);
        
        this.khoaRepo = khoaRepo;
        this.lopHocRepo = lopHocRepo;
        this.giangVienRepo = giangVienRepo;
        this.hocPhanRepo = hocPhanRepo;
        this.sinhVienRepo = sinhVienRepo;
        this.diemRepo = diemRepo;
        
        // Khởi tạo services
        this.tinhDiemService = new TinhDiemService();
        this.xepLoaiService = new XepLoaiService();
        this.bangXepHangService = new BangXepHangService();
        this.thongKeService = new ThongKeService();
        
        // Khởi tạo sub-menus
        this.menuQuanLy = new MenuQuanLy(scanner, khoaRepo, lopHocRepo, giangVienRepo, 
                                         hocPhanRepo, sinhVienRepo);
        this.menuDiem = new MenuDiem(scanner, sinhVienRepo, hocPhanRepo, diemRepo, 
                                     tinhDiemService, xepLoaiService);
        this.menuXepHang = new MenuXepHang(scanner, khoaRepo, lopHocRepo, sinhVienRepo, 
                                           diemRepo, bangXepHangService);
        this.menuBaoCao = new MenuBaoCao(scanner, sinhVienRepo, hocPhanRepo, diemRepo, 
                                         thongKeService, bangXepHangService);
    }
    
    public void hienThi() {
        boolean tiepTuc = true;
        
        while (tiepTuc) {
            hienThiMenuChinh();
            
            try {
                int luaChon = Integer.parseInt(scanner.nextLine().trim());
                
                switch (luaChon) {
                    case 1:
                        menuQuanLy.hienThi();
                        break;
                    case 2:
                        menuDiem.hienThi();
                        break;
                    case 3:
                        menuXepHang.hienThi();
                        break;
                    case 4:
                        menuBaoCao.hienThi();
                        break;
                    case 5:
                        timKiem();
                        break;
                    case 6:
                        luuDuLieu();
                        break;
                    case 0:
                        tiepTuc = false;
                        luuDuLieu();
                        System.out.println("\nCam on ban da su dung he thong!");
                        System.out.println("Hen gap lai!");
                        break;
                    default:
                        System.out.println("\nLua chon khong hop le!");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nVui long nhap so!");
            } catch (Exception e) {
                System.err.println("\nLoi: " + e.getMessage());
                e.printStackTrace();
            }
            
            if (tiepTuc) {
                System.out.println("\nNhan Enter de tiep tuc...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private void hienThiMenuChinh() {
        System.out.println("\n===============================================");
        System.out.println("    MENU CHINH - HE THONG QUAN LY DIEM");
        System.out.println("===============================================");
        System.out.println("1. Quan ly (Khoa/Lop/Giang vien/Hoc phan/Sinh vien)");
        System.out.println("2. Quan ly diem");
        System.out.println("3. Bang xep hang");
        System.out.println("4. Bao cao va thong ke");
        System.out.println("5. Tim kiem");
        System.out.println("6. Luu du lieu");
        System.out.println("0. Thoat");
        System.out.println("===============================================");
        System.out.print("Nhap lua chon cua ban: ");
    }
    
    private void timKiem() {
        System.out.println("\n===============================================");
        System.out.println("    TIM KIEM");
        System.out.println("===============================================");
        System.out.println("1. Tim kiem sinh vien");
        System.out.println("2. Tim kiem hoc phan");
        System.out.println("3. Tim kiem giang vien");
        System.out.println("0. Quay lai");
        System.out.println("===============================================");
        System.out.print("Nhap lua chon: ");
        
        try {
            int luaChon = Integer.parseInt(scanner.nextLine().trim());
            
            switch (luaChon) {
                case 1:
                    timKiemSinhVien();
                    break;
                case 2:
                    timKiemHocPhan();
                    break;
                case 3:
                    timKiemGiangVien();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui long nhap so!");
        }
    }
    
    private void timKiemSinhVien() {
        System.out.print("\nNhap ten hoac ma sinh vien: ");
        String tuKhoa = scanner.nextLine().trim();
        
        // Tìm theo mã trước
        var timTheoMa = sinhVienRepo.timTheoMa(tuKhoa);
        var ketQua = new java.util.ArrayList<>(sinhVienRepo.timKiemMo(tuKhoa));
        
        // Nếu tìm thấy theo mã và chưa có trong kết quả, thêm vào
        if (timTheoMa.isPresent() && !ketQua.contains(timTheoMa.get())) {
            ketQua.add(0, timTheoMa.get());
        }
        
        System.out.println("\nKet qua tim kiem: " + ketQua.size() + " sinh vien");
        if (!ketQua.isEmpty()) {
            System.out.println(String.format("%-12s %-25s %-30s %-25s %-8s",
                    "Ma SV", "Ho ten", "Lop", "Khoa", "GPA"));
            System.out.println("----------------------------------------------------------------------------------------------");
            for (var sv : ketQua) {
                System.out.println(String.format("%-12s %-25s %-30s %-25s %-8.2f",
                        sv.getMa(),
                        sv.getHoTen(),
                        sv.getLopHoc() != null ? sv.getLopHoc().getTenLop() : "N/A",
                        sv.getKhoa() != null ? sv.getKhoa().getTenKhoa() : "N/A",
                        sv.getGpa()));
            }
        }
    }
    
    private void timKiemHocPhan() {
        System.out.print("\nNhap ten hoac ma hoc phan can tim: ");
        String tuKhoa = scanner.nextLine().trim();
        
        // Tìm theo mã trước
        var timTheoMa = hocPhanRepo.timTheoMa(tuKhoa);
        var ketQua = new java.util.ArrayList<>(hocPhanRepo.timTheoTen(tuKhoa));
        
        // Nếu tìm thấy theo mã và chưa có trong kết quả, thêm vào đầu
        if (timTheoMa.isPresent() && !ketQua.contains(timTheoMa.get())) {
            ketQua.add(0, timTheoMa.get());
        }
        
        System.out.println("\nKet qua tim kiem: " + ketQua.size() + " hoc phan");
        if (!ketQua.isEmpty()) {
            System.out.println(String.format("%-10s %-35s %-10s %-25s",
                    "Ma HP", "Ten hoc phan", "Tin chi", "Giang vien"));
            System.out.println("--------------------------------------------------------------------------------------");
            for (var hp : ketQua) {
                System.out.println(String.format("%-10s %-35s %-10d %-25s",
                        hp.getMaHocPhan(),
                        hp.getTenHocPhan(),
                        hp.getSoTinChi(),
                        hp.getGiangVien() != null ? hp.getGiangVien().getHoTen() : "N/A"));
            }
        }
    }
    
    private void timKiemGiangVien() {
        System.out.print("\nNhap ten hoac ma giang vien can tim: ");
        String tuKhoa = scanner.nextLine().trim();
        
        // Tìm theo mã trước
        var timTheoMa = giangVienRepo.timTheoMa(tuKhoa);
        var ketQua = new java.util.ArrayList<>(giangVienRepo.timTheoTen(tuKhoa));
        
        // Nếu tìm thấy theo mã và chưa có trong kết quả, thêm vào đầu
        if (timTheoMa.isPresent() && !ketQua.contains(timTheoMa.get())) {
            ketQua.add(0, timTheoMa.get());
        }
        
        System.out.println("\nKet qua tim kiem: " + ketQua.size() + " giang vien");
        if (!ketQua.isEmpty()) {
            System.out.println(String.format("%-10s %-25s %-25s %-15s",
                    "Ma GV", "Ho ten", "Khoa", "Hoc vi"));
            System.out.println("-----------------------------------------------------------------------------");
            for (var gv : ketQua) {
                System.out.println(String.format("%-10s %-25s %-25s %-15s",
                        gv.getMa(),
                        gv.getHoTen(),
                        gv.getKhoa() != null ? gv.getKhoa().getTenKhoa() : "N/A",
                        gv.getHocVi() != null ? gv.getHocVi() : "N/A"));
            }
        }
    }
    
    private void luuDuLieu() {
        try {
            System.out.print("\nDang luu du lieu...");
            khoaRepo.luuFile();
            lopHocRepo.luuFile();
            giangVienRepo.luuFile();
            hocPhanRepo.luuFile();
            sinhVienRepo.luuFile();
            diemRepo.luuFile();
            System.out.println(" Hoan thanh!");
        } catch (Exception e) {
            System.err.println("\nLoi khi luu du lieu: " + e.getMessage());
        }
    }
}

