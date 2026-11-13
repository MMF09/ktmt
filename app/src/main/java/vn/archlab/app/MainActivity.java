package vn.archlab.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends Activity {
    private static final String PREFERENCES = "archlab_native";
    private static final String COMPLETED_KEY = "completed_modules";
    private static final String THEME_KEY = "dark_theme";
    private static final int GREEN = Color.rgb(43, 129, 91);
    private static final int DEEP_GREEN = Color.rgb(25, 61, 48);
    private final Set<String> completedLessons = new HashSet<>();

    private SharedPreferences preferences;
    private LinearLayout root;
    private LinearLayout pageContent;
    private ScrollView pageScroll;
    private TextView moduleHeader;
    private String currentScreen = "home";
    private String activeLevel = "Tất cả";
    private String searchText = "";
    private CourseData.Lesson selectedLesson;
    private boolean darkMode;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        darkMode = preferences.getBoolean(THEME_KEY, false);
        readProgress();
        showHome();
    }

    private void readProgress() {
        String value = preferences.getString(COMPLETED_KEY, "");
        if (value == null || value.isEmpty()) return;
        for (String id : value.split(",")) {
            CourseData.Lesson lesson = CourseData.findById(id);
            if (lesson != null) completedLessons.add(id);
        }
    }

    private void saveProgress() {
        StringBuilder value = new StringBuilder();
        for (CourseData.Lesson lesson : CourseData.LESSONS) {
            if (!completedLessons.contains(lesson.id)) continue;
            if (value.length() > 0) value.append(',');
            value.append(lesson.id);
        }
        preferences.edit().putString(COMPLETED_KEY, value.toString()).apply();
    }

    private void buildScreen(String title, boolean showNavigation) {
        int pageColor = palette(0xFFF5F7F3, 0xFF111A16);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(pageColor);
        setContentView(root);
        updateSystemBars(pageColor);

        LinearLayout appBar = new LinearLayout(this);
        appBar.setGravity(Gravity.CENTER_VERTICAL);
        appBar.setPadding(dp(20), dp(8), dp(18), dp(8));
        appBar.setBackgroundColor(palette(0xFFFCFDFB, 0xFF18221C));
        appBar.setElevation(dp(2));

        ArchMark mark = new ArchMark(this);
        appBar.addView(mark, new LinearLayout.LayoutParams(dp(42), dp(42)));
        LinearLayout name = new LinearLayout(this);
        name.setOrientation(LinearLayout.VERTICAL);
        name.setPadding(dp(8), 0, 0, 0);
        addText(name, "ARCHLAB", 14, palette(0xFF20352A, 0xFFE5EEE7), true);
        addText(name, title, 9, palette(0xFF87938A, 0xFF9CAFA1), false, 0, 2, 0, 0);
        appBar.addView(name, new LinearLayout.LayoutParams(0, -2, 1));

        if ("lesson".equals(currentScreen)) {
            addToolbarButton(appBar, "‹", "Quay về giáo trình", () -> showHome());
            addToolbarButton(appBar, darkMode ? "☀" : "◐", "Đổi giao diện", this::toggleTheme);
        } else {
            addToolbarButton(appBar, darkMode ? "☀" : "◐", "Đổi giao diện", this::toggleTheme);
        }
        root.addView(appBar, new LinearLayout.LayoutParams(-1, dp(62)));

        pageScroll = new ScrollView(this);
        pageScroll.setFillViewport(false);
        pageScroll.setVerticalScrollBarEnabled(false);
        pageScroll.setClipToPadding(false);
        pageContent = new LinearLayout(this);
        pageContent.setOrientation(LinearLayout.VERTICAL);
        pageContent.setPadding(dp(18), dp(18), dp(18), dp(24));
        pageScroll.addView(pageContent);
        root.addView(pageScroll, new LinearLayout.LayoutParams(-1, 0, 1));
        if (showNavigation) addBottomNavigation();
    }

    private void addToolbarButton(LinearLayout bar, String icon, String label, Runnable action) {
        TextView button = text(icon, 22, palette(0xFF466F53, 0xFFB7D9BD), true);
        button.setGravity(Gravity.CENTER);
        button.setContentDescription(label);
        button.setBackground(rippleBackground());
        button.setOnClickListener(view -> action.run());
        bar.addView(button, new LinearLayout.LayoutParams(dp(42), dp(42)));
    }

    private void updateSystemBars(int pageColor) {
        Window window = getWindow();
        window.setStatusBarColor(pageColor);
        window.setNavigationBarColor(pageColor);
        int flags = darkMode ? 0 : View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !darkMode) {
            flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        }
        window.getDecorView().setSystemUiVisibility(flags);
    }

    private void toggleTheme() {
        darkMode = !darkMode;
        preferences.edit().putBoolean(THEME_KEY, darkMode).apply();
        renderCurrentScreen();
    }

    private int palette(int light, int dark) {
        return darkMode ? dark : light;
    }

    private void renderCurrentScreen() {
        if ("lesson".equals(currentScreen) && selectedLesson != null) {
            showLesson(selectedLesson);
        } else if ("roadmap".equals(currentScreen)) {
            showRoadmap();
        } else if ("resources".equals(currentScreen)) {
            showResources();
        } else {
            showHome();
        }
    }

    private void addBottomNavigation() {
        LinearLayout navigation = new LinearLayout(this);
        navigation.setGravity(Gravity.CENTER);
        navigation.setPadding(dp(8), dp(7), dp(8), dp(7));
        navigation.setBackgroundColor(palette(0xFFFCFDFB, 0xFF18221C));
        String[][] items = {
                {"⌂", "Học tập", "home"},
                {"◷", "Lộ trình", "roadmap"},
                {"↗", "Tài nguyên", "resources"}
        };
        for (String[] item : items) {
            boolean active = currentScreen.equals(item[2]);
            LinearLayout tab = new LinearLayout(this);
            tab.setGravity(Gravity.CENTER);
            tab.setOrientation(LinearLayout.VERTICAL);
            TextView icon = text(item[0], 19, active ? GREEN : palette(0xFF849087, 0xFF9AA99D), true);
            TextView label = text(item[1], 9, active ? GREEN : palette(0xFF849087, 0xFF9AA99D), active);
            tab.addView(icon);
            tab.addView(label);
            tab.setBackground(rippleBackground());
            tab.setOnClickListener(view -> openTab(item[2]));
            navigation.addView(tab, new LinearLayout.LayoutParams(0, dp(48), 1));
        }
        root.addView(navigation, new LinearLayout.LayoutParams(-1, dp(62)));
    }

    private void openTab(String tab) {
        if ("home".equals(tab)) showHome();
        else if ("roadmap".equals(tab)) showRoadmap();
        else showResources();
    }

    private void showHome() {
        currentScreen = "home";
        buildScreen("Không gian học tập", true);
        addHomeHero();
        addProgressCard();
        moduleHeader = text("Giáo trình", 23, palette(0xFF1E3025, 0xFFE4ECE5), true);
        pageContent.addView(moduleHeader, marginParams(-1, -2, 0, 27, 0, 4));
        addText(pageContent, "Chọn một chương để bắt đầu hoặc tiếp tục học.", 11,
                palette(0xFF828D85, 0xFFA0AEA3), false, 0, 0, 0, 14);
        addCourseSearchAndList();
    }

    private void addHomeHero() {
        LinearLayout hero = panel(0xFF193D30, 20, 20, 20, 19, false);
        addText(hero, "LỘ TRÌNH HỌC TẬP  ·  12 MÔ-ĐUN", 9, 0xFFB4D3BA, true);
        addText(hero, "Hiểu cỗ máy.\nKiến tạo tương lai.", 29, Color.WHITE, true, 0, 13, 0, 9);
        addText(hero, "Từ những cổng logic đầu tiên đến kiến trúc máy tính hiện đại.", 12,
                0xFFD0DED2, false, 0, 0, 0, 15);
        TextView start = text("BẮT ĐẦU HỌC   →", 10, 0xFF214833, true);
        start.setGravity(Gravity.CENTER);
        start.setPadding(dp(14), 0, dp(14), 0);
        start.setBackground(roundRect(0xFFD8EEDB, 9));
        start.setOnClickListener(view -> pageScroll.smoothScrollTo(0, moduleHeader.getTop()));
        hero.addView(start, new LinearLayout.LayoutParams(-2, dp(38)));
        pageContent.addView(hero, marginParams(-1, -2, 0, 0, 0, 13));
    }

    private void addProgressCard() {
        int percent = CourseData.LESSONS.isEmpty()
                ? 0 : Math.round(completedLessons.size() * 100f / CourseData.LESSONS.size());
        LinearLayout card = panel(palette(0xFFFFFFFF, 0xFF1C2821), 15, 14, 15, 14, true);
        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);
        TextView percentText = text(percent + "%", 27, GREEN, true);
        top.addView(percentText, new LinearLayout.LayoutParams(dp(76), -2));
        LinearLayout detail = new LinearLayout(this);
        detail.setOrientation(LinearLayout.VERTICAL);
        addText(detail, "TIẾN ĐỘ CỦA BẠN", 9, palette(0xFF748177, 0xFF9CA99E), true);
        addText(detail, completedLessons.size() + " / " + CourseData.LESSONS.size() + " bài học hoàn thành",
                10, palette(0xFF87928A, 0xFFABB7AD), false, 0, 4, 0, 0);
        top.addView(detail, new LinearLayout.LayoutParams(0, -2, 1));
        card.addView(top);
        android.widget.FrameLayout progressTrack = new android.widget.FrameLayout(this);
        progressTrack.setBackground(roundRect(palette(0xFFE9EEE9, 0xFF344138), 8));
        View progress = new View(this);
        progress.setBackground(roundRect(GREEN, 8));
        progressTrack.addView(progress, new android.widget.FrameLayout.LayoutParams(0, -1, Gravity.START));
        card.addView(progressTrack, marginParams(-1, dp(5), 0, 12, 0, 0));
        progressTrack.post(() -> {
            int width = Math.round(progressTrack.getWidth() * percent / 100f);
            progress.getLayoutParams().width = width;
            progress.requestLayout();
        });
        pageContent.addView(card, marginParams(-1, -2, 0, 0, 0, 0));
    }

    private void addCourseSearchAndList() {
        EditText search = new EditText(this);
        search.setSingleLine(true);
        search.setTextSize(13);
        search.setHint("⌕    Tìm bài học hoặc khái niệm...");
        search.setHintTextColor(palette(0xFF929C95, 0xFF88978C));
        search.setTextColor(palette(0xFF24352A, 0xFFE4ECE5));
        search.setPadding(dp(14), 0, dp(14), 0);
        search.setBackground(roundRect(palette(0xFFFFFFFF, 0xFF1C2821),
                palette(0xFFE3E9E3, 0xFF334138), 10));
        search.setContentDescription("Tìm kiếm bài học");
        pageContent.addView(search, marginParams(-1, dp(48), 0, 0, 0, 10));

        HorizontalScrollView filters = new HorizontalScrollView(this);
        filters.setHorizontalScrollBarEnabled(false);
        LinearLayout chips = new LinearLayout(this);
        chips.setGravity(Gravity.CENTER_VERTICAL);
        String[] levels = {"Tất cả", "Nền tảng", "Trung cấp", "Nâng cao"};
        for (String level : levels) {
            boolean active = level.equals(activeLevel);
            TextView chip = text(level, 10, active ? Color.WHITE : palette(0xFF6D7A71, 0xFFADB9AF), active);
            chip.setGravity(Gravity.CENTER);
            chip.setPadding(dp(13), 0, dp(13), 0);
            chip.setBackground(roundRect(active ? GREEN : palette(0xFFFFFFFF, 0xFF1C2821), 20));
            chip.setOnClickListener(view -> {
                activeLevel = level;
                hideKeyboard(search);
                refreshModules();
            });
            chips.addView(chip, marginParams(-2, dp(34), 0, 0, 8, 0));
        }
        filters.addView(chips);
        pageContent.addView(filters, marginParams(-1, dp(42), 0, 0, 0, 8));

        LinearLayout moduleList = new LinearLayout(this);
        moduleList.setOrientation(LinearLayout.VERTICAL);
        pageContent.addView(moduleList);
        renderModules(moduleList);
        search.setText(searchText);
        search.setSelection(search.length());
        search.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence value, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence value, int start, int before, int count) {
                searchText = value.toString();
                renderModules(moduleList);
            }
            @Override public void afterTextChanged(Editable value) { }
        });
    }

    private void refreshModules() {
        showHome();
    }

    private void renderModules(LinearLayout list) {
        list.removeAllViews();
        int count = 0;
        for (CourseData.Lesson lesson : CourseData.LESSONS) {
            if (!"Tất cả".equals(activeLevel) && !activeLevel.equals(lesson.level)) continue;
            if (!lesson.matches(searchText)) continue;
            list.addView(moduleCard(lesson), marginParams(-1, -2, 0, 0, 0, 9));
            count++;
        }
        if (count == 0) {
            LinearLayout empty = panel(palette(0xFFFFFFFF, 0xFF1C2821), 18, 19, 18, 19, true);
            addText(empty, "Chưa tìm thấy bài học", 14, palette(0xFF34463A, 0xFFDCE7DE), true);
            addText(empty, "Thử từ khóa khác hoặc chọn cấp độ khác.", 11,
                    palette(0xFF87928A, 0xFFA6B3A8), false, 0, 5, 0, 0);
            list.addView(empty);
        }
    }

    private View moduleCard(CourseData.Lesson lesson) {
        boolean done = completedLessons.contains(lesson.id);
        LinearLayout card = panel(palette(0xFFFFFFFF, 0xFF1C2821), 13, 13, 13, 13, true);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);

        TextView index = text(done ? "✓" : lesson.id, 11,
                done ? Color.WHITE : palette(0xFF4F785B, 0xFFB5D9BA), true);
        index.setGravity(Gravity.CENTER);
        index.setBackground(roundRect(done ? GREEN : palette(0xFFEDF4ED, 0xFF29392E), 10));
        card.addView(index, new LinearLayout.LayoutParams(dp(42), dp(42)));

        LinearLayout details = new LinearLayout(this);
        details.setOrientation(LinearLayout.VERTICAL);
        details.setPadding(dp(11), 0, dp(5), 0);
        addText(details, lesson.phase + "   ·   " + lesson.level.toUpperCase(new Locale("vi", "VN")),
                8, palette(0xFF7E9182, 0xFF9DB49F), true);
        addText(details, lesson.title, 12, palette(0xFF29392F, 0xFFE2EBE4), true, 0, 4, 0, 3);
        addText(details, lesson.subtitle, 9, palette(0xFF89938C, 0xFFA4B0A6), false);
        card.addView(details, new LinearLayout.LayoutParams(0, -2, 1));
        TextView arrow = text("›", 23, palette(0xFF87968B, 0xFFADBCB0), false);
        card.addView(arrow, new LinearLayout.LayoutParams(dp(25), -2));
        card.setContentDescription("Mô-đun " + lesson.id + ", " + lesson.title + ", " + lesson.articleWordCount() + " từ");
        card.setBackground(roundRect(palette(0xFFFFFFFF, 0xFF1C2821),
                palette(0xFFE7ECE7, 0xFF2C3931), 12));
        card.setFocusable(true);
        card.setClickable(true);
        card.setOnClickListener(view -> showLesson(lesson));
        return card;
    }

    private void showLesson(CourseData.Lesson lesson) {
        currentScreen = "lesson";
        selectedLesson = lesson;
        buildScreen("Bài " + lesson.id + "  /  " + lesson.phase, false);
        addLessonHeader(lesson);
        addText(pageContent, "BÀI ĐỌC", 9, GREEN, true, 0, 7, 0, 8);

        for (CourseData.Section section : lesson.sections) {
            LinearLayout articleCard = panel(palette(0xFFFFFFFF, 0xFF1C2821), 16, 15, 16, 15, true);
            addText(articleCard, section.title, 16, palette(0xFF294B34, 0xFFB7D8BC), true, 0, 0, 0, 8);
            addText(articleCard, section.body, 12, palette(0xFF4F5F54, 0xFFC0CBC2), false);
            pageContent.addView(articleCard, marginParams(-1, -2, 0, 0, 0, 10));
        }

        addExampleCard(lesson);
        addExerciseCard(lesson);
        addSelfCheckCard(lesson);
        addText(pageContent, "Bạn có thể xem lại bài bất kỳ lúc nào. Tiến độ chỉ đổi khi nhấn nút bên dưới.",
                10, palette(0xFF829087, 0xFF9DADA1), false, 2, 13, 2, 10);
        addLessonFooter(lesson);
    }

    private void addLessonHeader(CourseData.Lesson lesson) {
        LinearLayout hero = panel(palette(0xFF193D30, 0xFF1D3629), 18, 17, 18, 16, false);
        addText(hero, "MÔ-ĐUN " + lesson.id + "  ·  " + lesson.phase, 9, 0xFFB6D8BC, true);
        addText(hero, lesson.title, 24, Color.WHITE, true, 0, 9, 0, 5);
        addText(hero, lesson.subtitle, 11, 0xFFD0DED2, false);
        LinearLayout tags = new LinearLayout(this);
        tags.setGravity(Gravity.CENTER_VERTICAL);
        addTag(tags, lesson.level);
        addTag(tags, lesson.duration);
        addTag(tags, lesson.articleWordCount() + " từ");
        hero.addView(tags, marginParams(-1, -2, 0, 13, 0, 0));
        pageContent.addView(hero, marginParams(-1, -2, 0, 0, 0, 17));

        LinearLayout summary = panel(palette(0xFFEAF2EA, 0xFF25372A), 14, 13, 14, 13, false);
        addText(summary, "TÓM TẮT", 8, palette(0xFF50785A, 0xFFAED1B2), true, 0, 0, 0, 5);
        addText(summary, lesson.summary, 11, palette(0xFF526156, 0xFFBECCC0), false);
        pageContent.addView(summary, marginParams(-1, -2, 0, 0, 0, 15));

        addText(pageContent, "MỤC TIÊU HỌC TẬP", 9, GREEN, true, 0, 1, 0, 8);
        for (String objective : lesson.objectives) addBullet(pageContent, objective);
        addText(pageContent, "CHỦ ĐỀ", 9, GREEN, true, 0, 10, 0, 7);
        LinearLayout topics = new LinearLayout(this);
        topics.setOrientation(LinearLayout.HORIZONTAL);
        topics.setPadding(0, 0, 0, dp(1));
        for (String topic : lesson.topics) addTag(topics, topic);
        HorizontalScrollView topicScroll = new HorizontalScrollView(this);
        topicScroll.setHorizontalScrollBarEnabled(false);
        topicScroll.addView(topics);
        pageContent.addView(topicScroll, marginParams(-1, dp(36), 0, 0, 0, 14));
    }

    private void addExampleCard(CourseData.Lesson lesson) {
        LinearLayout card = panel(palette(0xFFF1F6F0, 0xFF233428), 15, 13, 15, 13, false);
        addText(card, "✳  VÍ DỤ MINH HỌA", 9, palette(0xFF4F7958, 0xFFB3D7B7), true, 0, 0, 0, 6);
        addText(card, lesson.example, 11, palette(0xFF516056, 0xFFBECABF), false);
        pageContent.addView(card, marginParams(-1, -2, 0, 7, 0, 10));
    }

    private void addExerciseCard(CourseData.Lesson lesson) {
        LinearLayout card = panel(palette(0xFFEDF3FA, 0xFF202F3A), 15, 13, 15, 13, false);
        addText(card, "✎  THỰC HÀNH", 9, palette(0xFF53799A, 0xFFA9C9E2), true, 0, 0, 0, 6);
        addText(card, lesson.exercise, 11, palette(0xFF52606A, 0xFFBAC7CF), false);
        pageContent.addView(card, marginParams(-1, -2, 0, 0, 0, 10));
    }

    private void addSelfCheckCard(CourseData.Lesson lesson) {
        LinearLayout card = panel(palette(0xFFFFFFFF, 0xFF1C2821), 15, 13, 15, 13, true);
        addText(card, "TỰ KIỂM TRA", 9, GREEN, true, 0, 0, 0, 6);
        addText(card, lesson.question, 12, palette(0xFF35463A, 0xFFDCE7DE), true);
        TextView reveal = text("XEM GỢI Ý TRẢ LỜI   ↓", 9, GREEN, true);
        reveal.setPadding(0, dp(12), 0, dp(4));
        card.addView(reveal);
        TextView answer = text(lesson.answer, 11, palette(0xFF5C695F, 0xFFB9C7BB), false);
        answer.setVisibility(View.GONE);
        card.addView(answer);
        reveal.setOnClickListener(view -> {
            boolean visible = answer.getVisibility() != View.VISIBLE;
            answer.setVisibility(visible ? View.VISIBLE : View.GONE);
            reveal.setText(visible ? "ẨN GỢI Ý   ↑" : "XEM GỢI Ý TRẢ LỜI   ↓");
        });
        pageContent.addView(card, marginParams(-1, -2, 0, 0, 0, 8));
    }

    private void addLessonFooter(CourseData.Lesson lesson) {
        LinearLayout footer = new LinearLayout(this);
        footer.setGravity(Gravity.CENTER_VERTICAL);
        footer.setPadding(dp(15), dp(9), dp(15), dp(9));
        footer.setBackgroundColor(palette(0xFFFCFDFB, 0xFF18221C));
        TextView complete = text(completedLessons.contains(lesson.id)
                ? "✓  ĐÃ HOÀN THÀNH" : "ĐÁNH DẤU HOÀN THÀNH", 10,
                completedLessons.contains(lesson.id) ? GREEN : Color.WHITE, true);
        complete.setGravity(Gravity.CENTER);
        complete.setPadding(dp(13), 0, dp(13), 0);
        complete.setBackground(roundRect(completedLessons.contains(lesson.id)
                ? palette(0xFFE7F2E8, 0xFF26392B) : GREEN, 9));
        complete.setOnClickListener(view -> {
            if (!completedLessons.add(lesson.id)) completedLessons.remove(lesson.id);
            saveProgress();
            showLesson(lesson);
        });
        footer.addView(complete, new LinearLayout.LayoutParams(0, dp(43), 1));
        TextView next = text("BÀI TIẾP   ›", 9, GREEN, true);
        next.setGravity(Gravity.CENTER);
        next.setPadding(dp(13), 0, dp(5), 0);
        next.setOnClickListener(view -> {
            int index = CourseData.LESSONS.indexOf(lesson);
            if (index + 1 < CourseData.LESSONS.size()) showLesson(CourseData.LESSONS.get(index + 1));
            else showHome();
        });
        footer.addView(next, new LinearLayout.LayoutParams(-2, dp(43)));
        root.addView(footer, new LinearLayout.LayoutParams(-1, dp(62)));
    }

    private void addTag(LinearLayout parent, String label) {
        TextView tag = text(label, 9, palette(0xFF58745D, 0xFFB9D2BB), true);
        tag.setGravity(Gravity.CENTER);
        tag.setPadding(dp(9), 0, dp(9), 0);
        tag.setBackground(roundRect(palette(0xFFE7F1E7, 0xFF2B3C2E), 18));
        parent.addView(tag, marginParams(-2, dp(27), 0, 0, 6, 0));
    }

    private void addBullet(LinearLayout parent, String body) {
        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.TOP);
        TextView dot = text("•", 15, GREEN, true);
        dot.setPadding(0, 0, dp(8), 0);
        row.addView(dot);
        TextView text = text(body, 11, palette(0xFF59665C, 0xFFBBC7BD), false);
        row.addView(text, new LinearLayout.LayoutParams(0, -2, 1));
        parent.addView(row, marginParams(-1, -2, 0, 0, 0, 6));
    }

    private void showRoadmap() {
        currentScreen = "roadmap";
        buildScreen("Lộ trình học tập", true);
        addText(pageContent, "Học từng bước.", 26, palette(0xFF20352A, 0xFFE4ECE5), true);
        addText(pageContent, "Mười hai tuần · bốn chặng kiến thức.", 11,
                palette(0xFF829087, 0xFFA0AEA3), false, 0, 5, 0, 18);
        String[][] stages = {
                {"01", "TUẦN 01—03", "Nền móng", "Logic số, biểu diễn dữ liệu và ISA RISC-V.", "01"},
                {"02", "TUẦN 04—06", "Bộ xử lý", "Datapath, pipeline và tối ưu hiệu năng.", "04"},
                {"03", "TUẦN 07—09", "Phân cấp bộ nhớ", "Cache, bộ nhớ ảo, lưu trữ và I/O.", "07"},
                {"04", "TUẦN 10—12", "Hệ thống hiện đại", "Đa lõi, accelerator và đồ án CPU.", "10"}
        };
        for (String[] stage : stages) {
            LinearLayout card = panel(palette(0xFFFFFFFF, 0xFF1C2821), 15, 15, 15, 15, true);
            LinearLayout heading = new LinearLayout(this);
            heading.setGravity(Gravity.CENTER_VERTICAL);
            TextView number = text(stage[0], 11, GREEN, true);
            number.setGravity(Gravity.CENTER);
            number.setBackground(roundRect(palette(0xFFE8F1E9, 0xFF29392E), 20));
            heading.addView(number, new LinearLayout.LayoutParams(dp(34), dp(34)));
            addText(heading, stage[1], 9, palette(0xFF819087, 0xFFA2B1A5), true, 10, 0, 0, 0);
            card.addView(heading);
            addText(card, stage[2], 17, palette(0xFF29392F, 0xFFE2EBE4), true, 0, 10, 0, 4);
            addText(card, stage[3], 11, palette(0xFF758178, 0xFFAFBBB1), false);
            TextView open = text("XEM MÔ-ĐUN   →", 9, GREEN, true);
            open.setPadding(0, dp(11), 0, 0);
            card.addView(open);
            card.setOnClickListener(view -> {
                CourseData.Lesson lesson = CourseData.findById(stage[4]);
                if (lesson != null) showLesson(lesson);
            });
            pageContent.addView(card, marginParams(-1, -2, 0, 0, 0, 10));
        }
        int progress = Math.round(completedLessons.size() * 100f / CourseData.LESSONS.size());
        addText(pageContent, "Tiến độ hành trình: " + progress + "%", 12, GREEN, true, 0, 8, 0, 8);
    }

    private void showResources() {
        currentScreen = "resources";
        buildScreen("Tài nguyên học tập", true);
        addText(pageContent, "Mở rộng kiến thức.", 25, palette(0xFF20352A, 0xFFE4ECE5), true);
        addText(pageContent, "Tài liệu và công cụ được chọn để học sâu, tra chuẩn, thực hành.",
                11, palette(0xFF829087, 0xFFA0AEA3), false, 0, 5, 0, 17);
        String[][] resources = {
                {"ĐẶC TẢ CHUẨN", "RISC-V Specifications", "Đặc tả kiến trúc tập lệnh mở từ RISC-V International.", "https://riscv.org/technical/specifications/"},
                {"KHÓA HỌC", "Berkeley CS 61C", "Tổ chức máy tính, hợp ngữ RISC-V, CPU và tính toán song song.", "https://cs61c.org/"},
                {"HỌC QUA DỰ ÁN", "Nand2Tetris", "Xây dựng máy tính từ cổng logic lên các tầng phần mềm.", "https://www.nand2tetris.org/"},
                {"MÔ PHỎNG CPU", "Ripes", "Quan sát datapath và pipeline RISC-V một cách trực quan.", "https://github.com/mortbopet/Ripes"},
                {"MÔ PHỎNG HỢP NGỮ", "Venus", "Viết, chạy và kiểm tra chương trình RISC-V.", "https://venus.cs61c.org/"}
        };
        for (String[] resource : resources) {
            LinearLayout card = panel(palette(0xFFFFFFFF, 0xFF1C2821), 15, 14, 15, 14, true);
            addText(card, resource[0], 8, GREEN, true);
            addText(card, resource[1], 16, palette(0xFF29392F, 0xFFE2EBE4), true, 0, 9, 0, 5);
            addText(card, resource[2], 11, palette(0xFF758178, 0xFFAFBBB1), false, 0, 0, 0, 10);
            addText(card, "MỞ TÀI NGUYÊN   ↗", 9, GREEN, true);
            card.setOnClickListener(view -> openResource(resource[3]));
            pageContent.addView(card, marginParams(-1, -2, 0, 0, 0, 9));
        }
    }

    private void openResource(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (android.content.ActivityNotFoundException exception) {
            android.util.Log.e("ArchLab", "Không thể mở tài nguyên " + url, exception);
            Toast.makeText(this, "Không tìm thấy trình duyệt để mở tài nguyên.", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (input != null) input.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void addText(LinearLayout parent, String value, int size, int color, boolean bold) {
        addText(parent, value, size, color, bold, 0, 0, 0, 0);
    }

    private void addText(LinearLayout parent, String value, int size, int color, boolean bold,
                         int left, int top, int right, int bottom) {
        parent.addView(text(value, size, color, bold), marginParams(-1, -2, left, top, right, bottom));
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(size);
        view.setTextColor(color);
        view.setTypeface(Typeface.create("sans-serif", bold ? Typeface.BOLD : Typeface.NORMAL));
        view.setLineSpacing(dp(2), 1f);
        view.setIncludeFontPadding(true);
        return view;
    }

    private LinearLayout panel(int background, int left, int top, int right, int bottom, boolean outlined) {
        LinearLayout view = new LinearLayout(this);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setPadding(dp(left), dp(top), dp(right), dp(bottom));
        view.setBackground(roundRect(background,
                outlined ? palette(0xFFE6EBE6, 0xFF2D3A32) : Color.TRANSPARENT, 13));
        return view;
    }

    private GradientDrawable roundRect(int background, int radius) {
        return roundRect(background, Color.TRANSPARENT, radius);
    }

    private GradientDrawable roundRect(int background, int stroke, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(background);
        shape.setCornerRadius(dp(radius));
        if (stroke != Color.TRANSPARENT) shape.setStroke(dp(1), stroke);
        return shape;
    }

    private android.graphics.drawable.Drawable rippleBackground() {
        return new RippleDrawable(ColorStateList.valueOf(palette(0x1A2B815B, 0x335C9469)),
                null, roundRect(Color.WHITE, 22));
    }

    private LinearLayout.LayoutParams marginParams(int width, int height,
                                                   int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.setMargins(dp(left), dp(top), dp(right), dp(bottom));
        return params;
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }

    @Override
    public void onBackPressed() {
        if ("lesson".equals(currentScreen) || "roadmap".equals(currentScreen)
                || "resources".equals(currentScreen)) {
            showHome();
            return;
        }
        super.onBackPressed();
    }

    private static final class ArchMark extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        ArchMark(Context context) {
            super(context);
            setContentDescription("ArchLab");
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float scale = getWidth() / 48f;
            canvas.save();
            canvas.scale(scale, scale);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.rgb(25, 61, 48));
            canvas.drawRoundRect(2, 2, 46, 46, 13, 13, paint);
            paint.setColor(Color.rgb(55, 105, 69));
            canvas.drawRoundRect(10, 10, 38, 38, 5, 5, paint);
            paint.setColor(Color.rgb(183, 221, 182));
            canvas.drawRect(16, 16, 21, 21, paint);
            canvas.drawRect(27, 16, 32, 21, paint);
            canvas.drawRect(16, 27, 21, 32, paint);
            canvas.drawRect(27, 27, 32, 32, paint);
            paint.setStrokeWidth(1.7f);
            paint.setColor(Color.rgb(142, 201, 154));
            canvas.drawLine(13, 5, 13, 10, paint);
            canvas.drawLine(35, 5, 35, 10, paint);
            canvas.drawLine(13, 38, 13, 43, paint);
            canvas.drawLine(35, 38, 35, 43, paint);
            canvas.drawLine(5, 13, 10, 13, paint);
            canvas.drawLine(5, 35, 10, 35, paint);
            canvas.drawLine(38, 13, 43, 13, paint);
            canvas.drawLine(38, 35, 43, 35, paint);
            canvas.drawLine(21, 24, 27, 24, paint);
            canvas.drawLine(24, 21, 24, 27, paint);
            canvas.restore();
        }
    }
}
