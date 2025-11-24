package com.quanlydiem.dichvu;

import com.quanlydiem.model.nguoi.SinhVien;

/**
 * Service XepLoaiService - Xử lý logic xếp loại học lực
 */
public class XepLoaiService {
    
    // Các ngưỡng xếp loại (thang điểm 10.0)
    public static final double NGUONG_XUAT_SAC = 9.0;
    public static final double NGUONG_GIOI = 8.0;
    public static final double NGUONG_KHA = 6.5;
    public static final double NGUONG_TRUNG_BINH = 5.0;
    
    /**
     * Xếp loại học lực dựa trên GPA
     * - Xuất sắc: GPA >= 9.0
     * - Giỏi: 8.0 <= GPA < 9.0
     * - Khá: 6.5 <= GPA < 8.0
     * - Trung bình: 5.0 <= GPA < 6.5
     * - Yếu: GPA < 5.0
     * @param gpa Điểm GPA
     * @return Xếp loại học lực
     */
    public String xepLoaiHocLuc(double gpa) {
        if (gpa >= NGUONG_XUAT_SAC) {
            return "Xuat sac";
        } else if (gpa >= NGUONG_GIOI) {
            return "Gioi";
        } else if (gpa >= NGUONG_KHA) {
            return "Kha";
        } else if (gpa >= NGUONG_TRUNG_BINH) {
            return "Trung binh";
        } else {
            return "Yeu";
        }
    }
    
    /**
     * Xếp loại học lực cho sinh viên và cập nhật vào đối tượng
     * @param sinhVien Sinh viên cần xếp loại
     * @return Xếp loại học lực
     */
    public String xepLoaiHocLucChoSinhVien(SinhVien sinhVien) {
        String xepLoai = xepLoaiHocLuc(sinhVien.getGpa());
        sinhVien.setXepLoai(xepLoai);
        return xepLoai;
    }
    
    /**
     * Kiểm tra sinh viên có đạt loại Giỏi trở lên không
     * @param sinhVien Sinh viên cần kiểm tra
     * @return true nếu GPA >= 8.0
     */
    public boolean daDatGioiTroLen(SinhVien sinhVien) {
        return sinhVien.getGpa() >= NGUONG_GIOI;
    }
    
    /**
     * Kiểm tra sinh viên có đạt loại Khá trở lên không
     * @param sinhVien Sinh viên cần kiểm tra
     * @return true nếu GPA >= 6.5
     */
    public boolean daDatKhaTroLen(SinhVien sinhVien) {
        return sinhVien.getGpa() >= NGUONG_KHA;
    }
    
    /**
     * Kiểm tra sinh viên có bị xếp loại Yếu không
     * @param sinhVien Sinh viên cần kiểm tra
     * @return true nếu GPA < 5.0
     */
    public boolean biXepLoaiYeu(SinhVien sinhVien) {
        return sinhVien.getGpa() < NGUONG_TRUNG_BINH;
    }
    
    /**
     * Lấy mô tả chi tiết về xếp loại
     * @param xepLoai Xếp loại học lực
     * @return Mô tả chi tiết
     */
    public String layMoTaXepLoai(String xepLoai) {
        switch (xepLoai) {
            case "Xuat sac":
                return "Xuat sac (GPA >= 9.0)";
            case "Gioi":
                return "Gioi (8.0 <= GPA < 9.0)";
            case "Kha":
                return "Kha (6.5 <= GPA < 8.0)";
            case "Trung binh":
                return "Trung binh (5.0 <= GPA < 6.5)";
            case "Yeu":
                return "Yeu (GPA < 5.0)";
            default:
                return "Chua xep loai";
        }
    }
    
    /**
     * Chuyển đổi xếp loại sang điểm số tương ứng (lấy giá trị giữa của khoảng)
     * @param xepLoai Xếp loại học lực
     * @return Điểm số tương ứng
     */
    public double chuyenXepLoaiThanhDiem(String xepLoai) {
        switch (xepLoai) {
            case "Xuat sac":
                return 9.5; // Giữa khoảng [9.0, 10.0]
            case "Gioi":
                return 8.5; // Giữa khoảng [8.0, 9.0)
            case "Kha":
                return 7.25; // Giữa khoảng [6.5, 8.0)
            case "Trung binh":
                return 5.75; // Giữa khoảng [5.0, 6.5)
            case "Yeu":
                return 2.5; // Giữa khoảng [0.0, 5.0)
            default:
                return 0.0;
        }
    }
}

