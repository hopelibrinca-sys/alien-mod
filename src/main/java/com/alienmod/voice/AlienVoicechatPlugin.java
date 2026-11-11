package com.alienmod.voice;

import com.alienmod.MouthTracker;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.ClientReceiveSoundEvent;
import de.maxhenkel.voicechat.api.events.ClientSoundEvent;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import net.minecraft.client.MinecraftClient;

/** Loaded by Simple Voice Chat only (entrypoint "voicechat"). */
public class AlienVoicechatPlugin implements VoicechatPlugin {
    @Override
    public String getPluginId() {
        return "alienmod";
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        // Your own microphone
        registration.registerEvent(ClientSoundEvent.class, event -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player != null) MouthTracker.onAudio(mc.player.getUuid(), event.getRawAudio());
        });
        // Other players talking to you (proximity voice)
        registration.registerEvent(ClientReceiveSoundEvent.EntitySound.class,
            event -> MouthTracker.onAudio(event.getId(), event.getRawAudio()));
    }
}
