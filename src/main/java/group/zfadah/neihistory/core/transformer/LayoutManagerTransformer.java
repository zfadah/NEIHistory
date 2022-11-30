package group.zfadah.neihistory.core.transformer;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                method.instructions.insert(method.instructions.get(94), insert);
            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        byte[] bytes1 = writer.toByteArray();
        try {
            OutputStream os = Files.newOutputStream(Paths.get("D://LayoutManager.class"), CREATE_NEW);
            os.write(bytes1);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes1;
    }
}
