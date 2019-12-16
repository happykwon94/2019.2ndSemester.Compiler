package listener.main;

//201502012 KwonOhBeom

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import generated.MiniCParser;
import generated.MiniCParser.Fun_declContext;
import generated.MiniCParser.Local_declContext;
import generated.MiniCParser.ParamsContext;
import generated.MiniCParser.Type_specContext;
import generated.MiniCParser.Var_declContext;
import listener.main.SymbolTable.Type;
import static listener.main.BytecodeGenListenerHelper.*;


public class SymbolTable {
	// Added Float
	enum Type {
		INT, INTARRAY, VOID, FLOAT, ERROR
	}
	
	static public class VarInfo {
		Type type; 
		int id;
		int initVal;
		float initFloatVal;
		
		public VarInfo(Type type,  int id, int initVal) {
			this.type = type;
			this.id = id;
			this.initVal = initVal;
		}
		
		public VarInfo(Type type,  int id, float initFloatVal) {
			this.type = type;
			this.id = id;
			this.initFloatVal = initFloatVal;
		}
		
		public VarInfo(Type type,  int id) {
			this.type = type;
			this.id = id;
			this.initVal = 0;
		}
	}
	
	static public class FInfo {
		public String sigStr;
	}
	
	private Map<String, VarInfo> _lsymtable = new HashMap<>();	// local v.
	private Map<String, VarInfo> _gsymtable = new HashMap<>();	// global v.
	private Map<String, FInfo> _fsymtable = new HashMap<>();	// function 
	
		
	private int _globalVarID = 0;
	private int _localVarID = 0;
	private int _labelID = 0;
	private int _tempVarID = 0;
	
	SymbolTable(){
		initFunDecl();
		initFunTable();
	}
	
	void initFunDecl(){		// at each func decl
		_lsymtable.clear();
		_localVarID = 0;
		_labelID = 0;
		_tempVarID = 32;		
	}
	
	// table에 값을 입력한다. 키값 varname, type에 관한 정보를 포함한다. 지역변수
	void putLocalVar(String varname, Type type){
		//<Fill here>
		VarInfo varInfo = new VarInfo(type, _localVarID++);
		_lsymtable.put(varname, varInfo);
	}
	
	// table에 값을 입력한다. 키값 varname, type에 관한 정보를 포함한다. 전역변수
	void putGlobalVar(String varname, Type type){
		//<Fill here>
		VarInfo varInfo = new VarInfo(type, _globalVarID++);
		_gsymtable.put(varname, varInfo);
	}
	
	// table에 값을 저장한다. 키값은 varname, type과 초기값에 관한 정보를 담고 있다. 지역변수
	void putLocalVarWithInitVal(String varname, Type type, int initVar){
		//<Fill here>
		VarInfo varInfo = new VarInfo(type, _localVarID++, initVar);
		_lsymtable.put(varname, varInfo);
	}
	
	////////////////////////////////////////////////////////////////////

	
	// float형을 Table에 추가하기 위해 Float값을 넣을 수 있는 함수 
	void putLocalVarWithInitVal(String varname, Type type, float initVar){
		//<Fill here>
		VarInfo varInfo = new VarInfo(type, _localVarID++, initVar);
		_lsymtable.put(varname, varInfo);
	}
	
	void putGlobalVarWithInitVal(String varname, Type type, float initVar){
		//<Fill here>
		VarInfo varInfo = new VarInfo(type, _globalVarID++, initVar);
		_gsymtable.put(varname, varInfo);
	
	}
		
	
	////////////////////////////////////////////////////////////////////
	
	
	// table에 값을 저장한다. 키값은 varname, type과 초기값에 관한 정보를 담고 있다. 전역변수
	void putGlobalVarWithInitVal(String varname, Type type, int initVar){
		//<Fill here>
		VarInfo varInfo = new VarInfo(type, _globalVarID++, initVar);
		_gsymtable.put(varname, varInfo);
	
	}
	
