package com.bolsadeideas.springboot.web.app.controller;

import java.sql.SQLException;
import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bolsadeideas.springboot.web.app.models.Alumno;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

	@GetMapping("/listar")
	public String listaAlu(Model model) {
		AlumnoManager alumnoManager = new AlumnoManager();
		List<Alumno> alumnos = new ArrayList<>();
		alumnos = alumnoManager.getAllAlumnos();
		model.addAttribute("titulo", "Lista de Alumnos");
		model.addAttribute("mensaje", "Lista de alumnos");
		model.addAttribute("idalumno", "Idalumno"); // atributo + nombre
		model.addAttribute("nombre", "Nombre");
		model.addAttribute("apellido", "Apellido");
		model.addAttribute("alumnos", alumnos);
		return "alumno-template/listarAlumno";
	}

	@GetMapping("/agregar")
	public String agregarAlu(Model model) {
		Alumno alumno = new Alumno();
		model.addAttribute("titulo", "Agregar Alumno");
		model.addAttribute("alumno", alumno);
		model.addAttribute("error", new HashMap<>());
		return "alumno-template/agregar";
	}

	@PostMapping("/agregar")
	public String agregarAluProc(@Valid Alumno alumno, BindingResult result, Model model,
			@RequestParam(name = "nombre") String nombre, @RequestParam(name = "apellido") String apellido)
			throws SQLException {
		model.addAttribute("titulo", "Falta datos");
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("error", errores);
			return "alumno-template/agregar";
		}
		AlumnoManager AlumnoManager = new AlumnoManager();
		alumno = AlumnoManager.add(nombre, apellido);
		model.addAttribute("idalumno", "Idalumno");
		model.addAttribute("nombre", "Nombre");
		model.addAttribute("apellido", "Apellido");
		model.addAttribute("titulo", "Alumno Agregado");
		model.addAttribute("alumno", alumno);
		return "alumno-template/resultadoAlu";
	}

	@GetMapping("/modificar")
	public String modificar(Model model) {
		Alumno alumno = new Alumno();
		model.addAttribute("titulo", "Modificar Alumno");
		model.addAttribute("alumno", alumno);
		model.addAttribute("error", new HashMap<>());
		return "alumno-template/modificar";
	}

	@PostMapping("/modificar")
	public String modificar(@Valid Alumno alumno, BindingResult result, Model model,
			@RequestParam(name = "idalumno") int idalumno, @RequestParam(name = "nombre") String nombre,
			@RequestParam String apellido) {
		model.addAttribute("titulo", "Falta datos");
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("titulo", "Modificar Alumno");
			model.addAttribute("error", errores);
			return "alumno-template/modificar";
		}
		AlumnoManager alumnoManager = new AlumnoManager();
		alumnoManager.modify(idalumno, nombre, apellido);
		alumno.setIdalumno(idalumno);
		alumno.setNombre(nombre);
		alumno.setApellido(apellido);
		model.addAttribute("idalumno", "Idalumno");
		model.addAttribute("nombre", "Nombre");
		model.addAttribute("apellido", "Apellido");
		model.addAttribute("alumno", alumno);
		return "alumno-template/resultadoAlu";
	}

	@GetMapping("/buscar")
	public String buscarAlu(Model model) {
		Alumno alumno = new Alumno();
		model.addAttribute("titulo", "Buscar Alumno");
		model.addAttribute("alumno", alumno);
		model.addAttribute("error", new HashMap<>());
		return "alumno-template/buscar";
	}

	@PostMapping("/buscar")
	public String buscarAlu(@Valid Alumno alumno, BindingResult result, Model model,
			@RequestParam(name = "idalumno") int idalumno) throws SQLException {

		if (result.hasGlobalErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("titulo", "Debe ser numero entero");
			model.addAttribute("error", errores);
			return "alumno-template/buscar";
		}
		AlumnoManager studentManager = new AlumnoManager();
		alumno = studentManager.getByid(idalumno);
		model.addAttribute("idalumno", "Idalumno");
		model.addAttribute("nombre", "Nombre");
		model.addAttribute("apellido", "Apellido");
		model.addAttribute("titulo", "Alumno Encontrado");
		model.addAttribute("alumno", alumno);
		return "alumno-template/resultadoAlu";

	}

	@GetMapping("/eliminar")
	public String eliminarAlu(Model model) {
		Alumno alumno1 = new Alumno();
		model.addAttribute("titulo", "Eliminar Alumno");
		model.addAttribute("alumno", alumno1);
		model.addAttribute("error", new HashMap<>());
		return "alumno-template/eliminar";
	}

	@PostMapping("/eliminar")
	public String eliminarAluProc(@Valid Alumno alumno1, BindingResult result, Model model,
			@RequestParam(name = "idalumno") int idalumno) throws SQLException {

		if (result.hasGlobalErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("titulo", "Debe ser numero entero");
			model.addAttribute("error", errores);
			return "alumno-template/eliminar";
		}
		AlumnoManager alumnoManager = new AlumnoManager();
		alumno1 = alumnoManager.getByid(idalumno);
		if (alumnoManager.delete(idalumno) == false) {
			model.addAttribute("idalumno", " ");
			model.addAttribute("nombre", " ");
			model.addAttribute("apellido", " ");
			model.addAttribute("titulo", "El alumno no se puede eliminar esta referido a otra base de datos");
			model.addAttribute("alumno", alumno1);

		} else {
			if (alumno1 == null) {
				model.addAttribute("idalumno", " ");
				model.addAttribute("nombre", " ");
				model.addAttribute("apellido", " ");
				model.addAttribute("titulo", "El alumno no se encuentra en la base de datos");
				model.addAttribute("alumno", alumno1);
			} else {
				model.addAttribute("idalumno", "Idalumno");
				model.addAttribute("nombre", "Nombre");
				model.addAttribute("apellido", "Apellido");
				model.addAttribute("titulo", "Alumno Eliminado");
				model.addAttribute("alumno", alumno1);
			}
		}

		return "alumno-template/resultadoEliminado";

	}

}
