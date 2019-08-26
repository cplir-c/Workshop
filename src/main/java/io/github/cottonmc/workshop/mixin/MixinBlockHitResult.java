package io.github.cottonmc.workshop.mixin;

import io.github.cottonmc.workshop.impl.HitPosGetter;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockHitResult.class)
public abstract class MixinBlockHitResult extends HitResult implements HitPosGetter {

	protected MixinBlockHitResult(Vec3d pos) {
		super(pos);
	}

	@Override
	public Vec3d workshop_getPos() {
		return pos;
	}
}
