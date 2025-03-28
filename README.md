## worldedit-hang-fix

[![License](https://img.shields.io/github/license/Fallen-Breath/worldedit-hang-fix.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
[![MC Versions](https://cf.way2muchnoise.eu/versions/For%20MC_1122137_all.svg)](https://www.curseforge.com/minecraft/mc-mods/worldedit-hang-fix)
[![CurseForge](https://cf.way2muchnoise.eu/full_1122137_downloads.svg)](https://legacy.curseforge.com/minecraft/mc-mods/worldedit-hang-fix)
[![Modrinth](https://img.shields.io/modrinth/dt/uz1wuff3?label=Modrinth%20Downloads)](https://modrinth.com/project/worldedit-hang-fix)

A simple mod that prevents WorldEdit from hanging the Minecraft server when the server is stopping

It completely fixes https://github.com/EngineHub/WorldEdit/issues/2459. What it does:

- Shutdown `WorldEdit.getInstance().getExecutorService()` on dedicated server stop (or it will hang the server for maximum 60s) (WorldEdit had it fixed in v7.3.11)
- Ensure the singleton `java.util.Timer` object in `FutureProgressListener` runs on a daemon thread (or it will hang the server forever)

Supported environment

| Platform | Minecraft    | WorldEdit |
|----------|--------------|-----------|
| fabric   | MC >= 1.14   | >=7.0.0   |
| forge    | MC >= 1.15   | >=7.0.0   |
| neoforge | MC >= 1.20.6 | >=7.0.0   |
