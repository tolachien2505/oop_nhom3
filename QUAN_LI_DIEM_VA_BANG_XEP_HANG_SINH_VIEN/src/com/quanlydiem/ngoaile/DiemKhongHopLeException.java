package com.quanlydiem.ngoaile;

/**
 * Ngoại lệ DiemKhongHopLeException - Ném ra khi điểm nhập vào không hợp lệ
 */
public class DiemKhongHopLeException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public DiemKhongHopLeException(String message) {
        super(message);
    }
    
    public DiemKhongHopLeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DiemKhongHopLeException(double diem) {
        super("Diem khong hop le: " + diem + ". Diem phai nam trong khoang [0, 10]");
    }
}

