package dietchallenge;

import java.util.List;
import java.util.Scanner;

public class DietMain {

	public static void main(String[] args) {
		   DietDAO ddao = new DietDAO();
		   Scanner sc = new Scanner(System.in);
		      
		   boolean flag = true;
		   while(flag) {
		       System.out.println("[1]������ ���");
		       System.out.println("[2]���� Į�θ� ���");
		       System.out.println("[3]���̾�Ʈ ��Ȳ ��ü��ȸ");
		       System.out.println("[4]���̾�Ʈ ��Ȳ ������ȸ");
		       System.out.println("[5]���̾��� ��ŷ��ȸ");
		       System.out.println("[6]������ ��ü ����");
		       System.out.println("[7]������ �˻�");
		       System.out.println("[8]������ ���� ����");
		       System.out.println("[9]�ߵ� ����");
		       System.out.println("[0]����");
		       System.out.print("�ڿ��Ͻô� �޴��� ��ȣ�� �Է��� �ּ���: ");
		       int menu = sc.nextInt();
		       switch(menu) {
		       case 1:
		          System.out.print("������ȣ: ");
		          int bno = ddao.MaxBno()+1;
		          System.out.println(bno);
		          System.out.print("�̸�: ");
		          String name = sc.next();
		          System.out.print("����: ");
		          String gender = sc.next();
		          System.out.print("����: ");
		          int age = sc.nextInt();
		          System.out.print("Ű: ");
		          double height = sc.nextDouble();
		          System.out.print("������: ");
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
		               System.out.println("30�� ���̾�Ʈ ç������ ȸ������ �ǽ� ���� ȯ���մϴ�!");
		               System.out.println();
		            }            
		            break;
		       case 2:
		          System.out.print("��¥ ex)20210831: ");
		       	  String eatendate = sc.next();
		       	  System.out.print("������ȣ: ");
		          bno = sc.nextInt();
		          System.out.print("��ħ ���� ���� ���� �Է�: ");
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
		        		 System.out.println("���� Į�θ� ��� �Ϸ�Ǿ����ϴ�.");
		        	 }
		            break;
		       case 3:
		          List<DietcaloriesVO> list = ddao.DietCurrentStatusAllSelect();
		          System.out.println("\n�������������������������������� ���̾�Ʈ ������ ��ü ��ȸ"+
		          "�ߡ������������������������������");
		          System.out.println("��¥\t\t��ȣ\t�̸�\t����Į�θ�\t\t����Į�θ�\t\t�Ҹ�Į�θ�\t\t�ʰ�Į�θ�\t\t������ ����");
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
		          System.out.print("�ڰ˻��� ���Ͻô� �����ڴ��� ��ȣ�� �Է��Ͽ� �ֽʽÿ�: ");
		          bno = sc.nextInt();
		          List<DietcaloriesVO> list1 = ddao.DietCurrentStatusOneSelect(bno);
		          if(list1.size()==0) {
		        	System.out.println("������ȣ Ȯ�� �ٶ��ϴ�.");
		        	continue;
		        	  }
			      System.out.println("\n�������������������������������� ���̾�Ʈ ������ ��ü ��ȸ"+
		        	  " �ߡ������������������������������");
			      System.out.println("��¥\t\t��ȣ\t�̸�\t����Į�θ�\t\t����Į�θ�\t\t�Ҹ�Į�θ�\t\t�ʰ�Į�θ�\t\t������ ����");
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
		       		System.out.println("\n���������������������� ���̾�Ʈ ������ ���� ��ȸ"+
		       		" �ߡ��������������������");
		       		System.out.println("��ȣ\t�̸�\t���۸�����\t\t���������(kg)\t��������(kg)\t����");
		       		int r =0;
		       		for(int i=0;i<list2.size();i++) {
		       			System.out.print(list2.get(i).getBno()+"\t");
		       			System.out.print(list2.get(i).getName()+"\t");
		       			System.out.print(list2.get(i).getWeight()+"\t\t");
		       			System.out.print(list2.get(i).getPweight()+"\t\t");
		       			System.out.print(list2.get(i).getTotvariation()+"\t\t");
		       			System.out.print((r+1)+"��\n");
		       			r++;
		       		}
		       			System.out.print("������ ������ ��Ģ : ");
		       			String penalty=ddao.penalty();
		       			System.out.println(penalty);
		            
		            break;
		         case 6://��� ���̺� ��ü ��ȸ
		             List<DietmemberVO> list3 = ddao.DietMemberAllSelect();
		             System.out.println("\n����������� ���̾�Ʈ ������ ��ü ��ȸ �ߡ���������");
		             System.out.println("������ȣ\t�̸�\t����\t����\tŰ\t������\t����Į�θ�");
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
		         case 7://��� ���̺� ���� ��ȸ
		        	 System.out.print("�ڰ˻��� ���Ͻô� �����ڴ��� ��ȣ�� �Է��Ͽ� �ֽʽÿ�: ");
		             bno = sc.nextInt();
		             DietmemberVO dvo = ddao.DietMemberOneSelect(bno);
		  
		             if(dvo==null) {
		                System.out.println("\n�������� ������ȣ �Դϴ�.");
		                continue;
		             }else {
		                System.out.println("\n����������� ���̾�Ʈ ������ ���� ��ȸ �ߡ���������");
		                System.out.println("������ȣ\t�̸�\t����\t����\tŰ\t������\t����Į�θ�");
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
		         case 8://��� ���̺� ����
		        	 System.out.print("�����Ͻ� ������ ��ȣ�� �Է��Ͽ� �ֽʽÿ�: ");
		             bno = sc.nextInt();
		             DietmemberVO dmvo1 = ddao.memberInfoModify(bno);
		             
		             if(dmvo1 == null) {
		                System.out.println("���������ʴ� ȸ�� ��ȣ �Դϴ�. �ٽ� ��ȣ�� �����Ͽ� �ֽʽÿ�.");
		                continue;
		             }
		             System.out.println("ȸ����ȣ: "+dmvo1.getBno());
		             System.out.print("ȸ���̸�: "+dmvo1.getName()+"/������ ��ġ������ ��쿣 [0]�� �Է��Ͽ� �ֽʽÿ� �� ");
		             String name1 = sc.next();
		             if(name1.equals("0")) {
		                name1 = dmvo1.getName();
		             }
		             
		             System.out.print("����: ");
		             if(dmvo1.getGender().equalsIgnoreCase("M")) {
		                System.out.print("����");
		             }else {
		                System.out.print("����");
		             }
		             System.out.print("/������ ��ġ������ ��쿣 [0]�� �Է��Ͽ� �ֽʽÿ� �� ");
		             String gender1 = sc.next();
		             if(gender1.equals("0")) {
		                gender1 = dmvo1.getGender();
		             }
		             
		             System.out.print("����: "+dmvo1.getAge()+"/������ ��ġ������ ��쿣 [0]�� �Է��Ͽ� �ֽʽÿ� �� ");
		             int age1 = sc.nextInt();
		             if(age1==0) {
		                age1 = dmvo1.getAge();
		             }
		             
		              System.out.print("Ű: "+dmvo1.getHeight()+"/������ ��ġ������ ��쿣 [0]�� �Է��Ͽ� �ֽʽÿ� �� ");
		              double height1 = sc.nextDouble();
		              if(height1 == 0) {
		                 height1 = dmvo1.getHeight();
		              }
		              
		              System.out.print("������: "+dmvo1.getWeight()+"/������ ��ġ������ ��쿣 [0]�� �Է��Ͽ� �ֽʽÿ� �� ");
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
		             
		             int okupdate =ddao.memberUpdate(dmvo2); //result���� ok������ ����
		             if(okupdate>0) {
		                System.out.println("\n�ڼ����� �Ϸ�Ǿ����ϴ�!");
		             }else {
		                System.out.println("\n�ڿ��� �߻�");
		             }

		        	 break;
		         case 9:
			         ddao.DietMemberDelete();      
		        	 break;
		         case 0:
		            System.out.println("\n30�� ���̾�Ʈ ç���� ���α׷��� ����Ǿ����ϴ�. �����մϴ٢�");
		            flag=false;
		            break;
		         default:
		               System.out.println("�̿��Ͻ� �޴���ȣ�� �ٽ� Ȯ���Ͽ� �ֽñ� �ٶ��ϴ�.");
		               continue;            
		         }
		         
		      }

	}

}
