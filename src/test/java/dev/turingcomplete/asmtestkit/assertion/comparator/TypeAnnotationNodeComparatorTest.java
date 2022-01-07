package dev.turingcomplete.asmtestkit.assertion.comparator;

import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.io.IOException;
import java.util.List;

import static dev.turingcomplete.asmtestkit.assertion.comparator.TypeAnnotationNodeComparator.INSTANCE;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;

class TypeAnnotationNodeComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() throws IOException {
    @Language("Java")
    String typeParameterAnnotation = "import java.lang.annotation.*;" +
                                     "@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})\n" +
                                     "@Retention(RetentionPolicy.RUNTIME)\n" +
                                     "@interface TypeParameterAnnotation { }";
    @Language("Java")
    String myClass = "class MyClass<@TypeParameterAnnotation S, @TypeParameterAnnotation T> { }";

    List<TypeAnnotationNode> visibleTypeAnnotations = create()
            .addJavaInputSource(typeParameterAnnotation)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .visibleTypeAnnotations;

    TypeAnnotationNode firstTypeAnnotationNode = visibleTypeAnnotations.get(0);
    TypeAnnotationNode secondTypeAnnotationNode = visibleTypeAnnotations.get(1);

    //noinspection EqualsWithItself
    assertThat(INSTANCE.compare(firstTypeAnnotationNode, firstTypeAnnotationNode))
              .isEqualTo(0);

    assertThat(INSTANCE.compare(firstTypeAnnotationNode, secondTypeAnnotationNode))
            .isLessThanOrEqualTo(-1);

    assertThat(INSTANCE.compare(secondTypeAnnotationNode, firstTypeAnnotationNode))
            .isGreaterThanOrEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
