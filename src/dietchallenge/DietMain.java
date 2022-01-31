package dietchallenge;

import java.util.List;
import java.util.Scanner;

public class DietMain {

	public static void main(String[] args) {
		   DietDAO ddao = new DietDAO();
		   Scanner sc = new Scanner(System.in);
		      
		   boolean flag = true;
		   while(flag) {
		       System.out.println("[1]참가자 등록");
		       System.out.println("[2]일일 칼로리 계산");
		       System.out.println("[3]다이어트 현황 전체조회");
		       System.out.println("[4]다이어트 현황 개인조회");
		       System.out.println("[5]다이어터 랭킹조회");
		       System.out.println("[6]참가자 전체 보기");
		       System.out.println("[7]참가자 검색");
		       System.out.println("[8]참가자 정보 수정");
		       System.out.println("[9]중도 포기");
		       System.out.println("[0]종료");
		       System.out.print("★원하시는 메뉴의 번호를 입력해 주세요: ");
		       int menu = sc.nextInt();
		       switch(menu) {
		       case 1:
		          System.out.print("참가번호: ");
		          int bno = ddao.MaxBno()+1;
		          System.out.println(bno);
		          System.out.print("이름: ");
		          String name = sc.next();
		          System.out.print("성별: ");
		          String gender = sc.next();
		          System.out.print("나이: ");
		          int age = sc.nextInt();
		          System.out.print("키: ");
		          double height = sc.nextDouble();
		          System.out.print("몸무게: ");
		          double weight = sc.nextDouble();
		            
		          DietmemberVO dmvo = new DietmemberVO();
		          dmvo.setBno(bno);
		          dmvo.setName(name);
		          dmvo.setGender(gender);
		          dmvo.setAge(age);
		          dmvo.setHeight(height);
		          dmvo.setWeight(weight);
		            
		          int result = ddao.DietMemberInsert(dmvo);
		          if(result>0) {
		               System.out.println("30일 다이어트 챌린지의 회원님이 되신 것을 환영합니다!");
		               System.out.println();
		            }            
		            break;
		       case 2:
		          System.out.print("날짜 ex)20210831: ");
		       	  String eatendate = sc.next();
		       	  System.out.print("참가번호: ");
		          bno = sc.nextInt();
		          System.out.print("아침 점심 저녁 간식 입력: ");
		          String breakfast = sc.next();
		          String lunch = sc.next();
		          String dinner = sc.next();
		          String snacks = sc.next();
		        	 
		          DietfoodsVO dfvo = new DietfoodsVO();
		        	 
		          DietcaloriesVO dcvo = new DietcaloriesVO();
		        	 
		          dcvo.setEatendate(eatendate);
		          dcvo.setBno(bno);
		          dfvo.setBreakfast(breakfast);
		          dfvo.setLunch(lunch);
		          dfvo.setDinner(dinner);
		          dfvo.setSnacks(snacks);
		        	 
		          int ok = ddao.ItkCaloriesCalc(dfvo, dcvo);
		        	 
		          if(ok>0) {
		        		 System.out.println("일일 칼로리 계산 완료되었습니다.");
		        	 }
		            break;
		       case 3:
		          List<DietcaloriesVO> list = ddao.DietCurrentStatusAllSelect();
		          System.out.println("\n〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓◆ 다이어트 참가자 전체 조회"+
		          "◆〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		          System.out.println("날짜\t\t번호\t이름\t권장칼로리\t\t섭취칼로리\t\t소모칼로리\t\t초과칼로리\t\t몸무게 증감");
		          for(int i=0; i<list.size(); i++) {
		          System.out.print(list.get(i).getEatendate()+"\t");
		          System.out.print(list.get(i).getBno()+"\t");
		          System.out.print(list.get(i).getName()+"\t");
		          System.out.print(list.get(i).getRcmdcalories()+"\t\t");
		          System.out.print(list.get(i).getItkcalories()+"\t\t");
		          System.out.print(list.get(i).getBndcalories()+"\t\t");
		          System.out.print(list.get(i).getExcalories()+"\t\t");
		          System.out.print(list.get(i).getVariation()+"\n");
		          }   
		           break;
		       case 4:
		          System.out.print("★검색을 원하시는 참가자님의 번호를 입력하여 주십시오: ");
		          bno = sc.nextInt();
		          List<DietcaloriesVO> list1 = ddao.DietCurrentStatusOneSelect(bno);
		          if(list1.size()==0) {
		        	System.out.println("참가번호 확인 바랍니다.");
		        	continue;
		        	  }
			      System.out.println("\n〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓◆ 다이어트 참가자 전체 조회"+
		        	  " ◆〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			      System.out.println("날짜\t\t번호\t이름\t권장칼로리\t\t섭취칼로리\t\t소모칼로리\t\t초과칼로리\t\t몸무게 증감");
			      for(int i=0; i<list1.size(); i++) {
			          System.out.print(list1.get(i).getEatendate()+"\t");
			          System.out.print(list1.get(i).getBno()+"\t");
			          System.out.print(list1.get(i).getName()+"\t");
			          System.out.print(list1.get(i).getRcmdcalories()+"\t\t");
			          System.out.print(list1.get(i).getItkcalories()+"\t\t");
			          System.out.print(list1.get(i).getBndcalories()+"\t\t");
			          System.out.print(list1.get(i).getExcalories()+"\t\t");
			          System.out.print(list1.get(i).getVariation()+"\n");
			        }
		           break;
		       	case 5:
		       		List<DietmemberVO> list2 = ddao.DietMemberRankSelect();
		       		System.out.println("\n〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓◆ 다이어트 참가자 순위 조회"+
		       		" ◆〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		       		System.out.println("번호\t이름\t시작몸무게\t\t현재몸무게(kg)\t뺀몸무게(kg)\t순위");
		       		int r =0;
		       		for(int i=0;i<list2.size();i++) {
		       			System.out.print(list2.get(i).getBno()+"\t");
		       			System.out.print(list2.get(i).getName()+"\t");
		       			System.out.print(list2.get(i).getWeight()+"\t\t");
		       			System.out.print(list2.get(i).getPweight()+"\t\t");
		       			System.out.print(list2.get(i).getTotvariation()+"\t\t");
		       			System.out.print((r+1)+"위\n");
		       			r++;
		       		}
		       			System.out.print("최하위 참가자 벌칙 : ");
		       			String penalty=ddao.penalty();
		       			System.out.println(penalty);
		            
		            break;
		         case 6://멤버 테이블 전체 조회
		             List<DietmemberVO> list3 = ddao.DietMemberAllSelect();
		             System.out.println("\n〓〓〓〓〓〓〓〓〓◆ 다이어트 참가자 전체 조회 ◆〓〓〓〓〓〓〓〓〓");
		             System.out.println("참가번호\t이름\t성별\t나이\t키\t몸무게\t권장칼로리");
		          for(int i=0; i<list3.size(); i++) {
		             System.out.print(list3.get(i).getBno()+"\t");
		             System.out.print(list3.get(i).getName()+"\t");
		             System.out.print(list3.get(i).getGender()+"\t");
		             System.out.print(list3.get(i).getAge()+"\t");
		             System.out.print(list3.get(i).getHeight()+"\t");
		             System.out.print(list3.get(i).getWeight()+"\t");
		             System.out.print(list3.get(i).getRcmdcalories()+"\t");
		             System.out.println();
		          }   
		             System.out.println();
		            break;
		         case 7://멤버 테이블 개인 조회
		        	 System.out.print("★검색을 원하시는 참가자님의 번호를 입력하여 주십시오: ");
		             bno = sc.nextInt();
		             DietmemberVO dvo = ddao.DietMemberOneSelect(bno);
		  
		             if(dvo==null) {
		                System.out.println("\n옳지않은 참가번호 입니다.");
		                continue;
		             }else {
		                System.out.println("\n〓〓〓〓〓〓〓〓〓◆ 다이어트 참가자 개인 조회 ◆〓〓〓〓〓〓〓〓〓");
		                System.out.println("참가번호\t이름\t성별\t나이\t키\t몸무게\t권장칼로리");
		                System.out.print(dvo.getBno()+"\t");
		                System.out.print(dvo.getName()+"\t");
		                System.out.print(dvo.getGender()+"\t");
		                System.out.print(dvo.getAge()+"\t");
		                System.out.print(dvo.getHeight()+"\t");
		                System.out.print(dvo.getWeight()+"\t");
		                System.out.print(dvo.getRcmdcalories()+"\t\n");
		                         
		             }
		             
		             System.out.println();               

		        	 break;
		         case 8://멤버 테이블 수정
		        	 System.out.print("수정하실 참가자 번호를 입력하여 주십시오: ");
		             bno = sc.nextInt();
		             DietmemberVO dmvo1 = ddao.memberInfoModify(bno);
		             
		             if(dmvo1 == null) {
		                System.out.println("존재하지않는 회원 번호 입니다. 다시 번호를 선택하여 주십시오.");
		                continue;
		             }
		             System.out.println("회원번호: "+dmvo1.getBno());
		             System.out.print("회원이름: "+dmvo1.getName()+"/수정을 원치않으실 경우엔 [0]을 입력하여 주십시오 → ");
		             String name1 = sc.next();
		             if(name1.equals("0")) {
		                name1 = dmvo1.getName();
		             }
		             
		             System.out.print("성별: ");
		             if(dmvo1.getGender().equalsIgnoreCase("M")) {
		                System.out.print("남자");
		             }else {
		                System.out.print("여자");
		             }
		             System.out.print("/수정을 원치않으실 경우엔 [0]을 입력하여 주십시오 → ");
		             String gender1 = sc.next();
		             if(gender1.equals("0")) {
		                gender1 = dmvo1.getGender();
		             }
		             
		             System.out.print("나이: "+dmvo1.getAge()+"/수정을 원치않으실 경우엔 [0]을 입력하여 주십시오 → ");
		             int age1 = sc.nextInt();
		             if(age1==0) {
		                age1 = dmvo1.getAge();
		             }
		             
		              System.out.print("키: "+dmvo1.getHeight()+"/수정을 원치않으실 경우엔 [0]을 입력하여 주십시오 → ");
		              double height1 = sc.nextDouble();
		              if(height1 == 0) {
		                 height1 = dmvo1.getHeight();
		              }
		              
		              System.out.print("몸무게: "+dmvo1.getWeight()+"/수정을 원치않으실 경우엔 [0]을 입력하여 주십시오 → ");
		              double weight1 = sc.nextDouble();
		              if(weight1 == 0) {
		                 weight1 = dmvo1.getWeight();
		              }
	
		             DietmemberVO dmvo2 = new DietmemberVO();
		             dmvo2.setBno(bno);
		             dmvo2.setName(name1);
		             dmvo2.setGender(gender1);
		             dmvo2.setAge(age1);
		             dmvo2.setHeight(height1);
		             dmvo2.setWeight(weight1);
		             
		             int okupdate =ddao.memberUpdate(dmvo2); //result값을 ok변수로 받음
		             if(okupdate>0) {
		                System.out.println("\n★수정이 완료되었습니다!");
		             }else {
		                System.out.println("\n★에러 발생");
		             }

		        	 break;
		         case 9:
			         ddao.DietMemberDelete();      
		        	 break;
		         case 0:
		            System.out.println("\n30일 다이어트 챌린지 프로그램이 종료되었습니다. 감사합니다♥");
		            flag=false;
		            break;
		         default:
		               System.out.println("이용하실 메뉴번호를 다시 확인하여 주시기 바랍니다.");
		               continue;            
		         }
		         
		      }

	}

}
