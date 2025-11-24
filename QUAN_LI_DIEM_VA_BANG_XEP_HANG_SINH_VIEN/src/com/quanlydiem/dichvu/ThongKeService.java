package com.quanlydiem.dichvu;

import com.quanlydiem.model.nguoi.SinhVien;
import com.quanlydiem.model.hocphan.Diem;
import com.quanlydiem.model.hocphan.HocPhan;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service ThongKeService - Xử lý logic thống kê và báo cáo
 */
public class ThongKeService {
    
    private XepLoaiService xepLoaiService;
    private TinhDiemService tinhDiemService;
    
    public ThongKeService() {
        this.xepLoaiService = new XepLoaiService();
        this.tinhDiemService = new TinhDiemService();
    }
    
    /**
     * Thống kê tỷ lệ xếp loại học lực
     * @param danhSachSinhVien Danh sách sinh viên
     * @return Map chứa tỷ lệ từng xếp loại
     */
    public Map<String, Double> thongKeTyLeXepLoai(List<SinhVien> danhSachSinhVien) {
        Map<String, Double> tyLe = new HashMap<>();
        int tongSo = danhSachSinhVien.size();
        
        if (tongSo == 0) {
            return tyLe;
        }
        
        // Đếm số sinh viên ở từng xếp loại
        Map<String, Long> soLuong = danhSachSinhVien.stream()
                .collect(Collectors.groupingBy(SinhVien::getXepLoai, Collectors.counting()));
        
        // Tính tỷ lệ phần trăm
        for (Map.Entry<String, Long> entry : soLuong.entrySet()) {
            double phanTram = (entry.getValue() * 100.0) / tongSo;
            tyLe.put(entry.getKey(), phanTram);
        }
        
        // Đảm bảo tất cả các xếp loại đều có trong map
        String[] cacXepLoai = {"Xuat sac", "Gioi", "Kha", "Trung binh", "Yeu"};
        for (String xepLoai : cacXepLoai) {
            tyLe.putIfAbsent(xepLoai, 0.0);
        }
        
        return tyLe;
    }
    
    /**
     * Thống kê số lượng sinh viên theo xếp loại
     * @param danhSachSinhVien Danh sách sinh viên
     * @return Map chứa số lượng từng xếp loại
     */
    public Map<String, Long> thongKeSoLuongXepLoai(List<SinhVien> danhSachSinhVien) {
        Map<String, Long> soLuong = danhSachSinhVien.stream()
                .collect(Collectors.groupingBy(SinhVien::getXepLoai, Collectors.counting()));
        
        // Đảm bảo tất cả các xếp loại đều có trong map
        String[] cacXepLoai = {"Xuat sac", "Gioi", "Kha", "Trung binh", "Yeu"};
        for (String xepLoai : cacXepLoai) {
            soLuong.putIfAbsent(xepLoai, 0L);
        }
        
        return soLuong;
    }
    
    /**
     * Danh sách sinh viên cần học lại
     * @param danhSachDiem Danh sách điểm
     * @return Danh sách sinh viên có môn học không đạt
     */
    public List<Map.Entry<SinhVien, List<HocPhan>>> danhSachHocLai(List<Diem> danhSachDiem) {
        Map<SinhVien, List<HocPhan>> sinhVienHocLai = new HashMap<>();
        
        for (Diem diem : danhSachDiem) {
            if (!diem.isDaDat() && diem.getSinhVien() != null && diem.getHocPhan() != null) {
                sinhVienHocLai.computeIfAbsent(diem.getSinhVien(), k -> new ArrayList<>())
                              .add(diem.getHocPhan());
            }
        }
        
        return new ArrayList<>(sinhVienHocLai.entrySet());
    }
    
    /**
     * Thống kê điểm trung bình của từng học phần
     * @param danhSachDiem Danh sách điểm
     * @return Map chứa điểm trung bình của từng học phần
     */
    public Map<String, Double> thongKeDiemTrungBinhHocPhan(List<Diem> danhSachDiem) {
        Map<String, List<Double>> diemTheoHocPhan = new HashMap<>();
        
        for (Diem diem : danhSachDiem) {
            if (diem.getHocPhan() != null) {
                String maHocPhan = diem.getHocPhan().getMaHocPhan();
                diemTheoHocPhan.computeIfAbsent(maHocPhan, k -> new ArrayList<>())
                              .add(diem.getDiemTongKet());
            }
        }
        
        Map<String, Double> diemTrungBinh = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : diemTheoHocPhan.entrySet()) {
            double tb = entry.getValue().stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
            diemTrungBinh.put(entry.getKey(), tb);
        }
        
