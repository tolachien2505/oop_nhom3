package com.quanlydiem.luutru;

import com.quanlydiem.model.hocphan.Diem;
import com.quanlydiem.model.hocphan.HocPhan;
import com.quanlydiem.model.nguoi.SinhVien;
import com.quanlydiem.giaodien.LuuTru;
import com.quanlydiem.ngoaile.DiemKhongHopLeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository DiemRepository - Quản lý lưu trữ và truy xuất dữ liệu Diem
 */
public class DiemRepository implements LuuTru {
    private static final String FILE_PATH = "data/grades.csv";
    private static final String[] HEADER = {"MaDiem", "MaSinhVien", "MaHocPhan", "DiemQuiz", 
                                           "DiemGiuaKy", "DiemCuoiKy", "DiemBaiTap", "DiemTongKet", "DiemChu", "DaDat"};
    
    private List<Diem> danhSachDiem;
    private SinhVienRepository sinhVienRepository;
    private HocPhanRepository hocPhanRepository;
    
    public DiemRepository(SinhVienRepository sinhVienRepository, HocPhanRepository hocPhanRepository) {
        this.sinhVienRepository = sinhVienRepository;
        this.hocPhanRepository = hocPhanRepository;
        this.danhSachDiem = new ArrayList<>();
        try {
            docFile();
        } catch (IOException e) {
            System.err.println("Khong the doc file diem: " + e.getMessage());
        }
    }
    
    @Override
    public void luuFile() throws IOException {
        List<String[]> records = new ArrayList<>();
        for (Diem diem : danhSachDiem) {
            String[] record = {
                diem.getMaDiem(),
                diem.getSinhVien() != null ? diem.getSinhVien().getMa() : "",
                diem.getHocPhan() != null ? diem.getHocPhan().getMaHocPhan() : "",
                String.format("%.2f", diem.getDiemQuiz()),
                String.format("%.2f", diem.getDiemGiuaKy()),
                String.format("%.2f", diem.getDiemCuoiKy()),
                String.format("%.2f", diem.getDiemBaiTap()),
                String.format("%.2f", diem.getDiemTongKet()),
                diem.getDiemChu(),
                String.valueOf(diem.isDaDat())
            };
            records.add(record);
        }
        CSVUtils.ghiCSV(FILE_PATH, records, HEADER);
    }
    
    @Override
    public void docFile() throws IOException {
        danhSachDiem.clear();
        List<String[]> records = CSVUtils.docCSV(FILE_PATH);
        
        for (int i = 0; i < records.size(); i++) {
            if (i == 0 && records.get(i)[0].equals("MaDiem")) {
                continue; // Bỏ qua header
            }
            
            String[] record = records.get(i);
            if (record.length >= 8) {
                Diem diem = new Diem();
                diem.setMaDiem(record[0]);
                
                if (record.length > 1 && !record[1].isEmpty()) {
                    Optional<SinhVien> sv = sinhVienRepository.timTheoMa(record[1]);
                    sv.ifPresent(diem::setSinhVien);
                }
                
                if (record.length > 2 && !record[2].isEmpty()) {
                    Optional<HocPhan> hp = hocPhanRepository.timTheoMa(record[2]);
                    hp.ifPresent(diem::setHocPhan);
                }
                
                try {
                    if (record.length > 3) diem.setDiemQuiz(Double.parseDouble(record[3]));
                    if (record.length > 4) diem.setDiemGiuaKy(Double.parseDouble(record[4]));
                    if (record.length > 5) diem.setDiemCuoiKy(Double.parseDouble(record[5]));
                    if (record.length > 6) diem.setDiemBaiTap(Double.parseDouble(record[6]));
                    if (record.length > 7) diem.setDiemTongKet(Double.parseDouble(record[7]));
                } catch (NumberFormatException e) {
                    // Giữ giá trị mặc định 0.0
                }
                
                if (record.length > 8) diem.setDiemChu(record[8]);
                if (record.length > 9) diem.setDaDat(Boolean.parseBoolean(record[9]));
                
                danhSachDiem.add(diem);
            }
        }
    }
    
    // CRUD operations
    public void them(Diem diem) throws IOException {
        if (diem.getMaDiem() == null || diem.getMaDiem().isEmpty()) {
            diem.setMaDiem(UUID.randomUUID().toString().substring(0, 8));
        }
        danhSachDiem.add(diem);
        
        // Cập nhật danh sách mã điểm trong sinh viên
        if (diem.getSinhVien() != null) {
            diem.getSinhVien().themMaDiem(diem.getMaDiem());
        }
        
        luuFile();
    }
    
    public void capNhat(Diem diem) throws IOException {
        for (int i = 0; i < danhSachDiem.size(); i++) {
            if (danhSachDiem.get(i).getMaDiem().equals(diem.getMaDiem())) {
                danhSachDiem.set(i, diem);
                luuFile();
                return;
            }
        }
    }
    
    public void xoa(String maDiem) throws IOException {
        Diem diemToRemove = danhSachDiem.stream()
                .filter(d -> d.getMaDiem().equals(maDiem))
                .findFirst()
                .orElse(null);
        
        if (diemToRemove != null && diemToRemove.getSinhVien() != null) {
            diemToRemove.getSinhVien().xoaMaDiem(maDiem);
        }
        
        danhSachDiem.removeIf(d -> d.getMaDiem().equals(maDiem));
        luuFile();
    }
    
    public Optional<Diem> timTheoMa(String maDiem) {
        return danhSachDiem.stream()
                .filter(d -> d.getMaDiem().equals(maDiem))
                .findFirst();
    }
    
    public List<Diem> timTheoSinhVien(String maSinhVien) {
        return danhSachDiem.stream()
                .filter(d -> d.getSinhVien() != null && d.getSinhVien().getMa().equals(maSinhVien))
                .toList();
    }
    
    public List<Diem> timTheoHocPhan(String maHocPhan) {
        return danhSachDiem.stream()
                .filter(d -> d.getHocPhan() != null && d.getHocPhan().getMaHocPhan().equals(maHocPhan))
                .toList();
    }
    
    public Optional<Diem> timTheoSinhVienVaHocPhan(String maSinhVien, String maHocPhan) {
        return danhSachDiem.stream()
                .filter(d -> d.getSinhVien() != null && d.getSinhVien().getMa().equals(maSinhVien) &&
                           d.getHocPhan() != null && d.getHocPhan().getMaHocPhan().equals(maHocPhan))
                .findFirst();
    }
    
    public List<Diem> layTatCa() {
        return new ArrayList<>(danhSachDiem);
    }
    
    public boolean tonTai(String maDiem) {
        return danhSachDiem.stream()
                .anyMatch(d -> d.getMaDiem().equals(maDiem));
    }
    
    /**
     * Validate tất cả điểm trong hệ thống
     * @return true nếu tất cả điểm hợp lệ
     */
    public boolean validateTatCaDiem() {
        for (Diem diem : danhSachDiem) {
            try {
                Diem.kiemTraDiem(diem.getDiemQuiz());
                Diem.kiemTraDiem(diem.getDiemGiuaKy());
                Diem.kiemTraDiem(diem.getDiemCuoiKy());
                Diem.kiemTraDiem(diem.getDiemBaiTap());
            } catch (DiemKhongHopLeException e) {
                System.err.println("Diem khong hop le: " + diem.getMaDiem() + " - " + e.getMessage());
                return false;
            }
        }
        return true;
    }
}

