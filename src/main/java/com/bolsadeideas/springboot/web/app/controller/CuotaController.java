package com.bolsadeideas.springboot.web.app.controller;

import com.bolsadeideas.springboot.web.app.models.Cuota;
import com.bolsadeideas.springboot.web.app.models.CursoHabilitado;
import com.bolsadeideas.springboot.web.app.models.Materia;
import com.bolsadeideas.springboot.web.app.utils.ConfigManager;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cuotas")
public class CuotaController{

    @GetMapping("/listar")
    public String listarCuota(Model model) {
        CuotaManager cuotaManager = new CuotaManager();
        List<Cuota> cuotas = cuotaManager.getAllCuota();
        model.addAttribute("titulo", "Lista de Cuotas");
        model.addAttribute("idcuota", "ID cuota");
        model.addAttribute("fecha", "Fecha");
        model.addAttribute("fechavencimiento", "Fecha Vencimiento");
        model.addAttribute("idinscripcion", "ID Inscripcion");
        model.addAttribute("monto", "Monto a Pagar");
        model.addAttribute("pagos", "Pagos");
        model.addAttribute("cuotas", cuotas);
        return "cuota-template/listar";
    }
    
    @GetMapping("/agregar")
    public String agregarCuota(Model model) {
        Cuota cuota = new Cuota();
        model.addAttribute("titulo", "Agregar Cuota");
        model.addAttribute("cuota", cuota);
        model.addAttribute("error", new HashMap<>());
        return "cuota-template/agregar";
    }

    @PostMapping("/agregar")
    public String agregarCuotaProc(@Valid Cuota cuota, BindingResult result, Model model,
                                   @RequestParam(name="idinscripcion") int idinscripcion) throws SQLException {
        model.addAttribute("titulo", "Falta datos");
        if(result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("error", errores);
            return "cuota-template/agregar";
        }
        CuotaManager cuotaManager = new CuotaManager();
        cuota = cuotaManager.add(idinscripcion);
        model.addAttribute("idcuota", "ID Cuota");
        model.addAttribute("fecha", "Fecha actual");
        model.addAttribute("fechavencimiento", "Fecha vencimiento");
        model.addAttribute("idinscripcion", "ID inscripcion");
        model.addAttribute("monto", "Monto");
        model.addAttribute("pagos", "Pagos");
        model.addAttribute("cuota", cuota);
        return "cuota-template/resultado";
    }
    
    
    @GetMapping("/buscar")
    public String buscarCuota(Model model) {
        Cuota cuota = new Cuota();
        model.addAttribute("titulo", "Buscar Cuota");
        model.addAttribute("cuota", cuota);
        model.addAttribute("error", new HashMap<>());
        return "cuota-template/buscar";
    }

    @PostMapping("/buscar")
    public String buscarPro(@Valid Cuota cuota, BindingResult result, Model model,
                                 @RequestParam(name= "idcuota") int idcuota) throws SQLException {

        if(result.hasGlobalErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("titulo", "Debe ser numero entero");
            model.addAttribute("error", errores);
            return "cuota-template/buscar";
        }
        
        CuotaManager cuotaManager = new CuotaManager();
        cuota = cuotaManager.getByid(idcuota);
        
        model.addAttribute("idcuota", "ID Cuota");
        model.addAttribute("fecha", "Fecha");
        model.addAttribute("fechavencimiento", "Fecha de Vencimiento");
        model.addAttribute("idinscripcion", "ID Inscripcion");
        model.addAttribute("monto", "Monto");
        model.addAttribute("pagos", "Pagos");
        model.addAttribute("titulo", "Cuota Encontrada");
        model.addAttribute("cuota", cuota);
        return "cuota-template/resultado";
    }
    
    @GetMapping("/modificar")
    public String modificarCuota(Model model) {
        Cuota cuota = new Cuota();
        model.addAttribute("titulo", "Modificar inscripcion Habilitado");
        model.addAttribute("cuota", cuota);
        model.addAttribute("error", new HashMap<>());
        return "cuota-template/modificar";
    }

    @PostMapping("/modificar")
    public String modificarCuotaProc(@Valid Cuota cuota, BindingResult result, Model model,
                            @RequestParam(name="idcuota") int idcuota,
                            @RequestParam(name="monto") int monto,
                            @RequestParam(name="pagos") int pagos ) {
        model.addAttribute("titulo", "Falta datos");
        if(result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("titulo", "Modificar inscripcion Habilitado");
            model.addAttribute("error", errores);
            return "cuota-template/modificar";
        }
        CuotaManager cuotaManager = new CuotaManager();
        cuotaManager.modify(idcuota,monto,pagos);
        cuota.setIdcuota(idcuota);
        cuota.setMonto(monto);
        cuota.setPagos(pagos);
        model.addAttribute("idcuota", "ID Cuota");
        model.addAttribute("monto", "Monto a Pagar");
        model.addAttribute("pagos", "pagos");
        model.addAttribute("cuota", cuota);
        model.addAttribute("titulo","Cuota modificado ");
        return "cuota-template/resultado";
    }

