package com.quanlydiem.ngoaile;

/**
 * Ngoại lệ KhongTimThayHocPhanException - Ném ra khi không tìm thấy học phần
 */
public class KhongTimThayHocPhanException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public KhongTimThayHocPhanException(String message) {
        super(message);
    }
    
    public KhongTimThayHocPhanException(String message, Throwable cause) {
        super(message, cause);
    }
}

