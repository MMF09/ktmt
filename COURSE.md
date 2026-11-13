# Giáo trình kiến trúc máy tính hiện đại

## Mô tả

Giáo trình dẫn người học từ biểu diễn dữ liệu và mạch logic tới ISA, vi kiến trúc CPU, phân cấp bộ nhớ và kiến trúc song song. Mỗi mô-đun trong ứng dụng Android native có bài đọc dài trên 300 từ, mục tiêu, ví dụ, bài thực hành và câu tự kiểm tra. Đây là giáo trình nhập môn có phần mở rộng nâng cao, không thay thế tài liệu đặc tả hoặc tài liệu kỹ thuật của từng bộ xử lý.

**Thời lượng gợi ý:** 48 giờ trong 12 tuần, khoảng 4 giờ mỗi tuần.  
**Đối tượng:** sinh viên công nghệ thông tin, kỹ sư phần mềm hoặc người học đã biết lập trình căn bản.  
**Tiên quyết:** biến, điều kiện, vòng lặp và hàm trong một ngôn ngữ lập trình; toán học phổ thông. Không bắt buộc biết điện tử.

## Kết quả học tập

Sau khi hoàn thành, người học có thể:

1. Giải thích các lớp trừu tượng và phân biệt ISA với vi kiến trúc.
2. Biểu diễn số nguyên, số thực và phân tích giới hạn độ rộng bit.
3. Đọc hợp ngữ RISC-V căn bản và lần theo dữ liệu qua datapath.
4. Phân tích pipeline, hazards, cache, bộ nhớ ảo và các thước đo hiệu năng.
5. Giải thích các vấn đề cơ bản của đa lõi, GPU, I/O và bảo mật vi kiến trúc.
6. Đề xuất, kiểm thử và trình bày một thiết kế CPU giáo khoa cùng các đánh đổi.

## Lộ trình theo tuần

| Tuần | Mô-đun | Trọng tâm | Sản phẩm học tập |
|---|---|---|---|
| 1 | Từ transistor đến hệ thống | Lớp trừu tượng, ISA, latency/throughput | Sơ đồ các lớp của một hệ thống |
| 2 | Logic số & biểu diễn dữ liệu | Nhị phân, bù hai, số thực, mạch | Bộ cộng 4-bit và bài phân tích tràn |
| 3 | ISA & hợp ngữ RISC-V | Thanh ghi, load/store, nhánh, ABI | Vòng lặp C chuyển thành hợp ngữ |
| 4 | Datapath & điều khiển CPU | ALU, register file, PC, tín hiệu | Datapath cho add, lw, sw, beq |
| 5 | Pipeline & hazards | Pipeline 5 giai đoạn, forwarding, stall | Biểu đồ pipeline có hazards |
| 6 | Hiệu năng & thực thi ngoài thứ tự | CPI, Amdahl, superscalar, OoO | Bài tính speedup và phân tích nút thắt |
| 7 | Cache & tính cục bộ | Tag/index/offset, hit/miss, AMAT | Mô phỏng truy cập cache |
| 8 | Bộ nhớ ảo & phân trang | MMU, TLB, page table, page fault | Sơ đồ dịch địa chỉ |
| 9 | DRAM, lưu trữ & I/O | Độ trễ, băng thông, DMA, ngắt | Bảng so sánh phân cấp lưu trữ |
| 10 | Đa lõi & nhất quán bộ nhớ | MESI, race, atomic, false sharing | Phân tích chương trình nhiều luồng |
| 11 | GPU & accelerator | SIMD/SIMT, throughput, băng thông | Đề xuất kiến trúc cho một workload |
| 12 | Bảo mật, độ tin cậy & đồ án | Side channel, kiểm thử, tổng hợp | Báo cáo và demo CPU thu nhỏ |

## Cách học mỗi mô-đun trong ứng dụng

