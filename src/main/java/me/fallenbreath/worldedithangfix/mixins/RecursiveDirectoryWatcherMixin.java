/*
 * This file is part of the worldedit-hang-fix project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2026  Fallen_Breath and contributors
 *
 * worldedit-hang-fix is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * worldedit-hang-fix is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with worldedit-hang-fix.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.fallenbreath.worldedithangfix.mixins;

import me.fallenbreath.worldedithangfix.WorldEditHangFixMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.sk89q.worldedit.internal.util.RecursiveDirectoryWatcher")
public abstract class RecursiveDirectoryWatcherMixin
{
	@Shadow(remap = false)
	private Thread watchThread;

	/**
	 * Ensure the thread is a daemon thread,
	 * or the server might hang forever if the thread itself can't exit by itself (e.g. in worldedit >= 7.4.1)
	 */
	@Inject(
			method = "start",
			at = @At(
					value = "INVOKE",
					target = "Ljava/lang/Thread;start()V",
					remap = false
			),
			remap = false
	)
	private void setWatchThreadDaemon(CallbackInfo ci)
	{
		WorldEditHangFixMod.LOGGER.debug("Ensuring that the RecursiveDirectoryWatcher#start launch a daemon thread");
		this.watchThread.setDaemon(true);
	}
}
