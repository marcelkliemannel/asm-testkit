package dev.turingcomplete.asmtestkit.asmutils._internal;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.Textifier;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public final class TextifierUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  private TextifierUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public static String textify(Consumer<ExtendedTextifier> textify) {
    Objects.requireNonNull(textify);

    var stringWriter = new StringWriter();
    var printWriter = new PrintWriter(stringWriter);
    var textifier = new ExtendedTextifier();
    textify.accept(textifier);
    textifier.print(printWriter);

    return stringWriter.toString();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  public static class ExtendedTextifier extends Textifier {

    public ExtendedTextifier() {
      super(Opcodes.ASM9);

      tab = ""; // Fields methods
      tab2 = "  "; // Instructions
      tab3 = "    "; // Switch cases
      ltab = ""; // Labels
    }

    public ExtendedTextifier setTab2(int tab2) {
      Objects.checkIndex(tab2, Integer.MAX_VALUE);
      this.tab2 = " ".repeat(tab2);

      return this;
    }

    public ExtendedTextifier setTab3(int tab3) {
      Objects.checkIndex(tab3, Integer.MAX_VALUE);
      this.tab3 = " ".repeat(tab3);

      return this;
    }

    public void addText(String text) {
      this.text.add(text);
    }

    public String getCurrentStringBuilderValue() {
      return stringBuilder.toString();
    }

    @Override
    protected Textifier createTextifier() {
      return new ExtendedTextifier();
    }

    public Map<Label, String> getLabelNames() {
      return labelNames;
    }

    @Override
    public void visitClassEnd() {
    }
  }
}
