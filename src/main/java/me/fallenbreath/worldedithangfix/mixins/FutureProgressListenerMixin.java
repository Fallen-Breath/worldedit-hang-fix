/*
 * This file is part of the worldedit-hang-fix project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2024  Fallen_Breath and contributors
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

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Timer;

@SuppressWarnings("UnresolvedMixinReference")
@Pseudo
@Mixin(targets = "com.sk89q.worldedit.command.util.FutureProgressListener")
public abstract class FutureProgressListenerMixin
{
	/**
	 * Ensure the timer thread is a daemon thread, or the server will hang forever
	 */
	@Redirect(
			method = "<clinit>",
			at = @At(
					value = "NEW",
					target = "()Ljava/util/Timer;",
					remap = false
			),
			remap = false
	)
	private static Timer replaceWithTimerWithDaemonThread()
	{
		return new Timer(true);
	}
}
