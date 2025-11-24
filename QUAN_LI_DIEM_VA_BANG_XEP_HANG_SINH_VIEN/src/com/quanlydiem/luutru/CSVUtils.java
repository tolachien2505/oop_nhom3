package com.quanlydiem.luutru;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp tiện ích CSVUtils - Hỗ trợ đọc và ghi file CSV
 */
public class CSVUtils {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEWLINE_SEPARATOR = "\n";
    
    /**
     * Đọc tất cả dòng từ file CSV
     * @param filePath Đường dẫn file CSV
     * @return Danh sách các dòng, mỗi dòng là một mảng String
     * @throws IOException nếu có lỗi đọc file
     */
    public static List<String[]> docCSV(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        File file = new File(filePath);
        
        // Tạo file rỗng nếu chưa tồn tại
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return records;
        }
        
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] values = phanTichDong(line);
                    records.add(values);
                }
            }
        }
        
        return records;
    }
    
    /**
     * Ghi dữ liệu vào file CSV
     * @param filePath Đường dẫn file CSV
     * @param records Danh sách các dòng cần ghi
     * @param header Dòng header (có thể null)
     * @throws IOException nếu có lỗi ghi file
     */
    public static void ghiCSV(String filePath, List<String[]> records, String[] header) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            
            // Ghi header nếu có
            if (header != null && header.length > 0) {
                bw.write(taoDong(header));
                bw.write(NEWLINE_SEPARATOR);
            }
            
            // Ghi từng dòng dữ liệu
            for (String[] record : records) {
                bw.write(taoDong(record));
                bw.write(NEWLINE_SEPARATOR);
            }
        }
    }
    
    /**
     * Phân tích một dòng CSV xử lý trường hợp có dấu phẩy trong nội dung
     * @param line Dòng CSV cần phân tích
     * @return Mảng các giá trị
     */
    private static String[] phanTichDong(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(currentValue.toString().trim());
                currentValue = new StringBuilder();
            } else {
                currentValue.append(c);
            }
        }
        
        // Thêm giá trị cuối cùng
        values.add(currentValue.toString().trim());
        
        return values.toArray(new String[0]);
    }
    
    /**
     * Tạo một dòng CSV từ mảng giá trị
     * @param values Mảng giá trị
     * @return Dòng CSV
     */
    private static String taoDong(String[] values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                sb.append(COMMA_DELIMITER);
            }
            
            String value = values[i] != null ? values[i] : "";
            
            // Bọc trong dấu ngoặc kép nếu chứa dấu phẩy
            if (value.contains(COMMA_DELIMITER) || value.contains("\"") || value.contains("\n")) {
                value = "\"" + value.replace("\"", "\"\"") + "\"";
            }
            
            sb.append(value);
        }
        return sb.toString();
    }
    
    /**
     * Kiểm tra xem file CSV có tồn tại và có dữ liệu không
     * @param filePath Đường dẫn file CSV
     * @return true nếu file tồn tại và có dữ liệu
     */
    public static boolean kiemTraFileTonTai(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.length() > 0;
    }
    
    /**
     * Xóa file CSV
     * @param filePath Đường dẫn file CSV
     * @return true nếu xóa thành công
     */
    public static boolean xoaFile(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }
}

