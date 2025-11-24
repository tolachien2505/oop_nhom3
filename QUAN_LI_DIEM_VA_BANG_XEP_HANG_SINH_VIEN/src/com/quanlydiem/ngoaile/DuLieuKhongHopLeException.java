package com.quanlydiem.ngoaile;

/**
 * Ngoại lệ DuLieuKhongHopLeException - Ném ra khi dữ liệu đầu vào không hợp lệ
 */
public class DuLieuKhongHopLeException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public DuLieuKhongHopLeException(String message) {
        super(message);
    }
    
    public DuLieuKhongHopLeException(String message, Throwable cause) {
        super(message, cause);
    }
}

