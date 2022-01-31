package dietchallenge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DietDAO implements DietInterface{
	Scanner sc = new Scanner(System.in);
	   
	   DietDBManager ddbm = new DietDBManager();
	   
	   @Override
	   public int MaxBno() {
	      Connection conn = null; //db접속
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      String sql = "select max(bno) as maxbno from tbl_member";
	      int maxno = 0;
	      
	      try {
	         conn = ddbm.getConnection();
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         while(rs.next()) {
	            maxno = rs.getInt("maxbno");
	           
	         }
	      }catch(Exception e) {
	         e.printStackTrace();
	      }finally {
	         try {
	            if(pstmt!=null) {pstmt.close();}
	            if(conn!=null) {conn.close();}
	         }catch(Exception e) {
	            e.printStackTrace();
	         }
	      }
	      return maxno;
	      
	   }
	   
	   @Override
	   public int DietMemberInsert(DietmemberVO dmvo) {
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      
	      String sql = "insert into tbl_member(bno,name,gender,age,height,weight) values(?,?,?,?,?,?)";
	      
	      int result = 0;
	      
	      try {
	         conn = ddbm.getConnection();
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setInt(1, dmvo.getBno());
	         pstmt.setString(2, dmvo.getName());
	         pstmt.setString(3, dmvo.getGender());
	         pstmt.setInt(4, dmvo.getAge());
	         pstmt.setDouble(5, dmvo.getHeight());
	         pstmt.setDouble(6, dmvo.getWeight());
	         
	         result = pstmt.executeUpdate();
//	         return result;

	      }catch(Exception e) {
	         e.printStackTrace();
	      }finally {
	         try {if(pstmt!=null) {pstmt.close();}
	              if(conn!=null) {conn.close();}            
	         }catch(Exception e) {
	            e.printStackTrace();
	         }
	      }      
	      RcdCaloriesCalc(dmvo);
	      return result;
	      
	   } 
	   
	   @Override
	   public void RcdCaloriesCalc(DietmemberVO dmvo) {
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      String sql = "update tbl_member set rcmdcalories=? where bno=?";
	      try {
	         conn = ddbm.getConnection();
	         pstmt = conn.prepareStatement(sql);
	     
	         if(dmvo.getGender().equalsIgnoreCase("M")) {
	        	 dmvo.setRcmdcalories(Math.round(66.47+(13.75*dmvo.getWeight())+(5*dmvo.getHeight())-(6.76*dmvo.getAge())*1.375));
	        	 pstmt.setDouble(1, dmvo.getRcmdcalories());
	        	 pstmt.setInt(2, dmvo.getBno());
	        	 
	         }else if(dmvo.getGender().equalsIgnoreCase("F")) {
	        	 dmvo.setRcmdcalories(Math.round(655.1+(9.56*dmvo.getWeight())+(1.85*dmvo.getHeight())-(4.68*dmvo.getAge())*1.375));
	        	 pstmt.setDouble(1,dmvo.getRcmdcalories());
	        	 pstmt.setInt(2, dmvo.getBno());
	         }
	         
	         pstmt.executeUpdate();
	         
	      }catch(Exception e) {
	         e.printStackTrace();
	      }finally {
	         try {if(pstmt!=null) {pstmt.close();}
	              if(conn!=null) {conn.close();}
	         }catch(Exception e) {
	            e.printStackTrace();
	         }
	      }
	      
	   }
	   
	   @Override
	   public int ItkCaloriesCalc( DietfoodsVO dfvo, DietcaloriesVO dcvo ) {
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      String sql = "select sum(fdcalories) as itkcalories from tbl_foods where fdname=? or fdname=? or fdname=? or fdname=?";
	      String sql1= "select rcmdcalories from tbl_member where bno=?";//멤버 테이블에서 권장칼로리,몸무게 가져와서 칼로리 테이블에 저장
	      String sql2= "insert into tbl_calories values(?,?,?,?,?,?,?)";//마지막으로 칼로리 테이블에 저장
	      
	      int result = 0;
	try {
		conn = ddbm.getConnection();
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, dfvo.getBreakfast());
	    pstmt.setString(2, dfvo.getLunch());
	    pstmt.setString(3, dfvo.getDinner());
	    pstmt.setString(4, dfvo.getSnacks());
	    
	    rs = pstmt.executeQuery();//rs가 일일 섭취 칼로리를 가리킨다
	    
	    while(rs.next()) {
	    	dcvo.setItkcalories(rs.getInt("itkcalories"));
	    	
	    }
	    pstmt.close();
	    
	    pstmt = conn.prepareStatement(sql1);
	    pstmt.setInt(1,dcvo. getBno());
	    rs = pstmt.executeQuery();
	    
	    while(rs.next()) {
	    	dcvo.setRcmdcalories(rs.getDouble("rcmdcalories"));
	    }
	    
	    double sub = dcvo.getRcmdcalories()-dcvo.getItkcalories();
	    double pweight=0.;
	    if(sub>0) {
	    	dcvo.setBndcalories(sub);
	    	dcvo.setExcalories(0);
	    	double variation = (-1*(sub*0.1))/1000;//ex) -1*100*0.1 = -10g
	    	dcvo.setVariation(variation);
//	    	pweight = (dcvo.getWeight())+(dcvo.getVariation());
//	    	dcvo.setPweight(pweight);
	    }else if(sub==0) {
	    	dcvo.setBndcalories(0);
	    	dcvo.setExcalories(0);
	    	dcvo.setVariation(0);
//	    	dcvo.setPweight(dcvo.getWeight());
	    }else {//sub 음수
	    	dcvo.setBndcalories(0);
	    	dcvo.setExcalories(-1*sub); //양수
	    	double variation = (-1*sub*0.1)/1000;
	    	dcvo.setVariation(variation);
//	    	pweight = (dcvo.getWeight())+(dcvo.getVariation());
//	    	dcvo.setPweight(pweight);
	    }
	    
	    pstmt.close();
	    
	    pstmt = conn.prepareStatement(sql2);
	    pstmt.setString(1, dcvo.getEatendate());
	    pstmt.setInt(2, dcvo.getBno());
	    pstmt.setDouble(3, dcvo.getRcmdcalories());
	    pstmt.setDouble(4, dcvo.getItkcalories());
	    pstmt.setDouble(5, dcvo.getBndcalories());
	    pstmt.setDouble(6, dcvo.getExcalories());
	    pstmt.setDouble(7, dcvo.getVariation());
	    
	    result = pstmt.executeUpdate();
	   
	      }catch(Exception e) {
	         e.printStackTrace();
	      }finally {
	         try {if(pstmt!=null) {pstmt.close();}
	              if(conn!=null) {conn.close();}
	         }catch(Exception e) {
	            e.printStackTrace();
	         }
	      }
	return result;
	
	   }
	   
	   @Override
	   public  List<DietcaloriesVO> DietCurrentStatusAllSelect() {
		   Connection conn = null;
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      String sql = "select c.eatendate, c.bno, m.name, c.rcmdcalories, c.itkcalories, c.bndcalories, c.excalories, c.variation " + 
		      		"from tbl_calories c, tbl_member m " + 
		      		"where c.bno = m.bno order by bno, eatendate";
		      
		      List<DietcaloriesVO> list = new ArrayList<DietcaloriesVO>();

		      try {
		         conn = ddbm.getConnection();
		            pstmt = conn.prepareStatement(sql);
		            rs = pstmt.executeQuery();
		            
		            while(rs.next()) {
		               
		               DietcaloriesVO cvo = new DietcaloriesVO();
		               
		               cvo.setEatendate(rs.getString("eatendate"));
		               cvo.setBno(rs.getInt("bno"));
		               cvo.setName(rs.getString("name"));
		               cvo.setRcmdcalories(rs.getDouble("rcmdcalories"));
		               cvo.setItkcalories(rs.getDouble("itkcalories"));
		               cvo.setBndcalories(rs.getDouble("bndcalories"));
		               cvo.setExcalories(rs.getDouble("excalories"));
		               cvo.setVariation(rs.getDouble("variation"));
		               
		               list.add(cvo);
		               
		            }
		         
		      }catch(Exception e) {
		         e.printStackTrace();
		      }finally {
		         try {if(rs!=null) {rs.close();}
		             if(pstmt!=null) {pstmt.close();}
		              if(conn!=null) {conn.close();}
		         }catch(Exception e) {
		            e.printStackTrace();
		         }
		      }
		      return list;
		   }
		   
		   @Override
		   public List<DietcaloriesVO> DietCurrentStatusOneSelect(int no) {
		      Connection conn = null;
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      
		      List<DietcaloriesVO> list = new ArrayList<DietcaloriesVO>();
		      
		      String sql = "select c.eatendate, c.bno, m.name, c.rcmdcalories, c.itkcalories, c.bndcalories, c.excalories, c.variation " + 
			      		"from tbl_calories c, tbl_member m " + 
			      		"where c.bno = m.bno and m.bno=? order by eatendate";
		try {
		   conn = ddbm.getConnection();
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, no);
		   rs = pstmt.executeQuery();

		   while(rs.next()) {
			  DietcaloriesVO cvo = new DietcaloriesVO();
			  cvo.setEatendate(rs.getString("eatendate"));
              cvo.setBno(rs.getInt("bno"));
              cvo.setName(rs.getString("name"));
              cvo.setRcmdcalories(rs.getDouble("rcmdcalories"));
              cvo.setItkcalories(rs.getDouble("itkcalories"));
              cvo.setBndcalories(rs.getDouble("bndcalories"));
              cvo.setExcalories(rs.getDouble("excalories"));
              cvo.setVariation(rs.getDouble("variation"));
              
              list.add(cvo);  
		   }   
		      }catch(Exception e) {
		         e.printStackTrace();
		      }finally {
		         try {if(rs!=null) {rs.close();}
		             if(pstmt!=null) {pstmt.close();}
		              if(conn!=null) {conn.close();}
		         }catch(Exception e) {
		            e.printStackTrace();            
		         }
		      }
		return list;

	   }
	   
