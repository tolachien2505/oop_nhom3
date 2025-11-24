package com.quanlydiem.luutru;

import com.quanlydiem.model.tochuc.LopHoc;
import com.quanlydiem.model.tochuc.Khoa;
import com.quanlydiem.giaodien.LuuTru;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository LopHocRepository - Quản lý lưu trữ và truy xuất dữ liệu LopHoc
 */
public class LopHocRepository implements LuuTru {
    private static final String FILE_PATH = "data/class_groups.csv";
    private static final String[] HEADER = {"MaLop", "TenLop", "MaKhoa", "NamNhapHoc", "GiaoVienChuNhiem"};
    
    private List<LopHoc> danhSachLopHoc;
    private KhoaRepository khoaRepository;
    
    public LopHocRepository(KhoaRepository khoaRepository) {
        this.khoaRepository = khoaRepository;
        this.danhSachLopHoc = new ArrayList<>();
        try {
            docFile();
        } catch (IOException e) {
            System.err.println("Khong the doc file lop hoc: " + e.getMessage());
        }
    }
    
    @Override
    public void luuFile() throws IOException {
        List<String[]> records = new ArrayList<>();
        for (LopHoc lop : danhSachLopHoc) {
            String[] record = {
                lop.getMaLop(),
                lop.getTenLop(),
                lop.getKhoa() != null ? lop.getKhoa().getMaKhoa() : "",
                String.valueOf(lop.getNamNhapHoc()),
                lop.getGiaoVienChuNhiem() != null ? lop.getGiaoVienChuNhiem() : ""
            };
            records.add(record);
        }
        CSVUtils.ghiCSV(FILE_PATH, records, HEADER);
    }
    
    @Override
    public void docFile() throws IOException {
        danhSachLopHoc.clear();
        List<String[]> records = CSVUtils.docCSV(FILE_PATH);
        
        for (int i = 0; i < records.size(); i++) {
            if (i == 0 && records.get(i)[0].equals("MaLop")) {
                continue; // Bỏ qua header
            }
            
            String[] record = records.get(i);
            if (record.length >= 2) {
                LopHoc lop = new LopHoc();
                lop.setMaLop(record[0]);
                lop.setTenLop(record[1]);
                
                if (record.length > 2 && !record[2].isEmpty()) {
                    Optional<Khoa> khoa = khoaRepository.timTheoMa(record[2]);
                    khoa.ifPresent(lop::setKhoa);
                }
                
                if (record.length > 3 && !record[3].isEmpty()) {
                    try {
                        lop.setNamNhapHoc(Integer.parseInt(record[3]));
                    } catch (NumberFormatException e) {
                        lop.setNamNhapHoc(0);
                    }
                }
                
                if (record.length > 4) lop.setGiaoVienChuNhiem(record[4]);
                
                danhSachLopHoc.add(lop);
            }
        }
    }
    
    // CRUD operations
    public void them(LopHoc lop) throws IOException {
        danhSachLopHoc.add(lop);
        luuFile();
    }
    
    public void capNhat(LopHoc lop) throws IOException {
        for (int i = 0; i < danhSachLopHoc.size(); i++) {
            if (danhSachLopHoc.get(i).getMaLop().equals(lop.getMaLop())) {
                danhSachLopHoc.set(i, lop);
                luuFile();
                return;
            }
        }
    }
    
    public void xoa(String maLop) throws IOException {
        danhSachLopHoc.removeIf(l -> l.getMaLop().equals(maLop));
        luuFile();
    }
    
    public Optional<LopHoc> timTheoMa(String maLop) {
        return danhSachLopHoc.stream()
                .filter(l -> l.getMaLop().equals(maLop))
                .findFirst();
    }
    
    public List<LopHoc> timTheoKhoa(String maKhoa) {
        return danhSachLopHoc.stream()
                .filter(l -> l.getKhoa() != null && l.getKhoa().getMaKhoa().equals(maKhoa))
                .toList();
    }
    
    public List<LopHoc> layTatCa() {
        return new ArrayList<>(danhSachLopHoc);
    }
    
    public boolean tonTai(String maLop) {
        return danhSachLopHoc.stream()
                .anyMatch(l -> l.getMaLop().equals(maLop));
    }
}

