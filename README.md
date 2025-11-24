# HỆ THỐNG QUẢN LÝ ĐIỂM VÀ BẢNG XẾP HẠNG SINH VIÊN

## Mô tả dự án

Đây là một Java Console Application hỗ trợ quản lý điểm học phần của sinh viên và tạo bảng xếp hạng theo lớp, khoa hoặc toàn trường. Ứng dụng được xây dựng hoàn toàn bằng Java với thiết kế hướng đối tượng (OOP), không sử dụng cơ sở dữ liệu mà lưu trữ dữ liệu bền vững bằng file CSV.

## Tính năng chính

### 1. Quản lý dữ liệu
- Quản lý học phần: thêm, sửa, xóa, tìm kiếm
- Quản lý sinh viên: nhập, chỉnh sửa thông tin cơ bản
- Quản lý giảng viên: nhập, chỉnh sửa thông tin
- Quản lý lớp học và khoa

### 2. Quản lý điểm
- Nhập điểm quiz, giữa kỳ, cuối kỳ, bài tập lớn
- Sửa điểm khi nhập sai
- Xem bảng điểm chi tiết của sinh viên
- Validation điểm (0-10)

### 3. Tính toán và xếp loại
- Tính điểm tổng kết theo công thức trọng số
- Tính GPA 
- Xếp loại học lực: Xuất sắc, Giỏi, Khá, Trung bình, Yếu

### 4. Bảng xếp hạng
- Xếp hạng theo lớp
- Xếp hạng theo khoa
- Xếp hạng toàn trường
- Top N sinh viên
- Lọc theo GPA tối thiểu

### 5. Báo cáo và thống kê
- Danh sách sinh viên có GPA cao nhất
- Tỷ lệ sinh viên đạt từng mức xếp loại
- Danh sách sinh viên cần học lại
- Histogram phân bố điểm (text-based)
- Xuất báo cáo ra file CSV

### 6. Tính năng nâng cao
- Fuzzy search (tìm kiếm mờ) theo tên sinh viên/học phần
- Histogram phân bố điểm theo GPA và theo học phần
- Lọc bảng xếp hạng theo GPA tối thiểu

## Kiến trúc OOP

### Kế thừa và Đa hình
```
Person (abstract)
├── SinhVien
└── GiangVien

DanhGia (abstract)
├── BaiKiemTra (Quiz)
├── ThiGiuaKy
├── ThiCuoiKy
└── BaiTapLon
```

### Interfaces
- **LuuTru**: Định nghĩa `luuFile()` và `docFile()`
- **XepHang**: Định nghĩa `tinhGPA()` và `xepLoaiHocLuc()`

### Exceptions tùy biến
- `DiemKhongHopLeException`
- `KhongTimThaySinhVienException`
- `KhongTimThayHocPhanException`
- `DuLieuKhongHopLeException`
- `KhongTimThayGiangVienException`

## Cấu trúc dự án

```
BTL/
├── src/
│   └── com/quanlydiem/
│       ├── model/
│       │   ├── nguoi/          # Person, SinhVien, GiangVien
│       │   ├── danhgia/        # DanhGia và các subclass
│       │   ├── hocphan/        # HocPhan, Diem
│       │   └── tochuc/         # LopHoc, Khoa
│       ├── giaodien/           # Interfaces (LuuTru, XepHang)
│       ├── ngoaile/            # Custom exceptions
│       ├── luutru/             # CSV utilities & Repositories
│       ├── dichvu/             # Services (TinhDiemService, XepLoaiService, etc.)
│       ├── menu/               # CLI menu system
│       └── Main.java           # Entry point
├── data/                       # CSV data files
│   ├── faculties.csv
│   ├── class_groups.csv
│   ├── lecturers.csv
│   ├── courses.csv
│   ├── students.csv
│   └── grades.csv
├── test/                       # Test cases
│   └── TEST_CASES.md
├── docs/                       # Documentation
│   └── class-diagram.puml
└── README.md
```

## Lưu trữ dữ liệu (CSV)

### Cấu trúc các file CSV:

**faculties.csv** - Thông tin khoa
```csv
MaKhoa,TenKhoa,TruongKhoa,DienThoai
CNTT,Cong nghe thong tin,PGS.TS Tran Dang Cong,0123456789
```

**students.csv** - Thông tin sinh viên
```csv
MaSinhVien,HoTen,NgaySinh,Email,SoDienThoai,MaLop,MaKhoa,GPA,XepLoai
SV001,Nguyen Van A,2004-01-15,nvan@student.edu.vn,0912345001,CNTT01,CNTT,3.65,Xuat sac
```

**grades.csv** - Điểm học phần
```csv
MaDiem,MaSinhVien,MaHocPhan,DiemQuiz,DiemGiuaKy,DiemCuoiKy,DiemBaiTap,DiemTongKet,DiemChu,DaDat
D001,SV001,HP001,8.50,8.00,9.00,8.50,8.65,B+,true
```

## Hướng dẫn cài đặt và chạy

### Yêu cầu hệ thống
- Java Development Kit (JDK) 11 trở lên
- Windows/Linux/macOS

### Cách 1: Chạy từ source code

1. **Clone hoặc download dự án**
```bash
cd ...
```

2. **Biên dịch source code**
```bash
# Windows
javac -encoding UTF-8 -d bin -sourcepath src src/com/quanlydiem/Main.java src/com/quanlydiem/**/*.java

# Linux/Mac
javac -encoding UTF-8 -d bin -sourcepath src $(find src -name "*.java")
```

