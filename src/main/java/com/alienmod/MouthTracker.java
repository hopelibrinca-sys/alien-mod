package com.alienmod;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Remembers who is talking (fed by the Simple Voice Chat plugin). */
public final class MouthTracker {
    private record Talk(float level, long until) {}
    private static final Map<UUID, Talk> TALKING = new ConcurrentHashMap<>();

    private MouthTracker() {}

    /** Called from the voice chat thread with 16-bit PCM audio for one 20ms frame. */
    public static void onAudio(UUID id, short[] pcm) {
        if (pcm == null || pcm.length == 0) return;
        double sum = 0;
        for (short s : pcm) sum += (double) s * s;
        double rms = Math.sqrt(sum / pcm.length) / 32768.0;
        float level = (float) Math.min(1.0, rms * 8.0);
        TALKING.put(id, new Talk(level, System.currentTimeMillis() + 120));
    }

    public static int mouthState(UUID id) {
        Talk t = TALKING.get(id);
        if (t == null || System.currentTimeMillis() > t.until()) return 0;
        if (t.level() > 0.35f) return 2;
        if (t.level() > 0.08f) return 1;
        return 0;
    }
}
