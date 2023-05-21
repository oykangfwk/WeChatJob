package com.wechat;

public class IntegerAction {
	
	private static volatile IntegerAction integeraction;
	
	private static Integer cnt = 1;
	
	public IntegerAction(){
		/*cnt++;
		System.out.println("cnt="+cnt);*/
	};
	
	public static IntegerAction getInstance(){
		if(integeraction == null){
			synchronized (IntegerAction.class) {
				if(integeraction == null){
					integeraction = new IntegerAction();
				}
			}
		}
		return integeraction;
	}

	public static Integer getCnt() {
		return cnt;
	}

	public static void setCnt(Integer cnt) {
		IntegerAction.cnt = cnt;
	}
	
}