        return diemTrungBinh;
    }
    
    /**
     * Tạo histogram phân bố điểm (text-based)
     * @param danhSachSinhVien Danh sách sinh viên
     * @return Chuỗi hiển thị histogram
     */
    public String taoHistogramPhanBoDiem(List<SinhVien> danhSachSinhVien) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===============================================\n");
        sb.append("   HISTOGRAM PHAN BO DIEM GPA\n");
        sb.append("===============================================\n\n");
        
        // Phân loại GPA vào các khoảng (theo thang 10.0)
        int[] khoang = new int[11]; // 0-0.9, 1.0-1.9, ..., 9.0-10.0
        
        for (SinhVien sv : danhSachSinhVien) {
            double gpa = sv.getGpa();
            int index = Math.min((int) gpa, 10);
            if (gpa >= 10.0) index = 10; // GPA = 10.0 vào khoảng 10.0
            khoang[index]++;
        }
        
        // Tìm giá trị lớn nhất để scale
        int maxCount = Arrays.stream(khoang).max().orElse(1);
        int maxBarLength = 50;
        
        // Tạo các khoảng GPA (theo thang 10.0)
        String[] nhanKhoang = {
            "0.0 - 0.9",
            "1.0 - 1.9",
            "2.0 - 2.9",
            "3.0 - 3.9",
            "4.0 - 4.9",
            "5.0 - 5.9",
            "6.0 - 6.9",
            "7.0 - 7.9",
            "8.0 - 8.9",
            "9.0 - 9.9",
            "10.0    "
        };
        
        // Đếm lại theo thang 10.0
        int[] khoang10 = new int[11];
        for (SinhVien sv : danhSachSinhVien) {
            double gpa = sv.getGpa();
            if (gpa < 1.0) khoang10[0]++;
            else if (gpa < 2.0) khoang10[1]++;
            else if (gpa < 3.0) khoang10[2]++;
            else if (gpa < 4.0) khoang10[3]++;
            else if (gpa < 5.0) khoang10[4]++;
            else if (gpa < 6.0) khoang10[5]++;
            else if (gpa < 7.0) khoang10[6]++;
            else if (gpa < 8.0) khoang10[7]++;
            else if (gpa < 9.0) khoang10[8]++;
            else if (gpa < 10.0) khoang10[9]++;
            else khoang10[10]++;
        }
        
        maxCount = Arrays.stream(khoang10).max().orElse(1);
        
        for (int i = 0; i < nhanKhoang.length; i++) {
            int count = khoang10[i];
            int barLength = maxCount > 0 ? (count * maxBarLength) / maxCount : 0;
            
            sb.append(String.format("%-12s [%3d]: ", nhanKhoang[i], count));
            for (int j = 0; j < barLength; j++) {
                sb.append("█");
            }
            sb.append("\n");
        }
        
        sb.append("\n===============================================\n");
        sb.append(String.format("Tong so sinh vien: %d\n", danhSachSinhVien.size()));
        sb.append("===============================================\n");
        
        return sb.toString();
    }
    
    /**
     * Tạo histogram phân bố điểm theo học phần
     * @param maHocPhan Mã học phần
     * @param tenHocPhan Tên học phần
     * @param danhSachDiem Danh sách điểm
     * @return Chuỗi hiển thị histogram
     */
    public String taoHistogramTheoHocPhan(String maHocPhan, String tenHocPhan, List<Diem> danhSachDiem) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===============================================\n");
        sb.append("   HISTOGRAM DIEM HOC PHAN\n");
        sb.append(String.format("   %s - %s\n", maHocPhan, tenHocPhan));
        sb.append("===============================================\n\n");
        
        // Lọc điểm theo học phần
        List<Diem> diemHocPhan = danhSachDiem.stream()
                .filter(d -> d.getHocPhan() != null && d.getHocPhan().getMaHocPhan().equals(maHocPhan))
                .collect(Collectors.toList());
        
        if (diemHocPhan.isEmpty()) {
            sb.append("Khong co du lieu diem\n");
            return sb.toString();
        }
        
        // Phân loại điểm vào các khoảng
        int[] khoang = new int[11]; // 0-0.9, 1-1.9, ..., 9-9.9, 10
        
        for (Diem diem : diemHocPhan) {
            double d = diem.getDiemTongKet();
            int index = Math.min((int) d, 10);
            khoang[index]++;
        }
        
        int maxCount = Arrays.stream(khoang).max().orElse(1);
        int maxBarLength = 50;
        
        for (int i = 0; i < khoang.length; i++) {
            int count = khoang[i];
            int barLength = maxCount > 0 ? (count * maxBarLength) / maxCount : 0;
            
            String label = (i == 10) ? "10.0    " : String.format("%d.0 - %d.9", i, i);
            sb.append(String.format("%-10s [%3d]: ", label, count));
            for (int j = 0; j < barLength; j++) {
                sb.append("█");
            }
            sb.append("\n");
        }
        
        sb.append("\n===============================================\n");
        
        // Thống kê
        double diemTB = diemHocPhan.stream()
                .mapToDouble(Diem::getDiemTongKet)
                .average()
                .orElse(0.0);
        double diemCaoNhat = diemHocPhan.stream()
                .mapToDouble(Diem::getDiemTongKet)
                .max()
                .orElse(0.0);
        double diemThapNhat = diemHocPhan.stream()
                .mapToDouble(Diem::getDiemTongKet)
                .min()
                .orElse(0.0);
        long soSinhVienDat = diemHocPhan.stream()
                .filter(Diem::isDaDat)
                .count();
        
        sb.append(String.format("Tong so sinh vien: %d\n", diemHocPhan.size()));
        sb.append(String.format("Diem trung binh: %.2f\n", diemTB));
        sb.append(String.format("Diem cao nhat: %.2f\n", diemCaoNhat));
        sb.append(String.format("Diem thap nhat: %.2f\n", diemThapNhat));
        sb.append(String.format("So sinh vien dat: %d (%.1f%%)\n", 
                soSinhVienDat, (soSinhVienDat * 100.0) / diemHocPhan.size()));
        sb.append("===============================================\n");
        
        return sb.toString();
    }
    
    /**
     * Tạo báo cáo tổng hợp
     * @param danhSachSinhVien Danh sách sinh viên
     * @param danhSachDiem Danh sách điểm
     * @return Chuỗi báo cáo tổng hợp
     */
    public String taoBaoCaoTongHop(List<SinhVien> danhSachSinhVien, List<Diem> danhSachDiem) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===============================================\n");
        sb.append("   BAO CAO TONG HOP\n");
        sb.append("===============================================\n\n");
        
        // Thống kê chung
        sb.append("1. THONG KE CHUNG:\n");
        sb.append(String.format("   - Tong so sinh vien: %d\n", danhSachSinhVien.size()));
        sb.append(String.format("   - Tong so ban ghi diem: %d\n", danhSachDiem.size()));
        
        double gpaTraungBinh = danhSachSinhVien.stream()
                .mapToDouble(SinhVien::getGpa)
                .average()
                .orElse(0.0);
        sb.append(String.format("   - GPA trung binh: %.2f\n\n", gpaTraungBinh));
        
        // Thống kê xếp loại
        sb.append("2. THONG KE XEP LOAI:\n");
        Map<String, Long> soLuongXepLoai = thongKeSoLuongXepLoai(danhSachSinhVien);
        Map<String, Double> tyLeXepLoai = thongKeTyLeXepLoai(danhSachSinhVien);
        
        String[] cacXepLoai = {"Xuat sac", "Gioi", "Kha", "Trung binh", "Yeu"};
        for (String xepLoai : cacXepLoai) {
            sb.append(String.format("   - %-15s: %3d sinh vien (%.1f%%)\n",
                    xepLoai,
                    soLuongXepLoai.getOrDefault(xepLoai, 0L),
                    tyLeXepLoai.getOrDefault(xepLoai, 0.0)));
        }
        
        // Thống kê học lại
        List<Map.Entry<SinhVien, List<HocPhan>>> dsHocLai = danhSachHocLai(danhSachDiem);
        sb.append(String.format("\n3. SINH VIEN CAN HOC LAI: %d sinh vien\n", dsHocLai.size()));
        
        sb.append("\n===============================================\n");
        
        return sb.toString();
    }
}

