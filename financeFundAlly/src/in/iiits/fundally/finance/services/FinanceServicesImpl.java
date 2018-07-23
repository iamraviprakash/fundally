package in.iiits.fundally.finance.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.iiits.fundally.finance.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.finance.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.finance.classes.DisplayRequest;
import in.iiits.fundally.finance.dao.FinanceDAOs;
import in.iiits.fundally.finance.entity.InstituteAccount;
import in.iiits.fundally.finance.entity.InstituteRequest;
import in.iiits.fundally.finance.entity.InstituteRequestStage;
import in.iiits.fundally.finance.entity.InstituteRequestStageType;
import in.iiits.fundally.finance.entity.InstituteTransaction;
import in.iiits.fundally.finance.entity.ProjectAccount;
import in.iiits.fundally.finance.entity.ProjectRequest;
import in.iiits.fundally.finance.entity.ProjectRequestStage;
import in.iiits.fundally.finance.entity.ProjectTransaction;
import in.iiits.fundally.finance.entity.Status;
import in.iiits.fundally.finance.entity.TransactionType;
import in.iiits.fundally.finance.entity.User;
import in.iiits.fundally.finance.entity.UserSession;

@Service
public class FinanceServicesImpl implements FinanceServices {

	@Autowired
	private FinanceDAOs financeDAOs;
	
	// common
	
	@Transactional
	@Override
	public String checkSessionStatus(String sessionId, String userType) {
		
		UserSession userSession = financeDAOs.getSession(sessionId);
		if(userSession != null){
			if(userSession.getStatus().equals(Status.ACTIVE) && userSession.getUser().getType().equals(userType)) 
				return "ACTIVE";
			else
				return "INACTIVE";
		}
		else
			return "INACTIVE";
	}

	@Override
	@Transactional
	public User getUser(String sessionId) {
		UserSession session = financeDAOs.getSession(sessionId);
		return session.getUser();
	}
	
	@Transactional
	@Override
	public List<DisplayRequest> getAllDisplayRequestList() {
		
		// for purchased resource
		InstituteRequestStageType stageType = financeDAOs.getStageType("RP");
		List<InstituteRequest> instituteRequestList = financeDAOs.getInstituteRequestListByStageType(stageType);

		// for intangible request
		stageType = financeDAOs.getStageType("RA");
		instituteRequestList.addAll(financeDAOs.getInstituteRequestListByStageType(stageType));
		
		List<DisplayRequest> displayRequestList = new ArrayList<DisplayRequest>();
		
		DisplayRequest displayRequest = null;
		
		for(InstituteRequest r: instituteRequestList) {
			
			displayRequest = new DisplayRequest();
			
			displayRequest.setRequestSource("Institute");
			displayRequest.setAmount(r.getResource().getAmount());
			displayRequest.setRequesterEmailId(r.getAccount().getUser().getEmailId());
			displayRequest.setDeadline(r.getResource().getDeadlineDate());
			displayRequest.setRequestId(r.getId());
			displayRequest.setResourceId(r.getResource().getId());
			displayRequest.setResourceName(r.getResource().getName());
			displayRequest.setStatus(r.getStatus().name());
			displayRequest.setStage(r.getStageType().getName());
			displayRequest.setType(r.getResource().getType().name());
			
			displayRequestList.add(displayRequest);
		}
		
		stageType = financeDAOs.getStageType("RP");
		List<ProjectRequest> projectRequestList = financeDAOs.getProjectRequestListByStageType(stageType);
		
		stageType = financeDAOs.getStageType("RA");
		projectRequestList.addAll(financeDAOs.getProjectRequestListByStageType(stageType));
		
		for(ProjectRequest r: projectRequestList) {
			
			displayRequest = new DisplayRequest();
			
			displayRequest.setRequestSource("Project");
			displayRequest.setAmount(r.getResource().getAmount());
			displayRequest.setRequesterEmailId(r.getUser().getEmailId());
			displayRequest.setDeadline(r.getResource().getDeadlineDate());
			displayRequest.setRequestId(r.getId());
			displayRequest.setResourceId(r.getResource().getId());
			displayRequest.setResourceName(r.getResource().getName());
			displayRequest.setStatus(r.getStatus().name());
			displayRequest.setStage(r.getStageType().getName());
			displayRequest.setType(r.getResource().getType().name());
			
						
			displayRequestList.add(displayRequest);
		}
		
		return displayRequestList;
	}

	@Transactional
	@Override
	public String getRequestStageId(String requestId) {
		// get Request
		InstituteRequest instituteRequest = financeDAOs.getInstituteRequest(requestId);
		ProjectRequest projectRequest = financeDAOs.getProjectRequest(requestId);
		
		if(instituteRequest != null)
			// return stage
			return instituteRequest.getStageType().getId();
		else if(projectRequest != null)
			return projectRequest.getStageType().getId();
		else
			return null;
	}

