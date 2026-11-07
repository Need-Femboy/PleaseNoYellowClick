package com.pleasedonotrun;

import net.runelite.client.config.*;

import static com.pleasedonotrun.PleaseDoNotRunConfig.CONFIG_GROUP;

@ConfigGroup(CONFIG_GROUP)
public interface PleaseDoNotRunConfig extends Config
{
	String CONFIG_GROUP = "pleasedonotrun";
	@ConfigItem(
		keyName = "hotkey",
		name = "No movement hotkey",
		description = "What key you have to press down to disable yellow click movement"
	)
	default Keybind hotkey()
	{
		return Keybind.NOT_SET;
	}
}
