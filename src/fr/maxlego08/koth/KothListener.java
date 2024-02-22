package fr.maxlego08.koth;

import fr.maxlego08.koth.board.Board;
import fr.maxlego08.koth.board.ColorBoard;
import fr.maxlego08.koth.board.EmptyBoard;
import fr.maxlego08.koth.listener.ListenerAdapter;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.utils.nms.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;

public class KothListener extends ListenerAdapter {

    private final KothPlugin plugin;
    private final KothManager manager;
    private final Board board = NMSUtils.isHexColor() ? new ColorBoard() : new EmptyBoard();

    public KothListener(KothPlugin plugin, KothManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    protected void onInteract(PlayerInteractEvent event, Player player) {

        ItemStack itemStack = player.getItemInHand();

        if (itemStack != null && event.getClickedBlock() != null && same(itemStack, Message.AXE_NAME.getMessage())) {

            event.setCancelled(true);

            if (NMSUtils.isTwoHand() && event.getHand() == EquipmentSlot.OFF_HAND) return;

            Optional<Selection> optional = this.manager.getSelection(player.getUniqueId());
            Selection selection = null;

            if (!optional.isPresent()) {
                selection = new Selection();
                this.manager.createSelection(player.getUniqueId(), selection);
            } else {
                selection = optional.get();
            }

            Location location = event.getClickedBlock().getLocation();
            org.bukkit.event.block.Action action = event.getAction();

            LivingEntity entity = null;

            if (NMSUtils.isHexColor()) {

                Shulker shulker = location.getWorld().spawn(location, Shulker.class);
                shulker.setInvulnerable(true);
                shulker.setAI(false);
                shulker.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 99999, 1, false, false));
                shulker.setInvisible(true);
                shulker.setCollidable(false);

                entity = shulker;

                Bukkit.getScheduler().runTaskLater(this.plugin, shulker::remove, 20 * 60);
            }

            selection.action(action, location, entity);

            this.board.addEntity(selection.isCorrect(), selection.getLeftEntity());
            this.board.addEntity(selection.isCorrect(), selection.getRightEntity());

            Message message = action.equals(org.bukkit.event.block.Action.LEFT_CLICK_BLOCK) ? Message.AXE_POS1 : Message.AXE_POS2;
            message(player, message, "%x%", String.valueOf(location.getBlockX()), "%y%", String.valueOf(location.getBlockY()), "%z%", String.valueOf(location.getBlockZ()), "%world%", location.getWorld().getName());

            if (selection.isValid()) {
                message(player, selection.isCorrect() ? Message.AXE_VALID : Message.AXE_ERROR);
            }
        }
    }
}