	@Transactional
	@Override
	public String payRequest(String source, String requestId) {
		// get Request
		InstituteRequest instituteRequest = financeDAOs.getInstituteRequest(requestId);
		
		ProjectRequest projectRequest = financeDAOs.getProjectRequest(requestId);
		
		// current timestamp
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
		
		if(instituteRequest != null && source.equals("institute")) {
			// get accountNo
			InstituteAccount account = instituteRequest.getAccount();
			
			// get amount
			Double resourceAmount = Double.parseDouble(instituteRequest.getResource().getAmount());
			Double currentBalance = Double.parseDouble(account.getBalance());
			Double difference = currentBalance - resourceAmount;
			
			if(difference >= 0.00) {
				// deduct amount 
				account.setBalance(difference.toString());
				instituteRequest.setAccount(account);
				
				// add to transaction
				InstituteTransaction transaction = new InstituteTransaction();
				transaction.setOpeningBalance(currentBalance.toString());
				transaction.setClosingBalance(difference.toString());
				
				transaction.setDate(timestamp.toString());
				transaction.setType(TransactionType.DEBIT);
				transaction.setRequest(instituteRequest);
				account.add(transaction);
				
				// add to stage and set stage AP
				// get Stage Type
				InstituteRequestStageType stageType = financeDAOs.getStageType("AP");
					
				// create Request Stage Instance
				InstituteRequestStage stage = new InstituteRequestStage();
				stage.setRequest(instituteRequest);
				stage.setStageType(stageType);
				
				// set and update request
				instituteRequest.setStageType(stageType);
				instituteRequest.add(stage);
				
				// add finished stage
				// create Request Stage Instance
				stageType = financeDAOs.getStageType("CMPL");
				
				stage = new InstituteRequestStage();
				stage.setRequest(instituteRequest);
				stage.setStageType(stageType);
				
				instituteRequest.setEndDate(timestamp.toString());
				instituteRequest.setStageType(stageType);
				instituteRequest.setStatus(Status.FINISHED);
				instituteRequest.add(stage);
				
				// return transaction Id
				return "Transaction Successfull! <br>Request Id:" + instituteRequest.getId() + "<br>Transacton Id:" + transaction.getId()+"<br>Request Closed!";
			}
			else 
				return "Insufficient Balance!";
		}
		else if(projectRequest != null && source.equals("project")) {
			
			// get accountNo
			ProjectAccount account = projectRequest.getAccount();
			
			// get amount
			Double resourceAmount = Double.parseDouble(projectRequest.getResource().getAmount());
			Double currentBalance = Double.parseDouble(account.getBalance());
			Double difference = currentBalance - resourceAmount;
			
			if(difference >= 0.00) {
				// deduct amount 
				account.setBalance(difference.toString());
				projectRequest.setAccount(account);
				
				// add to transaction
				ProjectTransaction transaction = new ProjectTransaction();
				transaction.setOpeningBalance(currentBalance.toString());
				transaction.setClosingBalance(difference.toString());
				
				transaction.setDate(timestamp.toString());
				transaction.setType(TransactionType.DEBIT);
				transaction.setRequest(projectRequest);
				account.add(transaction);
				
				// add to stage and set stage AP
				// get Stage Type
				InstituteRequestStageType stageType = financeDAOs.getStageType("AP");
					
				// create Request Stage Instance
				ProjectRequestStage stage = new ProjectRequestStage();
				stage.setRequest(projectRequest);
				stage.setStageType(stageType);
				
				// set and update request
				projectRequest.setStageType(stageType);
				projectRequest.add(stage);
				
				// add finished stage
				// create Request Stage Instance
				stageType = financeDAOs.getStageType("CMPL");
				
				stage = new ProjectRequestStage();
				stage.setRequest(projectRequest);
				stage.setStageType(stageType);
				
				projectRequest.setEndDate(timestamp.toString());
				projectRequest.setStageType(stageType);
				projectRequest.setStatus(Status.FINISHED);
				projectRequest.add(stage);
				
				// return transaction Id
				return "Transaction Successfull! <br>Request Id:" + projectRequest.getId() + "<br>Transacton Id:" + transaction.getId()+"<br>Request Closed!";
			}
			else 
				return "Insufficient Balance!";
		}
		else
			return "Invalid Request!";
	}
	
	
	// institute
	
	@Transactional
	@Override
	public DisplayInstituteRequestProfile getDisplayInstituteRequestProfile(String requestId) {
		
		DisplayInstituteRequestProfile profile = new DisplayInstituteRequestProfile();
		
		// get Request
		InstituteRequest request = financeDAOs.getInstituteRequest(requestId);
		
		if(request != null) {
			// set Request Profile
			profile.setInstituteAccount(request.getAccount());
			profile.setUser(request.getAccount().getUser());
			profile.setInstituteQuotationList(financeDAOs.getInstituteQuotationList(request));
			profile.setInstituteRequest(request);
			profile.setInstituteRequestStageList(financeDAOs.getInstituteRequestStageList(request));
			profile.setInstituteResource(request.getResource());
			profile.setInstituteResourceDocumentList(request.getResource().getResourceDocumentList());		
			profile.setInstituteTransaction(request.getTransaction());
		}
		
		return profile;
	}
	
	
	// project
	
	@Transactional
	@Override
	public DisplayProjectRequestProfile getDisplayProjectRequestProfile(String requestId) {
		
		DisplayProjectRequestProfile profile = new DisplayProjectRequestProfile();
		
		// get Request
		ProjectRequest request = financeDAOs.getProjectRequest(requestId);
		
		if(request != null) {
			// set Request Profile
			profile.setProject(request.getAccount().getProject());
			profile.setProjectAccount(request.getAccount());
			profile.setUser(request.getUser());
			profile.setProjectQuotationList(financeDAOs.getProjectQuotationList(request));
			profile.setProjectRequest(request);
			profile.setProjectRequestStageList(financeDAOs.getProjectRequestStageList(request));
			profile.setProjectResource(request.getResource());
			profile.setProjectResourceDocumentList(request.getResource().getResourceDocumentList());		
			profile.setProjectTransaction(request.getTransaction());
		}
		
		return profile;
	}

}
