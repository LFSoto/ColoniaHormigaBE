package com.hormiguero.reina.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hormiguero.exception.EndpointError;
import com.hormiguero.reina.entity.EntornoEntity;
import com.hormiguero.reina.entity.HormigaEntity;
import com.hormiguero.reina.service.ExternalHormigueroService;
import com.hormiguero.reina.service.ReinaService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@OpenAPIDefinition(info = @Info(title = "Subsistema Reina", 
								description = "Subsistema encargado de crear, reubicar y sepultar hormigas.", 
								version = "v1.2",
								contact = @Contact(name = "Grupo #3 Subsistema Reina | Josue Quirós, Amanda Bermúdez, Diego Cabezas y Luis Felipe Soto ", email = "Josue Alexander Quirós Abarca <jaquirosa@ucenfotec.ac.cr>; Amanda Paulina Bermúdez Méndez <abermudezm@ucenfotec.ac.cr>; Diego Cabezas Durán <dcabezasd@ucenfotec.ac.cr>; Luis Felipe Soto Cruz <lsotocr@ucenfotec.ac.cr>")))
public class HormigaController {

	@Autowired
    private ReinaService reina;
	
	@Autowired
	private ExternalHormigueroService endpoint;

	
	@Operation(summary = "Crear hormigas según el tipo y la cantidad parametrizada.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Hormiga creada exitosamente.",
					content = {
							@Content(mediaType = "application/json", 
									schema = @Schema(implementation = HormigaEntity.class)
					)}),
			@ApiResponse(responseCode = "500", description = "Subsistema Entorno y/o Comida están fuera de línea.", content = @Content)})
    @GetMapping("/v1/getHormiga")
    public List<HormigaEntity> getHormiga(@RequestParam int cantidad, @RequestParam String tipo) throws Exception {
		return reina.getHormigas(cantidad, tipo);
    }

	
	@Operation(summary = "Liberar hormigas para ser reutilizadas.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Libera una lista de hormigas para ser reutilizadas.", content = @Content)			
	})
    @PostMapping(value = "/v1/releaseHormiga", consumes = {"application/JSON"})
    public void releaseHormigas(@Parameter(description = "Lista de hormigas a liberar") @RequestBody HormigaEntity[] hormigas) {
    	reina.releaseHormigas(hormigas);
    }
	

	@Operation(summary = "Sepultar hormigas en el olvido.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sepulta una lista de hormigas.", content = @Content)
	})
    @PostMapping(value = "/v1/killHormiga", consumes = {"application/JSON"})
    public void killHormigas(@Parameter(description = "Lista de hormigas a sepultar") @RequestBody HormigaEntity[] hormigas) {
		reina.killHormigas(hormigas);
    }
    
	
	@Operation(summary = "Diagnosticar cuántas hormigas han sido creadas.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Reporta todas las hormigas existentes en el hormiguero.",
					content = {
							@Content(mediaType = "application/json", 
									schema = @Schema(implementation = HormigaEntity.class)
					)})
	})
    @GetMapping("/v1/listAll")
    public List<HormigaEntity> listAll() {
    	return this.reina.listAll();
    }
    
	
	@Operation(summary = "Diagnosticar la integración con el Subsistema de Entorno")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna la cantidad de comida requerida para crear una nueva hormiga.", 
					content = { 
							@Content(mediaType = "application/json", 
							schema = @Schema(implementation = EntornoEntity.class)
			)}),
			@ApiResponse(responseCode = "500", description = "El Subsistema de Entorno se encuentra fuera de línea o mal configurado en el Subsistema Reina.", content = @Content)
	})
    @GetMapping("/v1/entorno/foodCost")
    public int getFoodCost() throws Exception{
    	return this.endpoint.getHormigaCost();
    }
	
    
	@Operation(summary = "Diagnosticar la integración con el Subsistema de Recolección de Comida")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna la cantidad de comida disponible en el Subsistema de Recolección de Comida.", content = @Content),
			@ApiResponse(responseCode = "500", description = "El Subsistema de Recolección de Comida se encuentra fuera de línea o mal configurado en el Subsistema Reina.", content = @Content)
	})
    @GetMapping("/v1/comida/foodAvailable")
    public int getFoodAvailable() throws Exception {
    	return this.endpoint.getFoodAvailable();
    }
    
	
    @GetMapping("/swagger")
    public ModelAndView redirectToSwagger(ModelMap model) {
    	
    	return new ModelAndView("redirect:/swagger-ui/index.html", model);
    }
    
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<EndpointError> handleAll(Exception ex, WebRequest request) {
        EndpointError apiError = new EndpointError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<EndpointError>(apiError, null, apiError.getStatus());
    }
}
