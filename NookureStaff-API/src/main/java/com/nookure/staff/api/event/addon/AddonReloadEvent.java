package com.nookure.staff.api.event.addon;

import com.nookure.staff.api.addons.AddonContainer;
import com.nookure.staff.api.event.Event;

public record AddonReloadEvent(AddonContainer container) implements Event {
}