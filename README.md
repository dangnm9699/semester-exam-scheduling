# Tối ưu lập kế hoạch
**Học kỳ:** 20202 <br>
**Topics:** Semester exam schedule <br>
## Đề bài
### Mô tả
- Có ```N``` môn ```1,2,...,N``` cần được xếp lịch thi
- Môn ```i``` có số lượng sinh viên đăng ký thi là ```d(i)```
- Giữa ```N``` môn thi có danh sách các cặp 2 môn ```(i,j)``` không thể xếp trùng kíp, ngày
- Có ```M``` phòng thi ```1,2,...,M```, trong đó phòng ```j``` có số lượng chỗ ngồi là ```c(j)```
- Mỗi ngày được chia thành ```4``` kíp <br>

=> Hãy lập kế hoạch bố trí lịch và phòng cho các môn thi sao cho tổng số ngày diễn ra N môn thi là nhỏ nhất 
### Đầu vào
- Dòng 1: N
- Dòng 2: d<sub>1</sub>, d<sub>2</sub>, ..., d<sub>N</sub>
- Dòng 3: M
- Dòng 4: c<sub>1</sub>, c<sub>2</sub>, ..., c<sub><</sub>
- Dòng 5: K nguyên dương
- Dòng 5+k (k=1,...,K): các cặp i, j

## Hướng dẫn cài đặt OR-Tools Java
*OS: Ubuntu 20.04 LTS* <br>
**<u>Chú ý:</u> OR-Tools là thư viện C++, vì vậy, với Windows OS, đảm bảo cài đặt MingW. <u>Đảm bảo thêm các biến mỗi trường và thêm vào Path đầy đủ</u>**
### Cài đặt môi trường
```shell
sudo apt-get install openjdk-11-jdk
sudo apt-get install maven
```

### Cài đặt OR-Tools
Tải xuống và giải nén: [OR-Tools for Ubuntu 20.04 LTS](https://github.com/google/or-tools/releases/download/v8.2/or-tools_ubuntu-20.04_v8.2.8710.tar.gz)

### Kiểm tra cài đặt
Đi tới thư mục vừa giải nén
```shell
make test_java
```
Nếu các ví dụ chạy thành công, OR-Tools đã sẵn sàng