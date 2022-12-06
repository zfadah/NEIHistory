package group.zfadah.neihistory.core.transformer;

import java.util.ListIterator;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class LayoutManagerTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {

        if (transformedName.contains("codechicken.nei.LayoutManager")) {
            return patchUpdateWidgetASM(transformedName, basicClass);
        }
        return basicClass;
    }

    private byte[] patchUpdateWidgetASM(String name, byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(bytes);
        cr.accept(classNode, 0);

        for (MethodNode method : classNode.methods) {
            if ("updateWidgetVisiblities".equals(method.name)
                    && "(Lnet/minecraft/client/gui/inventory/GuiContainer;Lcodechicken/nei/VisiblityData;)V"
                            .equals(method.desc)) {
                AbstractInsnNode cur;
                AbstractInsnNode tar = null;
                ListIterator<AbstractInsnNode> iterator = method.instructions.iterator();
                while (iterator.hasNext()) {
                    cur = iterator.next();
                    if (cur.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                        if (cur instanceof MethodInsnNode) {
                            MethodInsnNode cur1 = (MethodInsnNode) cur;
                            if ("codechicken/nei/ItemPanel".equals(cur1.owner)
                                    && "setVisible".equals(cur1.name)
                                    && "()V".equals(cur1.desc)) {
                                tar = cur;
                                break;
                            }
                        }
                    }
                }
                if (tar != null) {
                    InsnList insert = new InsnList();
                    insert.add(new FieldInsnNode(
                            Opcodes.GETSTATIC,
                            "group/zfadah/neihistory/HistoryInstance",
                            "historyPanel",
                            "Lgroup/zfadah/neihistory/history/HistoryPanel;"));
                    insert.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            "codechicken/nei/LayoutManager",
                            "addWidget",
                            "(Lcodechicken/nei/Widget;)V",
                            false));
                    method.instructions.insert(tar.getNext(), insert);
                }
            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
