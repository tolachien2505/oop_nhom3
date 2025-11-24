package com.quanlydiem.giaodien;

import java.io.IOException;

/**
 * Interface LuuTru - Định nghĩa các phương thức lưu trữ và đọc dữ liệu
 */
public interface LuuTru {
    /**
     * Lưu dữ liệu ra file
     * @throws IOException nếu có lỗi khi ghi file
     */
    void luuFile() throws IOException;
    
    /**
     * Đọc dữ liệu từ file
     * @throws IOException nếu có lỗi khi đọc file
     */
    void docFile() throws IOException;
}

