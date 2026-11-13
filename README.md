# ArchLab — ứng dụng kiến trúc máy tính

ArchLab là ứng dụng học kiến trúc máy tính dành cho Android, được viết bằng Java và Android Views thuần. Giao diện, điều hướng, tìm kiếm, bài đọc, chế độ sáng/tối, tiến độ và icon đều chạy native; dự án không còn WebView, HTML, CSS, JavaScript hay dữ liệu JSON trong luồng build.

## Cấu trúc dự án

```text
.
├── COURSE.md
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/vn/archlab/app/
│       │   ├── MainActivity.java
│       │   └── CourseData.java
│       └── res/
│           ├── drawable/               # Icon ArchLab
│           ├── mipmap-anydpi/          # Icon launcher
│           ├── mipmap-anydpi-v26/      # Adaptive icon
│           └── values/                 # Tên ứng dụng và theme
├── .github/workflows/build-apk.yml
├── build.gradle
├── gradle.properties
└── settings.gradle
```

## Giáo trình

`CourseData.java` chứa toàn bộ dữ liệu 12 mô-đun cùng bài đọc, ví dụ, mục tiêu và bài tập. Mỗi bài đọc có bốn phần và trên 300 từ; ứng dụng kiểm tra điều kiện độ dài khi nạp dữ liệu. `COURSE.md` mô tả lộ trình 12 tuần, đồ án RISC-V và tiêu chí đánh giá.

## Mở và chạy

Mở thư mục gốc bằng Android Studio, chờ Gradle sync và chạy cấu hình `app` trên thiết bị hoặc emulator. Dự án yêu cầu JDK 17 và Android SDK Platform 35.

## Build APK debug

Trong Android Studio chạy task `assembleDebug`, hoặc dùng terminal có Gradle 8.9:

```sh
gradle --no-daemon assembleDebug
```

APK được tạo tại `app/build/outputs/apk/debug/app-debug.apk`.

Workflow `.github/workflows/build-apk.yml` tự build khi push lên nhánh `main`/`master`, mở pull request hoặc chạy thủ công. Artifact có tên `archlab-debug-apk`.

## Giao diện và dữ liệu

- Màn hình học tập có tổng quan tiến độ, tìm kiếm theo nội dung bài, lọc cấp độ và điều hướng dưới.
- Màn đọc bài có tóm tắt, mục tiêu, thuật ngữ, bài đọc dài, ví dụ, bài tập, đáp án tự kiểm tra và điều hướng bài tiếp theo.
- Màn lộ trình và tài nguyên tham khảo được dựng bằng Android Views.
- `SharedPreferences` lưu tiến độ cùng giao diện sáng/tối trên thiết bị.
- Các liên kết học liệu bên ngoài mở bằng trình duyệt; ứng dụng không cần quyền Internet.
- Icon launcher là vector native, tự co giãn theo launcher Android.
