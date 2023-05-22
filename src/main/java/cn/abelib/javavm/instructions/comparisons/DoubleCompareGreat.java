package cn.abelib.javavm.instructions.comparisons;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/5 20:05
 */
public class DoubleCompareGreat extends NoOperandsInstruction implements DoubleCompare {
    @Override
    public void execute(Frame frame) {
        compare(frame, true);
    }
}
