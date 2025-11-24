package com.quanlydiem;

import com.quanlydiem.menu.MenuChinh;
import com.quanlydiem.luutru.*;

/**
 * Lớp Main - Điểm khởi đầu của ứng dụng Quản Lý Điểm Sinh Viên
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("    HE THONG QUAN LY DIEM SINH VIEN");
        System.out.println("===============================================");
        System.out.println();
        
        try {
            // Khởi tạo các repository và load dữ liệu từ file
            KhoaRepository khoaRepo = new KhoaRepository();
            LopHocRepository lopHocRepo = new LopHocRepository(khoaRepo);
            GiangVienRepository giangVienRepo = new GiangVienRepository(khoaRepo);
            HocPhanRepository hocPhanRepo = new HocPhanRepository(giangVienRepo);
            SinhVienRepository sinhVienRepo = new SinhVienRepository(lopHocRepo, khoaRepo);
            DiemRepository diemRepo = new DiemRepository(sinhVienRepo, hocPhanRepo);
            
            // Khởi động menu chính
            MenuChinh menu = new MenuChinh(
                khoaRepo, lopHocRepo, giangVienRepo, 
                hocPhanRepo, sinhVienRepo, diemRepo
            );
            menu.hienThi();
            
        } catch (Exception e) {
            System.err.println("Loi khoi dong ung dung: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

