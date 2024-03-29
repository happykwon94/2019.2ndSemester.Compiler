// Generated from MiniC.g4 by ANTLR 4.4
 
package generated;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniCLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__19=1, T__18=2, T__17=3, T__16=4, T__15=5, T__14=6, T__13=7, T__12=8, 
		T__11=9, T__10=10, T__9=11, T__8=12, T__7=13, T__6=14, T__5=15, T__4=16, 
		T__3=17, T__2=18, T__1=19, T__0=20, VOID=21, INT=22, FLOAT=23, NEW=24, 
		FOR=25, WHILE=26, IF=27, ELSE=28, RETURN=29, OR=30, AND=31, LE=32, GE=33, 
		EQ=34, NE=35, IDENT=36, LITERAL=37, FLOAT_REGEX=38, DecimalConstant=39, 
		OctalConstant=40, HexadecimalConstant=41, WS=42;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'", "'\\u001D'", "'\\u001E'", 
		"'\\u001F'", "' '", "'!'", "'\"'", "'#'", "'$'", "'%'", "'&'", "'''", 
		"'('", "')'", "'*'"
	};
	public static final String[] ruleNames = {
		"T__19", "T__18", "T__17", "T__16", "T__15", "T__14", "T__13", "T__12", 
		"T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", 
		"T__2", "T__1", "T__0", "VOID", "INT", "FLOAT", "NEW", "FOR", "WHILE", 
		"IF", "ELSE", "RETURN", "OR", "AND", "LE", "GE", "EQ", "NE", "IDENT", 
		"LITERAL", "FLOAT_REGEX", "DecimalConstant", "OctalConstant", "HexadecimalConstant", 
		"WS"
	};


	public MiniCLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiniC.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2,\u00f3\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\3"+
		"\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t"+
		"\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21"+
		"\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\26\3\26"+
		"\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31"+
		"\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\35"+
		"\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37"+
		"\3 \3 \3 \3 \3!\3!\3!\3\"\3\"\3\"\3#\3#\3#\3$\3$\3$\3%\3%\7%\u00c3\n%"+
		"\f%\16%\u00c6\13%\3&\3&\3&\3&\5&\u00cc\n&\3\'\3\'\3\'\6\'\u00d1\n\'\r"+
		"\'\16\'\u00d2\3(\3(\3(\7(\u00d8\n(\f(\16(\u00db\13(\5(\u00dd\n(\3)\3)"+
		"\7)\u00e1\n)\f)\16)\u00e4\13)\3*\3*\3*\6*\u00e9\n*\r*\16*\u00ea\3+\6+"+
		"\u00ee\n+\r+\16+\u00ef\3+\3+\2\2,\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,\3\2\n\5\2"+
		"C\\aac|\6\2\62;C\\aac|\3\2\62;\3\2\63;\3\2\629\4\2ZZzz\5\2\62;CHch\5\2"+
		"\13\f\17\17\"\"\u00fc\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2"+
		"\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2"+
		"Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\3W\3\2\2\2\5Y\3\2\2\2\7[\3\2\2\2\t]\3"+
		"\2\2\2\13_\3\2\2\2\ra\3\2\2\2\17d\3\2\2\2\21g\3\2\2\2\23i\3\2\2\2\25k"+
		"\3\2\2\2\27m\3\2\2\2\31o\3\2\2\2\33q\3\2\2\2\35s\3\2\2\2\37u\3\2\2\2!"+
		"w\3\2\2\2#y\3\2\2\2%{\3\2\2\2\'}\3\2\2\2)\177\3\2\2\2+\u0081\3\2\2\2-"+
		"\u0086\3\2\2\2/\u008a\3\2\2\2\61\u0090\3\2\2\2\63\u0094\3\2\2\2\65\u0098"+
		"\3\2\2\2\67\u009e\3\2\2\29\u00a1\3\2\2\2;\u00a6\3\2\2\2=\u00ad\3\2\2\2"+
		"?\u00b0\3\2\2\2A\u00b4\3\2\2\2C\u00b7\3\2\2\2E\u00ba\3\2\2\2G\u00bd\3"+
		"\2\2\2I\u00c0\3\2\2\2K\u00cb\3\2\2\2M\u00cd\3\2\2\2O\u00dc\3\2\2\2Q\u00de"+
		"\3\2\2\2S\u00e5\3\2\2\2U\u00ed\3\2\2\2WX\7\61\2\2X\4\3\2\2\2YZ\7}\2\2"+
		"Z\6\3\2\2\2[\\\7]\2\2\\\b\3\2\2\2]^\7=\2\2^\n\3\2\2\2_`\7>\2\2`\f\3\2"+
		"\2\2ab\7-\2\2bc\7-\2\2c\16\3\2\2\2de\7/\2\2ef\7/\2\2f\20\3\2\2\2gh\7\177"+
		"\2\2h\22\3\2\2\2ij\7_\2\2j\24\3\2\2\2kl\7?\2\2l\26\3\2\2\2mn\7@\2\2n\30"+
		"\3\2\2\2op\7#\2\2p\32\3\2\2\2qr\7\'\2\2r\34\3\2\2\2st\7*\2\2t\36\3\2\2"+
		"\2uv\7+\2\2v \3\2\2\2wx\7,\2\2x\"\3\2\2\2yz\7-\2\2z$\3\2\2\2{|\7.\2\2"+
		"|&\3\2\2\2}~\7/\2\2~(\3\2\2\2\177\u0080\7\60\2\2\u0080*\3\2\2\2\u0081"+
		"\u0082\7x\2\2\u0082\u0083\7q\2\2\u0083\u0084\7k\2\2\u0084\u0085\7f\2\2"+
		"\u0085,\3\2\2\2\u0086\u0087\7k\2\2\u0087\u0088\7p\2\2\u0088\u0089\7v\2"+
		"\2\u0089.\3\2\2\2\u008a\u008b\7h\2\2\u008b\u008c\7n\2\2\u008c\u008d\7"+
		"q\2\2\u008d\u008e\7c\2\2\u008e\u008f\7v\2\2\u008f\60\3\2\2\2\u0090\u0091"+
		"\7p\2\2\u0091\u0092\7g\2\2\u0092\u0093\7y\2\2\u0093\62\3\2\2\2\u0094\u0095"+
		"\7h\2\2\u0095\u0096\7q\2\2\u0096\u0097\7t\2\2\u0097\64\3\2\2\2\u0098\u0099"+
		"\7y\2\2\u0099\u009a\7j\2\2\u009a\u009b\7k\2\2\u009b\u009c\7n\2\2\u009c"+
		"\u009d\7g\2\2\u009d\66\3\2\2\2\u009e\u009f\7k\2\2\u009f\u00a0\7h\2\2\u00a0"+
		"8\3\2\2\2\u00a1\u00a2\7g\2\2\u00a2\u00a3\7n\2\2\u00a3\u00a4\7u\2\2\u00a4"+
		"\u00a5\7g\2\2\u00a5:\3\2\2\2\u00a6\u00a7\7t\2\2\u00a7\u00a8\7g\2\2\u00a8"+
		"\u00a9\7v\2\2\u00a9\u00aa\7w\2\2\u00aa\u00ab\7t\2\2\u00ab\u00ac\7p\2\2"+
		"\u00ac<\3\2\2\2\u00ad\u00ae\7q\2\2\u00ae\u00af\7t\2\2\u00af>\3\2\2\2\u00b0"+
		"\u00b1\7c\2\2\u00b1\u00b2\7p\2\2\u00b2\u00b3\7f\2\2\u00b3@\3\2\2\2\u00b4"+
		"\u00b5\7>\2\2\u00b5\u00b6\7?\2\2\u00b6B\3\2\2\2\u00b7\u00b8\7@\2\2\u00b8"+
		"\u00b9\7?\2\2\u00b9D\3\2\2\2\u00ba\u00bb\7?\2\2\u00bb\u00bc\7?\2\2\u00bc"+
		"F\3\2\2\2\u00bd\u00be\7#\2\2\u00be\u00bf\7?\2\2\u00bfH\3\2\2\2\u00c0\u00c4"+
		"\t\2\2\2\u00c1\u00c3\t\3\2\2\u00c2\u00c1\3\2\2\2\u00c3\u00c6\3\2\2\2\u00c4"+
		"\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5J\3\2\2\2\u00c6\u00c4\3\2\2\2"+
		"\u00c7\u00cc\5O(\2\u00c8\u00cc\5Q)\2\u00c9\u00cc\5S*\2\u00ca\u00cc\5M"+
		"\'\2\u00cb\u00c7\3\2\2\2\u00cb\u00c8\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb"+
		"\u00ca\3\2\2\2\u00ccL\3\2\2\2\u00cd\u00ce\5O(\2\u00ce\u00d0\7\60\2\2\u00cf"+
		"\u00d1\t\4\2\2\u00d0\u00cf\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d0\3\2"+
		"\2\2\u00d2\u00d3\3\2\2\2\u00d3N\3\2\2\2\u00d4\u00dd\7\62\2\2\u00d5\u00d9"+
		"\t\5\2\2\u00d6\u00d8\t\4\2\2\u00d7\u00d6\3\2\2\2\u00d8\u00db\3\2\2\2\u00d9"+
		"\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00dd\3\2\2\2\u00db\u00d9\3\2"+
		"\2\2\u00dc\u00d4\3\2\2\2\u00dc\u00d5\3\2\2\2\u00ddP\3\2\2\2\u00de\u00e2"+
		"\7\62\2\2\u00df\u00e1\t\6\2\2\u00e0\u00df\3\2\2\2\u00e1\u00e4\3\2\2\2"+
		"\u00e2\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3R\3\2\2\2\u00e4\u00e2\3"+
		"\2\2\2\u00e5\u00e6\7\62\2\2\u00e6\u00e8\t\7\2\2\u00e7\u00e9\t\b\2\2\u00e8"+
		"\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00e8\3\2\2\2\u00ea\u00eb\3\2"+
		"\2\2\u00ebT\3\2\2\2\u00ec\u00ee\t\t\2\2\u00ed\u00ec\3\2\2\2\u00ee\u00ef"+
		"\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1"+
		"\u00f2\b+\2\2\u00f2V\3\2\2\2\f\2\u00c2\u00c4\u00cb\u00d2\u00d9\u00dc\u00e2"+
		"\u00ea\u00ef\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}