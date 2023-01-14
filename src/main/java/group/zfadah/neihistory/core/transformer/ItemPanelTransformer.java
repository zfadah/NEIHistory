package group.zfadah.neihistory.core.transformer;

import static org.objectweb.asm.Opcodes.*;

import java.util.ListIterator;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ItemPanelTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {

        if (transformedName.contains("codechicken.nei.ItemPanel")) {
            return patchItemPanelASM(name, basicClass);
        }
        return basicClass;
    }

    public byte[] patchItemPanelASM(String name, byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(bytes);
        cr.accept(classNode, 0);
        for (MethodNode m : classNode.methods) {
            if ("getHeight".equals(m.name) && "(Lnet/minecraft/client/gui/inventory/GuiContainer;)I".equals(m.desc)) {
                AbstractInsnNode currentNode;
                AbstractInsnNode targetNode = null;

                ListIterator<AbstractInsnNode> iterator1 = m.instructions.iterator();
                while (iterator1.hasNext()) {
                    currentNode = iterator1.next();
                    if (currentNode.getOpcode() >= Opcodes.IRETURN && currentNode.getOpcode() <= Opcodes.RETURN
                            || currentNode.getOpcode() == ATHROW) {
                        targetNode = currentNode;
                        break;
                    }
                }
                InsnList inject = new InsnList();
                inject.add(new VarInsnNode(BIPUSH, 54));
                inject.add(new InsnNode(ISUB));
                if (targetNode != null) {
                    targetNode = targetNode.getPrevious();
                    m.instructions.insert(targetNode, inject);
                }
                break;
            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
