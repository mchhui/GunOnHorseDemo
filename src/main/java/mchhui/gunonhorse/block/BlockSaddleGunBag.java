 package mchhui.gunonhorse.block;

import net.minecraft.world.level.block.Block;

//只是用来加载模型而已
public class BlockSaddleGunBag extends Block{

    public BlockSaddleGunBag(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(getStateDefinition().any());
    }

}
