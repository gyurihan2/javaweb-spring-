package com.spring.javawebS.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.spring.javawebS.dao.StudyDAO;
import com.spring.javawebS.vo.KakaoAddressVO;
import com.spring.javawebS.vo.MemberVO;
import com.spring.javawebS.vo.QrCodeVO;
import com.spring.javawebS.vo.UserVO;

@Service
public class StudyServiceImple implements StudyService {

	@Autowired
	StudyDAO studyDAO;

	@Override
	public String[] getCityStringArray(String dodo) {
		String[] strArray = new String[100];
		
		if(dodo.equals("서울")) {
			strArray[0] = "강남구";
			strArray[1] = "서초구";
			strArray[2] = "마초구";
			strArray[3] = "영등포구";
			strArray[4] = "관악구";
			strArray[5] = "동작구";
			strArray[6] = "성북구";
		}
		else if(dodo.equals("경기")) {
			strArray[0] = "수원시";
			strArray[1] = "안양시";
			strArray[2] = "안성시";
			strArray[3] = "고양시";
			strArray[4] = "일산시";
			strArray[5] = "용인시";
		}
		else if(dodo.equals("충북")) {
			strArray[0] = "청주시";
			strArray[1] = "충주시";
			strArray[2] = "제천시";
			strArray[3] = "단양군";
			strArray[4] = "음성군";
			strArray[5] = "괴산군";
			strArray[6] = "보은군";
			strArray[7] = "진천시";
		}
		else if(dodo.equals("충남")) {
			strArray[1] = "천안시";
			strArray[2] = "아산시";
			strArray[3] = "홍성군";
			strArray[4] = "예산군";
			strArray[5] = "공주시";
			strArray[6] = "청양군";
			strArray[7] = "논산시";
		}
		else if(dodo.equals("경북")) {
			strArray[0] = "봉화군";
			strArray[1] = "구미시";
			strArray[2] = "영주시";
			strArray[3] = "칠곡군";
			strArray[4] = "안동시";
			strArray[5] = "포항시";
			strArray[6] = "의성군";
		}
		return strArray;
	}

	@Override
	public ArrayList<String> getCityArrayList(String dodo) {
		ArrayList<String> vos = new ArrayList<>();
		
		if(dodo.equals("서울")) {
			vos.add("강남구");
			vos.add("서초구");
			vos.add("마초구");
			vos.add("영등포구");
			vos.add("관악구");
			vos.add("동작구");
			vos.add("성북구");
			
		}
		else if(dodo.equals("경기")) {
			vos.add("수원시");
			vos.add("안양시");
			vos.add("안성시");
			vos.add("고양시");
			vos.add("일산시");
			vos.add("수원시");
			vos.add("용인시");
		}
		else if(dodo.equals("충북")) {
			vos.add("청주시");
			vos.add("충주시");
			vos.add("제천시");
			vos.add("단양군");
			vos.add("음성군");
			vos.add("괴산군");
			vos.add("보은군");
			vos.add("진천시");
			
		}
		else if(dodo.equals("충남")) {
			vos.add("천안시");
			vos.add("아산시");
			vos.add("홍성군");
			vos.add("예산군");
			vos.add("공주시");
			vos.add("청양군");
			vos.add("논산시");
		}
		else if(dodo.equals("경북")) {
			vos.add("봉화군");
			vos.add("구미시");
			vos.add("영주시");
			vos.add("칠곡군");
			vos.add("안동시");
			vos.add("포항시");
			vos.add("의성군");
			
		}
		
		return vos;
	}

	@Override
	public MemberVO getMemberMidSearch(String name) {
	
		return studyDAO.getMemberMidSearch(name);
	}

	@Override
	public ArrayList<MemberVO> getMemberMidSearch2(String name) {
		
		return studyDAO.getMemberMidSearch2(name);
	}

	@Override
	public int fileUpload(MultipartFile fName, String mid) {
		int res = 0;
		
		// 메모리 올라와 있는 파일의 정보를 실제 서버 파일 시스템에 저장 처리한다.(spring 은 output만 하면됨 / jsp in out 다 해야됨)
		// 예외 처리를 위해 메소드 생성
		
		
		try {
			writeFile(fName);
			res=1;
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return res;
	}

	public void writeFile(MultipartFile fName) throws IOException {
		byte[] data = fName.getBytes();
		
		 LocalDate now = LocalDate.now();
     // 포맷 정의
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");

     // 포맷 적용
     String formatedNow = now.format(formatter);

     // 결과 출력
    
		
		HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
		FileOutputStream fos = new FileOutputStream(realPath+formatedNow+"_"+fName.getOriginalFilename());
		
		fos.write(data);
		fos.close();
	}

	@Override
	public int setUserInput(UserVO vo) {
		
		return studyDAO.setUserInput(vo);
	}

	@Override
	public ArrayList<UserVO> getUserList() {
		
		return studyDAO.getUserList();
	}

	@Override
	public void setUserDelete(int idx) {
		
		studyDAO.setUserDelete(idx);
	}

	@Override
	public KakaoAddressVO getKakaoAddressName(String address) {
		
		return studyDAO.getKakaoAddressName(address);
	}

	@Override
	public void setKakaoAddressInput(KakaoAddressVO vo) {
		studyDAO.setKakaoAddressInput(vo);
	}

	@Override
	public List<KakaoAddressVO> getKakaoAddressList() {
		
		return studyDAO.getKakaoAddressList();
	}

	@Override
	public void setKakaoAddressDelete(String address) {
		
		 studyDAO.setKakaoAddressDelete(address);
	}

	@Override
	public String qrCreate(QrCodeVO vo, String realPath) {
		// 네이밍 -> 날짜_아이디_성명_메일주소_랜덤번호2자리
		String qrCodeName = "";
		String qrCodeName2 = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		
		UUID uid = UUID.randomUUID();
		String strUid = uid.toString().substring(0,2);
		
		qrCodeName = sdf.format(new Date())+"_"+vo.getMid()+"_"+vo.getName()+"_"+vo.getEmail()+"_"+strUid;
		qrCodeName2 = sdf.format(new Date())+"\n"+vo.getMid()+"\n"+vo.getName()+"\n"+vo.getEmail()+"\n"+strUid;
		
		
		try {
			File file = new File(realPath);
			if(!file.exists()) file.mkdirs();
			
			//String name = new String(vo.getName().getBytes("UTF-8"),"ISO-8859-1");
			qrCodeName2 = new String(qrCodeName2.getBytes("UTF-8"),"ISO-8859-1");
			
			// qr코드 만들기
			int qrCodeColor = 0xFF000000; // QR CODE 글자색 -> 검정
			int qrCodeBackColor = 0xFFFFFFFF; // QR CODE 배경색 -> 흰색
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter(); // QR Code 객체 생성
			
			//BitMatrix bitMatrix = qrCodeWriter.encode(name, BarcodeFormat.QR_CODE, 200, 200); // QR코드 크기
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeName2, BarcodeFormat.QR_CODE, 200, 200); // QR코드 크기
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor,qrCodeBackColor); 
			
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
			
			// ImageIO객체를 이용하면 byte 배열 단위로 변환 없이 파일을 write 시킬수 있다
			ImageIO.write(bufferedImage, "png", new File(realPath+"/"+qrCodeName+".png"));
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		return qrCodeName;
	}

