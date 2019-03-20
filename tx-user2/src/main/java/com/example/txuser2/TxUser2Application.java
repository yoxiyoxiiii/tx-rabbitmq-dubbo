package com.example.txuser2;

import com.example.txuser2.httpclient.HttpClientUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.hibernate.annotations.GeneratorType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@SpringBootApplication(scanBasePackages = "com.example")
public class TxUser2Application {

	public static void main(String[] args) {
		SpringApplication.run(TxUser2Application.class, args);
	}

	@GetMapping("get")
	public synchronized int get() throws IOException {
		HttpClientUtil clientUtil = HttpClientUtil.httpClientBuilder().builder();
		Map<String, String> arge = new ConcurrentHashMap<>();
		Map<String, String> header = new ConcurrentHashMap<>();

		arge.put("type","2");
		arge.put("path","/contract/RISKANNOUNCE/RISKANNOUNCE_20190220110857_5.pdf");

		header.put("consumerAuth","KR_econtractTest");
		header.put("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE1NTI2MTI4NjIsIkluc3RJZCI6NTAyOCwiY2hhbm5lbCI6ImdhdGV3YXkiLCJjdXN0VHlwZSI6MiwiZXhwIjoxNTUyNjk5MjYyfQ.6aMY24wrmicYzKryNRP7oCU6jvsq-P3w_VfmN39h0Ec");
		header.put("Content-Type","application/pdf; charset=UTF-8");
		HttpResponse httpget = clientUtil.httpget("https://apitest.iservice.citics.com:443/v1/econtract/downloadFileForApi", arge, header);
		InputStream content = httpget.getEntity().getContent();

		File file = new File("");
		String canonicalPath = file.getCanonicalPath();
		IOUtils.copy(content,new FileOutputStream(new File(canonicalPath+"\\test.pdf")));
		return 0;

	}

}
