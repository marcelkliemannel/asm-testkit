package dev.turingcomplete.asmtestkit.assertion;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.assertion.option.AssertOption;
import dev.turingcomplete.asmtestkit.assertion.option.StandardAssertOption;
import dev.turingcomplete.asmtestkit.assertion.representation.FieldNodeRepresentation;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.List;
import java.util.Optional;

import static dev.turingcomplete.asmtestkit.assertion.AsmAssertions.assertThat;
import static dev.turingcomplete.asmtestkit.assertion._internal.AssertUtils.getFromObjectElseNull;
import static org.assertj.core.api.Assertions.assertThat;

public class FieldNodeAssert extends ClassEntityAssert<FieldNodeAssert, FieldNode> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  /**
   * Initializes a {@link FieldNodeAssert}.
   *
   * <p>The supported {@link AssertOption}s are:
   * <ul>
   *  <li>{@link StandardAssertOption#IGNORE_NAME}
   *  <li>{@link StandardAssertOption#IGNORE_DESCRIPTOR}
   *  <li>{@link StandardAssertOption#IGNORE_ACCESS}
   *  <li>{@link StandardAssertOption#IGNORE_SIGNATURE}
   *  <li>{@link StandardAssertOption#IGNORE_VALUE}
   *  <li>{@link StandardAssertOption#IGNORE_VISIBLE_ANNOTATIONS}
   *  <li>{@link StandardAssertOption#IGNORE_INVISIBLE_ANNOTATIONS}
   *  <li>{@link StandardAssertOption#IGNORE_VISIBLE_TYPE_ANNOTATIONS}
   *  <li>{@link StandardAssertOption#IGNORE_INVISIBLE_TYPE_ANNOTATIONS}
   *  <li>{@link StandardAssertOption#IGNORE_ATTRIBUTES}
   * </ul>
   *
   * @param actual        the actual {@link FieldNode}; may be null.
   * @param assertOptions an array of {@link AssertOption}s; never null.
   */
  public FieldNodeAssert(FieldNode actual, AssertOption... assertOptions) {
    super("field", actual, FieldNodeAssert.class, FieldNode.class, createSelfDescription(actual), assertOptions);

    info.useRepresentation(FieldNodeRepresentation.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public FieldNodeAssert isEqualTo(Object expected) {
    super.isEqualTo(expected);

    isEqualDescriptor(expected);
    isEqualValue(expected);

    return this;
  }

  /**
   * Checks whether the descriptor of the given expected {@link FieldNode} is
   * equal to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link FieldNode}; may
   *                 be null.
   */
  protected void isEqualDescriptor(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_DESCRIPTOR)) {
      return;
    }

    assertThat(getFromObjectElseNull(actual, (FieldNode fieldNode) -> Type.getType(fieldNode.desc)))
            .addOptions(options)
            .as(createDescription("Is equal field descriptor"))
            .isEqualTo(getFromObjectElseNull(expected, FieldNode.class, fieldNode -> Type.getType(fieldNode.desc)));
  }

  /**
   * Checks whether the value of the given expected {@link FieldNode} is equal
   * to the actual one.
   *
   * @param expected an {@link Object} expected to be a {@link FieldNode}; may
   *                 be null.
   */
  protected void isEqualValue(Object expected) {
    if (hasOption(StandardAssertOption.IGNORE_VALUE)) {
      return;
    }

    assertThat(getFromObjectElseNull(actual, (FieldNode fieldNode) -> fieldNode.value))
            .as(createDescription("Is equal field value"))
            .isEqualTo(getFromObjectElseNull(expected, FieldNode.class, fieldNode -> fieldNode.value));
  }

  @Override
  protected String getName(FieldNode fieldNode) {
    return fieldNode.name;
  }

  @Override
  protected Integer getAccess(FieldNode fieldNode) {
    return fieldNode.access;
  }

  @Override
  protected AccessKind getAccessKind() {
    return AccessKind.FIELD;
  }

  @Override
  protected String getSignature(FieldNode fieldNode) {
    return fieldNode.signature;
  }

  @Override
  protected List<AnnotationNode> getVisibleAnnotations(FieldNode fieldNode) {
    return fieldNode.visibleAnnotations;
  }

  @Override
  protected List<AnnotationNode> getInvisibleAnnotations(FieldNode fieldNode) {
    return fieldNode.invisibleAnnotations;
  }

  @Override
  protected List<TypeAnnotationNode> getVisibleTypeAnnotations(FieldNode fieldNode) {
    return fieldNode.visibleTypeAnnotations;
  }

  @Override
  protected List<TypeAnnotationNode> getInvisibleTypeAnnotations(FieldNode fieldNode) {
    return fieldNode.invisibleTypeAnnotations;
  }

  @Override
  protected List<Attribute> getAttributes(FieldNode fieldNode) {
    return fieldNode.attrs;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static String createSelfDescription(FieldNode actual) {
    return "Field: " + Optional.ofNullable(actual).map(fieldNode -> fieldNode.name).orElse(null);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
