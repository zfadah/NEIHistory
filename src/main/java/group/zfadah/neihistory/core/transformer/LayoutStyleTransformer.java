package group.zfadah.neihistory.core.transformer;

import java.util.ListIterator;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class LayoutStyleTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {

        if (transformedName.contains("codechicken.nei.LayoutStyleMinecraft")) {
            return patchLayoutASM(transformedName, basicClass);
        }
        return basicClass;
    }

    public byte[] patchLayoutASM(String name, byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(bytes);
        cr.accept(classNode, 0);

        for (MethodNode method : classNode.methods) {
            if ("layout".equals(method.name)
                    && "(Lnet/minecraft/client/gui/inventory/GuiContainer;Lcodechicken/nei/VisiblityData;)V"
                            .equals(method.desc)) {
                AbstractInsnNode cur;
                AbstractInsnNode tar = null;
                ListIterator<AbstractInsnNode> iterator = method.instructions.iterator();
                while (iterator.hasNext()) {
                    cur = iterator.next();
                    if (cur.getOpcode() >= Opcodes.IRETURN && cur.getOpcode() <= Opcodes.RETURN
                            || cur.getOpcode() == Opcodes.ATHROW) {
                        tar = cur.getPrevious();
                        break;
                    }
                }
                InsnList insert = new InsnList();
                insert.add(new FieldInsnNode(
                        Opcodes.GETSTATIC,
                        "group/zfadah/neihistory/HistoryInstance",
                        "historyPanel",
                        "Lgroup/zfadah/neihistory/history/HistoryPanel;"));
                insert.add(new VarInsnNode(Opcodes.ALOAD, 1));
                insert.add(new MethodInsnNode(
                        Opcodes.INVOKEVIRTUAL,
                        "group/zfadah/neihistory/history/HistoryPanel",
                        "resize",
                        "(Lnet/minecraft/client/gui/inventory/GuiContainer;)V",
                        false));
                if (tar != null) {
                    method.instructions.insert(tar, insert);
                }
                break;
            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
