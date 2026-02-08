package me.x150.renderer.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.util.Identifier;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Helper for registering a {@link GpuTextureView} as a texture that can be referenced
 * by {@link net.minecraft.client.render.RenderSetup.Builder#texture}.
 */
public class GlIdTexturing {
	private static final AtomicInteger NEXT_ID = new AtomicInteger();

	private final GpuTextureView glTex;
	private final Identifier id;

	/**
	 * Constructor.
	 * @param glId GpuTexture to bind to texture 0
	 * @param linear Bilinear sampling? NEAREST otherwise
	 */
	public GlIdTexturing(GpuTextureView glId, boolean linear) {
		this.glTex = glId;
		this.id = Identifier.of("renderer", "gpu/" + NEXT_ID.incrementAndGet());
		MinecraftClient.getInstance().getTextureManager().registerTexture(this.id, new ExternalTexture(glId, linear));
	}

	/**
	 * @return Identifier registered for this texture.
	 */
	public Identifier getId() {
		return id;
	}

	/**
	 * @return Underlying texture view.
	 */
	public GpuTextureView getTextureView() {
		return glTex;
	}

	@Override
	public String toString() {
		return "GlIdTexturing[" + this.id + "]";
	}

	private static final class ExternalTexture extends AbstractTexture {
		private ExternalTexture(GpuTextureView view, boolean linear) {
			this.glTextureView = view;
			this.glTexture = view.texture();
			FilterMode filter = linear ? FilterMode.LINEAR : FilterMode.NEAREST;
			this.sampler = RenderSystem.getSamplerCache().get(filter);
		}

		@Override
		public void close() {
			// no-op: we do not own the underlying GPU texture
		}
	}
}
