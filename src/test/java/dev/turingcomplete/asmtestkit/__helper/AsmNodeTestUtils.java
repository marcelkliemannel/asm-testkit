package dev.turingcomplete.asmtestkit.__helper;

import org.assertj.core.util.Streams;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LineNumberNode;

public class AsmNodeTestUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  
  private AsmNodeTestUtils() {
    throw new UnsupportedOperationException();
  }
  
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //
  
  public static long countLineNumbers(InsnList insnList) {
    return Streams.stream(insnList).filter(LineNumberNode.class::isInstance).count();
  }
  
  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
