package listener.main;

// 201502012 KwonOhBeom

import java.util.Hashtable;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import generated.MiniCBaseListener;
import generated.MiniCParser;
import generated.MiniCParser.ExprContext;
import generated.MiniCParser.For_stmtContext;
import generated.MiniCParser.Fun_declContext;
import generated.MiniCParser.Local_declContext;
import generated.MiniCParser.ParamsContext;
import generated.MiniCParser.ProgramContext;
import generated.MiniCParser.StmtContext;
import generated.MiniCParser.Type_specContext;
import generated.MiniCParser.Var_declContext;
import listener.main.SymbolTable.Type;

import static listener.main.BytecodeGenListenerHelper.*;
import static listener.main.SymbolTable.*;

public class BytecodeGenListener extends MiniCBaseListener implements ParseTreeListener {
	ParseTreeProperty<String> newTexts = new ParseTreeProperty<String>();
	SymbolTable symbolTable = new SymbolTable();

	int tab = 0;
	int label = 0;

	// program : decl+

	@Override
	public void enterFun_decl(MiniCParser.Fun_declContext ctx) {
		symbolTable.initFunDecl();

		String fname = getFunName(ctx);
		ParamsContext params;

		if (fname.equals("main")) {
			symbolTable.putLocalVar("args", Type.INTARRAY);
		} else {
			symbolTable.putFunSpecStr(ctx);
			params = (MiniCParser.ParamsContext) ctx.getChild(3);
			symbolTable.putParams(params);
		}
	}

	// var_decl : type_spec IDENT ';' | type_spec IDENT '=' LITERAL ';'|type_spec
	// IDENT '[' LITERAL ']' ';'
	@Override
	public void enterVar_decl(MiniCParser.Var_declContext ctx) {
		String varName = ctx.IDENT().getText();
		String x = ctx.getChild(0).getText();

		if (isArrayDecl(ctx)) {
			symbolTable.putGlobalVar(varName, Type.INTARRAY);
		} else if (isDeclWithInit(ctx)) {
			if (x.equals("float"))
				symbolTable.putGlobalVarWithInitVal(varName, Type.FLOAT, initFloatVal(ctx));
			if (x.equals("int"))
				symbolTable.putGlobalVarWithInitVal(varName, Type.INT, initIntVal(ctx));
		} else { // simple decl
			if (x.equals("float"))
				symbolTable.putGlobalVar(varName, Type.FLOAT);
			if (x.equals("int"))
				symbolTable.putGlobalVar(varName, Type.INT);
		}
	}

	@Override
	public void enterLocal_decl(MiniCParser.Local_declContext ctx) {
		String type = ctx.getChild(0).getText();
		String varName = ctx.IDENT().getText();

		if (isArrayDecl(ctx)) {
			symbolTable.putLocalVar(varName, Type.INTARRAY);
		} else if (isDeclWithInit(ctx)) {
			if (type.equals("float"))
				symbolTable.putLocalVarWithInitVal(varName, Type.FLOAT, initFloatVal(ctx));
			if (type.equals("int"))
				symbolTable.putLocalVarWithInitVal(varName, Type.INT, initIntVal(ctx));
		} else { // simple decl
			if (type.equals("float"))
				symbolTable.putLocalVar(varName, Type.FLOAT);
			if (type.equals("int"))
				symbolTable.putLocalVar(varName, Type.INT);
		}
	}

	@Override
	public void exitProgram(MiniCParser.ProgramContext ctx) {
		String classProlog = getFunProlog();

		String fun_decl = "", var_decl = "";

		for (int i = 0; i < ctx.getChildCount(); i++) {
			if (isFunDecl(ctx, i))
				fun_decl += newTexts.get(ctx.decl(i));
			else
				var_decl += newTexts.get(ctx.decl(i));
		}

		newTexts.put(ctx, classProlog + var_decl + fun_decl);

		System.out.println(newTexts.get(ctx));
	}

	// decl : var_decl | fun_decl
	@Override
	public void exitDecl(MiniCParser.DeclContext ctx) {
		String decl = "";
		if (ctx.getChildCount() == 1) {
			// var_decl
			if (ctx.var_decl() != null)
				decl += newTexts.get(ctx.var_decl());
			else
				// fun_decl
				decl += newTexts.get(ctx.fun_decl());
		}
		newTexts.put(ctx, decl);
	}

