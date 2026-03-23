package io.github.jodlodi.blight_star.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockModel extends Model {
	private final ModelPart cube;

	public BlockModel(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.cube = root.getChild("cube");
	}

	public static LayerDefinition createBlockLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F), PartPose.ZERO);
		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		this.cube.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}
