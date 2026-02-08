package me.x150.renderer.util;

import com.mojang.blaze3d.systems.RenderPass;
import net.minecraft.client.render.RenderLayer;

import java.util.function.Consumer;

/**
 * A duck interface for {@link RenderLayer}, allowing to specify a custom action to apply to the {@link RenderPass} before drawing.
 */
public interface MoreMultiPhase {
	/**
	 * Specifies an action to apply to a {@link RenderPass} before drawing happens.
	 * @param rp Action to apply
	 * @return this
	 */
	RenderLayer withRenderPassSetup(Consumer<RenderPass> rp);

	/**
	 * Helper function to hide the ugly cast.
	 * @param layer RenderLayer to convert
	 * @return MoreMultiPhase for the given RenderLayer
	 */
	static MoreMultiPhase moreOptions(RenderLayer layer) {
		return ((MoreMultiPhase) (Object) layer);
	}
}
