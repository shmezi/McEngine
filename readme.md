# McEngine

> A rapid development engine for Minecraft plugin development.

**Developed by:** Ezra Golombek — [alexirving992@gmail.com](mailto:alexirving992@gmail.com) | [github.com/shmezi](https://github.com/shmezi)

---

## Overview

McEngine is a Kotlin-based Minecraft plugin framework targeting Paper servers (MC 1.19). It provides a modular, batteries-included foundation for building feature-rich plugins — covering economies, custom items, animations, and gameplay modules like a full prison mine system — so you can focus on game logic instead of boilerplate.

---

## Project Structure

The repository is organized as a multi-module Gradle project:

```
McEngine/
├── engine-core/          # Core engine plugin (items, animations, economy, config)
│   └── run/              # Local dev server environment with all dependencies pre-configured
├── engine-prison/        # Prison gamemode module (mines, prestige, effects)
├── build-logic/          # Shared Gradle build conventions
├── build.gradle.kts
└── settings.gradle.kts
```

---

## Core Systems

### Custom Items

McEngine's item system lets you define fully data-driven custom items in JSON. Items support rich placeholders, level-based attributes, group limits, and configurable interaction properties — all without writing Kotlin for each item type.

**Item properties:**

| Property | Type | Description |
|---|---|---|
| `id` | String | Unique item identifier |
| `material` | Material / Placeholder | Bukkit material — can itself be a placeholder (e.g. `%MATERIAL%_PICKAXE`) |
| `name` | String | Display name — supports MiniMessage formatting and placeholders |
| `lore` | String[] | Lore lines — supports MiniMessage formatting and placeholders |
| `properties` | Map | Flags to control interactions: `PLACE`, `THROW`, `CONSUME`, `INVENTORY`, `ALWAYS_KEEP` |
| `groupLimits` | Map | Cap how many attributes of a given group can exist on the item (e.g. max 10 enchants) |
| `placeholders` | Map | Declare `STATIC` (fixed value list) or `DYNAMIC` (level-driven) placeholders |
| `sections` | Map | Attribute sections (e.g. `enchants`) containing individual upgradeable attributes |
| `defaults` | Map | Default attribute levels on item creation (e.g. `"enchants.speed": 1`) |

**Placeholder modes:**
- **`STATIC`** — maps a placeholder to a fixed list of values indexed by level (e.g. `WOODEN → STONE → IRON → DIAMOND`)
- **`DYNAMIC`** — derives the display value from the current attribute level; use `*` for arabic numerals or roman numeral format

**Example — a tiered pickaxe:**
```json
{
  "id": "pickaxe_of_god",
  "material": "%MATERIAL%_PICKAXE",
  "name": "%player%'s Pickaxe",
  "lore": ["A pickaxe given by god or something like that..", "%speed%"],
  "groupLimits": { "enchant": 10 },
  "placeholders": {
    "MATERIAL": { "mode": "STATIC", "values": ["WOODEN", "STONE", "IRON", "GOLDEN", "DIAMOND"] },
    "speed":    { "mode": "DYNAMIC", "format": "LEVEL-*" }
  },
  "defaults": { "enchants.speed": 1 },
  "sections": {
    "enchants": {
      "speed": {
        "id": "speed",
        "max": -1,
        "actions": [[ { "type": "Effect", "data": { "effect": "SPEED" } } ]],
        "groups": [ { "enchant": 1 } ]
      }
    }
  }
}
```

Items are placed in `plugins/McEngine/items/` and hot-reloaded with `/engine reload`.

---

### Animation System

McEngine ships a frame-based animation engine that drives **armor stand entities** through sequences of scripted commands. Animations can be authored in either YAML (a compact scripting DSL) or JSON (structured frame objects).

**Core concepts:**
- **Entities** — named armor stands (e.g. `A`, `B`, `C`) declared at the top of the animation file
- **Frames** — ordered list of commands executed tick-by-tick at a configurable `FrameDelay`
- **Commands** — pipe-separated (`|`) instructions executed together in a single frame

**Available commands:**

| Command | Description |
|---|---|
| `Tp(entity; [x,y,z])` | Teleport a stand to a location |
| `SetItem(entity; slot; itemId)` | Equip an item in an armor stand slot |
| `SetInvisible(entity)` | Make the stand invisible |
| `SetDisplayName(entity; name)` | Set the stand's display name (supports MiniMessage) |
| `DrawLine(action; count; from; to)` | Interpolate an action across a straight line over N sub-frames |
| `DrawCircle(action; origin; axis; angle; count)` | Sweep an action along a circular arc |
| `Kill(entity)` | Remove a stand |
| `Loop(command; ticks)` | Repeat a command for a number of ticks |
| `Empty()` | No-op frame (used for timing/delays) |

**YAML format** (compact scripting DSL):
```yaml
FrameDelay: 1
Stands:
  A: ARMOR_STAND
  B: ARMOR_STAND
Frames:
  - "SetItem(A;4;Skull)|DrawLine(Tp(A;[X,Y,Z]);9;[0,0,0];[0,1,0])"
  - "Empty()"
  - "Loop(Empty();100)"
```

**JSON format** (structured, better for programmatic generation):
```json
{
  "id": "example",
  "delay": 1,
  "entities": { "A": "ARMOR_STAND" },
  "frames": [[
    { "type": "SetInvisible", "entity": "A" },
    { "type": "SetItem", "entity": "A", "slot": 4, "itemId": "Skull" },
    { "type": "DrawLine", "frameCount": 600,
      "from": { "x": 0.0, "y": 0.0, "z": 0.0 },
      "to":   { "x": 10.0, "y": 10.0, "z": -3.0 },
      "action": { "type": "Tp", "entity": "A", "location": { "x": "X", "y": "Y", "z": "Z" } }
    }
  ]]
}
```

Animation files live in `plugins/McEngine/animations/` and are compiled at startup by `AniCompiler` into `AnimationSession` objects that can be played, paused, and killed at runtime.

---

### Economy

A multi-economy system backed by MongoDB (via KMongo). Multiple named economies can run simultaneously — the default config ships with `Default` and `Tokens`.

```yaml
Ecos:
  - "Default"
  - "Tokens"
DefaultEco: "Default"
```

| Command | Description | Permission |
|---|---|---|
| `/eco <player>?` | Check a player/bank balance | `engine.eco.balance` |
| `/eco set <player>` | Set a player/bank balance | `engine.eco.set` |
| `/eco pay` | Pay a player or bank | `engine.eco.pay` |
| `/eco balancetop` | Show top balances | `engine.eco.balancetop` |

---

### Mine System (`engine-prison`)

A full private-mine and prestige system built on top of `engine-core`. Each mine is defined in `config.yml` with block composition, reset timing, spawn points, and event hooks.

**Mine config example:**
```yaml
Mines:
  A:
    Duration: 5000          # Ticks between auto-resets
    On-Reset-Player:
      - "msg %player% mine has been reset!"
    Location:
      World: "world"
      Spawn: { X: 0.0, Y: 100.0, Z: 0.0 }
      LocationA: { X: 10.0, Y: 100.0, Z: 10.0 }
      LocationB: { X: -10.0, Y: 100.0, Z: -10.0 }
    Materials:
      STONE: 10
      DIAMOND_ORE: 50
      DIAMOND_BLOCK: 30
```

Each mine also supports **private mine** sub-configs (per-player instances) with their own block compositions and reset intervals.

**Mine effects** are special triggered events that fire inside mines:

| Effect | Description |
|---|---|
| `Laser` | Fires a laser visual effect |
| `Nuke` | Triggers a nuke-style block clear |
| `Jackhammer` | Breaks blocks in a large radius around the player |

**Prestiges** map directly to mine IDs — when a player prestiges through mine `A`, the `A` prestige entry fires its price check and reward commands:
```yaml
Prestiges:
  A:
    Price: 100
    Name: "A"
    Cmds:
      - "give %player% diamond 5"
```

> When setting up prestiges, make sure each prestige `id` matches the corresponding mine's ID.

---

## Additional Engine Commands

| Command | Description | Permission |
|---|---|---|
| `/engine getitem` | Give yourself a custom engine item | `engine.get` |
| `/engine reload` | Hot-reload the entire engine | `engine.core.reload` |
| `/engine forceDbUpdate` | Force a database sync to MongoDB | `engine.core.dbupdate` |

---

## Tech Stack

- **Language:** Kotlin
- **Build:** Gradle (Kotlin DSL), multi-module with shared `build-logic` conventions
- **Server target:** Paper 1.19
- **Key dependencies:**
    - [KMongo](https://litote.org/kmongo/) — MongoDB Kotlin driver for persistence
    - [PacketEvents](https://github.com/retrooper/packetevents) — low-level packet API
    - [ProtocolLib](https://github.com/dmulloy2/ProtocolLib) — protocol manipulation
    - [WorldEdit](https://enginehub.org/worldedit/) — region & block manipulation (used by mine resets)
    - [Triumph Frameworks](https://triumphteam.dev/) — command & GUI framework
    - [NbtApi](https://github.com/tr7zw/Item-NBT-API) — NBT tag access for custom items
    - [Adventure Chat](https://docs.advntr.dev/) — MiniMessage rich text in item names/lore
    - [Reflections](https://github.com/ronmamo/reflections) — runtime classpath scanning
    - [ViaVersion / ViaBackwards](https://viaversion.com/) — multi-version client support

---

## Getting Started

### Prerequisites
- JDK 17+
- Gradle (or use the included `./gradlew` wrapper)
- A MongoDB instance (for economy and player data persistence)

### Build

```bash
git clone https://github.com/shmezi/McEngine.git
cd McEngine
./gradlew build
```

### Run the Dev Server

A pre-configured Paper 1.19 dev environment lives under `engine-core/run/`. All required plugins are already present.

```bash
./gradlew runServer
```

### Configuration

All plugin configs live under `plugins/McEngine/` in the dev environment:

| File / Folder | Purpose |
|---|---|
| `config.yml` | Main settings — MongoDB connection, economies, mines, prestiges |
| `items.yml` | Simple item registry (YAML format) |
| `items/*.json` | Rich custom item definitions (JSON format) |
| `animations/*.yml` | YAML-format animation scripts |
| `animations/*.json` | JSON-format animation definitions |

---

## License

All code in this project is fully owned by Ezra Golombek (shmezi). **Do not redistribute.**

© 2020 Ezra Golombek