//	   @Override
//	   public List<DietmemberVO> DietMemberOneSelect() {
//	      Connection conn = null;
//	      PreparedStatement pstmt = null;
//	      ResultSet rs = null;
//	      List<DietmemberVO> list = new ArrayList<DietmemberVO>();
//	      String sql = "";
//	try {conn = ddbm.getConnection();
//	pstmt = conn.prepareStatement(sql);
//	         
//	      }catch(Exception e) {
//	         e.printStackTrace();
//	      }finally {
//	         try {if(rs!=null) {rs.close();}
//	             if(pstmt!=null) {pstmt.close();}
//	              if(conn!=null) {conn.close();}
//	         }catch(Exception e) {
//	            e.printStackTrace();
//	         }
//	      }
//	return list;
//	   }
	   
	   @Override
	public List<DietmemberVO> DietMemberRankSelect() {
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      List<DietmemberVO> list = new ArrayList<DietmemberVO>();
	      
	      String sql = "select c.bno, m.name, m.weight, sum(c.variation) as totvariation from tbl_member m, tbl_calories c " + 
	      		"where c.bno=m.bno " + "group by c.bno, m.name, m.weight " + "order by totvariation";
	      
	try {conn = ddbm.getConnection();
	     pstmt = conn.prepareStatement(sql);
	     rs = pstmt.executeQuery();
	     
	     while(rs.next()) {
	    	 DietmemberVO mvo = new DietmemberVO();
	    	 mvo.setBno(rs.getInt("bno"));
	    	 mvo.setName(rs.getString("name"));
	    	 mvo.setWeight(rs.getDouble("weight"));
	    	 mvo.setTotvariation(rs.getDouble("totvariation"));
	    	 double pweight = mvo.getWeight()+mvo.getTotvariation();
	    	 mvo.setPweight(pweight);
	    	 
	    	 list.add(mvo);
	     }
	         
	      }catch(Exception e) {
	         e.printStackTrace();
	      }finally {
	         try {if(rs!=null) {rs.close();}
	             if(pstmt!=null) {pstmt.close();}
	              if(conn!=null) {conn.close();}
	         }catch(Exception e) {
	            e.printStackTrace();
	         }
	      }
	return list;
    }
	   
	   @Override
	public List<DietmemberVO> DietMemberAllSelect() {
		      Connection conn = null;
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      String sql = "select * from tbl_member order by bno";
		      
		      List<DietmemberVO> list = new ArrayList<DietmemberVO>();

		      try {
		         conn = ddbm.getConnection();
		            pstmt = conn.prepareStatement(sql);
		            rs = pstmt.executeQuery();
		            
		            while(rs.next()) {
		               DietmemberVO dvo = new DietmemberVO();
		               dvo.setBno(rs.getInt("bno"));
		               dvo.setName(rs.getString("name"));
		               dvo.setGender(rs.getString("gender"));
		               dvo.setAge(rs.getInt("age"));
		               dvo.setHeight(rs.getInt("height"));
		               dvo.setWeight(rs.getInt("weight"));
		               dvo.setRcmdcalories(rs.getInt("rcmdcalories"));
		               list.add(dvo);
		            }
		         
		      }catch(Exception e) {
		         e.printStackTrace();
		      }finally {
		         try {if(rs!=null) {rs.close();}
		             if(pstmt!=null) {pstmt.close();}
		              if(conn!=null) {conn.close();}
		         }catch(Exception e) {
		            e.printStackTrace();
		         }
		      }
		      return list;
	    }
	   
	   @Override
	public DietmemberVO DietMemberOneSelect(int bno) {
		   Connection conn = null;
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      
		      DietmemberVO dvo = null;
		      //List<DietmemberVO> list = new ArrayList<DietmemberVO>();
		try {
		   conn = ddbm.getConnection();
		   
		   String sql = "select * from tbl_member where bno = ? order by bno";
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, bno);
		   rs = pstmt.executeQuery();

		   while(rs.next()) {
		         dvo = new DietmemberVO();
		         dvo.setBno(rs.getInt("bno"));
		         dvo.setName(rs.getString("name"));
		         dvo.setGender(rs.getString("gender"));
		         dvo.setAge(rs.getInt("age"));
		         dvo.setHeight(rs.getInt("height"));
		         dvo.setWeight(rs.getInt("weight"));
		         dvo.setRcmdcalories(rs.getInt("rcmdcalories"));
		      }   
		   } catch(Exception e) {
		         e.printStackTrace();
		      }finally {
		         try {if(rs!=null) {rs.close();}
		             if(pstmt!=null) {pstmt.close();}
		              if(conn!=null) {conn.close();}
		         }catch(Exception e) {
		            e.printStackTrace();            
		         }
		      }
		             return dvo;
	       }
	   
	   @Override
	public DietmemberVO memberInfoModify(int bno) {
		      Connection conn = null;
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      
		      String sql = "select * from tbl_member where bno = ?";
		      DietmemberVO dmvo = null;
		try { 
		   conn = ddbm.getConnection();
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, bno);
		   rs = pstmt.executeQuery();
		   
		   while(rs.next()) {
		      dmvo = new DietmemberVO();
		      dmvo.setBno(rs.getInt("bno"));
		      dmvo.setName(rs.getString("name"));
		      dmvo.setGender(rs.getString("gender"));
		      dmvo.setAge(rs.getInt("age"));
		      dmvo.setHeight(rs.getDouble("height"));
		      dmvo.setWeight(rs.getDouble("weight"));
		      dmvo.setRcmdcalories(rs.getDouble("rcmdcalories"));
		      //한사람만 리턴할 시에는 배열을 만들필요없이 VO객체로 리턴
		   }         
		      }catch(Exception e) {
		         e.printStackTrace();
		      }finally {
		         try {
		            if(pstmt!=null) {pstmt.close();}
		            if(conn!=null) {conn.close();}            
		         }catch(Exception e) {
		            e.printStackTrace();
		         }
		      }
		      return dmvo;
		   }

	@Override
		public int memberUpdate(DietmemberVO dmvo2) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	            
	    String sql = "update tbl_member set name = ?, gender = ?, age = ?, height = ?, weight = ? where bno = ?";
	    int result = 0; //리턴값을 통해 업뎃이 잘 됐는지 확인하기 위해 리절트 변수 선언
	    try {
	       conn = ddbm.getConnection();
	       pstmt=conn.prepareStatement(sql);
	       pstmt.setString(1, dmvo2.getName());
	       pstmt.setString(2, dmvo2.getGender());
	       pstmt.setInt(3, dmvo2.getAge());
	       pstmt.setDouble(4, dmvo2.getHeight());
	       pstmt.setDouble(5, dmvo2.getWeight());
	       pstmt.setInt(6, dmvo2.getBno());
	       result = pstmt.executeUpdate();
	         
	       RcdCaloriesCalc(dmvo2);
	      }catch(Exception e) {
	         e.printStackTrace();
	      }finally {
	         try {
	            if(pstmt!=null) {pstmt.close();}
	            if(conn!=null) {conn.close();}
	            
	         }catch(Exception e) {
	            e.printStackTrace();
	         }
	      }
	      return result;

		}	
	   
	
	   
	   @Override
	   public void DietMemberDelete() {
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      String answer = null;
	      try {conn = ddbm.getConnection();
	      
	      System.out.println("\n중도 포기할 경우 엄청난 패널티가 주어집니다.");
	      System.out.print("정말 포기하시겠습니까?[Y]/[N]: ");
	      answer = sc.next();
	      
	      DietpenaltyVO dpvo = new DietpenaltyVO();
	      
	      if(answer.equalsIgnoreCase("Y")) {
	         
	         System.out.println("(프로그램 회원 탈퇴와 함께 패널티가 보여집니다)");
	         System.out.print("참가번호 입력: ");
	         int bno = sc.nextInt();
	         String sql = "delete tbl_member where bno =? "; 
	         String sql1 = "delete tbl_calories where bno=?";
	         pstmt = conn.prepareStatement(sql); 
	         pstmt.setInt(1, bno);
	         pstmt.executeUpdate();
	         
	         pstmt.close();
	         
	         pstmt= conn.prepareStatement(sql1);
	         pstmt.setInt(1, bno);
	         int okdelete = pstmt.executeUpdate();
	         
	         if(okdelete>0) {
	        	 System.out.println("\n다이어트 챌린지 프로그램의 회원탈퇴가 완료되었습니다.");
		         System.out.println("당신의 패널티는...");
		         dpvo.setPname(penalty());
		         System.out.println(dpvo);
	         }else {
	        	 System.out.println("참가번호를 조회할 수 없습니다.");
	         }
	 
	         
	      }else{
	         System.out.println("\n잘 생각하셨습니다! 마지막까지 최선을 다 해주세요!");
	         System.out.println("미래의 몸짱 당신을 응원합니다★★★\n");
	      }         
	      }catch(Exception e) {
	         e.printStackTrace();
	      }finally {
	         try {
	            if(pstmt != null) {pstmt.close();}
	            if(conn != null) {conn.close();}            
	         }catch(Exception e) {
	            e.printStackTrace();
	         }
	      }
	   }
	   
	   @Override
	   public String penalty() {
	      Connection conn = null; //db접속
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      String sql = "select * from tbl_penalty where pbno = ?";
	      
	      int com =(int)(Math.random()*7)+1;
	      DietpenaltyVO dpvo = new DietpenaltyVO();
	      try {
	         conn = ddbm.getConnection();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, com);
	         rs = pstmt.executeQuery();

	         while(rs.next()) {
	            //dpvo.getPname();
	        	 dpvo.setPname(rs.getString("pname"));
	
	         }
	      }catch(Exception e) {
	         e.printStackTrace();
	      }finally {
	         try {
	            if(pstmt!=null) {pstmt.close();}
	            if(conn!=null) {conn.close();}
	         }catch(Exception e) {
	            e.printStackTrace();
	         }
	      }
	      return dpvo.getPname();
	      
	      
	   }

}
