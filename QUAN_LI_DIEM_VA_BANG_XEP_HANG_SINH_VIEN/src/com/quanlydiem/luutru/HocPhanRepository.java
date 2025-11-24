package com.quanlydiem.luutru;

import com.quanlydiem.model.hocphan.HocPhan;
import com.quanlydiem.model.nguoi.GiangVien;
import com.quanlydiem.giaodien.LuuTru;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository HocPhanRepository - Quản lý lưu trữ và truy xuất dữ liệu HocPhan
 */
public class HocPhanRepository implements LuuTru {
    private static final String FILE_PATH = "data/courses.csv";
    private static final String[] HEADER = {"MaHocPhan", "TenHocPhan", "SoTinChi", "MaGiangVien", 
                                           "TrongSoQuiz", "TrongSoGiuaKy", "TrongSoCuoiKy", "TrongSoBaiTap"};
    
    private List<HocPhan> danhSachHocPhan;
    private GiangVienRepository giangVienRepository;
    
    public HocPhanRepository(GiangVienRepository giangVienRepository) {
        this.giangVienRepository = giangVienRepository;
        this.danhSachHocPhan = new ArrayList<>();
        try {
            docFile();
        } catch (IOException e) {
            System.err.println("Khong the doc file hoc phan: " + e.getMessage());
        }
    }
    
    @Override
    public void luuFile() throws IOException {
        List<String[]> records = new ArrayList<>();
        for (HocPhan hp : danhSachHocPhan) {
            String[] record = {
                hp.getMaHocPhan(),
                hp.getTenHocPhan(),
                String.valueOf(hp.getSoTinChi()),
                hp.getGiangVien() != null ? hp.getGiangVien().getMa() : "",
                String.valueOf(hp.getTrongSoQuiz()),
                String.valueOf(hp.getTrongSoGiuaKy()),
                String.valueOf(hp.getTrongSoCuoiKy()),
                String.valueOf(hp.getTrongSoBaiTap())
            };
            records.add(record);
        }
        CSVUtils.ghiCSV(FILE_PATH, records, HEADER);
    }
    
    @Override
    public void docFile() throws IOException {
        danhSachHocPhan.clear();
        List<String[]> records = CSVUtils.docCSV(FILE_PATH);
        
        for (int i = 0; i < records.size(); i++) {
            if (i == 0 && records.get(i)[0].equals("MaHocPhan")) {
                continue; // Bỏ qua header
            }
            
            String[] record = records.get(i);
            if (record.length >= 3) {
                HocPhan hp = new HocPhan();
                hp.setMaHocPhan(record[0]);
                hp.setTenHocPhan(record[1]);
                
                try {
                    hp.setSoTinChi(Integer.parseInt(record[2]));
                } catch (NumberFormatException e) {
                    hp.setSoTinChi(3); // Mặc định 3 tín chỉ
                }
                
                if (record.length > 3 && !record[3].isEmpty()) {
                    Optional<GiangVien> gv = giangVienRepository.timTheoMa(record[3]);
                    gv.ifPresent(hp::setGiangVien);
                }
                
                // Đọc trọng số (nếu có)
                if (record.length > 4 && !record[4].isEmpty()) {
                    try {
                        hp.setTrongSoQuiz(Double.parseDouble(record[4]));
                    } catch (NumberFormatException e) {
                        hp.setTrongSoQuiz(0.1);
                    }
                }
                
                if (record.length > 5 && !record[5].isEmpty()) {
                    try {
                        hp.setTrongSoGiuaKy(Double.parseDouble(record[5]));
                    } catch (NumberFormatException e) {
                        hp.setTrongSoGiuaKy(0.2);
                    }
                }
                
                if (record.length > 6 && !record[6].isEmpty()) {
                    try {
                        hp.setTrongSoCuoiKy(Double.parseDouble(record[6]));
                    } catch (NumberFormatException e) {
                        hp.setTrongSoCuoiKy(0.5);
                    }
                }
                
                if (record.length > 7 && !record[7].isEmpty()) {
                    try {
                        hp.setTrongSoBaiTap(Double.parseDouble(record[7]));
                    } catch (NumberFormatException e) {
                        hp.setTrongSoBaiTap(0.2);
                    }
                }
                
                danhSachHocPhan.add(hp);
            }
        }
    }
    
    // CRUD operations
    public void them(HocPhan hp) throws IOException {
        danhSachHocPhan.add(hp);
        luuFile();
    }
    
    public void capNhat(HocPhan hp) throws IOException {
        for (int i = 0; i < danhSachHocPhan.size(); i++) {
            if (danhSachHocPhan.get(i).getMaHocPhan().equals(hp.getMaHocPhan())) {
                danhSachHocPhan.set(i, hp);
                luuFile();
                return;
            }
        }
    }
    
    public void xoa(String maHocPhan) throws IOException {
        danhSachHocPhan.removeIf(hp -> hp.getMaHocPhan().equals(maHocPhan));
        luuFile();
    }
    
    public Optional<HocPhan> timTheoMa(String maHocPhan) {
        return danhSachHocPhan.stream()
                .filter(hp -> hp.getMaHocPhan().equals(maHocPhan))
                .findFirst();
    }
    
    public List<HocPhan> timTheoTen(String tenHocPhan) {
        return danhSachHocPhan.stream()
                .filter(hp -> hp.getTenHocPhan().toLowerCase().contains(tenHocPhan.toLowerCase()))
                .toList();
    }
    
    public List<HocPhan> timTheoGiangVien(String maGiangVien) {
        return danhSachHocPhan.stream()
                .filter(hp -> hp.getGiangVien() != null && hp.getGiangVien().getMa().equals(maGiangVien))
                .toList();
    }
    
    public List<HocPhan> layTatCa() {
        return new ArrayList<>(danhSachHocPhan);
    }
    
    public boolean tonTai(String maHocPhan) {
        return danhSachHocPhan.stream()
                .anyMatch(hp -> hp.getMaHocPhan().equals(maHocPhan));
    }
}