	// stmt : expr_stmt | compound_stmt | if_stmt | while_stmt | return_stmt
	@Override
	public void exitStmt(MiniCParser.StmtContext ctx) {
		String stmt = "";
		if (ctx.getChildCount() > 0) {
			if (ctx.expr_stmt() != null) // expr_stmt
				stmt += newTexts.get(ctx.expr_stmt());
			else if (ctx.compound_stmt() != null) // compound_stmt
				stmt += newTexts.get(ctx.compound_stmt());
			// <(0) Fill here>
			// ctx에 해당하는 statemente들을 찾아 각자의 statement별로 처리한다.
			else if (ctx.if_stmt() != null)
				stmt += newTexts.get(ctx.if_stmt());
			else if (ctx.while_stmt() != null)
				stmt += newTexts.get(ctx.while_stmt());
			else if (ctx.for_stmt() != null)
				stmt += newTexts.get(ctx.for_stmt());
			else if (ctx.return_stmt() != null)
				stmt += newTexts.get(ctx.return_stmt());
		}
		newTexts.put(ctx, stmt);
	}

	// expr_stmt : expr ';'
	@Override
	public void exitExpr_stmt(MiniCParser.Expr_stmtContext ctx) {
		String stmt = "";
		if (ctx.getChildCount() == 2) {
			stmt += newTexts.get(ctx.expr()); // expr
		}
		newTexts.put(ctx, stmt);
	}

	// while_stmt : WHILE '(' expr ')' stmt
	@Override
	public void exitWhile_stmt(MiniCParser.While_stmtContext ctx) {
		// <(1) Fill here!>
		// while문의 경우를 처리하는 함수이다. loop와 label을 새롭게 label로 사용하고 JVM의 문법에 맞추어 while문 코드를
		// 작성한다.
		// WHILE '(' expr ')' stmt
		String s = null, s1 = null, expr = null, s2 = null, stmt = null;

		String loop = symbolTable.newLabel();
		String label = symbolTable.newLabel();

		expr = newTexts.get(ctx.expr());
		stmt = newTexts.get(ctx.stmt());

		String whileText = loop + ": " + "\n" + expr + "ifeq " + label + "\n" + stmt + "\n" + "goto " + loop + "\n"
				+ label + ": " + "\n";

		newTexts.put(ctx, whileText);

	}

	// for문이 끝날 때 동작하는 함수로 조건부, 함수부 등으로 부분을 나누고 While문의 형식과 같은 형식으로 배치한다.
	@Override
	public void exitFor_stmt(For_stmtContext ctx) {
		// TODO Auto-generated method stub
		String var_decl = null, expr1 = null, expr2 = null, stmt = null;

		String loop = symbolTable.newLabel();
		String label = symbolTable.newLabel();

		var_decl = newTexts.get(ctx.local_decl());
		expr1 = newTexts.get(ctx.expr(0));
		expr2 = newTexts.get(ctx.expr(1));
		stmt = newTexts.get(ctx.compound_stmt());

		String forText = var_decl + "\n" + loop + ": " + "\n" + expr1 + "ifeq " + label + "\n" + stmt + "\n" + expr2
				+ "\n" + "goto " + loop + "\n" + label + ": " + "\n";

		newTexts.put(ctx, forText);
	}

	@Override
	public void exitFun_decl(MiniCParser.Fun_declContext ctx) {
		// <(2) Fill here!>
		// function일 때 동작하는 함수이다. ctx를 필요한 부분에 맞추어 파싱하여 재조합하고 함수의 끝을 알리는 .end method를
		// newText에 저장한다.
		String type_spec = null, ident = null, compound_stmt = null;

		ident = ctx.IDENT().getText();
		type_spec = funcHeader(ctx, ident);

		compound_stmt = newTexts.get(ctx.compound_stmt());

		newTexts.put(ctx, type_spec + compound_stmt + ".end method\n");
	}

	private String funcHeader(MiniCParser.Fun_declContext ctx, String fname) {
		return ".method public static " + symbolTable.getFunSpecStr(fname) + "\n" + ".limit stack " + getStackSize(ctx)
				+ "\n" + ".limit locals " + getLocalVarSize(ctx) + "\n";

	}

