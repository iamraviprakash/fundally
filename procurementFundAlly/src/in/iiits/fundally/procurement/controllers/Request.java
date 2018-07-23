package in.iiits.fundally.procurement.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import in.iiits.fundally.procurement.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.procurement.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.procurement.classes.DisplayRequest;
import in.iiits.fundally.procurement.entity.User;
import in.iiits.fundally.procurement.services.ProcurementServices;

@Controller
@RequestMapping("/request")
public class Request {
	
	@Autowired
	private ProcurementServices procurementServices;
	
	@GetMapping("/")
	public String showRequestPage(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || procurementServices.checkSessionStatus(sessionId, "procurement").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}
	
		User user = procurementServices.getUser(sessionId);
		
		// get the List of Request, New first.
		List<DisplayRequest> displayRequestList = procurementServices.getAllDisplayRequestList();
		
		// bind the data to the model
		model.addAttribute("displayRequestList", displayRequestList);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		
		return "requestSection";

	}
	
	@GetMapping("/{source:institute|project}/{requestId}/view")
	public String showRequestProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String requestId, @PathVariable String source, @RequestParam(defaultValue="") String message) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || procurementServices.checkSessionStatus(sessionId, "procurement").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}	
		
		User user = procurementServices.getUser(sessionId);
		
		String approveUrl = null;
		String uploadForm = null;
		String backUrl = "<a href=\"/procurementFundAlly/request/\">Back To List</a>";
		String stageId = procurementServices.getRequestStageId(requestId);
		
		if(stageId != null && stageId.equals("RV")) {
			approveUrl = "<a href=\"approve\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Approve</a>";
			uploadForm = "<form enctype=\"multipart/form-data\" style=\"display: inline-block; margin:10px; padding: 5px;\" action=\"document/upload\" method=\"post\"><input style=\"border: #536DFE; margin:5px; \" type=\"file\" name=\"file\"/><input style=\" margin:5px; padding: 5px; background: #536DFE; border: 0px; max-width: 150px; color: white;\" type=\"submit\" name=\"submit\"value=\"Upload Quotation\"/></form>";
		}
		else if(stageId != null && stageId.equals("QA")) {
			approveUrl = "<a href=\"approve\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Approve</a>";
			uploadForm = "<form enctype=\"multipart/form-data\" style=\"display: inline-block; margin:10px; padding: 5px;\" action=\"document/upload\" method=\"post\"><input style=\"border: #536DFE; margin:5px; \" type=\"file\" name=\"file\"/><input style=\" margin:5px; padding: 5px; background: #536DFE; border: 0px; max-width: 150px; color: white;\" type=\"submit\" name=\"submit\"value=\"Upload Documents\"/></form>";
		}
		else if(stageId != null && stageId.equals("IRQ")) {
			approveUrl = "<a href=\"approve\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Replace Resource</a>";
			uploadForm = "No option available!";
		}
		else { 
			uploadForm = "No option available!";
			approveUrl = "No option available!";
		}
		
		if(source.equals("institute")) {
			
			DisplayInstituteRequestProfile displayInstituteRequestProfile = procurementServices.getDisplayInstituteRequestProfile(requestId);
			
			if(displayInstituteRequestProfile != null) {
				
				model.addAttribute("account", displayInstituteRequestProfile.getInstituteAccount());
				model.addAttribute("user", displayInstituteRequestProfile.getUser());
				model.addAttribute("resource", displayInstituteRequestProfile.getInstituteResource());
				model.addAttribute("request", displayInstituteRequestProfile.getInstituteRequest());
				model.addAttribute("transaction", displayInstituteRequestProfile.getInstituteTransaction());
				model.addAttribute("requestStageList", displayInstituteRequestProfile.getInstituteRequestStageList());
				model.addAttribute("quotationList", displayInstituteRequestProfile.getInstituteQuotationList());
				model.addAttribute("resourceDocumentList", displayInstituteRequestProfile.getInstituteResourceDocumentList());
				model.addAttribute("source", "institute");
			}
		}
		else if(source.equals("project")) {
			
			DisplayProjectRequestProfile displayProjectRequestProfile = procurementServices.getDisplayProjectRequestProfile(requestId);
			
			if(displayProjectRequestProfile != null) {
				
				model.addAttribute("project", displayProjectRequestProfile.getProject());
				model.addAttribute("account", displayProjectRequestProfile.getProjectAccount());
				model.addAttribute("user", displayProjectRequestProfile.getUser());
				model.addAttribute("resource", displayProjectRequestProfile.getProjectResource());
				model.addAttribute("request", displayProjectRequestProfile.getProjectRequest());
				model.addAttribute("transaction", displayProjectRequestProfile.getProjectTransaction());
				model.addAttribute("requestStageList", displayProjectRequestProfile.getProjectRequestStageList());
				model.addAttribute("quotationList", displayProjectRequestProfile.getProjectQuotationList());
				model.addAttribute("resourceDocumentList", displayProjectRequestProfile.getProjectResourceDocumentList());
				model.addAttribute("source", "project");
			}
		}
		
		model.addAttribute("approveUrl", approveUrl);
		model.addAttribute("uploadForm", uploadForm);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("message", message);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("sessionId", sessionId);
				
		return "requestProfile";
	}
	
	@GetMapping("/{source:institute|project}/{requestId}/approve")
	public String approveRequest(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String source, @PathVariable String requestId) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || procurementServices.checkSessionStatus(sessionId, "procurement").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}	
		
		String message = procurementServices.approveRequest(source, requestId);
		
		model.addAttribute("message", message);
		
		return "redirect:/request/" + source + "/" + requestId + "/view";
	}
	
	@PostMapping("/{source:institute|project}/{requestId}/document/upload")
	public String uploadDocument(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String requestId, @PathVariable String source, @RequestParam("file") MultipartFile file) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || procurementServices.checkSessionStatus(sessionId, "procurement").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}	
		
		//String currentPath = Paths.get("").toAbsolutePath().toString() + "/eclipse-workspace";
		
		String currentPath = "/opt/tomcat/webapps";
		
		String UPLOAD_ROOT_QUOTATION = currentPath + "/procurementFundAlly/WebContent/resources/uploads/quotations";
		String UPLOAD_ROOT_RESOURCEDOCUMENT = currentPath + "/procurementFundAlly/WebContent/resources/uploads/resourceDocuments";
		String currentFilePath = null;
		String message = null;
		
		String stageId = procurementServices.getRequestStageId(requestId);
		
		if(!file.isEmpty()) {
			try {
				
				if(stageId != null && stageId.equals("RV")) {
					
					currentFilePath = UPLOAD_ROOT_QUOTATION;
					
					// save quotation details
					message = procurementServices.saveQuotation(source, requestId, currentFilePath);
					// delete file is the request Id is invalid
					
					currentFilePath = UPLOAD_ROOT_QUOTATION + "/" + message;
					Files.copy(file.getInputStream(), Paths.get(currentFilePath));
					
					if(message.equals("Invalid Request!"))
						Files.deleteIfExists(Paths.get(currentFilePath));
					else
						message = "Quotation Uploaded Successfully!";
				} 
				else if(procurementServices.getRequestStageId(requestId).equals("QA")) {
					
					currentFilePath = UPLOAD_ROOT_RESOURCEDOCUMENT;
					
					// save resource document details
					message = procurementServices.saveResourceDocument(source, requestId, currentFilePath);
					
					currentFilePath = UPLOAD_ROOT_RESOURCEDOCUMENT + "/" + message;
					Files.copy(file.getInputStream(), Paths.get(currentFilePath));
					
					// delete file is the request Id is invalid
					if(message.equals("Invalid Request!"))
						Files.deleteIfExists(Paths.get(currentFilePath));
					else
						message = "Resource Document Uploaded Successfully!";
				}
			}
			catch(IOException | RuntimeException e) {
				message = "Fail To Upload!";
				System.out.println(message + " " + e.getMessage() );
			}
		}
		else 
			message = "Fail To Upload!";

		model.addAttribute("message", message);
		
		return "redirect:/request/" + source + "/" + requestId + "/view";
	}
	
	
	@GetMapping("/redirectToLogin")
	public ModelAndView redirectToLoginPage(Model model){
		
		String LoginPageUrl = "http://accounts.fundally.iiits.ac.in/auth/procurement";
		return new ModelAndView("redirect:" + LoginPageUrl);
	}
	
}
