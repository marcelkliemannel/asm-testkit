package dev.turingcomplete.asmtestkit.assertion.representation._internal;

import dev.turingcomplete.asmtestkit.assertion.representation.AnnotationNodeRepresentation;
import dev.turingcomplete.asmtestkit.assertion.representation.TypeAnnotationNodeRepresentation;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.util.ArrayList;
import java.util.List;

public final class RepresentationUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final String INVISIBLE_POSTFIX = " // invisible";

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private RepresentationUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public static List<String> createAnnotationNodesRepresentations(AnnotationNodeRepresentation annotationNodeRepresentation,
                                                                  TypeAnnotationNodeRepresentation typeAnnotationNodeRepresentation,
                                                                  List<AnnotationNode> visibleAnnotations,
                                                                  List<AnnotationNode> invisibleAnnotations,
                                                                  List<TypeAnnotationNode> typeVisibleAnnotations,
                                                                  List<TypeAnnotationNode> typeInvisibleAnnotations) {

    List<String> representations = new ArrayList<>();

    if (visibleAnnotations != null) {
      for (AnnotationNode annotationNode : visibleAnnotations) {
        representations.add(annotationNodeRepresentation.toStringOf(annotationNode));
      }
    }

    if (invisibleAnnotations != null) {
      for (AnnotationNode annotationNode : invisibleAnnotations) {
        representations.add(annotationNodeRepresentation.toStringOf(annotationNode) + INVISIBLE_POSTFIX);
      }
    }

    if (typeVisibleAnnotations != null) {
      for (AnnotationNode annotationNode : typeVisibleAnnotations) {
        representations.add(typeAnnotationNodeRepresentation.toStringOf(annotationNode));
      }
    }

    if (typeInvisibleAnnotations != null) {
      for (AnnotationNode annotationNode : typeInvisibleAnnotations) {
        representations.add(typeAnnotationNodeRepresentation.toStringOf(annotationNode) + INVISIBLE_POSTFIX);
      }
    }

    return representations;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
