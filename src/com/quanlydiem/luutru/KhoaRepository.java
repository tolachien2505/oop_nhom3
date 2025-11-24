package com.quanlydiem.luutru;

import com.quanlydiem.model.tochuc.Khoa;
import com.quanlydiem.giaodien.LuuTru;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository KhoaRepository - Quản lý lưu trữ và truy xuất dữ liệu Khoa
 */
public class KhoaRepository implements LuuTru {
    private static final String FILE_PATH = "data/faculties.csv";
    private static final String[] HEADER = {"MaKhoa", "TenKhoa", "TruongKhoa", "DienThoai"};
    
    private List<Khoa> danhSachKhoa;
    
    public KhoaRepository() {
        this.danhSachKhoa = new ArrayList<>();
        try {
            docFile();
        } catch (IOException e) {
            System.err.println("Khong the doc file khoa: " + e.getMessage());
        }
    }
    
    @Override
    public void luuFile() throws IOException {
        List<String[]> records = new ArrayList<>();
        for (Khoa khoa : danhSachKhoa) {
            String[] record = {
                khoa.getMaKhoa(),
                khoa.getTenKhoa(),
                khoa.getTruongKhoa() != null ? khoa.getTruongKhoa() : "",
                khoa.getDienThoai() != null ? khoa.getDienThoai() : ""
            };
            records.add(record);
        }
        CSVUtils.ghiCSV(FILE_PATH, records, HEADER);
    }
    
    @Override
    public void docFile() throws IOException {
        danhSachKhoa.clear();
        List<String[]> records = CSVUtils.docCSV(FILE_PATH);
        
        for (int i = 0; i < records.size(); i++) {
            if (i == 0 && records.get(i)[0].equals("MaKhoa")) {
                continue; // Bỏ qua header
            }
            
            String[] record = records.get(i);
            if (record.length >= 2) {
                Khoa khoa = new Khoa();
                khoa.setMaKhoa(record[0]);
                khoa.setTenKhoa(record[1]);
                if (record.length > 2) khoa.setTruongKhoa(record[2]);
                if (record.length > 3) khoa.setDienThoai(record[3]);
                danhSachKhoa.add(khoa);
            }
        }
    }
    
    // CRUD operations
    public void them(Khoa khoa) throws IOException {
        danhSachKhoa.add(khoa);
        luuFile();
    }
    
    public void capNhat(Khoa khoa) throws IOException {
        for (int i = 0; i < danhSachKhoa.size(); i++) {
            if (danhSachKhoa.get(i).getMaKhoa().equals(khoa.getMaKhoa())) {
                danhSachKhoa.set(i, khoa);
                luuFile();
                return;
            }
        }
    }
    
    public void xoa(String maKhoa) throws IOException {
        danhSachKhoa.removeIf(k -> k.getMaKhoa().equals(maKhoa));
        luuFile();
    }
    
    public Optional<Khoa> timTheoMa(String maKhoa) {
        return danhSachKhoa.stream()
                .filter(k -> k.getMaKhoa().equals(maKhoa))
                .findFirst();
    }
    
    public List<Khoa> timTheoTen(String tenKhoa) {
        return danhSachKhoa.stream()
                .filter(k -> k.getTenKhoa().toLowerCase().contains(tenKhoa.toLowerCase()))
                .toList();
    }
    
    public List<Khoa> layTatCa() {
        return new ArrayList<>(danhSachKhoa);
    }
    
    public boolean tonTai(String maKhoa) {
        return danhSachKhoa.stream()
                .anyMatch(k -> k.getMaKhoa().equals(maKhoa));
    }
}

