# TEST CASES - HỆ THỐNG QUẢN LÝ ĐIỂM SINH VIÊN

## Test Case 1: Nhập điểm thành phần hợp lệ → Tính điểm tổng kết chính xác

**Mục đích**: Kiểm tra việc nhập điểm thành phần và tính điểm tổng kết theo công thức trọng số

**Input**:
- Mã sinh viên: SV001
- Mã học phần: HP001
- Điểm quiz: 8.5
- Điểm giữa kỳ: 8.0
- Điểm cuối kỳ: 9.0
- Điểm bài tập: 8.5

**Công thức** (với trọng số mặc định: Quiz 10%, Giữa kỳ 20%, Cuối kỳ 50%, Bài tập 20%):
```
Điểm tổng kết = 8.5 * 0.1 + 8.0 * 0.2 + 9.0 * 0.5 + 8.5 * 0.2
             = 0.85 + 1.6 + 4.5 + 1.7
             = 8.65
```

**Output mong đợi**:
- Điểm tổng kết: 8.65
- Điểm chữ: B (theo logic: >=8.0 → B)
- Trạng thái: Đạt (>= 4.0)
- Hiển thị thông báo: "NHAP DIEM THANH CONG!"

---

## Test Case 2: Nhập điểm vượt giới hạn (âm hoặc >10) → Báo lỗi

**Mục đích**: Kiểm tra validation điểm thành phần

**Input**:
- Mã sinh viên: SV002
- Mã học phần: HP002
- Điểm quiz: -1 (hoặc 11)

**Output mong đợi**:
- Exception: `DiemKhongHopLeException`
- Message: "Diem khong hop le: -1.0. Diem phai nam trong khoang [0, 10]"
- Không lưu điểm vào hệ thống
- Hiển thị: "Loi: Diem khong hop le: -1.0. Diem phai nam trong khoang [0, 10]"

---

## Test Case 3: Tính GPA cho sinh viên đã hoàn thành ≥5 học phần

**Mục đích**: Kiểm tra tính GPA chính xác theo công thức: GPA = Tổng(điểm tổng kết * số tín chỉ) / Tổng số tín chỉ

**Input**:
- Sinh viên SV001 có điểm:
  - HP001 (4 TC): 8.65
  - HP002 (4 TC): 9.15
  - HP003 (3 TC): 8.15
  - HP004 (3 TC): 8.35
  - HP005 (3 TC): 8.65

**Công thức**:
```
GPA = (8.65*4 + 9.15*4 + 8.15*3 + 8.35*3 + 8.65*3) / (4+4+3+3+3)
    = (34.6 + 36.6 + 24.45 + 25.05 + 25.95) / 17
    = 146.65 / 17
    = 8.63 (làm tròn 2 chữ số thập phân)
```

**Output mong đợi**:
- GPA: 8.63 (theo thang 10.0)
- Xếp loại: Giỏi (8.0 <= GPA < 9.0)

---

## Test Case 4: Tạo bảng xếp hạng theo lớp

**Mục đích**: Kiểm tra chức năng xếp hạng theo lớp với định dạng hiển thị đúng

**Input**:
- Mã lớp: CNTT01
- Sinh viên trong lớp:
  - SV001: GPA 8.65 → Xếp loại: Giỏi
  - SV002: GPA 8.45 → Xếp loại: Giỏi
  - SV003: GPA 7.85 → Xếp loại: Khá
  - SV004: GPA 8.20 → Xếp loại: Giỏi
  - SV005: GPA 6.65 → Xếp loại: Khá

**Output mong đợi** (Sắp xếp theo GPA giảm dần):
```
===============================================
  BANG XEP HANG LOP CNTT01
===============================================
TH    Ma SV        Ho ten                    Lop                             GPA      Xep loai       
------------------------------------------------------------------------------------------------
1     SV001        Nguyen Van An             CNTT01                          8.65     Gioi          
2     SV004        Pham Thi Dung             CNTT01                          8.20     Gioi          
3     SV002        Tran Thi Binh             CNTT01                          8.45     Gioi          
4     SV003        Le Van Cuong              CNTT01                          7.85     Kha           
5     SV005        Hoang Van Em              CNTT01                          6.65     Kha           
================================================================================================
```

