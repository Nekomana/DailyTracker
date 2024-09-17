package com.dailytracker;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.GameState;
import net.runelite.client.game.ItemManager;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@PluginDescriptor(
		name = "Daily Tracker",
		description = "Tracks daily activities in Old School RuneScape",
		tags = {"daily", "tracker", "activities"}
)
public class DailyTrackerPlugin extends Plugin {
	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private ItemManager itemManager;

	private NavigationButton navButton;
	private DailyTrackerPanel panel;
	private Map<LocalDate, DailyActivities> dailyActivitiesMap = new HashMap<>();

	@Override
	protected void startUp() {
		panel = new DailyTrackerPanel(itemManager);
		BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/icon.png");
		navButton = NavigationButton.builder()
				.tooltip("Daily Tracker")
				.icon(icon)
				.priority(5)
				.panel(panel)
				.build();
		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() {
		clientToolbar.removeNavigation(navButton);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged) {
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
			updateDailyActivities();
		}
	}

	private void updateDailyActivities() {
		LocalDate today = LocalDate.now();
		DailyActivities activities = dailyActivitiesMap.computeIfAbsent(today, k -> new DailyActivities());
		// TODO: Implement logic to update daily activities
		panel.update(activities);
	}
}