1. Đọc mục tiêu và bốn phần bài đọc trong ứng dụng ArchLab.
2. Tự vẽ sơ đồ hoặc tính ví dụ trước khi xem lại ghi chú.
3. Làm bài tập thực hành; ghi rõ giả định về độ rộng, chu kỳ và chính sách bộ nhớ.
4. Trả lời câu tự kiểm tra bằng lời của mình, sau đó mở phần gợi ý để đối chiếu.
5. Đánh dấu hoàn thành khi có thể giải thích lại khái niệm và làm bài tập mà không dựa vào đáp án.

## Đồ án cuối khóa: CPU RISC-V giáo khoa

### Mục tiêu

Thiết kế và kiểm thử một bộ xử lý nhỏ thực hiện một tập lệnh con. Mục tiêu là hiểu luồng dữ liệu, tín hiệu điều khiển và cách kiểm chứng—không phải tạo CPU thương mại hay triển khai ISA đầy đủ.

### Phạm vi lệnh đề xuất

`add`, `sub`, `and`, `or`, `addi`, `lw`, `sw`, `beq`, `jal`.

Có thể chọn CPU đơn chu kỳ để tập trung vào datapath hoặc mở rộng pipeline 5 giai đoạn nếu đã hoàn thành phần hazard. Ghi rõ độ rộng thanh ghi, kích thước bộ nhớ, căn chỉnh địa chỉ, hành vi khi lệnh không hợp lệ và mọi giả định khác.

### Các mốc

1. **Đặc tả:** bảng lệnh, tác động lên thanh ghi/bộ nhớ/PC và mẫu chương trình.
2. **Thiết kế:** sơ đồ khối datapath, bộ giải mã và bảng tín hiệu điều khiển.
3. **Hiện thực:** simulator, HDL hoặc mô hình có thể chạy; nêu công cụ và cách tái lập.
4. **Kiểm thử:** kiểm thử ALU, decoder, load/store, nhánh tiến/lùi, vòng lặp và trường hợp biên.
5. **Đánh giá:** phân tích số chu kỳ, đường tới hạn hoặc hazards; nêu giới hạn và hướng mở rộng.

### Tiêu chí đánh giá

| Tiêu chí | Tỷ trọng |
|---|---:|
| Thực thi đúng đặc tả và xử lý đủ lệnh | 35% |
| Datapath, điều khiển và giải thích thiết kế | 25% |
| Bộ kiểm thử rõ ràng, có trường hợp biên, tái lập được | 20% |
| Phân tích hiệu năng, năng lượng, giới hạn và đánh đổi | 20% |

## Tài nguyên tham khảo

Các nguồn sau dùng để đọc sâu, tra chuẩn hoặc thực hành. Phần bài đọc trong ArchLab được diễn đạt mới và không sao chép nội dung có bản quyền.

- [RISC-V International — Specifications](https://riscv.org/technical/specifications/): đặc tả chính thức; tra cứu khi cần xác minh mã hóa lệnh và hành vi ISA.
- [UC Berkeley CS 61C](https://cs61c.org/): khóa học tổ chức máy tính, hợp ngữ RISC-V và tính toán song song.
- [Nand2Tetris](https://www.nand2tetris.org/): chuỗi bài tập xây dựng hệ thống từ logic số lên các lớp phần mềm.
- [Ripes](https://github.com/mortbopet/Ripes): công cụ mô phỏng pipeline RISC-V trực quan.
- [Venus](https://venus.cs61c.org/): trình mô phỏng hợp ngữ RISC-V trong trình duyệt.

## Phạm vi và thuật ngữ

Các sơ đồ và mô hình trong giáo trình là mô hình sư phạm. Bộ xử lý thương mại có thể dùng cấu trúc phức tạp hơn, khác nhau giữa các nhà sản xuất và thế hệ. “Coherence” và “consistency” là hai khái niệm liên quan nhưng không đồng nghĩa. Từ “từ” (word) luôn cần đi kèm kiến trúc cụ thể để biết độ rộng.
