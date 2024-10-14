## worldedit-hang-fix

[![License](https://img.shields.io/github/license/Fallen-Breath/fabric-mod-template.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)

WorldEdit plz don't hang the Minecraft server forever on server stopping

A simple mod that fixes https://github.com/EngineHub/WorldEdit/issues/2459

What it patches:

- Shutdown `WorldEdit.getInstance().getExecutorService()` on dedicated server stop
- Ensure the singleton `java.util.Timer` object in `FutureProgressListener` runs on a daemon thread
