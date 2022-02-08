package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.node.AccessNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.stream.Stream;

import static dev.turingcomplete.asmtestkit.representation.AccessNodeRepresentation.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AccessNodeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @MethodSource("createAccessFlagsTestArguments")
  void testToStringOf(AccessNode accessNode, List<String> expected) {
    assertThat(INSTANCE.toStringOf(accessNode))
            .isEqualTo("[" + accessNode.access() + ": " + String.join(", ", expected) + "]");
  }

  @ParameterizedTest
  @MethodSource("createAccessFlagsTestArguments")
  void testToJavaSourceCodeRepresentation(AccessNode accessNode, List<String> expected) {
    assertThat(INSTANCE.toJavaSourceCodeRepresentation(accessNode))
            .isEqualTo(String.join(" ", expected));
  }
  
  @Test
  void testInvalidAccessToStringOf() {
    assertThat(INSTANCE.toStringOf(AccessNode.create(10, AccessKind.PARAMETER)))
            .isEqualTo("[10]");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static Stream<Arguments> createAccessFlagsTestArguments() {
    return Stream.of(Arguments.of(AccessNode.forClass(Opcodes.ACC_PUBLIC + Opcodes.ACC_INTERFACE), List.of("public", "interface"),
                                  Arguments.of(AccessNode.forClass(Opcodes.ACC_INTERFACE + Opcodes.ACC_PUBLIC), List.of("public", "interface")), // Wrong order to test sort
                                  Arguments.of(AccessNode.forField(Opcodes.ACC_TRANSIENT + Opcodes.ACC_PUBLIC), List.of("public", "transient")),
                                  Arguments.of(AccessNode.forMethod(Opcodes.ACC_NATIVE + Opcodes.ACC_PUBLIC), List.of("public", "native")),
                                  Arguments.of(AccessNode.forParameter(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED), List.of("synthetic", "mandated")),
                                  Arguments.of(AccessNode.forModule(Opcodes.ACC_OPEN + Opcodes.ACC_MANDATED), List.of("open", "mandated")),
                                  Arguments.of(AccessNode.forModuleRequires(Opcodes.ACC_TRANSITIVE + Opcodes.ACC_STATIC_PHASE), List.of("transitive, static_phase")),
                                  Arguments.of(AccessNode.forModuleOpens(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED), List.of("synthetic", "mandated")),
                                  Arguments.of(AccessNode.forModuleExports(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED), List.of("synthetic", "mandated"))));
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
