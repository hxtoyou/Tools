package ebills.tools.webService.cxf2;

import org.springframework.stereotype.Service;

@Service("sample")
public class SampleImpl implements Sample {

	@Override
	public String sayHello(String name, String age) {
		// TODO Auto-generated method stub
		return "Hello " + name + " ! " + " \r " + "I'm " + age;
	}

}
