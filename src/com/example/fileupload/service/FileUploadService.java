package com.example.fileupload.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	/* 경로를 '/'로 해 주면 root를 찾아가게하는데, window(c:\)와 linux(/)의 root경로가 다르다. -> '/'로 해 주면 JVM이 OS마다 변환해줌 */
	/* 현재는 '/'로 할 때, 프로젝트가 D:/에 있으니 D드라이브로 연결됨 */
	/* 줄 때, 물리적인 위치를 주지 말아라! 물리적인 위치와 서비스로 접근하는 url을 반드시 매핑시켜라! */
	/* 실제 파일이 저장되는 위치(tomcat/webapp/....)랑 url(webapp/....)이랑 위치가 완전히 다르니, 가상 URL로 매칭시켜주는 작업이 반드시 필요함 */
	private static String SAVE_PATH = "/uploads";
	private static String PREFIX_URL= "/uploads/images/";
	
	/*
	 * 파일을 서버에서 저장할 때, 여러 사용자가 여러가지 이름으로 혹은 같은 이름으로 저장할 수 있으니, 
	 * 보통 이름을 확장자를 뺀 후, 현재 서버 시간의 마지막 2자리를 가지고 이름을 삼는다.
	 * 이때, 2자리 중 1번째 자리숫자의 폴더 아래 2번째 자리 숫자의폴더로 나누어 저장한다.
	 */

	public List<String> restore(MultipartFile[] multipartFile) {
		
		List<String> url = new ArrayList<String>();
		
		try {
			for( MultipartFile mf : multipartFile ) {
				String originalFileName = mf.getOriginalFilename();
				/* 파일 확장자 */
				String extName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
				Long size = mf.getSize();
				/* 파일이름을 변경해서 서버에 저장할 때, 이름이 중복되면 안됨 */
				String saveFileName = genSaveFileName( extName );
	
				System.out.println("######" + originalFileName);
				System.out.println("######" + extName);
				System.out.println("######" + saveFileName);
				System.out.println("######" + size);
	
				writeFile( mf, saveFileName );
				
				url.add(PREFIX_URL + saveFileName);
//				System.out.println("######" + url);
			}
			
		} catch( IOException ex ) {
			throw new RuntimeException(ex);
		}
		return url;
	}

	private void writeFile(MultipartFile multipartFile, String saveFileName) throws IOException {
		byte[] fileData = multipartFile.getBytes();
		FileOutputStream fos = new FileOutputStream( SAVE_PATH + "/" + saveFileName );
		fos.write(fileData);
		fos.close();
	}

	private String genSaveFileName(String extName) {
		String fileName = "";

		Calendar calendar = Calendar.getInstance();
		fileName += calendar.get(Calendar.YEAR);
		/* 원래는 Calendar.MONTH는 + 1을 해 줘야 정확한 month가 나오지만, 우리 목적은 시간을 구하는 것이 아닌, 파일 이름을 고유한 값으로 저장하기 위함이다. */
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);

		fileName += extName;

		return fileName;
	}

}
