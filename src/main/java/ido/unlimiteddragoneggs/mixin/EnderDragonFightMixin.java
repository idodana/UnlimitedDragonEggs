package ido.unlimiteddragoneggs.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.Blocks;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;

@Mixin(EnderDragonFight.class)
public abstract class EnderDragonFightMixin {
	@Shadow
	private ServerBossBar bossBar;

	@Shadow
	private UUID dragonUuid;

	@Shadow
	public abstract void generateEndPortal(boolean previouslyKilled);

	@Shadow
	public abstract void generateNewEndGateway();

	@Shadow
	private ServerWorld world;

	@Shadow
	private boolean previouslyKilled;

	@Shadow
	private boolean dragonKilled;

	@Overwrite
	public void dragonKilled(EnderDragonEntity dragon) {
		if (dragon.getUuid().equals(this.dragonUuid)) {
			this.bossBar.setPercent(0.0F);
			this.bossBar.setVisible(false);
			this.generateEndPortal(true);
			this.generateNewEndGateway();
			this.world.setBlockState(this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.ORIGIN),
					Blocks.DRAGON_EGG.getDefaultState());
			this.previouslyKilled = true;
			this.dragonKilled = true;
		}
	}
}
