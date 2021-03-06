package ml.rederpz.betterlocalserver.mixin.impl.screen;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Rederpz on Jan 22, 2021
 */
@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {

    protected GameMenuScreenMixin(final Text title) {
        super(title);
    }

    @Inject(method = "initWidgets", at = @At("TAIL"))
    private void initWidgets(final CallbackInfo callbackInfo) {
        for (final AbstractButtonWidget widget : this.buttons) {
            final Text text = widget.getMessage();

            if (text instanceof TranslatableText) {
                final TranslatableText translatableText = (TranslatableText) text;

                if (translatableText.getKey().equals("menu.shareToLan")) {
                    if (this.client != null && this.client.isIntegratedServerRunning()) {
                        widget.active = true;

                        if (this.client.getServer() != null && this.client.getServer().isRemote()) {
                            widget.setMessage(new TranslatableText("menu.localServer.options"));
                            break;
                        }
                    }

                    widget.setMessage(new TranslatableText("menu.localServer.start"));
                    break;
                }
            }
        }
    }
}