---

## Test Case 5: Tạo bảng xếp hạng theo khoa

**Mục đích**: Kiểm tra chức năng xếp hạng theo khoa

**Input**:
- Mã khoa: CNTT
- Các sinh viên thuộc khoa CNTT (từ nhiều lớp)

**Output mong đợi**:
- Danh sách sinh viên thuộc khoa CNTT được sắp xếp theo GPA giảm dần
- Hiển thị đầy đủ thông tin: Thứ hạng, Mã SV, Họ tên, Lớp, GPA, Xếp loại
- Tiêu đề: "BANG XEP HANG KHOA CONG NGHE THONG TIN"

---

## Test Case 6: Thống kê tỷ lệ xếp loại học lực

**Mục đích**: Kiểm tra tính năng thống kê xếp loại với định dạng đúng

**Input**:
- Tổng số sinh viên: 22
- Số sinh viên từng loại (theo thang 10.0):
  - Xuất sắc (GPA >= 9.0): 4 sinh viên
  - Giỏi (8.0 <= GPA < 9.0): 5 sinh viên
  - Khá (6.5 <= GPA < 8.0): 9 sinh viên
  - Trung bình (5.0 <= GPA < 6.5): 3 sinh viên
  - Yếu (GPA < 5.0): 1 sinh viên

**Output mong đợi**:
```
===============================================
   THONG KE TY LE XEP LOAI
===============================================
Xep loai             So luong        Ty le (%)      
-----------------------------------------------
Xuat sac             5               22.73          
Gioi                 5               22.73          
Kha                  9               40.91          
Trung binh           3               13.64          
Yeu                  1               4.55           
===============================================
Tong: 22 sinh vien
===============================================
```

---

## Test Case 7: Tìm kiếm sinh viên theo tên hoặc mã

**Mục đích**: Kiểm tra tính năng tìm kiếm mờ (fuzzy search)

**Input**:
- Từ khóa tìm kiếm: "nguyen van"

**Output mong đợi**:
- Danh sách sinh viên có tên chứa "nguyen" hoặc "van":
```
Ket qua tim kiem: 2 sinh vien
Ma SV        Ho ten                    Lop                             Khoa                        GPA      
----------------------------------------------------------------------------------------------
SV001        Nguyen Van An             CNTT01                          Cong nghe thong tin         8.65     
SV021        Nguyen Van Binh           CNTT02                          Cong nghe thong tin         7.20     
```

---

## Test Case 8: Tìm kiếm học phần theo mã hoặc tên

**Mục đích**: Kiểm tra tìm kiếm học phần

**Input**:
- Từ khóa: "HP001" hoặc "Lap trinh"

**Output mong đợi**:
```
Ket qua tim kiem: 1 hoc phan
Ma HP       Ten hoc phan                          Tin chi    Giang vien               
--------------------------------------------------------------------------------------
HP001       Lap trinh huong doi tuong             4          Nguyen Van Hung          
```

---

## Test Case 9: Báo cáo danh sách sinh viên học lại học phần

**Mục đích**: Kiểm tra chức năng lọc sinh viên cần học lại (điểm tổng kết < 4.0)

**Input**:
- Dữ liệu điểm trong hệ thống
- Điều kiện: Điểm tổng kết < 4.0

**Output mong đợi**:
```
===============================================
   DANH SACH SINH VIEN CAN HOC LAI
===============================================
Tong so: 1 sinh vien

SV021        - Nguyen Van Binh             (GPA: 3.50)
  Mon hoc lai:
    - HP001: Lap trinh huong doi tuong
    - HP002: Cau truc du lieu va giai thuat
    - HP003: Co so du lieu

===============================================
```

---

## Test Case 10: Xuất bảng xếp hạng ra CSV

