const modules = [
  {
    id: "01", phase: "NỀN TẢNG", level: "Nền tảng", duration: "3 giờ",
    title: "Từ transistor đến hệ thống",
    subtitle: "Các lớp trừu tượng và tư duy kiến trúc",
    description: "Định hình bức tranh tổng thể: một hệ thống máy tính được cấu thành từ những lớp nào, và quyết định kiến trúc ảnh hưởng ra sao đến hiệu năng, chi phí và năng lượng.",
    objectives: ["Mô tả các lớp từ transistor, logic, vi kiến trúc đến phần mềm.", "Phân biệt kiến trúc tập lệnh (ISA) với cách hiện thực vi kiến trúc.", "Giải thích các thước đo hiệu năng: latency, throughput và công suất."],
    topics: ["Lớp trừu tượng", "ISA vs. vi kiến trúc", "Hiệu năng", "Năng lượng"],
    practice: "Vẽ sơ đồ một máy tính bạn sử dụng hằng ngày; chỉ ra ranh giới giữa phần cứng, ISA, hệ điều hành và ứng dụng."
  },
  {
    id: "02", phase: "NỀN TẢNG", level: "Nền tảng", duration: "4 giờ",
    title: "Logic số & biểu diễn dữ liệu",
    subtitle: "Từ bit đến phép toán số học",
    description: "Tìm hiểu cách dữ liệu được mã hóa và xử lý bằng mạch số: hệ nhị phân, số nguyên có dấu, số thực dấu phẩy động và các khối logic tổ hợp, tuần tự.",
    objectives: ["Chuyển đổi giữa nhị phân, thập phân và thập lục phân.", "Giải thích bù hai, tràn số và chuẩn IEEE 754 ở mức khái niệm.", "Thiết kế bộ cộng, bộ chọn và mạch tuần tự đơn giản."],
    topics: ["Hệ cơ số", "Bù hai", "IEEE 754", "Boolean", "Mạch số"],
    practice: "Tự thiết kế bộ cộng 4-bit bằng các khối logic, rồi kiểm tra trường hợp tràn số có dấu."
  },
  {
    id: "03", phase: "NỀN TẢNG", level: "Nền tảng", duration: "4 giờ",
    title: "ISA & lập trình hợp ngữ RISC-V",
    subtitle: "Giao kèo giữa phần mềm và bộ xử lý",
    description: "Đọc một tập lệnh như giao diện ổn định của máy tính. Dùng RISC-V để kết nối câu lệnh C với thanh ghi, bộ nhớ, lệnh máy và quy ước gọi hàm.",
    objectives: ["Phân loại lệnh số học, tải/lưu, nhánh và nhảy.", "Lần theo ánh xạ từ mã C sang hợp ngữ và mã máy.", "Giải thích thanh ghi, stack, địa chỉ và quy ước gọi hàm."],
    topics: ["RISC-V", "Thanh ghi", "Load/store", "Stack", "Assembler"],
    practice: "Dịch một vòng lặp C nhỏ sang RISC-V; chạy thử bằng Ripes hoặc Venus và quan sát thanh ghi."
  },
  {
    id: "04", phase: "BỘ XỬ LÝ", level: "Trung cấp", duration: "4 giờ",
    title: "Datapath & điều khiển CPU",
    subtitle: "Một lệnh đi qua CPU như thế nào?",
    description: "Ghép ALU, thanh ghi, bộ nhớ và tín hiệu điều khiển thành đường dữ liệu có thể thực thi lệnh. Phân tích lựa chọn thiết kế CPU đơn chu kỳ và đa chu kỳ.",
    objectives: ["Truy vết fetch, decode, execute, memory và write-back.", "Đọc sơ đồ datapath cho lệnh RISC-V cơ bản.", "Xác định tín hiệu điều khiển và đường dữ liệu của từng loại lệnh."],
    topics: ["Datapath", "ALU", "Control unit", "Single-cycle", "Multi-cycle"],
    practice: "Vẽ datapath tối giản cho lệnh add và lw; đánh dấu các mux, tín hiệu điều khiển và nguồn dữ liệu."
  },
  {
    id: "05", phase: "BỘ XỬ LÝ", level: "Trung cấp", duration: "4 giờ",
    title: "Pipeline & hazards",
    subtitle: "Tăng thông lượng bằng xử lý chồng lấp",
    description: "Chia thực thi lệnh thành các giai đoạn để tăng thông lượng. Nhận diện xung đột dữ liệu, điều khiển và cấu trúc; tìm hiểu forwarding, stall và dự đoán nhánh.",
    objectives: ["Giải thích pipeline 5 giai đoạn và biểu đồ thời gian.", "Nhận diện RAW hazards và xác định khi nào cần forwarding hoặc stall.", "Mô tả flush và các chiến lược xử lý nhánh cơ bản."],
    topics: ["Pipeline", "Forwarding", "Stall", "Branch prediction", "CPI"],
    practice: "Lập biểu đồ pipeline cho chuỗi lệnh có phụ thuộc dữ liệu; so sánh số chu kỳ khi có và không có forwarding."
  },
  {
    id: "06", phase: "BỘ XỬ LÝ", level: "Nâng cao", duration: "4 giờ",
    title: "Hiệu năng & thực thi ngoài thứ tự",
    subtitle: "Vượt qua giới hạn của pipeline cơ bản",
    description: "Từ định luật Amdahl và benchmark đến các thiết kế superscalar, thực thi ngoài thứ tự, đổi tên thanh ghi và suy đoán để khai thác song song ở mức lệnh.",
    objectives: ["Dùng CPI, thời gian CPU và Amdahl để đánh giá cải tiến.", "Phân biệt song song mức lệnh với song song mức luồng.", "Mô tả mục tiêu của register renaming và out-of-order execution."],
    topics: ["Amdahl", "Benchmark", "Superscalar", "OoO", "Speculation"],
    practice: "Dùng Amdahl tính mức tăng tốc tối đa khi tăng tốc một phần chương trình, với nhiều tỉ lệ song song."
  },
  {
    id: "07", phase: "BỘ NHỚ", level: "Trung cấp", duration: "4 giờ",
    title: "Cache & tính cục bộ",
    subtitle: "Đưa dữ liệu gần hơn với bộ xử lý",
    description: "Khám phá phân cấp bộ nhớ và lý do cache hoạt động hiệu quả. Phân tích ánh xạ trực tiếp, liên kết tập hợp, chính sách thay thế và ghi.",
    objectives: ["Phân biệt tính cục bộ thời gian và không gian.", "Tính tag, index, offset cho địa chỉ cache.", "Đánh giá hit rate, miss penalty và chính sách write-through/write-back."],
    topics: ["L1/L2/L3", "Cache mapping", "Cache miss", "Write policy", "Locality"],
    practice: "Tự mô phỏng cache direct-mapped nhỏ với chuỗi địa chỉ cho trước và tính hit/miss."
  },
  {
    id: "08", phase: "BỘ NHỚ", level: "Trung cấp", duration: "4 giờ",
    title: "Bộ nhớ ảo & phân trang",
    subtitle: "Không gian địa chỉ an toàn, linh hoạt",
    description: "Tìm hiểu cách MMU ánh xạ địa chỉ ảo sang địa chỉ vật lý bằng bảng trang và TLB; hiểu page fault, quyền truy cập và vai trò của hệ điều hành.",
    objectives: ["Phân biệt địa chỉ ảo và địa chỉ vật lý.", "Mô tả tra cứu TLB, page table và xử lý page fault.", "Giải thích phân trang giúp cô lập tiến trình và chia sẻ bộ nhớ."],
    topics: ["MMU", "TLB", "Page table", "Page fault", "Protection"],
    practice: "Lần theo một phép dịch địa chỉ ảo qua TLB và bảng trang hai cấp; xác định khi nào xảy ra page fault."
  },
  {
    id: "09", phase: "BỘ NHỚ", level: "Nâng cao", duration: "3 giờ",
    title: "DRAM, lưu trữ & I/O",
    subtitle: "Kết nối CPU với thế giới dữ liệu",
    description: "Đặt SRAM, DRAM, bộ nhớ flash và lưu trữ vào đúng vị trí trong hệ thống; xem xét độ trễ, băng thông, tính bền vững và cách thiết bị I/O giao tiếp.",
    objectives: ["So sánh SRAM, DRAM, NAND flash và lưu trữ bền vững.", "Phân biệt độ trễ, băng thông và thông lượng.", "Mô tả ngắt, DMA và memory-mapped I/O."],
    topics: ["DRAM", "SSD/NVMe", "Băng thông", "DMA", "I/O"],
    practice: "Lập bảng so sánh cache, RAM và SSD theo độ trễ, dung lượng, giá thành và tính bền vững."
  },
  {
    id: "10", phase: "SONG SONG", level: "Nâng cao", duration: "4 giờ",
    title: "Đa lõi & tính nhất quán bộ nhớ",
    subtitle: "Nhiều bộ xử lý, một hệ thống",
    description: "Khảo sát hệ thống đa lõi và các thách thức khi nhiều lõi cùng đọc/ghi dữ liệu. Nắm ý tưởng về coherence, false sharing, atomic operation và mô hình nhất quán.",
    objectives: ["Giải thích cache coherence và giao thức MESI ở mức khái niệm.", "Phân biệt coherence với memory consistency.", "Nhận diện race condition và vai trò của thao tác atomic."],
    topics: ["Multicore", "MESI", "Atomic", "False sharing", "Consistency"],
    practice: "Phân tích hai luồng cập nhật biến dùng chung; xác định race condition và đề xuất đồng bộ phù hợp."
  },
  {
    id: "11", phase: "SONG SONG", level: "Nâng cao", duration: "4 giờ",
    title: "GPU, accelerator & kiến trúc chuyên dụng",
    subtitle: "Tính toán song song ở quy mô lớn",
    description: "So sánh CPU và GPU, mô hình SIMT, băng thông bộ nhớ và các bộ tăng tốc như NPU/TPU. Tìm hiểu vì sao miền ứng dụng quyết định thiết kế phần cứng.",
    objectives: ["Phân biệt mục tiêu tối ưu của CPU và GPU.", "Mô tả SIMT, warp/wavefront và phân kỳ nhánh.", "Đánh giá đánh đổi giữa hiệu năng, tính linh hoạt và năng lượng."],
    topics: ["SIMD/SIMT", "GPU", "NPU/TPU", "Throughput", "Energy"],
    practice: "Chọn một tác vụ song song (đồ họa, học máy hoặc xử lý ảnh) và giải thích vì sao GPU/accelerator phù hợp."
  },
  {
    id: "12", phase: "TỔNG HỢP", level: "Nâng cao", duration: "6 giờ",
    title: "Bảo mật, độ tin cậy & đồ án",
    subtitle: "Thiết kế có trách nhiệm cho hệ thống hiện đại",
    description: "Tổng hợp kiến thức qua thiết kế CPU RISC-V thu nhỏ, đồng thời xem xét bề mặt tấn công vi kiến trúc, side channel, độ tin cậy và các đánh đổi thiết kế.",
    objectives: ["Giải thích vì sao speculative execution có thể tạo side channel.", "Đề xuất cách kiểm thử chức năng và hiệu năng của CPU.", "Kết nối ISA, datapath, pipeline và bộ nhớ trong đồ án cuối khóa."],
    topics: ["Side channel", "Reliability", "Verification", "RISC-V core", "Đồ án"],
    practice: "Đồ án: đặc tả tập lệnh con, thiết kế datapath/pipeline 5 giai đoạn, kiểm thử chương trình mẫu và viết báo cáo đánh đổi."
  }
];

