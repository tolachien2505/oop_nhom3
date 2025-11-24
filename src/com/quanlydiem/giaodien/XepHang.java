package com.quanlydiem.giaodien;

/**
 * Interface XepHang - Định nghĩa các phương thức tính GPA và xếp loại học lực
 */
public interface XepHang {
    /**
     * Tính điểm trung bình tích lũy (GPA)
     * @return GPA của sinh viên
     */
    double tinhGPA();
    
    /**
     * Xếp loại học lực dựa trên GPA
     * @return Xếp loại học lực (Xuất sắc, Giỏi, Khá, Trung bình, Yếu)
     */
    String xepLoaiHocLuc();
}