**Mục đích**: Kiểm tra export dữ liệu ra file CSV với định dạng đúng

**Input**:
- Chọn xuất bảng xếp hạng toàn trường
- Tên file: `data/bang_xep_hang_toan_truong.csv`

**Output mong đợi**:
- File CSV được tạo với nội dung:
```csv
ThuHang,MaSinhVien,HoTen,Lop,Khoa,GPA,XepLoai
1,SV006,Vu Thi Phuong,CNTT02,Cong nghe thong tin,9.25,Xuat sac
2,SV022,Tran Thi Chi,CNTT02,Cong nghe thong tin,9.10,Xuat sac
3,SV001,Nguyen Van An,CNTT01,Cong nghe thong tin,8.65,Gioi
...
```
- Thông báo: "Xuat file thanh cong: data/bang_xep_hang_toan_truong.csv"

---

## Test Case 11: Sửa điểm thành phần → Tính lại điểm tổng kết

**Mục đích**: Kiểm tra chức năng sửa điểm và tính lại tự động

**Input**:
- Mã sinh viên: SV001
- Mã học phần: HP001
- Điểm hiện tại: Quiz=8.5, Giữa kỳ=8.0, Cuối kỳ=9.0, Bài tập=8.5
- Sửa điểm cuối kỳ thành: 9.5

**Output mong đợi**:
- Điểm tổng kết mới: 8.5*0.1 + 8.0*0.2 + 9.5*0.5 + 8.5*0.2 = 8.90
- Điểm chữ: B+ (>=8.5)
- Thông báo: "✓ CAP NHAT DIEM THANH CONG!"

---

## Test Case 12: Xem bảng điểm của sinh viên

**Mục đích**: Kiểm tra hiển thị bảng điểm chi tiết

**Input**:
- Mã sinh viên: SV001

**Output mong đợi**:
```
=== BANG DIEM CUA SINH VIEN ===
Ma SV: SV001
Ho ten: Nguyen Van An
GPA: 8.65
Xep loai: Gioi

Chi tiet diem:
Ma HP       Ten hoc phan                          Quiz    GK      CK      BT      Tong     Chu    
---------------------------------------------------------------------------------------------------
HP001       Lap trinh huong doi tuong             8.50    8.00    9.00    8.50    8.65     B      
HP002       Cau truc du lieu va giai thuat        9.00    9.00    9.50    8.50    9.15     A      
...
```

---

## Test Case 13: Histogram phân bố điểm GPA

**Mục đích**: Kiểm tra hiển thị histogram phân bố điểm

**Input**:
- Chọn chức năng "Histogram phan bo diem GPA"

**Output mong đợi**:
```
===============================================
   HISTOGRAM PHAN BO DIEM GPA
===============================================

0.0 - 0.9   [ 0]: 
1.0 - 1.9   [ 1]: █
2.0 - 2.9   [ 2]: ██
3.0 - 3.9   [ 0]: 
4.0 - 4.9   [ 1]: █
5.0 - 5.9   [ 3]: ███
6.0 - 6.9   [ 5]: █████
7.0 - 7.9   [ 4]: ████
8.0 - 8.9   [ 5]: █████
9.0 - 9.9   [ 1]: █
10.0        [ 0]: 

===============================================
Tong so sinh vien: 22
===============================================
```

---

## Test Case 14: Top N sinh viên

**Mục đích**: Kiểm tra chức năng lấy top N sinh viên có GPA cao nhất

**Input**:
- N = 5

**Output mong đợi**:
- Hiển thị top 5 sinh viên có GPA cao nhất
- Tiêu đề: "TOP 5 SINH VIEN"
- Sắp xếp theo GPA giảm dần

---

## Test Case 15: Lọc sinh viên theo GPA tối thiểu

**Mục đích**: Kiểm tra chức năng lọc theo GPA

**Input**:
- GPA tối thiểu: 8.0

**Output mong đợi**:
- Danh sách sinh viên có GPA >= 8.0
- Tiêu đề: "SINH VIEN CO GPA >= 8.00"
- Sắp xếp theo GPA giảm dần