	@Override
	public String qrCreate2(QrCodeVO vo, String realPath) {
		String qrCodeName = "";

		qrCodeName = vo.getMoveUrl();
		
		
		try {
			File file = new File(realPath);
			if(!file.exists()) file.mkdirs();
			
			qrCodeName = new String(qrCodeName.getBytes("UTF-8"),"ISO-8859-1");
			
			// qr코드 만들기
			int qrCodeColor = 0xFF000000; // QR CODE 글자색 -> 검정
			int qrCodeBackColor = 0xFFFFFFFF; // QR CODE 배경색 -> 흰색
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter(); // QR Code 객체 생성
			
			//BitMatrix bitMatrix = qrCodeWriter.encode(name, BarcodeFormat.QR_CODE, 200, 200); // QR코드 크기
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeName, BarcodeFormat.QR_CODE, 200, 200); // QR코드 크기
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor,qrCodeBackColor); 
			
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
			
			// ImageIO객체를 이용하면 byte 배열 단위로 변환 없이 파일을 write 시킬수 있다
			ImageIO.write(bufferedImage, "png", new File(realPath+"/"+qrCodeName+".png"));
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		return qrCodeName;
	}

	@Override
	public String qrCreate3(QrCodeVO vo, String realPath) {
		String qrCodeName = "";

		try {
			File file = new File(realPath);
			if(!file.exists()) file.mkdirs();
			
			qrCodeName = new String(vo.getMovieTemp().getBytes("UTF-8"),"ISO-8859-1");
			
			// qr코드 만들기
			int qrCodeColor = 0xFF000000; // QR CODE 글자색 -> 검정
			int qrCodeBackColor = 0xFFFFFFFF; // QR CODE 배경색 -> 흰색
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter(); // QR Code 객체 생성
			
			//BitMatrix bitMatrix = qrCodeWriter.encode(name, BarcodeFormat.QR_CODE, 200, 200); // QR코드 크기
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeName, BarcodeFormat.QR_CODE, 200, 200); // QR코드 크기
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor,qrCodeBackColor); 
			
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
			
			// ImageIO객체를 이용하면 byte 배열 단위로 변환 없이 파일을 write 시킬수 있다
			ImageIO.write(bufferedImage, "png", new File(realPath+"/"+qrCodeName+".png"));
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		return qrCodeName;
	}

	@Override
	public String qrCreate4(QrCodeVO vo, String realPath) {
		// QR 코드명은 ...
		String qrCodeName = "";

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			UUID uid = UUID.randomUUID();
			String strUid = uid.toString().substring(0,4);
			
			qrCodeName = sdf.format(new Date()) + "_" + strUid;
			
			File file = new File(realPath);
			if(!file.exists()) file.mkdirs();
			
			String qrTemp = new String(vo.getMovieTemp().getBytes("UTF-8"),"ISO-8859-1");
			
			// qr코드 만들기
			int qrCodeColor = 0xFF000000; // QR CODE 글자색 -> 검정
			int qrCodeBackColor = 0xFFFFFFFF; // QR CODE 배경색 -> 흰색
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter(); // QR Code 객체 생성
			
			BitMatrix bitMatrix = qrCodeWriter.encode(qrTemp, BarcodeFormat.QR_CODE, 200, 200); // QR코드 크기
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor,qrCodeBackColor); 
			
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
			
			// ImageIO객체를 이용하면 byte 배열 단위로 변환 없이 파일을 write 시킬수 있다
			ImageIO.write(bufferedImage, "png", new File(realPath+"/"+qrCodeName+".png"));
			
			// 생성된 QR 코드 정보를 DB에 저장
			vo.setQrCodeName(qrCodeName+".png");
			studyDAO.setQrCreateDB(vo);
			
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		return qrCodeName;
	}

	@Override
	public QrCodeVO getQrCodeSearch(String qrCode) {
		
		return studyDAO.getQrCodeSearch(qrCode);
	}

	
}