	// 전역변수를 처리하는 함수이다.
	@Override
	public void exitVar_decl(MiniCParser.Var_declContext ctx) {
		String varType = ctx.getChild(0).getText();
		String varName = ctx.IDENT().getText();
		String varEqual = ctx.getChild(2).getText();
		String varDecl = "";
		String varValue = ctx.getChild(3).getText();

		if (isDeclWithInit(ctx) && symbolTable.getVarType(varName) != null) {
			if (varType.toUpperCase().charAt(0) == 'F')
				varValue += ".0";

			varDecl += ".field " + varName + " " + varType.toUpperCase().charAt(0) + " " + varEqual + " " + varValue
					+ "\n";
			// (원본) varDecl += "putfield " + varName + "\n";
			// v. initialization => Later! skip now..:
		}

		newTexts.put(ctx, varDecl);
	}

	// 지역 변수를 설정하는 함수로 데이터 타입에 맞추어 Int 타입은 i, Float 타입은 f를 붙힌다.
	@Override
	public void exitLocal_decl(MiniCParser.Local_declContext ctx) {
		String varDecl = "";
		String varValue = "";
		String varName = "";

		if (isDeclWithInit(ctx)) {
			varValue = ctx.LITERAL().getText();
			varName = ctx.IDENT().getText();

			String vId = symbolTable.getVarId(ctx);

			varDecl += "ldc " + varValue + "\n" + firstCharAt(symbolTable.getVarType(varName)) + "store " + vId + "\n";
		}

		newTexts.put(ctx, varDecl);
	}

	// compound_stmt : '{' local_decl* stmt* '}'
	@Override
	public void exitCompound_stmt(MiniCParser.Compound_stmtContext ctx) {
		// <(3) Fill here>
		// Coumpound일 때 동작하는 함수이다. 필요한 내용별로 StringBuilder를 생성하고 이에 계속 append하여 더해나간 뒤 두
		// 내용을 합쳐서 newText에 저장한다.
		StringBuilder lineLocal_decl = new StringBuilder();
		StringBuilder lineStmt = new StringBuilder();

		int stmtSize = ctx.stmt().size();
		int localSize = ctx.local_decl().size();

		// stmt인 경우 띄어쓰기
		for (int i = 0; i < stmtSize; i++) {
			lineStmt.append(newTexts.get(ctx.stmt(i)));
		}

		// local_decl인 경우 띄어쓰기
		for (int i = 0; i < localSize; i++) {
			lineLocal_decl.append(newTexts.get(ctx.local_decl(i)));
		}

		newTexts.put(ctx, lineLocal_decl + "\n" + lineStmt);
	}

	// if_stmt : IF '(' expr ')' stmt | IF '(' expr ')' stmt ELSE stmt;
	@Override
	public void exitIf_stmt(MiniCParser.If_stmtContext ctx) {
		String stmt = "";
		String condExpr = newTexts.get(ctx.expr());
		String thenStmt = newTexts.get(ctx.stmt(0));

		String lend = symbolTable.newLabel();
		String lelse = symbolTable.newLabel();

		if (noElse(ctx)) {
			stmt += condExpr + "\n" + "ifeq " + lend + "\n" + thenStmt + "\n" + lend + ":" + "\n";
		} else {
			String elseStmt = newTexts.get(ctx.stmt(1));
			stmt += condExpr + "\n" + "ifeq " + lelse + "\n" + thenStmt + "\n" + "goto " + lend + "\n" + lelse + ": "
					+ elseStmt + "\n" + lend + ":" + "\n";
		}

		newTexts.put(ctx, stmt);
	}

	// return_stmt : RETURN ';' | RETURN expr ';'
	@Override
	public void exitReturn_stmt(MiniCParser.Return_stmtContext ctx) {
		// <(4) Fill here>
		// return을 처리하는 함수이다. return값이 있을 때와 없을 때를 나누어서 ireturn과 return을 newText에 put한다.
		String _return = null, val = null, _expr = null;

		if (ctx.getChildCount() == 2) {
			_return = ctx.getChild(0).getText();

			newTexts.put(ctx, _return + "\n");
		}

		else if (ctx.getChildCount() == 3) {

			val = newTexts.get(ctx.expr());

			char x = symbolTable.getVarType(ctx.getChild(1).getText()).toString().toLowerCase().charAt(0);
			_return = x + "return";

			newTexts.put(ctx, val + _return + "\n");
		}
	}

