package sladki.tfc.ab.Entities.Mobs.Extended;

import com.bioxx.tfc.Entities.AI.EntityAIAvoidEntityTFC;
import com.bioxx.tfc.Entities.AI.EntityAIPanicTFC;
import com.bioxx.tfc.Entities.Mobs.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.Iterator;


public class EntityLivestock implements IExtendedEntityProperties {

    public final static String extensionTagName = "extendedPropertiesTFCLivestock";
    private static Class[] mobsClasses;
    private static Class[] blacklistedAIs;

    private boolean hasShepherd = false;


    public static void setup() {
        mobsClasses = new Class[5];

        int i = 0;
        mobsClasses[i++] = EntityChickenTFC.class;
        mobsClasses[i++] = EntityCowTFC.class;
        mobsClasses[i++] = EntityHorseTFC.class;
        mobsClasses[i++] = EntityPigTFC.class;
        mobsClasses[i++] = EntitySheepTFC.class;

        blacklistedAIs = new Class[5];

        i = 0;
        blacklistedAIs[i++] = EntityAIAvoidEntityTFC.class;
        blacklistedAIs[i++] = EntityAIWander.class;
        blacklistedAIs[i++] = EntityAIFollowParent.class;
        blacklistedAIs[i++] = EntityAIPanic.class;
        blacklistedAIs[i++] = EntityAIPanicTFC.class;
    }

    public static boolean hasShepherd(Entity entity) {
        return ((EntityLivestock) entity.getExtendedProperties(extensionTagName)).hasShepherd;
    }

    public static void toggleHerd(Entity entity) {
        ((EntityLivestock) entity.getExtendedProperties(extensionTagName)).hasShepherd ^= true;
    }

    public static boolean isDomesticated(Entity entity) {
        for(int i = 0; i < EntityLivestock.mobsClasses.length; i++) {
            if(entity.getClass().equals(EntityLivestock.mobsClasses[i])) {
                return true;
            }
        }

        return false;
    }

    public static void setHerdAI(Entity entity) {
        Iterator<EntityAITasks.EntityAITaskEntry> iterator = ((EntityLiving) entity).tasks.taskEntries.iterator();
        while(iterator.hasNext()) {
            Class aiTaskClass = iterator.next().action.getClass();
            for(int i = 0; i < blacklistedAIs.length; i++) {
                if(aiTaskClass.equals(blacklistedAIs[i])) {
                    iterator.remove();
                }
            }
        }

        ((EntityLiving) entity).getNavigator().clearPathEntity();
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setBoolean("hasShepherd", hasShepherd);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        hasShepherd = compound.getBoolean("hasShepherd");
    }

    @Override
    public void init(Entity entity, World world) {}

}
