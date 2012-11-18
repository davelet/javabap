package cn.vash.mbca;

public class GA {
	final float crossPosibility = 0.8f;
	final float differParents = 0.2f;

	public void CrossOperation(String a, String b) {
		if ((a.length()) != b.length()) {
			System.out.print("Not the same length");
			return;
		}
		double rand = java.lang.Math.random();
		System.out.println(rand);
		if (rand <= crossPosibility) {
			int start = (int) (Math.random() * a.length());// start position of
															// change
			int dura = (int) (Math.random() * (a.length() - start));// length of
																	// change
			if(dura==0){
				dura+=1;
			}
			System.out.println(start + "      " + dura);
			String[] ab = { a, b };
			String[] backStrings=interChange(ab, start, start + dura);
			System.out.println(backStrings[0]+"        "+backStrings[1]);
		}
	}

	private String[] interChange(String[] s, int start, int end) {
		if (2 != s.length || (start > end)) {
			System.out.println("Not correct length  "+s.length+"  "+start+"  "+end);
			return s;
		}
		StringBuilder aba = new StringBuilder(s[0]);
		StringBuilder abb = new StringBuilder(s[1]);
		String tempString = aba.substring(start, end);
		aba.delete(start, end).insert(start , abb.substring(start, end));
		abb.delete(start, end).insert(start , tempString);
		String[] as = { aba.toString(), abb.toString() };
		return as;
	}

	public static void main(String[] args) {
		long start871456 = System.currentTimeMillis();

		new GA().CrossOperation("7fh", "456");

		long end84654856 = System.currentTimeMillis();
		System.out.println("运行时间是" + (end84654856 - start871456) + "毫秒");
	}
}
