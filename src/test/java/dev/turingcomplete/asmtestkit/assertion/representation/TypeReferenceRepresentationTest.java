package dev.turingcomplete.asmtestkit.assertion.representation;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.TypeReference;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static dev.turingcomplete.asmtestkit.assertion.representation.TypeReferenceRepresentation.INSTANCE;
import static java.lang.reflect.Modifier.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;

class TypeReferenceRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testStringRepresentation() {
    List<String> sortFields = Arrays.stream(TypeReference.class.getFields())
                                    .filter(field -> field.getType().equals(int.class) && (field.getModifiers() & PUBLIC) != 0)
                                    .map(Field::getName)
                                    .collect(Collectors.toList());

    assertThat(sortFields)
            .hasSizeGreaterThanOrEqualTo(15)
            .allSatisfy(sortField -> {
              int sort = (int) TypeReference.class.getField(sortField).get(null);
              assertThat(INSTANCE.toStringOf(new TypeReference(sort << 24)))
                      .startsWith(sortField.toLowerCase(Locale.ROOT));
            });
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
