package com.quanlydiem.luutru;

import com.quanlydiem.model.nguoi.SinhVien;
import com.quanlydiem.model.tochuc.LopHoc;
import com.quanlydiem.model.tochuc.Khoa;
import com.quanlydiem.giaodien.LuuTru;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository SinhVienRepository - Quản lý lưu trữ và truy xuất dữ liệu SinhVien
 */
public class SinhVienRepository implements LuuTru {
    private static final String FILE_PATH = "data/students.csv";
    private static final String[] HEADER = {"MaSinhVien", "HoTen", "NgaySinh", "Email", "SoDienThoai", "MaLop", "MaKhoa", "GPA", "XepLoai"};
    
    private List<SinhVien> danhSachSinhVien;
    private LopHocRepository lopHocRepository;
    private KhoaRepository khoaRepository;
    
    public SinhVienRepository(LopHocRepository lopHocRepository, KhoaRepository khoaRepository) {
        this.lopHocRepository = lopHocRepository;
        this.khoaRepository = khoaRepository;
        this.danhSachSinhVien = new ArrayList<>();
        try {
            docFile();
        } catch (IOException e) {
            System.err.println("Khong the doc file sinh vien: " + e.getMessage());
        }
    }
    
    @Override
    public void luuFile() throws IOException {
        List<String[]> records = new ArrayList<>();
        for (SinhVien sv : danhSachSinhVien) {
            String[] record = {
                sv.getMa(),
                sv.getHoTen(),
                sv.getNgaySinh() != null ? sv.getNgaySinh().toString() : "",
                sv.getEmail() != null ? sv.getEmail() : "",
                sv.getSoDienThoai() != null ? sv.getSoDienThoai() : "",
                sv.getLopHoc() != null ? sv.getLopHoc().getMaLop() : "",
                sv.getKhoa() != null ? sv.getKhoa().getMaKhoa() : "",
                String.format("%.2f", sv.getGpa()),
                sv.getXepLoai()
            };
            records.add(record);
        }
        CSVUtils.ghiCSV(FILE_PATH, records, HEADER);
    }
    
    @Override
    public void docFile() throws IOException {
        danhSachSinhVien.clear();
        List<String[]> records = CSVUtils.docCSV(FILE_PATH);
        
        for (int i = 0; i < records.size(); i++) {
            if (i == 0 && records.get(i)[0].equals("MaSinhVien")) {
                continue; // Bỏ qua header
            }
            
            String[] record = records.get(i);
            if (record.length >= 2) {
                SinhVien sv = new SinhVien();
                sv.setMa(record[0]);
                sv.setHoTen(record[1]);
                
                if (record.length > 2 && !record[2].isEmpty()) {
                    try {
                        sv.setNgaySinh(LocalDate.parse(record[2]));
                    } catch (Exception e) {
                        sv.setNgaySinh(null);
                    }
                }
                
                if (record.length > 3) sv.setEmail(record[3]);
                if (record.length > 4) sv.setSoDienThoai(record[4]);
                
                if (record.length > 5 && !record[5].isEmpty()) {
                    Optional<LopHoc> lop = lopHocRepository.timTheoMa(record[5]);
                    lop.ifPresent(sv::setLopHoc);
                }
                
                if (record.length > 6 && !record[6].isEmpty()) {
                    Optional<Khoa> khoa = khoaRepository.timTheoMa(record[6]);
                    khoa.ifPresent(sv::setKhoa);
                }
                
                if (record.length > 7 && !record[7].isEmpty()) {
                    try {
                        sv.setGpa(Double.parseDouble(record[7]));
                    } catch (NumberFormatException e) {
                        sv.setGpa(0.0);
                    }
                }
                
                if (record.length > 8) {
                    sv.setXepLoai(record[8]);
                } else {
                    sv.setXepLoai("Chua xep loai");
                }
                
                danhSachSinhVien.add(sv);
            }
        }
    }
    
    // CRUD operations
    public void them(SinhVien sv) throws IOException {
        danhSachSinhVien.add(sv);
        luuFile();
    }
    
    public void capNhat(SinhVien sv) throws IOException {
        for (int i = 0; i < danhSachSinhVien.size(); i++) {
            if (danhSachSinhVien.get(i).getMa().equals(sv.getMa())) {
                danhSachSinhVien.set(i, sv);
                luuFile();
                return;
            }
        }
    }
    
    public void xoa(String maSinhVien) throws IOException {
        danhSachSinhVien.removeIf(sv -> sv.getMa().equals(maSinhVien));
        luuFile();
    }
    
    public Optional<SinhVien> timTheoMa(String maSinhVien) {
        return danhSachSinhVien.stream()
                .filter(sv -> sv.getMa().equals(maSinhVien))
                .findFirst();
    }
    
    public List<SinhVien> timTheoTen(String hoTen) {
        return danhSachSinhVien.stream()
                .filter(sv -> sv.getHoTen().toLowerCase().contains(hoTen.toLowerCase()))
                .toList();
    }
    
    public List<SinhVien> timTheoLop(String maLop) {
        return danhSachSinhVien.stream()
                .filter(sv -> sv.getLopHoc() != null && sv.getLopHoc().getMaLop().equals(maLop))
                .toList();
    }
    
    public List<SinhVien> timTheoKhoa(String maKhoa) {
        return danhSachSinhVien.stream()
                .filter(sv -> sv.getKhoa() != null && sv.getKhoa().getMaKhoa().equals(maKhoa))
                .toList();
    }
    
    public List<SinhVien> layTatCa() {
        return new ArrayList<>(danhSachSinhVien);
    }
    
    public boolean tonTai(String maSinhVien) {
        return danhSachSinhVien.stream()
                .anyMatch(sv -> sv.getMa().equals(maSinhVien));
    }
    
    /**
     * Tìm kiếm mờ theo tên sinh viên (fuzzy search)
     * @param searchTerm Từ khóa tìm kiếm
     * @return Danh sách sinh viên phù hợp
     */
    public List<SinhVien> timKiemMo(String searchTerm) {
        String[] keywords = searchTerm.toLowerCase().split("\\s+");
        return danhSachSinhVien.stream()
                .filter(sv -> {
                    String hoTen = sv.getHoTen().toLowerCase();
                    for (String keyword : keywords) {
                        if (!hoTen.contains(keyword)) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList();
    }
}

