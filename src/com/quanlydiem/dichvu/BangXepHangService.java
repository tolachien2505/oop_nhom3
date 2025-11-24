package com.quanlydiem.dichvu;

import com.quanlydiem.model.nguoi.SinhVien;
import com.quanlydiem.model.hocphan.Diem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service BangXepHangService - Xử lý logic tạo bảng xếp hạng sinh viên
 */
public class BangXepHangService {
    
    private TinhDiemService tinhDiemService;
    private XepLoaiService xepLoaiService;
    
    public BangXepHangService() {
        this.tinhDiemService = new TinhDiemService();
        this.xepLoaiService = new XepLoaiService();
    }
    
    /**
     * Tạo bảng xếp hạng toàn trường
     * @param danhSachSinhVien Danh sách tất cả sinh viên
     * @param danhSachDiem Danh sách tất cả điểm
     * @return Danh sách sinh viên đã sắp xếp theo GPA giảm dần
     */
    public List<SinhVien> xepHangToanTruong(List<SinhVien> danhSachSinhVien, List<Diem> danhSachDiem) {
        // Tính GPA cho tất cả sinh viên
        tinhDiemService.tinhGPAChoTatCaSinhVien(danhSachSinhVien, danhSachDiem);
        
        // Xếp loại học lực cho tất cả sinh viên
        for (SinhVien sv : danhSachSinhVien) {
            xepLoaiService.xepLoaiHocLucChoSinhVien(sv);
        }
        
        // Sắp xếp theo GPA giảm dần
        return danhSachSinhVien.stream()
                .sorted(Comparator.comparingDouble(SinhVien::getGpa).reversed())
                .collect(Collectors.toList());
    }
    
    /**
     * Tạo bảng xếp hạng theo khoa
     * @param maKhoa Mã khoa
     * @param danhSachSinhVien Danh sách tất cả sinh viên
     * @param danhSachDiem Danh sách tất cả điểm
     * @return Danh sách sinh viên của khoa đã sắp xếp theo GPA giảm dần
     */
    public List<SinhVien> xepHangTheoKhoa(String maKhoa, List<SinhVien> danhSachSinhVien, List<Diem> danhSachDiem) {
        // Lọc sinh viên theo khoa
        List<SinhVien> sinhVienKhoa = danhSachSinhVien.stream()
                .filter(sv -> sv.getKhoa() != null && sv.getKhoa().getMaKhoa().equals(maKhoa))
                .collect(Collectors.toList());
        
        // Tính GPA và xếp loại
        tinhDiemService.tinhGPAChoTatCaSinhVien(sinhVienKhoa, danhSachDiem);
        for (SinhVien sv : sinhVienKhoa) {
            xepLoaiService.xepLoaiHocLucChoSinhVien(sv);
        }
        
        // Sắp xếp theo GPA giảm dần
        return sinhVienKhoa.stream()
                .sorted(Comparator.comparingDouble(SinhVien::getGpa).reversed())
                .collect(Collectors.toList());
    }
    
