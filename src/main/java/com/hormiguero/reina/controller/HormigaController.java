package com.hormiguero.reina.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.hormiguero.reina.service.HormigueroUris;
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
	
	private Logger log = LoggerFactory.getLogger(HormigaController.class);

	
	@Operation(summary = "Crear hormigas según el tipo y la cantidad parametrizada.", tags = "Hormiguero")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Hormiga creada exitosamente.",
					content = {
							@Content(mediaType = "application/json", 
									schema = @Schema(implementation = HormigaEntity.class)
					)}),
			@ApiResponse(responseCode = "500", description = "Subsistema Entorno y/o Comida están fuera de línea.", content = @Content)})
    @GetMapping("/v1/getHormiga")
    public List<HormigaEntity> getHormiga(@RequestParam int cantidad, @RequestParam String tipo) throws Exception {
		long id = System.currentTimeMillis();
		log.info("[HORMIGUERO] RequestId: {}. Se han solicitado {} hormigas de tipo {}.", id, cantidad, tipo);
		List<HormigaEntity> result = reina.getHormigas(cantidad, tipo);
		log.info("[HORMIGUERO] RequestId: {}. Se logró crear {} hormigas de tipo {}.", id, result.size(), tipo);
		return result;
    }

	
	@Operation(summary = "Liberar hormigas para ser reutilizadas.", tags = "Hormiguero")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Libera una lista de hormigas para ser reutilizadas.", content = @Content)			
	})
    @PostMapping(value = "/v1/releaseHormiga", consumes = {"application/JSON"})
    public void releaseHormigas(@Parameter(description = "Lista de hormigas a liberar") @RequestBody HormigaEntity[] hormigas) {
		if (hormigas.length > 0) {
	    	reina.releaseHormigas(hormigas);
	    	log.info("[HORMIGUERO] Se han liberado {} hormigas de tipo {}.", hormigas.length, hormigas[0].getType());
		}
		else {
			log.warn("[HORMIGUERO] La lista de hormigas a liberar se encuentra vacía.");
		}
    }
	

	@Operation(summary = "Sepultar hormigas en el olvido.", tags = "Hormiguero")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sepulta una lista de hormigas.", content = @Content)
	})
    @PostMapping(value = "/v1/killHormiga", consumes = {"application/JSON"})
    public void killHormigas(@Parameter(description = "Lista de hormigas a sepultar") @RequestBody HormigaEntity[] hormigas) {
		if (hormigas.length > 0) {
			reina.killHormigas(hormigas);
			log.info("[HORMIGUERO] Se han sepultado {} hormigas de tipo {}.", hormigas.length, hormigas[0].getType());
		}
		else {
			log.warn("[HORMIGUERO] La lista de hormigas a sepultar se encuentra vacía.");
		}
	}
    
	
	@Operation(summary = "Diagnosticar cuántas hormigas han sido creadas.", tags = "Diagnóstico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Reporta todas las hormigas existentes en el hormiguero.",
					content = {
							@Content(mediaType = "application/json", 
									schema = @Schema(implementation = HormigaEntity.class)
					)})
	})
    @GetMapping("/v1/listAll")
    public List<HormigaEntity> listAll() {
		List<HormigaEntity> all = this.reina.listAll();
		log.info("[DIAGNOSTICO] Se solicitó listar todas las hormigas. Encontradas: {}", all.size());
		return all;
    }
    
	
	@Operation(summary = "Diagnosticar la integración con el Subsistema de Entorno", tags = "Diagnóstico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna la cantidad de comida requerida para crear una nueva hormiga.", 
					content = { 
							@Content(mediaType = "application/json", 
							schema = @Schema(implementation = EntornoEntity.class)
			)}),
			@ApiResponse(responseCode = "500", description = "El Subsistema de Entorno se encuentra fuera de línea o mal configurado en el Subsistema Reina.", content = @Content)
	})
    @GetMapping("/v1/entorno/foodCost")
    public int getFoodCost() {
		int result = this.endpoint.getHormigaCost();
		log.info("[DIAGNOSTICO] Se solicitó verificar la integración con Subsistema Entorno. Costo: {}", result);
		return result;
    }
	
    
	@Operation(summary = "Diagnosticar la integración con el Subsistema de Recolección de Comida", tags = "Diagnóstico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna la cantidad de comida disponible en el Subsistema de Recolección de Comida.", content = @Content),
			@ApiResponse(responseCode = "500", description = "El Subsistema de Recolección de Comida se encuentra fuera de línea o mal configurado en el Subsistema Reina.", content = @Content)
	})
    @GetMapping("/v1/comida/foodAvailable")
    public int getFoodAvailable() {
		int result = this.endpoint.getFoodAvailable();
		log.info("[DIAGNOSTICO] Se solicitó verificar la integración con Subsistema Recolección de Comida. Comida disponible: {}", result);
    	return result;
    }
    
	
    @GetMapping("/swagger")
    public ModelAndView redirectToSwagger(ModelMap model) {
    	log.info("[DIAGNOSTICO] Se esta redireccionando al Swagger UI");
    	return new ModelAndView("redirect:/swagger-ui/index.html", model);
    }
    
    
    @Operation(summary = "Retorna el endpoint configurado para Recolección de Comida", tags = {"Configuración","Comida"})
    @GetMapping("/config/comida")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Retorna el endpoint configurado para el Subsistema de Recolección de Comida", content = @Content)
    })
    public String getConfigComida() {
    	String result = HormigueroUris.getInstance().getUrl(HormigueroUris.SubSistemas.COMIDA);
    	log.info("[CONFIG] Se ha solicitado el endpoint de Recolección de Comida: {}", result);
    	return result;
    }
    
    
    @Operation(description = "Reconfigura el endpoint de Recolección de Comida", tags = {"Configuración","Comida"})
    @PostMapping("/config/comida")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Configura el nuevo endpoint para el Subsistema de Recolección de Comida", content = @Content)
    })
    public boolean putConfigComida(@Parameter(description = "El nuevo endpoint") @RequestBody String url) {
    	boolean result = HormigueroUris.getInstance().setUrl(HormigueroUris.SubSistemas.COMIDA, url);
    	log.warn("[CONFIG] El endpoint  de Recolección de Comida ha sido actualizado ( resultado = {} ) a apuntar  {}", result, url);
    	return result;
    }

    
    @Operation(description = "Retorna el endpoint configurado para el Subsistema de Entorno", tags = {"Configuración","Entorno"})
    @GetMapping("/config/entorno")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Retorna el endpoint configurado para el Subsistema de Entorno", content = @Content)
    })
    public String getConfigEntorno() {
    	String result = HormigueroUris.getInstance().getUrl(HormigueroUris.SubSistemas.ENTORNO);
    	log.info("[CONFIG] Se ha solicitado el endpoint de Entorno: {}", result);
    	return result;
    }
    
    
    @Operation(description = "Reconfigura el endpoint de Entorno", tags = {"Configuración","Entorno"})
    @PostMapping("/config/entorno")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Configura el nuevo endpoint para el Subsistema de Entorno", content = @Content)
    })
    public boolean putConfigEntorno(@Parameter(description = "El nuevo endpoint") @RequestBody String url) {
    	boolean result = HormigueroUris.getInstance().setUrl(HormigueroUris.SubSistemas.ENTORNO, url);
    	log.warn("[CONFIG] El endpoint  de Entorno ha sido actualizado ( resultado = {} ) a apuntar  {}", result, url);
    	return result;
    }
    
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<EndpointError> handleAll(Exception ex, WebRequest request) {
        EndpointError apiError = new EndpointError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<>(apiError, null, apiError.getStatus());
    }
}
