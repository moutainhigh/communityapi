package net.okdi.apiV4.service;

import java.util.List;
import java.util.Map;

public interface ProblemPackageService {

	List<Map<String,Object>> queryProblemPackageList(Long actualSendMember);

	void probPackAssignAgain(String parIds, Long memberId, String memberPhone);

	void probPackBackComp(String parIds);
	
	public void addParcelRelation(Long oldParId, Long newParId, Short operationType,Long oldMemberId,Long newMemberId);
	
	public void copyParcel(String parId,Long newId,Long compId,Long netId,Long actualSendMember,String type);

	public void addParTaskProcess(Long taskId, Long fromCompId,Long fromMemberId, Long toCompId, Long toMemberId,
			Integer taskStatus, Integer taskTransmitCause, Long operatorId, Long operatorCompId, String operatorDesc,String taskProcessDesc);
}
