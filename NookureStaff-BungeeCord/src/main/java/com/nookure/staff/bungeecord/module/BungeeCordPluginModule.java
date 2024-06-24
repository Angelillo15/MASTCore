package com.nookure.staff.bungeecord.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.nookure.staff.api.Logger;
import com.nookure.staff.api.NookureStaffPlatform;
import com.nookure.staff.api.event.EventManager;
import com.nookure.staff.api.messaging.EventMessenger;
import com.nookure.staff.messaging.DecoderPluginMessenger;
import com.nookure.staff.bungeecord.NookureStaff;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCordPluginModule extends AbstractModule {
  private final NookureStaff plugin;

  public BungeeCordPluginModule(NookureStaff plugin) {
    this.plugin = plugin;
  }

  @Override
  protected void configure() {
    bind(Logger.class).toInstance(plugin.getPLogger());
    bind(com.nookure.staff.api.NookureStaff.class).toInstance(plugin);
    bind(NookureStaff.class).toInstance(plugin);
    bind(EventManager.class).asEagerSingleton();
    bind(EventMessenger.class).to(DecoderPluginMessenger.class).asEagerSingleton();

    bind(new TypeLiteral<NookureStaffPlatform<Plugin>>() {
    }).toInstance(plugin);
  }
}
