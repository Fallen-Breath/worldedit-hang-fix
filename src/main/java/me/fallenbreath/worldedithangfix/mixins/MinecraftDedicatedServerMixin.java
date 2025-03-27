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

import me.fallenbreath.worldedithangfix.WorldEditHangFixMod;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

@Mixin(MinecraftDedicatedServer.class)
public abstract class MinecraftDedicatedServerMixin
{
	/**
	 * Shutdown WorldEdit.getInstance().getExecutorService(), or the server will hang for a while (<60s)
	 * See also: {@link com.sk89q.worldedit.util.concurrency.EvenMoreExecutors#newBoundedCachedThreadPool}
	 */
	@Inject(method = "shutdown", at = @At("TAIL"))
	private void shutdownWorldEditTaskExecutor(CallbackInfo ci)
	{
		ExecutorService executorService;
		try
		{
			Class<?> clazz = Class.forName("com.sk89q.worldedit.WorldEdit");
			Object we = clazz.getMethod("getInstance").invoke(null);
			Method method = clazz.getMethod("getExecutorService");
			executorService = (ExecutorService)method.invoke(we);
		}
		catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e)
		{
			if (e instanceof InvocationTargetException && e.getCause() instanceof IllegalStateException && "Currently invalid".equals(e.getCause().getMessage()))
			{
				// WorldEdit v7.3.11 fixes this executor services issue
				// https://github.com/EngineHub/WorldEdit/pull/2715
				// With WorldEdit v7.3.11, calling getExecutorService() will get an IllegalStateException, since it has stopped already
				// see also:
				//   com.sk89q.worldedit.extension.platform.PlatformManager#handleNewPlatformUnready
				//   com.sk89q.worldedit.extension.platform.PlatformManager#getExecutorService
				//   com.sk89q.worldedit.util.lifecycle.Lifecycled#valueOrThrow
				return;
			}
			WorldEditHangFixMod.LOGGER.error("Failed to shutdown worldedit's task executor", e);
			return;
		}

		WorldEditHangFixMod.LOGGER.debug("Shutting down the executor service of WorldEdit");
		executorService.shutdown();
	}
}
