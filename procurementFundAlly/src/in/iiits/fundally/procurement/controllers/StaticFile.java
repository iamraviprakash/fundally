package in.iiits.fundally.procurement.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import in.iiits.fundally.procurement.services.ProcurementServices;

@Controller
@RequestMapping("/static")
public class StaticFile {

	@Autowired
	private ProcurementServices procurementServices;
	
	@GetMapping("/pdf/{source:institute|project}/{docType:quotation|resourceDocument}/{documentId}/session/{sessionId}")
	public ResponseEntity<Resource> showPdf(@PathVariable String sessionId, Model model, @PathVariable String documentId, @PathVariable String docType, @PathVariable String source) {
		
		// Dealing with cookie
		if(procurementServices.checkSessionStatus(sessionId).equals("INACTIVE")) {
			//redirect to login page 
			return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
		}	

		try {
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        headers.add("Pragma", "no-cache");
	        headers.add("Expires", "0");
			
	        String filePath = null;
	        
	        if(docType.equals("resourceDocument")) {
	        	
	        	filePath = procurementServices.getResourceDocumentPath(source, documentId);
	        }
	        else if(docType.equals("quotation")){
	        	
	        	filePath = procurementServices.getQuotationPath(source, documentId);
	        }
	        
	        File file = new File(filePath);
	        
			Path path = Paths.get(filePath);
		    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
	
		    return ResponseEntity.ok()
		            .headers(headers)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/pdf"))
		            .body(resource);
		}
		catch(Exception e) {
			
			e.printStackTrace();
			
			return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
		}
	}    
}
