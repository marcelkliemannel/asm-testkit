package dev.turingcomplete.asmtestkit.asmutils;

import org.assertj.core.util.Arrays;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class AnnotationNodeUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private AnnotationNodeUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a new {@link AnnotationNode} for the given {@link Annotation} and
   * values.
   *
   * @param annotationClass the annotation {@link Class}; never null.
   * @param values          an optional array with key value pairs (the first
   *                        element is the name and the second the value); may be null.
   * @return a new {@link AnnotationNode}; never null.
   */
  public static AnnotationNode createAnnotationNode(Class<? extends Annotation> annotationClass, Object... values) {
    var annotationNode = new AnnotationNode(Type.getDescriptor(Objects.requireNonNull(annotationClass)));
    if (values != null) {
      annotationNode.values = Arrays.asList(values);
    }
    return annotationNode;
  }

  /**
   * Converts an ASM annotation values list (the first element is the key and
   * the second one is the value) to a {@link Map}.
   *
   * @param values a {@link List} of key-value pairs
   *               (from {@link AnnotationNode#values}); may be null.
   * @return a {@link Map} representation of the given values; never null.
   * @throws IllegalArgumentException if {@code values} has an odd number of
   *                                  elements.
   */
  public static Map<Object, Object> convertAnnotationNodeValuesToMap(List<Object> values) {
    if (values == null || values.size() == 0) {
      return Collections.emptyMap();
    }

    if (values.size() % 2 != 0) {
      throw new IllegalArgumentException("There must be an even number of values.");
    }

    Map<Object, Object> result = new HashMap<>();
    for (int i = 0; i < values.size(); i = i + 2) {
      result.put(values.get(i), values.get(i + 1));
    }
    return result;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
