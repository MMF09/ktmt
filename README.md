# ArchLab Android

Ứng dụng Android viết bằng Java, đóng gói giáo trình ArchLab và website hiện có để học ngoại tuyến. Các bài học, tìm kiếm, lọc cấp độ, giao diện sáng/tối và tiến độ được hiển thị trong WebView; Android lưu `localStorage` của ứng dụng trên thiết bị. Liên kết tham khảo bên ngoài sẽ mở bằng trình duyệt.

## Mở bằng Android Studio

Mở thư mục gốc này trong Android Studio, chờ Gradle đồng bộ, sau đó chạy cấu hình `app` trên máy ảo hoặc thiết bị Android.

## Build APK debug

Yêu cầu JDK 17, Gradle 8.9 và Android SDK Platform 35:

```sh
gradle --no-daemon assembleDebug
```

APK được tạo tại `app/build/outputs/apk/debug/app-debug.apk`.

## Build APK bằng GitHub Actions

Workflow `.github/workflows/build-apk.yml` tự build khi push lên `main`/`master`, mở pull request hoặc chạy thủ công. Tải APK từ artifact `archlab-debug-apk` trong trang Actions của lần chạy tương ứng.

## Cập nhật nội dung website trong app

Các tài nguyên đóng gói nằm trong `app/src/main/assets/`. Khi sửa `index.html`, `styles.css` hoặc `curriculum.js` ở thư mục gốc, đồng bộ lại ba tệp vào thư mục assets trước khi build:

```sh
cp index.html styles.css curriculum.js app/src/main/assets/
```
