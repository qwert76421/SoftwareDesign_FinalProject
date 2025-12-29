package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GhostWanderingPolicyTest {

    static class FakeAccess implements GhostLevelAccess {
        boolean hasHeap;
        boolean isExit;

        FakeAccess(boolean hasHeap, boolean isExit) {
            this.hasHeap = hasHeap;
            this.isExit = isExit;
        }

        @Override
        public boolean hasHeapAt(int pos) {
            return hasHeap;
        }

        @Override
        public boolean isExitCell(int pos) {
            return isExit;
        }
    }

    @Test
    void D0_negativeCandidate_returnsMinusOne() {
        assertEquals(-1, GhostWanderingPolicy.filterDestination(-1, new FakeAccess(false, false)));
    }

    @Test
    void D1_hasHeap_returnsMinusOne() {
        assertEquals(-1, GhostWanderingPolicy.filterDestination(10, new FakeAccess(true, false)));
    }

    @Test
    void D2_isExit_returnsMinusOne() {
        assertEquals(-1, GhostWanderingPolicy.filterDestination(10, new FakeAccess(false, true)));
    }

    @Test
    void D3_normalCell_returnsSamePos() {
        assertEquals(10, GhostWanderingPolicy.filterDestination(10, new FakeAccess(false, false)));
    }
}