const moduleList = document.querySelector("#module-list");
const detailPanel = document.querySelector("#detail-panel");
const searchInput = document.querySelector("#search-input");
const levelFilter = document.querySelector("#level-filter");
const emptyState = document.querySelector("#empty-state");
const completedKey = "archlab-completed-modules";
const themeKey = "archlab-theme";
let completedModules = readCompletedModules();
let selectedModuleId = null;

function readCompletedModules() {
  try {
    const saved = JSON.parse(localStorage.getItem(completedKey) || "[]");
    return Array.isArray(saved) ? saved.filter((id) => modules.some((module) => module.id === id)) : [];
  } catch (error) {
    console.warn("Không thể đọc tiến độ đã lưu.", error);
    return [];
  }
}

function renderModules() {
  const query = searchInput.value.trim().toLocaleLowerCase("vi");
  const level = levelFilter.value;
  const filtered = modules.filter((module) => {
    const matchesLevel = level === "all" || module.level === level;
    const searchable = [module.title, module.subtitle, module.description, module.phase, module.level, ...module.topics]
      .join(" ").toLocaleLowerCase("vi");
    return matchesLevel && searchable.includes(query);
  });

  moduleList.innerHTML = filtered.map((module) => {
    const completed = completedModules.includes(module.id);
    const selected = selectedModuleId === module.id;
    return `
      <article class="module-card${completed ? " completed" : ""}${selected ? " selected" : ""}"
        data-module-id="${module.id}" tabindex="0" role="button" aria-pressed="${selected}">
        <span class="module-index">${completed ? "✓" : module.id}</span>
        <span class="module-info">
          <span class="module-kicker">${module.phase}<span class="module-level">${module.level}</span></span>
          <span class="module-title">${module.title}</span>
          <span class="module-subtitle">${module.subtitle}</span>
        </span>
        <span class="module-side"><span>${module.duration}</span><span class="module-arrow">${completed ? "✓" : "↗"}</span></span>
      </article>`;
  }).join("");
  emptyState.hidden = filtered.length !== 0;
  moduleList.hidden = filtered.length === 0;
  moduleList.querySelectorAll(".module-card").forEach((card) => {
    card.addEventListener("click", () => selectModule(card.dataset.moduleId));
    card.addEventListener("keydown", (event) => {
      if (event.key === "Enter" || event.key === " ") {
        event.preventDefault();
        selectModule(card.dataset.moduleId);
      }
    });
  });
}

