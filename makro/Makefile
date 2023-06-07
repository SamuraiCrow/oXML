all: Main

JAVACCSRC = Token.java ParseException.java TokenMgrError.java SimpleCharStream.java
SMLSRC = SmlParser.java SmlParserConstants.java SmlParserTokenManager.java
XMLSRC = XmlParser.java XmlParserConstants.java XmlParserTokenManager.java
GENSRC = $(JAVACCSRC) $(SMLSRC) $(XMLSRC)

%.class: %.java
	javac $<

%.java: %.jj
	javacc $<

Main: SmlParser.java XmlParser.java Main.java
	javac *.java

jar: Main
	jar cmf manifest.mf SmlParsers.jar *.class

clean:
	rm -f $(GENSRC) *.class SmlParsers.jar
