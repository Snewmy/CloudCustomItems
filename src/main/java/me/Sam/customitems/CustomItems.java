package me.Sam.customitems;

import com.gmail.nossr50.mcMMO;
import me.Sam.customitems.Listeners.*;
import me.Sam.customitems.commandshit.*;
import me.Sam.customitems.items.*;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class CustomItems extends JavaPlugin {

    public static CustomItems instance;
    public mcMMO mcmmo;
    public static HashMap<String, CustomItem> customItems = new HashMap<>();
    public CoreProtectAPI coreApi;
    public NamespacedKey nameKey = new NamespacedKey(this, "customitems");
    public static Economy econ = null;
    public File messagesFile;
    public FileConfiguration messages;

    public void onEnable() {
        instance = this;
        saveResource("messages.yml", false);
        messagesFile = new File(getDataFolder(), "messages.yml");
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        PluginManager pm = getServer().getPluginManager();
        getServer().getLogger().info("Custom Items Enabled.");
        if (!this.setupEconomy()) {
            this.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", this.getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        }
        pm.registerEvents(new BlockBreak(), this);
        pm.registerEvents(new OnBlockPlace(), this);
        pm.registerEvents(new OnInteract(), this);
        pm.registerEvents(new ProjectileHit(), this);
        pm.registerEvents(new BucketEmpty(), this);
        pm.registerEvents(new BucketFill(), this);
        pm.registerEvents(new EntityDamage(), this);
        pm.registerEvents(new EntityDamageByEntity(), this);
        pm.registerEvents(new ItemConsume(), this);
        pm.registerEvents(new LaunchProjectile(), this);
        pm.registerEvents(new PlayerItemDamage(), this);
        pm.registerEvents(new RenameListener(), this);
        pm.registerEvents(new CauldronLevelChange(), this);
        pm.registerEvents(new InventoryClick(), this);
        pm.registerEvents(new PlayerInteractEntity(), this);
        if (pm.getPlugin("Jobs") != null) {
            pm.registerEvents(new JobsListener(), this);
        }
        getCommand("givecustomitem").setExecutor(new GiveCommand());
        getCommand("givecustomitem").setTabCompleter(new GiveTab());
        getCommand("customitems").setExecutor(new CustomItemsCommand());
        getCommand("customitems").setTabCompleter(new CustomItemsTab());
        if (pm.getPlugin("mcMMO") != null) {
            this.mcmmo = (mcMMO) pm.getPlugin("mcMMO");
        }
        this.coreApi = getCoreProtect();
        if (coreApi != null) { //Ensure we have access to the API
            coreApi.testAPI(); //Will print out "[CoreProtect] API Test Successful." in the console.
        }
        //CustomItem arrowBlaster = new ArrowBlaster("arrowblaster");
        CustomItem biggerInfiniteSponge = new BiggerInfiniteSponge("biggerinfinitesponge");
        CustomItem diggersShovel = new DiggersShovel("diggersshovel");
        CustomItem endlessTorches = new EndlessTorches("endlesstorches");
        CustomItem explosiveBow = new ExplosiveBow("explosivebow");
        CustomItem glassShovel = new GlassShovel("glassshovel");
        CustomItem infiniteBoneMeal = new InfiniteBoneMeal("infinitebonemeal");
        CustomItem infiniteEmptyBucket = new InfiniteEmptyBucket("infiniteemptybucket");
        CustomItem infiniteEnderpearl = new InfiniteEnderpearl("infiniteenderpearl");
        CustomItem infiniteHoneyBottle = new InfiniteHoneyBottle("infinitehoneybottle");
        CustomItem infiniteLavaBucket = new InfiniteLavaBucket("infinitelavabucket");
        CustomItem infiniteSponge = new InfiniteSponge("infinitesponge");
        CustomItem infiniteWaterBucket = new InfiniteWaterBucket("infinitewaterbucket");
        CustomItem lavaRod = new LavaRod("lavarod");
        CustomItem minersPickaxe = new MinersPickaxe("minerspickaxe");
        CustomItem smelterPickaxe = new SmelterPickaxe("smelterpickaxe");
        CustomItem teleportbow = new TeleportationBow("teleportbow");
        CustomItem unbreakableShears = new UnbreakableShears("unbreakableshears");
        CustomItem copperManipulator = new CopperManipulator("coppermanipulator");
        CustomItem santascookies = new SantasCookies("santascookies");
        CustomItem santasmilk = new SantasMilk("santasmilk");
        CustomItem infinitesnowball = new InfiniteSnowball("infinitesnowball");
        CustomItem endlessPumpkinPie = new EndlessPumpkinPie("endlesspumpkinpie");
        CustomItem bottomlesspowdersnow = new BottomlessPowderSnow("bottomlesspowdersnow");
        new Locale();
    }

    public ItemStack handCheck(Player p, String nbtTag) {
        ItemStack mainHand = p.getInventory().getItemInMainHand();
        ItemStack offHand = p.getInventory().getItemInOffHand();

        if (mainHand.hasItemMeta()) {
            PersistentDataContainer dataContainer = mainHand.getItemMeta().getPersistentDataContainer();
            if (dataContainer.has(nameKey, PersistentDataType.STRING)) {
                if (dataContainer.get(nameKey, PersistentDataType.STRING).equalsIgnoreCase(nbtTag)) {
                    return mainHand;
                }
            }
        }
        if (offHand.hasItemMeta()) {
            PersistentDataContainer dataContainer = offHand.getItemMeta().getPersistentDataContainer();
            if (dataContainer.has(nameKey, PersistentDataType.STRING)) {
                if (dataContainer.get(nameKey, PersistentDataType.STRING).equalsIgnoreCase(nbtTag)) {
                    return offHand;
                }
            }
        }
        return null;
    }

    private CoreProtectAPI getCoreProtect() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (plugin == null) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((net.coreprotect.CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled()) {
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 6) {
            return null;
        }

        return CoreProtect;
    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            } else {
                econ = (Economy)rsp.getProvider();
                return true;
            }
        }
    }
}
