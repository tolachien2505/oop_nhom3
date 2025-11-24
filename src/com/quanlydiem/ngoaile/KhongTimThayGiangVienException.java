package com.quanlydiem.ngoaile;

/**
 * Ngoại lệ KhongTimThayGiangVienException - Ném ra khi không tìm thấy giảng viên
 */
public class KhongTimThayGiangVienException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public KhongTimThayGiangVienException(String message) {
        super(message);
    }
    
    public KhongTimThayGiangVienException(String message, Throwable cause) {
        super(message, cause);
    }
}