	// 매개변수에 관한 값들을 처리하는 함수이다. params 전체 중 param이 몇 개인지 갯수를 세어 각각 param마다 이름과 타입을 찾아서 테이블에 저장한다.
	void putParams(MiniCParser.ParamsContext params) {
		for(int i = 0; i < params.param().size(); i++) {
			//<Fill here>
			String paramType = params.param(i).type_spec().getText();
			String paramName = params.param(i).getChild(1).getText();
			putLocalVar(paramName, Type.valueOf(paramType.toUpperCase()));
		}
	}
	
	private void initFunTable() {
		FInfo printlninfo = new FInfo();
		printlninfo.sigStr = "java/io/PrintStream/println(R_TYPE)V";
		
		FInfo maininfo = new FInfo();
		maininfo.sigStr = "main([Ljava/lang/String;)V";
		_fsymtable.put("_print", printlninfo);
		_fsymtable.put("main", maininfo);
	}
	
	// table에서 함수에 관한 정보를 꺼내는 함수이다. 함수명을 키값으로 한다.
	public String getFunSpecStr(String fname) {		
		// <Fill here>
		return _fsymtable.get(fname).sigStr;
	}

	// table에서 함수에 관한 정보를 꺼내는 함수이다. 함수를 포함하는 ctx에서 함수명에 해당하는 부분을 찾아 이를 키값으로 한다.
	public String getFunSpecStr(Fun_declContext ctx) {
		// <Fill here>
		return _fsymtable.get(ctx.getChild(1).getText()).sigStr;
	}
	
	public String putFunSpecStr(Fun_declContext ctx) {
		String fname = getFunName(ctx);
		String argtype = "";	
		String rtype = "";
		String res = "";
		
		// <Fill here>	 type_spec IDENT '(' params ')' compound_stmt
		// 매개변수들의 타입을 검사하여 첫글자만 대문자로 파싱한 뒤 매개변수의 수에 맞게 구한다. 리턴 타입도 위와 같은 방식으로 해당하는 부분을 찾아 앞글자만 파싱하여 사용한다.
		for(int i = 0; i<ctx.params().param().size(); i++) {
			char first = ctx.params().param(i).type_spec().getText().toUpperCase().charAt(0);
			argtype += first;
		}
		
		rtype += ctx.type_spec().getText().toUpperCase().charAt(0);
				
		res = fname + "(" + argtype + ")" + rtype;
				
		FInfo finfo = new FInfo();
		finfo.sigStr = res.trim();
		_fsymtable.put(fname, finfo);
		
		return res;
	}
	
	String getVarId(String name){
		// <Fill here>
		VarInfo lvar = (VarInfo) _lsymtable.get(name);
		if (lvar != null) {
			return Integer.toString(lvar.id);
		}
		
		VarInfo gvar = (VarInfo) _gsymtable.get(name);
		if (gvar != null) {
			return Integer.toString(gvar.id);
		}
		
		return null;	
		
	}
	
	Type getVarType(String name){
		VarInfo lvar = (VarInfo) _lsymtable.get(name);
		if (lvar != null) {
			return lvar.type;
		}
		
		VarInfo gvar = (VarInfo) _gsymtable.get(name);
		if (gvar != null) {
			return gvar.type;
		}
		
		return Type.ERROR;	
	}
	String newLabel() {
		return "label" + _labelID++;
	}
	
	String newTempVar() {
		String id = "";
		return id + _tempVarID--;
	}

	// 변수의 id값을 꺼내는 함수이다. ctx에서 변수명에 해당하는 부분을 찾아 String값으로 반환한다.
	// global
	public String getVarId(Var_declContext ctx) {
		// <Fill here>
		String sname = "";
		sname += getVarId(ctx.IDENT().getText());
		return sname;
	}

	// local
	public String getVarId(Local_declContext ctx) {
		String sname = "";
		sname += getVarId(ctx.IDENT().getText());
		return sname;
	}

}
