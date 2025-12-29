package com.shatteredpixel.shatteredpixeldungeon.levels.painters;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.watabou.utils.Point;
import com.watabou.utils.Rect;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaintTargetTest {

    /**
     * 測試用最小 Level：
     * 我們只需要 Painter 會用到的兩件事：
     * 1) level.map 這個 int[]
     * 2) level.width()
     *
     * 若 Level 是 abstract，IDE/編譯器會提示你還缺哪些 abstract method，
     * 你就依提示補 override，回傳 0/null 即可（本測試不會走到那些方法）。
     */
    private static class TestLevel extends Level {

        private final int w;
        private final int h;

        TestLevel(int w, int h) {
            this.w = w;
            this.h = h;
            this.map = new int[w * h];
        }

        @Override
        public int width() {
            return w;
        }

        // 如果 Level 有 height() / length() 且是 abstract，就補上：
        @Override
        public int height() {
            return h;
        }

        @Override
        public int length() {
            return w * h;
        }

        @Override
        protected boolean build() {
            // 測試 Painter delegation 用，不需要真的生成地圖
            return true;
        }

        @Override
        protected void createMobs() {
            // 測試不需要怪物生成
        }

        @Override
        protected void createItems() {
            // 測試不需要物品生成
        }

    }

    @Test
    void set_withPaintTarget_shouldWriteSameCellValue() {
        TestLevel level = new TestLevel(5, 5);
        PaintTarget t = new PaintTarget(level, 7);

        // Act
        Painter.set(t, 1, 2);

        // Assert: cell = x + y*width
        assertEquals(7, level.map[1 + 2 * level.width()]);
    }

    @Test
    void fill_rect_shouldFillAreaWithTargetValue() {
        TestLevel level = new TestLevel(6, 6);
        PaintTarget t = new PaintTarget(level, 3);

        Rect rect = new Rect(1, 1, 4, 4); // left=1 top=1 right=4 bottom=4
        // 依 SPD Rect 的慣例：width = right-left, height = bottom-top

        Painter.fill(t, rect);

        // 期望填滿 (x=1..3, y=1..3) 共 3x3（取決於 Rect 的 right/bottom 是否含端點）
        // 這裡用「Painter.fill 的實作」判斷：fill(level, left, top, rect.width(), rect.height(),
        // value)
        // 所以 width=rect.width()、height=rect.height()
        for (int y = rect.top; y < rect.top + rect.height(); y++) {
            for (int x = rect.left; x < rect.left + rect.width(); x++) {
                assertEquals(3, level.map[x + y * level.width()]);
            }
        }
    }

    @Test
    void fill_xywh_shouldFillAreaWithTargetValue() {
        TestLevel level = new TestLevel(6, 6);
        PaintTarget t = new PaintTarget(level, 9);

        Painter.fill(t, 2, 2, 2, 3); // (2,2) 寬2 高3

        for (int y = 2; y < 2 + 3; y++) {
            for (int x = 2; x < 2 + 2; x++) {
                assertEquals(9, level.map[x + y * level.width()]);
            }
        }
    }

    @Test
    void drawLine_shouldSetAlongLineWithTargetValue() {
        TestLevel level = new TestLevel(6, 6);
        PaintTarget t = new PaintTarget(level, 5);

        // 水平線：from(1,2) -> to(4,2)
        Painter.drawLine(t, new Point(1, 2), new Point(4, 2));

        for (int x = 1; x <= 4; x++) {
            assertEquals(5, level.map[x + 2 * level.width()]);
        }
    }

}
