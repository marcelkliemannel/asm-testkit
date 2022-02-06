package dev.turingcomplete.asmtestkit.representation._internal;

import dev.turingcomplete.asmtestkit.representation.AsmRepresentations;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class RepresentationUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final String INVISIBLE_POSTFIX = " // invisible";

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private RepresentationUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public static List<String> createAttributesRepresentations(AsmRepresentations asmRepresentationsProvider, List<Attribute> attributes) {
    if (attributes == null) {
      return List.of();
    }

    return attributes.stream().map(asmRepresentationsProvider::toStringOf).collect(Collectors.toList());
  }

  public static String prependToFirstLine(String toPrepend, String text) {
    Objects.requireNonNull(text);
    Objects.requireNonNull(toPrepend);

    int indexOfFirstNewLine = text.indexOf("\n");
    if (indexOfFirstNewLine >= 0) {
      return toPrepend + text.replaceAll("\n", "\n" + " ".repeat(toPrepend.length()));
    }
    else {
      return toPrepend + text;
    }
  }

  public static String appendToFirstLine(String toAppend, String text) {
    Objects.requireNonNull(text);
    Objects.requireNonNull(toAppend);

    int indexOfFirstNewLine = text.indexOf("\n");
    if (indexOfFirstNewLine >= 0) {
      // In case the instruction has multiple line, the opcode will be
      // appended to the first one.
      return text.replaceFirst("\n", toAppend + "\n");
    }
    else {
      return text + toAppend;
    }
  }

  public static List<String> createAnnotationNodesRepresentations(AsmRepresentations asmRepresentationsProvider,
                                                                  List<AnnotationNode> visibleAnnotations,
                                                                  List<AnnotationNode> invisibleAnnotations) {

    List<String> representations = new ArrayList<>();

    if (visibleAnnotations != null) {
      for (AnnotationNode annotationNode : visibleAnnotations) {
        representations.add(asmRepresentationsProvider.toStringOf(annotationNode));
      }
    }

    if (invisibleAnnotations != null) {
      for (AnnotationNode annotationNode : invisibleAnnotations) {
        representations.add(asmRepresentationsProvider.toStringOf(annotationNode) + INVISIBLE_POSTFIX);
      }
    }

    return representations;
  }

  public static List<String> createTypeAnnotationNodesRepresentations(AsmRepresentations asmRepresentationsProvider,
                                                                      List<TypeAnnotationNode> typeVisibleAnnotations,
                                                                      List<TypeAnnotationNode> typeInvisibleAnnotations) {

    List<String> representations = new ArrayList<>();

    if (typeVisibleAnnotations != null) {
      for (AnnotationNode annotationNode : typeVisibleAnnotations) {
        representations.add(asmRepresentationsProvider.toStringOf(annotationNode));
      }
    }

    if (typeInvisibleAnnotations != null) {
      for (AnnotationNode annotationNode : typeInvisibleAnnotations) {
        representations.add(asmRepresentationsProvider.toStringOf(annotationNode) + INVISIBLE_POSTFIX);
      }
    }

    return representations;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
