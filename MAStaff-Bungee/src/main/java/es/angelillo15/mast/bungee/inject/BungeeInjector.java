package es.angelillo15.mast.bungee.inject;

import com.nookure.mast.addon.ServerAddonManager;
import com.nookure.mast.api.addons.AddonManager;
import com.nookure.mast.api.event.PluginMessageManager;
import es.angelillo15.mast.api.IServerUtils;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.config.common.CommonConfigLoader;
import es.angelillo15.mast.api.inject.CommonModule;
import com.nookure.mast.api.manager.cmd.CommandBungeeSenderManager;
import es.angelillo15.mast.api.utils.MAStaffInject;
import com.nookure.mast.bungee.MAStaff;
import es.angelillo15.mast.bungee.utils.BungeePluginMessageManager;
import es.angelillo15.mast.bungee.utils.BungeeServerUtils;

public class BungeeInjector extends CommonModule {
  @Override
  protected void configure() {
    super.configure();
    bind(MAStaffInstance.class).toInstance(MAStaff.getInstance());
    bind(MAStaffInject.class).toInstance(MAStaff.getInstance());
    bind(MAStaff.class).toInstance(MAStaff.getInstance());
    bind(com.nookure.mast.api.MAStaff.class).toInstance(MAStaff.getInstance());
    bind(BungeeServerUtils.class).asEagerSingleton();
    bind(CommonConfigLoader.class).asEagerSingleton();
    bind(IServerUtils.class).to(BungeeServerUtils.class).asEagerSingleton();
    // bind(TemplateLoaders.class).asEagerSingleton();
    bind(CommandBungeeSenderManager.class).asEagerSingleton();
    bind(AddonManager.class).to(ServerAddonManager.class).asEagerSingleton();
    bind(BungeePluginMessageManager.class).asEagerSingleton();
    bind(PluginMessageManager.class).to(BungeePluginMessageManager.class).asEagerSingleton();
  }
}