	// expr이 종룔될 때 실행하는 함수로 load와 store의 자료형을 맞추어 준다.
	@Override
	public void exitExpr(MiniCParser.ExprContext ctx) {
		String expr = "";

		if (ctx.getChildCount() <= 0) {
			newTexts.put(ctx, "");
			return;
		}

		if (ctx.getChildCount() == 1) { // IDENT | LITERAL
			if (ctx.IDENT() != null) {
				String idName = ctx.IDENT().getText();
				expr += firstCharAt(symbolTable.getVarType(idName)) + "load " + symbolTable.getVarId(idName) + " \n";

				if (ctx.LITERAL() != null) {
					String literalStr = ctx.LITERAL().getText();
					expr += "ldc " + literalStr + " \n";
				}
			} else if (ctx.getChildCount() == 2) { // UnaryOperation
				expr = handleUnaryExpr(ctx, newTexts.get(ctx) + expr);
			} else if (ctx.getChildCount() == 3) {
				// '(' expr ')'
				if (ctx.getChild(0).getText().equals("(")) {
					expr = newTexts.get(ctx.expr(0));

				}
				// IDENT '=' expr
				else if (ctx.getChild(1).getText().equals("=")) {
					String test = symbolTable.getVarId(ctx.IDENT().getText());
					expr = newTexts.get(ctx.expr(0)) + firstCharAt(symbolTable.getVarType(ctx.IDENT().getText()))
							+ "store " + symbolTable.getVarId(ctx.IDENT().getText()) + "\n";

				}
				// binary operation
				else {
					expr = handleBinExpr(ctx, expr);

				}
			}
			// IDENT '(' args ')' | IDENT '[' expr ']'
			else if (ctx.getChildCount() == 4) {
				if (ctx.args() != null) { // function calls
					expr = handleFunCall(ctx, expr);
				} else { // expr
					// Arrays: TODO
				}
			}
			// IDENT '[' expr ']' '=' expr
			else { // Arrays: TODO */
			}
			newTexts.put(ctx, expr);
		}
	}

	private String handleUnaryExpr(MiniCParser.ExprContext ctx, String expr) {
		String l1 = symbolTable.newLabel();
		String l2 = symbolTable.newLabel();
		String lend = symbolTable.newLabel();

		expr += newTexts.get(ctx.expr(0));
		switch (ctx.getChild(0).getText()) {
		case "-":
			expr += "           ineg \n";
			break;
		case "--":
			expr += "ldc 1" + "\n" + "isub" + "\n";
			break;
		case "++":
			expr += "ldc 1" + "\n" + "iadd" + "\n";
			break;
		case "!":
			expr += "ifeq " + l2 + "\n" + l1 + ": " + "ldc 0" + "\n" + "goto " + lend + "\n" + l2 + ": " + "ldc 1"
					+ "\n" + lend + ": " + "\n";
			break;
		}
		return expr;
	}

