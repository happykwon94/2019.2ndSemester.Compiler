package listener.main;

//201502012 KwonOhBeom

import java.util.Hashtable;

import generated.MiniCParser;
import generated.MiniCParser.ExprContext;
import generated.MiniCParser.Fun_declContext;
import generated.MiniCParser.If_stmtContext;
import generated.MiniCParser.Local_declContext;
import generated.MiniCParser.ParamContext;
import generated.MiniCParser.ParamsContext;
import generated.MiniCParser.Type_specContext;
import generated.MiniCParser.Var_declContext;
import listener.main.SymbolTable;
import listener.main.SymbolTable.VarInfo;

public class BytecodeGenListenerHelper {
	
	// <boolean functions>
	
	static boolean isFunDecl(MiniCParser.ProgramContext ctx, int i) {
		return ctx.getChild(i).getChild(0) instanceof MiniCParser.Fun_declContext;
	}
	
	// type_spec IDENT '[' ']'
	static boolean isArrayParamDecl(ParamContext param) {
		return param.getChildCount() == 4;
	}
	
	// global vars
	static int initVal(Var_declContext ctx) {
		return Integer.parseInt(ctx.LITERAL().getText());
	}

	// var_decl	: type_spec IDENT '=' LITERAL ';
	static boolean isDeclWithInit(Var_declContext ctx) {
		return ctx.getChildCount() == 5 ;
	}
	// var_decl	: type_spec IDENT '[' LITERAL ']' ';'
	static boolean isArrayDecl(Var_declContext ctx) {
		return ctx.getChildCount() == 6;
	}

	// <local vars>
	// local_decl	: type_spec IDENT '[' LITERAL ']' ';'
	static int initIntVal(Local_declContext ctx) {
		return Integer.parseInt(ctx.LITERAL().getText());
	}
	
	//Float 타입의 값으로 변환해주는 함수
	static float initFloatVal(Local_declContext ctx) {
		return Float.parseFloat(ctx.LITERAL().getText());
	}


	//////////////////////////////////////////////////////////////////
	// 타입을 찾는 함수이다.
	static char firstCharAt(SymbolTable.Type type) {
		return type.toString().toLowerCase().charAt(0); 
	}
	
	// 전역변수를 처리하는 함수이다.
	static float initFloatVal(Var_declContext ctx) {
		return Float.parseFloat(ctx.LITERAL().getText());
	}
	
	static int initIntVal(Var_declContext ctx) {
		return Integer.parseInt(ctx.LITERAL().getText());
	}
	
	//////////////////////////////////////////////////////////////////
	
	static boolean isArrayDecl(Local_declContext ctx) {
		return ctx.getChildCount() == 6;
	}
	
	static boolean isDeclWithInit(Local_declContext ctx) {
		return ctx.getChildCount() == 5 ;
	}
	
	static boolean isVoidF(Fun_declContext ctx) {
			// <Fill in>
			// 함수가 비어있다면 {, } 두 개의 Child만을 가진다.
		return ctx.getChildCount() == 2;
	}
	
	static boolean isIntReturn(MiniCParser.Return_stmtContext ctx) {
		return ctx.getChildCount() == 3;
	}


	static boolean isVoidReturn(MiniCParser.Return_stmtContext ctx) {
		return ctx.getChildCount() == 2;
	}
	
	// <information extraction>
	static String getStackSize(Fun_declContext ctx) {
		return "32";
	}
	static String getLocalVarSize(Fun_declContext ctx) {
		return "32";
	}
	
	static String getTypeText(Type_specContext typespec) {
			// <Fill in>
			// 해당하는 type값을 String 형식으로 반환해준다.
		return typespec.getText();

	}

	// params
	static String getParamName(ParamContext param) {
		// <Fill in>
		// parameter값의 String값으로 반환해준다.
		return param.getText();

	}
	
	static String getParamTypesText(ParamsContext params) {
		String typeText = "";
		
		for(int i = 0; i < params.param().size(); i++) {
			MiniCParser.Type_specContext typespec = (MiniCParser.Type_specContext)  params.param(i).getChild(0);
			typeText += getTypeText(typespec); // + ";";
		}
		return typeText;
	}
	
	static String varName(Local_declContext local_decl) {
		// <Fill in>
		// 변수의 이름을 반환해준다. local_decl의 child 중 이름에 해당하는 부분값을 String값으로 반환한다.
		return local_decl.getChild(1).getText();
		
	}
	
	static String getFunName(Fun_declContext ctx) {
		// <Fill in>
		// 함수의 이름을 반환해준다. ctx의 child 중 이름에 해당하는 부분값을 String값으로 반환한다.
		return ctx.getChild(1).getText();
	}
	
	static String getFunName(ExprContext ctx) {
		// <Fill in>		
		// 함수의 이름을 반환해준다. ExprContext의 child 중 이름에 해당하는 부분값을 String값으로 반환한다.
		return ctx.getChild(0).getText();

	}
	
	static boolean noElse(If_stmtContext ctx) {
		return ctx.getChildCount() == 5;
	}
	
	static String getFunProlog() {
		// 주어진 조건에 맞게 Prolog를 적어놓고 사용한다.
		return ".class public "+ getCurrentClassName() +"\n" + 
				".super java/lang/Object\n";
	}
	
	static String getCurrentClassName() {
		return "Test";
	}
}
