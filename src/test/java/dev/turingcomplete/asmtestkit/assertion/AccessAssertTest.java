package dev.turingcomplete.asmtestkit.assertion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatClassAccess;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatFieldAccess;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatMethodAccess;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatModuleAccess;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatModuleExportsAccess;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatModuleOpensAccess;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatModuleRequiresAccess;
import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThatParameterAccess;

class AccessAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testClassAccess() {
    assertThatClassAccess(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT)
            .isEqualTo(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT);

    Assertions.assertThatThrownBy(() -> assertThatClassAccess(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT)
                      .isEqualTo(Opcodes.ACC_ABSTRACT + Opcodes.ACC_FINAL))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Access: (1025) public abstract > Has equal access values] \n" +
                          "Expecting actual:\n" +
                          "  [\"public\", \"abstract\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"final\", \"abstract\"]\n" +
                          "elements not found:\n" +
                          "  [\"final\"]\n" +
                          "and elements not expected:\n" +
                          "  [\"public\"]\n");
  }

  @Test
  void testFieldAccess() {
    assertThatFieldAccess(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC)
            .isEqualTo(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC);

    Assertions.assertThatThrownBy(() -> assertThatFieldAccess(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC)
                      .isEqualTo(Opcodes.ACC_STATIC + Opcodes.ACC_FINAL))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Access: (9) public static > Has equal access values] \n" +
                          "Expecting actual:\n" +
                          "  [\"public\", \"static\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"static\", \"final\"]\n" +
                          "elements not found:\n" +
                          "  [\"final\"]\n" +
                          "and elements not expected:\n" +
                          "  [\"public\"]\n");
  }

  @Test
  void testMethodAccess() {
    assertThatMethodAccess(Opcodes.ACC_PUBLIC + Opcodes.ACC_NATIVE)
            .isEqualTo(Opcodes.ACC_PUBLIC + Opcodes.ACC_NATIVE);

    Assertions.assertThatThrownBy(() -> assertThatMethodAccess(Opcodes.ACC_PUBLIC + Opcodes.ACC_NATIVE)
                      .isEqualTo(Opcodes.ACC_NATIVE + Opcodes.ACC_FINAL))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Access: (257) public native > Has equal access values] \n" +
                          "Expecting actual:\n" +
                          "  [\"public\", \"native\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"final\", \"native\"]\n" +
                          "elements not found:\n" +
                          "  [\"final\"]\n" +
                          "and elements not expected:\n" +
                          "  [\"public\"]\n");
  }

  @Test
  void testParameterAccess() {
    assertThatParameterAccess(Opcodes.ACC_FINAL + Opcodes.ACC_SYNTHETIC)
            .isEqualTo(Opcodes.ACC_FINAL + Opcodes.ACC_SYNTHETIC);

    Assertions.assertThatThrownBy(() -> assertThatParameterAccess(Opcodes.ACC_FINAL + Opcodes.ACC_SYNTHETIC)
                      .isEqualTo(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Access: (4112) final synthetic > Has equal access values] \n" +
                          "Expecting actual:\n" +
                          "  [\"final\", \"synthetic\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"synthetic\", \"mandated\"]\n" +
                          "elements not found:\n" +
                          "  [\"mandated\"]\n" +
                          "and elements not expected:\n" +
                          "  [\"final\"]\n");
  }

  @Test
  void testModuleAccess() {
    assertThatModuleAccess(Opcodes.ACC_OPEN + Opcodes.ACC_MANDATED)
            .isEqualTo(Opcodes.ACC_OPEN + Opcodes.ACC_MANDATED);

    Assertions.assertThatThrownBy(() -> assertThatModuleAccess(Opcodes.ACC_OPEN + Opcodes.ACC_MANDATED)
                      .isEqualTo(Opcodes.ACC_MANDATED + Opcodes.ACC_SYNTHETIC))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Access: (32800) open mandated > Has equal access values] \n" +
                          "Expecting actual:\n" +
                          "  [\"open\", \"mandated\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"synthetic\", \"mandated\"]\n" +
                          "elements not found:\n" +
                          "  [\"synthetic\"]\n" +
                          "and elements not expected:\n" +
                          "  [\"open\"]\n");
  }

  @Test
  void testModuleRequiresAccess() {
    assertThatModuleRequiresAccess(Opcodes.ACC_TRANSITIVE + Opcodes.ACC_STATIC_PHASE)
            .isEqualTo(Opcodes.ACC_TRANSITIVE + Opcodes.ACC_STATIC_PHASE);

    Assertions.assertThatThrownBy(() -> assertThatModuleRequiresAccess(Opcodes.ACC_TRANSITIVE + Opcodes.ACC_STATIC_PHASE)
                      .isEqualTo(Opcodes.ACC_STATIC_PHASE + Opcodes.ACC_SYNTHETIC))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Access: (96) transitive static_phase > Has equal access values] \n" +
                          "Expecting actual:\n" +
                          "  [\"transitive\", \"static_phase\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"static_phase\", \"synthetic\"]\n" +
                          "elements not found:\n" +
                          "  [\"synthetic\"]\n" +
                          "and elements not expected:\n" +
                          "  [\"transitive\"]\n");
  }

  @Test
  void testModuleExportsAccess() {
    assertThatModuleExportsAccess(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED)
            .isEqualTo(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED);

    Assertions.assertThatThrownBy(() -> assertThatModuleExportsAccess(Opcodes.ACC_SYNTHETIC)
                      .isEqualTo(Opcodes.ACC_MANDATED))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Access: (4096) synthetic > Has equal access values] \n" +
                          "Expecting actual:\n" +
                          "  [\"synthetic\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"mandated\"]\n" +
                          "elements not found:\n" +
                          "  [\"mandated\"]\n" +
                          "and elements not expected:\n" +
                          "  [\"synthetic\"]\n");
  }

  @Test
  void testModuleOpensAccess() {
    assertThatModuleOpensAccess(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED)
            .isEqualTo(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED);

    Assertions.assertThatThrownBy(() -> assertThatModuleOpensAccess(Opcodes.ACC_SYNTHETIC)
                      .isEqualTo(Opcodes.ACC_MANDATED))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Access: (4096) synthetic > Has equal access values] \n" +
                          "Expecting actual:\n" +
                          "  [\"synthetic\"]\n" +
                          "to contain exactly in any order:\n" +
                          "  [\"mandated\"]\n" +
                          "elements not found:\n" +
                          "  [\"mandated\"]\n" +
                          "and elements not expected:\n" +
                          "  [\"synthetic\"]\n");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