	// 이항 연산을 실행하는 함수로 값들의 타입에 따라 연산자의 타입을 설정한다.
	private String handleBinExpr(MiniCParser.ExprContext ctx, String expr) {
		String l2 = symbolTable.newLabel();
		String lend = symbolTable.newLabel();

		String test = ctx.getChild(0).getText();
		char type = firstCharAt(symbolTable.getVarType(test));

		String test2 = ctx.getChild(2).getText();
		char type2 = firstCharAt(symbolTable.getVarType(test2));

		expr += newTexts.get(ctx.expr(0));
		expr += newTexts.get(ctx.expr(1));

		switch (ctx.getChild(1).getText()) {
		case "*":
			expr += type + "mul \n";
			break;
		case "/":
			expr += type + "div \n";
			break;
		case "%":
			expr += type + "rem \n";
			break;
		case "+": // expr(0) expr(1) iadd
			expr += type + "add \n";
			break;
		case "-":
			expr += type + "sub \n";
			break;

		case "==":
			expr += type + "sub " + "\n" + "ifeq " + l2 + "\n" + "ldc 0" + "\n" + "goto " + lend + "\n" + l2 + ":\n"
					+ "ldc 1" + "\n" + lend + ": " + "\n";
			break;
		case "!=":
			expr += type + "sub " + "\n" + "ifeq " + l2 + "\n" + "ldc 0" + "\n" + "goto " + lend + "\n" + l2 + ":\n"
					+ "ldc 1" + "\n" + lend + ": " + "\n";
			break;
		case "<=":
			// 양 쪽값을 뺀 후 0과 비교하여 0보다 작거나 같으면 우측값이 더크거나 같으므로 참으로 처리
			// <(5) Fill here>
			expr += type + "sub " + "\n" + "ifle " + l2 + "\n" + "ldc 0" + "\n" + "goto " + lend + "\n" + l2 + ":\n"
					+ "ldc 1" + "\n" + lend + ": " + "\n";
			break;
		case "<":
			// <(6) Fill here>
			// 양 쪽값을 뺀 후 0과 비교하여 0보다 작으면 우측값이 더 크므로 참으로 처리
			expr += type + "sub " + "\n" + "iflt " + l2 + "\n" + "ldc 0" + "\n" + "goto " + lend + "\n" + l2 + ":\n"
					+ "ldc 1" + "\n" + lend + ": " + "\n";
			break;

		case ">=":
			// <(7) Fill here>
			// 양 쪽값을 뺀 후 0과 비교하여 0보다 크거나 같으면 우측값이 더 작거나 같으므로 참으로 처리
			expr += type + "sub " + "\n" + "ifge " + l2 + "\n" + "ldc 0" + "\n" + "goto " + lend + "\n" + l2 + ":\n"
					+ "ldc 1" + "\n" + lend + ": " + "\n";
			break;

		case ">":
			// <(8) Fill here>
			// 양 쪽값을 뺀 후 0과 비교하여 0보다 크면 우측값이 더 작으므로 참으로 처리
			expr += type + "sub " + "\n" + "ifgt " + l2 + "\n" + "ldc 0" + "\n" + "goto " + lend + "\n" + l2 + ":\n"
					+ "ldc 1" + "\n" + lend + ": " + "\n";
			break;

		case "and":
			expr += "ifne " + lend + "\n" + "pop" + "\n" + "ldc 0" + "\n" + lend + ": " + "\n";
			break;
		case "or":
			// <(9) Fill here>
			// 조건 중 하나라도 1(참)이 되면 나머지는 보지않아도 바로 참으로 처리하였다.
			expr += "ifeq " + lend + "\n" + "pop" + "\n" + "ldc 1" + "\n" + lend + ": " + "\n";
			break;
		}

		return expr;
	}

	// 함수의 동작을 처리하는 함수로 함수들의 리턴 타입에 대한 처리름 담당한다.
	private String handleFunCall(MiniCParser.ExprContext ctx, String expr) {
		String fname = getFunName(ctx);

		if (fname.equals("_print")) { // System.out.println
			String temp = symbolTable.getFunSpecStr("_print");
			String[] temp2 = temp.split("R_TYPE");

			char funcType = ' ';
			// 입력 받은 값이 Float, Int 타입의 값인지 아니면 단순한 정수, 실수인지에 따라서 처리한다.
			if (ctx.getChild(2).getChild(0).getChildCount() == 4) {
				String tmp = this.symbolTable.getFunSpecStr(ctx.getChild(2).getChild(0).getChild(0).getText());
				funcType = tmp.charAt(tmp.length() - 1);
			} else if (ctx.getChild(2).getChildCount() == 1) {
				String value = ctx.getChild(2).getText();
				if (value.contains(".")) {
					funcType = 'F';
				} else
					funcType = 'I';
			}

			expr = "getstatic java/lang/System/out Ljava/io/PrintStream; " + newTexts.get(ctx.args()) + "invokevirtual "
					+ temp2[0] + funcType + temp2[1] + "\n";
		} else {
			expr = newTexts.get(ctx.args()) + "invokestatic " + getCurrentClassName() + "/"
					+ symbolTable.getFunSpecStr(fname) + "\n";
		}

		return expr;

	}

	// args : expr (',' expr)* | ;
	@Override
	public void exitArgs(MiniCParser.ArgsContext ctx) {

		String argsStr = "\n";

		for (int i = 0; i < ctx.expr().size(); i++) {
			argsStr += newTexts.get(ctx.expr(i));
		}
		newTexts.put(ctx, argsStr);
	}

}
