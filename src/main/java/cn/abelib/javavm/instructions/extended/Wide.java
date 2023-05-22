package cn.abelib.javavm.instructions.extended;

import cn.abelib.javavm.instructions.base.BytecodeReader;
import cn.abelib.javavm.instructions.base.Index8Instruction;
import cn.abelib.javavm.instructions.base.Instruction;
import cn.abelib.javavm.instructions.loads.IntegerLoad;
import cn.abelib.javavm.instructions.maths.IntegerIncrement;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/7 0:00
 */
public class Wide implements Instruction {
    private Instruction modifiedInstruction;
    @Override
    public void fetchOperands(BytecodeReader reader) {
        int opcode = reader.readUInt8();
        switch (opcode)  {
            // iload
            case 0x15: {
                Index8Instruction inst = new IntegerLoad();
                inst.setIndex(reader.readInt16());
                this.modifiedInstruction = inst;
            }
            // lload
            case 0x16: {

            }
            // fload
            case 0x17: {

            }
            // dload
            case 0x18: {

            }
            // aload
            case 0x19: {

            }
            // istore
            case 0x36: {

            }
            // lstore
            case 0x37: {

            }
            // fstore
            case 0x38: {

            }
            // dstore
            case 0x39: {

            }
            // astore
            case 0x3a: {

            }
            // iinc
            case 0x84: {
                IntegerIncrement inst = new IntegerIncrement();
                inst.setIndex(reader.readInt16());
                inst.setConstant(reader.readInt16());
                this.modifiedInstruction = inst;
            }
            // ret
            case 0xa9:
                throw new RuntimeException("Unsupported opcode: 0xa9!");
        }
    }

    @Override
    public void execute(Frame frame) {
        this.modifiedInstruction.execute(frame);
    }
}