function selectModule(id) {
  selectedModuleId = id;
  const module = modules.find((item) => item.id === id);
  if (!module) return;
  const completed = completedModules.includes(id);
  detailPanel.innerHTML = `
    <div class="detail-top"><span class="detail-number">MÔ-ĐUN ${module.id} · ${module.phase}</span><span class="detail-level">${module.level}</span></div>
    <h3>${module.title}</h3>
    <p class="detail-subtitle">${module.subtitle} · ${module.duration}</p>
    <p class="detail-description">${module.description}</p>
    <div class="detail-label">SAU MÔ-ĐUN NÀY, BẠN CÓ THỂ</div>
    <ul class="objective-list">${module.objectives.map((objective) => `<li>${objective}</li>`).join("")}</ul>
    <div class="detail-label">CHỦ ĐỀ CHÍNH</div>
    <div class="topic-tags">${module.topics.map((topic) => `<span class="topic-tag">${topic}</span>`).join("")}</div>
    <div class="practice-box"><strong>THỰC HÀNH / DỰ ÁN</strong><p>${module.practice}</p></div>
    <button class="complete-button${completed ? " done" : ""}" id="complete-button">${completed ? "✓  Đã hoàn thành · Bỏ đánh dấu" : "Đánh dấu đã hoàn thành"}</button>`;
  document.querySelector("#complete-button").addEventListener("click", toggleCompletion);
  renderModules();
}

