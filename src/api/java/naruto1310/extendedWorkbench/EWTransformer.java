package naruto1310.extendedWorkbench;

import static naruto1310.extendedWorkbench.EWFMLPlugin.useCoremod;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.DLOAD;
import static org.objectweb.asm.Opcodes.DMUL;
import static org.objectweb.asm.Opcodes.DRETURN;
import static org.objectweb.asm.Opcodes.DSTORE;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class EWTransformer implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String newName, byte[] bytes)
	{
		if(useCoremod)
		{
			//increase the range of extended tools
			if(newName.contentEquals("net.minecraft.server.management.ItemInWorldManager"))		//make server allow this
				return transformItemInWorldManager(bytes);
		
			if(newName.contentEquals("net.minecraft.client.renderer.EntityRenderer"))			//make client do this
				return transformEntityRenderer(bytes);
		}
		
		//replace workbench and farmland with extended versions
		if(newName.contentEquals("net.minecraft.block.Block"))
			return transformBlock(bytes);
		
		return bytes;
	}
	
	private byte[] transformEntityRenderer(byte[] bytes)
    {
		FMLLog.fine("[ExtendedWorkbench] Starting to transform EntityRenderer.");
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        methods:
        for(MethodNode m : classNode.methods)
        {
            if(m.access == ACC_PUBLIC && m.desc.contentEquals("(F)V"))
            {
            	for(int i = 0; i < Math.min(m.instructions.size(), 10); i++)
            	{
            		AbstractInsnNode insn = m.instructions.get(i);
            		if(insn instanceof LdcInsnNode)
            			continue methods;
            		// verify it's not updateCameraAndRender method
            	}
            	
            	for(int i = 0; i < m.instructions.size(); i++)
                {
                	AbstractInsnNode insn = m.instructions.get(i);
                	if(insn instanceof VarInsnNode && insn.getOpcode() == DSTORE && ((VarInsnNode)insn).var == 2)
                	{
                		insn = m.instructions.get(i + 1);
                		m.instructions.insertBefore(insn, new VarInsnNode(DLOAD, 2));
                		m.instructions.insertBefore(insn, new MethodInsnNode(INVOKESTATIC, "naruto1310/extendedWorkbench/mod_ExtendedWorkbench", "extendReach", "(D)D"));
                		m.instructions.insertBefore(insn, new VarInsnNode(DSTORE, 2));
                		break;
                	}
                }
            	break;
            }
        }

		FMLLog.fine("[ExtendedWorkbench] Finished transforming EntityRenderer successfully.");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
	
	private byte[] transformItemInWorldManager(byte[] bytes)
    {
		FMLLog.fine("[ExtendedWorkbench] Starting to transform ItemInWorldManager.");
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        
        for(MethodNode m : classNode.methods)
        {
            if(m.access == ACC_PUBLIC && m.desc.contentEquals("()D")) 
            {
            	for(int i = 0; i < m.instructions.size(); i++)
                {
                	AbstractInsnNode insn = m.instructions.get(i);
                	if(insn.getOpcode() == DRETURN)
                	{
                		m.instructions.insertBefore(insn, new LdcInsnNode(new Double(mod_ExtendedWorkbench.extendedValues.increaseToolReach)));
                		m.instructions.insertBefore(insn, new InsnNode(DMUL));
                		break;
                	}
                }
            }
        }

		FMLLog.fine("[ExtendedWorkbench] Finished transforming ItemInWorldManager successfully.");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }

	private byte[] transformBlock(byte[] bytes)
	{
		FMLLog.fine("[ExtendedWorkbench] Starting to transform Block.");
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        
        for(MethodNode m : classNode.methods)
        {
        	//public static void ?
            if(m.access == (ACC_PUBLIC | ACC_STATIC) && m.desc.contentEquals("()V")) 
            {
            	for(int i = 0; i < m.instructions.size(); i++)
                {
                	AbstractInsnNode insn = m.instructions.get(i);
                	
                	replaceBlock(insn, "Workbench");
                	replaceBlock(insn, "Farmland");
                }
            }
        }

		FMLLog.fine("[ExtendedWorkbench] Finished transforming Block successfully.");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
	}
	
	private void replaceBlock(AbstractInsnNode insn, String block)
	{
		String source = "net/minecraft/block/Block" + block,
				sourceObf = FMLDeobfuscatingRemapper.INSTANCE.unmap(source),
				toReplace = "naruto1310/extendedWorkbench/block/BlockExtended" + block;
		
		if(insn instanceof TypeInsnNode && insn.getOpcode() == 187)
    		if(((TypeInsnNode)insn).desc.contentEquals(source) || ((TypeInsnNode)insn).desc.contentEquals(sourceObf))
    			((TypeInsnNode)insn).desc = toReplace;
    		
    	if(insn instanceof MethodInsnNode && insn.getOpcode() == 183)
    		if((((MethodInsnNode)insn).owner.contentEquals(source) || ((MethodInsnNode)insn).owner.contentEquals(sourceObf)))
    			((MethodInsnNode)insn).owner = toReplace;
	}
}
