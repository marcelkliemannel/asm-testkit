package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotation;
import dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.List;
import java.util.Map;

class TryCatchBlockNodeAssertTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testIsEqualToStart() {
    var firstTryCatchBlock = new TryCatchBlockNode(new LabelNode(), null, null, null);
    var secondTryCatchBlock = new TryCatchBlockNode(new LabelNode(), null, null, null);

    Map<Label, String> labelNames = Map.of(firstTryCatchBlock.start.getLabel(), "L1",
                                           secondTryCatchBlock.start.getLabel(), "L2");

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(firstTryCatchBlock)
                                                     .useLabelNames(labelNames)
                                                     .isEqualTo(secondTryCatchBlock))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Try Catch Block: finally, range: L1-null, handled in: null > Has equal start] \n" +
                          "expected: L2\n" +
                          " but was: L1\n" +
                          "when comparing values using LabelNodeComparator");
  }

  @Test
  void testIsEqualToEnd() {
    var firstTryCatchBlock = new TryCatchBlockNode(null, new LabelNode(), null, null);
    var secondTryCatchBlock = new TryCatchBlockNode(null, new LabelNode(), null, null);

    Map<Label, String> labelNames = Map.of(firstTryCatchBlock.end.getLabel(), "L1",
                                           secondTryCatchBlock.end.getLabel(), "L2");

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(firstTryCatchBlock)
                                                     .useLabelNames(labelNames)
                                                     .isEqualTo(secondTryCatchBlock))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Try Catch Block: finally, range: null-L1, handled in: null > Has equal end] \n" +
                          "expected: L2\n" +
                          " but was: L1\n" +
                          "when comparing values using LabelNodeComparator");
  }

  @Test
  void testIsEqualToHandler() {
    var firstTryCatchBlock = new TryCatchBlockNode(null, null, new LabelNode(), null);
    var secondTryCatchBlock = new TryCatchBlockNode(null, null, new LabelNode(), null);

    Map<Label, String> labelNames = Map.of(firstTryCatchBlock.handler.getLabel(), "L1",
                                           secondTryCatchBlock.handler.getLabel(), "L2");

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(firstTryCatchBlock)
                                                     .useLabelNames(labelNames)
                                                     .isEqualTo(secondTryCatchBlock))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Try Catch Block: finally, range: null-null, handled in: L1 > Has equal handler] \n" +
                          "expected: L2\n" +
                          " but was: L1\n" +
                          "when comparing values using LabelNodeComparator");
  }

  @Test
  void testIsEqualToType() {
    var firstTryCatchBlock = new TryCatchBlockNode(null, null, null, "java/io/IOException");
    var secondTryCatchBlock = new TryCatchBlockNode(null, null, null, "java/lang/IllegalArgumentException");

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(firstTryCatchBlock)
                                                     .isEqualTo(secondTryCatchBlock))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Try Catch Block: java.io.IOException, range: null-null, handled in: null > Has equal type] \n" +
                          "expected: \"java.lang.IllegalArgumentException\"\n" +
                          " but was: \"java.io.IOException\"");
  }

  @Test
  void testIsEqualToVisibleTypeAnnotations() {
    var firstTryCatchBlock = new TryCatchBlockNode(null, null, null, null);
    firstTryCatchBlock.visibleTypeAnnotations = List.of(new TypeAnnotationNode(1, TypePath.fromString("*"), Type.getDescriptor(VisibleTypeParameterAnnotationA.class)));
    var secondTryCatchBlock = new TryCatchBlockNode(null, null, null, null);
    secondTryCatchBlock.visibleTypeAnnotations = List.of(new TypeAnnotationNode(1, TypePath.fromString("[2"), Type.getDescriptor(VisibleTypeParameterAnnotationA.class)));

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(firstTryCatchBlock)
                                                     .isEqualTo(secondTryCatchBlock))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Try Catch Block: finally, range: null-null, handled in: null > Has equal visible type annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=0; path: *]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=0; path: [2;]\n" +
                          "elements not found:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=0; path: [2;]\n" +
                          "and elements not expected:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.VisibleTypeParameterAnnotationA // reference: class_type_parameter=0; path: *]\n" +
                          "when comparing values using TypeAnnotationNodeComparator");
  }

  @Test
  void testIsEqualToInvisibleTypeAnnotations() {
    var firstTryCatchBlock = new TryCatchBlockNode(null, null, null, null);
    firstTryCatchBlock.invisibleTypeAnnotations = List.of(new TypeAnnotationNode(1, TypePath.fromString("*"), Type.getDescriptor(InvisibleTypeParameterAnnotation.class)));
    var secondTryCatchBlock = new TryCatchBlockNode(null, null, null, null);
    secondTryCatchBlock.invisibleTypeAnnotations = List.of(new TypeAnnotationNode(1, TypePath.fromString("[2"), Type.getDescriptor(InvisibleTypeParameterAnnotation.class)));

    Assertions.assertThatThrownBy(() -> AsmAssertions.assertThat(firstTryCatchBlock)
                                                     .isEqualTo(secondTryCatchBlock))
              .isInstanceOf(AssertionError.class)
              .hasMessage("[Try Catch Block: finally, range: null-null, handled in: null > Has equal visible type annotations] \n" +
                          "Expecting actual:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotation // reference: class_type_parameter=0; path: *]\n" +
                          "to contain exactly in any order:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotation // reference: class_type_parameter=0; path: [2;]\n" +
                          "elements not found:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotation // reference: class_type_parameter=0; path: [2;]\n" +
                          "and elements not expected:\n" +
                          "  [@dev.turingcomplete.asmtestkit.assertion.__helper.InvisibleTypeParameterAnnotation // reference: class_type_parameter=0; path: *]\n" +
                          "when comparing values using TypeAnnotationNodeComparator");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
