package com.pleasedonotrun;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PleaseDoNotRunPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PleaseDoNotRunPlugin.class);
		RuneLite.main(args);
	}
}