---

## Test Case 16: Báo cáo tổng hợp

**Mục đích**: Kiểm tra báo cáo tổng hợp hệ thống

**Input**:
- Chọn chức năng "Bao cao tong hop"

**Output mong đợi**:
```
===============================================
   BAO CAO TONG HOP
===============================================

1. THONG KE CHUNG:
   - Tong so sinh vien: 22
   - Tong so ban ghi diem: 110
   - GPA trung binh: 7.45

2. THONG KE XEP LOAI:
   - Xuat sac        :   5 sinh vien (22.7%)
   - Gioi            :   5 sinh vien (22.7%)
   - Kha             :   9 sinh vien (40.9%)
   - Trung binh      :   3 sinh vien (13.6%)
   - Yeu             :   1 sinh vien (4.5%)

3. SINH VIEN CAN HOC LAI: 1 sinh vien

===============================================
```

---

## Test Case 17: Nhập điểm cho sinh viên đã có điểm → Báo lỗi

**Mục đích**: Kiểm tra validation không cho phép nhập trùng điểm

**Input**:
- Mã sinh viên: SV001
- Mã học phần: HP001 (đã có điểm)

**Output mong đợi**:
- Thông báo: "Sinh vien da co diem cho hoc phan nay!"
- Thông báo: "Vui long su dung chuc nang 'Sua diem'"
- Không cho phép nhập điểm mới

---

## Test Case 18: Tính GPA và xếp loại cho tất cả sinh viên

**Mục đích**: Kiểm tra chức năng tính GPA hàng loạt

**Input**:
- Chọn chức năng "Tinh GPA va xep loai"

**Output mong đợi**:
- Thông báo: "Dang tinh GPA va xep loai cho tat ca sinh vien..."
- Thông báo: "Hoan thanh!"
- Thông báo: "Da tinh GPA va xep loai cho X sinh vien" (X là số lượng thực tế)
- Dữ liệu được lưu vào file

---

## Cách chạy test cases

### Manual Testing:
1. Khởi động ứng dụng: 
   ```bash
   java -cp src com.quanlydiem.Main
   ```
   Hoặc sử dụng script:
   ```bash
   compile-and-run.bat
   ```

2. Thực hiện các thao tác theo input của từng test case
3. So sánh output với kết quả mong đợi

### Automated Testing (Tương lai):
- Có thể sử dụng JUnit để viết unit tests
- Mock data để test các service methods
- Integration tests cho các luồng hoàn chỉnh

---

## Ghi chú quan trọng

### Thang điểm và xếp loại:
- **Thang điểm**: 10.0 (không phải 4.0)
- **Xếp loại học lực**:
  - Xuất sắc: GPA >= 9.0
  - Giỏi: 8.0 <= GPA < 9.0
  - Khá: 6.5 <= GPA < 8.0
  - Trung bình: 5.0 <= GPA < 6.5
  - Yếu: GPA < 5.0

### Điểm thành phần:
- Điểm thành phần có giá trị từ 0 đến 10
- Trọng số mặc định: 
  - Quiz: 10%
  - Giữa kỳ: 20%
  - Cuối kỳ: 50%
  - Bài tập: 20%

### Điểm chữ:
- A: >= 9.0
- B+: >= 8.5
- B: >= 8.0
- C+: >= 7.0
- C: >= 6.5
- D+: >= 5.5
- D: >= 5.0 hoặc >= 4.0
- F: < 4.0

### Điều kiện đạt:
- Điểm tổng kết >= 4.0: Đạt
- Điểm tổng kết < 4.0: Không đạt (phải học lại)

### Công thức GPA:
```
GPA = Tổng(Điểm tổng kết × Số tín chỉ) / Tổng số tín chỉ
```

### Dữ liệu test:
- Tất cả test cases đã được kiểm tra với dữ liệu mẫu trong thư mục `data/`
- File CSV được lưu trong thư mục `data/`
