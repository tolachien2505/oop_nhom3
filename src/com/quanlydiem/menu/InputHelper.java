package com.quanlydiem.menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Lớp tiện ích InputHelper - Hỗ trợ nhập liệu với validation và retry
 */
public class InputHelper {
    
    private Scanner scanner;
    
    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }
    
    /**
     * Nhập số thực với validation và retry
     * @param prompt Câu nhắc nhập
     * @param min Giá trị tối thiểu
     * @param max Giá trị tối đa
     * @return Số đã nhập
     */
    public double nhapSoThuc(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                double value = Double.parseDouble(input);
                
                if (value < min || value > max) {
                    System.out.println("Loi: Gia tri phai nam trong khoang [" + min + ", " + max + "]");
                    System.out.print("Ban co muon nhap lai? (y/n): ");
                    String choice = scanner.nextLine().trim();
                    if (!choice.equalsIgnoreCase("y")) {
                        throw new RuntimeException("Nguoi dung huy thao tac");
                    }
                    continue;
                }
                
                return value;
                
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so hop le!");
                System.out.println("      Dinh dang: so thuc (VD: 8.5, 9.0, 7.25)");
                System.out.print("Ban co muon nhap lai? (y/n): ");
                String choice = scanner.nextLine().trim();
                if (!choice.equalsIgnoreCase("y")) {
                    throw new RuntimeException("Nguoi dung huy thao tac");
                }
            }
        }
    }
    
    /**
     * Nhập số nguyên với validation và retry
     * @param prompt Câu nhắc nhập
     * @param min Giá trị tối thiểu
     * @param max Giá trị tối đa
     * @return Số đã nhập
     */
    public int nhapSoNguyen(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                
                if (value < min || value > max) {
                    System.out.println("Loi: Gia tri phai nam trong khoang [" + min + ", " + max + "]");
                    System.out.print("Ban co muon nhap lai? (y/n): ");
                    String choice = scanner.nextLine().trim();
                    if (!choice.equalsIgnoreCase("y")) {
                        throw new RuntimeException("Nguoi dung huy thao tac");
                    }
                    continue;
                }
                
                return value;
                
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so nguyen hop le!");
                System.out.print("Ban co muon nhap lai? (y/n): ");
                String choice = scanner.nextLine().trim();
                if (!choice.equalsIgnoreCase("y")) {
                    throw new RuntimeException("Nguoi dung huy thao tac");
                }
            }
        }
    }
    
    /**
     * Nhập ngày tháng với validation và retry
     * @param prompt Câu nhắc nhập
     * @return Ngày đã nhập
     */
    public LocalDate nhapNgayThang(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                
                // Thử parse
                LocalDate date = LocalDate.parse(input, formatter);
                return date;
                
            } catch (DateTimeParseException e) {
                System.out.println("\nLoi: Dinh dang ngay thang khong hop le!");
                System.out.println("--------------------------------------------------");
                System.out.println("Dinh dang dung: yyyy-MM-dd");
                System.out.println("Chu y:");
                System.out.println("  - Nam phai co 4 chu so (VD: 2004)");
                System.out.println("  - Thang phai co 2 chu so (01-12)");
                System.out.println("  - Ngay phai co 2 chu so (01-31)");
                System.out.println("\nVi du hop le:");
                System.out.println("  ✓ 2004-01-15  (15 thang 1 nam 2004)");
                System.out.println("  ✓ 2004-07-24  (24 thang 7 nam 2004)");
                System.out.println("  ✓ 2006-12-05  (5 thang 12 nam 2006)");
                System.out.println("\nVi du SAI:");
                System.out.println("  ✗ 2004-1-15   (thieu so 0 truoc 1)");
                System.out.println("  ✗ 2004-7-24   (thieu so 0 truoc 7)");
                System.out.println("  ✗ 24-7-2004   (sai thu tu)");
                System.out.println("  ✗ 2004/07/24  (dung '-' chu khong phai '/')");
                System.out.println("--------------------------------------------------");
                System.out.print("\nBan co muon nhap lai? (y/n): ");
                String choice = scanner.nextLine().trim();
                if (!choice.equalsIgnoreCase("y")) {
                    throw new RuntimeException("Nguoi dung huy thao tac");
                }
            }
        }
    }
    
    /**
     * Nhập chuỗi không rỗng
     * @param prompt Câu nhắc nhập
     * @return Chuỗi đã nhập
     */
    public String nhapChuoi(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Loi: Khong duoc de trong!");
                System.out.print("Ban co muon nhap lai? (y/n): ");
                String choice = scanner.nextLine().trim();
                if (!choice.equalsIgnoreCase("y")) {
                    throw new RuntimeException("Nguoi dung huy thao tac");
                }
                continue;
            }
            
            return input;
        }
    }
    
    /**
     * Nhập chuỗi (có thể rỗng)
     * @param prompt Câu nhắc nhập
     * @return Chuỗi đã nhập
     */
    public String nhapChuoiTuyChon(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    /**
     * Xác nhận yes/no
     * @param prompt Câu hỏi xác nhận
     * @return true nếu chọn yes
     */
    public boolean xacNhan(String prompt) {
        System.out.print(prompt);
        String choice = scanner.nextLine().trim();
        return choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes");
    }
    
    /**
     * Nhập số thực tùy chọn (có thể bỏ qua bằng Enter)
     * @param prompt Câu nhắc nhập
     * @param giaTriHienTai Giá trị hiện tại
     * @param min Giá trị tối thiểu
     * @param max Giá trị tối đa
     * @return Số đã nhập hoặc giá trị hiện tại nếu bỏ qua
     */
    public double nhapSoThucTuyChon(String prompt, double giaTriHienTai, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                
                // Nếu Enter (empty) thì giữ nguyên giá trị cũ
                if (input.isEmpty()) {
                    return giaTriHienTai;
                }
                
                double value = Double.parseDouble(input);
                
                if (value < min || value > max) {
                    System.out.println("Loi: Diem phai nam trong khoang [" + min + ", " + max + "]");
                    System.out.print("Ban co muon nhap lai? (y/n, Enter=y): ");
                    String choice = scanner.nextLine().trim();
                    if (!choice.isEmpty() && !choice.equalsIgnoreCase("y")) {
                        return giaTriHienTai; // Giữ nguyên giá trị cũ
                    }
                    continue;
                }
                
                return value;
                
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so hop le!");
                System.out.println("      Dinh dang: so thuc (VD: 8.5, 9.0, 7.25)");
                System.out.print("Ban co muon nhap lai? (y/n, Enter=y): ");
                String choice = scanner.nextLine().trim();
                if (!choice.isEmpty() && !choice.equalsIgnoreCase("y")) {
                    return giaTriHienTai; // Giữ nguyên giá trị cũ
                }
            }
        }
    }
}

