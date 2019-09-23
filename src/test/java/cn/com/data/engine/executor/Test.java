package cn.com.data.engine.executor;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		l.add(1);
		l.add(2);
		l.add(3);
		l.add(null);
		l.add(null);
		l.removeIf(s->s==null);
		System.out.println(l.size());
	}

}
