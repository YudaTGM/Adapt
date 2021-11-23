package com.volmit.adapt.content.adaptation.experimental;

import com.volmit.adapt.Adapt;
import com.volmit.adapt.api.adaptation.SimpleAdaptation;
import com.volmit.adapt.util.C;
import com.volmit.adapt.util.Element;
import com.volmit.adapt.util.Form;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.Random;


public class RiftStorage extends SimpleAdaptation {
    public RiftStorage() {
        super("ender-storage");
        setDescription("Open an enderchest by clicking");
        setIcon(Material.ENDER_CHEST);
        setBaseCost(0);
        setCostFactor(0);
        setMaxLevel(1);
        setInitialCost(10);
        setInterval(9248);
    }

    private double getConsumePercent(int level) {
        return 0.15 + (0.15 * level);
    }


    @Override
    public void addStats(int level, Element v) {
        v.addLore(C.GREEN + "+ " + Form.pc(getConsumePercent(level)) + C.GRAY + " Chance to Resist Consumption");
        v.addLore(C.ITALIC + "*Click an Enderchest in your hand to open (Just dont place)*");
    }


    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (getLevel(e.getPlayer()) >0 ){
            if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ENDER_CHEST)
                && (e.getAction().equals(Action.RIGHT_CLICK_AIR)
                || e.getAction().equals(Action.LEFT_CLICK_AIR)
                || e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
            Adapt.info("Opened Enderchest");
            Player p = e.getPlayer();

            Objects.requireNonNull(p.getLocation().getWorld()).playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1.35f, 0.100f); // Not sure why i need to do this NONNULL here only
            p.getLocation().getWorld().playSound(p.getLocation(), Sound.PARTICLE_SOUL_ESCAPE, 5.35f, 0.10f);
            p.getLocation().getWorld().playSound(p.getLocation(), Sound.BLOCK_BELL_RESONATE, 5.35f, 0.10f);
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 45, 1000));
            p.openInventory(e.getPlayer().getEnderChest());



            }
        }
    }


    @Override
    public void onTick() {

    }
}