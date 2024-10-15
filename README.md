## worldedit-hang-fix

[![License](https://img.shields.io/github/license/Fallen-Breath/worldedit-hang-fix.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)

A simple mod that prevents WorldEdit from hanging the Minecraft server when the server is stopping

It fixes https://github.com/EngineHub/WorldEdit/issues/2459. What it does:

- Shutdown `WorldEdit.getInstance().getExecutorService()` on dedicated server stop (or it will hang the server for maximum 60s)
- Ensure the singleton `java.util.Timer` object in `FutureProgressListener` runs on a daemon thread (or it will hang the server forever)

Supported environment

| Platform | Minecraft    |
|----------|--------------|
| fabric   | MC >= 1.14   |
| forge    | MC >= 1.15   |
| neoforge | MC >= 1.20.6 |
