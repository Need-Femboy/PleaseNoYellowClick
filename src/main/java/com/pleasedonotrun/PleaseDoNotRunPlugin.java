package com.pleasedonotrun;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.Keybind;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@Slf4j
@PluginDescriptor(
	name = "Please no yellow click",
	description = "Press a hotkey and click away to do something without worrying about yellow clicks",
	tags = {"pvm", "skilling", "yellow"}
)
public class PleaseDoNotRunPlugin extends Plugin implements KeyListener
{
	@Inject
	private Client client;

	@Inject
	private KeyManager keyManager;

	@Inject
	private PleaseDoNotRunConfig config;

	private boolean hotkeyPressed;


	@Override
	protected void startUp() {
		keyManager.registerKeyListener(this);
	}


	@Override
	protected void shutDown() {
		keyManager.unregisterKeyListener(this);
	}

	//Basically so you can ctrl click stuff still to walk it
	private boolean hotkeyMatch(KeyEvent e)
	{
		Keybind hotkey = config.hotkey();
		if (hotkey == Keybind.NOT_SET)
		{
			return false;
		}

		Keybind ctrlHotkey = new Keybind(hotkey.getKeyCode(), hotkey.getModifiers() | InputEvent.CTRL_DOWN_MASK); //config.hotkey() but with ctrl modifier attached

		return config.hotkey().matches(e) || ctrlHotkey.matches(e);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (hotkeyMatch(e))
		{
			hotkeyPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (hotkeyMatch(e))
		{
			hotkeyPressed = false;
		}
	}

	@Subscribe
	private void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (hotkeyPressed && event.getMenuAction().equals(MenuAction.WALK))
		{
			event.consume();
		}
	}

	@Subscribe
	public void onFocusChanged(FocusChanged event)
	{
		if (!event.isFocused())
		{
			hotkeyPressed = false;
		}
	}

	//stub
	@Override
	public void keyTyped(KeyEvent e) {}

	@Provides
	PleaseDoNotRunConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PleaseDoNotRunConfig.class);
	}
}
