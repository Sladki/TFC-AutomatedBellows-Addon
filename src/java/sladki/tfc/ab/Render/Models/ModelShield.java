package sladki.tfc.ab.Render.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelShield extends ModelBase {
		
	private ModelRenderer shield;

	public ModelShield() {
		super();
		
		shield = new ModelRenderer(this, 0, 0);
		
		shield.addBox(-8.0f, -8.0f, 0.0f, 16, 16, 1);		
		shield.setTextureSize(64, 32);
	}
	
	public void render() {
		shield.render(0.0625F);
	}	
}
