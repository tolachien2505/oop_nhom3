package com.quanlydiem.ngoaile;

/**
 * Ngoại lệ KhongTimThaySinhVienException - Ném ra khi không tìm thấy sinh viên
 */
public class KhongTimThaySinhVienException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public KhongTimThaySinhVienException(String message) {
        super(message);
    }
    
    public KhongTimThaySinhVienException(String message, Throwable cause) {
        super(message, cause);
    }
}

