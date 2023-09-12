---
description: How to set-up the glow module
---

# 🔆 Glow

### Config  of the module

```yaml
Config:
  # The plugin will get the groups from Vault,
  # make sure you have it installed.
  # Color per group. These are the available colors:
  # BLUE, GREEN, YELLOW, AQUA, DARK_AQUA, DARK_BLUE,
  # DARK_GRAY, DARK_GREEN, DARK_PURPLE, DARK_RED, GOLD,
  # GRAY, LIGHT_PURPLE, WHITE, BLACK
  groups:
    helper:
      color: 'GREEN'
    moderator:
      color: 'DARK_AQUA'
    administrator:
      color: 'RED'
    owner:
      color: 'DARK_RED'
```

Here you will learn to set-up the glow module colors for each group, for this you will need vault, if you doesn't have a tab/scoreboard plugin thats all

## Tab settings

#### Compatibility issue

> Glow color is managed by scoreboard teams, which also handle nametag formatting and player sorting in tablist. Only one plugin can handle teams at a time. Because of that, TAB will prevent glow plugins from assigning players into teams.
>
>
>
> From its oficial [wiki](https://github.com/NEZNAMY/TAB/wiki/How-to-make-TAB-compatible-with-glow-plugins#reason-for-compatibility-issue)

You are going to need to go to the **groups.yml** and add this placeholder \`%mastaff\_glow\_color%\`

```yaml
_DEFAULT_:
  tagprefix: '%vault-prefix%%mastaff_glow:color%'
```

This will add the color to the name if you are in staffmode with glow enabled

> Keep in mind glow color is the same as name color. That's because there's only one field handling both values. However, you can bypass this with [Unlimited nametag mode](https://github.com/NEZNAMY/TAB/wiki/Feature-guide:-Unlimited-nametag-mode). When enabling it, you'll get access to `customtagname` property, which is really just a part of the final armor stand string and can be freely modified.
>
>
>
> From its official [wiki](https://github.com/NEZNAMY/TAB/wiki/How-to-make-TAB-compatible-with-glow-plugins#solution)



