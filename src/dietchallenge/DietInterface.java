package dietchallenge;

import java.util.List;

public interface DietInterface {
	
	   int DietMemberInsert(DietmemberVO dmvo);
	   int MaxBno();
	   String penalty();
	   void RcdCaloriesCalc(DietmemberVO dmvo);
	   int ItkCaloriesCalc(DietfoodsVO dfvo, DietcaloriesVO dcvo);
	   List<DietcaloriesVO> DietCurrentStatusAllSelect();
	   List<DietcaloriesVO> DietCurrentStatusOneSelect(int bno);
	   List<DietmemberVO> DietMemberAllSelect();
	   DietmemberVO DietMemberOneSelect(int bno);
	   List<DietmemberVO> DietMemberRankSelect();
	   DietmemberVO memberInfoModify(int bno);
	   int memberUpdate(DietmemberVO dmvo2);
	   void DietMemberDelete();

}
