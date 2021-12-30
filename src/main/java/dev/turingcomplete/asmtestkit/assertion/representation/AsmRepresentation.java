package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.presentation.StandardRepresentation;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.Textifier;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AsmRepresentation<T> extends StandardRepresentation {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Class<T> nodeClass;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected AsmRepresentation(Class<T> nodeClass) {
    this.nodeClass = Objects.requireNonNull(nodeClass);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Creates a {@link String} representation of the given {@code object}.
   *
   * @param object the {@link T} object whose representation is to be created;
   *               never null.
   * @return the {@link String} representation; never null.
   */
  protected abstract String toStringRepresentation(T object);

  @Override
  protected final String fallbackToStringOf(Object object) {
    if (nodeClass.isInstance(object)) {
      return toStringRepresentation(nodeClass.cast(object));
    }

    return super.fallbackToStringOf(object);
  }

  final boolean isApplicable(Class<?> nodeClass) {
    return this.nodeClass.equals(nodeClass);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  protected static String getAsmTextifierRepresentation(Consumer<ExtendedTextifier> asmTextifierConsumer) {
    Objects.requireNonNull(asmTextifierConsumer);

    var stringWriter = new StringWriter();
    var printWriter = new PrintWriter(stringWriter);
    var textifier = new ExtendedTextifier();
    asmTextifierConsumer.accept(textifier);
    textifier.print(printWriter);

    return stringWriter.toString();
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  protected static class ExtendedTextifier extends Textifier {

    private ExtendedTextifier() {
      super(Opcodes.ASM9);
      tab = "";
      tab2 = "  ";
      tab3 = "    ";
      ltab = "";
    }

    public void addText(String text) {
      this.text.add(text);
    }

    @Override
    protected Textifier createTextifier() {
      return new ExtendedTextifier();
    }

    public Map<Label, String> getLabelNames() {
      return labelNames;
    }
  }
}
