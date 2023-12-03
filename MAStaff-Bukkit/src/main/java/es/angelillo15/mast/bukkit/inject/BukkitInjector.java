package es.angelillo15.mast.bukkit.inject;

import com.nookure.mast.addon.ServerAddonManager;
import com.nookure.mast.api.addons.AddonManager;
import com.nookure.mast.api.manager.FreezeManager;
import com.nookure.mast.api.staff.StaffFeatureManager;
import es.angelillo15.mast.api.IServerUtils;
import com.nookure.mast.api.manager.cmd.CommandBukkitSenderManager;
import es.angelillo15.mast.api.nms.VersionSupport;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.inject.CommonModule;
import es.angelillo15.mast.api.managers.StaffManager;
import es.angelillo15.mast.api.utils.MAStaffInject;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.ServerUtils;
import es.angelillo15.mast.bukkit.manager.BukkitStaffFeatureManager;
import es.angelillo15.mast.bukkit.utils.NMSUtils;
import org.bukkit.plugin.Plugin;

public class BukkitInjector extends CommonModule {
  @Override
  protected void configure() {
    super.configure();
    bind(MAStaffInstance.class).toInstance(MAStaff.getPlugin());
    bind(MAStaff.class).toInstance(MAStaff.getPlugin());
    bind(com.nookure.mast.api.MAStaff.class).toInstance(MAStaff.getPlugin());
    bind(MAStaffInject.class).toInstance(MAStaff.getPlugin());
    bind(StaffManager.class).asEagerSingleton();
    bind(IServerUtils.class).to(ServerUtils.class).asEagerSingleton();
    bind(VersionSupport.class).toInstance(NMSUtils.getVersionSupport());
    bind(Plugin.class).toInstance(MAStaff.getPlugin());
    bind(CommandBukkitSenderManager.class).asEagerSingleton();
    bind(AddonManager.class).to(ServerAddonManager.class).asEagerSingleton();
    bind(StaffFeatureManager.class).to(BukkitStaffFeatureManager.class).asEagerSingleton();
    bind(FreezeManager.class).asEagerSingleton();
  }
}
