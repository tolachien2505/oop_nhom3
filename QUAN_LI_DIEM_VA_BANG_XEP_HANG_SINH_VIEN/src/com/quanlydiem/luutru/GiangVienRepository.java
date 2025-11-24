package com.quanlydiem.luutru;

import com.quanlydiem.model.nguoi.GiangVien;
import com.quanlydiem.model.tochuc.Khoa;
import com.quanlydiem.giaodien.LuuTru;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository GiangVienRepository - Quản lý lưu trữ và truy xuất dữ liệu GiangVien
 */
public class GiangVienRepository implements LuuTru {
    private static final String FILE_PATH = "data/lecturers.csv";
    private static final String[] HEADER = {"MaGiangVien", "HoTen", "NgaySinh", "Email", "SoDienThoai", "MaKhoa", "ChucVu", "HocVi"};
    
    private List<GiangVien> danhSachGiangVien;
    private KhoaRepository khoaRepository;
    
    public GiangVienRepository(KhoaRepository khoaRepository) {
        this.khoaRepository = khoaRepository;
        this.danhSachGiangVien = new ArrayList<>();
        try {
            docFile();
        } catch (IOException e) {
            System.err.println("Khong the doc file giang vien: " + e.getMessage());
        }
    }
    
    @Override
    public void luuFile() throws IOException {
        List<String[]> records = new ArrayList<>();
        for (GiangVien gv : danhSachGiangVien) {
            String[] record = {
                gv.getMa(),
                gv.getHoTen(),
                gv.getNgaySinh() != null ? gv.getNgaySinh().toString() : "",
                gv.getEmail() != null ? gv.getEmail() : "",
                gv.getSoDienThoai() != null ? gv.getSoDienThoai() : "",
                gv.getKhoa() != null ? gv.getKhoa().getMaKhoa() : "",
                gv.getChucVu() != null ? gv.getChucVu() : "",
                gv.getHocVi() != null ? gv.getHocVi() : ""
            };
            records.add(record);
        }
        CSVUtils.ghiCSV(FILE_PATH, records, HEADER);
    }
    
    @Override
    public void docFile() throws IOException {
        danhSachGiangVien.clear();
        List<String[]> records = CSVUtils.docCSV(FILE_PATH);
        
        for (int i = 0; i < records.size(); i++) {
            if (i == 0 && records.get(i)[0].equals("MaGiangVien")) {
                continue; // Bỏ qua header
            }
            
            String[] record = records.get(i);
            if (record.length >= 2) {
                GiangVien gv = new GiangVien();
                gv.setMa(record[0]);
                gv.setHoTen(record[1]);
                
                if (record.length > 2 && !record[2].isEmpty()) {
                    try {
                        gv.setNgaySinh(LocalDate.parse(record[2]));
                    } catch (Exception e) {
                        gv.setNgaySinh(null);
                    }
                }
                
                if (record.length > 3) gv.setEmail(record[3]);
                if (record.length > 4) gv.setSoDienThoai(record[4]);
                
                if (record.length > 5 && !record[5].isEmpty()) {
                    Optional<Khoa> khoa = khoaRepository.timTheoMa(record[5]);
                    khoa.ifPresent(gv::setKhoa);
                }
                
                if (record.length > 6) gv.setChucVu(record[6]);
                if (record.length > 7) gv.setHocVi(record[7]);
                
                danhSachGiangVien.add(gv);
            }
        }
    }
    
    // CRUD operations
    public void them(GiangVien gv) throws IOException {
        danhSachGiangVien.add(gv);
        luuFile();
    }
    
    public void capNhat(GiangVien gv) throws IOException {
        for (int i = 0; i < danhSachGiangVien.size(); i++) {
            if (danhSachGiangVien.get(i).getMa().equals(gv.getMa())) {
                danhSachGiangVien.set(i, gv);
                luuFile();
                return;
            }
        }
    }
    
    public void xoa(String maGiangVien) throws IOException {
        danhSachGiangVien.removeIf(gv -> gv.getMa().equals(maGiangVien));
        luuFile();
    }
    
    public Optional<GiangVien> timTheoMa(String maGiangVien) {
        return danhSachGiangVien.stream()
                .filter(gv -> gv.getMa().equals(maGiangVien))
                .findFirst();
    }
    
    public List<GiangVien> timTheoTen(String hoTen) {
        return danhSachGiangVien.stream()
                .filter(gv -> gv.getHoTen().toLowerCase().contains(hoTen.toLowerCase()))
                .toList();
    }
    
    public List<GiangVien> timTheoKhoa(String maKhoa) {
        return danhSachGiangVien.stream()
                .filter(gv -> gv.getKhoa() != null && gv.getKhoa().getMaKhoa().equals(maKhoa))
                .toList();
    }
    
    public List<GiangVien> layTatCa() {
        return new ArrayList<>(danhSachGiangVien);
    }
    
    public boolean tonTai(String maGiangVien) {
        return danhSachGiangVien.stream()
                .anyMatch(gv -> gv.getMa().equals(maGiangVien));
    }
}

