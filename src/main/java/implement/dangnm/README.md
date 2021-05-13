# Mô hình hóa bài toán
**Lưu ý:** Mô hình này được sử dụng cho Constraints Programming và Constraint-based local search

## Biến
```textmate
X[i]: kíp thi của môn i
        D(X)={1,...,N}
        i={0,...,N-1}
Y[i][j]: môn i được xếp vào phòng j
        D(Y)={0,1}
        i={0,...,N-1}, j={0,...,M-1}
```
## Ràng buộc
### C1
```textmate
2 môn conflict không được xếp chung một kíp
```
### C2
```textmate
2 môn cùng kíp thì không được xếp chung một phòng
```
### C3
```textmate
1 môn chỉ được xếp vào một phòng duy nhất
```
### C4
```textmate
Chỉ xếp môn học vào phòng có sức chứa phù hơp
```
## Hàm mục tiêu
```textmate
Objective = max(X) -> min
```