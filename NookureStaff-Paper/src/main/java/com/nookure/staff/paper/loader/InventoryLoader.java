package com.nookure.staff.paper.loader;

import com.google.inject.Inject;
import com.nookure.core.inv.paper.PaperNookureInventoryEngine;
import com.nookure.core.inv.template.extension.PaginationItemExtension;
import com.nookure.staff.api.util.AbstractLoader;
import com.nookure.staff.api.util.JarUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class InventoryLoader implements AbstractLoader {
  @Inject
  private JavaPlugin plugin;
  @Inject
  private AtomicReference<PaperNookureInventoryEngine> engine;

  @Override
  public void load() {
    PaperNookureInventoryEngine.Builder builder = new PaperNookureInventoryEngine.Builder()
        .plugin(plugin)
        .templateFolder("inventories")
        .extensions(new PaginationItemExtension());

    engine.set(builder.build());

    try {
      JarUtil.copyFolderFromJar("inventories", plugin.getDataFolder(), JarUtil.CopyOption.COPY_IF_NOT_EXIST);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
