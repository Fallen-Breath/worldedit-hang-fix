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

package me.fallenbreath.worldedithangfix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//#if FORGE
//$$ import net.minecraftforge.fml.common.Mod;
//#elseif NEOFORGE
//$$ import net.neoforged.fml.common.Mod;
//#endif

//#if FORGE_LIKE
//$$ @Mod(WorldEditHangFixMod.MOD_ID)
//#endif
public class WorldEditHangFixMod
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "worldedithangfix";
}
