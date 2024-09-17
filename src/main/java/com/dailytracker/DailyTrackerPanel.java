package com.dailytracker;

import net.runelite.api.ItemID;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.game.ItemManager;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DailyTrackerPanel extends PluginPanel {
    private final ItemManager itemManager;
    private final JLabel herbRunLabel;
    private final JLabel birdHouseRunLabel;
    private final JLabel battlestaffLabel;
    private final JLabel essenceLabel;
    private final JLabel farmingContractLabel;

    public DailyTrackerPanel(ItemManager itemManager) {
        this.itemManager = itemManager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        herbRunLabel = new JLabel("Herb run: Not completed");
        birdHouseRunLabel = new JLabel("Birdhouse run: Not completed");
        battlestaffLabel = new JLabel("Daily battlestaves: Not collected");
        essenceLabel = new JLabel("Daily essence: Not collected");
        farmingContractLabel = new JLabel("Farming contract: Not completed");

        add(createIconLabel(herbRunLabel, ItemID.GRIMY_RANARR_WEED));
        add(createIconLabel(birdHouseRunLabel, ItemID.BIRD_HOUSE));
        add(createIconLabel(battlestaffLabel, ItemID.BATTLESTAFF));
        add(createIconLabel(essenceLabel, ItemID.PURE_ESSENCE));
        add(createIconLabel(farmingContractLabel, ItemID.SEED_PACK));
    }

    private JPanel createIconLabel(JLabel label, int itemId) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        BufferedImage icon = itemManager.getImage(itemId);
        JLabel iconLabel = new JLabel(new ImageIcon(icon));
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(label);

        return panel;
    }

    public void update(DailyActivities activities) {
        herbRunLabel.setText("Herb run: " + (activities.isHerbRunCompleted() ? "Completed" : "Not completed"));
        birdHouseRunLabel.setText("Birdhouse run: " + (activities.isBirdHouseRunCompleted() ? "Completed" : "Not completed"));
        battlestaffLabel.setText("Daily battlestaves: " + (activities.isDailyBattlestavesCollected() ? "Collected" : "Not collected"));
        essenceLabel.setText("Daily essence: " + (activities.isDailyEssenceCollected() ? "Collected" : "Not collected"));
        farmingContractLabel.setText("Farming contract: " + (activities.getDailyFarmingContractCompleted() > 0 ? "Completed " + activities.getDailyFarmingContractCompleted() + " times" : "Not completed"));
    }
}