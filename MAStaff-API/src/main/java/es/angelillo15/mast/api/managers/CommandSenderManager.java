package es.angelillo15.mast.api.managers;

import es.angelillo15.mast.api.cmd.sender.CommandSender;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentHashMap;

public abstract class CommandSenderManager<S> {
  private final ConcurrentHashMap<S, CommandSender> senders = new ConcurrentHashMap<>();

  /**
   * Gets a sender from the manager
   * @param sender The sender to get
   * @return The sender from the manager
   */
  @Nullable
  public CommandSender getSender(S sender) {
    return senders.computeIfAbsent(sender, this::createCommandSender);
  }

  /**
   * Registers a sender to the manager
   * @param sender The sender to register
   */
  public void registerSender(@NotNull S sender) {
    Intrinsics.checkNotNull(sender, "Sender cannot be null");
    senders.put(sender, createCommandSender(sender));
  }

  /**
   * Unregisters a sender from the manager
   * @param sender The sender to unregister
   */
  public void unregisterSender(@NotNull S sender) {
    Intrinsics.checkNotNull(sender, "Sender cannot be null");
    senders.remove(sender);
  }

  /**
   * Creates a CommandSender from a sender
   * @param sender The sender to create the CommandSender from
   * @return The CommandSender created
   */
  public abstract CommandSender createCommandSender(S sender);
}