3. **Chạy ứng dụng**
```bash
java -cp bin com.quanlydiem.Main
```

### Cách 2: Sử dụng IDE

1. Mở dự án bằng IntelliJ IDEA / Eclipse / NetBeans
2. Đảm bảo JDK đã được cấu hình
3. Chạy file `src/com/quanlydiem/Main.java`

### Cách 3: Sử dụng run.bat (Windows)

**Lưu ý**: Cách này chỉ dành cho Windows. Nếu bạn dùng Linux/Mac, hãy sử dụng Cách 1 hoặc Cách 2.

- **Double-click vào file `run.bat`**
- Ứng dụng sẽ khởi động ngay lập tức

#### Lưu ý khi sử dụng:
- Đảm bảo đã cài đặt JDK và có thể chạy lệnh `java` và `javac` từ Command Prompt
- Nếu gặp lỗi "java is not recognized", hãy cài đặt JDK và thêm vào PATH
- File `run.bat` sẽ tự động pause sau khi thoát ứng dụng để bạn có thể xem thông báo lỗi (nếu có)

## Dữ liệu mẫu

Hệ thống đi kèm với dữ liệu mẫu đầy đủ:
- 3 khoa: CNTT, Điện, Cơ khí
- 4 lớp học
- 10 học phần (courses)
- 7 giảng viên
- 22 sinh viên
- 70 bản ghi điểm

## Hướng dẫn sử dụng

### 1. Menu chính
Khi khởi động, bạn sẽ thấy menu chính với các tùy chọn:
```
===============================================
    MENU CHINH - HE THONG QUAN LY DIEM
===============================================
1. Quan ly (Khoa/Lop/Giang vien/Hoc phan/Sinh vien)
2. Quan ly diem
3. Bang xep hang
4. Bao cao va thong ke
5. Tim kiem
6. Luu du lieu
0. Thoat
===============================================
```

### 2. Quản lý điểm
- Chọn menu **2. Quan ly diem**
- Nhập điểm cho sinh viên bằng cách nhập mã sinh viên và mã học phần
- Nhập điểm quiz, giữa kỳ, cuối kỳ, bài tập (từ 0-10)
- Hệ thống tự động tính điểm tổng kết

### 3. Xem bảng xếp hạng
- Chọn menu **3. Bang xep hang**
- Chọn loại xếp hạng: toàn trường, theo khoa, hoặc theo lớp
- Kết quả hiển thị danh sách sinh viên sắp xếp theo GPA giảm dần

### 4. Xem báo cáo và thống kê
- Chọn menu **4. Bao cao va thong ke**
- Xem histogram phân bố điểm
- Xem tỷ lệ xếp loại
- Danh sách sinh viên cần học lại
- Xuất báo cáo ra file CSV

### 5. Tìm kiếm
- Chọn menu **5. Tim kiem**
- Tìm kiếm sinh viên theo tên (hỗ trợ fuzzy search)
- Tìm kiếm học phần theo tên hoặc mã
- Tìm kiếm giảng viên

## Công thức tính điểm

### Điểm tổng kết học phần
```
Điểm tổng kết = DiemQuiz × 10% + DiemGiuaKy × 20% + DiemCuoiKy × 50% + DiemBaiTap × 20%
```

### GPA (Grade Point Average)
```
GPA = Σ(Điểm tổng kết × Số tín chỉ) / Σ(Số tín chỉ)
```

### Xếp loại học lực
- **Xuất sắc**: GPA ≥ 9.0
- **Giỏi**: 8.0 ≤ GPA < 9.0
- **Khá**: 6.5 ≤ GPA < 8.0
- **Trung bình**: 5.0 ≤ GPA < 6.5
- **Yếu**: GPA < 5.0

## Test Cases

Xem file [test/TEST_CASES.md](test/TEST_CASES.md) để biết chi tiết 10 test cases bắt buộc.

## Biểu đồ lớp (Class Diagram)

Xem file [docs/class-diagram.puml](docs/class-diagram.puml) để xem sơ đồ lớp chi tiết.

## Điểm nổi bật

1. **Thiết kế OOP chuẩn**: Sử dụng kế thừa, đa hình, abstract class, interface
2. **Clean Architecture**: Phân tách rõ ràng giữa model, repository, service, và presentation
3. **Error Handling**: Custom exceptions cho từng trường hợp lỗi cụ thể
4. **Data Validation**: Kiểm tra tính hợp lệ của dữ liệu đầu vào
5. **Persistence**: Lưu trữ bền vững bằng CSV, tự động load/save
6. **Advanced Features**: Histogram, fuzzy search, filtering


## Xử lý lỗi

- Điểm không hợp lệ (< 0 hoặc > 10) → Exception
- Không tìm thấy sinh viên/học phần → Exception với message rõ ràng
- File không tồn tại → Tự động tạo file mới
- Dữ liệu không đồng bộ → Validation và báo lỗi

## Ghi chú

- Tất cả dữ liệu được lưu trong thư mục `data/`
- Dữ liệu được tự động load khi khởi động và save khi thoát
- GPA được tính theo thang 4.0
- Encoding: UTF-8 để hỗ trợ tiếng Việt

---

**Môn học**: Lập trình Hướng Đối Tượng (OOP)  
**Đề tài**: 3 - Quản lý điểm và bảng xếp hạng sinh viên


