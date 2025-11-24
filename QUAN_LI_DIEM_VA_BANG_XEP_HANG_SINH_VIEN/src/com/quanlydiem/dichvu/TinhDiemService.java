package com.quanlydiem.dichvu;

import com.quanlydiem.model.hocphan.Diem;
import com.quanlydiem.model.hocphan.HocPhan;
import com.quanlydiem.model.nguoi.SinhVien;
import com.quanlydiem.ngoaile.DiemKhongHopLeException;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Service TinhDiemService - Xử lý logic tính điểm tổng kết và GPA
 */
public class TinhDiemService {
    private static final DecimalFormat df = new DecimalFormat("#.##");
    
    /**
     * Tính điểm tổng kết cho một bản ghi điểm
     * @param diem Bản ghi điểm cần tính
     * @throws DiemKhongHopLeException nếu điểm không hợp lệ
     */
    public void tinhDiemTongKet(Diem diem) throws DiemKhongHopLeException {
        // Kiểm tra tính hợp lệ của các điểm thành phần
        Diem.kiemTraDiem(diem.getDiemQuiz());
        Diem.kiemTraDiem(diem.getDiemGiuaKy());
        Diem.kiemTraDiem(diem.getDiemCuoiKy());
        Diem.kiemTraDiem(diem.getDiemBaiTap());
        
        // Tính điểm tổng kết
        diem.tinhDiemTongKet();
    }
    
    /**
     * Tính GPA cho sinh viên dựa trên danh sách điểm
     * GPA = Tổng (điểm tổng kết * số tín chỉ) / Tổng số tín chỉ
     * @param sinhVien Sinh viên cần tính GPA
     * @param danhSachDiem Danh sách điểm của sinh viên
     * @return GPA đã tính
     */
    public double tinhGPA(SinhVien sinhVien, List<Diem> danhSachDiem) {
        if (danhSachDiem == null || danhSachDiem.isEmpty()) {
            sinhVien.setGpa(0.0);
            return 0.0;
        }
        
        double tongDiemCoTrongSo = 0.0;
        int tongSoTinChi = 0;
        
        for (Diem diem : danhSachDiem) {
            if (diem.getSinhVien().getMa().equals(sinhVien.getMa())) {
                HocPhan hocPhan = diem.getHocPhan();
                if (hocPhan != null) {
                    double diemTongKet = diem.getDiemTongKet();
                    int soTinChi = hocPhan.getSoTinChi();
                    
                    tongDiemCoTrongSo += diemTongKet * soTinChi;
                    tongSoTinChi += soTinChi;
                }
            }
        }
        
        double gpa = 0.0;
        if (tongSoTinChi > 0) {
            gpa = tongDiemCoTrongSo / tongSoTinChi;
            gpa = Double.parseDouble(df.format(gpa).replace(",", "."));
        }
        
        sinhVien.setGpa(gpa);
        return gpa;
    }
    
    /**
     * Tính GPA cho danh sách sinh viên
     * @param danhSachSinhVien Danh sách sinh viên
     * @param danhSachDiem Danh sách điểm
     */
    public void tinhGPAChoTatCaSinhVien(List<SinhVien> danhSachSinhVien, List<Diem> danhSachDiem) {
        for (SinhVien sinhVien : danhSachSinhVien) {
            tinhGPA(sinhVien, danhSachDiem);
        }
    }
    
    /**
     * Lấy danh sách điểm của một sinh viên
     * @param maSinhVien Mã sinh viên
     * @param danhSachDiem Danh sách tất cả điểm
     * @return Danh sách điểm của sinh viên
     */
    public List<Diem> layDiemCuaSinhVien(String maSinhVien, List<Diem> danhSachDiem) {
        return danhSachDiem.stream()
                .filter(d -> d.getSinhVien() != null && d.getSinhVien().getMa().equals(maSinhVien))
                .toList();
    }
    
    /**
     * Kiểm tra sinh viên có phải học lại môn này không
     * @param diem Bản ghi điểm
     * @return true nếu cần học lại (điểm < 4.0)
     */
    public boolean canHocLai(Diem diem) {
        return diem.getDiemTongKet() < 4.0;
    }
    
    /**
     * Đếm số học phần đã hoàn thành của sinh viên
     * @param maSinhVien Mã sinh viên
     * @param danhSachDiem Danh sách điểm
     * @return Số học phần đã hoàn thành
     */
    public int demSoHocPhanHoanThanh(String maSinhVien, List<Diem> danhSachDiem) {
        return (int) danhSachDiem.stream()
                .filter(d -> d.getSinhVien() != null && 
                           d.getSinhVien().getMa().equals(maSinhVien) &&
                           d.isDaDat())
                .count();
    }
    
    /**
     * Đếm số học phần cần học lại của sinh viên
     * @param maSinhVien Mã sinh viên
     * @param danhSachDiem Danh sách điểm
     * @return Số học phần cần học lại
     */
    public int demSoHocPhanHocLai(String maSinhVien, List<Diem> danhSachDiem) {
        return (int) danhSachDiem.stream()
                .filter(d -> d.getSinhVien() != null && 
                           d.getSinhVien().getMa().equals(maSinhVien) &&
                           !d.isDaDat())
                .count();
    }
    
    /**
     * Tính điểm trung bình của một học phần
     * @param maHocPhan Mã học phần
     * @param danhSachDiem Danh sách điểm
     * @return Điểm trung bình
     */
    public double tinhDiemTrungBinhHocPhan(String maHocPhan, List<Diem> danhSachDiem) {
        List<Diem> diemHocPhan = danhSachDiem.stream()
                .filter(d -> d.getHocPhan() != null && 
                           d.getHocPhan().getMaHocPhan().equals(maHocPhan))
                .toList();
        
        if (diemHocPhan.isEmpty()) {
            return 0.0;
        }
        
        double tongDiem = diemHocPhan.stream()
                .mapToDouble(Diem::getDiemTongKet)
                .sum();
        
        double diemTB = tongDiem / diemHocPhan.size();
        return Double.parseDouble(df.format(diemTB).replace(",", "."));
    }
}

