package io.github.jodlodi.blight_star.client.renderers;

/*@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SandRenderer implements BlockEntityRenderer<SpectralSandBlock.Entity> {
	private static final ResourceLocation TEXTURE = BlightStar.prefix("textures/block/spectral_sand.png");
	private final BlockModel model;

	public SandRenderer(BlockEntityRendererProvider.Context context) {
		this.model = new BlockModel(context.bakeLayer(ModModelLayers.BLOCK));
	}

	@Override
	public void render(SpectralSandBlock.Entity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		poseStack.pushPose();
		VertexConsumer consumer = bufferSource.getBuffer(this.model.renderType(TEXTURE));
		this.model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, getColor(entity.getLevel(), partialTick));
		poseStack.popPose();
	}

	public static int getColor(@Nullable Level level, float partialTick) {
		if (level == null) return 0xFFFFFFFF;
		return Color.HSBtoRGB((level.getGameTime() + partialTick) / 360.0F, 1.0F, 1.0F);
	}
}*/
