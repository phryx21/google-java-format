package com.google.googlejavaformat.java;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class NewlinesBeforeAssignmentOperatorsStyleTest {

  @Parameterized.Parameters
  public static Iterable<Object[]> parameters() {
    return ImmutableList.copyOf(new Object[][] {{"\n"}, {"\r"}, {"\r\n"}});
  }

  private final String newline;

  public NewlinesBeforeAssignmentOperatorsStyleTest(String newline) {
    this.newline = newline;
  }

  String lines(String... args) {
    return Joiner.on(newline).join(args);
  }

  @Test
  public void
      givenStandardGoogleFormatStyle_whenCombinedDeclarationAndInitialization_thenDeclareOnePutsOperatorBeforeNewline()
          throws Exception {
    String input =
        lines(
            "class Foo {{",
            "ImmutableList<Integer> ids = ImmutableList.builder()",
            ".addWithVeryLongMethodNameToTriggerLineBreak(1)",
            ".addWithVeryLongMethodNameToTriggerLineBreak(2)",
            ".addWithVeryLongMethodNameToTriggerLineBreak(3)",
            ".build();",
            "}}",
            "");
    String expectedOutput =
        lines(
            "class Foo {{",
            "    ImmutableList<Integer> ids =",
            "        ImmutableList.builder()",
            "            .addWithVeryLongMethodNameToTriggerLineBreak(1)",
            "            .addWithVeryLongMethodNameToTriggerLineBreak(2)",
            "            .addWithVeryLongMethodNameToTriggerLineBreak(3)",
            "            .build();",
            "}}",
            "");
    int idx = input.indexOf("addWithVeryLongMethodNameToTriggerLineBreak(2)");
    String output =
        doGetFormatReplacementsWithNewlinesAfterAssignmentOperators(input, idx, idx + 1);
    Assert.assertEquals(expectedOutput, output);
  }

  @Test
  public void
      givenOperatorsFirstStyle_whenCombinedDeclarationAndInitialization_thenDeclareOnePutsOperatorAfterNewline()
          throws Exception {
    String input =
        lines(
            "class Foo {{",
            "ImmutableList<Integer> ids = ImmutableList.builder()",
            ".addWithVeryLongMethodNameToTriggerLineBreak(1)",
            ".addWithVeryLongMethodNameToTriggerLineBreak(2)",
            ".addWithVeryLongMethodNameToTriggerLineBreak(3)",
            ".build();",
            "}}",
            "");
    String expectedOutput =
        lines(
            "class Foo {{",
            "    ImmutableList<Integer> ids",
            "        = ImmutableList.builder()",
            "            .addWithVeryLongMethodNameToTriggerLineBreak(1)",
            "            .addWithVeryLongMethodNameToTriggerLineBreak(2)",
            "            .addWithVeryLongMethodNameToTriggerLineBreak(3)",
            "            .build();",
            "}}",
            "");
    int idx = input.indexOf("addWithVeryLongMethodNameToTriggerLineBreak(2)");
    String output =
        doGetFormatReplacementsWithNewlinesBeforeAssignmentOperators(input, idx, idx + 1);
    Assert.assertEquals(output, expectedOutput);
  }

  @Test
  public void
      givenOperatorsFirstStyle_whenSeparateInitializationAfterDeclaration_thenVisitAssignmentPutsOperatorAfterNewline()
          throws Exception {
    String input =
        lines(
            "class Foo {{",
            "ImmutableList<Integer> ids;",
            "ids = ImmutableList.builder()",
            ".addWithVeryLongMethodNameToTriggerLineBreak(1)",
            ".addWithVeryLongMethodNameToTriggerLineBreak(2)",
            ".addWithVeryLongMethodNameToTriggerLineBreak(3)",
            ".build();",
            "}}",
            "");
    String expectedOutput =
        lines(
            "class Foo {{",
            "ImmutableList<Integer> ids;",
            "    ids",
            "        = ImmutableList.builder()",
            "            .addWithVeryLongMethodNameToTriggerLineBreak(1)",
            "            .addWithVeryLongMethodNameToTriggerLineBreak(2)",
            "            .addWithVeryLongMethodNameToTriggerLineBreak(3)",
            "            .build();",
            "}}",
            "");
    int idx = input.indexOf("addWithVeryLongMethodNameToTriggerLineBreak(2)");
    String output =
        doGetFormatReplacementsWithNewlinesBeforeAssignmentOperators(input, idx, idx + 1);
    Assert.assertEquals(expectedOutput, output);
  }

  @Test
  public void
      givenOperatorsFirstStyle_whenCompoundAssignment_thenVisitCompoundAssignmentPutsPlusEqualsAfterNewline()
          throws Exception {
    String input =
        lines(
            "class Foo {{",
            "String stringWithVeryAbsurdlyLongNameToEnsureInAllCasesThatALineBreakWillBeInserted = \"\";",
            "stringWithVeryAbsurdlyLongNameToEnsureInAllCasesThatALineBreakWillBeInserted += \""
                + "a".repeat(80)
                + "\";",
            "}}",
            "");
    String expectedOutput =
        lines(
            "class Foo {{",
            "String stringWithVeryAbsurdlyLongNameToEnsureInAllCasesThatALineBreakWillBeInserted = \"\";",
            "    stringWithVeryAbsurdlyLongNameToEnsureInAllCasesThatALineBreakWillBeInserted",
            "        += \"" + "a".repeat(80) + "\";",
            "}}",
            "");
    int idx = input.indexOf("+=");
    String output =
        doGetFormatReplacementsWithNewlinesBeforeAssignmentOperators(input, idx, idx + 1);
    Assert.assertEquals(expectedOutput, output);
  }

  @Test
  public void
      givenOperatorsFirstStyle_whenCompoundDeclarationAndInitialization_thenDeclareManyPutsEqualsAfterNewline()
          throws Exception {
    String input =
        lines(
            "class Foo {{",
            "String stringWithVeryAbsurdlyLongNameToEnsureInAllCasesThatALineBreakWillBeInserted1 = "
                + "\""
                + "a".repeat(70)
                + "\","
                + "stringWithVeryAbsurdlyLongNameToEnsureInAllCasesThatALineBreakWillBeInserted2 = "
                + "\""
                + "b".repeat(70)
                + "\","
                + "stringWithVeryAbsurdlyLongNameToEnsureInAllCasesThatALineBreakWillBeInserted3 = "
                + "\""
                + "c".repeat(70)
                + "\";",
            "}}",
            "");
    String expectedOutput =
        lines(
            "class Foo {{",
            "    String",
            "        stringWithVeryAbsurdlyLongNameToEnsureInAllCasesThatALineBreakWillBeInserted1",
            "        = " + "\"" + "a".repeat(70) + "\",",
            "        stringWithVeryAbsurdlyLongNameToEnsureInAllCasesThatALineBreakWillBeInserted2",
            "        = " + "\"" + "b".repeat(70) + "\",",
            "        stringWithVeryAbsurdlyLongNameToEnsureInAllCasesThatALineBreakWillBeInserted3",
            "        = " + "\"" + "c".repeat(70) + "\";",
            "}}",
            "");
    int idx = input.indexOf("2");
    String output =
        doGetFormatReplacementsWithNewlinesBeforeAssignmentOperators(input, idx, idx + 1);
    Assert.assertEquals(expectedOutput, output);
  }

  private String doGetFormatReplacementsWithNewlinesBeforeAssignmentOperators(
      String input, int characterILo, int characterIHi) throws Exception {
    return new Formatter(
            JavaFormatterOptions.builder()
                .style(JavaFormatterOptions.Style.GOOGLE_NEWLINES_BEFORE_OPERATORS)
                .formatJavadoc(true)
                .reorderModifiers(true)
                .build())
        .formatSource(input, ImmutableList.of(Range.closedOpen(characterILo, characterIHi + 1)));
  }

  private String doGetFormatReplacementsWithNewlinesAfterAssignmentOperators(
      String input, int characterILo, int characterIHi) throws Exception {
    return new Formatter()
        .formatSource(input, ImmutableList.of(Range.closedOpen(characterILo, characterIHi + 1)));
  }
}