    @GetMapping("/pagar")
    public String eliminarCuota(Model model) {
        Cuota cuota = new Cuota();
        model.addAttribute("titulo", "Buscar Cuota");
        model.addAttribute("cuota", cuota);
        model.addAttribute("error", new HashMap<>());
        return "cuota-template/pagar";
    }

    @PostMapping("/pagar")
    public String eliminarCuotaPro(@Valid Cuota cuota, BindingResult result, Model model,
                            @RequestParam(name= "idcuota") int idcuota) throws SQLException {

        if(result.hasGlobalErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("titulo", "Debe ser numero entero");
            model.addAttribute("error", errores);
            return "cuota-template/pagar";
        }
        
        CuotaManager cuotaManager = new CuotaManager();
        cuota = cuotaManager.getByid(idcuota);

        if(cuotaManager.delete(idcuota)==false){
            model.addAttribute("idcuota", "ID cuota");
            model.addAttribute("fecha", "Fecha");
            model.addAttribute("fechavencimiento", "Fecha Vencimiento");
            model.addAttribute("idinscripcion", "ID Inscripcion");
            model.addAttribute("monto", "Monto a Pagar");
            model.addAttribute("pagos", "Pagos");
            model.addAttribute("titulo","No se puede pagar, la cuota esta referido a otra base de datos");
            model.addAttribute("cuota", cuota);
        }else{
            if(cuota==null){
                model.addAttribute("idcuota", "");
                model.addAttribute("fecha", "");
                model.addAttribute("fechavencimiento", "");
                model.addAttribute("idinscripcion", "");
                model.addAttribute("monto", "");
                model.addAttribute("pagos", "");
                model.addAttribute("titulo","La cuota no esta en la base de datos");
                model.addAttribute("cuota", cuota);
            }
            else{
                model.addAttribute("idcuota", "ID cuota");
                model.addAttribute("fecha", "Fecha");
                model.addAttribute("fechavencimiento", "Fecha Vencimiento");
                model.addAttribute("idinscripcion", "ID Inscripcion");
                model.addAttribute("monto", "Monto a Pagar");
                model.addAttribute("pagos", "Pagos");
                model.addAttribute("titulo","La cuota se ha pagado");
                model.addAttribute("cuota", cuota);
            }
        }

        return "cuota-template/resultado";

    }
    
    /*
    @GetMapping("/pagar")
    public String pagarCuota(Model model) {
        Cuota cuota = new Cuota();
        model.addAttribute("titulo", "Buscar Cuota");
        model.addAttribute("cuota", cuota);
        model.addAttribute("error", new HashMap<>());
        return "cuota-template/pagar";
    }

    @PostMapping("/pagar")
    public String pagarCuotaPro(@Valid Cuota cuota, BindingResult result, Model model,
                                  @RequestParam(name= "idcuota") int idcuota,
                                  @RequestParam(name= "pagos") int pagos) throws SQLException {

        if(result.hasGlobalErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("titulo", "Debe ser numero entero");
            model.addAttribute("error", errores);
            return "cuota-template/pagar";
        }
        CuotaManager cuotaManager = new CuotaManager();
        cuota = cuotaManager.getByid(idcuota);
        Timestamp datemodify = new Timestamp(System.currentTimeMillis());
        Timestamp date = new Timestamp(System.currentTimeMillis());
        date = cuota.getFecha();
        datemodify.setMonth(date.getMonth()+pagos+1);
        int montoConstante = cuotaManager.sacarCuotaConstante();
        int montonew = montoConstante-pagos*(montoConstante/4);
        cuotaManager.modify(idcuota,montonew,pagos);
        cuota = cuotaManager.getByid(idcuota);
        model.addAttribute("idcuota", " ID cuota");
        model.addAttribute("fecha", "Fecha de cuota generada ");
        model.addAttribute("fechavencimiento", "Fecha Vencimiento siguiente");
        model.addAttribute("idinscripcion", " ID Inscripcion");
        model.addAttribute("monto", " Falta pagar ");
        model.addAttribute("pagos", " Pagos");
        model.addAttribute("titulo"," Pago realizado");
        model.addAttribute("cuota", cuota);
        return "cuota-template/resultado";
    }*/
}