    /**
     * Tạo bảng xếp hạng theo lớp
     * @param maLop Mã lớp
     * @param danhSachSinhVien Danh sách tất cả sinh viên
     * @param danhSachDiem Danh sách tất cả điểm
     * @return Danh sách sinh viên của lớp đã sắp xếp theo GPA giảm dần
     */
    public List<SinhVien> xepHangTheoLop(String maLop, List<SinhVien> danhSachSinhVien, List<Diem> danhSachDiem) {
        // Lọc sinh viên theo lớp
        List<SinhVien> sinhVienLop = danhSachSinhVien.stream()
                .filter(sv -> sv.getLopHoc() != null && sv.getLopHoc().getMaLop().equals(maLop))
                .collect(Collectors.toList());
        
        // Tính GPA và xếp loại
        tinhDiemService.tinhGPAChoTatCaSinhVien(sinhVienLop, danhSachDiem);
        for (SinhVien sv : sinhVienLop) {
            xepLoaiService.xepLoaiHocLucChoSinhVien(sv);
        }
        
        // Sắp xếp theo GPA giảm dần
        return sinhVienLop.stream()
                .sorted(Comparator.comparingDouble(SinhVien::getGpa).reversed())
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy top N sinh viên có GPA cao nhất
     * @param n Số lượng sinh viên cần lấy
     * @param danhSachSinhVien Danh sách tất cả sinh viên
     * @param danhSachDiem Danh sách tất cả điểm
     * @return Danh sách top N sinh viên
     */
    public List<SinhVien> layTopN(int n, List<SinhVien> danhSachSinhVien, List<Diem> danhSachDiem) {
        List<SinhVien> xepHang = xepHangToanTruong(danhSachSinhVien, danhSachDiem);
        return xepHang.stream().limit(n).collect(Collectors.toList());
    }
    
    /**
     * Lấy top N sinh viên theo khoa
     * @param n Số lượng sinh viên cần lấy
     * @param maKhoa Mã khoa
     * @param danhSachSinhVien Danh sách tất cả sinh viên
     * @param danhSachDiem Danh sách tất cả điểm
     * @return Danh sách top N sinh viên của khoa
     */
    public List<SinhVien> layTopNTheoKhoa(int n, String maKhoa, List<SinhVien> danhSachSinhVien, List<Diem> danhSachDiem) {
        List<SinhVien> xepHang = xepHangTheoKhoa(maKhoa, danhSachSinhVien, danhSachDiem);
        return xepHang.stream().limit(n).collect(Collectors.toList());
    }
    
    /**
     * Lấy top N sinh viên theo lớp
     * @param n Số lượng sinh viên cần lấy
     * @param maLop Mã lớp
     * @param danhSachSinhVien Danh sách tất cả sinh viên
     * @param danhSachDiem Danh sách tất cả điểm
     * @return Danh sách top N sinh viên của lớp
     */
    public List<SinhVien> layTopNTheoLop(int n, String maLop, List<SinhVien> danhSachSinhVien, List<Diem> danhSachDiem) {
        List<SinhVien> xepHang = xepHangTheoLop(maLop, danhSachSinhVien, danhSachDiem);
        return xepHang.stream().limit(n).collect(Collectors.toList());
    }
    
    /**
     * Lọc bảng xếp hạng theo GPA tối thiểu
     * @param gpaToiThieu GPA tối thiểu
     * @param danhSachSinhVien Danh sách sinh viên đã xếp hạng
     * @return Danh sách sinh viên có GPA >= gpaToiThieu
     */
    public List<SinhVien> locTheoGPAToiThieu(double gpaToiThieu, List<SinhVien> danhSachSinhVien) {
        return danhSachSinhVien.stream()
                .filter(sv -> sv.getGpa() >= gpaToiThieu)
                .collect(Collectors.toList());
    }
    
    /**
     * Lọc sinh viên theo xếp loại
     * @param xepLoai Xếp loại cần lọc
     * @param danhSachSinhVien Danh sách sinh viên
     * @return Danh sách sinh viên có xếp loại tương ứng
     */
    public List<SinhVien> locTheoXepLoai(String xepLoai, List<SinhVien> danhSachSinhVien) {
        return danhSachSinhVien.stream()
                .filter(sv -> sv.getXepLoai().equalsIgnoreCase(xepLoai))
                .collect(Collectors.toList());
    }
    
    /**
     * Tìm thứ hạng của một sinh viên trong danh sách
     * @param maSinhVien Mã sinh viên
     * @param danhSachXepHang Danh sách đã xếp hạng
     * @return Thứ hạng của sinh viên (bắt đầu từ 1), -1 nếu không tìm thấy
     */
    public int timThuHang(String maSinhVien, List<SinhVien> danhSachXepHang) {
        for (int i = 0; i < danhSachXepHang.size(); i++) {
            if (danhSachXepHang.get(i).getMa().equals(maSinhVien)) {
                return i + 1;
            }
        }
        return -1;
    }
    
    /**
     * Tạo chuỗi hiển thị bảng xếp hạng
     * @param danhSachXepHang Danh sách đã xếp hạng
     * @param tieuDe Tiêu đề bảng xếp hạng
     * @return Chuỗi hiển thị bảng xếp hạng
     */
    public String taoBangXepHang(List<SinhVien> danhSachXepHang, String tieuDe) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===============================================\n");
        sb.append("  ").append(tieuDe).append("\n");
        sb.append("===============================================\n");
        sb.append(String.format("%-5s %-12s %-25s %-30s %-8s %-15s\n", 
                "TH", "Ma SV", "Ho ten", "Lop", "GPA", "Xep loai"));
        sb.append("------------------------------------------------------------------------------------------------\n");
        
        for (int i = 0; i < danhSachXepHang.size(); i++) {
            SinhVien sv = danhSachXepHang.get(i);
            sb.append(String.format("%-5d %-12s %-25s %-30s %-8.2f %-15s\n",
                    (i + 1),
                    sv.getMa(),
                    sv.getHoTen(),
                    sv.getLopHoc() != null ? sv.getLopHoc().getTenLop() : "N/A",
                    sv.getGpa(),
                    sv.getXepLoai()));
        }
        sb.append("================================================================================================\n");
        return sb.toString();
    }
}