function toggleCompletion() {
  if (!selectedModuleId) return;
  if (completedModules.includes(selectedModuleId)) {
    completedModules = completedModules.filter((id) => id !== selectedModuleId);
  } else {
    completedModules = [...completedModules, selectedModuleId];
  }
  try {
    localStorage.setItem(completedKey, JSON.stringify(completedModules));
  } catch (error) {
    console.error("Không thể lưu tiến độ học tập vào trình duyệt.", error);
  }
  updateProgress();
  selectModule(selectedModuleId);
}

function updateProgress() {
  const percent = Math.round((completedModules.length / modules.length) * 100);
  document.querySelector("#sidebar-progress-label").textContent = `${percent}%`;
  document.querySelector("#sidebar-progress-bar").style.width = `${percent}%`;
  document.querySelector("#overview-progress-label").innerHTML = `${percent}<small>%</small>`;
  document.querySelector("#overview-progress-bar").style.width = `${percent}%`;
  document.querySelector("#sidebar-progress-note").textContent = completedModules.length
    ? `Đã hoàn thành ${completedModules.length}/${modules.length} mô-đun`
    : "Bắt đầu hành trình của bạn";
}

searchInput.addEventListener("input", renderModules);
levelFilter.addEventListener("change", renderModules);
document.querySelector("#theme-button").addEventListener("click", () => {
  document.body.classList.toggle("dark");
  try {
    localStorage.setItem(themeKey, document.body.classList.contains("dark") ? "dark" : "light");
  } catch (error) {
    console.warn("Không thể lưu tùy chọn giao diện.", error);
  }
});
document.querySelector("#menu-button").addEventListener("click", (event) => {
  const sidebar = document.querySelector("#sidebar");
  const isOpen = sidebar.classList.toggle("open");
  document.querySelector(".topbar").classList.toggle("menu-open", isOpen);
  event.currentTarget.setAttribute("aria-expanded", String(isOpen));
  event.currentTarget.setAttribute("aria-label", isOpen ? "Đóng menu" : "Mở menu");
});
document.querySelectorAll(".side-nav a").forEach((link) => {
  link.addEventListener("click", () => {
    document.querySelectorAll(".side-nav a").forEach((item) => item.classList.remove("active"));
    link.classList.add("active");
    document.querySelector("#sidebar").classList.remove("open");
    document.querySelector(".topbar").classList.remove("menu-open");
    document.querySelector("#menu-button").setAttribute("aria-expanded", "false");
  });
});
document.addEventListener("keydown", (event) => {
  if ((event.metaKey || event.ctrlKey) && event.key.toLowerCase() === "k") {
    event.preventDefault();
    searchInput.focus();
  }
});

try {
  if (localStorage.getItem(themeKey) === "dark") document.body.classList.add("dark");
} catch (error) {
  console.warn("Không thể đọc tùy chọn giao diện.", error);
}
document.querySelector("#module-count").textContent = String(modules.length).padStart(2, "0");
renderModules();
updateProgress();
